package com.microsoft.azure.storage;

import co.freeside.betamax.Betamax;
import co.freeside.betamax.MatchRule;
import co.freeside.betamax.Recorder;
import co.freeside.betamax.TapeMode;
import co.freeside.betamax.httpclient.BetamaxRoutePlanner;
import com.microsoft.azure.storage.blob.*;
import com.microsoft.azure.storage.blob.Base64;
import com.microsoft.azure.storage.models.*;
import com.microsoft.rest.v2.RestResponse;
import com.microsoft.rest.v2.http.*;
import com.microsoft.rest.v2.util.FlowableUtil;
import io.reactivex.Flowable;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertArrayEquals;


public class BlobStorageAPITests {

    @Rule
    public Recorder recorder = new Recorder();
    // Can set the default mode to quickly turn recording on or off

    @Betamax(tape="my-tape", mode=TapeMode.WRITE_ONLY)
    @Test
    public void TestPutBlobBasic() throws IOException, InvalidKeyException, InterruptedException {
        //BetamaxRoutePlanner.configure();
        /**
         * This library uses the Azure Rest Pipeline to make its requests. Details on this pipeline can be found here:
         * https://github.com/Azure/azure-pipeline-go/blob/master/pipeline/doc.go All references to HttpPipeline and
         * the like refer to this structure.
         * This library uses Microsoft AutoRest to generate the protocol layer off of the Swagger API spec of the
         * blob service. All files in the implementation and models folders as well as the Interfaces in the root
         * directory are auto-generated using this tool.
         * This library's paradigm is centered around the URL object. A URL is constructed to a resource, such as
         * BlobURL. This is solely a reference to a location; the existence of a BlobURL does not indicate the existence
         * of a blob or hold any state related to the blob. The URL objects define methods for all operations related
         * to that resource (or will eventually; some are not supported in the library yet).
         * Several structures are defined on top of the auto-generated protocol layer to logically group items or
         * concepts relevant to a given operation or resource. This both reduces the length of the parameter list
         * and provides some coherency and relationship of ideas to aid the developer, improving efficiency and
         * discoverability.
         * In this sample test, we demonstrate the use of all APIs that are currently implemented. They have been tested
         * to work in these cases, but they have not been thoroughly tested. More advanced operations performed by
         * specifying or modifying calls in this test are not guaranteed to work. APIs not shown here are not guaranteed
         * to work. Any reports on bugs found will be welcomed and addressed.
         */


        // Creating a pipeline requires a credentials object and a structure of pipeline options to customize the behavior.
        // Set your system environment variables of ACCOUNT_NAME and ACCOUNT_KEY to pull the appropriate credentials.
        // Credentials may be SharedKey as shown here or Anonymous as shown below.
        SharedKeyCredentials creds = new SharedKeyCredentials(System.getenv().get("ACCOUNT_NAME"),
                System.getenv().get("ACCOUNT_KEY"));

        // Currently only the default PipelineOptions are supported.
        HttpPipeline pipeline = StorageURL.CreatePipeline(creds, new PipelineOptions());

        // Create a reference to the service.
        ServiceURL su = new ServiceURL(
                new URL("http://" + System.getenv().get("ACCOUNT_NAME") + ".blob.core.windows.net"), pipeline);

        // Create a reference to a container. Using the ServiceURL to create the ContainerURL appends
        // the container name to the ServiceURL. A ContainerURL may also be created by calling its
        // constructor with a full path to the container and a pipeline.
        String containerName = "javatestcontainer" + System.currentTimeMillis();
        ContainerURL cu = su.createContainerURL(containerName);

        // Create a reference to a blob. Same pattern as containers.
        BlockBlobURL bu = cu.createBlockBlobURL("javatestblob");
        try {
            // Calls to blockingGet force the call to be synchronous. This whole test is synchronous.
            // APIs will typically return a RestResponse<*HeadersType*, *BodyType*>. It is therefore possible to
            // retrieve the headers and the deserialized body of every request. If there is no body in the request,
            // the body type will be Void.
            // Errors are thrown as exceptions in the synchronous (blockingGet) case.

            // Create the container. NOTE: Metadata is not currently supported on any resource.
            cu.createAsync(null, PublicAccessType.BLOB).blockingGet();

            // List the containers in the account.
            List<Container> containerList = new ArrayList<>();
            String marker = null;
            do {
                RestResponse<ServiceListContainersHeaders, ListContainersResponse> resp = su.listConatinersAsync(
                        "java", marker, null, null).blockingGet();
                containerList.addAll(resp.body().containers());
                marker = resp.body().marker();
            } while(marker != null);

            // NOTE: Assert statements are only for test purposes and should not be used in production.
            Assert.assertEquals(1, containerList.size());
            Assert.assertEquals(containerList.get(0).name(), containerName);

            // Create the blob with a single put. See below for the putBlock(List) scenario.
            bu.putBlobAsync(Flowable.just(new byte[]{0, 0, 0}), 3, null, null,
                    null).blockingGet();

            // Download the blob contents.
            Flowable<byte[]> data = bu.getBlobAsync(new BlobRange(0L, 3L),
                    null, false).blockingGet().body();
            byte[] dataByte = FlowableUtil.collectBytes(data).blockingGet();
            assertArrayEquals(dataByte, new byte[]{0, 0, 0});

            // Set and retrieve the blob properties. Metadata is not yet supported.
            BlobHttpHeaders headers = new BlobHttpHeaders("myControl", "myDisposition",
                    "myContentEncoding", "myLanguage", null,
                    "myType");
            bu.setPropertiesAsync(headers, null).blockingGet();
            BlobGetPropertiesHeaders receivedHeaders = bu.getPropertiesAndMetadataAsync(
                    null).blockingGet().headers();
            Assert.assertEquals(headers.getCacheControl(), receivedHeaders.cacheControl());
            Assert.assertEquals(headers.getContentDisposition(), receivedHeaders.contentDisposition());
            Assert.assertEquals(headers.getContentEncoding(), receivedHeaders.contentEncoding());
            Assert.assertEquals(headers.getContentLanguage(), receivedHeaders.contentLanguage());
            Assert.assertEquals(headers.getContentType(), receivedHeaders.contentType());

            // Create a snapshot of the blob and pull the snapshot ID out of the headers.
            String snapshot = bu.createSnapshotAsync(null, null).blockingGet()
                    .headers().snapshot().toString();

            // Create a reference to the blob snapshot. This returns a new BlockBlobURL object that references the same
            // path as the base blob with the query string including the snapshot value appended to the end.
            BlockBlobURL buSnapshot = bu.withSnapshot(snapshot);

            // Download the contents of the snapshot.
            data = buSnapshot.getBlobAsync(new BlobRange(0L, 3L),
                    null, false).blockingGet().body();
            dataByte = FlowableUtil.collectBytes(data).blockingGet();
            assertArrayEquals(dataByte, new byte[]{0,0,0});

            // Create a reference to another blob within the same container and copies the first blob into this location.
            BlockBlobURL bu2 = cu.createBlockBlobURL("javablob2");
            bu2.startCopyAsync(bu.toURL(), null, null, null)
                    .blockingGet();

            // Simple delay to wait for the copy. Inefficient buf effective. A better method would be to periodically
            // poll the blob.
            TimeUnit.SECONDS.sleep(5);

            // Check the existence of the copied blob.
            receivedHeaders = bu2.getPropertiesAndMetadataAsync(null).blockingGet()
                    .headers();
            Assert.assertEquals(headers.getContentType(), receivedHeaders.contentType());

            // Create a reference to a new blob within the same container to upload blocks. Upload a single block.
            BlockBlobURL bu3 = cu.createBlockBlobURL("javablob3");
            ArrayList<String> blockIDs = new ArrayList<>();
            blockIDs.add(Base64.encode(new Byte[]{0}));
            bu3.putBlockAsync(blockIDs.get(0), Flowable.just(new byte[]{0,0,0}), 3,
                    null).blockingGet();

            // Get the list of blocks on this blob. For demonstration purposes.
            BlockList blockList = bu3.getBlockListAsync(BlockListType.ALL, null)
                    .blockingGet().body();
            Assert.assertEquals(blockIDs.get(0), blockList.uncommittedBlocks().get(0).name());

            // Get a list of blobs in the container including copies, snapshots, and uncommitted blobs.
            // For demonstration purposes.
            List<Blob> blobs = cu.listBlobsAsync(null,
                    new ListBlobsOptions(new BlobListingDetails(
                            true, false, true, true),
                            null, null, null)).blockingGet().body().blobs().blob();
            Assert.assertEquals(4, blobs.size());

            // Commit the list of blocks. Download the blob to verify.
            bu3.putBlockListAsync(blockIDs, null, null, null).blockingGet();
            data = bu3.getBlobAsync(new BlobRange(0L, 3L),
                    null, false).blockingGet().body();
            dataByte = FlowableUtil.collectBytes(data).blockingGet();
            assertArrayEquals(dataByte, new byte[]{0,0,0});

            // SAS -----------------------------
            // Parses a URL into its constituent components. This structure's URL fields may be modified.
            BlobURLParts parts = URLParser.ParseURL(bu.toURL());

            // Construct the AccountSAS values object. This encapsulates all the values needed to create an AccountSAS.
            AccountSAS sas = new AccountSAS("2016-05-31", SASProtocol.HTTPS_HTTP,
                    null, DateTime.now().plusDays(1).toDate(),
                    EnumSet.of(AccountSASPermission.READ, AccountSASPermission.WRITE),
                    null,
                    EnumSet.of(AccountSASService.BLOB),
                    EnumSet.of(AccountSASResourceType.OBJECT));

            // Construct a ServiceSAS in a pattern similar to that of the AccountSAS.
            // Comment out the AccountSAS creation and uncomment this to run with ServiceSAS.
            /*ServiceSAS sas = new ServiceSAS("2016-05-31", SASProtocol.HTTPS_HTTP,
                    DateTime.now().minusDays(1).toDate(), DateTime.now().plusDays(1).toDate(),
                    EnumSet.of(ContainerSASPermission.READ, ContainerSASPermission.WRITE),
                    null, containerName, null, null,
                    null, null, null, null);*/


            // GenerateSASQueryParameters hashes the sas using your account's credentials and then associates the
            // sasQueryParameters with the blobURLParts.
            parts.setSasQueryParameters(sas.GenerateSASQueryParameters(creds));

            // Using a SAS requires AnonymousCredentials on the pipeline.
            pipeline = StorageURL.CreatePipeline(new AnonymousCredentials(), new PipelineOptions());

            // Call toURL on the parts to get a string representation of the URL. This, along with the pipeline,
            // is used to create a new BlockBlobURL object.
            BlockBlobURL sasBlob = new BlockBlobURL(parts.toURL(), pipeline);

            // Download the blob using the SAS. To perform other operations, ensure the appropriate permissions are
            // specified above.
            data = sasBlob.getBlobAsync(new BlobRange(0L, 3L), null, false).blockingGet().body();
            dataByte = FlowableUtil.collectBytes(data).blockingGet();
            assertArrayEquals(dataByte, new byte[]{0, 0, 0});

            // --------------APPEND BLOBS-------------
            AppendBlobURL abu = cu.createAppendBlobURL("appendblob");
            abu.createBlobAsync(null, null, null).blockingGet();
            abu.appendBlockAsync(Flowable.just(new byte[]{0,0,0}), 3,  null).blockingGet();

            data = abu.getBlobAsync(new BlobRange(0L, 3L), null, false).blockingGet().body();
            dataByte = FlowableUtil.collectBytes(data).blockingGet();
            assertArrayEquals(dataByte, new byte[]{0, 0, 0});

            // ---------------PAGE BLOBS-------------
            PageBlobURL pbu = cu.createPageBlobURL("pageblob");
            pbu.createBlobAsync((512L * 3L), null, null, null, null).blockingGet();
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            for(int i=0; i<1024; i++) {
                os.write(1);
            }
            pbu.putPagesAsync(new PageRange().withStart(0).withEnd(1023), Flowable.just(os.toByteArray()),
                    null).blockingGet();
            String pageSnap = pbu.createSnapshotAsync(null, null).blockingGet().headers().snapshot();
            pbu.clearPagesAsync(new PageRange().withStart(0).withEnd(511), null).blockingGet();
            PageRange pr = pbu.getPageRangesAsync(new BlobRange(0L, (512L * 3L)), null).blockingGet()
                    .body().pageRange().get(0);
            Assert.assertEquals(pr.start(), 512);
            Assert.assertEquals(pr.end(), 1023);
            ClearRange cr = pbu.getPageRangesDiffAsync(null, pageSnap, null).blockingGet().body().clearRange().get(0);
            Assert.assertEquals(cr.start(), 0);
            Assert.assertEquals(cr.end(), 511);

            pbu.resizeAsync(512L * 4L, null).blockingGet();
            pbu.setSequenceNumber(SequenceNumberActionType.INCREMENT, null, null, null).blockingGet();
            BlobGetPropertiesHeaders pageHeaders = pbu.getPropertiesAndMetadataAsync(null).blockingGet().headers();
            Assert.assertEquals(1, pageHeaders.blobSequenceNumber().longValue());
            Assert.assertEquals((long)(512*4), pageHeaders.contentLength().longValue());

            PageBlobURL copyPbu = cu.createPageBlobURL("copyPage");
            CopyStatusType status = copyPbu.startIncrementalCopyAsync(pbu.toURL(), pageSnap, null).blockingGet().headers().copyStatus();
            Assert.assertEquals(CopyStatusType.PENDING, status);

            // ACCOUNT----------------------------
            StorageServiceProperties props = new StorageServiceProperties();
            Logging logging = new Logging().withRead(true).withVersion("1.0").
                    withRetentionPolicy(new RetentionPolicy().withDays(1).withEnabled(true));
            props = props.withLogging(logging);
            su.setPropertiesAsync(props).blockingGet();

            StorageServiceProperties receivedProps = su.getPropertiesAsync().blockingGet().body();
            Assert.assertEquals(receivedProps.logging().read(), props.logging().read());

            su.setPropertiesAsync(props.withLogging(logging.withRead(false).withRetentionPolicy(new RetentionPolicy()
                    .withEnabled(false)))).blockingGet();

            String secondaryAccount = System.getenv("ACCOUNT_NAME") + "-secondary";
            pipeline = StorageURL.CreatePipeline(creds, new PipelineOptions());
            ServiceURL secondary = new ServiceURL(new URL("http://" + secondaryAccount + ".blob.core.windows.net"),
                    pipeline);
            secondary.getStats().blockingGet();
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        finally {
            // Delete the blob and container. Deleting a container does not require deleting the blobs first.
            // This is just for demonstration purposes.
            try {
                bu.deleteAsync(DeleteSnapshotsOptionType.INCLUDE, null).blockingGet();
            }
            finally {
                cu.deleteAsync(null).blockingGet();
            }
        }
    }

}
