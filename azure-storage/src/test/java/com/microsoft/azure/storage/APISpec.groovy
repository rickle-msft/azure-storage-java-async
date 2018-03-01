package com.microsoft.azure.storage

import com.microsoft.azure.storage.blob.ContainerURL
import spock.lang.Shared
import spock.lang.Specification

class APISpec extends Specification {
    @Shared
    Integer iterationNo = 0 // Used to generate stable container names for recording tests with multiple iterations.

    Integer entityNo = 0 // Used to generate stable container names for recording tests requiring multiple containers.

    ContainerURL cu

    def generateContainerName() {
        TestUtility.generateContainerName(specificationContext, iterationNo, entityNo++)
    }

    def generateBlobName() {
        TestUtility.generateBlobName(specificationContext, iterationNo, entityNo++)
    }

    def cleanupSpec() {
        TestUtility.cleanupContainers()
    }

    def setup() {
        TestUtility.setupFeatureRecording(specificationContext.getCurrentIteration().name)
        cu = TestUtility.getPrimaryServiceURL().createContainerURL(generateContainerName())
        cu.create(null, null).blockingGet()
    }

    def cleanup() {
        TestUtility.cleanupFeatureRecording()
        iterationNo = TestUtility.updateIterationNo(specificationContext, iterationNo)
    }
}
