package com.chatbot.ai;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests with Playwright calling external APIs
 */
class PlaywrightTest {

    private Playwright playwright;
    private Browser browser;
    private BrowserContext context;
    private Page page;

    @BeforeEach
    void setUp() {
        // Initialize Playwright
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
        context = browser.newContext();
        page = context.newPage();
    }

    @AfterEach
    void tearDown() {
        if (page != null) {
            page.close();
        }
        if (context != null) {
            context.close();
        }
        if (browser != null) {
            browser.close();
        }
        if (playwright != null) {
            playwright.close();
        }
    }

    @Test
    void testJsonPlaceholderAPI() {
        // Test JSONPlaceholder - Public API for testing
        String url = "https://jsonplaceholder.typicode.com/posts/1";

        page.navigate(url);

        String response = page.content();
        assertNotNull(response);
        assertFalse(response.isEmpty());
        assertTrue(response.contains("userId") || response.contains("id"));

        System.out.println("✓ JSONPlaceholder API test passed");
        System.out.println("Response: " + response.substring(0, Math.min(200, response.length())));
    }

    @Test
    void testJsonPlaceholderMultiplePosts() {
        // Test multiple requests to the API
        for (int i = 1; i <= 3; i++) {
            String url = "https://jsonplaceholder.typicode.com/posts/" + i;
            page.navigate(url);

            String response = page.content();
            assertNotNull(response);
            assertTrue(response.contains("\"id\":" + i));

            System.out.println("Post " + i + " retrieved successfully");
        }
    }

    @Test
    void testRESTCountriesAPI() {
        // Test REST Countries API - Public API for country data
        String url = "https://restcountries.com/v3.1/name/Brazil";

        page.navigate(url);

        String response = page.content();
        assertNotNull(response);
        assertTrue(response.contains("Brazil"));

        System.out.println("✓ REST Countries API test passed");
    }

    @Test
    void testAPIWithNetworkMonitoring() {
        // Monitor network requests and responses
        page.onRequest(request -> System.out.println(">> REQUEST: " + request.method() + " " + request.url()));
        page.onResponse(response -> System.out.println("<< RESPONSE: " + response.status() + " " + response.url()));

        String url = "https://jsonplaceholder.typicode.com/users/1";
        Response response = page.navigate(url);

        assertEquals(200, response.status());
        System.out.println("✓ Network monitoring test passed");
    }

    @Test
    void testMultipleExternalAPIs() {
        // Test multiple external APIs in sequence
        String[] apiUrls = {
                "https://jsonplaceholder.typicode.com/todos/1",
                "https://restcountries.com/v3.1/name/USA",
                "https://jsonplaceholder.typicode.com/comments/1"
        };

        for (String url : apiUrls) {
            page.navigate(url);
            Response response = page.navigate(url);
            assertEquals(200, response.status(), "API at " + url + " returned error");
            System.out.println("✓ " + url + " - Status: " + response.status());
        }
    }

    @Test
    void testAPIResponseTime() {
        // Measure API response time
        long startTime = System.currentTimeMillis();

        String url = "https://jsonplaceholder.typicode.com/posts";
        page.navigate(url);

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        System.out.println("✓ Response time: " + duration + "ms");
        assertTrue(duration < 5000, "API took more than 5 seconds");
    }

}

