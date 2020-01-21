package com.wks.wsIm.biz;


import com.wks.wsIm.domain.req.SendMsg;
import com.wks.wsIm.domain.resp.SendResp;
import com.wks.wsIm.domain.resp.User;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;

import static com.wks.wsIm.domain.Commands.SEND_MSSAGE;

@Command(SEND_MSSAGE)
public class SendMessageService extends BaseService<SendMsg, Void> {
    @Override
    Void process(MsgContext context, SendMsg sendMsg) {
        Channel to = (Channel) LoginService.getUserPool().get(new User(sendMsg.getSendToId()));
        Channel from = context.getChannel();
        if (to == null) {
            send(from, new SendResp(false, "对方不在线"));
        }

        transferMsg(from, to, sendMsg, 3);
        return null;
    }

    private void transferMsg(Channel from, Channel to, Object sendMsg, int reTry) {

        if (reTry < 1) {
            send(from, new SendResp(false, "FAIL"));
        }

        send(to, sendMsg).addListener(future -> {
            if (future.isSuccess()) {
                send(from, (new SendResp(true, "SUCCESS")));
            } else {
                transferMsg(from, to, sendMsg, reTry - 1);
            }


        });
    }

    public ChannelFuture send(Channel channel, Object sendMsg) {
        assert channel != null && channel.isWritable() && channel.isActive();
        return channel.writeAndFlush(sendMsg);
    }
}
