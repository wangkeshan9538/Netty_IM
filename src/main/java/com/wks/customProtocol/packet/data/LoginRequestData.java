package com.wks.customProtocol.packet.data;


import com.wks.customProtocol.packet.Command;
import lombok.Data;

@Data
public class LoginRequestData implements EncodeData{

    private String userId;

    private String username;

    private String password;

    public LoginRequestData(String userId, String username, String password) {
        this.userId = userId;
        this.username = username;
        this.password = password;
    }

    @Override
    public byte getCommand() {
        return Command.LOGIN_REQUEST;
    }
}