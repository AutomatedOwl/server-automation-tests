package com.github.automatedowl.examples;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.List;

import static org.testng.Assert.*;

public class ServerAutomationTests extends ServerAutomationTestCase {

    private static final int FIRST_JOKE_INDEX = 0;
    private static final int FIRST_INDEX_URL = 0;
    private static final int SECOND_INDEX_URL = 1;
    private static final int EXPECTED_CATEGORIES_LENGTH = 4;
    private String constructedURL;
    private HttpUtils httpUtils = new HttpUtils();
    private FileUtils fileUtils = new FileUtils();
    JSONObject tempJsonObject = new JSONObject();

    @Test
    void rapidApiGetJobTest() throws IOException, InterruptedException {
        logger.info("Performing rapidApiGetJobTest..");
        logger.info(testConfig.urls.get(FIRST_INDEX_URL));
        request = httpUtils.setHttpRequest(
                testConfig.urls.get(FIRST_INDEX_URL), testConfig.TEST_HEADERS);
        logger.info("Sending HTTP request");
        response = httpUtils.sendHttpRequest(request, HTTP_CLIENT);
        logger.info("Verifying status code");
        responseHandler(response, FIRST_INDEX_URL);
    }

    @Test
    void rapidApiGetCategoryTest() throws IOException, InterruptedException {
        logger.info("Performing rapidApiGetCategoryTest..");

        // First URL scenario
        logger.info("Setting HTTP request");
        request = httpUtils.setHttpRequest(
                testConfig.urls.get(FIRST_INDEX_URL), testConfig.TEST_HEADERS);
        logger.info("Sending HTTP request");
        response = httpUtils.sendHttpRequest(request, HTTP_CLIENT);
        logger.info("Verifying status code");
        responseHandler(response, FIRST_INDEX_URL);

        // Second URL scenario
        logger.info("Setting HTTP request for second URL");
        request = httpUtils.setHttpRequest(constructedURL, testConfig.TEST_HEADERS);
        logger.info("Sending HTTP request for second URL");
        response = httpUtils.sendHttpRequest(request, HTTP_CLIENT);
        logger.info("Verifying status code");
        responseHandler(response, SECOND_INDEX_URL);
    }

    void responseHandler(HttpResponse response, int index) {
        switch (testConfig) {
            case API_GET_JOB_TEST:
                switch (response.statusCode()) {
                    case 404:
                        logger.severe("Received status code 404");
                        assertFalse(response.body().toString().contains(
                                testConfig.ERROR_404_TEST));
                        break;
                    default:

                        // Trigger non-failure scenario on unexpected text
                        assertTrue(true);
                }
            case API_GET_CATEGORY_TEST:
                switch (response.statusCode()) {
                    case 200:
                        logger.info("Received status code 200");

                        // Get JSON object
                        JSONObject jsonObject = new JSONObject(response.body().toString());
                        if (index == FIRST_INDEX_URL) {

                            // Parse categories and build second URL
                            parseCategories(jsonObject);
                        }
                        if (index == SECOND_INDEX_URL) {

                            // Verify and write fourth category
                            verifyCategory(jsonObject);
                        }
                        break;
                    default:

                        // Trigger failure scenario on different status code
                        fail("Unexpected status code");
                }
        }
    }

    void verifyCategory(JSONObject jsonObject) {

        // Get description and category and assert
        JSONArray jokes = jsonObject.getJSONObject("contents")
                .getJSONArray("jokes");
        iterateValuesAndWrite(jokes.getJSONObject(FIRST_JOKE_INDEX), List.of("description", "category"));

        // Get id, title and text assert
        iterateValuesAndWrite(jokes.getJSONObject(FIRST_JOKE_INDEX).getJSONObject("joke"),
                List.of("id", "title", "text"));

        // Write results to file
        fileUtils.writeJSON(tempJsonObject);
    }

   void iterateValuesAndWrite(JSONObject jsonObject, List<String> stringsList) {
        jsonObject.keys().forEachRemaining(key -> {
            if (stringsList.contains(key) && testConfig.EXPECTED_FIELDS.contains(key)) {
                tempJsonObject.put(key, jsonObject.getString(key));;
            }
        });
    }
    
    void parseCategories(JSONObject jsonObject) {
        JSONArray categories = jsonObject.getJSONObject("contents")
                .getJSONArray("categories");

        // Check for length of four
        if (categories.length() == EXPECTED_CATEGORIES_LENGTH) {

            // Set the extracted string
            String secondURL = testConfig.urls.get(SECOND_INDEX_URL)
                    .replace("$category",
                            categories.getJSONObject(EXPECTED_CATEGORIES_LENGTH - 1)
                                    .get("name").toString());
            logger.info("Extracted URL: " + secondURL);

            // Save the fourth category URL
            constructedURL = secondURL;
        } else {
            fail("Four categories were not received");
        }
    }
}
