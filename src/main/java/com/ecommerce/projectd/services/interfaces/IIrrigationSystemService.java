package com.ecommerce.projectd.services.interfaces;

import com.ecommerce.projectd.controllers.dtos.request.CreateIrrigationSystemRequest;
import com.ecommerce.projectd.controllers.dtos.response.BaseResponse;
import com.ecommerce.projectd.entities.IrrigationSystem;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface IIrrigationSystemService {


    BaseResponse create(CreateIrrigationSystemRequest request);

    BaseResponse get(Long id);

    BaseResponse list();

    BaseResponse getIrrigationSystemsByUserId(Long userId);
    void delete(Long id);

    IrrigationSystem getIrrigationSystem(Long id);

}
