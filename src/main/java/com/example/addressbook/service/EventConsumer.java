package com.example.addressbook.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class EventConsumer {

    @RabbitListener(queues = "addressbook-events")
    public void consumeMessage(Map<String, Object> message) {
        System.out.println("Received message: " + message);

    }



}
