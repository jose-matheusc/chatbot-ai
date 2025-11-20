package com.chatbot.ai.controller;

import com.chatbot.ai.service.PedidoProducer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final PedidoProducer pedidoProducer;

    public OrderController(PedidoProducer pedidoProducer) {
        this.pedidoProducer = pedidoProducer;
    }

    @PostMapping("/public/send")
    public ResponseEntity<String> sendOrder() {
        pedidoProducer.enviarPedido();
        return ResponseEntity.ok("Order sent successfully!");
    }
}
