package com.wks.wsIm.biz;

import com.wks.wsIm.domain.resp.User;
import lombok.Data;


/**
 * 标志 channel 里的用户信息
 */
@Data
public class Session {

    boolean isLogin;

    User user;

    public Session(boolean isLogin, User user) {
        this.isLogin = isLogin;
        this.user = user;
    }
}
