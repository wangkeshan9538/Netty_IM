package com.wks.packet.data;

import com.wks.packet.Command;
import lombok.Data;

@Data
public class MessageResponseData implements EncodeData{
    private String MessageResponse;

    public MessageResponseData(String messageResponse) {
        MessageResponse = messageResponse;
    }

    @Override
    public byte getCommand() {
        return Command.MESSGAE_RESPONSE;
    }
}
