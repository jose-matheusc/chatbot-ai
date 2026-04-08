package com.chatbot.ai;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes com Playwright chamando APIs externas
 */
public class PlaywrightTest {

    private Playwright playwright;
    private Browser browser;
    private BrowserContext context;
    private Page page;

    @BeforeEach
    public void setUp() {
        // Inicializa o Playwright
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
        context = browser.newContext();
        page = context.newPage();
    }

    @AfterEach
    public void tearDown() {
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
    public void testJsonPlaceholderAPI() {
        // Testa JSONPlaceholder - API pública para teste
        String url = "https://jsonplaceholder.typicode.com/posts/1";

        page.navigate(url);

        String response = page.content();
        assertNotNull(response);
        assertFalse(response.isEmpty());
        assertTrue(response.contains("userId") || response.contains("id"));

        System.out.println("✓ JSONPlaceholder API test passed");
        System.out.println("Resposta: " + response.substring(0, Math.min(200, response.length())));
    }

    @Test
    public void testJsonPlaceholderMultiplePosts() {
        // Testa múltiplas requisições à API
        for (int i = 1; i <= 3; i++) {
            String url = "https://jsonplaceholder.typicode.com/posts/" + i;
            page.navigate(url);

            String response = page.content();
            assertNotNull(response);
            assertTrue(response.contains("\"id\":" + i));

            System.out.println("Post " + i + " recuperado com sucesso");
        }
    }

    @Test
    public void testRESTCountriesAPI() {
        // Testa REST Countries API - API pública para dados de países
        String url = "https://restcountries.com/v3.1/name/Brazil";

        page.navigate(url);

        String response = page.content();
        assertNotNull(response);
        assertTrue(response.contains("Brazil") || response.contains("Brasil"));

        System.out.println("✓ REST Countries API test passed");
    }

    @Test
    public void testAPIWithNetworkMonitoring() {
        // Monitora requisições e respostas de rede
        page.onRequest(request -> System.out.println(">> REQUEST: " + request.method() + " " + request.url()));
        page.onResponse(response -> System.out.println("<< RESPONSE: " + response.status() + " " + response.url()));

        String url = "https://jsonplaceholder.typicode.com/users/1";
        Response response = page.navigate(url);

        assertEquals(200, response.status());
        System.out.println("✓ Network monitoring test passed");
    }

    @Test
    public void testMultipleExternalAPIs() {
        // Testa várias APIs externas em sequência
        String[] apiUrls = {
                "https://jsonplaceholder.typicode.com/todos/1",
                "https://restcountries.com/v3.1/name/USA",
                "https://jsonplaceholder.typicode.com/comments/1"
        };

        for (String url : apiUrls) {
            page.navigate(url);
            Response response = page.navigate(url);
            assertEquals(200, response.status(), "API em " + url + " retornou erro");
            System.out.println("✓ " + url + " - Status: " + response.status());
        }
    }

    @Test
    public void testAPIResponseTime() {
        // Mede o tempo de resposta da API
        long startTime = System.currentTimeMillis();

        String url = "https://jsonplaceholder.typicode.com/posts";
        page.navigate(url);

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        System.out.println("✓ Tempo de resposta: " + duration + "ms");
        assertTrue(duration < 5000, "API levou mais de 5 segundos");
    }

}

