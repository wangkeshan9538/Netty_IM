package com.wks.packet.data;


import lombok.Data;

@Data
public class LoginPacketData {

    private Integer userId;

    private String username;

    private String password;

    public LoginPacketData(Integer userId, String username, String password) {
        this.userId = userId;
        this.username = username;
        this.password = password;
    }
}