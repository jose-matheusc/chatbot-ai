# Testes com Playwright - APIs Externas

Este documento descreve como usar os testes com Playwright para chamar APIs externas.

## Configuração

O projeto já possui a dependência do Playwright adicionada no `pom.xml`:

```xml
<dependency>
    <groupId>com.microsoft.playwright</groupId>
    <artifactId>playwright</artifactId>
    <version>1.40.0</version>
</dependency>
```

## Arquivos de Teste

### 1. **PlaywrightTest.java**
Testes que chamam APIs públicas externas:
- ✅ JSONPlaceholder API (posts, usuários, comentários)
- ✅ REST Countries API (dados de países)
- ✅ Múltiplas requisições em sequência
- ✅ Monitoramento de rede e status HTTP
- ✅ Medição de tempo de resposta

### 2. **PlaywrightExternalAPIsTest.java**
Exemplos de múltiplas APIs externas que você pode testar:
- 🌤️ OpenWeatherMap API - Dados meteorológicos
- 🐹 PokeAPI - Dados de Pokémon
- 🐕 Dog CEO API - Imagens e dados de cães
- 🐙 GitHub API - Dados públicos do GitHub
- 🎉 Public Holidays API - Feriados por país
- 💰 CoinGecko API - Dados de criptomoedas
- 🐱 Cat Facts API - Curiosidades sobre gatos
- 🎂 Age Guess API - Adivinha idade pelo nome
- 👤 Gender Guess API - Adivinha gênero pelo nome
- 📍 IP Info API - Informações de endereço IP

## Como Executar os Testes

### Opção 1: Executar todos os testes
```bash
mvn test
```

### Opção 2: Executar apenas testes de Playwright
```bash
mvn test -Dtest=PlaywrightTest,PlaywrightExternalAPIsTest
```

### Opção 3: Executar um teste específico
```bash
mvn test -Dtest=PlaywrightTest#testJsonPlaceholderAPI
```

### Opção 4: Executar testes com saída detalhada
```bash
mvn test -Dtest=PlaywrightExternalAPIsTest -e
```

## Características dos Testes

✅ **Múltiplas APIs Externas** - Exemplos com diferentes provedores
✅ **Testes de Status HTTP** - Valida respostas 200 OK
✅ **Monitoramento de Rede** - Captura requisições e respostas
✅ **Validação de Conteúdo** - Verifica se dados estão no JSON
✅ **Testes de Performance** - Mede tempo de resposta
✅ **Sem Dependências de Servidor Local** - Usa APIs públicas

## Exemplos de APIs Públicas Grátis

### APIs Testadas no Projeto

| API | URL | Descrição |
|-----|-----|-----------|
| JSONPlaceholder | https://jsonplaceholder.typicode.com | Dados fake para teste |
| REST Countries | https://restcountries.com | Dados de países |
| PokeAPI | https://pokeapi.co | Dados de Pokémon |
| Dog CEO | https://dog.ceo | Imagens e dados de cães |
| GitHub | https://api.github.com | Dados públicos do GitHub |
| CoinGecko | https://api.coingecko.com | Dados de criptomoedas |
| Cat Facts | https://catfact.ninja | Curiosidades sobre gatos |

## Como Adicionar Uma Nova API Externa

```java
@Test
public void testMinhaNovaAPI() {
    String url = "https://api.exemplo.com/dados";
    
    page.navigate(url);
    String response = page.content();
    
    assertNotNull(response);
    assertTrue(response.contains("campo_esperado"));
    
    System.out.println("✓ Minha API - OK");
}
```

## Requisitos de Sistema

- Java 17+
- Maven 3.6+
- Navegador Chromium (instalado automaticamente pelo Playwright)
- Conexão com internet (para acessar APIs externas)

## Troubleshooting

### Erro: "Browser not found"
Execute uma vez:
```bash
mvn test
```
Isso baixará os navegadores necessários.

### API retorna erro 429 (Rate Limit)
Algumas APIs têm limite de requisições. Adicione delay entre testes:
```java
Thread.sleep(1000); // aguarda 1 segundo
```

### Timeout de conexão
Se a API está lenta, aumente o timeout:
```java
page.navigate(url, new Page.NavigateOptions().setTimeout(30000));
```

### SSL Certificate Error
Para testes com APIs HTTPS problemáticas:
```java
context = browser.newContext(new Browser.NewContextOptions().setIgnoreHTTPSErrors(true));
```

## Integração Contínua (CI/CD)

Para usar em pipeline GitHub Actions:
```yaml
- name: Run Playwright Tests
  run: mvn test -Dtest=PlaywrightTest,PlaywrightExternalAPIsTest
```

## Documentação Oficial

- [Playwright Java Documentation](https://playwright.dev/java/)
- [JSONPlaceholder](https://jsonplaceholder.typicode.com)
- [Public APIs List](https://github.com/public-apis/public-apis)

