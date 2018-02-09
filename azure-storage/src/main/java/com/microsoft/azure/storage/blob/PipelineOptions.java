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

import com.microsoft.rest.v2.http.HttpClient;
import com.microsoft.rest.v2.http.HttpPipelineLogLevel;
import com.microsoft.rest.v2.http.HttpPipelineLogger;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class PipelineOptions {
    /*
     PipelineOptions is mutable, but its fields refer to immutable objects. The createPipeline method can pass the
     fields to other methods, but the PipelineOptions object itself can only be used for the duration of this call; it
     must not be passed to anything with a longer lifetime.
     */

    // Log configures the pipeline's logging infrastructure indicating what information is logged and where.
    public HttpClient client;

    public HttpPipelineLogger logger;

    // Retry configures the built-in retry policy behavior.
    public RequestRetryOptions requestRetryOptions = RequestRetryOptions.DEFAULT;

    // configures the built-in request logging policy.
    public LoggingOptions loggingOptions = LoggingOptions.DEFAULT;

    // Telemetry configures the built-in telemetry policy behavior.
    public TelemetryOptions telemetryOptions = TelemetryOptions.DEFAULT;

    // TODO:
    public PipelineOptions() {
        HttpClient.Configuration configuration = new HttpClient.Configuration(
                new Proxy(Proxy.Type.HTTP, new InetSocketAddress("localhost", 8888)));
        // TODO: Move proxy addition to tests.
        this.client = HttpClient.createDefault(configuration); // Pass in configuration for Fiddler support.

        this.logger = new HttpPipelineLogger() {
            @Override
            public HttpPipelineLogLevel minimumLogLevel() {
                return HttpPipelineLogLevel.OFF;
            }

            // TODO: Revisit
            @Override
            public void log(HttpPipelineLogLevel logLevel, String s, Object... objects) {
                if (logLevel == HttpPipelineLogLevel.INFO) {
                    Logger.getGlobal().info(String.format(s, objects));
                } else if (logLevel == HttpPipelineLogLevel.WARNING) {
                    Logger.getGlobal().warning(String.format(s, objects));
                } else if (logLevel == HttpPipelineLogLevel.ERROR) {
                    Logger.getGlobal().severe(String.format(s, objects));
                }
            }
        };
    }
}
