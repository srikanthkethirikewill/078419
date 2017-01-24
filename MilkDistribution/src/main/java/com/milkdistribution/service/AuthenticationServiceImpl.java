package com.milkdistribution.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.MessageAttributeValue;
import com.amazonaws.services.sns.model.PublishRequest;
import com.milkdistribution.dao.AuthenticationDAO;
import com.milkdistribution.entity.Authentication;



@Service("authenticationService")
@Transactional(propagation=Propagation.REQUIRED,readOnly=false)
public class AuthenticationServiceImpl implements AuthenticationService{
	
	@Autowired
	AuthenticationDAO authenticationDAO;
	@Override
	public void save(Authentication authentication) {
		// TODO Auto-generated method stub
		AmazonSNSClient snsClient = new AmazonSNSClient();
        
        String phoneNumber = "+91"+authentication.getMobileNumber();
        List<Integer> numbers = new ArrayList<Integer>();
        for(int i = 0; i < 10; i++){
            numbers.add(i);
        }

        Collections.shuffle(numbers);

        String result = "";
        for(int i = 0; i < 4; i++){
            result += numbers.get(i).toString();
        }
        Map<String, MessageAttributeValue> smsAttributes = 
                new HashMap<String, MessageAttributeValue>();
        
        String message = "Your OTP for Milk Donor Application is "+result;
        authentication.setOtp(result);
        snsClient.publish(new PublishRequest().withMessage(message).withPhoneNumber(phoneNumber).withMessageAttributes(smsAttributes));
		authenticationDAO.save(authentication);
	}

	@Override
	public Authentication getAuthentication(String mobileNo) {
		// TODO Auto-generated method stub
		return authenticationDAO.getAuthentication(mobileNo);
	}

	@Override
	public void update(Authentication authentication) {

		AmazonSNSClient snsClient = new AmazonSNSClient();
        
        String phoneNumber = "+91"+authentication.getMobileNumber();
        List<Integer> numbers = new ArrayList<Integer>();
        for(int i = 0; i < 10; i++){
            numbers.add(i);
        }

        Collections.shuffle(numbers);

        String result = "";
        for(int i = 0; i < 4; i++){
            result += numbers.get(i).toString();
        }
        Map<String, MessageAttributeValue> smsAttributes = 
                new HashMap<String, MessageAttributeValue>();
        
        String message = "Your OTP for Milk Donor Application is "+result;
        authentication.setOtp(result);
        snsClient.publish(new PublishRequest().withMessage(message).withPhoneNumber(phoneNumber).withMessageAttributes(smsAttributes));
		authenticationDAO.update(authentication);
	}

}
