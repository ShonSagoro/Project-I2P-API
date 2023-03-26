package com.ecommerce.projectd.services;

import com.ecommerce.projectd.controllers.dtos.request.CreateIrrigationSystemRequest;
import com.ecommerce.projectd.controllers.dtos.response.BaseResponse;

import com.ecommerce.projectd.controllers.dtos.response.GetIrrigationSystemResponse;
import com.ecommerce.projectd.controllers.dtos.response.GetUserResponse;
import com.ecommerce.projectd.entities.IrrigationSystem;
import com.ecommerce.projectd.entities.User;
import com.ecommerce.projectd.entities.projection.IrrigationSystemProjection;
import com.ecommerce.projectd.repositories.IIrrigationSystemRepository;
import com.ecommerce.projectd.services.interfaces.IIrrigationSystemService;
import com.ecommerce.projectd.services.interfaces.IRabbitPublisherService;
import com.ecommerce.projectd.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IrrigationSystemServiceImpl implements IIrrigationSystemService {
    @Autowired
    private IIrrigationSystemRepository repository;

    @Autowired
    private IUserService userService;

    @Autowired
    private IRabbitPublisherService rabbitPublisherService;

    @Override
    public BaseResponse create(CreateIrrigationSystemRequest request) {

        IrrigationSystem irrigationSystem= from(request);
        GetIrrigationSystemResponse response = from(repository.save(irrigationSystem));

        rabbitPublisherService.sendChangeSystemToRabbit(response.getId());
        return BaseResponse.builder()
                .data(response)
                .message("The irrigation has been created correctly")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.CREATED).build();

    }

    @Override
    public BaseResponse get(Long id) {
        GetIrrigationSystemResponse response=from(id);

        return BaseResponse.builder()
                .data(response)
                .message("The irrigation has been found")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.FOUND).build();
    }

    @Override
    public IrrigationSystem getIrrigationSystem(Long id) {
        return repository.findById(id)
                .orElseThrow(()-> new RuntimeException("The irrigation was never done"));
    }

    @Override
    public BaseResponse list() {
        List<GetIrrigationSystemResponse> response=repository
                .findAll()
                .stream()
                .map(this::from).
                collect(Collectors.toList());

        return BaseResponse.builder()
                .data(response)
                .message("The risks have been found")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.FOUND).build();
    }

    @Override
    public BaseResponse getIrrigationSystemsByUserId(Long userId) {
        List<GetIrrigationSystemResponse> responses= repository.findRisksByUserId(userId)
                .stream()
                .map(this::from)
                .collect(Collectors.toList());

        return BaseResponse.builder()
                .data(responses)
                .message("The risks made by Irrigation System have been found")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.FOUND).build();
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    private GetIrrigationSystemResponse from(Long id){
        GetIrrigationSystemResponse response=repository.findById(id)
                .map(this::from)
                .orElseThrow(()-> new RuntimeException("The irrigation was never done"));
        return response;
    }

    private GetIrrigationSystemResponse from(IrrigationSystem irrigationSystem){
        GetIrrigationSystemResponse response= new GetIrrigationSystemResponse();
        response.setId(irrigationSystem.getId());
        response.setModel(irrigationSystem.getModel());
        response.setUser(from(irrigationSystem.getUser()));
        return  response;
    }


    private GetUserResponse from(User user){
        return GetUserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .profilePicture(user.getProfilePicture()).build();
    }

    private IrrigationSystem from(CreateIrrigationSystemRequest request){
        IrrigationSystem irrigationSystem = new IrrigationSystem();
        irrigationSystem.setModel(request.getModel());
        irrigationSystem.setUser(userService.getUser(request.getUserId()));
        return irrigationSystem;

    }

    private GetIrrigationSystemResponse from(IrrigationSystemProjection projection){
        GetIrrigationSystemResponse response= new GetIrrigationSystemResponse();
        response.setId(projection.getId());
        response.setModel(projection.getModel());
        response.setUser(from(userService.getUser(projection.getUser_Id())));
        return response;
    }

}
