package com.jg.scdfcustomsource.channels;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface Channels {

    /**
     * This channel is overridden by Spring Cloud Data Flow when the application is deployed to a Stream.
     * Otherwise, the dev profile's yaml is used to override this to a custom exchange and queue.
     */
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
