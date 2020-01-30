package com.wks.wsIm.biz;

import com.wks.wsIm.domain.resp.UserResp;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


/**
 * 标志 channel 里的用户信息
 */
@Setter
@Getter
public class Session {


    UserInfo user;

    public Session(UserInfo user) {
        this.user = user;
    }
}
