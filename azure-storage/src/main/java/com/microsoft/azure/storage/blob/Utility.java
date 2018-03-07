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
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

final class Utility {

    static final class RFC1123GMTDateFormat extends SimpleDateFormat {
        RFC1123GMTDateFormat() {
            super("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
            this.setTimeZone(TimeZone.getTimeZone("GMT"));
        }
    }

    static final class ISO8601UTCDateFormat extends SimpleDateFormat {
        ISO8601UTCDateFormat() {
            super("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
            this.setTimeZone(TimeZone.getTimeZone("UTC"));
        }
    }

    static final DateFormat RFC1123GMTDateFormat = new RFC1123GMTDateFormat();

    static final DateFormat ISO8601UTCDateFormat = new ISO8601UTCDateFormat();

    /**
     * Asserts that a value is not <code>null</code>.
     *
     * @param param
     *            A {@code String} that represents the name of the parameter, which becomes the exception message
     *            text if the <code>value</code> parameter is <code>null</code>.
     * @param value
     *            An <code>Object</code> object that represents the value of the specified parameter. This is the value
     *            being asserted as not <code>null</code>.
     */
    static void assertNotNull(final String param, final Object value) {
        if (value == null) {
            throw new IllegalArgumentException(String.format(Locale.US, SR.ARGUMENT_NULL_OR_EMPTY, param));
        }
    }

    /**
     * Returns a value that indicates whether the specified string is <code>null</code> or empty.
     *
     * @param value
     *            A {@code String} being examined for <code>null</code> or empty.
     *
     * @return <code>true</code> if the specified value is <code>null</code> or empty; otherwise, <code>false</code>
     */
    static boolean isNullOrEmpty(final String value) {
        return value == null || value.length() == 0;
    }

    /**
     * Performs safe decoding of the specified string, taking care to preserve each <code>+</code> character, rather
     * than replacing it with a space character.
     *
     * @param stringToDecode
     *            A {@code String} that represents the string to decode.
     *
     * @return A {@code String} that represents the decoded string.
     */
    static String safeURLDecode(final String stringToDecode) {
        if (stringToDecode.length() == 0) {
            return Constants.EMPTY_STRING;
        }

        // '+' are decoded as ' ' so preserve before decoding
        if (stringToDecode.contains("+")) {
            final StringBuilder outBuilder = new StringBuilder();

            int startDex = 0;
            for (int m = 0; m < stringToDecode.length(); m++) {
                if (stringToDecode.charAt(m) == '+') {
                    if (m > startDex) {
                        try {
                            outBuilder.append(URLDecoder.decode(stringToDecode.substring(startDex, m),
                                    Constants.UTF8_CHARSET));
                        } catch (UnsupportedEncodingException e) {
                            throw new Error(e);
                        }
                    }

                    outBuilder.append("+");
                    startDex = m + 1;
                }
            }

            if (startDex != stringToDecode.length()) {
                try {
                    outBuilder.append(URLDecoder.decode(stringToDecode.substring(startDex, stringToDecode.length()),
                            Constants.UTF8_CHARSET));
                } catch (UnsupportedEncodingException e) {
                    throw new Error(e);
                }
            }

            return outBuilder.toString();
        }
        else {
            try {
                return URLDecoder.decode(stringToDecode, Constants.UTF8_CHARSET);
            } catch (UnsupportedEncodingException e) {
                throw new Error(e);
            }
        }
    }

    /**
     * Stores a reference to the UTC time zone.
     */
     static final TimeZone UTC_ZONE = TimeZone.getTimeZone("UTC");

    /**
     * Stores a reference to the date/time pattern with the greatest precision Java.util.Date is capable of expressing.
     */
    private static final String MAX_PRECISION_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS";

    /**
     * Stores a reference to the ISO8601 date/time pattern.
     */
    private static final String ISO8601_PATTERN = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    /**
     * Stores a reference to the ISO8601 date/time pattern.
     */
    private static final String ISO8601_PATTERN_NO_SECONDS = "yyyy-MM-dd'T'HH:mm'Z'";

