package ru.diasoft.task.subscriber;

import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TaskStreamListeners {

    @StreamListener(ConsumerChannels.DEMO)
    public void printMessage(String message) {
        System.out.println("In demo listener: " + message);
    }
}
