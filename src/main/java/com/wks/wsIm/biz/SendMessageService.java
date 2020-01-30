package com.wks.wsIm.biz;


import com.wks.wsIm.domain.Packet;
import com.wks.wsIm.domain.req.SendMsg;
import com.wks.wsIm.domain.resp.ReceiveNotify;
import com.wks.wsIm.domain.resp.SendResp;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import static com.wks.wsIm.domain.Commands.MESSAGE_NOTIFY;
import static com.wks.wsIm.domain.Commands.SEND_MSSAGE;
import static com.wks.wsIm.server.WebSocketFrameHandler.serializer;

@Command(SEND_MSSAGE)
public class SendMessageService extends BaseService<SendMsg, Void> {
    @Override
    Void process(MsgContext context, SendMsg sendMsg) {
        UserInfo to = UserService.getUserPool().get(sendMsg.getSendToId());
        Channel from = context.getChannel();
        if (to == null) {
            SendResp data = new SendResp("FAIL", "对方不在线");
            Packet p = new Packet(SEND_MSSAGE, context.getTraceId(), data);
            send(from, p);
            return null;
        }

        //发送
        Packet p = new Packet(MESSAGE_NOTIFY, null, new ReceiveNotify(sendMsg.getSnedFromID(), sendMsg.getMsg()));
        transferMsg(from, to.getChannel(), p, 3, context.getTraceId());

        return null;
    }

    private void transferMsg(Channel from, Channel to, Packet sendMsg, int reTry, String traceId) {

        if (reTry < 1) {

            SendResp data = new SendResp("FAIL", "发送失败");
            Packet p = new Packet(SEND_MSSAGE, traceId, data);
            send(from, p);
        }


        send(to, sendMsg).addListener(future -> {
            if (future.isSuccess()) {
                SendResp data = new SendResp("SUCCESS", "SUCCESS");
                Packet p = new Packet(SEND_MSSAGE, traceId, data);
                send(from, p);

            } else {
                transferMsg(from, to, sendMsg, reTry - 1, traceId);
            }
        });
    }

    public ChannelFuture send(Channel channel, Packet sendMsg) {
        assert channel != null && channel.isWritable() && channel.isActive();
        return channel.writeAndFlush(new TextWebSocketFrame(serializer.serialize(sendMsg)));
    }
}
