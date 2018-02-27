package com.microsoft.azure.storage

import com.linkedin.flashback.SceneAccessLayer
import com.linkedin.flashback.factory.SceneFactory
import com.linkedin.flashback.matchrules.CompositeMatchRule
import com.linkedin.flashback.matchrules.MatchBody
import com.linkedin.flashback.matchrules.MatchMethod
import com.linkedin.flashback.matchrules.MatchRuleUtils
import com.linkedin.flashback.scene.Scene
import com.linkedin.flashback.scene.SceneConfiguration
import com.linkedin.flashback.scene.SceneMode
import com.linkedin.flashback.smartproxy.FlashbackRunner
import com.microsoft.azure.storage.blob.ContainerAccessConditions
import com.microsoft.azure.storage.blob.ContainerURL
import com.microsoft.azure.storage.blob.ETag
import com.microsoft.azure.storage.blob.HTTPAccessConditions
import com.microsoft.azure.storage.blob.LeaseAccessConditions
import com.microsoft.azure.storage.blob.ListContainersOptions
import com.microsoft.azure.storage.blob.PipelineOptions
import com.microsoft.azure.storage.blob.ServiceURL
import com.microsoft.azure.storage.blob.SharedKeyCredentials
import com.microsoft.azure.storage.blob.StorageURL
import com.microsoft.azure.storage.models.Container
import com.microsoft.azure.storage.models.ContainerCreateHeaders
import com.microsoft.azure.storage.models.PublicAccessType
import com.microsoft.rest.v2.RestException
import com.microsoft.rest.v2.http.HttpClient
import com.microsoft.rest.v2.http.HttpPipeline
import org.joda.time.DateTime
import org.testng.annotations.Test
import spock.lang.*

class ContainerAPI extends Specification {

    static ServiceURL su

    @Shared
    int testNo = 0 // Used to generate stable container names for recordings

    String containerName = TestUtility.enableRecordings ? TestUtility.containerPrefix + testNo :
            TestUtility.containerPrefix + System.currentTimeMillis()

    ContainerURL cu

    String sceneName = "WillNeverBeWrittenTo" // Because the the setup for the first test will change the file

    @Shared
    FlashbackRunner flashbackRunner

    @Shared
    CompositeMatchRule rule

    /*
    The values below are used to create data-driven tests for access conditions.
     */
    @Shared
    Date oldDate = new DateTime().minusDays(1).toDate()

    @Shared
    Date newDate = new DateTime().plusDays(1).toDate()

    /*
    Note that this value is only used to check if we are depending on the received etag. This value will not actually
    be used.
     */
    @Shared
    ETag receivedEtag = new ETag("received")

    @Shared
    ETag garbageEtag = new ETag("garbage")

    static SharedKeyCredentials creds

    def setupSpec() {
        creds = new SharedKeyCredentials(System.getenv().get("ACCOUNT_NAME"),
                System.getenv().get("ACCOUNT_KEY"))

        PipelineOptions po = new PipelineOptions()
        if (TestUtility.enableDebugging) {
            HttpClient.Configuration configuration = new HttpClient.Configuration(
                    new Proxy(Proxy.Type.HTTP, new InetSocketAddress("localhost", 8888)))
            po.client = HttpClient.createDefault(configuration)
        }
        else if (TestUtility.enableRecordings) {
            HttpClient.Configuration configuration = new HttpClient.Configuration(
                    new Proxy(Proxy.Type.HTTP, new InetSocketAddress("localhost", 1234)))
            po.client = HttpClient.createDefault(configuration)
            configureMatchRule()
        }

        HttpPipeline pipeline = StorageURL.createPipeline(creds, po)

        su = new ServiceURL(
                new URL("http://" + System.getenv().get("ACCOUNT_NAME") + ".blob.core.windows.net"), pipeline)
    }

    def configureMatchRule() {
        rule = new CompositeMatchRule()
        rule.addRule(new MatchBody())
        rule.addRule(new MatchMethod())

        /*
         We can ignore the access condition headers because they will be
         distinguished by the container name when the test unrolls.
         */
        HashSet<String> blacklistHeaders = new HashSet<>()
        blacklistHeaders.add("Authorization")
        blacklistHeaders.add("x-ms-date")
        blacklistHeaders.add("x-ms-client-request-id")
        blacklistHeaders.add("If-Modified-Since")
        blacklistHeaders.add("If-Unmodified-Since")
        blacklistHeaders.add("If-Match")
        blacklistHeaders.add("If-None-Match")
        rule.addRule(MatchRuleUtils.matchHeadersWithBlacklist(blacklistHeaders))

        HashSet<String> blacklistQuery = new HashSet<>()
        blacklistQuery.add("sig")

        rule.addRule(MatchRuleUtils.matchUriWithQueryBlacklist(blacklistQuery))
    }

