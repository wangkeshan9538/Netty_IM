package com.wks.wsIm.domain.req;

import lombok.Data;

@Data
public class AddFriendReq {
    //操作人
    private String userId;
    //添加的userId
    private String addId;
}
