package com.wks.wsIm.domain.resp;

import lombok.Data;

@Data
public class AddFriendNotify {
    private String userId;
    private String userName;

    public AddFriendNotify(String userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }
}
