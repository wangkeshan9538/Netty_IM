package com.wks.wsIm.biz;

import io.netty.channel.Channel;
import lombok.Data;

@Data
public class MsgContext {
    private Channel channel;
}
