package com.ecommerce.projectd.controllers;


import com.ecommerce.projectd.controllers.dtos.request.CreateIrrigationSystemRequest;
import com.ecommerce.projectd.controllers.dtos.response.BaseResponse;
import com.ecommerce.projectd.services.interfaces.IIrrigationSystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("system")
public class IrrigationSystemController {

    @Autowired
    private IIrrigationSystemService service;

    @GetMapping("list")
    public ResponseEntity<BaseResponse> list(){
        BaseResponse baseResponse= service.list();
        return new ResponseEntity<>(baseResponse, baseResponse.getHttpStatus());
    }

    @GetMapping("user/{id}/systems")
    public ResponseEntity<BaseResponse>  getIrrigationSystemsByUserId(@PathVariable long id){
        BaseResponse baseResponse= service.getIrrigationSystemsByUserId(id);
        return new ResponseEntity<>(baseResponse, baseResponse.getHttpStatus());
    }

    @PostMapping("/create")
    public ResponseEntity<BaseResponse> create(@RequestBody CreateIrrigationSystemRequest request){
        BaseResponse baseResponse= service.create(request);
        return new ResponseEntity<>(baseResponse, baseResponse.getHttpStatus());
    }
    @GetMapping("{id}")
    public ResponseEntity<BaseResponse> get(@PathVariable long id){
        BaseResponse baseResponse= service.get(id);
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
}
