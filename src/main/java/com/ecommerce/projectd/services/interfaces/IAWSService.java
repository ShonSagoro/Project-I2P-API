package com.ecommerce.projectd.services.interfaces;

import com.ecommerce.projectd.controllers.dtos.response.BaseResponse;
import org.springframework.web.multipart.MultipartFile;

public interface IAWSService {
    BaseResponse uploadProfilePicture(MultipartFile multipartFile, Long id);
}
