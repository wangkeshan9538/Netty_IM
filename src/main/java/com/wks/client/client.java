package com.wks.client;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import com.wks.Utils;
import com.wks.codec.PacketDecoder;
import com.wks.codec.PacketEncoder;
import com.wks.codec.Spliter;
import com.wks.packet.Command;
import com.wks.packet.Packet;
import com.wks.packet.data.LoginRequestData;
import com.wks.packet.data.MessageRequestData;
import com.wks.serializer.SerializerAlgorithm;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.util.AttributeKey;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class client {
    public static void main(String[] args) throws InterruptedException {
        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup();

        bootstrap.group(group)
                .attr(AttributeKey.newInstance("clientName"), "nettyClient")
                .channel(NioSocketChannel.class)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel ch) {


                        ch.pipeline().addLast(new Spliter());

                        ch.pipeline().addLast(new PacketDecoder());
                        ch.pipeline().addLast(new LoginHandler());
                        ch.pipeline().addLast(new MessageHandler());

                        ch.pipeline().addLast(new PacketEncoder());
                    }
                });
        connect(bootstrap, "localhost", 8080, MAX_RETRY);
    }

    final static int MAX_RETRY = 5;

    private static void connect(Bootstrap bootstrap, String host, int port, int retry) {
        bootstrap.connect(host, port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("连接成功!");
                startConsoleThread(((ChannelFuture) future).channel());
            } else if (retry == 0) {
                System.err.println("重试次数已用完，放弃连接！");
            } else {
                // 第几次重连
                int order = (MAX_RETRY - retry) + 1;
                // 本次重连的间隔
                int delay = 1 << order;
                bootstrap.config().group().schedule(() -> connect(bootstrap, host, port, retry - 1), delay, TimeUnit
                        .SECONDS);
            }
        });
    }


    public static Object waitForLoginEnd = new Object();

    private static void startConsoleThread(Channel channel) {
        System.out.println("开始监控用户输入");
        Scanner sc = new Scanner(System.in);
        new Thread(() -> {
            while (!Thread.interrupted()) {
                if (!Utils.clientHasLogin(channel)) {
                    System.out.print("输入userID userName Password 登录: ");

                    String userId = sc.next();
                    String userName = sc.next();
                    String passWord = sc.next();

                    LoginRequestData data = new LoginRequestData(userId, userName, passWord);
                    channel.writeAndFlush(data);

                    //阻塞
                    synchronized (waitForLoginEnd) {
                        try {
                            waitForLoginEnd.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("登录成功，可以发送");
                } else {
                    String toUserId = sc.next();
                    String message = sc.next();
                    channel.writeAndFlush(new MessageRequestData(toUserId, message));

                    /*if(line.equals("exit")){
                        channel.close();
                        System.exit(0);
                    }*/
                }
            }
        }).start();
    }
}
