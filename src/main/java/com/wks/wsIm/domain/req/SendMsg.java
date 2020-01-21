package com.wks.wsIm.domain.req;

import lombok.Data;

@Data
public class SendMsg {
    String snedFromID;
    String sendToId;
    String msg;
}
