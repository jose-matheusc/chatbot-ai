package com.chatbot.ai.service;

import com.chatbot.ai.config.RabbitConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class PedidoConsumer {

    private final PdfService pdfService;
    private final OllamaService ollamaService;

    public PedidoConsumer(PdfService pdfService, OllamaService ollamaService) {
        this.ollamaService = ollamaService;
        this.pdfService = pdfService;
    }

    /**
     * Main processing method for orders coming from RabbitMQ.
     * Uses the chat service, generates a styled PDF containing the AI response,
     * and returns the same response as the method result.
     */
    @RabbitListener(queues = RabbitConfig.QUEUE)
    public String processOrder(String message) throws IOException {
        String response = ollamaService.chat(message);

        String path = "C:\\Users\\Maria Angela\\Documents\\aa\\pedido_" + System.currentTimeMillis() + ".pdf";
        new File(path).getParentFile().mkdirs();

        pdfService.createPdf(response, path);
        return response;
    }

    /**
     * Backward-compatible alias in PT-BR. Prefer using {@link #processOrder(String)}.
     */
    @Deprecated
    public String processarPedido(String mensagem) throws IOException {
        return processOrder(mensagem);
    }
}
