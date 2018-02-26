package com.microsoft.azure.storage

import com.linkedin.flashback.SceneAccessLayer
import com.linkedin.flashback.factory.SceneFactory
import com.linkedin.flashback.matchrules.CompositeMatchRule
import com.linkedin.flashback.matchrules.MatchBody
import com.linkedin.flashback.matchrules.MatchMethod
import com.linkedin.flashback.matchrules.MatchRuleUtils
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
import spock.lang.*

import java.nio.channels.Pipe

class ContainerAPI extends Specification {

    static ServiceURL su

    static boolean enableDebugging = false

    static boolean enableRecordings = true

    static String containerPrefix = "javatestcontainer"

    @Shared
    int testNo = 0 // Used to generate stable container names for recordings

    String containerName = enableRecordings ? containerPrefix + testNo : containerPrefix + System.currentTimeMillis()

    ContainerURL cu

    static SceneMode sceneMode = SceneMode.PLAYBACK

    static String recordingsDir = "C:\\Users\\frley\\Documents\\azure-storage-java-async\\azure-storage\\src\\test\\resources\\recordings";

    static String sceneName = "GroovyScene";

    @Shared
    FlashbackRunner flashbackRunner

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
        if (enableDebugging) {
            HttpClient.Configuration configuration = new HttpClient.Configuration(
                    new Proxy(Proxy.Type.HTTP, new InetSocketAddress("localhost", 8888)))
            po.client = HttpClient.createDefault(configuration)
        }
        else if (enableRecordings) {
            HttpClient.Configuration configuration = new HttpClient.Configuration(
                    new Proxy(Proxy.Type.HTTP, new InetSocketAddress("localhost", 1234)))
            po.client = HttpClient.createDefault(configuration)
            configureRecordings()
        }

        HttpPipeline pipeline = StorageURL.createPipeline(creds, po)

        su = new ServiceURL(
                new URL("http://" + System.getenv().get("ACCOUNT_NAME") + ".blob.core.windows.net"), pipeline)
    }

    def configureRecordings() {
        String proxyHost = "localhost"
        int port = 1234

        CompositeMatchRule rule = new CompositeMatchRule()
        rule.addRule(new MatchBody())
        rule.addRule(new MatchMethod())

        /*
         We can ignore the access condition headers because they will be distinguished by the container name when the
         test unrolls.
         */
        HashSet<String> blacklistHeaders = new HashSet<>()
        blacklistHeaders.add("Authorization")
        blacklistHeaders.add("x-ms-date")
        blacklistHeaders.add("x-ms-client-request-id")
        /*
         The scene will not get serialized correctly if we blacklist the headers on the way out
         (the responses get recorded with the wrong status code for some reason, possibly because
         the proxy was actually modifying the outgoing request?), but we must ignore these values
         during playback for proper matching.
         */
        if (sceneMode == SceneMode.PLAYBACK) {
            blacklistHeaders.add("If-Modified-Since")
            blacklistHeaders.add("If-Unmodified-Since")
            blacklistHeaders.add("If-Match")
            blacklistHeaders.add("If-None-Match")
        }
        rule.addRule(MatchRuleUtils.matchHeadersWithBlacklist(blacklistHeaders))

        HashSet<String> blacklistQuery = new HashSet<>()
        blacklistQuery.add("sig")
        rule.addRule(MatchRuleUtils.matchUriWithQueryBlacklist(blacklistQuery))

        SceneConfiguration sceneConfig = new SceneConfiguration(recordingsDir, sceneMode, sceneName);
        flashbackRunner = new FlashbackRunner.Builder().host(proxyHost).port(port).mode(sceneMode)
                .sceneAccessLayer(new SceneAccessLayer(SceneFactory.create(sceneConfig), rule))
                .build()
        flashbackRunner.start()
    }

    def cleanupSpec() {
        if (flashbackRunner != null) {
            flashbackRunner.close()
        }

        // We don't need to clean up containers if we are playing back
        if (!(enableRecordings && sceneMode.equals(SceneMode.PLAYBACK))) {
            // Clean up containers
            // Create a new pipline without any proxies
            HttpPipeline pipeline = StorageURL.createPipeline(creds, new PipelineOptions())

            ServiceURL serviceURL = new ServiceURL(
                    new URL("http://" + System.getenv().get("ACCOUNT_NAME") + ".blob.core.windows.net"), pipeline)
            // There should not be more than 50000 containers from these tests
            for (Container c : serviceURL.listContainers(null,
                    new ListContainersOptions(null, containerPrefix, null)).blockingGet().body()
                    .containers()) {
                ContainerURL containerURL = su.createContainerURL(c.name())
                containerURL.delete(null).blockingGet()
            }
        }
    }

    def setup() {
        cu = su.createContainerURL(containerName)
    }

    def cleanup() {
        testNo++
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
        //TODO: Have to use this to actually check the exceptio message in the expect section
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

        where:
        modified           | unmodified      | match          | noneMatch        || statusCode
        newDate            | null            | null           | null             || 412
        null               | oldDate         | null           | null             || 412
        null               | null            | null           | null             || 202
        oldDate            | null            | null           | null             || 202
        null               | newDate         | null           | null             || 202
        null               | null            | receivedEtag   | null             || 0
        null               | null            | null           | garbageEtag      || 0
        /*
         Note that the value of receivedEtag is overwritten. It merely indicates that the received ETag
         will be used for the test once it is received.
         */
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
