package com.ecommerce.projectd.controllers.dtos.request;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class CreateIrrigationRequest {
    private Integer previousMoistureState;

    private Integer moistureState;
    
    private Long irrigationSystemId;
    
}
