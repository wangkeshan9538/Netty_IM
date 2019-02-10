package com.wks.packet.data;

import lombok.Data;

@Data
public class MessageResponseData {
    private String MessageResponse;

    public MessageResponseData(String messageResponse) {
        MessageResponse = messageResponse;
    }
}
