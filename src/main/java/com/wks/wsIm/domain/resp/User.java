package com.wks.wsIm.domain.resp;

import lombok.Data;

@Data
public class User {

    String userId;
    String userName;

    public User(String userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    public User(String userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof User) && ((User) obj).userId.equals(this.userId);
    }

}
