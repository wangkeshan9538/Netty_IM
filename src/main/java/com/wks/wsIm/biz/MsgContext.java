package com.wks.wsIm.biz;

import io.netty.channel.Channel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


/**
 * 作为路由和 nettyHandler之间调用的一个上下文
 */
@Getter
@Setter
public class MsgContext {
    private String traceId;
    private Channel channel;
}
