package com.ecommerce.projectd.services.interfaces;


public interface IRabbitPublisherService {
    void sendToRabbit(String message);

    void sendChangeIrrigationToRabbit(String message);
    
    void sendChangeSystemToRabbit(String message);
}
