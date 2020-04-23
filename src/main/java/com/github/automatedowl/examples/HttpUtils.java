package com.github.automatedowl.examples;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.logging.Logger;

public class HttpUtils {
    Logger logger = Logger.getGlobal();

    // Perform configuration of HTTP request
    public HttpRequest setHttpRequest(String URL, Map<String, String> TEST_HEADERS) {
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(URI.create(URL));
        TEST_HEADERS.forEach(requestBuilder::header);
        return requestBuilder.build();
    }

    // Perform HTTP request
    public HttpResponse sendHttpRequest(HttpRequest request, HttpClient httpClient)
            throws InterruptedException, IOException {
        HttpResponse httpResponse;
        try {
            httpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            logger.info("Received content: " + httpResponse.body().toString());
        } catch (InterruptedException | IOException exception) {
            throw (exception);
        }
        return httpResponse;
    }
}
