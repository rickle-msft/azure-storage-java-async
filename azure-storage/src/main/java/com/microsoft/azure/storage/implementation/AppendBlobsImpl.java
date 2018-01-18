/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 * Changes may cause incorrect behavior and will be lost if the code is
 * regenerated.
 */

package com.microsoft.azure.storage.implementation;

import com.google.common.reflect.TypeToken;
import com.microsoft.azure.storage.AppendBlobs;
import com.microsoft.azure.storage.models.AppendBlobAppendBlockHeaders;
import com.microsoft.rest.v2.DateTimeRfc1123;
import com.microsoft.rest.v2.RestException;
import com.microsoft.rest.v2.RestProxy;
import com.microsoft.rest.v2.RestResponse;
import com.microsoft.rest.v2.ServiceCallback;
import com.microsoft.rest.v2.ServiceFuture;
import com.microsoft.rest.v2.annotations.BodyParam;
import com.microsoft.rest.v2.annotations.ExpectedResponses;
import com.microsoft.rest.v2.annotations.HeaderParam;
import com.microsoft.rest.v2.annotations.Headers;
import com.microsoft.rest.v2.annotations.Host;
import com.microsoft.rest.v2.annotations.HostParam;
import com.microsoft.rest.v2.annotations.PathParam;
import com.microsoft.rest.v2.annotations.PUT;
import com.microsoft.rest.v2.annotations.QueryParam;
import com.microsoft.rest.v2.http.AsyncInputStream;
import com.microsoft.rest.v2.http.HttpClient;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.functions.Function;
import java.io.IOException;
import org.joda.time.DateTime;

/**
 * An instance of this class provides access to all the operations defined in
 * AppendBlobs.
 */
public class AppendBlobsImpl implements AppendBlobs {
    /**
     * The proxy service used to perform REST calls.
     */
    private AppendBlobsService service;

    /**
     * The service client containing this operation class.
     */
    private StorageClientImpl client;

    /**
     * Initializes an instance of AppendBlobsImpl.
     *
     * @param client the instance of the service client containing this operation class.
     */
    public AppendBlobsImpl(StorageClientImpl client) {
        this.service = RestProxy.create(AppendBlobsService.class, client);
        this.client = client;
    }

    /**
     * The interface defining all the services for AppendBlobs to be used by
     * the proxy service to perform REST calls.
     */
    @Host("{url}")
    interface AppendBlobsService {
        @PUT("{containerName}/{blob}")
        @ExpectedResponses({201})
        Single<RestResponse<AppendBlobAppendBlockHeaders, Void>> appendBlock(@HostParam("url") String url, @BodyParam("application/xml; charset=utf-8") AsyncInputStream body, @QueryParam("timeout") Integer timeout, @HeaderParam("x-ms-lease-id") String leaseId, @HeaderParam("x-ms-blob-condition-maxsize") Integer maxSize, @HeaderParam("x-ms-blob-condition-appendpos") Integer appendPosition, @HeaderParam("If-Modified-Since") DateTimeRfc1123 ifModifiedSince, @HeaderParam("If-Unmodified-Since") DateTimeRfc1123 ifUnmodifiedSince, @HeaderParam("If-Match") String ifMatches, @HeaderParam("If-None-Match") String ifNoneMatch, @HeaderParam("x-ms-version") String version, @HeaderParam("x-ms-client-request-id") String requestId, @QueryParam("comp") String comp);
    }

    /**
     * The Append Block operation commits a new block of data to the end of an existing append blob. The Append Block operation is permitted only if the blob was created with x-ms-blob-type set to AppendBlob. Append Block is supported only on version 2015-02-21 version or later.
     *
     * @param body Initial data
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @throws RestException thrown if the request is rejected by server
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent
     */
    public void appendBlock(AsyncInputStream body) {
        appendBlockAsync(body).blockingAwait();
    }

    /**
     * The Append Block operation commits a new block of data to the end of an existing append blob. The Append Block operation is permitted only if the blob was created with x-ms-blob-type set to AppendBlob. Append Block is supported only on version 2015-02-21 version or later.
     *
     * @param body Initial data
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the {@link ServiceFuture} object
     */
    public ServiceFuture<Void> appendBlockAsync(AsyncInputStream body, final ServiceCallback<Void> serviceCallback) {
        return ServiceFuture.fromBody(appendBlockAsync(body), serviceCallback);
    }

