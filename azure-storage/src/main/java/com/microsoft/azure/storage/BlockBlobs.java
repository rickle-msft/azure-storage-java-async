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

import com.microsoft.azure.storage.models.BlockBlobGetBlockListHeaders;
import com.microsoft.azure.storage.models.BlockBlobPutBlockHeaders;
import com.microsoft.azure.storage.models.BlockBlobPutBlockListHeaders;
import com.microsoft.azure.storage.models.BlockList;
import com.microsoft.azure.storage.models.BlockListType;
import com.microsoft.azure.storage.models.BlockLookupList;
import com.microsoft.rest.v2.RestResponse;
import com.microsoft.rest.v2.ServiceCallback;
import com.microsoft.rest.v2.ServiceFuture;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import java.nio.ByteBuffer;
import java.util.Map;
import org.joda.time.DateTime;

/**
 * An instance of this class provides access to all the operations defined in
 * BlockBlobs.
 */
public interface BlockBlobs {
    /**
     * The Put Block operation creates a new block to be committed as part of a blob.
     *
     * @param blockId A valid Base64 string value that identifies the block. Prior to encoding, the string must be less than or equal to 64 bytes in size. For a given blob, the length of the value specified for the blockid parameter must be the same size for each block.
     * @param contentLength The length of the request.
     * @param body Initial data.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    void putBlock(@NonNull String blockId, @NonNull long contentLength, @NonNull Flowable<ByteBuffer> body);

    /**
     * The Put Block operation creates a new block to be committed as part of a blob.
     *
     * @param blockId A valid Base64 string value that identifies the block. Prior to encoding, the string must be less than or equal to 64 bytes in size. For a given blob, the length of the value specified for the blockid parameter must be the same size for each block.
     * @param contentLength The length of the request.
     * @param body Initial data.
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return the {@link ServiceFuture&lt;Void&gt;} object.
     */
    ServiceFuture<Void> putBlockAsync(@NonNull String blockId, @NonNull long contentLength, @NonNull Flowable<ByteBuffer> body, @NonNull ServiceCallback<Void> serviceCallback);

    /**
     * The Put Block operation creates a new block to be committed as part of a blob.
     *
     * @param blockId A valid Base64 string value that identifies the block. Prior to encoding, the string must be less than or equal to 64 bytes in size. For a given blob, the length of the value specified for the blockid parameter must be the same size for each block.
     * @param contentLength The length of the request.
     * @param body Initial data.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return the {@link Single&lt;RestResponse&lt;BlockBlobPutBlockHeaders, Void&gt;&gt;} object if successful.
     */
    Single<RestResponse<BlockBlobPutBlockHeaders, Void>> putBlockWithRestResponseAsync(@NonNull String blockId, @NonNull long contentLength, @NonNull Flowable<ByteBuffer> body);

    /**
     * The Put Block operation creates a new block to be committed as part of a blob.
     *
     * @param blockId A valid Base64 string value that identifies the block. Prior to encoding, the string must be less than or equal to 64 bytes in size. For a given blob, the length of the value specified for the blockid parameter must be the same size for each block.
     * @param contentLength The length of the request.
     * @param body Initial data.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return the {@link Completable} object if successful.
     */
    Completable putBlockAsync(@NonNull String blockId, @NonNull long contentLength, @NonNull Flowable<ByteBuffer> body);

