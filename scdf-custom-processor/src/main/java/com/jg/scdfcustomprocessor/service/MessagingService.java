package com.jg.scdfcustomprocessor.service;

import com.jg.scdfcustomprocessor.config.MyProperties;
import com.jg.scdfcustomprocessor.dto.MyMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.integration.annotation.Transformer;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@EnableBinding(Processor.class)
public class MessagingService {

    private final MyProperties myProperties;

    @Transformer(inputChannel = Processor.INPUT, outputChannel = Processor.OUTPUT)
    public Message<MyMessage> transform(Message<MyMessage> message) {
        log.info("Received message: {}", message.getPayload().toString());
        message.getPayload().setValue(
                message.getPayload().getValue() + "-" + myProperties.getAppendValue()
        );
        log.info("Modified message as: {}", message.getPayload().toString());
        return message;
    }

}
