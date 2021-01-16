package ru.diasoft.task.subscriber;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface ConsumerChannels {

    String DEMO = "demo";

    @Input(DEMO)
    SubscribableChannel demo();

}
