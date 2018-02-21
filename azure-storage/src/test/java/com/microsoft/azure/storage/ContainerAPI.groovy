package com.microsoft.azure.storage

import com.microsoft.azure.storage.blob.BlockBlobURL
import com.microsoft.azure.storage.blob.ContainerAccessConditions
import com.microsoft.azure.storage.blob.ContainerURL
import com.microsoft.azure.storage.blob.ETag
import com.microsoft.azure.storage.blob.HTTPAccessConditions
import com.microsoft.azure.storage.blob.LeaseAccessConditions
import com.microsoft.azure.storage.blob.PipelineOptions
import com.microsoft.azure.storage.blob.ServiceURL
import com.microsoft.azure.storage.blob.SharedKeyCredentials
import com.microsoft.azure.storage.blob.StorageURL
import com.microsoft.azure.storage.models.ContainerCreateHeaders
import com.microsoft.azure.storage.models.PublicAccessType
import com.microsoft.rest.v2.RestException
import com.microsoft.rest.v2.http.HttpClient
import com.microsoft.rest.v2.http.HttpPipeline
import org.joda.time.DateTime
import spock.lang.*

class ContainerAPI extends Specification {
    @Shared
    Date oldDate = new DateTime().minusDays(1).toDate()

    @Shared
    Date newDate = new DateTime().plusDays(1).toDate()

    @Shared
    ETag receivedEtag = new ETag("received")

    @Shared
    ETag garbageEtag = new ETag("garbage")

    static ServiceURL su

    String containerName = "javatestcontainer" + System.currentTimeMillis()

    ContainerURL cu

    def setupSpec() {
        SharedKeyCredentials creds = new SharedKeyCredentials(System.getenv().get("ACCOUNT_NAME"),
                System.getenv().get("ACCOUNT_KEY"))

        PipelineOptions po = new PipelineOptions()
        HttpClient.Configuration configuration = new HttpClient.Configuration(
                new Proxy(Proxy.Type.HTTP, new InetSocketAddress("localhost", 8888)));
        po.client = HttpClient.createDefault();//configuration);
        HttpPipeline pipeline = StorageURL.createPipeline(creds, po)

        this.su = new ServiceURL(
                new URL("http://" + System.getenv().get("ACCOUNT_NAME") + ".blob.core.windows.net"), pipeline)
    }

    def setup() {
        this.cu = this.su.createContainerURL(containerName)
    }

    @Unroll
    def "Delete container with access conditions"() {
        setup:
        ContainerCreateHeaders headers = cu.create(null, PublicAccessType.BLOB).blockingGet().headers()
        if (match.equals(receivedEtag)) {
            match = new ETag(headers.eTag())
        }
        ContainerAccessConditions cac = new ContainerAccessConditions(
                new HTTPAccessConditions(modified, unmodified, match, noneMatch), LeaseAccessConditions.NONE)
        int code = 0
        try{
            code = cu.delete(cac).blockingGet().statusCode()
        }
        catch (RestException e) {
            code = e.response().statusCode()
            cu.delete(null).blockingGet() // If we were testing a failure case, still clean up the container
        }
        catch (IllegalArgumentException e) {
            e.message.contains("ETag access conditions are not supported")
            cu.delete(null).blockingGet()
        }
        expect:
        code == statusCode

        where:
        modified           | unmodified      | match          | noneMatch        || statusCode
        null               | null            | null           | null             || 202
        oldDate            | null            | null           | null             || 202
        null               | newDate         | null           | null             || 202
        newDate            | null            | null           | null             || 412
        null               | oldDate         | null           | null             || 412
        null               | null            | receivedEtag   | null             || 0
        null               | null            | null           | garbageEtag      || 0
        /*
         Note that the value of receivedEtag is overwritten. It merely indicates that the received ETag
         will be used for the test once it is received.
         */
    }
}