    def cleanupSpec() {
        if (TestUtility.enableRecordings) {
            flashbackRunner.close()
        }

        // We don't need to clean up containers if we are playing back
        if (!(TestUtility.enableRecordings && TestUtility.sceneMode.equals(SceneMode.PLAYBACK))) {
            // Create a new pipeline without any proxies
            HttpPipeline pipeline = StorageURL.createPipeline(creds, new PipelineOptions())

            ServiceURL serviceURL = new ServiceURL(
                    new URL("http://" + System.getenv().get("ACCOUNT_NAME") + ".blob.core.windows.net"), pipeline)
            // There should not be more than 50000 containers from these tests
            for (Container c : serviceURL.listContainers(null,
                    new ListContainersOptions(null, TestUtility.containerPrefix, null)).blockingGet().body()
                    .containers()) {
                ContainerURL containerURL = serviceURL.createContainerURL(c.name())
                containerURL.delete(null).blockingGet()
            }
        }
    }

    def setup() {
        cu = su.createContainerURL(containerName)

        if (TestUtility.enableRecordings) {
            sceneName = specificationContext.getCurrentIteration().name
            SceneConfiguration sceneConfig =
                    new SceneConfiguration(TestUtility.sceneDir, TestUtility.sceneMode, sceneName)
            flashbackRunner = new FlashbackRunner.Builder().host("localhost").port(1234)
                    .mode(TestUtility.sceneMode)
                    .sceneAccessLayer(new SceneAccessLayer(SceneFactory.create(sceneConfig), rule))
                    .build()
            flashbackRunner.start()
        }
    }

    def cleanup() {
        testNo++
        // TODO: If this there is a new test, this will fail because the file isn't created until
        // the flashbackRunner closes and writes. Should move this to the cleanupSpec and list out the dir?
        // In that case I'll need a different dir per spec, which is fine.
        //TestUtility.scrubAuthHeader(sceneName)
        if (TestUtility.enableRecordings) {
            flashbackRunner.close()
        }

    }

    @Unroll
    def "Delete container with access conditions"() {
        setup:
        ContainerCreateHeaders headers = cu.create(null, PublicAccessType.BLOB).blockingGet().headers()
        if (match.equals(receivedEtag)) {
            match = new ETag(headers.eTag())
        }
        ContainerAccessConditions cac = new ContainerAccessConditions(
                new HTTPAccessConditions(modified, unmodified, match, noneMatch), LeaseAccessConditions.NONE)
        int code = 0
        //TODO: Have to use this to actually check the exception message in the expect section
        String message = ""
        try{
            code = cu.delete(cac).blockingGet().statusCode()
        }
        catch (RestException e) {
            code = e.response().statusCode()
            //cu.delete(null).blockingGet() // If we were testing a failure case, still clean up the container
        }
        catch (IllegalArgumentException e) {
            e.message.contains("ETag access conditions are not supported")
            //cu.delete(null).blockingGet()
        }
        expect:
        code == statusCode

        /*
        For some reason, putting the 400 cases first works fine but putting them later in the sequence
        yields some sort of timeout error.
         */
        where:
        modified           | unmodified      | match          | noneMatch        || statusCode
        newDate            | null            | null           | null             || 412
        null               | oldDate         | null           | null             || 412
        null               | null            | null           | null             || 202
        oldDate            | null            | null           | null             || 202
        null               | newDate         | null           | null             || 202
        null               | null            | receivedEtag   | null             || 0
        null               | null            | null           | garbageEtag      || 0
    }

    def "Test the failed data driven case"() {
        setup:
        Date modified = newDate
        Date unmodified = null
        ETag match = null
        ETag noneMatch = null
        ContainerCreateHeaders headers = cu.create(null, PublicAccessType.BLOB).blockingGet().headers()
        if (match.equals(receivedEtag)) {
            match = new ETag(headers.eTag())
        }
        ContainerAccessConditions cac = new ContainerAccessConditions(
                new HTTPAccessConditions(modified, unmodified, match, noneMatch), LeaseAccessConditions.NONE)
        int code = 0
        try{
            code = cu.delete(cac).blockingGet().statusCode()
        }
        catch (RestException e) {
            code = e.response().statusCode()
            //cu.delete(null).blockingGet() // If we were testing a failure case, still clean up the container
        }
        catch (IllegalArgumentException e) {
            e.message.contains("ETag access conditions are not supported")
            //cu.delete(null).blockingGet()
        }
        expect:
        code == 412
    }

    @Unroll
    def "Test recorded failed response"() {
        ContainerCreateHeaders headers = cu.create(null, PublicAccessType.BLOB).blockingGet().headers()

        ContainerAccessConditions cac
        if (succeed) {
            cac = ContainerAccessConditions.NONE
        }
        else {
            cac = new ContainerAccessConditions(HTTPAccessConditions.NONE,
                    new LeaseAccessConditions("lease"))
        }

        expect:
        try {
            cu.delete(cac).blockingGet()
        }
        catch (RestException e) {
            e.response().statusCode() == result
        }

        where:
        succeed || result
        true    || 202
        false   || 400
    }
}
