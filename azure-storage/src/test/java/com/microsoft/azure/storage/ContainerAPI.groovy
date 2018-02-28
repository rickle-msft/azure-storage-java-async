package com.microsoft.azure.storage

import com.microsoft.azure.storage.blob.ContainerAccessConditions
import com.microsoft.azure.storage.blob.ContainerURL
import com.microsoft.azure.storage.blob.ETag
import com.microsoft.azure.storage.blob.HTTPAccessConditions
import com.microsoft.azure.storage.blob.LeaseAccessConditions
import com.microsoft.azure.storage.blob.Metadata
import com.microsoft.azure.storage.models.ContainerCreateHeaders
import com.microsoft.azure.storage.models.PublicAccessType
import com.microsoft.rest.v2.RestException
import com.microsoft.rest.v2.RestResponse
import spock.lang.*

class ContainerAPI extends Specification {

    @Shared
    int testNo = 0 // Used to generate stable container names for recordings

    ContainerURL cu

    def generateContainerName() {
        String suffix = TestUtility.enableRecordings ? testNo.toString() : System.currentTimeMillis().toString()
        TestUtility.generateContainerName("",
                specificationContext.currentFeature.name.replace(' ', '').toLowerCase(),
                suffix.toString())
    }

    def cleanupSpec() {
        TestUtility.cleanupContainers()
    }

    def setup() {
        cu = TestUtility.getPrimaryServiceURL().createContainerURL(generateContainerName())
        TestUtility.setupFeatureRecording(specificationContext.getCurrentIteration().name)
    }

    def cleanup() {
        testNo++
        TestUtility.cleanupFeatureRecording()
    }

    def "Container create all null" () {
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

    def "Container create metadata" () {
        setup:
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

    def "Container create publicAccess Blob" () {
        when:
        int statusCode = cu.create(null, PublicAccessType.BLOB).blockingGet().statusCode()
        PublicAccessType access =
                cu.getPropertiesAndMetadata(null).blockingGet().headers().blobPublicAccess()

        then:
        statusCode == 201
        access == PublicAccessType.BLOB
    }

    @Unroll
    def "Container delete with access conditions"() {
        setup:
        ContainerCreateHeaders headers = cu.create(null, PublicAccessType.BLOB).blockingGet().headers()
        if (match.equals(TestUtility.receivedEtag)) {
            match = new ETag(headers.eTag())
        }
        ContainerAccessConditions cac = new ContainerAccessConditions(
                new HTTPAccessConditions(modified, unmodified, match, noneMatch), LeaseAccessConditions.NONE)
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
        modified            | unmodified          | match                    | noneMatch               || statusCode | foundMessage
        TestUtility.newDate | null                | null                     | null                    || 412        | false
        null                | TestUtility.oldDate | null                     | null                    || 412        | false
        null                | null                | null                     | null                    || 202        | false
        TestUtility.oldDate | null                | null                     | null                    || 202        | false
        null                | TestUtility.newDate | null                     | null                    || 202        | false
        null                | null                | TestUtility.receivedEtag | null                    || 0          | true
        null                | null                | null                     | TestUtility.garbageEtag || 0          | true
    }
}
