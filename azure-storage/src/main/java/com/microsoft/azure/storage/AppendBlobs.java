/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 * Changes may cause incorrect behavior and will be lost if the code is
 * regenerated.
 */

package com.microsoft.azure.storage;

import com.microsoft.azure.storage.models.AppendBlobAppendBlockResponse;
import com.microsoft.azure.storage.models.AppendBlobCreateResponse;
import com.microsoft.rest.v2.ServiceCallback;
import com.microsoft.rest.v2.ServiceFuture;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import java.nio.ByteBuffer;
import java.time.OffsetDateTime;
import java.util.Map;

/**
 * An instance of this class provides access to all the operations defined in
 * AppendBlobs.
 */
public interface AppendBlobs {
    /**
     * The Create Append Blob operation creates a new append blob.
     *
     * @param contentLength The length of the request.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    void create(@NonNull long contentLength);

    /**
     * The Create Append Blob operation creates a new append blob.
     *
     * @param contentLength The length of the request.
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return a ServiceFuture which will be completed with the result of the network request.
     */
    ServiceFuture<Void> createAsync(@NonNull long contentLength, ServiceCallback<Void> serviceCallback);

    /**
     * The Create Append Blob operation creates a new append blob.
     *
     * @param contentLength The length of the request.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return a Single which performs the network request upon subscription.
     */
    Single<AppendBlobCreateResponse> createWithRestResponseAsync(@NonNull long contentLength);

    /**
     * The Create Append Blob operation creates a new append blob.
     *
     * @param contentLength The length of the request.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return a Single which performs the network request upon subscription.
     */
    Completable createAsync(@NonNull long contentLength);

    /**
     * The Create Append Blob operation creates a new append blob.
     *
     * @param contentLength The length of the request.
     * @param timeout The timeout parameter is expressed in seconds. For more information, see &lt;a href="https://docs.microsoft.com/en-us/rest/api/storageservices/fileservices/setting-timeouts-for-blob-service-operations"&gt;Setting Timeouts for Blob Service Operations.&lt;/a&gt;.
     * @param blobContentType Optional. Sets the blob's content type. If specified, this property is stored with the blob and returned with a read request.
     * @param blobContentEncoding Optional. Sets the blob's content encoding. If specified, this property is stored with the blob and returned with a read request.
     * @param blobContentLanguage Optional. Set the blob's content language. If specified, this property is stored with the blob and returned with a read request.
     * @param blobContentMD5 Optional. An MD5 hash of the blob content. Note that this hash is not validated, as the hashes for the individual blocks were validated when each was uploaded.
     * @param blobCacheControl Optional. Sets the blob's cache control. If specified, this property is stored with the blob and returned with a read request.
     * @param metadata Optional. Specifies a user-defined name-value pair associated with the blob. If no name-value pairs are specified, the operation will copy the metadata from the source blob or file to the destination blob. If one or more name-value pairs are specified, the destination blob is created with the specified metadata, and metadata is not copied from the source blob or file. Note that beginning with version 2009-09-19, metadata names must adhere to the naming rules for C# identifiers. See Naming and Referencing Containers, Blobs, and Metadata for more information.
     * @param leaseId If specified, the operation only succeeds if the container's lease is active and matches this ID.
     * @param blobContentDisposition Optional. Sets the blob's Content-Disposition header.
     * @param ifModifiedSince Specify this header value to operate only on a blob if it has been modified since the specified date/time.
     * @param ifUnmodifiedSince Specify this header value to operate only on a blob if it has not been modified since the specified date/time.
     * @param ifMatches Specify an ETag value to operate only on blobs with a matching value.
     * @param ifNoneMatch Specify an ETag value to operate only on blobs without a matching value.
     * @param requestId Provides a client-generated, opaque value with a 1 KB character limit that is recorded in the analytics logs when storage analytics logging is enabled.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    void create(@NonNull long contentLength, Integer timeout, String blobContentType, String blobContentEncoding, String blobContentLanguage, String blobContentMD5, String blobCacheControl, Map<String, String> metadata, String leaseId, String blobContentDisposition, OffsetDateTime ifModifiedSince, OffsetDateTime ifUnmodifiedSince, String ifMatches, String ifNoneMatch, String requestId);

    /**
     * The Create Append Blob operation creates a new append blob.
     *
     * @param contentLength The length of the request.
     * @param timeout The timeout parameter is expressed in seconds. For more information, see &lt;a href="https://docs.microsoft.com/en-us/rest/api/storageservices/fileservices/setting-timeouts-for-blob-service-operations"&gt;Setting Timeouts for Blob Service Operations.&lt;/a&gt;.
     * @param blobContentType Optional. Sets the blob's content type. If specified, this property is stored with the blob and returned with a read request.
     * @param blobContentEncoding Optional. Sets the blob's content encoding. If specified, this property is stored with the blob and returned with a read request.
     * @param blobContentLanguage Optional. Set the blob's content language. If specified, this property is stored with the blob and returned with a read request.
     * @param blobContentMD5 Optional. An MD5 hash of the blob content. Note that this hash is not validated, as the hashes for the individual blocks were validated when each was uploaded.
     * @param blobCacheControl Optional. Sets the blob's cache control. If specified, this property is stored with the blob and returned with a read request.
     * @param metadata Optional. Specifies a user-defined name-value pair associated with the blob. If no name-value pairs are specified, the operation will copy the metadata from the source blob or file to the destination blob. If one or more name-value pairs are specified, the destination blob is created with the specified metadata, and metadata is not copied from the source blob or file. Note that beginning with version 2009-09-19, metadata names must adhere to the naming rules for C# identifiers. See Naming and Referencing Containers, Blobs, and Metadata for more information.
     * @param leaseId If specified, the operation only succeeds if the container's lease is active and matches this ID.
     * @param blobContentDisposition Optional. Sets the blob's Content-Disposition header.
     * @param ifModifiedSince Specify this header value to operate only on a blob if it has been modified since the specified date/time.
     * @param ifUnmodifiedSince Specify this header value to operate only on a blob if it has not been modified since the specified date/time.
     * @param ifMatches Specify an ETag value to operate only on blobs with a matching value.
     * @param ifNoneMatch Specify an ETag value to operate only on blobs without a matching value.
     * @param requestId Provides a client-generated, opaque value with a 1 KB character limit that is recorded in the analytics logs when storage analytics logging is enabled.
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return a ServiceFuture which will be completed with the result of the network request.
     */
    ServiceFuture<Void> createAsync(@NonNull long contentLength, Integer timeout, String blobContentType, String blobContentEncoding, String blobContentLanguage, String blobContentMD5, String blobCacheControl, Map<String, String> metadata, String leaseId, String blobContentDisposition, OffsetDateTime ifModifiedSince, OffsetDateTime ifUnmodifiedSince, String ifMatches, String ifNoneMatch, String requestId, ServiceCallback<Void> serviceCallback);

