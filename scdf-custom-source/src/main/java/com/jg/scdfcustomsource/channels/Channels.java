package com.jg.scdfcustomsource.channels;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface Channels {

    /* Outbound */
    String OUTPUT = "output";

    /* Inbound */
    String SINKOUTPUT = "sink-output";

    /* Outbound */
    @Output(OUTPUT)
    MessageChannel output();

    /* Inbound*/
    @Input(SINKOUTPUT)
    SubscribableChannel sinkOutputInput();

}
