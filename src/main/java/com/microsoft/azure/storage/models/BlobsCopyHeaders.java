/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 * Changes may cause incorrect behavior and will be lost if the code is
 * regenerated.
 */

package com.microsoft.azure.storage.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.microsoft.rest.v2.DateTimeRfc1123;
import org.joda.time.DateTime;

/**
 * Defines headers for Copy operation.
 */
@JacksonXmlRootElement(localName = "Blobs-Copy-Headers")
public class BlobsCopyHeaders {
    /**
     * The ETag contains a value that you can use to perform operations
     * conditionally. If the request version is 2011-08-18 or newer, the ETag
     * value will be in quotes.
     */
    @JsonProperty(value = "ETag")
    private String eTag;

    /**
     * Returns the date and time the container was last modified. Any operation
     * that modifies the blob, including an update of the blob's metadata or
     * properties, changes the last-modified time of the blob.
     */
    @JsonProperty(value = "Last-Modified")
    private DateTimeRfc1123 lastModified;

    /**
     * This header uniquely identifies the request that was made and can be
     * used for troubleshooting the request.
     */
    @JsonProperty(value = "x-ms-request-id")
    private String requestId;

    /**
     * Indicates the version of the Blob service used to execute the request.
     * This header is returned for requests made against version 2009-09-19 and
     * above.
     */
    @JsonProperty(value = "x-ms-version")
    private String version;

    /**
     * UTC date/time value generated by the service that indicates the time at
     * which the response was initiated.
     */
    @JsonProperty(value = "Date")
    private DateTimeRfc1123 dateProperty;

    /**
     * String identifier for this copy operation. Use with Get Blob Properties
     * to check the status of this copy operation, or pass to Abort Copy Blob
     * to abort a pending copy.
     */
    @JsonProperty(value = "x-ms-copy-id")
    private String copyId;

    /**
     * State of the copy operation identified by x-ms-copy-id. Possible values
     * include: 'pending', 'success', 'aborted', 'failed'.
     */
    @JsonProperty(value = "x-ms-copy-status")
    private CopyStatusType copyStatus;

    /**
     * Get the eTag value.
     *
     * @return the eTag value
     */
    public String eTag() {
        return this.eTag;
    }

    /**
     * Set the eTag value.
     *
     * @param eTag the eTag value to set
     * @return the BlobsCopyHeaders object itself.
     */
    public BlobsCopyHeaders withETag(String eTag) {
        this.eTag = eTag;
        return this;
    }

    /**
     * Get the lastModified value.
     *
     * @return the lastModified value
     */
    public DateTime lastModified() {
        if (this.lastModified == null) {
            return null;
        }
        return this.lastModified.dateTime();
    }

    /**
     * Set the lastModified value.
     *
     * @param lastModified the lastModified value to set
     * @return the BlobsCopyHeaders object itself.
     */
    public BlobsCopyHeaders withLastModified(DateTime lastModified) {
        if (lastModified == null) {
            this.lastModified = null;
        } else {
            this.lastModified = new DateTimeRfc1123(lastModified);
        }
        return this;
    }

    /**
     * Get the requestId value.
     *
     * @return the requestId value
     */
    public String requestId() {
        return this.requestId;
    }

    /**
     * Set the requestId value.
     *
     * @param requestId the requestId value to set
     * @return the BlobsCopyHeaders object itself.
     */
    public BlobsCopyHeaders withRequestId(String requestId) {
        this.requestId = requestId;
        return this;
    }

    /**
     * Get the version value.
     *
     * @return the version value
     */
    public String version() {
        return this.version;
    }

    /**
     * Set the version value.
     *
     * @param version the version value to set
     * @return the BlobsCopyHeaders object itself.
     */
    public BlobsCopyHeaders withVersion(String version) {
        this.version = version;
        return this;
    }

    /**
     * Get the dateProperty value.
     *
     * @return the dateProperty value
     */
    public DateTime dateProperty() {
        if (this.dateProperty == null) {
            return null;
        }
        return this.dateProperty.dateTime();
    }

    /**
     * Set the dateProperty value.
     *
     * @param dateProperty the dateProperty value to set
     * @return the BlobsCopyHeaders object itself.
     */
    public BlobsCopyHeaders withDateProperty(DateTime dateProperty) {
        if (dateProperty == null) {
            this.dateProperty = null;
        } else {
            this.dateProperty = new DateTimeRfc1123(dateProperty);
        }
        return this;
    }

    /**
     * Get the copyId value.
     *
     * @return the copyId value
     */
    public String copyId() {
        return this.copyId;
    }

    /**
     * Set the copyId value.
     *
     * @param copyId the copyId value to set
     * @return the BlobsCopyHeaders object itself.
     */
    public BlobsCopyHeaders withCopyId(String copyId) {
        this.copyId = copyId;
        return this;
    }

    /**
     * Get the copyStatus value.
     *
     * @return the copyStatus value
     */
    public CopyStatusType copyStatus() {
        return this.copyStatus;
    }

    /**
     * Set the copyStatus value.
     *
     * @param copyStatus the copyStatus value to set
     * @return the BlobsCopyHeaders object itself.
     */
    public BlobsCopyHeaders withCopyStatus(CopyStatusType copyStatus) {
        this.copyStatus = copyStatus;
        return this;
    }

}
