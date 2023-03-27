package com.ecommerce.projectd.services.interfaces;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface IRabbitPublisherService {
    void sendToRabbit(String message);

    void sendChangeIrrigationToRabbit(String message);
    void sendChangeSystemToRabbit(String message);
}
