package com.ecommerce.projectd.services;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.ecommerce.projectd.controllers.dtos.request.UpdateUserRequest;
import com.ecommerce.projectd.controllers.dtos.response.BaseResponse;
import com.ecommerce.projectd.entities.User;
import com.ecommerce.projectd.services.interfaces.IAWSService;
import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

@Service
public class AWSServiceImpl implements IAWSService {
    
    private AmazonS3 s3Client;

    @Value(value = "${aws.endpoint-url-s3}")
    private String endpointUrl;

    @Value(value = "${aws.bucket-name}")
    private String bucketName;

    @Value(value = "${aws.secret-key-s3}")
    private String secretKey;

    @Value(value = "${aws.access-key-s3}")
    private String accessKey;

    @Autowired
    private final UserServiceImpl userService;


    public AWSServiceImpl(UserServiceImpl userService){
        this.userService = userService;
    };

    @Override
    public BaseResponse uploadProfilePicture(MultipartFile multipartFile, Long idUser) {
        User user = userService.getUser(idUser);
        String fileUrl;

        try{

            File file = convertMultipartToFile(multipartFile);
            String finalPath = generateFileName(multipartFile);
            fileUrl = "https://" + bucketName + "." + endpointUrl + "/" + finalPath;
            uploadFileTos3Bucket(finalPath, file);
            user.setProfilePicture(fileUrl);
            userService.update(from(user), user.getId());
            file.delete();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return BaseResponse.builder()
                .data(fileUrl)
                .message("The profile picture was uploaded")
                .httpStatus(HttpStatus.CREATED)
                .success(true).build();
    }

    private File convertMultipartToFile(MultipartFile file) throws IOException {
        File convFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        try(FileOutputStream fos = new FileOutputStream(convFile)){
            fos.write(file.getBytes());
        }catch (Exception e){
            throw new RuntimeException();
        }
        return convFile;
    }
    @PostConstruct
    private void initializeAmazon() {
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        s3Client = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.US_EAST_2)
                .build();
    }

    private String generateFileName(MultipartFile multipartFile) {
        return Objects
                .requireNonNull(multipartFile.getOriginalFilename())
                .replace(" ", "_");
    }

    private void uploadFileTos3Bucket(String fileName, File file) {
        s3Client.putObject(new PutObjectRequest(bucketName, fileName, file)
                .withCannedAcl(CannedAccessControlList.PublicRead));
    }

    private UpdateUserRequest from(User user){
        return UpdateUserRequest.builder()
                .name(user.getName())
                .email(user.getEmail())
                .profilePicture(user.getProfilePicture())
                .build();
    }
}
