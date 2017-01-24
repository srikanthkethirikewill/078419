package com.milkdistribution.service;

import java.util.HashMap;
import java.util.Map;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.CreateTopicRequest;
import com.amazonaws.services.sns.model.CreateTopicResult;
import com.amazonaws.services.sns.model.MessageAttributeValue;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import com.amazonaws.services.sns.model.SubscribeRequest;
import com.amazonaws.services.sns.model.SubscribeResult;

public class AmazonSendSms {
	public static void main(String[] args) {
		AmazonSNSClient snsClient = new AmazonSNSClient();
        String message = "My SMS message";
        String phoneNumber = "+919885930967";
        Map<String, MessageAttributeValue> smsAttributes = 
                new HashMap<String, MessageAttributeValue>();
        
        //<set SMS attributes>
        String topicArn = "arn:aws:sns:us-west-2:257447068958:SMSTopic";//createSNSTopic(snsClient);
        snsClient.setRegion(Region.getRegion(Regions.US_WEST_2));
        //subscribeToTopic(snsClient,topicArn, "sms", phoneNumber);
        sendSMSMessage(snsClient, message, phoneNumber, smsAttributes, topicArn);
	}
	public static String createSNSTopic(AmazonSNSClient snsClient) {
	    CreateTopicRequest createTopic = new CreateTopicRequest("mySNSTopic");
	    CreateTopicResult result = snsClient.createTopic(createTopic);
	    System.out.println("Create topic request: " + 
	        snsClient.getCachedResponseMetadata(createTopic));
	    System.out.println("Create topic result: " + result);
	    return result.getTopicArn();
	}
	public static void subscribeToTopic(AmazonSNSClient snsClient, String topicArn, 
			String protocol, String endpoint) {	
	        SubscribeRequest subscribe = new SubscribeRequest(topicArn, protocol,
	                                                          endpoint);
	        SubscribeResult subscribeResult = snsClient.subscribe(subscribe);
	        System.out.println("Subscribe request: " + 
	                snsClient.getCachedResponseMetadata(subscribe));
	        System.out.println("Subscribe result: " + subscribeResult);
	}
	public static void sendSMSMessage(AmazonSNSClient snsClient, String message, 
			String phoneNumber, Map<String, MessageAttributeValue> smsAttributes,String topicArn) {
	        PublishResult result = snsClient.publish(new PublishRequest()
	        				//.withTopicArn(topicArn)
	                        .withMessage(message)
	                        .withPhoneNumber(phoneNumber)
	                        .withMessageAttributes(smsAttributes));
	        System.out.println(result); // Prints the message ID.
	}

}
