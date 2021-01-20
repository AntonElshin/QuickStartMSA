/*
 * Created by DQCodegen
 */
package ru.diasoft.micro.smsverificationdelivered.subscribe;


import ru.diasoft.micro.model.SmsDeliveredMessage;


public interface SmsVerificationDeliveredSubscribeListenerService {

    void smsVerificationDelivered(SmsDeliveredMessage msg);
    
}