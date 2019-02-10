package com.wks;

import io.netty.channel.Channel;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;

public class Utils {

    //登录相关code
    public final static Integer LOGIN_SUCCESS = 1000;

    public final static AttributeKey<Boolean> LOGIN_STATUS = AttributeKey.newInstance("login");



    public static void markAsLogin(Channel channel) {
        channel.attr(LOGIN_STATUS).set(true);
    }

    public static boolean hasLogin(Channel channel) {
        Attribute<Boolean> loginAttr = channel.attr(LOGIN_STATUS);

        return loginAttr.get() != null;
    }
}
