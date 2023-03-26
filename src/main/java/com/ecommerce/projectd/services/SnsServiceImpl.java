package com.ecommerce.projectd.services;

import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import com.amazonaws.services.sns.model.SubscribeRequest;
import com.amazonaws.services.sns.model.SubscribeResult;
import com.ecommerce.projectd.services.interfaces.ISNSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SnsServiceImpl implements ISNSService {
    @Value(value = "${aws.topic-arn-sns}")
    private String snsTopicArn;

    @Autowired
    private AmazonSNSClient snsClient;

    @Override
    public String sendNotificationOfIrrigation() {
        String message = buildMessageBody();
        PublishRequest publishRequest = new PublishRequest(snsTopicArn, message);
        PublishResult result = snsClient.publish(publishRequest);
        return result.getMessageId();
    }

    @Override
    public String subscribeAnUserWithEmail(String email) {
        SubscribeRequest subscribeRequest = new SubscribeRequest(snsTopicArn, "email", email);
        SubscribeResult subscribeResult = snsClient.subscribe(subscribeRequest);
        return subscribeResult.getSubscriptionArn();
    }

    private String buildMessageBody() {
       return  "Querido Cliente ,\n" +
                "\n" +
                "\n" +
                "Es de mi agrado informale que su planta ha sido regada."+"\n"+
                "Para mas información visite la pagina web, gracias";
    }
}