    /**
     * The Append Block operation commits a new block of data to the end of an existing append blob. The Append Block operation is permitted only if the blob was created with x-ms-blob-type set to AppendBlob. Append Block is supported only on version 2015-02-21 version or later.
     *
     * @param body Initial data
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the {@link Single&lt;RestResponse&lt;AppendBlobAppendBlockHeaders, Void&gt;&gt;} object if successful.
     */
    public Single<RestResponse<AppendBlobAppendBlockHeaders, Void>> appendBlockWithRestResponseAsync(AsyncInputStream body) {
        if (this.client.url() == null) {
            throw new IllegalArgumentException("Parameter this.client.url() is required and cannot be null.");
        }
        if (body == null) {
            throw new IllegalArgumentException("Parameter body is required and cannot be null.");
        }
        if (this.client.version() == null) {
            throw new IllegalArgumentException("Parameter this.client.version() is required and cannot be null.");
        }
        final String comp = "appendblock";
        final Integer timeout = null;
        final String leaseId = null;
        final Integer maxSize = null;
        final Integer appendPosition = null;
        final DateTime ifModifiedSince = null;
        final DateTime ifUnmodifiedSince = null;
        final String ifMatches = null;
        final String ifNoneMatch = null;
        final String requestId = null;
        DateTimeRfc1123 ifModifiedSinceConverted = null;
        if (ifModifiedSince != null) {
            ifModifiedSinceConverted = new DateTimeRfc1123(ifModifiedSince);
        }
        DateTimeRfc1123 ifUnmodifiedSinceConverted = null;
        if (ifUnmodifiedSince != null) {
            ifUnmodifiedSinceConverted = new DateTimeRfc1123(ifUnmodifiedSince);
        }
        return service.appendBlock(this.client.url(), body, timeout, leaseId, maxSize, appendPosition, ifModifiedSinceConverted, ifUnmodifiedSinceConverted, ifMatches, ifNoneMatch, this.client.version(), requestId, comp);
    }

    /**
     * The Append Block operation commits a new block of data to the end of an existing append blob. The Append Block operation is permitted only if the blob was created with x-ms-blob-type set to AppendBlob. Append Block is supported only on version 2015-02-21 version or later.
     *
     * @param body Initial data
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return a {@link Single} emitting the RestResponse<AppendBlobAppendBlockHeaders, Void> object
     */
    public Completable appendBlockAsync(AsyncInputStream body) {
        return appendBlockWithRestResponseAsync(body)
            .toCompletable();
    }

    /**
     * The Append Block operation commits a new block of data to the end of an existing append blob. The Append Block operation is permitted only if the blob was created with x-ms-blob-type set to AppendBlob. Append Block is supported only on version 2015-02-21 version or later.
     *
     * @param body Initial data
     * @param timeout The timeout parameter is expressed in seconds. For more information, see &lt;a href="https://docs.microsoft.com/en-us/rest/api/storageservices/fileservices/setting-timeouts-for-blob-service-operations"&gt;Setting Timeouts for Blob Service Operations.&lt;/a&gt;
     * @param leaseId If specified, the operation only succeeds if the container's lease is active and matches this ID.
     * @param maxSize Optional conditional header. The max length in bytes permitted for the append blob. If the Append Block operation would cause the blob to exceed that limit or if the blob size is already greater than the value specified in this header, the request will fail with MaxBlobSizeConditionNotMet error (HTTP status code 412 - Precondition Failed).
     * @param appendPosition Optional conditional header, used only for the Append Block operation. A number indicating the byte offset to compare. Append Block will succeed only if the append position is equal to this number. If it is not, the request will fail with the AppendPositionConditionNotMet error (HTTP status code 412 - Precondition Failed).
     * @param ifModifiedSince Specify this header value to operate only on a blob if it has been modified since the specified date/time.
     * @param ifUnmodifiedSince Specify this header value to operate only on a blob if it has not been modified since the specified date/time.
     * @param ifMatches Specify an ETag value to operate only on blobs with a matching value.
     * @param ifNoneMatch Specify an ETag value to operate only on blobs without a matching value.
     * @param requestId Provides a client-generated, opaque value with a 1 KB character limit that is recorded in the analytics logs when storage analytics logging is enabled.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @throws RestException thrown if the request is rejected by server
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent
     */
    public void appendBlock(AsyncInputStream body, Integer timeout, String leaseId, Integer maxSize, Integer appendPosition, DateTime ifModifiedSince, DateTime ifUnmodifiedSince, String ifMatches, String ifNoneMatch, String requestId) {
        appendBlockAsync(body, timeout, leaseId, maxSize, appendPosition, ifModifiedSince, ifUnmodifiedSince, ifMatches, ifNoneMatch, requestId).blockingAwait();
    }

