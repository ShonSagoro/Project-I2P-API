package com.ecommerce.projectd.components;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ConsumerRabbitComponent {
    @RabbitListener(queues = {"${rabbitmq.queue.change-irrigation}"})
    private void received(@Payload String message){
        log.info("Received message {}", message);

    }

    private void makeSlow() throws InterruptedException {
      Thread.sleep(5000);
    }
}
