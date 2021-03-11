package com.duckydoc.utenti.rabbit;

import com.duckydoc.utenti.model.Utente;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Runner {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private TopicExchange exchange;

    public void sendMessage(Utente utente) {
        rabbitTemplate.convertAndSend(exchange.getName(), "foo.bar.baz", utente.getId() + "-" + utente.getName() + " " + utente.getSurname());
        System.out.println("message sent...");
    }
}
