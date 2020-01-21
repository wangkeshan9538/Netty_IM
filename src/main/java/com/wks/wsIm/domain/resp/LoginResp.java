package com.wks.wsIm.domain.resp;


import lombok.Data;

@Data
public class LoginResp {
    private String userId;

    public LoginResp(String userId) {
        this.userId = userId;
    }
}
