package com.wks.wsIm.biz;

import com.wks.wsIm.domain.Packet;
import com.wks.wsIm.domain.resp.OffLineNotify;
import com.wks.wsIm.domain.resp.UserResp;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.wks.wsIm.biz.LoginService.SESSION;
import static com.wks.wsIm.domain.Commands.GET_USER_LIST;
import static com.wks.wsIm.domain.Commands.OFFLINE_NOTIFY;

@Slf4j
public class OffLineService {

    public static void process(ChannelHandlerContext ctx) {
        //删除session 和 用户信息
        Session session = ctx.channel().attr(SESSION).get();
        if (session == null || session.getUser() == null) return;
        log.info(session.getUser().getUserName() + "退出登录");
        UserInfo user = UserService.removeUser(session.getUser().getUserId());
        user.setStatus("0");
        //删除好友关系
        user.getFriends().forEach((v)->{v.getFriends().remove(user);});


        //广播用户列表
        List<UserResp> users = GetUserListService.getOnlineUsers();
        List<UserInfo> userInfos = UserService.getAllUsers();
        Packet p = new Packet(GET_USER_LIST, null, users);
        WriteService.spead(p, userInfos);

        //广播下线通知
        OffLineNotify notify = new OffLineNotify(user.getUserId(), user.getUserName());
        Packet offLinePacket = new Packet(OFFLINE_NOTIFY, null, notify);
        WriteService.spead(offLinePacket, new ArrayList<>(user.getFriends()));
    }
}
