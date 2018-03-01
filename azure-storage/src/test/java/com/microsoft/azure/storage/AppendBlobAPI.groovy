package com.microsoft.azure.storage

import com.microsoft.azure.storage.blob.AppendBlobURL
import com.microsoft.azure.storage.models.BlobPutHeaders
import com.microsoft.rest.v2.RestResponse

class AppendBlobAPI extends APISpec {
    AppendBlobURL bu

    def setup() {
        bu = cu.createAppendBlobURL(generateBlobName())
        bu.create(null, null, null).blockingGet()
    }

    def "Blob create all null"() {
        setup:
        bu = cu.createAppendBlobURL(generateBlobName())

        when:
        RestResponse<BlobPutHeaders, Void> response =
                bu.create(null, null, null).blockingGet()

        then:
        response.statusCode() == 201
        response.headers().eTag() != null
        response.headers().dateProperty() != null
        response.headers().lastModified() != null
        response.headers().requestId() != null
        response.headers().version() != null
    }
}
