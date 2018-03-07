/*
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

import com.microsoft.rest.v2.http.UrlBuilder;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * A BlobURLParts object represents the components that make up an Azure Storage Container/Blob URL. You parse an
 * existing URL into its parts by calling NewBlobURLParts(). You construct a URL from parts by calling toURL().
 * NOTE: Changing any SAS-related field requires computing a new SAS signature.
 */
public final class BlobURLParts {

    /**
     * A {@code String} representing the scheme. Ex: "https://".
     */
    public String scheme;

    /**
     * A {@code String} representing the host. Ex: "account.blob.core.windows.net".
     */
    public String host;

    /**
     * A {@code String} representing the container name or {@code null}.
     */
    public String containerName;

    /**
     * A {@code String} representing the blob name or {@code null}.
     */
    public String blobName;

    /**
     * A {@code java.util.Date} representing the snapshot time or {@code null}.
     */
    public String snapshot;

    /**
     * A {@link SASQueryParameters} representing the SAS query parameters or {@code null}.
     */
    public SASQueryParameters sasQueryParameters;

    /**
     * A {@code Map&lt;String, String[]&gt;} representing query parameter vey value pairs aside from SAS parameters
     * and snapshot time or {@code null}.
     */
    public Map<String, String[]> unparsedParameters;


    /**
     * Initializes a BlobURLParts object with all fields set to null, except unparsedParameters, which is an empty map.
     */
    public BlobURLParts() {
        unparsedParameters = new HashMap<>();
    }

    /**
     * Converts the blob URL parts to a {@link URL}.
     *
     * @return
     *      A {@code java.net.URL} to the blob resource composed of all the elements in the object.
     */
    public URL toURL() throws MalformedURLException {
        UrlBuilder url = new UrlBuilder().withScheme(this.scheme).withHost(this.host);

        StringBuilder path = new StringBuilder();
        if (this.containerName != null) {
            path.append(this.containerName);
            if (this.blobName != null) {
                path.append('/');
                path.append(this.blobName);
            }
        }
        url.withPath(path.toString());

        if (this.snapshot != null) {
            url.setQueryParameter(Constants.SNAPSHOT_QUERY_PARAMETER, this.snapshot);
        }
        String encodedSAS = this.sasQueryParameters.encode();
        if (encodedSAS.length() != 0) {
            url.withQuery(encodedSAS);
        }
        for (Map.Entry<String, String[]> entry : this.unparsedParameters.entrySet()) {
            // TODO: Test this is the proper encoding
            // The commas are intentionally encoded.
            try {
                url.setQueryParameter(entry.getKey(), URLEncoder.encode(
                        Utility.join(entry.getValue(), ','), Constants.UTF8_CHARSET));
            }
            catch (UnsupportedEncodingException e) {
                throw new Error(e); // If UTF-8 encoding is not supported, we give up.
            }
        }

        return url.toURL();
    }
}
