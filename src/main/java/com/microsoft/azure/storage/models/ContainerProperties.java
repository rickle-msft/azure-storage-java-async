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
 * Properties of a container.
 */
@JacksonXmlRootElement(localName = "ContainerProperties")
public class ContainerProperties {
    /**
     * The lastModified property.
     */
    @JsonProperty(value = "Last-Modified", required = true)
    private DateTimeRfc1123 lastModified;

    /**
     * The etag property.
     */
    @JsonProperty(value = "Etag", required = true)
    private String etag;

    /**
     * Possible values include: 'locked', 'unlocked'.
     */
    @JsonProperty(value = "LeaseStatus")
    private LeaseStatusType leaseStatus;

    /**
     * Possible values include: 'available', 'leased', 'expired', 'breaking',
     * 'broken'.
     */
    @JsonProperty(value = "LeaseState")
    private LeaseStateType leaseState;

    /**
     * Possible values include: 'infinite', 'fixed'.
     */
    @JsonProperty(value = "LeaseDuration")
    private LeaseDurationType leaseDuration;

    /**
     * Possible values include: 'container', 'blob'.
     */
    @JsonProperty(value = "PublicAccess")
    private PublicAccessType publicAccess;

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
     * @return the ContainerProperties object itself.
     */
    public ContainerProperties withLastModified(DateTime lastModified) {
        if (lastModified == null) {
            this.lastModified = null;
        } else {
            this.lastModified = new DateTimeRfc1123(lastModified);
        }
        return this;
    }

    /**
     * Get the etag value.
     *
     * @return the etag value
     */
    public String etag() {
        return this.etag;
    }

    /**
     * Set the etag value.
     *
     * @param etag the etag value to set
     * @return the ContainerProperties object itself.
     */
    public ContainerProperties withEtag(String etag) {
        this.etag = etag;
        return this;
    }

    /**
     * Get the leaseStatus value.
     *
     * @return the leaseStatus value
     */
    public LeaseStatusType leaseStatus() {
        return this.leaseStatus;
    }

    /**
     * Set the leaseStatus value.
     *
     * @param leaseStatus the leaseStatus value to set
     * @return the ContainerProperties object itself.
     */
    public ContainerProperties withLeaseStatus(LeaseStatusType leaseStatus) {
        this.leaseStatus = leaseStatus;
        return this;
    }

    /**
     * Get the leaseState value.
     *
     * @return the leaseState value
     */
    public LeaseStateType leaseState() {
        return this.leaseState;
    }

    /**
     * Set the leaseState value.
     *
     * @param leaseState the leaseState value to set
     * @return the ContainerProperties object itself.
     */
    public ContainerProperties withLeaseState(LeaseStateType leaseState) {
        this.leaseState = leaseState;
        return this;
    }

    /**
     * Get the leaseDuration value.
     *
     * @return the leaseDuration value
     */
    public LeaseDurationType leaseDuration() {
        return this.leaseDuration;
    }

    /**
     * Set the leaseDuration value.
     *
     * @param leaseDuration the leaseDuration value to set
     * @return the ContainerProperties object itself.
     */
    public ContainerProperties withLeaseDuration(LeaseDurationType leaseDuration) {
        this.leaseDuration = leaseDuration;
        return this;
    }

    /**
     * Get the publicAccess value.
     *
     * @return the publicAccess value
     */
    public PublicAccessType publicAccess() {
        return this.publicAccess;
    }

    /**
     * Set the publicAccess value.
     *
     * @param publicAccess the publicAccess value to set
     * @return the ContainerProperties object itself.
     */
    public ContainerProperties withPublicAccess(PublicAccessType publicAccess) {
        this.publicAccess = publicAccess;
        return this;
    }

}
