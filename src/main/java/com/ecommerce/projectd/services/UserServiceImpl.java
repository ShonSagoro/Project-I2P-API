package com.ecommerce.projectd.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.ecommerce.projectd.controllers.dtos.request.LoginRequest;
import com.ecommerce.projectd.services.interfaces.ISNSService;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.ecommerce.projectd.controllers.advices.exceptions.NotFoundException;
import com.ecommerce.projectd.controllers.dtos.request.CreateUserRequest;
import com.ecommerce.projectd.controllers.dtos.request.UpdateUserRequest;
import com.ecommerce.projectd.controllers.dtos.response.BaseResponse;
import com.ecommerce.projectd.controllers.dtos.response.GetUserResponse;
import com.ecommerce.projectd.entities.User;
import com.ecommerce.projectd.repositories.IUserRepository;
import com.ecommerce.projectd.services.interfaces.IUserService;

@Service
public class UserServiceImpl implements IUserService{

    @Autowired
    private ISNSService snsService;

    @Autowired
    private final IUserRepository repository;

    public UserServiceImpl(IUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public BaseResponse get(LoginRequest request){
        User user = repository.findByEmail(request.getEmail())
                .orElseThrow(NotFoundException::new);
        return BaseResponse.builder()
                .data(from(user))
                .message("user for: " + request.getEmail())
                .success(true)
                .httpStatus(HttpStatus.FOUND).build();
    }

    @Override
    public BaseResponse getAll() {
        List<GetUserResponse> responses = repository
                .findAll()
                .stream()
                .map(this::from).collect(Collectors.toList());
        return BaseResponse.builder()
                .data(responses)
                .message("find all users")
                .success(true)
                .httpStatus(HttpStatus.FOUND).build();
    }

    @Override
    public BaseResponse create(CreateUserRequest request) {
        Optional<User> possibleCopy = repository.findByEmail(request.getEmail());

        if(possibleCopy.isPresent()){
            throw new RuntimeException("the user exist"); // (RegisterException)
        }

        User user = repository.save(from(request));
        GetUserResponse response= from(user);

        String subscriptionArn= snsService.subscribeAnUserWithEmail(user.getEmail());
        add(subscriptionArn, response);

        return BaseResponse.builder()
                .data(response)
                .message("The user has been created")
                .success(true)
                .httpStatus(HttpStatus.CREATED).build();
    }

    @Override
    public BaseResponse update(UpdateUserRequest request, Long idUser) {
        User usr = repository.findById(idUser).orElseThrow(RuntimeException::new);
        User upUser = repository.save(update(usr, request));
        return BaseResponse.builder()
                .data(from(upUser))
                .message("user updated")
                .success(true)
                .httpStatus(HttpStatus.ACCEPTED).build();
    }

    @Override
    public User getUser(String email) {
        return repository.findByEmail(email)
                .orElseThrow(RuntimeException::new);
    }

    @Override
    public User getUser(Long id) {
        return repository.findById(id)
                .orElseThrow(RuntimeException::new);
    }


    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }


    private  void add( String subscriptionArn, GetUserResponse response){
        response.setSubscriptionArn(subscriptionArn);
    }

    private GetUserResponse from(User user){
        return GetUserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
            .profilePicture(user.getProfilePicture()).build();
    }

    private User from(CreateUserRequest request){
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(new BCryptPasswordEncoder().encode(request.getPassword()));
        user.setProfilePicture("");
        return user;
    }

    private User update(User user, UpdateUserRequest update){
        user.setName(update.getName());
        user.setEmail(update.getEmail());
        user.setProfilePicture(update.getProfilePicture());
        return user;
    }

    
}
