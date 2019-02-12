package com.wks.client;

import com.wks.Utils;
import com.wks.packet.Command;
import com.wks.packet.Packet;
import com.wks.packet.data.LoginRequestData;
import com.wks.packet.data.LoginResponseData;
import com.wks.serializer.SerializerAlgorithm;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class LoginHandler extends SimpleChannelInboundHandler<LoginResponseData> {


    @Override
    public void channelActive(ChannelHandlerContext ctx) {

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponseData msg) throws Exception {
        // 验证
        if (msg.getIsSuccessful()) {
            System.out.println("登录成功");

            //唤醒输入线程
            synchronized (client.waitForLoginEnd){
                client.waitForLoginEnd.notify();
            }

            Utils.markLogin(ctx.channel());
        } else {
            System.out.println("登录失败" + "错误代码：" + msg.getCode());
        }
    }


    public static void main(String[] args) throws InterruptedException {

        Object o = new Object();

        final Thread a = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("ss");

                synchronized (o) {
                    try {
                        o.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                System.out.println("bb");
            }
        });
        a.start();
        Thread.sleep(1000);
        Thread b = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (o) {
                    System.out.println("唤醒");
                    o.notify();
                    System.out.println("end");
                }
            }
        });
        b.start();

    }

}
