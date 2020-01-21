package com.wks.wsIm.domain.resp;

import lombok.Data;

@Data
public class SendResp {

    boolean succss;
    String reason;

    public SendResp(boolean succss, String reason) {
        this.succss = succss;
        this.reason = reason;
    }
}