    /**
     * The Put Block operation creates a new block to be committed as part of a blob.
     *
     * @param blockId A valid Base64 string value that identifies the block. Prior to encoding, the string must be less than or equal to 64 bytes in size. For a given blob, the length of the value specified for the blockid parameter must be the same size for each block.
     * @param contentLength The length of the request.
     * @param body Initial data.
     * @param timeout The timeout parameter is expressed in seconds. For more information, see &lt;a href="https://docs.microsoft.com/en-us/rest/api/storageservices/fileservices/setting-timeouts-for-blob-service-operations"&gt;Setting Timeouts for Blob Service Operations.&lt;/a&gt;.
     * @param leaseId If specified, the operation only succeeds if the container's lease is active and matches this ID.
     * @param requestId Provides a client-generated, opaque value with a 1 KB character limit that is recorded in the analytics logs when storage analytics logging is enabled.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    void putBlock(@NonNull String blockId, @NonNull long contentLength, @NonNull Flowable<ByteBuffer> body, Integer timeout, String leaseId, String requestId);

    /**
     * The Put Block operation creates a new block to be committed as part of a blob.
     *
     * @param blockId A valid Base64 string value that identifies the block. Prior to encoding, the string must be less than or equal to 64 bytes in size. For a given blob, the length of the value specified for the blockid parameter must be the same size for each block.
     * @param contentLength The length of the request.
     * @param body Initial data.
     * @param timeout The timeout parameter is expressed in seconds. For more information, see &lt;a href="https://docs.microsoft.com/en-us/rest/api/storageservices/fileservices/setting-timeouts-for-blob-service-operations"&gt;Setting Timeouts for Blob Service Operations.&lt;/a&gt;.
     * @param leaseId If specified, the operation only succeeds if the container's lease is active and matches this ID.
     * @param requestId Provides a client-generated, opaque value with a 1 KB character limit that is recorded in the analytics logs when storage analytics logging is enabled.
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return the {@link ServiceFuture&lt;Void&gt;} object.
     */
    ServiceFuture<Void> putBlockAsync(@NonNull String blockId, @NonNull long contentLength, @NonNull Flowable<ByteBuffer> body, Integer timeout, String leaseId, String requestId, @NonNull ServiceCallback<Void> serviceCallback);

    /**
     * The Put Block operation creates a new block to be committed as part of a blob.
     *
     * @param blockId A valid Base64 string value that identifies the block. Prior to encoding, the string must be less than or equal to 64 bytes in size. For a given blob, the length of the value specified for the blockid parameter must be the same size for each block.
     * @param contentLength The length of the request.
     * @param body Initial data.
     * @param timeout The timeout parameter is expressed in seconds. For more information, see &lt;a href="https://docs.microsoft.com/en-us/rest/api/storageservices/fileservices/setting-timeouts-for-blob-service-operations"&gt;Setting Timeouts for Blob Service Operations.&lt;/a&gt;.
     * @param leaseId If specified, the operation only succeeds if the container's lease is active and matches this ID.
     * @param requestId Provides a client-generated, opaque value with a 1 KB character limit that is recorded in the analytics logs when storage analytics logging is enabled.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return the {@link Single&lt;RestResponse&lt;BlockBlobPutBlockHeaders, Void&gt;&gt;} object if successful.
     */
    Single<RestResponse<BlockBlobPutBlockHeaders, Void>> putBlockWithRestResponseAsync(@NonNull String blockId, @NonNull long contentLength, @NonNull Flowable<ByteBuffer> body, Integer timeout, String leaseId, String requestId);

    /**
     * The Put Block operation creates a new block to be committed as part of a blob.
     *
     * @param blockId A valid Base64 string value that identifies the block. Prior to encoding, the string must be less than or equal to 64 bytes in size. For a given blob, the length of the value specified for the blockid parameter must be the same size for each block.
     * @param contentLength The length of the request.
     * @param body Initial data.
     * @param timeout The timeout parameter is expressed in seconds. For more information, see &lt;a href="https://docs.microsoft.com/en-us/rest/api/storageservices/fileservices/setting-timeouts-for-blob-service-operations"&gt;Setting Timeouts for Blob Service Operations.&lt;/a&gt;.
     * @param leaseId If specified, the operation only succeeds if the container's lease is active and matches this ID.
     * @param requestId Provides a client-generated, opaque value with a 1 KB character limit that is recorded in the analytics logs when storage analytics logging is enabled.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return the {@link Completable} object if successful.
     */
    Completable putBlockAsync(@NonNull String blockId, @NonNull long contentLength, @NonNull Flowable<ByteBuffer> body, Integer timeout, String leaseId, String requestId);

