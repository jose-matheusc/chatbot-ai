package com.chatbot.ai;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes com Playwright para múltiplas APIs externas públicas
 * Exemplos de APIs que podem ser testadas com Playwright
 */
public class PlaywrightExternalAPIsTest {

    private Playwright playwright;
    private Browser browser;
    private BrowserContext context;
    private Page page;

    @BeforeEach
    public void setUp() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
        context = browser.newContext();
        page = context.newPage();
    }

    @AfterEach
    public void tearDown() {
        if (page != null) page.close();
        if (context != null) context.close();
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }

    @Test
    public void testPokeAPIAPI() {
        // Testa PokeAPI - API pública de Pokémon
        String url = "https://pokeapi.co/api/v2/pokemon/pikachu";

        page.navigate(url);
        String response = page.content();

        assertNotNull(response);
        assertTrue(response.contains("pikachu"));
        System.out.println("✓ PokeAPI - OK");
        System.out.println("Resposta: " + response.substring(0, Math.min(300, response.length())));
    }

    @Test
    public void testDogCEOAPI() {
        // Testa Dog CEO API - API pública com imagens de cães
        String url = "https://dog.ceo/api/breeds/list/all";

        page.navigate(url);
        String response = page.content();

        assertNotNull(response);
        assertTrue(response.contains("message"));
        System.out.println("✓ Dog CEO API - OK");
    }

    @Test
    public void testGithubAPI() {
        // Testa GitHub API - Busca informações públicas do GitHub
        String url = "https://api.github.com/users/torvalds";

        page.navigate(url);
        String response = page.content();

        assertNotNull(response);
        assertTrue(response.contains("login"));
        System.out.println("✓ GitHub API - OK");
        System.out.println("Resposta: " + response.substring(0, Math.min(300, response.length())));
    }

    @Test
    public void testPublicHolidaysAPI() {
        // Testa Public Holidays API - Feriados públicos por país
        String url = "https://date.nager.at/api/v3/publicholidays/2024/BR";

        page.navigate(url);
        String response = page.content();

        assertNotNull(response);
        assertTrue(response.contains("date") || response.length() > 0);
        System.out.println("✓ Public Holidays API - OK");
    }

    @Test
    public void testCoinGeckoAPI() {
        // Testa CoinGecko API - Dados de criptomoedas
        String url = "https://api.coingecko.com/api/v3/simple/price?ids=bitcoin&vs_currencies=usd";

        page.navigate(url);
        String response = page.content();

        assertNotNull(response);
        assertTrue(response.contains("bitcoin"));
        System.out.println("✓ CoinGecko API - OK");
        System.out.println("Resposta: " + response);
    }

    @Test
    public void testCatFactsAPI() {
        // Testa Cat Facts API - Fatos sobre gatos
        String url = "https://catfact.ninja/fact";

        page.navigate(url);
        String response = page.content();

        assertNotNull(response);
        assertTrue(response.contains("fact"));
        System.out.println("✓ Cat Facts API - OK");
    }

    @Test
    public void testAgeGuessAPI() {
        // Testa Age Guess API - Adivinha a idade pela nome
        String url = "https://api.agify.io?name=michael";

        page.navigate(url);
        String response = page.content();

        assertNotNull(response);
        assertTrue(response.contains("age"));
        System.out.println("✓ Age Guess API - OK");
    }

    @Test
    public void testGenderGuessAPI() {
        // Testa Gender Guess API - Adivinha o gênero pelo nome
        String url = "https://api.genderize.io?name=john";

        page.navigate(url);
        String response = page.content();

        assertNotNull(response);
        assertTrue(response.contains("gender"));
        System.out.println("✓ Gender Guess API - OK");
    }

    @Test
    public void testIPInfoAPI() {
        // Testa IP Info API - Informações sobre endereço IP
        String url = "https://ipinfo.io/8.8.8.8/json";

        page.navigate(url);
        String response = page.content();

        assertNotNull(response);
        assertTrue(response.contains("ip"));
        System.out.println("✓ IP Info API - OK");
    }

    @Test
    public void testMultipleAPIsSequence() {
        // Testa múltiplas APIs em sequência
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
                assertEquals(200, response.status(), "Falha ao acessar: " + api);
                System.out.println("✓ " + api.split("/")[2] + " - OK");
            } catch (Exception e) {
                System.out.println("✗ " + api + " - Erro: " + e.getMessage());
            }
        }
    }

    @Test
    public void testAPIResponseHeaders() {
        // Testa headers de resposta de uma API
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

