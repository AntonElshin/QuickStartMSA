package ru.diasoft.micro.subscriber;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.context.annotation.Configuration;
import ru.diasoft.micro.model.SmsDeliveredMessage;
import ru.diasoft.micro.model.SmsVerificationMessage;
import ru.diasoft.micro.publisher.SmsVerificationDeliveredPublishGateway;

@RequiredArgsConstructor
@Configuration
public class SmsVerificationMessageListener {

    private final SmsVerificationDeliveredPublishGateway gateway;

    @StreamListener(ConsumerChannels.SMS_VERIFICATION_MESSAGE)
    public void smsVerificationMessage(SmsVerificationMessage smsVerification) throws InterruptedException {
        Thread.sleep(3000);
        gateway.smsVerificationDelivered(SmsDeliveredMessage.builder().quid(smsVerification.getQuid()).build());
    }

}
