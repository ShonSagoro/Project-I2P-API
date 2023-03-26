package com.ecommerce.projectd.controllers.dtos.response;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class GetIrrigationResponse {
    private Long id;

    private Integer previousMoistureState;

    private Integer moistureState;

    private String date;

    private String notificationId;

    private GetIrrigationSystemResponse irrigationSystemResponse;
}
