package com.wks.customProtocol.packet.data;

import com.wks.customProtocol.packet.Command;
import lombok.Data;

@Data
public class MessageResponseData implements EncodeData {

    private String fromUserId;

    private String fromUserName;


    private String MessageResponse;

    public MessageResponseData(String fromUserId, String fromUserName, String messageResponse) {
        this.fromUserId = fromUserId;
        this.fromUserName = fromUserName;
        MessageResponse = messageResponse;
    }

    @Override
    public byte getCommand() {
        return Command.MESSGAE_RESPONSE;
    }
}
