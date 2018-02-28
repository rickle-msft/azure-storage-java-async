package com.microsoft.azure.storage

import com.linkedin.flashback.SceneAccessLayer
import com.linkedin.flashback.factory.SceneFactory
import com.linkedin.flashback.matchrules.CompositeMatchRule
import com.linkedin.flashback.matchrules.MatchBody
import com.linkedin.flashback.matchrules.MatchMethod
import com.linkedin.flashback.matchrules.MatchRule
import com.linkedin.flashback.matchrules.MatchRuleUtils
import com.linkedin.flashback.scene.SceneConfiguration
import com.linkedin.flashback.scene.SceneMode
import com.linkedin.flashback.smartproxy.FlashbackRunner
import com.microsoft.azure.storage.blob.ContainerURL
import com.microsoft.azure.storage.blob.ETag
import com.microsoft.azure.storage.blob.ListContainersOptions
import com.microsoft.azure.storage.blob.PipelineOptions
import com.microsoft.azure.storage.blob.ServiceURL
import com.microsoft.azure.storage.blob.SharedKeyCredentials
import com.microsoft.azure.storage.blob.StorageURL
import com.microsoft.azure.storage.models.Container
import com.microsoft.rest.v2.http.HttpClient
import com.microsoft.rest.v2.http.HttpPipeline
import org.joda.time.DateTime

import java.security.InvalidKeyException

class TestUtility {

    public static final boolean enableDebugging = false

    public static final boolean enableRecordings = true

    public static final String containerPrefix = "javatestcontainer"

    public static final SceneMode sceneMode = SceneMode.PLAYBACK

    public static final String sceneDir =
            "C:\\Users\\frley\\Documents\\azure-storage-java-async\\azure-storage\\src\\test\\resources\\recordings\\"

    private static FlashbackRunner flashbackRunner = null

    private static CompositeMatchRule matchRule = null

    /*
    The values below are used to create data-driven tests for access conditions.
     */
    // TODO: Change from joda time
    public static final Date oldDate = new DateTime().minusDays(1).toDate()

    public static final Date newDate = new DateTime().plusDays(1).toDate()

    /*
    Note that this value is only used to check if we are depending on the received etag. This value will not actually
    be used.
     */
    public static final ETag receivedEtag = new ETag("received")


    public static final ETag garbageEtag = new ETag("garbage")

    private static ServiceURL primaryServiceURL = null

    private static SharedKeyCredentials primaryCreds = null

    static MatchRule getMatchRule() {
        if (matchRule == null) {
            matchRule = new CompositeMatchRule()
            matchRule.addRule(new MatchBody())
            matchRule.addRule(new MatchMethod())

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
            matchRule.addRule(MatchRuleUtils.matchHeadersWithBlacklist(blacklistHeaders))

            HashSet<String> blacklistQuery = new HashSet<>()
            blacklistQuery.add("sig")

            matchRule.addRule(MatchRuleUtils.matchUriWithQueryBlacklist(blacklistQuery))
        }
        return matchRule
    }

    static String generateContainerName(String prefix, String core, String suffix) {
        return containerPrefix + prefix + core + suffix
    }

    static void setupFeatureRecording(String sceneName) {
        if (enableRecordings) {
            try {
                SceneConfiguration sceneConfig =
                        new SceneConfiguration(sceneDir, sceneMode, sceneName)
                FlashbackRunner runner = new FlashbackRunner.Builder().host("localhost").port(1234)
                        .mode(sceneMode)
                        .sceneAccessLayer(new SceneAccessLayer(SceneFactory.create(sceneConfig), getMatchRule()))
                        .build()
                runner.start()
                flashbackRunner = runner
            }
            catch (IOException | InterruptedException e) {
                throw new Error(e)
            }
        }
    }

    static void cleanupFeatureRecording() {
        if (enableRecordings) {
            flashbackRunner.close()
        }
        if (enableRecordings) {
            if (sceneMode.equals(SceneMode.RECORD)) {
                // TODO: Needed?
                //TestUtility.scrubAuthHeader(sceneName)
            }
        }
    }

    static void scrubAuthHeader(String sceneName) {
        /*try (Scanner scanner = new Scanner(new FileInputStream(sceneDir + sceneName))) {
            while(scanner.hasNext("\"Authorization\" :")) {

            }
        } catch (IOException e) {

        }*/
    }

    static SharedKeyCredentials getPrimaryCreds() throws InvalidKeyException {
        if (primaryCreds == null) {
            primaryCreds = new SharedKeyCredentials(System.getenv().get("ACCOUNT_NAME"),
                    System.getenv().get("ACCOUNT_KEY"))
        }
        return primaryCreds
    }

    static ServiceURL getPrimaryServiceURL() throws InvalidKeyException, MalformedURLException {
        if (primaryServiceURL == null) {
            PipelineOptions po = new PipelineOptions()
            if (enableDebugging) {
                HttpClient.Configuration configuration = new HttpClient.Configuration(
                        new Proxy(Proxy.Type.HTTP, new InetSocketAddress("localhost", 8888)))
                po.client = HttpClient.createDefault(configuration)
            } else if (enableRecordings) {
                HttpClient.Configuration configuration = new HttpClient.Configuration(
                        new Proxy(Proxy.Type.HTTP, new InetSocketAddress("localhost", 1234)))
                po.client = HttpClient.createDefault(configuration)
            }

            HttpPipeline pipeline = StorageURL.createPipeline(getPrimaryCreds(), po)

            primaryServiceURL = new ServiceURL(
                    new URL("http://" + getPrimaryCreds().getAccountName() + ".blob.core.windows.net"), pipeline)
        }
        return primaryServiceURL
    }

    static void cleanupContainers() throws MalformedURLException {
        // We don't need to clean up containers if we are playing back
        if (!(enableRecordings && sceneMode.equals(SceneMode.PLAYBACK))) {
            // Create a new pipeline without any proxies
            HttpPipeline pipeline = StorageURL.createPipeline(primaryCreds, new PipelineOptions())

            ServiceURL serviceURL = new ServiceURL(
                    new URL("http://" + System.getenv().get("ACCOUNT_NAME") + ".blob.core.windows.net"), pipeline)
            // There should not be more than 50000 containers from these tests
            for (Container c : serviceURL.listContainers(null,
                    new ListContainersOptions(null, containerPrefix, null)).blockingGet()
                    .body().containers()) {
                ContainerURL containerURL = serviceURL.createContainerURL(c.name())
                containerURL.delete(null).blockingGet()
            }
        }
    }
}
