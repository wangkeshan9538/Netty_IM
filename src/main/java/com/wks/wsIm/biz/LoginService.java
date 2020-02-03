package com.wks.wsIm.biz;

import com.wks.wsIm.domain.Packet;
import com.wks.wsIm.domain.req.Login;
import com.wks.wsIm.domain.resp.LoginResp;
import com.wks.wsIm.domain.resp.SendResp;
import com.wks.wsIm.domain.resp.UserResp;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

import static com.wks.wsIm.biz.WriteService.send;
import static com.wks.wsIm.domain.Commands.GET_USER_LIST;
import static com.wks.wsIm.domain.Commands.LOGIN;
import static com.wks.wsIm.domain.Commands.SEND_MSSAGE;

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

        log.info("登录=》"+ login.getUserName());

        //generate userID
        String userId = oldUserId == null ? generateUserId() : oldUserId;

        UserInfo user = new UserInfo(userId, login.getUserName(), context.getChannel(),"1");

        UserService.addUserInfo(userId, user);
        context.getChannel().attr(SESSION).set(new Session(user));

        //广播用户列表
        List<UserResp> users= GetUserListService.getOnlineUsers();
        List<UserInfo> userInfos=UserService.getAllUsers();
        Packet p=new Packet(GET_USER_LIST,null,users);
        WriteService.spead(p,userInfos);


        return new LoginResp(userId);
    }


    public static String generateUserId() {
        return UUID.randomUUID().toString();
    }


}
