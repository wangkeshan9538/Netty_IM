package com.wks.wsIm.domain.resp;


import lombok.Data;

@Data
public class CommonResp {
    private  String success;
    private  String reason;

    public CommonResp(String success, String reason) {
        this.success = success;
        this.reason = reason;
    }
}
