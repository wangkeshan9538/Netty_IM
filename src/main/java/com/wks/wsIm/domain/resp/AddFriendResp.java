package com.wks.wsIm.domain.resp;

import lombok.Data;

@Data
public class AddFriendResp extends CommonResp {

    //添加的好友的信息
    private String userId;
    private String userName;

    public AddFriendResp(String success, String reason, String userId, String userName) {
        super(success, reason);
        this.userId = userId;
        this.userName = userName;
    }
}
