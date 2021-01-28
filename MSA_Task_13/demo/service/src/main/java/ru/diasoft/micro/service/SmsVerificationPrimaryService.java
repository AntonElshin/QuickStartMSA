package ru.diasoft.micro.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.diasoft.micro.Status;
import ru.diasoft.micro.domain.*;
import ru.diasoft.micro.model.*;
import ru.diasoft.micro.smsverificationmessage.publish.SmsVerificationMessagePublishGateway;
import ru.diasoft.micro.repository.SmsVerificationRepository;
import ru.diasoft.micro.service.SmsVerificationService;

import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Primary
public class SmsVerificationPrimaryService implements SmsVerificationService {

    private final SmsVerificationRepository repository;
    private final SmsVerificationMessagePublishGateway publishGateway;

    @Override
    public SmsVerificationCheckResponse dsSmsVerificationCheck(SmsVerificationCheckRequest smsVerificationCheckRequest) {
        Optional<SmsVerification> verification = repository.findBySecretCodeAndProcessGuidAndStatus(
                smsVerificationCheckRequest.getCode()
                ,smsVerificationCheckRequest.getProcessGUID()
                , Status.OK.name()
        );

        return new SmsVerificationCheckResponse(verification.isPresent());
    }

    @Override
    public SmsVerificationPostResponse dsSmsVerificationCreate(SmsVerificationPostRequest smsVerificationPostRequest) {

        String guid = UUID.randomUUID().toString();
        String secredCode = String.format("%04d", new Random().nextInt(100000));
        SmsVerification smsVerification = SmsVerification.builder()
                .phoneNumber(smsVerificationPostRequest.getPhoneNumber())
                .processGuid(guid)
                .secretCode(secredCode)
                .status(Status.WAITING.name()).build();

        repository.save(smsVerification);

        publishGateway.smsVerificationMessage(SmsVerificationMessage.builder().quid(smsVerification.getProcessGuid()).build());

        return new SmsVerificationPostResponse(guid);

    }


}
