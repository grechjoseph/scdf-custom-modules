package com.jg.scdfcustomsink.channels;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface Channels {

    /* Outbound */
    String SINKOUTPUT = "sink-output";

    /**
     * This channel is overridden by Spring Cloud Data Flow when the application is deployed to a Stream.
     * Otherwise, the dev profile's yaml is used to override this to a custom exchange and queue.
     */
    /* Inbound */
    String INPUT = "input";

    /* Outbound */
    @Output(SINKOUTPUT)
    MessageChannel sinkOutputOuput();

    /* Inbound*/
    @Input(INPUT)
    SubscribableChannel input();

}