    /**
     * The Put Block List operation writes a blob by specifying the list of block IDs that make up the blob. In order to be written as part of a blob, a block must have been successfully written to the server in a prior Put Block operation. You can call Put Block List to update a blob by uploading only those blocks that have changed, then committing the new and existing blocks together. You can do this by specifying whether to commit a block from the committed block list or from the uncommitted block list, or to commit the most recently uploaded version of the block, whichever list it may belong to.
     *
     * @param blocks the BlockLookupList value.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    void putBlockList(@NonNull BlockLookupList blocks);

    /**
     * The Put Block List operation writes a blob by specifying the list of block IDs that make up the blob. In order to be written as part of a blob, a block must have been successfully written to the server in a prior Put Block operation. You can call Put Block List to update a blob by uploading only those blocks that have changed, then committing the new and existing blocks together. You can do this by specifying whether to commit a block from the committed block list or from the uncommitted block list, or to commit the most recently uploaded version of the block, whichever list it may belong to.
     *
     * @param blocks the BlockLookupList value.
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return the {@link ServiceFuture&lt;Void&gt;} object.
     */
    ServiceFuture<Void> putBlockListAsync(@NonNull BlockLookupList blocks, @NonNull ServiceCallback<Void> serviceCallback);

    /**
     * The Put Block List operation writes a blob by specifying the list of block IDs that make up the blob. In order to be written as part of a blob, a block must have been successfully written to the server in a prior Put Block operation. You can call Put Block List to update a blob by uploading only those blocks that have changed, then committing the new and existing blocks together. You can do this by specifying whether to commit a block from the committed block list or from the uncommitted block list, or to commit the most recently uploaded version of the block, whichever list it may belong to.
     *
     * @param blocks the BlockLookupList value.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return the {@link Single&lt;RestResponse&lt;BlockBlobPutBlockListHeaders, Void&gt;&gt;} object if successful.
     */
    Single<RestResponse<BlockBlobPutBlockListHeaders, Void>> putBlockListWithRestResponseAsync(@NonNull BlockLookupList blocks);

    /**
     * The Put Block List operation writes a blob by specifying the list of block IDs that make up the blob. In order to be written as part of a blob, a block must have been successfully written to the server in a prior Put Block operation. You can call Put Block List to update a blob by uploading only those blocks that have changed, then committing the new and existing blocks together. You can do this by specifying whether to commit a block from the committed block list or from the uncommitted block list, or to commit the most recently uploaded version of the block, whichever list it may belong to.
     *
     * @param blocks the BlockLookupList value.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return the {@link Completable} object if successful.
     */
    Completable putBlockListAsync(@NonNull BlockLookupList blocks);

