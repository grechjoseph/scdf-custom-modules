package com.jg.scdfcustomsink.channels;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface Channels {

    /* Outbound */
    String SINKOUTPUT = "sink-output";

    /* Inbound */
    String INPUT = "input";

    /* Outbound */
    @Output(SINKOUTPUT)
    MessageChannel sinkOutputOuput();

    /* Inbound*/
    @Input(INPUT)
    SubscribableChannel input();

}
