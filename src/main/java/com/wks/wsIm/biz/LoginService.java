package com.wks.wsIm.biz;

import com.wks.wsIm.domain.req.Login;
import com.wks.wsIm.domain.resp.LoginResp;
import com.wks.wsIm.domain.resp.UserResp;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.wks.wsIm.domain.Commands.LOGIN;

@Command(LOGIN)
@Slf4j
public class LoginService extends BaseService<Login, LoginResp> {


    public final static AttributeKey<Session> SESSION = AttributeKey.newInstance("SESSION");


    @Override
    LoginResp process(MsgContext context, Login login) {
        //如果已经登录过，就用之前的userId
        Session lastLogin = context.getChannel().attr(SESSION).get();
        String oldUserId = null;
        if (lastLogin != null) {
            oldUserId = lastLogin.getUser().getUserId();
        }

        log.info("登录=》", login.getUserName());

        //generate userID
        String userId = oldUserId == null ? generateUserId() : oldUserId;

        UserInfo user = new UserInfo(userId, login.getUserName(), context.getChannel());

        UserService.addUserInfo(userId, user);
        context.getChannel().attr(SESSION).set(new Session( user));

        return new LoginResp(userId);
    }


    public static String generateUserId() {
        return UUID.randomUUID().toString();
    }


}
