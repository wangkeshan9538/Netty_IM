package com.wks.packet.data;

import lombok.Data;

@Data
public class LoginResponseData {

    private Boolean isSuccessful;

    private Integer code;

    public LoginResponseData(Boolean isSuccessful, Integer code) {
        this.isSuccessful = isSuccessful;
        this.code = code;
    }


}
