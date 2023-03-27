package com.ecommerce.projectd.services.interfaces;

import com.ecommerce.projectd.controllers.dtos.request.CreateUserRequest;
import com.ecommerce.projectd.controllers.dtos.request.LoginRequest;
import com.ecommerce.projectd.controllers.dtos.request.UpdateUserRequest;
import com.ecommerce.projectd.controllers.dtos.response.BaseResponse;
import com.ecommerce.projectd.entities.User;

public interface IUserService {

    BaseResponse get(LoginRequest email);
    
    BaseResponse getAll();
    
    BaseResponse create(CreateUserRequest request);
    
    BaseResponse update(UpdateUserRequest request, Long 
    idUer);
    
    User getUser(String email);

    User getUser(Long id);
    
    void delete(Long id);
}
