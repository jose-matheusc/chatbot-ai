package com.chatbot.ai.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

	public static final String QUEUE = "fila.pedidos";
	public static final String EXCHANGE = "exchange.pedidos";
	public static final String ROUTING_KEY = "pedidos.criados";

	@Bean
	public Queue queue() {
		return new Queue(QUEUE, true);
	}

	@Bean
	public DirectExchange exchange() {
		return new DirectExchange(EXCHANGE);
	}

	@Bean
	public Binding binding(Queue queue, DirectExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
	}
}
