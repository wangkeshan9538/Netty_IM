package com.wks.wsIm.domain.resp;

import lombok.Data;

@Data
public class UserResp {

    String userId;
    String userName;

    public UserResp(String userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    public UserResp(String userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof UserResp) && ((UserResp) obj).userId.equals(this.userId);
    }

}
