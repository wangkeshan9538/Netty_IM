package com.wks.wsIm.biz;

import com.wks.wsIm.Util.GodChannel;
import com.wks.wsIm.domain.Packet;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static com.wks.wsIm.biz.UserService.GOD_ID;
import static com.wks.wsIm.server.WebSocketFrameHandler.serializer;

@Slf4j
public class WriteService {
    public static ChannelFuture send( Channel channel, Packet sendMsg) {

        if (channel instanceof GodChannel){
            return channel.writeAndFlush(serializer.serialize(sendMsg));
        }

        if (!(channel != null && channel.isWritable() && channel.isActive())) {
            throw new RuntimeException("用户不存在");
        }

        return channel.writeAndFlush(new TextWebSocketFrame(serializer.serialize(sendMsg)));
    }

    public static void spead(Packet p, List<UserInfo> users) {
        users.forEach((u) -> {
            try {
                send(u.getChannel(), p);
            } catch (Exception e) {
                //ignore
                log.info("广播失败");
            }
        });
    }
}
