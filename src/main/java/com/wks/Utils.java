package com.wks;

import com.wks.server.Session;
import io.netty.channel.Channel;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 偷个懒 把所有的常量和 常用方法 放在一起
 */
public class Utils {

    //登录相关code
    public final static Integer LOGIN_SUCCESS = 1000;

    public final static AttributeKey<Boolean> LOGIN_STATUS = AttributeKey.newInstance("LOGIN");


    public final static AttributeKey<Session> SESSION = AttributeKey.newInstance("SESSION");


    private static final Map<String, Channel> userIdChannelMap = new ConcurrentHashMap<>();


    public static void markLogin(Channel channel) {
        channel.attr(LOGIN_STATUS).set(true);
    }

    /**
     * 用于客户端判断是否登录
     *
     * @param channel
     * @return
     */
    public static boolean clientHasLogin(Channel channel) {
        Attribute<Boolean> loginAttr = channel.attr(LOGIN_STATUS);

        return loginAttr.get() != null;
    }


    public static void bindSession(Session session, Channel channel) {
        userIdChannelMap.put(session.getUserId(), channel);
        channel.attr(SESSION).set(session);
    }

    public static void unBindSession(Channel channel) {
        if (getSession(channel) != null) {
            userIdChannelMap.remove(getSession(channel).getUserId());
            channel.attr(SESSION).set(null);
        }
    }


    public static Session getSession(Channel channel) {

        return channel.hasAttr(SESSION) ? channel.attr(SESSION).get() : null;
    }

    public static Channel getChannel(String userId) {

        return userIdChannelMap.get(userId);
    }
}