    /**
     * The Append Block operation commits a new block of data to the end of an existing append blob. The Append Block operation is permitted only if the blob was created with x-ms-blob-type set to AppendBlob. Append Block is supported only on version 2015-02-21 version or later.
     *
     * @param body Initial data
     * @param timeout The timeout parameter is expressed in seconds. For more information, see &lt;a href="https://docs.microsoft.com/en-us/rest/api/storageservices/fileservices/setting-timeouts-for-blob-service-operations"&gt;Setting Timeouts for Blob Service Operations.&lt;/a&gt;
     * @param leaseId If specified, the operation only succeeds if the container's lease is active and matches this ID.
     * @param maxSize Optional conditional header. The max length in bytes permitted for the append blob. If the Append Block operation would cause the blob to exceed that limit or if the blob size is already greater than the value specified in this header, the request will fail with MaxBlobSizeConditionNotMet error (HTTP status code 412 - Precondition Failed).
     * @param appendPosition Optional conditional header, used only for the Append Block operation. A number indicating the byte offset to compare. Append Block will succeed only if the append position is equal to this number. If it is not, the request will fail with the AppendPositionConditionNotMet error (HTTP status code 412 - Precondition Failed).
     * @param ifModifiedSince Specify this header value to operate only on a blob if it has been modified since the specified date/time.
     * @param ifUnmodifiedSince Specify this header value to operate only on a blob if it has not been modified since the specified date/time.
     * @param ifMatches Specify an ETag value to operate only on blobs with a matching value.
     * @param ifNoneMatch Specify an ETag value to operate only on blobs without a matching value.
     * @param requestId Provides a client-generated, opaque value with a 1 KB character limit that is recorded in the analytics logs when storage analytics logging is enabled.
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the {@link ServiceFuture} object
     */
    public ServiceFuture<Void> appendBlockAsync(AsyncInputStream body, Integer timeout, String leaseId, Integer maxSize, Integer appendPosition, DateTime ifModifiedSince, DateTime ifUnmodifiedSince, String ifMatches, String ifNoneMatch, String requestId, final ServiceCallback<Void> serviceCallback) {
        return ServiceFuture.fromBody(appendBlockAsync(body, timeout, leaseId, maxSize, appendPosition, ifModifiedSince, ifUnmodifiedSince, ifMatches, ifNoneMatch, requestId), serviceCallback);
    }