    /**
     * The Put Block List operation writes a blob by specifying the list of block IDs that make up the blob. In order to be written as part of a blob, a block must have been successfully written to the server in a prior Put Block operation. You can call Put Block List to update a blob by uploading only those blocks that have changed, then committing the new and existing blocks together. You can do this by specifying whether to commit a block from the committed block list or from the uncommitted block list, or to commit the most recently uploaded version of the block, whichever list it may belong to.
     *
     * @param blocks the BlockLookupList value.
     * @param timeout The timeout parameter is expressed in seconds. For more information, see &lt;a href="https://docs.microsoft.com/en-us/rest/api/storageservices/fileservices/setting-timeouts-for-blob-service-operations"&gt;Setting Timeouts for Blob Service Operations.&lt;/a&gt;.
     * @param blobCacheControl Optional. Sets the blob's cache control. If specified, this property is stored with the blob and returned with a read request.
     * @param blobContentType Optional. Sets the blob's content type. If specified, this property is stored with the blob and returned with a read request.
     * @param blobContentEncoding Optional. Sets the blob's content encoding. If specified, this property is stored with the blob and returned with a read request.
     * @param blobContentLanguage Optional. Set the blob's content language. If specified, this property is stored with the blob and returned with a read request.
     * @param blobContentMD5 Optional. An MD5 hash of the blob content. Note that this hash is not validated, as the hashes for the individual blocks were validated when each was uploaded.
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
    void putBlockList(@NonNull BlockLookupList blocks, Integer timeout, String blobCacheControl, String blobContentType, String blobContentEncoding, String blobContentLanguage, String blobContentMD5, Map<String, String> metadata, String leaseId, String blobContentDisposition, DateTime ifModifiedSince, DateTime ifUnmodifiedSince, String ifMatches, String ifNoneMatch, String requestId);

    /**
     * The Put Block List operation writes a blob by specifying the list of block IDs that make up the blob. In order to be written as part of a blob, a block must have been successfully written to the server in a prior Put Block operation. You can call Put Block List to update a blob by uploading only those blocks that have changed, then committing the new and existing blocks together. You can do this by specifying whether to commit a block from the committed block list or from the uncommitted block list, or to commit the most recently uploaded version of the block, whichever list it may belong to.
     *
     * @param blocks the BlockLookupList value.
     * @param timeout The timeout parameter is expressed in seconds. For more information, see &lt;a href="https://docs.microsoft.com/en-us/rest/api/storageservices/fileservices/setting-timeouts-for-blob-service-operations"&gt;Setting Timeouts for Blob Service Operations.&lt;/a&gt;.
     * @param blobCacheControl Optional. Sets the blob's cache control. If specified, this property is stored with the blob and returned with a read request.
     * @param blobContentType Optional. Sets the blob's content type. If specified, this property is stored with the blob and returned with a read request.
     * @param blobContentEncoding Optional. Sets the blob's content encoding. If specified, this property is stored with the blob and returned with a read request.
     * @param blobContentLanguage Optional. Set the blob's content language. If specified, this property is stored with the blob and returned with a read request.
     * @param blobContentMD5 Optional. An MD5 hash of the blob content. Note that this hash is not validated, as the hashes for the individual blocks were validated when each was uploaded.
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
     * @return the {@link ServiceFuture&lt;Void&gt;} object.
     */
    ServiceFuture<Void> putBlockListAsync(@NonNull BlockLookupList blocks, Integer timeout, String blobCacheControl, String blobContentType, String blobContentEncoding, String blobContentLanguage, String blobContentMD5, Map<String, String> metadata, String leaseId, String blobContentDisposition, DateTime ifModifiedSince, DateTime ifUnmodifiedSince, String ifMatches, String ifNoneMatch, String requestId, @NonNull ServiceCallback<Void> serviceCallback);

    /**
     * The Put Block List operation writes a blob by specifying the list of block IDs that make up the blob. In order to be written as part of a blob, a block must have been successfully written to the server in a prior Put Block operation. You can call Put Block List to update a blob by uploading only those blocks that have changed, then committing the new and existing blocks together. You can do this by specifying whether to commit a block from the committed block list or from the uncommitted block list, or to commit the most recently uploaded version of the block, whichever list it may belong to.
     *
     * @param blocks the BlockLookupList value.
     * @param timeout The timeout parameter is expressed in seconds. For more information, see &lt;a href="https://docs.microsoft.com/en-us/rest/api/storageservices/fileservices/setting-timeouts-for-blob-service-operations"&gt;Setting Timeouts for Blob Service Operations.&lt;/a&gt;.
     * @param blobCacheControl Optional. Sets the blob's cache control. If specified, this property is stored with the blob and returned with a read request.
     * @param blobContentType Optional. Sets the blob's content type. If specified, this property is stored with the blob and returned with a read request.
     * @param blobContentEncoding Optional. Sets the blob's content encoding. If specified, this property is stored with the blob and returned with a read request.
     * @param blobContentLanguage Optional. Set the blob's content language. If specified, this property is stored with the blob and returned with a read request.
     * @param blobContentMD5 Optional. An MD5 hash of the blob content. Note that this hash is not validated, as the hashes for the individual blocks were validated when each was uploaded.
     * @param metadata Optional. Specifies a user-defined name-value pair associated with the blob. If no name-value pairs are specified, the operation will copy the metadata from the source blob or file to the destination blob. If one or more name-value pairs are specified, the destination blob is created with the specified metadata, and metadata is not copied from the source blob or file. Note that beginning with version 2009-09-19, metadata names must adhere to the naming rules for C# identifiers. See Naming and Referencing Containers, Blobs, and Metadata for more information.
     * @param leaseId If specified, the operation only succeeds if the container's lease is active and matches this ID.
     * @param blobContentDisposition Optional. Sets the blob's Content-Disposition header.
     * @param ifModifiedSince Specify this header value to operate only on a blob if it has been modified since the specified date/time.
     * @param ifUnmodifiedSince Specify this header value to operate only on a blob if it has not been modified since the specified date/time.
     * @param ifMatches Specify an ETag value to operate only on blobs with a matching value.
     * @param ifNoneMatch Specify an ETag value to operate only on blobs without a matching value.
     * @param requestId Provides a client-generated, opaque value with a 1 KB character limit that is recorded in the analytics logs when storage analytics logging is enabled.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return the {@link Single&lt;RestResponse&lt;BlockBlobPutBlockListHeaders, Void&gt;&gt;} object if successful.
     */
    Single<RestResponse<BlockBlobPutBlockListHeaders, Void>> putBlockListWithRestResponseAsync(@NonNull BlockLookupList blocks, Integer timeout, String blobCacheControl, String blobContentType, String blobContentEncoding, String blobContentLanguage, String blobContentMD5, Map<String, String> metadata, String leaseId, String blobContentDisposition, DateTime ifModifiedSince, DateTime ifUnmodifiedSince, String ifMatches, String ifNoneMatch, String requestId);

