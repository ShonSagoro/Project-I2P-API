package com.ecommerce.projectd.services;

import com.ecommerce.projectd.components.PublisherRabbitComponent;
import com.ecommerce.projectd.services.interfaces.IRabbitPublisherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RabbitPublisherServiceImpl implements IRabbitPublisherService {

    @Autowired
    private final PublisherRabbitComponent publisher;

    public RabbitPublisherServiceImpl(PublisherRabbitComponent publisher) {
        this.publisher = publisher;
    }

    @Override
    public void sendToRabbit(String message) {
        log.info("Message '{}', will be send...", message);
        publisher.sendToInit(message);
    }

    @Override
    public void sendChangeIrrigationToRabbit(String message) {
        log.info("Message '{}', will be send...", message);
        publisher.sendToChangeIrrigation(message);
    }

    @Override
    public void sendChangeSystemToRabbit(String message){
        log.info("Message '{}', will be send...", message);
        publisher.sendToChangeSystem(message);
    }
}
