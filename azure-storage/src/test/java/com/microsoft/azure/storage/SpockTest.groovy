package com.microsoft.azure.storage

import com.microsoft.azure.storage.blob.BlockBlobURL
import com.microsoft.azure.storage.blob.ContainerAccessConditions
import com.microsoft.azure.storage.blob.ContainerURL
import com.microsoft.azure.storage.blob.ETag
import com.microsoft.azure.storage.blob.HTTPAccessConditions
import com.microsoft.azure.storage.blob.PipelineOptions
import com.microsoft.azure.storage.blob.ServiceURL
import com.microsoft.azure.storage.blob.SharedKeyCredentials
import com.microsoft.azure.storage.blob.StorageURL
import com.microsoft.azure.storage.models.ContainerCreateHeaders
import com.microsoft.azure.storage.models.PublicAccessType
import com.microsoft.rest.v2.http.HttpClient
import com.microsoft.rest.v2.http.HttpPipeline
import org.joda.time.DateTime
import spock.lang.*

class SpockTest extends Specification {
    def "Easy test"() {
        expect:
        Math.min(p, b) == c

        where:
        a | b || c
        3 | 7 || 3
        5 | 4 || 4
    }

    def "A little harder"() {
        expect:
        a == b

        where:
        a    || b
        null || null
    }
    def "Will this help with access conditions?"() {

        setup:
        SharedKeyCredentials creds = new SharedKeyCredentials(System.getenv().get("ACCOUNT_NAME"),
                System.getenv().get("ACCOUNT_KEY"));

        PipelineOptions po = new PipelineOptions();
        HttpClient.Configuration configuration = new HttpClient.Configuration(
                new Proxy(Proxy.Type.HTTP, new InetSocketAddress("localhost", 8888)));
        po.client = HttpClient.createDefault();//configuration);
        HttpPipeline pipeline = StorageURL.createPipeline(creds, po);

        ServiceURL su = new ServiceURL(
                new URL("http://" + System.getenv().get("ACCOUNT_NAME") + ".blob.core.windows.net"), pipeline);

        String containerName = "javatestcontainer" + System.currentTimeMillis();
        ContainerURL cu = su.createContainerURL(containerName);

        BlockBlobURL bu = cu.createBlockBlobURL("javatestblob");

        try {

            when:
            ContainerCreateHeaders headers = cu.create(null, PublicAccessType.BLOB).blockingGet().headers();
            String etag = headers.eTag()
            Integer statusCode = -1;
            //String match, noneMatch
            //Date modified, unmodified
            Integer expected
            ContainerAccessConditions cac = new ContainerAccessConditions(
                    new HTTPAccessConditions(modified, unmodified, match, noneMatch), null)
            statusCode = cu.delete(cac).blockingGet().statusCode()
            Date oldDate = new DateTime().minusDays(1).toDate()
            Date nullDate = null
            then:
            statusCode == expected
            where:
            modified | unmodified | match      | noneMatch  || expected
            oldDate  | nullDate   | ETag.NONE  | ETag.NONE  || 202
            nullDate | nullDate   | ETag(etag) | ETag.NONE  || 202
            nullDate | nullDate   | ETag.NONE  | etag       || -1
        }
        finally {
            cu.delete(null);
        }
    }
}
