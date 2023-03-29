package com.ecommerce.projectd.services;


import com.ecommerce.projectd.controllers.dtos.request.CreateIrrigationRequest;
import com.ecommerce.projectd.controllers.dtos.response.BaseResponse;
import com.ecommerce.projectd.controllers.dtos.response.GetIrrigationResponse;
import com.ecommerce.projectd.controllers.dtos.response.GetIrrigationSystemResponse;
import com.ecommerce.projectd.controllers.dtos.response.GetUserResponse;
import com.ecommerce.projectd.entities.Irrigation;
import com.ecommerce.projectd.entities.IrrigationSystem;
import com.ecommerce.projectd.entities.User;
import com.ecommerce.projectd.entities.projection.IrrigationProjection;
import com.ecommerce.projectd.repositories.IIrrigationRepository;
import com.ecommerce.projectd.services.interfaces.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class IrrigationServiceImpl implements IIrrigationService {

    @Autowired
    private IIrrigationRepository repository;

    @Autowired
    private IIrrigationSystemService irrigationSystemService;

    @Autowired
    private ISNSService snsService;


    @Autowired
    private IRabbitPublisherService rabbitPublisherService;

    @Override
    public BaseResponse create(CreateIrrigationRequest request){
        Irrigation irrigation=from(request);
        GetIrrigationResponse response=from(repository.save(irrigation));

        String notificationId=snsService.sendNotificationOfIrrigation();
        add(notificationId, response);

        rabbitPublisherService.sendChangeIrrigationToRabbit(String.valueOf(response.getIrrigationSystemResponse().getId()));

        return BaseResponse.builder()
                .data(response)
                .message("The irrigation has been saved")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.CREATED)
                .build();
    }

    @Override
    public BaseResponse get(Long id) {
        GetIrrigationResponse response=from(id);
        return BaseResponse.builder()
                .data(response)
                .message("The irrigation has been found")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.FOUND)
                .build();
    }

    @Override
    public BaseResponse list() {
        List<GetIrrigationResponse> response=repository.findAll()
                .stream()
                .map(this::from)
                .collect(Collectors.toList());

        return  BaseResponse.builder()
                .data(response)
                .message("the risks have been got")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.FOUND)
                .build();
    }

    @Override
    public BaseResponse getRisksByIdSystem(Long systemId) {
        List<GetIrrigationResponse> response=repository.findRisksBySystemId(systemId)
                .stream()
                .map(this::from)
                .collect(Collectors.toList());

        return  BaseResponse.builder()
                .data(response)
                .message("the risks have been got by user Id")
                .success(Boolean.TRUE)
                .httpStatus(HttpStatus.OK)
                .build();
    }

    @Override
    public void delete(Long id){
        repository.deleteById(id);
    }

    private  void add(String notificationId, GetIrrigationResponse response){
        response.setNotificationId(notificationId);
    }

    private GetIrrigationResponse from(Long id){
        return repository.findById(id).
                map(this::from)
                .orElseThrow(()-> new RuntimeException("The Irrigation does not exist"));
    }

    private GetIrrigationResponse from(IrrigationProjection projection){
        GetIrrigationResponse response= new  GetIrrigationResponse();
        response.setId(projection.getId());
        response.setDate(projection.getDate());
        response.setPreviousMoistureState(projection.getPrevious_Moisture_State());
        response.setMoistureState(projection.getMoisture_State());
        response.setIrrigationSystemResponse(from(irrigationSystemService.getIrrigationSystem(projection.getIrrigation_System_Id())));
        return response;
    }
    private GetIrrigationResponse from(Irrigation irrigation){
        GetIrrigationResponse response= new GetIrrigationResponse();
        response.setId(irrigation.getId());
        response.setMoistureState(irrigation.getMoistureState());
        response.setDate(irrigation.getDate());
        response.setPreviousMoistureState(irrigation.getPreviousMoistureState());
        response.setIrrigationSystemResponse(from(irrigation.getIrrigationSystem()));
        return response;
    }

    private GetIrrigationSystemResponse from(IrrigationSystem irrigationSystem){
        GetIrrigationSystemResponse response= new GetIrrigationSystemResponse();
        response.setId(irrigationSystem.getId());
        response.setModel(irrigationSystem.getModel());
        response.setUser(from(irrigationSystem.getUser()));
        return response;
    }

    private GetUserResponse from(User user){
        return GetUserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .profilePicture(user.getProfilePicture()).build();
    }



    private Irrigation from(CreateIrrigationRequest request){
        Irrigation irrigation= new Irrigation();
        irrigation.setDate(getDate());
        irrigation.setMoistureState(request.getMoistureState());
        irrigation.setPreviousMoistureState(request.getPreviousMoistureState());
        irrigation.setIrrigationSystem(irrigationSystemService.getIrrigationSystem(request.getIrrigationSystemId()));
        return irrigation;
    }

    private String getDate(){
        LocalDateTime dateNow= LocalDateTime.now();
        return dateNow.format(getFormat());
    }

    private DateTimeFormatter getFormat(){
        return DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm:ss");
    }


}