    /**
     * The Create Append Blob operation creates a new append blob.
     *
     * @param contentLength The length of the request.
     * @param timeout The timeout parameter is expressed in seconds. For more information, see &lt;a href="https://docs.microsoft.com/en-us/rest/api/storageservices/fileservices/setting-timeouts-for-blob-service-operations"&gt;Setting Timeouts for Blob Service Operations.&lt;/a&gt;.
     * @param blobContentType Optional. Sets the blob's content type. If specified, this property is stored with the blob and returned with a read request.
     * @param blobContentEncoding Optional. Sets the blob's content encoding. If specified, this property is stored with the blob and returned with a read request.
     * @param blobContentLanguage Optional. Set the blob's content language. If specified, this property is stored with the blob and returned with a read request.
     * @param blobContentMD5 Optional. An MD5 hash of the blob content. Note that this hash is not validated, as the hashes for the individual blocks were validated when each was uploaded.
     * @param blobCacheControl Optional. Sets the blob's cache control. If specified, this property is stored with the blob and returned with a read request.
     * @param metadata Optional. Specifies a user-defined name-value pair associated with the blob. If no name-value pairs are specified, the operation will copy the metadata from the source blob or file to the destination blob. If one or more name-value pairs are specified, the destination blob is created with the specified metadata, and metadata is not copied from the source blob or file. Note that beginning with version 2009-09-19, metadata names must adhere to the naming rules for C# identifiers. See Naming and Referencing Containers, Blobs, and Metadata for more information.
     * @param leaseId If specified, the operation only succeeds if the container's lease is active and matches this ID.
     * @param blobContentDisposition Optional. Sets the blob's Content-Disposition header.
     * @param ifModifiedSince Specify this header value to operate only on a blob if it has been modified since the specified date/time.
     * @param ifUnmodifiedSince Specify this header value to operate only on a blob if it has not been modified since the specified date/time.
     * @param ifMatches Specify an ETag value to operate only on blobs with a matching value.
     * @param ifNoneMatch Specify an ETag value to operate only on blobs without a matching value.
     * @param requestId Provides a client-generated, opaque value with a 1 KB character limit that is recorded in the analytics logs when storage analytics logging is enabled.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return a Single which performs the network request upon subscription.
     */
    Single<AppendBlobCreateResponse> createWithRestResponseAsync(@NonNull long contentLength, Integer timeout, String blobContentType, String blobContentEncoding, String blobContentLanguage, String blobContentMD5, String blobCacheControl, Map<String, String> metadata, String leaseId, String blobContentDisposition, OffsetDateTime ifModifiedSince, OffsetDateTime ifUnmodifiedSince, String ifMatches, String ifNoneMatch, String requestId);

