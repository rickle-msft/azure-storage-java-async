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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.microsoft.azure.storage.models.ListBlobsIncludeItem;
import java.util.List;

@JacksonXmlRootElement(localName = "ListBlobsIncludeItem")
public class ListBlobsIncludeItemWrapper {

    @JacksonXmlProperty(localName = "ListBlobsIncludeItem")
    private final List<ListBlobsIncludeItem> listBlobsIncludeItem;

    @JsonCreator
    public ListBlobsIncludeItemWrapper(@JsonProperty("listBlobsIncludeItem") List<ListBlobsIncludeItem> listBlobsIncludeItem) {
        this.listBlobsIncludeItem = listBlobsIncludeItem;
    }

    /**
     * Get the ListBlobsIncludeItem value.
     *
     * @return the ListBlobsIncludeItem value
     */
    public List<ListBlobsIncludeItem> listBlobsIncludeItem() {
        return listBlobsIncludeItem;
    }
}
