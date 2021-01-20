/*
 * Created by DQCodegen
 */
package ru.diasoft.micro.smsverificationmessage.publish;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import ru.diasoft.micro.model.SmsVerificationMessage;

import ru.diasoft.micro.model.SmsVerificationMessageMock;


@RestController("mock-ctlr-smsVerificationMessage")
@Api(tags = {"mock-ctlr-task"})
public class SmsVerificationMessagePublishMockController  {

    @Autowired
    private SmsVerificationMessagePublishGateway gateway;

    @GetMapping (value = "/mock/smsVerificationMessage")
    @ApiOperation(value = "smsVerificationMessage", tags = {"mock-ctlr-task"})
    public ResponseEntity<SmsVerificationMessage> smsVerificationMessage() {
         SmsVerificationMessage mock = new SmsVerificationMessageMock();
         gateway.smsVerificationMessage(mock);
         return ResponseEntity.status(HttpStatus.OK).body(mock);
     }

}