    /**
     * The Put Block List operation writes a blob by specifying the list of block IDs that make up the blob. In order to be written as part of a blob, a block must have been successfully written to the server in a prior Put Block operation. You can call Put Block List to update a blob by uploading only those blocks that have changed, then committing the new and existing blocks together. You can do this by specifying whether to commit a block from the committed block list or from the uncommitted block list, or to commit the most recently uploaded version of the block, whichever list it may belong to.
     *
     * @param blocks the BlockLookupList value.
     * @param timeout The timeout parameter is expressed in seconds. For more information, see &lt;a href="https://docs.microsoft.com/en-us/rest/api/storageservices/fileservices/setting-timeouts-for-blob-service-operations"&gt;Setting Timeouts for Blob Service Operations.&lt;/a&gt;.
     * @param blobCacheControl Optional. Sets the blob's cache control. If specified, this property is stored with the blob and returned with a read request.
     * @param blobContentType Optional. Sets the blob's content type. If specified, this property is stored with the blob and returned with a read request.
     * @param blobContentEncoding Optional. Sets the blob's content encoding. If specified, this property is stored with the blob and returned with a read request.
     * @param blobContentLanguage Optional. Set the blob's content language. If specified, this property is stored with the blob and returned with a read request.
     * @param blobContentMD5 Optional. An MD5 hash of the blob content. Note that this hash is not validated, as the hashes for the individual blocks were validated when each was uploaded.
     * @param metadata Optional. Specifies a user-defined name-value pair associated with the blob. If no name-value pairs are specified, the operation will copy the metadata from the source blob or file to the destination blob. If one or more name-value pairs are specified, the destination blob is created with the specified metadata, and metadata is not copied from the source blob or file. Note that beginning with version 2009-09-19, metadata names must adhere to the naming rules for C# identifiers. See Naming and Referencing Containers, Blobs, and Metadata for more information.
     * @param leaseId If specified, the operation only succeeds if the container's lease is active and matches this ID.
     * @param blobContentDisposition Optional. Sets the blob's Content-Disposition header.
     * @param ifModifiedSince Specify this header value to operate only on a blob if it has been modified since the specified date/time.
     * @param ifUnmodifiedSince Specify this header value to operate only on a blob if it has not been modified since the specified date/time.
     * @param ifMatches Specify an ETag value to operate only on blobs with a matching value.
     * @param ifNoneMatch Specify an ETag value to operate only on blobs without a matching value.
     * @param requestId Provides a client-generated, opaque value with a 1 KB character limit that is recorded in the analytics logs when storage analytics logging is enabled.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return the {@link Completable} object if successful.
     */
    Completable putBlockListAsync(@NonNull BlockLookupList blocks, Integer timeout, String blobCacheControl, String blobContentType, String blobContentEncoding, String blobContentLanguage, String blobContentMD5, Map<String, String> metadata, String leaseId, String blobContentDisposition, DateTime ifModifiedSince, DateTime ifUnmodifiedSince, String ifMatches, String ifNoneMatch, String requestId);

