package com.wks.wsIm.domain.resp;

import lombok.Data;

@Data
public class OffLineNotify {
    private String userId;
    private String userName;

    public OffLineNotify(String userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }
}
