package com.jg.scdfcustomsource.queues;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface SinkOutput {

    String INPUT = "sink-output";

    @Input(INPUT)
    SubscribableChannel input();

}
