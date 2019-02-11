package com.wks.packet.data;

import com.wks.packet.Command;
import lombok.Data;

@Data
public class MessageRequestData implements EncodeData{
    private String message;

    public MessageRequestData(String message) {
        this.message = message;
    }

    @Override
    public byte getCommand() {
        return Command.MESSAGE_REQUEST;
    }
}
