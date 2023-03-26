package com.ecommerce.projectd.components;

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




    public void sendToInit(Object message){
        rabbitTemplate.convertAndSend(queueInit, message);
    }
    public void sendToChangeSystem(Object message){
        rabbitTemplate.convertAndSend(queueChangeSystem, message);
    }
    public void sendToChangeIrrigation(Object message){
        rabbitTemplate.convertAndSend(queueChangeIrrigation, message);
    }
}
