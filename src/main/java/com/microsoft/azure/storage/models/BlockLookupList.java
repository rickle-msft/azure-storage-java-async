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
import java.util.List;

/**
 * The BlockLookupList model.
 */
@JacksonXmlRootElement(localName = "BlockList")
public class BlockLookupList {
    /**
     * The committed property.
     */
    @JsonProperty(value = "Committed")
    private List<String> committed;

    /**
     * The uncommitted property.
     */
    @JsonProperty(value = "Uncommitted")
    private List<String> uncommitted;

    /**
     * The latest property.
     */
    @JsonProperty(value = "Latest")
    private List<String> latest;

    /**
     * Get the committed value.
     *
     * @return the committed value
     */
    public List<String> committed() {
        return this.committed;
    }

    /**
     * Set the committed value.
     *
     * @param committed the committed value to set
     * @return the BlockLookupList object itself.
     */
    public BlockLookupList withCommitted(List<String> committed) {
        this.committed = committed;
        return this;
    }

    /**
     * Get the uncommitted value.
     *
     * @return the uncommitted value
     */
    public List<String> uncommitted() {
        return this.uncommitted;
    }

    /**
     * Set the uncommitted value.
     *
     * @param uncommitted the uncommitted value to set
     * @return the BlockLookupList object itself.
     */
    public BlockLookupList withUncommitted(List<String> uncommitted) {
        this.uncommitted = uncommitted;
        return this;
    }

    /**
     * Get the latest value.
     *
     * @return the latest value
     */
    public List<String> latest() {
        return this.latest;
    }

    /**
     * Set the latest value.
     *
     * @param latest the latest value to set
     * @return the BlockLookupList object itself.
     */
    public BlockLookupList withLatest(List<String> latest) {
        this.latest = latest;
        return this;
    }

}
