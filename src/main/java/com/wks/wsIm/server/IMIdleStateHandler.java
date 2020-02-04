package com.wks.wsIm.server;

import com.wks.wsIm.biz.Session;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

import static com.wks.wsIm.biz.LoginService.SESSION;

@Slf4j
public class IMIdleStateHandler extends IdleStateHandler {

    private static final int READER_IDLE_TIME = 30;

    public IMIdleStateHandler() {
        super(READER_IDLE_TIME, 0, 0, TimeUnit.MINUTES);
    }

    @Override
    protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) {
        Channel c=ctx.channel();
        Session s=c.attr(SESSION).get();
        if(s!=null &&s.getUser()!=null){
            log.info("关闭空闲连接"+ s.getUser().getUserName());
        }else{
            log.info("关闭空闲连接");
        }
        ctx.channel().close();
    }
}