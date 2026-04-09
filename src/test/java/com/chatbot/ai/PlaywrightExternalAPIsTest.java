package com.chatbot.ai;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests with Playwright for multiple public external APIs
 * Examples of APIs that can be tested with Playwright
 */
class PlaywrightExternalAPIsTest {

    private Playwright playwright;
    private Browser browser;
    private BrowserContext context;
    private Page page;

    @BeforeEach
    void setUp() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
        context = browser.newContext();
        page = context.newPage();
    }

    @AfterEach
    void tearDown() {
        if (page != null) page.close();
        if (context != null) context.close();
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }

    @Test
    void testPokeAPIAPI() {
        // Test PokeAPI - Public Pokémon API
        String url = "https://pokeapi.co/api/v2/pokemon/pikachu";

        page.navigate(url);
        String response = page.content();

        assertNotNull(response);
        assertTrue(response.contains("pikachu"));
        System.out.println("✓ PokeAPI - OK");
        System.out.println("Response: " + response.substring(0, Math.min(300, response.length())));
    }

    @Test
    void testDogCEOAPI() {
        // Test Dog CEO API - Public API with dog images
        String url = "https://dog.ceo/api/breeds/list/all";

        page.navigate(url);
        String response = page.content();

        assertNotNull(response);
        assertTrue(response.contains("message"));
        System.out.println("✓ Dog CEO API - OK");
    }

    @Test
    void testGithubAPI() {
        // Test GitHub API - Get public information from GitHub
        String url = "https://api.github.com/users/torvalds";

        page.navigate(url);
        String response = page.content();

        assertNotNull(response);
        assertTrue(response.contains("login"));
        System.out.println("✓ GitHub API - OK");
        System.out.println("Response: " + response.substring(0, Math.min(300, response.length())));
    }

    @Test
    void testPublicHolidaysAPI() {
        // Test Public Holidays API - Public holidays by country
        String url = "https://date.nager.at/api/v3/publicholidays/2024/BR";

        page.navigate(url);
        String response = page.content();

        assertNotNull(response);
        System.out.println("✓ Public Holidays API - OK");
    }

    @Test
    void testCoinGeckoAPI() {
        // Test CoinGecko API - Cryptocurrency data
        String url = "https://api.coingecko.com/api/v3/simple/price?ids=bitcoin&vs_currencies=usd";

        page.navigate(url);
        String response = page.content();

        assertNotNull(response);
        assertTrue(response.contains("bitcoin"));
        System.out.println("✓ CoinGecko API - OK");
        System.out.println("Response: " + response);
    }

    @Test
    void testCatFactsAPI() {
        // Test Cat Facts API - Facts about cats
        String url = "https://catfact.ninja/fact";

        page.navigate(url);
        String response = page.content();

        assertNotNull(response);
        assertTrue(response.contains("fact"));
        System.out.println("✓ Cat Facts API - OK");
    }

    @Test
    void testAgeGuessAPI() {
        // Test Age Guess API - Guess age by name
        String url = "https://api.agify.io?name=michael";

        page.navigate(url);
        String response = page.content();

        assertNotNull(response);
        assertTrue(response.contains("age"));
        System.out.println("✓ Age Guess API - OK");
    }

    @Test
    void testGenderGuessAPI() {
        // Test Gender Guess API - Guess gender by name
        String url = "https://api.genderize.io?name=john";

        page.navigate(url);
        String response = page.content();

        assertNotNull(response);
        assertTrue(response.contains("gender"));
        System.out.println("✓ Gender Guess API - OK");
    }

    @Test
    void testIPInfoAPI() {
        // Test IP Info API - Information about IP address
        String url = "https://ipinfo.io/8.8.8.8/json";

        page.navigate(url);
        String response = page.content();

        assertNotNull(response);
        assertTrue(response.contains("ip"));
        System.out.println("✓ IP Info API - OK");
    }

    @Test
    void testMultipleAPIsSequence() {
        // Test multiple APIs in sequence
        String[] apis = {
                "https://jsonplaceholder.typicode.com/posts/1",
                "https://restcountries.com/v3.1/name/USA",
                "https://pokeapi.co/api/v2/pokemon/ditto",
                "https://dog.ceo/api/breeds/image/random",
                "https://catfact.ninja/fact"
        };

        for (String api : apis) {
            try {
                page.navigate(api);
                Response response = page.navigate(api);
                assertEquals(200, response.status(), "Failed to access: " + api);
                System.out.println("✓ " + api.split("/")[2] + " - OK");
            } catch (Exception e) {
                System.out.println("✗ " + api + " - Error: " + e.getMessage());
            }
        }
    }

    @Test
    void testAPIResponseHeaders() {
        // Test response headers from an API
        page.onResponse(response -> {
            String contentType = response.headerValue("content-type");
            System.out.println("Content-Type: " + contentType);
        });

        String url = "https://jsonplaceholder.typicode.com/posts/1";
        Response response = page.navigate(url);

        assertEquals(200, response.status());
        String contentType = response.headerValue("content-type");
        assertNotNull(contentType);
        assertTrue(contentType.contains("json"));
        System.out.println("✓ API Headers test - OK");
    }

}

