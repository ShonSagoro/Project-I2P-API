package com.ecommerce.projectd.controllers;

import com.ecommerce.projectd.services.interfaces.IRabbitPublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("rabbit")
public class RabbitPublisherController {
    @Autowired
    private final IRabbitPublisherService service;

    public RabbitPublisherController(IRabbitPublisherService service) {
        this.service = service;
    }

    @GetMapping("init")
    public Boolean testSendMessage(){
        String message= "soy un mensaje enviado desde nose #"+ ThreadLocalRandom.current().nextInt();
        service.sendToRabbit(message);
        return true;
    }
}
