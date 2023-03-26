package com.ecommerce.projectd.services.interfaces;

public interface IRabbitPublisherService {
    void sendToRabbit(String message);

    void sendChangeIrrigationToRabbit(Object message);
    void sendChangeSystemToRabbit(Object message);
}
