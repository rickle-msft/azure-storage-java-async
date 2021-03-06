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

import java.io.UnsupportedEncodingException;
import java.net.Inet4Address;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Map;

/**
 * Represents the components that make up an Azure Storage SAS' query parameters.
 * NOTE: Instances of this class are immutable.
 */
public final class SASQueryParameters {

    private final String version;

    private final String services;

    private final String resourceTypes;

    private final SASProtocol protocol;

    private final Date startTime;

    private final Date expiryTime;

    private final IPRange ipRange;

    private final String identifier;

    private final String resource;

    private final String permissions;

    private final String signature;

    /**
     * A {@code String} representing the storage version.
     */
    public String getVersion() {
        return version;
    }

    /**
     * A {@code String} representing the storage services being accessed (only for Account SAS). Please refer to
     * {@link AccountSASService} for more details.
     */
    public String getServices() {
        return services;
    }

    /**
     * A {@code String} representing the storage resource types being accessed (only for Account SAS). Please refer to
     * {@link AccountSASResourceType} for more details.
     */
    public String getResourceTypes() {
        return resourceTypes;
    }

    /**
     * A {@code String} representing the allowed HTTP protocol(s) or {@code null}. Please refer to {@link SASProtocol}
     * for more details.
     */
    public SASProtocol getProtocol() {
        return protocol;
    }

    /**
     * A {@code java.util.Date} representing the start time for this SAS token or {@code null}.
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * A {@code java.util.Date} representing the expiry time for this SAS token.
     */
    public Date getExpiryTime() {
        return expiryTime;
    }

    /**
     * A {@link IPRange} representing the range of valid IP addresses for this SAS token or {@code null}.
     */
    public IPRange getIpRange() {
        return ipRange;
    }

    /**
     * A {@code String} representing the signed identifier (only for Service SAS) or {@code null}.
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * A {@code String} representing the storage container or blob (only for Service SAS).
     */
    public String getResource() {
        return resource;
    }

    /**
     * A {@code String} representing the storage permissions or {@code null}. Please refer to
     * {@link AccountSASPermission}, {@link BlobSASPermission}, or {@link ContainerSASPermission} for more details.
     */
    public String getPermissions() {
        return permissions;
    }

    /**
     * A {@code String} representing the signature for the SAS token.
     */
    public String getSignature() {
        return signature;
    }

    /**
     * Creates a new {@link SASQueryParameters} object.
     *
     * @param queryParamsMap
     *      A {@code java.util.Map} representing all query parameters for the request as key-value pairs
     * @param removeSASParametersFromMap
     *      When {@code true}, the SAS query parameters will be removed from queryParamsMap
     */
    SASQueryParameters(Map<String, String[]> queryParamsMap, boolean removeSASParametersFromMap)
            throws UnknownHostException {

        String[] queryValue = queryParamsMap.get("sv");
        if (queryValue != null) {
            this.version = queryValue[0];
            if (removeSASParametersFromMap) {
                queryParamsMap.remove("sv");
            }
        }
        else {
            this.version = null;
        }

        queryValue = queryParamsMap.get("ss");
        if (queryValue != null) {
            this.services = queryValue[0];
            if (removeSASParametersFromMap) {
                queryParamsMap.remove("ss");
            }
        }
        else {
            this.services = null;
        }

        queryValue = queryParamsMap.get("srt");
        if (queryValue != null) {
            this.resourceTypes = queryValue[0];
            if (removeSASParametersFromMap) {
                queryParamsMap.remove("srt");
            }
        }
        else {
            this.resourceTypes = null;
        }

        queryValue = queryParamsMap.get("spr");
        if (queryValue != null) {
            this.protocol = SASProtocol.parse(queryValue[0]);
            if (removeSASParametersFromMap) {
                queryParamsMap.remove("spr");
            }
        }
        else {
            this.protocol = null;
        }

        queryValue = queryParamsMap.get("st");
        if (queryValue != null) {
            this.startTime = Utility.parseDate(queryValue[0]);
            if (removeSASParametersFromMap) {
                queryParamsMap.remove("st");
            }
        }
        else {
            this.startTime = null;
        }

        queryValue = queryParamsMap.get("se");
        if (queryValue != null) {
            this.expiryTime = Utility.parseDate(queryValue[0]);
            if (removeSASParametersFromMap) {
                queryParamsMap.remove("se");
            }
        }
        else {
            this.expiryTime = null;
        }

        queryValue = queryParamsMap.get("sip");
        if (queryValue != null) {
            this.ipRange = new IPRange();
            this.ipRange.ipMin = (Inet4Address)(Inet4Address.getByName(queryValue[0]));
            if (removeSASParametersFromMap) {
                queryParamsMap.remove("sip");
            }
        }
        else {
            this.ipRange = null;
        }

        queryValue = queryParamsMap.get("si");
        if (queryValue != null) {
            this.identifier = queryValue[0];
            if (removeSASParametersFromMap) {
                queryParamsMap.remove("si");
            }
        }
        else {
            this.identifier = null;
        }

        queryValue = queryParamsMap.get("sr");
        if (queryValue != null) {
            this.resource = queryValue[0];
            if (removeSASParametersFromMap) {
                queryParamsMap.remove("sr");
            }
        }
        else {
            this.resource = null;
        }

        queryValue = queryParamsMap.get("sp");
        if (queryValue != null) {
            this.permissions = queryValue[0];
            if (removeSASParametersFromMap) {
                queryParamsMap.remove("sp");
            }
        }
        else {
            this.permissions = null;
        }

        queryValue = queryParamsMap.get("sig");
        if (queryValue != null) {
            this.signature = queryValue[0];
            if (removeSASParametersFromMap) {
                queryParamsMap.remove("sig");
            }
        }
        else {
            this.signature = null;
        }
    }

