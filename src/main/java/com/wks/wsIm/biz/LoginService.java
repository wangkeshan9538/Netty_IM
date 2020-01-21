package com.wks.wsIm.biz;

import com.wks.wsIm.domain.req.Login;
import com.wks.wsIm.domain.resp.LoginResp;
import com.wks.wsIm.domain.resp.User;
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

    private final static Map<User, Channel> USER_POOL = new HashMap<>();

    public final static AttributeKey<Session> SESSION = AttributeKey.newInstance("SESSION");

    @Override
    LoginResp process(MsgContext context, Login login) {
        //如果已经登录过，就用之前的userId
        Session lastLogin=context.getChannel().attr(SESSION).get();
        String oldUserId = null;
        if(lastLogin!=null){
            oldUserId=lastLogin.getUser().getUserId();
        }

        log.info("登录=》", login.getUserName());

        //generate userID
        String userId = oldUserId==null?generateUserId():oldUserId;

        User user = new User(userId, login.getUserName());

        USER_POOL.put(user, context.getChannel());
        context.getChannel().attr(SESSION).set(new Session(true, user));

        return new LoginResp(userId);
    }


    public static String generateUserId() {
        return UUID.randomUUID().toString();
    }

    public static Map getUserPool() {
        return USER_POOL;
    }
}
