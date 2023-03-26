package com.ecommerce.projectd.controllers.dtos.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Builder @Setter
public class GetUserResponse {
    private Long id;
    private String name;
    private String email;
    private String subscriptionArn;
    private String profilePicture;

}
