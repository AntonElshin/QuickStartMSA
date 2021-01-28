package ru.diasoft.micro.publisher;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

import ru.diasoft.micro.model.SmsDeliveredMessage;

@MessagingGateway
public interface SmsVerificationDeliveredPublishGateway {

    @Gateway(requestChannel = ProducerChannels.SMS_VERIFICATION_DELIVERED)
    void smsVerificationDelivered(SmsDeliveredMessage msg);

}
