package com.github.automatedowl.examples;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class TestConfiguration {

    static Map<String, Config> TEST_TO_CONFIG_MAP = new HashMap<>();

    enum Config {

        // Enum constructor calls
        API_GET_JOB_TEST(List.of(
                "https://jokes.p.rapidapi.com/jod/test")),
        API_GET_CATEGORY_TEST(List.of(
                "https://jokes.p.rapidapi.com/jod/categories",
                "https://jokes.p.rapidapi.com/jod?category=$category"));

        // Define test expected texts
        public static final String ERROR_404_TEST = "message\":\"Not Found\"";

        // Declare URLs and headers
        public List<String> urls;
        Map<String, String> TEST_HEADERS;

        private Map<String, String> JOKES_RAPIDAPI_HEADERS =  new HashMap<>() {{
            put("x-rapidapi-host", "jokes.p.rapidapi.com");
            put("x-rapidapi-key", "56d7a4653emsh4c19b463b18e6b7p144eb7jsn030e478c59b2");
        }};

        Config(List<String> URLS) {
            this.urls = URLS;
            setHeaders(this);
        }

        private void setHeaders(Config config) {
            switch (config.toString()) {
                case "API_GET_JOB_TEST":
                    TEST_HEADERS = JOKES_RAPIDAPI_HEADERS;
                case "API_GET_CATEGORY_TEST":
                    TEST_HEADERS = JOKES_RAPIDAPI_HEADERS;
            }
        }
    }
}
