package com.microsoft.azure.storage

import com.microsoft.azure.storage.blob.ContainerAccessConditions
import com.microsoft.azure.storage.blob.ContainerURL
import com.microsoft.azure.storage.blob.ETag
import com.microsoft.azure.storage.blob.HTTPAccessConditions
import com.microsoft.azure.storage.blob.LeaseAccessConditions
import com.microsoft.azure.storage.blob.Metadata
import com.microsoft.azure.storage.models.ContainerCreateHeaders
import com.microsoft.azure.storage.models.ContainerGetPropertiesHeaders
import com.microsoft.azure.storage.models.ContainerLeaseHeaders
import com.microsoft.azure.storage.models.PublicAccessType
import com.microsoft.rest.v2.RestException
import com.microsoft.rest.v2.RestResponse
import spock.lang.*

class ContainerAPI extends Specification {

    @Shared
    int iterationNo = 0 // Used to generate stable container names for recording tests with multiple iterations.l

    int containerNo = 0 // Used to generate stable container names for recording tests requiring multiple containers.

    ContainerURL cu

    def generateContainerName() {
        /*
        The container name suffix ensures that the container name is unique for each test so there are no conflicts.
        If we are not recording, we can just use the time. If we are recording, the suffix must always be the same
        so we can match requests. To solve this, we use the iteration number of the test we are on.
         */
        String suffix = ""
        if (TestUtility.enableRecordings) {
            if (specificationContext.currentIteration.estimatedNumIterations > 1) {
                suffix += iterationNo++
            }
            else {
                iterationNo = 0
                suffix += iterationNo
            }
            suffix += containerNo++
        }
        else {
            suffix = System.currentTimeMillis()
        }
        TestUtility.generateContainerName("", TestUtility.getTestName(specificationContext), suffix.toString())
    }

    def cleanupSpec() {
        TestUtility.cleanupContainers()
    }

    def setup() {
        cu = TestUtility.getPrimaryServiceURL().createContainerURL(generateContainerName())
        cu.create(null, null).blockingGet()
        TestUtility.setupFeatureRecording(specificationContext.getCurrentIteration().name)
    }

    def cleanup() {
        TestUtility.cleanupFeatureRecording()
    }

    def "Container create all null"() {
        setup:
        // Overwrite the existing cu, which has already been created
        cu = TestUtility.getPrimaryServiceURL().createContainerURL(generateContainerName())

        when:
        RestResponse<ContainerCreateHeaders, Void> response = cu.create(null, null).blockingGet()

        then:
        response.statusCode() == 201
        response.headers().eTag() != null
        response.headers().dateProperty() != null
        response.headers().lastModified() != null
        response.headers().requestId() != null
        response.headers().version() != null
    }

    def "Container create metadata"() {
        setup:
        cu = TestUtility.getPrimaryServiceURL().createContainerURL(generateContainerName())

        Metadata metadata = new Metadata()
        metadata.put("foo", "bar")
        metadata.put("fizz", "buzz")

        when:
        int statusCode = cu.create(metadata, null).blockingGet().statusCode()
        Map<String, String> receivedMetadata =
                cu.getPropertiesAndMetadata(null).blockingGet().headers().metadata()

        then:
        statusCode == 201
        receivedMetadata.equals(metadata)
    }

    def "Container create publicAccess Blob"() {
        setup:
        cu = TestUtility.getPrimaryServiceURL().createContainerURL(generateContainerName())

        when:
        int statusCode = cu.create(null, PublicAccessType.BLOB).blockingGet().statusCode()
        PublicAccessType access =
                cu.getPropertiesAndMetadata(null).blockingGet().headers().blobPublicAccess()

        then:
        statusCode == 201
        access.toString() == "blob"
    }

    def "Container get properties null"() {
        when:
        ContainerGetPropertiesHeaders headers =
                cu.getPropertiesAndMetadata(null).blockingGet().headers()

        then:
        headers.blobPublicAccess() == null
        headers.eTag() != null
        headers.version() != null
        headers.leaseState().toString() == "available"
    }

    @Unroll
    def "Container get properties AC"(){
        setup:
        ContainerLeaseHeaders headers =
                cu.acquireLease(UUID.randomUUID().toString(), -1, null)
                        .blockingGet().headers()
        if (leaseID.equals(TestUtility.receivedLeaseID)) {
            leaseID = headers.leaseId()
        }

        int code

        try {
            code = cu.getPropertiesAndMetadata(new LeaseAccessConditions(leaseID)).blockingGet().statusCode()
        }
        catch (RestException e) {
            code = e.response().statusCode()
        }
        expect:
        code == statusCode

        where:
        leaseID                     || statusCode
        null                        || 200
        TestUtility.garbageLeaseID  || 412
        TestUtility.receivedLeaseID || 200

    }

    @Unroll
    def "Container set metadata"() {
        setup:
        Metadata metadata = new Metadata()
        metadata.put(key1, value1)
        metadata.put(key2, value2)

        expect:
        cu.setMetadata(metadata, null).blockingGet().statusCode() == statusCode

        where:
        key1    | value1     | key2     | value2    || statusCode
        "foo"   | "bar"      | "fizz"   | "buzz"    || 200
        //TODO: invalid characters. empty metadata
    }

    @Unroll
    def "Container delete AC"() {
        setup:
        ContainerGetPropertiesHeaders headers =
                cu.getPropertiesAndMetadata(null).blockingGet().headers()
        if (match.equals(TestUtility.receivedEtag)) {
            match = new ETag(headers.eTag())
        }
        if (leaseID != null) {
            ContainerLeaseHeaders headers2 =
                    cu.acquireLease(UUID.randomUUID().toString(), -1, null).blockingGet().headers()
            if (leaseID.equals(TestUtility.receivedLeaseID)) {
                leaseID = headers2.leaseId()
            }
        }
        ContainerAccessConditions cac = new ContainerAccessConditions(
                new HTTPAccessConditions(modified, unmodified, match, noneMatch), new LeaseAccessConditions(leaseID))

        int code = 0
        boolean foundErrorMessage = false
        try{
            code = cu.delete(cac).blockingGet().statusCode()
        }
        catch (RestException e) {
            code = e.response().statusCode()
            // We don't need to check the error message here because we check the status code.
        }
        catch (IllegalArgumentException e) {
            foundErrorMessage = e.message.contains("ETag access conditions are not supported")
        }
        expect:
        code == statusCode
        foundErrorMessage == foundMessage

        /*
        For some reason, putting the 400 cases first works fine but putting them later in the sequence
        yields some sort of timeout error.
         */
        where:
        modified            | unmodified          | match                    | noneMatch               | leaseID                     || statusCode | foundMessage
        TestUtility.newDate | null                | null                     | null                    | null                        || 412        | false
        null                | TestUtility.oldDate | null                     | null                    | null                        || 412        | false
        null                | null                | null                     | null                    | null                        || 202        | false
        TestUtility.oldDate | null                | null                     | null                    | null                        || 202        | false
        null                | TestUtility.newDate | null                     | null                    | null                        || 202        | false
        null                | null                | TestUtility.receivedEtag | null                    | null                        || 0          | true
        null                | null                | null                     | TestUtility.garbageEtag | null                        || 0          | true
        null                | null                | null                     | null                    | TestUtility.garbageLeaseID  || 412        | false
        null                | null                | null                     | null                    | TestUtility.receivedLeaseID || 202        | false
    }
}
