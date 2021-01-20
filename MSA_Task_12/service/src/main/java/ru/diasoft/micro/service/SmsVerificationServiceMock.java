/*
 * Created by DQCodegen
 */
package ru.diasoft.micro.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.diasoft.micro.domain.SmsVerificationCheckRequest;
import ru.diasoft.micro.domain.SmsVerificationCheckRequestMock;
import ru.diasoft.micro.domain.SmsVerificationCheckResponse;
import ru.diasoft.micro.domain.SmsVerificationCheckResponseMock;
import ru.diasoft.micro.domain.SmsVerificationPostRequest;
import ru.diasoft.micro.domain.SmsVerificationPostRequestMock;
import ru.diasoft.micro.domain.SmsVerificationPostResponse;
import ru.diasoft.micro.domain.SmsVerificationPostResponseMock;

@Service
public class SmsVerificationServiceMock implements SmsVerificationService {

    @Override
    public SmsVerificationCheckResponse dsSmsVerificationCheck(
            SmsVerificationCheckRequest smsVerificationCheckRequest) { 
        return new SmsVerificationCheckResponseMock();  
    } 

    @Override
    public SmsVerificationPostResponse dsSmsVerificationCreate(
            SmsVerificationPostRequest smsVerificationPostRequest) { 
        return new SmsVerificationPostResponseMock();  
    } 

}

