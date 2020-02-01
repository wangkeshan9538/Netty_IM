package com.wks.wsIm.domain.resp;

import lombok.Data;

@Data
public class ReceiveNotify {

    private String fromId;
    private String fromName;
    private String msg;

    public ReceiveNotify(String fromId, String fromName, String msg) {
        this.fromId = fromId;
        this.fromName = fromName;
        this.msg = msg;
    }
}