    /**
     * The Create Append Blob operation creates a new append blob.
     *
     * @param contentLength The length of the request.
     * @param timeout The timeout parameter is expressed in seconds. For more information, see &lt;a href="https://docs.microsoft.com/en-us/rest/api/storageservices/fileservices/setting-timeouts-for-blob-service-operations"&gt;Setting Timeouts for Blob Service Operations.&lt;/a&gt;.
     * @param blobContentType Optional. Sets the blob's content type. If specified, this property is stored with the blob and returned with a read request.
     * @param blobContentEncoding Optional. Sets the blob's content encoding. If specified, this property is stored with the blob and returned with a read request.
     * @param blobContentLanguage Optional. Set the blob's content language. If specified, this property is stored with the blob and returned with a read request.
     * @param blobContentMD5 Optional. An MD5 hash of the blob content. Note that this hash is not validated, as the hashes for the individual blocks were validated when each was uploaded.
     * @param blobCacheControl Optional. Sets the blob's cache control. If specified, this property is stored with the blob and returned with a read request.
     * @param metadata Optional. Specifies a user-defined name-value pair associated with the blob. If no name-value pairs are specified, the operation will copy the metadata from the source blob or file to the destination blob. If one or more name-value pairs are specified, the destination blob is created with the specified metadata, and metadata is not copied from the source blob or file. Note that beginning with version 2009-09-19, metadata names must adhere to the naming rules for C# identifiers. See Naming and Referencing Containers, Blobs, and Metadata for more information.
     * @param leaseId If specified, the operation only succeeds if the container's lease is active and matches this ID.
     * @param blobContentDisposition Optional. Sets the blob's Content-Disposition header.
     * @param ifModifiedSince Specify this header value to operate only on a blob if it has been modified since the specified date/time.
     * @param ifUnmodifiedSince Specify this header value to operate only on a blob if it has not been modified since the specified date/time.
     * @param ifMatches Specify an ETag value to operate only on blobs with a matching value.
     * @param ifNoneMatch Specify an ETag value to operate only on blobs without a matching value.
     * @param requestId Provides a client-generated, opaque value with a 1 KB character limit that is recorded in the analytics logs when storage analytics logging is enabled.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return a Single which performs the network request upon subscription.
     */
    Completable createAsync(@NonNull long contentLength, Integer timeout, String blobContentType, String blobContentEncoding, String blobContentLanguage, String blobContentMD5, String blobCacheControl, Map<String, String> metadata, String leaseId, String blobContentDisposition, OffsetDateTime ifModifiedSince, OffsetDateTime ifUnmodifiedSince, String ifMatches, String ifNoneMatch, String requestId);

    /**
     * The Append Block operation commits a new block of data to the end of an existing append blob. The Append Block operation is permitted only if the blob was created with x-ms-blob-type set to AppendBlob. Append Block is supported only on version 2015-02-21 version or later.
     *
     * @param body Initial data.
     * @param contentLength The length of the request.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    void appendBlock(@NonNull Flowable<ByteBuffer> body, @NonNull long contentLength);

    /**
     * The Append Block operation commits a new block of data to the end of an existing append blob. The Append Block operation is permitted only if the blob was created with x-ms-blob-type set to AppendBlob. Append Block is supported only on version 2015-02-21 version or later.
     *
     * @param body Initial data.
     * @param contentLength The length of the request.
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return a ServiceFuture which will be completed with the result of the network request.
     */
    ServiceFuture<Void> appendBlockAsync(@NonNull Flowable<ByteBuffer> body, @NonNull long contentLength, ServiceCallback<Void> serviceCallback);

    /**
     * The Append Block operation commits a new block of data to the end of an existing append blob. The Append Block operation is permitted only if the blob was created with x-ms-blob-type set to AppendBlob. Append Block is supported only on version 2015-02-21 version or later.
     *
     * @param body Initial data.
     * @param contentLength The length of the request.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return a Single which performs the network request upon subscription.
     */
    Single<AppendBlobAppendBlockResponse> appendBlockWithRestResponseAsync(@NonNull Flowable<ByteBuffer> body, @NonNull long contentLength);

