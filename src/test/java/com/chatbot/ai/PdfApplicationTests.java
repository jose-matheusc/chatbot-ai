package com.chatbot.ai;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mockito;

@SpringBootTest
class PdfApplicationTests {

	@Test
	void contextLoads() {
		//a
	}

	@Test
	void returnsGeneratedTextWhenModelProducesResponse() {
		// Arrange
		dev.langchain4j.model.chat.ChatLanguageModel model = Mockito.mock(dev.langchain4j.model.chat.ChatLanguageModel.class);
		Mockito.when(model.generate("hello")).thenReturn("generated-response");
		com.chatbot.ai.service.IAService service = new com.chatbot.ai.service.IAService(model);

		// Act
		String result = service.ask("hello");

		// Assert
		assertEquals("generated-response", result);
	}

	@Test
	void returnsEmptyStringWhenModelReturnsEmptyForEmptyInput() {
		// Arrange
		dev.langchain4j.model.chat.ChatLanguageModel model = Mockito.mock(dev.langchain4j.model.chat.ChatLanguageModel.class);
		Mockito.when(model.generate("")).thenReturn("");
		com.chatbot.ai.service.IAService service = new com.chatbot.ai.service.IAService(model);

		// Act
		String result = service.ask("");

		// Assert
		assertEquals("", result);
	}

	@Test
	void propagatesExceptionWhenModelThrowsDuringGeneration() {
		// Arrange
		dev.langchain4j.model.chat.ChatLanguageModel model = Mockito.mock(dev.langchain4j.model.chat.ChatLanguageModel.class);
		Mockito.when(model.generate(Mockito.anyString())).thenThrow(new RuntimeException("model-failure"));
		com.chatbot.ai.service.IAService service = new com.chatbot.ai.service.IAService(model);

		// Act & Assert
		RuntimeException ex = assertThrows(RuntimeException.class, () -> service.ask("any"));
		assertEquals("model-failure", ex.getMessage());
	}

}
