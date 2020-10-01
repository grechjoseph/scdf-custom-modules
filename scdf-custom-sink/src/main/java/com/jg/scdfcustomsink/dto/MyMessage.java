package com.jg.scdfcustomsink.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class MyMessage {

    @Builder.Default
    private UUID id = UUID.randomUUID();

    private String value;

}
