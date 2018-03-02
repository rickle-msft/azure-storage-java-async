package com.microsoft.azure.storage

import com.microsoft.azure.storage.blob.BlockBlobURL
import com.microsoft.azure.storage.models.BlockListType
import io.reactivex.Flowable

class BlockBlobAPI extends APISpec{
    BlockBlobURL bu

    def setup(){
        bu = cu.createBlockBlobURL(generateBlobName())
        bu.putBlob(Flowable.just(defaultData), defaultText.length(), null, null, null)
                .blockingGet()
    }

    def "Block blob put block"() {
        expect:
        bu.putBlock(new String(Base64.encoder.encode("0000".bytes)), Flowable.just(defaultData),
                defaultData.remaining(), null).blockingGet().statusCode() == 201
    }

    def "Block blob put block list"(){
        setup:
        String blockID = new String(Base64.encoder.encode("0000".bytes))
        bu.putBlock(blockID, Flowable.just(defaultData), defaultData.remaining(), null).blockingGet()
        ArrayList<String> ids = new ArrayList<>()
        ids.add(blockID)

        expect:
        bu.putBlockList(ids, null, null, null).blockingGet().statusCode() == 201
    }

    def "Block blob get block list"() {
        setup:
        String blockID = new String(Base64.encoder.encode("0000".bytes))
        bu.putBlock(blockID, Flowable.just(defaultData), defaultData.remaining(), null).blockingGet()
        ArrayList<String> ids = new ArrayList<>()
        ids.add(blockID)

        expect:
        bu.getBlockList(BlockListType.ALL, null).blockingGet()
                .body().uncommittedBlocks().get(0).name().equals(blockID)
    }
}
