/**
 * Copyright Microsoft Corporation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.microsoft.azure.storage.blob;

import com.microsoft.azure.storage.implementation.StorageClientImpl;
import com.microsoft.azure.storage.models.*;
import com.microsoft.rest.v2.RestResponse;
import com.microsoft.rest.v2.http.AsyncInputStream;
import com.microsoft.rest.v2.http.HttpPipeline;
import io.reactivex.Single;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.Date;

/**
 * Represents a URL to a page blob.
 */
public final class PageBlobURL extends BlobURL {

    /**
     * Creates a new {@link PageBlobURL} object.
     * @param url
     *      A {@code String} representing a URL to a page blob.
     * @param pipeline
     *      A {@link HttpPipeline} object representing the pipeline for requests.
     */
    public PageBlobURL(String url, HttpPipeline pipeline) {
        super( url, pipeline);
    }

    /**
     * Creates a new {@link PageBlobURL} with the given pipeline.
     * @param pipeline
     *      A {@link HttpPipeline} object to set.
     * @return
     *      A {@link PageBlobURL} object with the given pipeline.
     */
    public PageBlobURL withPipeline(HttpPipeline pipeline) {
        return new PageBlobURL(super.url, pipeline);
    }

    /**
     * Creates a new {@link PageBlobURL} with the given snapshot.
     * @param snapshot
     *      A <code>java.util.Date</code> to set.
     * @return
     *      A {@link PageBlobURL} object with the given pipeline.
     */
    public PageBlobURL withSnapshot(String snapshot) throws MalformedURLException, UnsupportedEncodingException {
        BlobURLParts blobURLParts = URLParser.ParseURL(super.url);
        blobURLParts.setSnapshot(snapshot);
        return new PageBlobURL(blobURLParts.toURL(), super.storageClient.httpPipeline());
    }

    /**
     * Create creates a page blob of the specified length. Call PutPage to upload data data to a page blob.
     * For more information, see https://docs.microsoft.com/rest/api/storageservices/put-blob.
     * @param size
     *           Specifies the maximum size for the page blob, up to 8 TB. The page blob size must be aligned to a
     *           512-byte boundary.
     * @param sequenceNumber
     *            A user-controlled value that you can use to track requests. The value of the sequence number must be
     *            between 0 and 2^63 - 1.The default value is 0.
     * @param blobHttpHeaders
     *            A {@Link BlobHttpHeaders} object that specifies which properties to set on the blob.
     * @param metadata
     *            A {@Link Metadata} object that specifies key value pairs to set on the blob.
     * @param blobAccessConditions
     *            A {@Link BlobAccessConditions} object that specifies under which conditions the operation should
     *            complete.
     * @return the {@link Single &lt;RestResponse&lt;BlobsPutHeaders, Void&gt;&gt;} object if successful.
     */
    public Single<RestResponse<BlobsPutHeaders, Void>> putBlobAsync(
            Long size, Long sequenceNumber, Metadata metadata, BlobHttpHeaders blobHttpHeaders,
            BlobAccessConditions blobAccessConditions) {
        if(metadata == null) {
            metadata = Metadata.getDefault();
        }
        if(blobHttpHeaders == null) {
            blobHttpHeaders = BlobHttpHeaders.getDefault();
        }
        if(blobAccessConditions == null) {
            blobAccessConditions = BlobAccessConditions.getDefault();
        }
        return this.storageClient.blobs().putWithRestResponseAsync(this.url, BlobType.PAGE_BLOB, null,
                null, null, blobHttpHeaders.getContentType(), blobHttpHeaders.getContentEncoding(),
                blobHttpHeaders.getContentLanguage(), blobHttpHeaders.getContentMD5(), blobHttpHeaders.getCacheControl(),
                metadata.toString(), blobAccessConditions.getLeaseAccessConditions().toString(),
                blobHttpHeaders.getContentDisposition(),
                blobAccessConditions.getHttpAccessConditions().getIfModifiedSince(),
                blobAccessConditions.getHttpAccessConditions().getIfUnmodifiedSince(),
                blobAccessConditions.getHttpAccessConditions().getIfMatch().toString(),
                blobAccessConditions.getHttpAccessConditions().getIfNoneMatch().toString(),
                size, sequenceNumber, null);
    }

    /**
     * PutPages writes 1 or more pages to the page blob. The start and end offsets must be a multiple of 512.
     * For more information, see https://docs.microsoft.com/rest/api/storageservices/put-page.
     * @param pageRange
     *           A {@Link PageRange} object. Specifies the range of bytes to be written as a page.
     * @param body
     *           A {@Link AsyncInputStream} that contains the content of the page.
     * @param accessConditions
     *           A {@Link BlobAccessConditions} object that specifies under which conditions the operation should
     *           complete.
     * @return
     */
    public Single<RestResponse<PageBlobsPutPageHeaders, Void>> putPagesAsync(
            PageRange pageRange, AsyncInputStream body, BlobAccessConditions accessConditions) {
        if(accessConditions == null) {
            accessConditions = BlobAccessConditions.getDefault();
        }
        return this.storageClient.pageBlobs().putPageWithRestResponseAsync(this.url, PageWriteType.UPDATE, body,
                null, this.pageRangeToString(pageRange), accessConditions.getLeaseAccessConditions().toString(),
                accessConditions.getPageBlobAccessConditions().getIfSequenceNumberLessThanOrEqual(),
                accessConditions.getPageBlobAccessConditions().getIfSequenceNumberLessThan(),
                accessConditions.getPageBlobAccessConditions().getIfSequenceNumberEqual(),
                accessConditions.getHttpAccessConditions().getIfModifiedSince(),
                accessConditions.getHttpAccessConditions().getIfUnmodifiedSince(),
                accessConditions.getHttpAccessConditions().getIfMatch().toString(),
                accessConditions.getHttpAccessConditions().getIfNoneMatch().toString(), null);
    }

    public Single<RestResponse<BlobsSetPropertiesHeaders, Void>> setSequenceNumber(
            SequenceNumberActionType action, Long sequenceNumber, BlobHttpHeaders headers,
            BlobAccessConditions accessConditions) {
        if(headers == null) {
            headers = BlobHttpHeaders.getDefault();
        }
        if(accessConditions == null) {
            accessConditions = BlobAccessConditions.getDefault();
        }
        // TODO: validate sequenceNumber
        if(action == SequenceNumberActionType.INCREMENT) {
           sequenceNumber = null;
        }
        return this.storageClient.blobs().setPropertiesWithRestResponseAsync(this.url, null,
                headers.getCacheControl(), headers.getContentType(), headers.getContentMD5(),
                headers.getContentEncoding(), headers.getContentLanguage(),
                accessConditions.getLeaseAccessConditions().toString(),
                accessConditions.getHttpAccessConditions().getIfModifiedSince(),
                accessConditions.getHttpAccessConditions().getIfUnmodifiedSince(),
                accessConditions.getHttpAccessConditions().getIfMatch().toString(),
                accessConditions.getHttpAccessConditions().getIfNoneMatch().toString(),
                headers.getContentDisposition(),
                null, action, sequenceNumber, null);
    }

    private String pageRangeToString(PageRange pageRange) {
        // TODO: Validation on PageRange.
        StringBuilder range = new StringBuilder("bytes=");
        range.append(pageRange.start());
        range.append('-');
        range.append(pageRange.end());
        return range.toString();
    }
}