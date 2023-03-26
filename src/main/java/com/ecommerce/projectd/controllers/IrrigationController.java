package com.ecommerce.projectd.controllers;


import com.ecommerce.projectd.controllers.dtos.request.CreateIrrigationRequest;
import com.ecommerce.projectd.controllers.dtos.response.BaseResponse;
import com.ecommerce.projectd.services.interfaces.IIrrigationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("irrigation")
public class IrrigationController {
    @Autowired
    private IIrrigationService service;


    @GetMapping("list")
    public ResponseEntity<BaseResponse> list(){
        BaseResponse baseResponse= service.list();
        return new ResponseEntity<>(baseResponse, baseResponse.getHttpStatus());
    }

    @GetMapping("system/{id}/risks")
    public ResponseEntity<BaseResponse>  getRiskByIdUser(@PathVariable long id){
        BaseResponse baseResponse= service.getRisksByIdSystem(id);
        return new ResponseEntity<>(baseResponse, baseResponse.getHttpStatus());
    }

    @GetMapping("{id}")
    public ResponseEntity<BaseResponse> get(@PathVariable long id){
        BaseResponse baseResponse= service.get(id);
        return new ResponseEntity<>(baseResponse, baseResponse.getHttpStatus());
    }

    @PostMapping("/create/{moistureState}/{previousMoistureState}/{irrigationSystemId}")
    public ResponseEntity<BaseResponse> create(@PathVariable Integer moistureState,@PathVariable Integer previousMoistureState, @PathVariable Long irrigationSystemId ){
        CreateIrrigationRequest request=from(moistureState, previousMoistureState, irrigationSystemId);
        BaseResponse baseResponse= service.create(request);
        return new ResponseEntity<>(baseResponse, baseResponse.getHttpStatus());
    }


    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id){
        service.delete(id);
    }

    @GetMapping("health")
    public String health() {
        return "Ok";
    }


    private  CreateIrrigationRequest from(Integer moistureState,Integer previousMoistureState,  Long irrigationSystemId ){
        CreateIrrigationRequest request= new CreateIrrigationRequest();
        request.setMoistureState(moistureState);
        request.setIrrigationSystemId(irrigationSystemId);
        request.setPreviousMoistureState(previousMoistureState);
        return  request;
    }
}