    /**
     * Creates a new {@link SASQueryParameters} object. These objects are only created internally by
     * *SASSignatureValues classes.
     *
     * @param version
     *      A {@code String} representing the storage version.
     * @param services
     *      A {@code String} representing the storage services being accessed (only for Account SAS).
     * @param resourceTypes
     *      A {@code String} representing the storage resource types being accessed (only for Account SAS).
     * @param protocol
     *      A {@code String} representing the allowed HTTP protocol(s) or {@code null}.
     * @param startTime
     *      A {@code java.util.Date} representing the start time for this SAS token or {@code null}.
     * @param expiryTime
     *      A {@code java.util.Date} representing the expiry time for this SAS token.
     * @param ipRange
     *      A {@link IPRange} representing the range of valid IP addresses for this SAS token or {@code null}.
     * @param identifier
     *      A {@code String} representing the signed identifier (only for Service SAS) or {@code null}.
     * @param resource
     *      A {@code String} representing the storage container or blob (only for Service SAS).
     * @param permissions
     *      A {@code String} representing the storage permissions or {@code null}.
     * @param signature
     *      A {@code String} representing the signature for the SAS token.
     */
     SASQueryParameters(String version, String services, String resourceTypes, SASProtocol protocol,
                              Date startTime, Date expiryTime, IPRange ipRange, String identifier,
                              String resource, String permissions, String signature) {

        this.version = version;
        this.services = services;
        this.resourceTypes = resourceTypes;
        this.protocol = protocol;
        this.startTime = startTime;
        this.expiryTime = expiryTime;
        this.ipRange = ipRange;
        this.identifier = identifier;
        this.resource = resource;
        this.permissions = permissions;
        this.signature = signature;
    }

    private void tryAppendQueryParameter(StringBuilder sb, String param, Object value) {
         if (value != null) {
             if (sb.length() == 0) {
                 sb.append('?');
             }
             else {
                 sb.append('&');
             }
             try {
                 sb.append(URLEncoder.encode(param, Constants.UTF8_CHARSET))
                         .append('=').append(URLEncoder.encode(value.toString(), Constants.UTF8_CHARSET));
             } catch (UnsupportedEncodingException e) {
                 throw new Error(e); // If we can't encode with UTF-8, we fail.
             }
         }
    }
    /**
     * Encodes all SAS query parameters into a string that can be appended to a URL.
     *
     * @return
     *      A {@code String} representing all SAS query parameters.
     */
    public String encode() {
        /*
         We should be url-encoding each key and each value, but because we know all the keys and values will encode to
         themselves, we cheat except for the signature value.
         */
        String[] params = {"sv", "ss", "srt", "spr", "st", "se", "sip", "si", "sr", "sp", "sig"};
        StringBuilder sb = new StringBuilder();
        for (String param : params) {
            switch (param) {
                case "sv":
                    tryAppendQueryParameter(sb, param, this.version);
                    break;
                case "ss":
                    tryAppendQueryParameter(sb, param, this.services);
                    break;
                case "srt":
                    tryAppendQueryParameter(sb, param, this.resourceTypes);
                    break;
                case "spr":
                    tryAppendQueryParameter(sb, param, this.protocol);
                    break;
                case "st":
                    tryAppendQueryParameter(sb, param,
                            this.startTime == null ? null : Utility.ISO8601UTCDateFormat.format(this.startTime));
                    break;
                case "se":
                    tryAppendQueryParameter(sb, param,
                            this.expiryTime == null ? null : Utility.ISO8601UTCDateFormat.format(this.expiryTime));
                    break;
                case "sip":
                    tryAppendQueryParameter(sb, param, this.ipRange);
                    break;
                case "si":
                    tryAppendQueryParameter(sb, param, this.identifier);
                    break;
                case "sr":
                    tryAppendQueryParameter(sb, param, this.resource);
                    break;
                case "sp":
                    tryAppendQueryParameter(sb, param, this.permissions);
                    break;
                case "sig":
                    tryAppendQueryParameter(sb, param, this.signature);
                    break;
            }
        }
        return sb.toString();
    }
}