    /**
     * The length of a datestring that matches the MAX_PRECISION_PATTERN.
     */
    private static final int MAX_PRECISION_DATESTRING_LENGTH = MAX_PRECISION_PATTERN.replaceAll("'", "").length();

    /**
     * Given a String representing a date in a form of the ISO8601 pattern, generates a Date representing it
     * with up to millisecond precision.
     *
     * @param dateString
     *              the {@code String} to be interpreted as a <code>Date</code>
     *
     * @return the corresponding <code>Date</code> object
     */
    // TODO: Get rid of this with Java 8 if possible.
    public static Date parseDate(String dateString) {
        String pattern = MAX_PRECISION_PATTERN;
        switch(dateString.length()) {
            case 28: // "yyyy-MM-dd'T'HH:mm:ss.SSSSSSS'Z'"-> [2012-01-04T23:21:59.1234567Z] length = 28
            case 27: // "yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'"-> [2012-01-04T23:21:59.123456Z] length = 27
            case 26: // "yyyy-MM-dd'T'HH:mm:ss.SSSSS'Z'"-> [2012-01-04T23:21:59.12345Z] length = 26
            case 25: // "yyyy-MM-dd'T'HH:mm:ss.SSSS'Z'"-> [2012-01-04T23:21:59.1234Z] length = 25
            case 24: // "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"-> [2012-01-04T23:21:59.123Z] length = 24
                dateString = dateString.substring(0, MAX_PRECISION_DATESTRING_LENGTH);
                break;
            case 23: // "yyyy-MM-dd'T'HH:mm:ss.SS'Z'"-> [2012-01-04T23:21:59.12Z] length = 23
                // SS is assumed to be milliseconds, so a trailing 0 is necessary
                dateString = dateString.replace("Z", "0");
                break;
            case 22: // "yyyy-MM-dd'T'HH:mm:ss.S'Z'"-> [2012-01-04T23:21:59.1Z] length = 22
                // S is assumed to be milliseconds, so trailing 0's are necessary
                dateString = dateString.replace("Z", "00");
                break;
            case 20: // "yyyy-MM-dd'T'HH:mm:ss'Z'"-> [2012-01-04T23:21:59Z] length = 20
                pattern = Utility.ISO8601_PATTERN;
                break;
            case 17: // "yyyy-MM-dd'T'HH:mm'Z'"-> [2012-01-04T23:21Z] length = 17
                pattern = Utility.ISO8601_PATTERN_NO_SECONDS;
                break;
            default:
                throw new IllegalArgumentException(String.format(SR.INVALID_DATE_STRING, dateString));
        }

        final DateFormat format = new SimpleDateFormat(pattern, Locale.US);
        format.setTimeZone(UTC_ZONE);
        try {
            return format.parse(dateString);
        }
        catch (final ParseException e) {
            throw new IllegalArgumentException(String.format(SR.INVALID_DATE_STRING, dateString), e);
        }
    }

    /**
     * Asserts that the specified integer is in the valid range.
     *
     * @param param
     *            A <code>String</code> that represents the name of the parameter, which becomes the exception message
     *            text if the <code>value</code> parameter is out of bounds.
     * @param value
     *            The value of the specified parameter.
     * @param min
     *            The minimum value for the specified parameter.
     * @param max
     *            The maximum value for the specified parameter.
     */
    public static void assertInBounds(final String param, final long value, final long min, final long max) {
        if (value < min || value > max) {
            throw new IllegalArgumentException(String.format(SR.PARAMETER_NOT_IN_RANGE, param, min, max));
        }
    }

    /*
    Note that this join method will treat null as an empty string instead of "null". This is because our use cases for
    this are building strings to sign, which want empty instead of "null".
     */

    public static String join(String[] components, char delimiter) {
        StringBuilder result = new StringBuilder();
        for (String component : components) {
            if (component == null) {
                result.append("");
            } else {
                result.append(component);
            }
            result.append(delimiter);
        }
        result.deleteCharAt(result.length() - 1); // Delete the extra delimiter.
        return result.toString();
    }
}
