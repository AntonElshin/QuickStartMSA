package ru.diasoft.micro.smsverificationdelivered.subscribe;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.diasoft.micro.model.SmsDeliveredMessage;
import ru.diasoft.micro.model.SmsDeliveredMessage;
import ru.diasoft.micro.repository.SmsVerificationRepository;
import ru.diasoft.micro.smsverificationdelivered.subscribe.SmsVerificationDeliveredSubscribeListenerService;

@RequiredArgsConstructor
@Service
@Primary
public class SmsVerificationDeliveredSubscribeListenerPrimaryService
        implements SmsVerificationDeliveredSubscribeListenerService {

    private final SmsVerificationRepository repository;

    @Override
    public void smsVerificationDelivered(SmsDeliveredMessage msg) {
        repository.updateStatusByProcessGuid("OK", msg.getQuid());
    }
}
