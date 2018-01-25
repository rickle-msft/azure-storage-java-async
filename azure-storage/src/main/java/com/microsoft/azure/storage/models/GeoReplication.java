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
 * The GeoReplication model.
 */
@JacksonXmlRootElement(localName = "GeoReplication")
public class GeoReplication {
    /**
     * The status of the secondary location. Possible values include: 'live',
     * 'bootstrap', 'unavailable'.
     */
    @JsonProperty(value = "Status", required = true)
    private GeoReplicationStatusType status;

    /**
     * A GMT date/time value, to the second. All primary writes preceding this
     * value are guaranteed to be available for read operations at the
     * secondary. Primary writes after this point in time may or may not be
     * available for reads.
     */
    @JsonProperty(value = "LastSyncTime", required = true)
    private DateTimeRfc1123 lastSyncTime;

    /**
     * Get the status value.
     *
     * @return the status value.
     */
    public GeoReplicationStatusType status() {
        return this.status;
    }

    /**
     * Set the status value.
     *
     * @param status the status value to set.
     * @return the GeoReplication object itself.
     */
    public GeoReplication withStatus(GeoReplicationStatusType status) {
        this.status = status;
        return this;
    }

    /**
     * Get the lastSyncTime value.
     *
     * @return the lastSyncTime value.
     */
    public DateTime lastSyncTime() {
        if (this.lastSyncTime == null) {
            return null;
        }
        return this.lastSyncTime.dateTime();
    }

    /**
     * Set the lastSyncTime value.
     *
     * @param lastSyncTime the lastSyncTime value to set.
     * @return the GeoReplication object itself.
     */
    public GeoReplication withLastSyncTime(DateTime lastSyncTime) {
        if (lastSyncTime == null) {
            this.lastSyncTime = null;
        } else {
            this.lastSyncTime = new DateTimeRfc1123(lastSyncTime);
        }
        return this;
    }
}