    /**
     * The Append Block operation commits a new block of data to the end of an existing append blob. The Append Block operation is permitted only if the blob was created with x-ms-blob-type set to AppendBlob. Append Block is supported only on version 2015-02-21 version or later.
     *
     * @param body Initial data.
     * @param contentLength The length of the request.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return a Single which performs the network request upon subscription.
     */
    Completable appendBlockAsync(@NonNull Flowable<ByteBuffer> body, @NonNull long contentLength);

    /**
     * The Append Block operation commits a new block of data to the end of an existing append blob. The Append Block operation is permitted only if the blob was created with x-ms-blob-type set to AppendBlob. Append Block is supported only on version 2015-02-21 version or later.
     *
     * @param body Initial data.
     * @param contentLength The length of the request.
     * @param timeout The timeout parameter is expressed in seconds. For more information, see &lt;a href="https://docs.microsoft.com/en-us/rest/api/storageservices/fileservices/setting-timeouts-for-blob-service-operations"&gt;Setting Timeouts for Blob Service Operations.&lt;/a&gt;.
     * @param leaseId If specified, the operation only succeeds if the container's lease is active and matches this ID.
     * @param maxSize Optional conditional header. The max length in bytes permitted for the append blob. If the Append Block operation would cause the blob to exceed that limit or if the blob size is already greater than the value specified in this header, the request will fail with MaxBlobSizeConditionNotMet error (HTTP status code 412 - Precondition Failed).
     * @param appendPosition Optional conditional header, used only for the Append Block operation. A number indicating the byte offset to compare. Append Block will succeed only if the append position is equal to this number. If it is not, the request will fail with the AppendPositionConditionNotMet error (HTTP status code 412 - Precondition Failed).
     * @param ifModifiedSince Specify this header value to operate only on a blob if it has been modified since the specified date/time.
     * @param ifUnmodifiedSince Specify this header value to operate only on a blob if it has not been modified since the specified date/time.
     * @param ifMatches Specify an ETag value to operate only on blobs with a matching value.
     * @param ifNoneMatch Specify an ETag value to operate only on blobs without a matching value.
     * @param requestId Provides a client-generated, opaque value with a 1 KB character limit that is recorded in the analytics logs when storage analytics logging is enabled.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    void appendBlock(@NonNull Flowable<ByteBuffer> body, @NonNull long contentLength, Integer timeout, String leaseId, Integer maxSize, Integer appendPosition, OffsetDateTime ifModifiedSince, OffsetDateTime ifUnmodifiedSince, String ifMatches, String ifNoneMatch, String requestId);

    /**
     * The Append Block operation commits a new block of data to the end of an existing append blob. The Append Block operation is permitted only if the blob was created with x-ms-blob-type set to AppendBlob. Append Block is supported only on version 2015-02-21 version or later.
     *
     * @param body Initial data.
     * @param contentLength The length of the request.
     * @param timeout The timeout parameter is expressed in seconds. For more information, see &lt;a href="https://docs.microsoft.com/en-us/rest/api/storageservices/fileservices/setting-timeouts-for-blob-service-operations"&gt;Setting Timeouts for Blob Service Operations.&lt;/a&gt;.
     * @param leaseId If specified, the operation only succeeds if the container's lease is active and matches this ID.
     * @param maxSize Optional conditional header. The max length in bytes permitted for the append blob. If the Append Block operation would cause the blob to exceed that limit or if the blob size is already greater than the value specified in this header, the request will fail with MaxBlobSizeConditionNotMet error (HTTP status code 412 - Precondition Failed).
     * @param appendPosition Optional conditional header, used only for the Append Block operation. A number indicating the byte offset to compare. Append Block will succeed only if the append position is equal to this number. If it is not, the request will fail with the AppendPositionConditionNotMet error (HTTP status code 412 - Precondition Failed).
     * @param ifModifiedSince Specify this header value to operate only on a blob if it has been modified since the specified date/time.
     * @param ifUnmodifiedSince Specify this header value to operate only on a blob if it has not been modified since the specified date/time.
     * @param ifMatches Specify an ETag value to operate only on blobs with a matching value.
     * @param ifNoneMatch Specify an ETag value to operate only on blobs without a matching value.
     * @param requestId Provides a client-generated, opaque value with a 1 KB character limit that is recorded in the analytics logs when storage analytics logging is enabled.
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return a ServiceFuture which will be completed with the result of the network request.
     */
    ServiceFuture<Void> appendBlockAsync(@NonNull Flowable<ByteBuffer> body, @NonNull long contentLength, Integer timeout, String leaseId, Integer maxSize, Integer appendPosition, OffsetDateTime ifModifiedSince, OffsetDateTime ifUnmodifiedSince, String ifMatches, String ifNoneMatch, String requestId, ServiceCallback<Void> serviceCallback);

