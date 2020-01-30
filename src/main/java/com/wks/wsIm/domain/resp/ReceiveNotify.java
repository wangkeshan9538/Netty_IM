package com.wks.wsIm.domain.resp;

import lombok.Data;

@Data
public class ReceiveNotify {

    private String fromId;

    private String msg;

    public ReceiveNotify(String fromId, String msg) {
        this.fromId = fromId;
        this.msg = msg;
    }
}
