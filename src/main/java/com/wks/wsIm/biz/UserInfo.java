package com.wks.wsIm.biz;

import com.google.common.collect.Sets;
import io.netty.channel.Channel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
public class UserInfo {

    //岂不是所有的牵扯并发访问的字段，都得加volatile
    private volatile String userId;
    private volatile String userName;
    private volatile Channel channel;
    private final Set<UserInfo> friends= Sets.newConcurrentHashSet();


    public UserInfo(String userId, String userName, Channel channel) {
        this.userId = userId;
        this.userName = userName;
        this.channel = channel;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof UserInfo) && ((UserInfo) obj).userId.equals(this.userId);
    }
}
