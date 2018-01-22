package com.microsoft.azure.storage;

import com.microsoft.azure.storage.blob.*;
import com.microsoft.azure.storage.models.ContainerCreateHeaders;
import com.microsoft.rest.v2.RestResponse;
import com.microsoft.rest.v2.http.HttpPipeline;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.Schedulers;
import org.junit.Assert;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.InvalidKeyException;

public class ContainerTests {

    @Test
    public void TestCreateContainerTestObserver()
            throws UnsupportedEncodingException, InvalidKeyException, MalformedURLException {
        SharedKeyCredentials creds = new SharedKeyCredentials(System.getenv().get("ACCOUNT_NAME"),
                System.getenv().get("ACCOUNT_KEY"));
        HttpPipeline pipeline = StorageURL.CreatePipeline(creds, new PipelineOptions());
        ServiceURL su = new ServiceURL(
                new URL("http://" + System.getenv().get("ACCOUNT_NAME") + ".blob.core.windows.net"), pipeline);
        String containerName = "javatestcontainer"+System.currentTimeMillis();
        ContainerURL cu = su.createContainerURL(containerName);

        TestObserver<RestResponse<ContainerCreateHeaders, Void>> testObserver = new TestObserver<>();
        cu.createAsync(null, null).subscribe(testObserver);

        testObserver.awaitTerminalEvent();
        Assert.assertEquals(1, testObserver.valueCount());
        Assert.assertEquals(201, testObserver.values().get(0).statusCode());
    }

    @Test
    public void TestCreateContainerOverride()
        throws UnsupportedEncodingException, InvalidKeyException, MalformedURLException {
        SharedKeyCredentials creds = new SharedKeyCredentials(System.getenv().get("ACCOUNT_NAME"),
                System.getenv().get("ACCOUNT_KEY"));
        HttpPipeline pipeline = StorageURL.CreatePipeline(creds, new PipelineOptions());
        ServiceURL su = new ServiceURL(
                new URL("http://" + System.getenv().get("ACCOUNT_NAME") + ".blob.core.windows.net"), pipeline);
        String containerName = "javatestcontainer" + System.currentTimeMillis();
        ContainerURL cu = su.createContainerURL(containerName);
        cu.createAsync(null, null).subscribeOn(Schedulers.trampoline()).subscribe(
                new Consumer<RestResponse<ContainerCreateHeaders, Void>>() {
                    @Override
                    public void accept(RestResponse<ContainerCreateHeaders, Void> resp)
                            throws Exception {
                        Assert.assertEquals(2011, resp.statusCode());
                    }
                });
    }

    @Test
    public void TestCreateContainerBlockingGet()
        throws UnsupportedEncodingException, InvalidKeyException, MalformedURLException {
            SharedKeyCredentials creds = new SharedKeyCredentials(System.getenv().get("ACCOUNT_NAME"),
                    System.getenv().get("ACCOUNT_KEY"));
            HttpPipeline pipeline = StorageURL.CreatePipeline(creds, new PipelineOptions());
            ServiceURL su = new ServiceURL(
                    new URL("http://" + System.getenv().get("ACCOUNT_NAME") + ".blob.core.windows.net"), pipeline);
            String containerName = "javatestcontainer" + System.currentTimeMillis();
            ContainerURL cu = su.createContainerURL(containerName);
            int status = cu.createAsync(null, null).blockingGet().statusCode();
        Assert.assertEquals(201, status);
    }

}
