package com.wks.packet.data;

import lombok.Data;

@Data
public class MessageRequestData {
    private String message;

    public MessageRequestData(String message) {
        this.message = message;
    }
}