    /**
     * The Get Block List operation retrieves the list of blocks that have been uploaded as part of a block blob.
     *
     * @param listType Specifies whether to return the list of committed blocks, the list of uncommitted blocks, or both lists together. Possible values include: 'committed', 'uncommitted', 'all'.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the BlockList object if successful.
     */
    BlockList getBlockList(@NonNull BlockListType listType);

    /**
     * The Get Block List operation retrieves the list of blocks that have been uploaded as part of a block blob.
     *
     * @param listType Specifies whether to return the list of committed blocks, the list of uncommitted blocks, or both lists together. Possible values include: 'committed', 'uncommitted', 'all'.
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return the {@link ServiceFuture&lt;BlockList&gt;} object.
     */
    ServiceFuture<BlockList> getBlockListAsync(@NonNull BlockListType listType, @NonNull ServiceCallback<BlockList> serviceCallback);

    /**
     * The Get Block List operation retrieves the list of blocks that have been uploaded as part of a block blob.
     *
     * @param listType Specifies whether to return the list of committed blocks, the list of uncommitted blocks, or both lists together. Possible values include: 'committed', 'uncommitted', 'all'.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return the {@link Single&lt;RestResponse&lt;BlockBlobGetBlockListHeaders, BlockList&gt;&gt;} object if successful.
     */
    Single<RestResponse<BlockBlobGetBlockListHeaders, BlockList>> getBlockListWithRestResponseAsync(@NonNull BlockListType listType);

    /**
     * The Get Block List operation retrieves the list of blocks that have been uploaded as part of a block blob.
     *
     * @param listType Specifies whether to return the list of committed blocks, the list of uncommitted blocks, or both lists together. Possible values include: 'committed', 'uncommitted', 'all'.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return the {@link Maybe&lt;BlockList&gt;} object if successful.
     */
    Maybe<BlockList> getBlockListAsync(@NonNull BlockListType listType);

    /**
     * The Get Block List operation retrieves the list of blocks that have been uploaded as part of a block blob.
     *
     * @param listType Specifies whether to return the list of committed blocks, the list of uncommitted blocks, or both lists together. Possible values include: 'committed', 'uncommitted', 'all'.
     * @param snapshot The snapshot parameter is an opaque DateTime value that, when present, specifies the blob snapshot to retrieve. For more information on working with blob snapshots, see &lt;a href="https://docs.microsoft.com/en-us/rest/api/storageservices/fileservices/creating-a-snapshot-of-a-blob"&gt;Creating a Snapshot of a Blob.&lt;/a&gt;.
     * @param timeout The timeout parameter is expressed in seconds. For more information, see &lt;a href="https://docs.microsoft.com/en-us/rest/api/storageservices/fileservices/setting-timeouts-for-blob-service-operations"&gt;Setting Timeouts for Blob Service Operations.&lt;/a&gt;.
     * @param leaseId If specified, the operation only succeeds if the container's lease is active and matches this ID.
     * @param requestId Provides a client-generated, opaque value with a 1 KB character limit that is recorded in the analytics logs when storage analytics logging is enabled.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the BlockList object if successful.
     */
    BlockList getBlockList(@NonNull BlockListType listType, String snapshot, Integer timeout, String leaseId, String requestId);

