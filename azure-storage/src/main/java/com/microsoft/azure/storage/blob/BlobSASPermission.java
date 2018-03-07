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

/**
 * Specifies the set of possible permissions for a blob shared access policy.
 */
public final class BlobSASPermission {
    /**
     * Specifies Read access granted.
     */
    public boolean read;

    /**
     * Specifies Add access granted.
     */
    public boolean add;

    /**
     * Specifies Create access granted.
     */
    public boolean create;

    /**
     * Specifies Write access granted.
     */
    public boolean write;

    /**
     * Specifies Delete access granted.
     */
    public boolean delete;

    /**
     * Initializes a {@code BlobSASPermission} object with all fields set to false.
     */
    public BlobSASPermission() {}

    /**
     * Converts the given permissions to a {@code String}. Using this method will guarantee the permissions are in an
     * order accepted by the service.
     *
     * @return
     *      A {@code String} which represents the {@code BlobSASPermission}.
     */
    @Override
    public String toString() {
        // The order of the characters should be as specified here to ensure correctness:
        // https://docs.microsoft.com/en-us/rest/api/storageservices/constructing-a-service-sas

        final StringBuilder builder = new StringBuilder();

        if (this.read) {
            builder.append('r');
        }

        if (this.add) {
            builder.append('a');
        }

        if (this.create) {
            builder.append('c');
        }

        if (this.write) {
            builder.append('w');
        }

        if (this.delete) {
            builder.append('d');
        }

        return builder.toString();
    }

    /**
     * Creates a {@code BlobSASPermission} from the specified permissions string. This method will throw an
     * {@code IllegalArgumentException} if it encounters a character that does not correspond to a valid permission.
     *
     * @param permString
     *      A {@code String} which represents the {@code BlobSASPermission}.
     * @return
     *      A {@code BlobSASPermission} generated from the given {@code String}.
     */
    public static BlobSASPermission parse(String permString) {
        BlobSASPermission permissions = new BlobSASPermission();

        for (int i=0; i<permString.length(); i++) {
            char c = permString.charAt(i);
            switch (c) {
                case 'r':
                    permissions.read = true;
                    break;
                case 'a':
                    permissions.read = true;
                    break;
                case 'c':
                    permissions.create = true;
                    break;
                case 'w':
                    permissions.write = true;
                    break;
                case 'd':
                    permissions.delete = true;
                    break;
                default:
                    throw new IllegalArgumentException(
                        String.format(SR.ENUM_COULD_NOT_BE_PARSED_INVALID_VALUE, "Permissions", permString, c));
            }
        }
        return permissions;
    }
}
