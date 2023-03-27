package com.ecommerce.projectd.services.interfaces;

import com.ecommerce.projectd.controllers.dtos.request.CreateIrrigationRequest;
import com.ecommerce.projectd.controllers.dtos.response.BaseResponse;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface IIrrigationService {

    BaseResponse create(CreateIrrigationRequest request);

    BaseResponse get(Long id);

    BaseResponse list();

    BaseResponse getRisksByIdSystem(Long systemId);

    void delete(Long id);
}