    /**
     * The Get Block List operation retrieves the list of blocks that have been uploaded as part of a block blob.
     *
     * @param listType Specifies whether to return the list of committed blocks, the list of uncommitted blocks, or both lists together. Possible values include: 'committed', 'uncommitted', 'all'.
     * @param snapshot The snapshot parameter is an opaque DateTime value that, when present, specifies the blob snapshot to retrieve. For more information on working with blob snapshots, see &lt;a href="https://docs.microsoft.com/en-us/rest/api/storageservices/fileservices/creating-a-snapshot-of-a-blob"&gt;Creating a Snapshot of a Blob.&lt;/a&gt;.
     * @param timeout The timeout parameter is expressed in seconds. For more information, see &lt;a href="https://docs.microsoft.com/en-us/rest/api/storageservices/fileservices/setting-timeouts-for-blob-service-operations"&gt;Setting Timeouts for Blob Service Operations.&lt;/a&gt;.
     * @param leaseId If specified, the operation only succeeds if the container's lease is active and matches this ID.
     * @param requestId Provides a client-generated, opaque value with a 1 KB character limit that is recorded in the analytics logs when storage analytics logging is enabled.
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return the {@link ServiceFuture&lt;BlockList&gt;} object.
     */
    ServiceFuture<BlockList> getBlockListAsync(@NonNull BlockListType listType, String snapshot, Integer timeout, String leaseId, String requestId, @NonNull ServiceCallback<BlockList> serviceCallback);

    /**
     * The Get Block List operation retrieves the list of blocks that have been uploaded as part of a block blob.
     *
     * @param listType Specifies whether to return the list of committed blocks, the list of uncommitted blocks, or both lists together. Possible values include: 'committed', 'uncommitted', 'all'.
     * @param snapshot The snapshot parameter is an opaque DateTime value that, when present, specifies the blob snapshot to retrieve. For more information on working with blob snapshots, see &lt;a href="https://docs.microsoft.com/en-us/rest/api/storageservices/fileservices/creating-a-snapshot-of-a-blob"&gt;Creating a Snapshot of a Blob.&lt;/a&gt;.
     * @param timeout The timeout parameter is expressed in seconds. For more information, see &lt;a href="https://docs.microsoft.com/en-us/rest/api/storageservices/fileservices/setting-timeouts-for-blob-service-operations"&gt;Setting Timeouts for Blob Service Operations.&lt;/a&gt;.
     * @param leaseId If specified, the operation only succeeds if the container's lease is active and matches this ID.
     * @param requestId Provides a client-generated, opaque value with a 1 KB character limit that is recorded in the analytics logs when storage analytics logging is enabled.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return the {@link Single&lt;RestResponse&lt;BlockBlobGetBlockListHeaders, BlockList&gt;&gt;} object if successful.
     */
    Single<RestResponse<BlockBlobGetBlockListHeaders, BlockList>> getBlockListWithRestResponseAsync(@NonNull BlockListType listType, String snapshot, Integer timeout, String leaseId, String requestId);

    /**
     * The Get Block List operation retrieves the list of blocks that have been uploaded as part of a block blob.
     *
     * @param listType Specifies whether to return the list of committed blocks, the list of uncommitted blocks, or both lists together. Possible values include: 'committed', 'uncommitted', 'all'.
     * @param snapshot The snapshot parameter is an opaque DateTime value that, when present, specifies the blob snapshot to retrieve. For more information on working with blob snapshots, see &lt;a href="https://docs.microsoft.com/en-us/rest/api/storageservices/fileservices/creating-a-snapshot-of-a-blob"&gt;Creating a Snapshot of a Blob.&lt;/a&gt;.
     * @param timeout The timeout parameter is expressed in seconds. For more information, see &lt;a href="https://docs.microsoft.com/en-us/rest/api/storageservices/fileservices/setting-timeouts-for-blob-service-operations"&gt;Setting Timeouts for Blob Service Operations.&lt;/a&gt;.
     * @param leaseId If specified, the operation only succeeds if the container's lease is active and matches this ID.
     * @param requestId Provides a client-generated, opaque value with a 1 KB character limit that is recorded in the analytics logs when storage analytics logging is enabled.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return the {@link Maybe&lt;BlockList&gt;} object if successful.
     */
    Maybe<BlockList> getBlockListAsync(@NonNull BlockListType listType, String snapshot, Integer timeout, String leaseId, String requestId);
}
