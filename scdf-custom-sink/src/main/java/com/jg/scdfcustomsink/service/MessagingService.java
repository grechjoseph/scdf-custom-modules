package com.jg.scdfcustomsink.service;

import com.jg.scdfcustomsink.config.MyProperties;
import com.jg.scdfcustomsink.dto.MyMessage;
import com.jg.scdfcustomsink.queues.SinkOutput;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@EnableBinding({ Sink.class, SinkOutput.class})
public class MessagingService {

    private final MyProperties myProperties;
    private final SinkOutput sinkOutput;

    @ServiceActivator(inputChannel = Sink.INPUT)
    public void handleMessage(final Message<MyMessage> message) {
        log.info("Received message with value: {}", message.getPayload());
        sendReceivedMessageId(message.getPayload().getId());
    }

    /**
     * Published acknowledgement message which is expected to be consumed by scdf-custom-source.
     * @param messageId The ID of the message to acknowledge.
     */
    public void sendReceivedMessageId(final UUID messageId) {
        sinkOutput.output().send(MessageBuilder.withPayload(messageId).build());
        log.info("Acknowledgement for message with ID '{}' sent.", messageId);
    }

}
