package com.jg.scdfcustomsource.service;

import com.jg.scdfcustomsource.channels.Channels;
import com.jg.scdfcustomsource.config.MyProperties;
import com.jg.scdfcustomsource.dto.MyMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Service
@EnableScheduling
@RequiredArgsConstructor
@EnableBinding({ Source.class, Channels.class })
public class MessagingService {

    private Set<MyMessage> messagesSent = new HashSet<>();
    private final MyProperties myProperties;
    private final Source source;

    /**
     * Send a MyMessage object to the Stream. Recommend flow: source -> processor -> sink.
     */
    @Scheduled(fixedDelay = 5_000L)
    public void sendMessage() {
        log.info("Sending message with value: {}", myProperties.getInitialValue());
        final MyMessage message = MyMessage.builder().value(myProperties.getInitialValue()).build();
        source.output().send(MessageBuilder.withPayload(message).build());
        log.info("Message sent successfully.");
        messagesSent.add(message);
    }

    /**
     * Listens to this queue for acknowledgement that MyMessage object with the argument UUID has been received.
     * Expected to be published by scdf-custom-sink.
     * @param message The message from the queue.
     */
    @ServiceActivator(inputChannel = Channels.SINKOUTPUT)
    public void handleMessage(final Message<UUID> message) {
        log.info("Received acknowledgement message with value: {}", message.getPayload());
        messagesSent.remove(messagesSent.stream().filter(m -> m.getId().equals(message.getPayload())).findAny().orElse(null));
        log.info("messagesSent collection cleaned. Remaining: {}", messagesSent.size());
    }

}
