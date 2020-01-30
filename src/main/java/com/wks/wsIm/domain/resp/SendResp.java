package com.wks.wsIm.domain.resp;

import lombok.Data;

public class SendResp extends CommonResp{


    public SendResp(String success, String reason) {
        super(success, reason);
    }
}
