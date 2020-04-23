package com.github.automatedowl.examples;

import java.lang.reflect.Method;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.logging.Logger;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Guice;

@Guice(modules = {TestModule.class})
public class ServerAutomationTestCase {

    // Set logger
    Logger logger = Logger.getGlobal();

    // Declare configuration object
    TestConfiguration.Config testConfig;

    // Declare HTTP client
    final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();
    HttpRequest request;
    HttpResponse response;

    /////////////////////////////////////////////////////////////////////////////////
    //////////////////////////// Test Case Methods //////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////

    @BeforeMethod
    void setTestConfiguration(Method method) {
        logger.info("Setting up ServerAutomationTestCase..");

        // Set test configuration
        testConfig = TestConfiguration.TEST_TO_CONFIG_MAP.get(method.getName());
    }

    @AfterClass
    void tearDown() {
        logger.info("Tearing down ServerAutomationTestCase..");
    }
}
