# Chatbot AI Service

This project is a Spring Boot application for processing questions via AI, using Langchain4j with Ollama, RabbitMQ for messaging, and JWT for authentication.

## Features
- REST endpoint for AI questions
- Integration with Ollama model (Langchain4j)
- Flexible security configuration (permitAll)
- RabbitMQ and JWT configuration via environment variables
- Automated tests for the AI service

## Technologies
- Java 17+
- Spring Boot
- Langchain4j
- Ollama
- RabbitMQ
- JWT (JSON Web Token)
- Maven

## Getting Started

### Prerequisites
- Java 17 or higher
- Maven
- Docker (for RabbitMQ and Ollama)

### Setup
1. Clone the repository:
   ```
   git clone <your-repo-url>
   ```
2. Start RabbitMQ with Docker Compose:
   ```
   docker-compose up -d
   ```
3. Start Ollama locally:
   ```
   ollama serve
   ollama pull qwen2.5:0.5b
   ```
4. Set environment variables for RabbitMQ and JWT as specified in `src/main/resources/application.yml`.
5. Build the project:
   ```
   mvn clean install
   ```
6. Run the application:
   ```
   mvn spring-boot:run
   ```

## Endpoints

### AI
- `GET /public/ia/perguntar?q=text` - Sends a question to the AI model

## Configuration
- Configuration is in `src/main/resources/application.yml`.
- Set RabbitMQ and JWT via environment variables.
- The AI model is configured in `LangchainConfig.java` to use local Ollama.

## Tests
Run tests with:
```
mvn test
```

## License
This project is licensed under the MIT License.

## Contact
For questions or support, contact the maintainer at: <josematheus.profissional@gmail.com>
