package com.microsoft.azure.storage.blob;

import com.microsoft.azure.storage.models.BlobPutHeaders;
import com.microsoft.azure.storage.models.BlobPutResponse;
import com.microsoft.azure.storage.models.BlockBlobPutBlockListHeaders;
import com.microsoft.azure.storage.models.BlockBlobPutBlockListResponse;
import com.microsoft.rest.v2.RestResponse;

import java.time.OffsetDateTime;

/**
 * A generic wrapper for any type of blob REST API response. Used and returned by methods in the {@link Highlevel}
 * class. The methods there return this type because they represent composite operations which may conclude with any of
 * several possible REST calls depending on the data provided.
 */
public final class CommonRestResponse {

    private RestResponse<BlobPutHeaders, Void> putBlobResponse;

    private RestResponse<BlockBlobPutBlockListHeaders, Void> putBlockListResponse;

    static CommonRestResponse createFromPutBlobResponse(BlobPutResponse response) {
        CommonRestResponse commonRestResponse = new CommonRestResponse();
        commonRestResponse.putBlobResponse = response;
        return commonRestResponse;
    }

    static CommonRestResponse createFromPutBlockListResponse(BlockBlobPutBlockListResponse response) {
        CommonRestResponse commonRestResponse = new CommonRestResponse();
        commonRestResponse.putBlockListResponse = response;
        return commonRestResponse;
    }

    private CommonRestResponse() {
        putBlobResponse = null;
        putBlockListResponse = null;
    }

    public String eTag() {
        if (putBlobResponse != null) {
            return putBlobResponse.headers().eTag();
        }
        return putBlockListResponse.headers().eTag();
    }

    public OffsetDateTime lastModifiedTime() {
        if (putBlobResponse != null) {
            return putBlobResponse.headers().lastModified();
        }
        return putBlockListResponse.headers().lastModified();
    }

    public String requestId() {
        if (putBlobResponse != null) {
            return putBlobResponse.headers().requestId();
        }
        return putBlockListResponse.headers().requestId();
    }

    public OffsetDateTime date() {
        if (putBlobResponse != null) {
            return putBlobResponse.headers().dateProperty();
        }
        return putBlockListResponse.headers().dateProperty();
    }

    public String version() {
        if (putBlobResponse != null) {
            return putBlobResponse.headers().version();
        }
        return putBlockListResponse.headers().version();
    }

    public RestResponse response() {
        if (putBlobResponse != null) {
            return putBlobResponse;
        }
        return putBlockListResponse;
    }

}
