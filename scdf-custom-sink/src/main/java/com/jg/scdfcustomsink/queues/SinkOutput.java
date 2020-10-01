package com.jg.scdfcustomsink.queues;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface SinkOutput {

    String OUTPUT = "sink-output";

    @Output(OUTPUT)
    MessageChannel output();

}
