package com.ecommerce.projectd.services.interfaces;

import com.ecommerce.projectd.controllers.dtos.request.CreateIrrigationRequest;
import com.ecommerce.projectd.controllers.dtos.response.BaseResponse;

public interface IIrrigationService {

    BaseResponse create(CreateIrrigationRequest request);

    BaseResponse get(Long id);

    BaseResponse list();

    BaseResponse getRisksByIdUser(Long userId);

    void delete(Long id);
}
