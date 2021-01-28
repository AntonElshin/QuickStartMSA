package ru.diasoft.micro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.integration.annotation.IntegrationComponentScan;
import ru.diasoft.micro.publisher.ProducerChannels;
import ru.diasoft.micro.subscriber.ConsumerChannels;

@SpringBootApplication
@EnableBinding({ConsumerChannels.class, ProducerChannels.class})
@IntegrationComponentScan
public class SmsAdapterApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmsAdapterApplication.class, args);
    }

}