    /**
     * The Append Block operation commits a new block of data to the end of an existing append blob. The Append Block operation is permitted only if the blob was created with x-ms-blob-type set to AppendBlob. Append Block is supported only on version 2015-02-21 version or later.
     *
     * @param body Initial data.
     * @param contentLength The length of the request.
     * @param timeout The timeout parameter is expressed in seconds. For more information, see &lt;a href="https://docs.microsoft.com/en-us/rest/api/storageservices/fileservices/setting-timeouts-for-blob-service-operations"&gt;Setting Timeouts for Blob Service Operations.&lt;/a&gt;.
     * @param leaseId If specified, the operation only succeeds if the container's lease is active and matches this ID.
     * @param maxSize Optional conditional header. The max length in bytes permitted for the append blob. If the Append Block operation would cause the blob to exceed that limit or if the blob size is already greater than the value specified in this header, the request will fail with MaxBlobSizeConditionNotMet error (HTTP status code 412 - Precondition Failed).
     * @param appendPosition Optional conditional header, used only for the Append Block operation. A number indicating the byte offset to compare. Append Block will succeed only if the append position is equal to this number. If it is not, the request will fail with the AppendPositionConditionNotMet error (HTTP status code 412 - Precondition Failed).
     * @param ifModifiedSince Specify this header value to operate only on a blob if it has been modified since the specified date/time.
     * @param ifUnmodifiedSince Specify this header value to operate only on a blob if it has not been modified since the specified date/time.
     * @param ifMatches Specify an ETag value to operate only on blobs with a matching value.
     * @param ifNoneMatch Specify an ETag value to operate only on blobs without a matching value.
     * @param requestId Provides a client-generated, opaque value with a 1 KB character limit that is recorded in the analytics logs when storage analytics logging is enabled.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return a Single which performs the network request upon subscription.
     */
    Single<AppendBlobAppendBlockResponse> appendBlockWithRestResponseAsync(@NonNull Flowable<ByteBuffer> body, @NonNull long contentLength, Integer timeout, String leaseId, Integer maxSize, Integer appendPosition, OffsetDateTime ifModifiedSince, OffsetDateTime ifUnmodifiedSince, String ifMatches, String ifNoneMatch, String requestId);

    /**
     * The Append Block operation commits a new block of data to the end of an existing append blob. The Append Block operation is permitted only if the blob was created with x-ms-blob-type set to AppendBlob. Append Block is supported only on version 2015-02-21 version or later.
     *
     * @param body Initial data.
     * @param contentLength The length of the request.
     * @param timeout The timeout parameter is expressed in seconds. For more information, see &lt;a href="https://docs.microsoft.com/en-us/rest/api/storageservices/fileservices/setting-timeouts-for-blob-service-operations"&gt;Setting Timeouts for Blob Service Operations.&lt;/a&gt;.
     * @param leaseId If specified, the operation only succeeds if the container's lease is active and matches this ID.
     * @param maxSize Optional conditional header. The max length in bytes permitted for the append blob. If the Append Block operation would cause the blob to exceed that limit or if the blob size is already greater than the value specified in this header, the request will fail with MaxBlobSizeConditionNotMet error (HTTP status code 412 - Precondition Failed).
     * @param appendPosition Optional conditional header, used only for the Append Block operation. A number indicating the byte offset to compare. Append Block will succeed only if the append position is equal to this number. If it is not, the request will fail with the AppendPositionConditionNotMet error (HTTP status code 412 - Precondition Failed).
     * @param ifModifiedSince Specify this header value to operate only on a blob if it has been modified since the specified date/time.
     * @param ifUnmodifiedSince Specify this header value to operate only on a blob if it has not been modified since the specified date/time.
     * @param ifMatches Specify an ETag value to operate only on blobs with a matching value.
     * @param ifNoneMatch Specify an ETag value to operate only on blobs without a matching value.
     * @param requestId Provides a client-generated, opaque value with a 1 KB character limit that is recorded in the analytics logs when storage analytics logging is enabled.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return a Single which performs the network request upon subscription.
     */
    Completable appendBlockAsync(@NonNull Flowable<ByteBuffer> body, @NonNull long contentLength, Integer timeout, String leaseId, Integer maxSize, Integer appendPosition, OffsetDateTime ifModifiedSince, OffsetDateTime ifUnmodifiedSince, String ifMatches, String ifNoneMatch, String requestId);
}
