/*
 * Created by DQCodegen
 */
package ru.diasoft.micro.smsverificationmessage.publish;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

import ru.diasoft.micro.model.SmsVerificationMessage;


@MessagingGateway
public interface SmsVerificationMessagePublishGateway {

    @Gateway(requestChannel = SmsVerificationMessagePublishChannelConstants.SMS_VERIFICATION_MESSAGE)
    void smsVerificationMessage(SmsVerificationMessage msg);

}

