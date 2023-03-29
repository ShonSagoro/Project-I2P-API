package com.ecommerce.projectd.configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.amazonaws.regions.Regions;
import org.springframework.context.annotation.Primary;

@Configuration
public class AwsSnsConfig {

    @Value("${aws.access-key-sns}")
    private String accessKeyId;

    @Value("${aws.secret-key-sns}")
    private String secretAccessKey;

    @Primary
    @Bean
    public AmazonSNSClient getSnsClient() {
        return (AmazonSNSClient) AmazonSNSClientBuilder.standard().withRegion(Regions.US_EAST_2)
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKeyId,
                        secretAccessKey)))
                .build();
    }
}