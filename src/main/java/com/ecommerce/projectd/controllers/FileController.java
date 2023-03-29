package com.ecommerce.projectd.controllers;

import com.ecommerce.projectd.controllers.dtos.response.BaseResponse;
import com.ecommerce.projectd.services.interfaces.IAWSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("file")
public class FileController {

    @Autowired
    private final IAWSService awsService;

    public FileController(IAWSService awsServiceImpl){
        this.awsService = awsServiceImpl;
    }

    @PostMapping("/profile/{idUser}")
    public ResponseEntity<BaseResponse> uploadUserProfilePicture(MultipartFile file,
                                                                 @PathVariable Long idUser){
        BaseResponse response = awsService.uploadProfilePicture(file, idUser);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @GetMapping("health")
    public String health() {
        return "Ok";
    }

}

