package com.microsoft.azure.storage

import com.microsoft.azure.storage.blob.BlobHTTPHeaders
import com.microsoft.azure.storage.blob.BlobURL
import com.microsoft.azure.storage.blob.Metadata
import com.microsoft.azure.storage.models.BlobGetPropertiesHeaders
import com.microsoft.azure.storage.models.BlobType
import com.microsoft.azure.storage.models.LeaseStateType
import com.microsoft.rest.v2.util.FlowableUtil
import io.reactivex.Flowable
import spock.lang.Shared
import spock.lang.Unroll

import java.nio.ByteBuffer
import java.security.MessageDigest


class BlobAPI extends APISpec{
    BlobURL bu

    @Shared
    String defaultText = "d"

    @Shared
    ByteBuffer defaultData = ByteBuffer.wrap(defaultText.bytes)

    def setup() {
        bu = cu.createBlockBlobURL(generateBlobName())
        bu.putBlob(Flowable.just(defaultData), defaultText.length(), null, null, null)
                .blockingGet()
    }

    def "Blob get all null"() {
        when:
        ByteBuffer body = FlowableUtil.collectBytesInBuffer(
                bu.getBlob(null, null, false).blockingGet().body())
                .blockingGet()

        then:
        body.compareTo(defaultData) == 0
    }

    def "Blob get properties all null"() {
        when:
        BlobGetPropertiesHeaders headers = bu.getPropertiesAndMetadata(null).blockingGet().headers()

        then:
        headers.blobType() == BlobType.BLOCK_BLOB
        headers.eTag() != null
        headers.version() != null
        headers.leaseState().toString() == "available"
    }

    @Unroll
    def "Blob set properties"() {
        setup:
        BlobHTTPHeaders putHeaders = new BlobHTTPHeaders(cacheControl, contentDisposition, contentEncoding,
                contentLanguage, contentMD5, contentType)
        bu.setProperties(putHeaders, null).blockingGet()
        BlobGetPropertiesHeaders receivedHeaders =
                bu.getPropertiesAndMetadata(null).blockingGet().headers()

        expect:
        receivedHeaders.cacheControl() == cacheControl
        receivedHeaders.contentDisposition() == contentDisposition
        receivedHeaders.contentEncoding() == contentEncoding
        receivedHeaders.contentLanguage() == contentLanguage
        receivedHeaders.contentMD5() == contentMD5
        receivedHeaders.contentType() == contentType

        where:
        cacheControl | contentDisposition | contentEncoding | contentLanguage | contentMD5                    | contentType
        null         | null               | null            | null            | null                          | null
        "control"    | "disposition"      | "encoding"      | "language"      | new String(Base64.getEncoder().encode(MessageDigest.getInstance("MD5").digest(defaultData.array()))) | "type"

    }

    @Unroll
    def "Blob set get metadata"() {
        setup:
        Metadata metadata = new Metadata()
        metadata.put(key1, value1)
        metadata.put(key2, value2)

        int initialCode = bu.setMetadata(metadata, null).blockingGet().statusCode()
        Map<String,String> receivedMetadata = bu.getPropertiesAndMetadata(null).blockingGet().headers()
                .metadata()

        expect:
        initialCode == statusCode
        receivedMetadata.get(key1).equals(value1)
        receivedMetadata.get(key2).equals(value2)

        where:
        key1    | value1     | key2     | value2    || statusCode
        "foo"   | "bar"      | "fizz"   | "buzz"    || 200
    }

    def "Blob acquire lease"() {
        setup:
        bu.acquireLease(UUID.randomUUID().toString(), -1, null).blockingGet()

        expect:
        bu.getPropertiesAndMetadata(null).blockingGet()
                .headers().leaseState().equals(LeaseStateType.LEASED)
    }

    def "Blob renew lease"() {
        setup:
        String leaseID =
                bu.acquireLease(UUID.randomUUID().toString(), 15, null).blockingGet()
                        .headers().leaseId()
        Thread.sleep(16000)
        bu.renewLease(leaseID, null).blockingGet()

        expect:
        bu.getPropertiesAndMetadata(null).blockingGet().headers().leaseState()
                .equals(LeaseStateType.LEASED)
    }

    def "Blob release lease"() {
        setup:
        String leaseID =
                bu.acquireLease(UUID.randomUUID().toString(), 15, null).blockingGet()
                        .headers().leaseId()
        bu.releaseLease(leaseID, null).blockingGet()

        expect:
        bu.getPropertiesAndMetadata(null).blockingGet().headers().leaseState()
                .equals(LeaseStateType.AVAILABLE)
    }

    def "Blob break lease"() {
        setup:
        bu.acquireLease(UUID.randomUUID().toString(), -1, null).blockingGet()

        bu.breakLease(null, null).blockingGet()

        expect:
        bu.getPropertiesAndMetadata(null).blockingGet().headers().leaseState()
                .equals(LeaseStateType.BROKEN)

    }

    def "Blob change lease"() {
        setup:
        String leaseID =
                bu.acquireLease(UUID.randomUUID().toString(), 15, null).blockingGet()
                        .headers().leaseId()
        leaseID = bu.changeLease(leaseID, UUID.randomUUID().toString(), null).blockingGet()
                .headers().leaseId()

        expect:
        bu.releaseLease(leaseID, null).blockingGet().statusCode() == 200
    }
}
