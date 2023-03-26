package com.ecommerce.projectd.controllers.dtos.response;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class GetIrrigationSystemResponse {

    private Long Id;

    private String model;

    private GetUserResponse user;
}