    /**
     * The Append Block operation commits a new block of data to the end of an existing append blob. The Append Block operation is permitted only if the blob was created with x-ms-blob-type set to AppendBlob. Append Block is supported only on version 2015-02-21 version or later.
     *
     * @param body Initial data
     * @param timeout The timeout parameter is expressed in seconds. For more information, see &lt;a href="https://docs.microsoft.com/en-us/rest/api/storageservices/fileservices/setting-timeouts-for-blob-service-operations"&gt;Setting Timeouts for Blob Service Operations.&lt;/a&gt;
     * @param leaseId If specified, the operation only succeeds if the container's lease is active and matches this ID.
     * @param maxSize Optional conditional header. The max length in bytes permitted for the append blob. If the Append Block operation would cause the blob to exceed that limit or if the blob size is already greater than the value specified in this header, the request will fail with MaxBlobSizeConditionNotMet error (HTTP status code 412 - Precondition Failed).
     * @param appendPosition Optional conditional header, used only for the Append Block operation. A number indicating the byte offset to compare. Append Block will succeed only if the append position is equal to this number. If it is not, the request will fail with the AppendPositionConditionNotMet error (HTTP status code 412 - Precondition Failed).
     * @param ifModifiedSince Specify this header value to operate only on a blob if it has been modified since the specified date/time.
     * @param ifUnmodifiedSince Specify this header value to operate only on a blob if it has not been modified since the specified date/time.
     * @param ifMatches Specify an ETag value to operate only on blobs with a matching value.
     * @param ifNoneMatch Specify an ETag value to operate only on blobs without a matching value.
     * @param requestId Provides a client-generated, opaque value with a 1 KB character limit that is recorded in the analytics logs when storage analytics logging is enabled.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the {@link Single&lt;RestResponse&lt;AppendBlobAppendBlockHeaders, Void&gt;&gt;} object if successful.
     */
    public Single<RestResponse<AppendBlobAppendBlockHeaders, Void>> appendBlockWithRestResponseAsync(AsyncInputStream body, Integer timeout, String leaseId, Integer maxSize, Integer appendPosition, DateTime ifModifiedSince, DateTime ifUnmodifiedSince, String ifMatches, String ifNoneMatch, String requestId) {
        if (this.client.url() == null) {
            throw new IllegalArgumentException("Parameter this.client.url() is required and cannot be null.");
        }
        if (body == null) {
            throw new IllegalArgumentException("Parameter body is required and cannot be null.");
        }
        if (this.client.version() == null) {
            throw new IllegalArgumentException("Parameter this.client.version() is required and cannot be null.");
        }
        final String comp = "appendblock";
        DateTimeRfc1123 ifModifiedSinceConverted = null;
        if (ifModifiedSince != null) {
            ifModifiedSinceConverted = new DateTimeRfc1123(ifModifiedSince);
        }
        DateTimeRfc1123 ifUnmodifiedSinceConverted = null;
        if (ifUnmodifiedSince != null) {
            ifUnmodifiedSinceConverted = new DateTimeRfc1123(ifUnmodifiedSince);
        }
        return service.appendBlock(this.client.url(), body, timeout, leaseId, maxSize, appendPosition, ifModifiedSinceConverted, ifUnmodifiedSinceConverted, ifMatches, ifNoneMatch, this.client.version(), requestId, comp);
    }

    /**
     * The Append Block operation commits a new block of data to the end of an existing append blob. The Append Block operation is permitted only if the blob was created with x-ms-blob-type set to AppendBlob. Append Block is supported only on version 2015-02-21 version or later.
     *
     * @param body Initial data
     * @param timeout The timeout parameter is expressed in seconds. For more information, see &lt;a href="https://docs.microsoft.com/en-us/rest/api/storageservices/fileservices/setting-timeouts-for-blob-service-operations"&gt;Setting Timeouts for Blob Service Operations.&lt;/a&gt;
     * @param leaseId If specified, the operation only succeeds if the container's lease is active and matches this ID.
     * @param maxSize Optional conditional header. The max length in bytes permitted for the append blob. If the Append Block operation would cause the blob to exceed that limit or if the blob size is already greater than the value specified in this header, the request will fail with MaxBlobSizeConditionNotMet error (HTTP status code 412 - Precondition Failed).
     * @param appendPosition Optional conditional header, used only for the Append Block operation. A number indicating the byte offset to compare. Append Block will succeed only if the append position is equal to this number. If it is not, the request will fail with the AppendPositionConditionNotMet error (HTTP status code 412 - Precondition Failed).
     * @param ifModifiedSince Specify this header value to operate only on a blob if it has been modified since the specified date/time.
     * @param ifUnmodifiedSince Specify this header value to operate only on a blob if it has not been modified since the specified date/time.
     * @param ifMatches Specify an ETag value to operate only on blobs with a matching value.
     * @param ifNoneMatch Specify an ETag value to operate only on blobs without a matching value.
     * @param requestId Provides a client-generated, opaque value with a 1 KB character limit that is recorded in the analytics logs when storage analytics logging is enabled.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return a {@link Single} emitting the RestResponse<AppendBlobAppendBlockHeaders, Void> object
     */
    public Completable appendBlockAsync(AsyncInputStream body, Integer timeout, String leaseId, Integer maxSize, Integer appendPosition, DateTime ifModifiedSince, DateTime ifUnmodifiedSince, String ifMatches, String ifNoneMatch, String requestId) {
        return appendBlockWithRestResponseAsync(body, timeout, leaseId, maxSize, appendPosition, ifModifiedSince, ifUnmodifiedSince, ifMatches, ifNoneMatch, requestId)
            .toCompletable();
    }
}
