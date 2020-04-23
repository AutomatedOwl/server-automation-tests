package com.github.automatedowl.examples;

import com.google.inject.AbstractModule;
import java.util.Arrays;

/**
 * Guice module for server tests
 */
public class TestModule extends AbstractModule {

    /**
     * Configure method for binding tests to configuration
     */
    protected synchronized void configure() {

        // Set configuration for all server tests
        Arrays.stream(TestConfiguration.Config.values()).forEach(config -> {
            TestConfiguration.TEST_TO_CONFIG_MAP.put(getConfigForTest(config), config);
        });
    }

    // Get config per test name
    private String getConfigForTest(TestConfiguration.Config config) {
        switch (config) {
            case API_GET_JOB_TEST:
                return "rapidApiGetJobTest";
            case API_GET_CATEGORY_TEST:
                return "rapidApiGetCategoryTest";
            default:
                return null;
        }
    }
}
