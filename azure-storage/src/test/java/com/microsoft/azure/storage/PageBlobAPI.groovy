package com.microsoft.azure.storage

import com.microsoft.azure.storage.blob.BlobRange
import com.microsoft.azure.storage.blob.PageBlobURL

import com.microsoft.rest.v2.RestResponse
import io.reactivex.Flowable

class PageBlobAPI extends APISpec {
    PageBlobURL bu

    def setup(){
        bu = cu.createPageBlobURL(generateBlobName())
        bu.create(512, null, null, null, null).blockingGet()
    }

    def "Page blob create all null"() {
        setup:
        bu = cu.createPageBlobURL(generateBlobName())

        when:
        RestResponse<BlobPutHeaders, Void> response =
        bu.create(512, null, null, null, null).blockingGet()

        then:
        response.statusCode() == 201
        response.headers().eTag() != null
        response.headers().dateProperty() != null
        response.headers().lastModified() != null
        response.headers().requestId() != null
        response.headers().version() != null
    }

    def "Page blob put page"() {
        expect:
        bu.uploadPages(new PageRange().withStart(0).withEnd(511),
                Flowable.just(getRandomData(512)), null).blockingGet()
                .statusCode() == 201
    }

    def "Page blob clear page"() {
        setup:
        bu.uploadPages(new PageRange().withStart(0).withEnd(511),
                Flowable.just(getRandomData(512)), null).blockingGet()

        when:
        bu.clearPages(new PageRange().withStart(0).withEnd(511), null).blockingGet()
        PageList pages = bu.getPageRanges(null, null).blockingGet().body()

        then:
        pages.pageRange() == null
    }

    def "Page blob get page ranges"() {
        setup:
        bu.uploadPages(new PageRange().withStart(0).withEnd(511),
                Flowable.just(getRandomData(512)),null).blockingGet()
        expect:
        bu.getPageRanges(new BlobRange(0, 512), null).blockingGet().body()
                .pageRange().size() == 1
    }

    def "Page blob get page ranges diff"() {
        setup:
        String snapshot = bu.createSnapshot(null, null).blockingGet().headers().snapshot()
        bu.uploadPages(new PageRange().withStart(0).withEnd(511),
                Flowable.just(getRandomData(512)),null).blockingGet()

        expect:
        bu.getPageRangesDiff(new BlobRange(0,512), snapshot, null).blockingGet().body()
                .pageRange().size() == 1
    }

    def "Page blob resize"() {
        setup:
        bu.resize(1024, null).blockingGet()

        expect:
        bu.getProperties(null).blockingGet().headers().contentLength() == 1024

    }

    def "Page blob sequence number"() {
        setup:
        bu.updateSequenceNumber(SequenceNumberActionType.UPDATE, 5, null, null)
                .blockingGet()

        expect:
        bu.getProperties(null).blockingGet().headers().blobSequenceNumber() == 5
    }

    def "Page blob start incremental copy"() {
        setup:
        cu.setAccessPolicy(PublicAccessType.BLOB, null, null).blockingGet()
        PageBlobURL bu2 = cu.createPageBlobURL(generateBlobName())
        String snapshot = bu.createSnapshot(null, null).blockingGet().headers().snapshot()
        bu2.copyIncremental(bu.toURL(), snapshot, null).blockingGet()

        expect:
        bu2.getProperties(null).blockingGet().headers().isIncrementalCopy()

    }
}
