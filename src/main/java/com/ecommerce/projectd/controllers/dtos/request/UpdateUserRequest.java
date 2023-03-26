package com.ecommerce.projectd.controllers.dtos.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Builder
public class UpdateUserRequest {
    private String name;
    private String email;
    private String profilePicture;
}
