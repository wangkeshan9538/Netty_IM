package com.wks.wsIm.domain.req;

import lombok.Data;

import java.util.Date;

@Data
public class SendMsg {
    String snedFromID;
    String sendToId;
    Date time;
    String msg;
}
