package com.ecommerce.projectd.components;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@EnableRabbit
public class PublisherRabbitComponent {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.queue.init}")
    private String queueInit;

    @Value("${rabbitmq.queue.change-system}")
    private String queueChangeSystem;

    @Value("${rabbitmq.queue.change-irrigation}")
    private String queueChangeIrrigation;




    public void sendToInit(String message){
        rabbitTemplate.convertAndSend(queueInit, message);
    }
    public void sendToChangeSystem(String message) {
        rabbitTemplate.convertAndSend(queueChangeSystem, message);
    }
    public void sendToChangeIrrigation(String message){
        rabbitTemplate.convertAndSend(queueChangeIrrigation, message);
    }


}
