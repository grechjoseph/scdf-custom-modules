package com.jg.scdfcustomprocessor.channels;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface Channels {

    /* Outbound */
    String OUTPUT = "output";

    /* Inbound */
    String INPUT = "input";

    /* Outbound */
    @Output(OUTPUT)
    MessageChannel output();

    /* Inbound*/
    @Input(INPUT)
    SubscribableChannel input();

}
