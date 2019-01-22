package com.wks.netty_test;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

public class NettyServer {
    public static void main(String[] args) {
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        serverBootstrap
                .group(boss, worker)
                .attr(AttributeKey.newInstance("serverName"), "nettyServer")
                .childAttr(AttributeKey.newInstance("clientKey"), "clientValue")
                .channel(NioServerSocketChannel.class)
                .handler(new ChannelInitializer<NioServerSocketChannel>() {
                    protected void initChannel(NioServerSocketChannel ch) throws Exception {
                        /**
                         * childHandler()用于指定处理新连接数据的读写处理逻辑，handler()用于指定在服务端启动过程中的一些逻辑，通常情况下呢，我们用不着这个方法。
                         */
                    }
                })

                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    protected void initChannel(NioSocketChannel ch) {
                        //ch.pipeline().addLast(new StringDecoder());
                        /*ch.pipeline().addLast(new SimpleChannelInboundHandler<String>() { // 4
                            @Override
                            protected void channelRead0(ChannelHandlerContext ctx, String msg) {
                                System.out.println(msg);
                            }
                        });*/

                        ch.pipeline().addLast(new FirstServerHandler());
                    }
                });
        bind(serverBootstrap, 8080);
    }


    private static void bind(final ServerBootstrap serverBootstrap, final int port) {
        serverBootstrap.bind(port).addListener(new GenericFutureListener<Future<? super Void>>() {
            public void operationComplete(Future<? super Void> future) {
                if (future.isSuccess()) {
                    System.out.println("端口[" + port + "]绑定成功!");
                } else {
                    System.err.println("端口[" + port + "]绑定失败!");
                    bind(serverBootstrap, port + 1);
                }
            }
        });
    }
}


/**
 * 此处指定的处理程序将始终由新接受的通道计算。 ChannelInitializer是一个特殊的处理程序，用于帮助用户配置新的通道。
 * 很可能要通过添加一些处理程序(例如DiscardServerHandler)来配置新通道的ChannelPipeline来实现您的网络应用程序。
 * 随着应用程序变得复杂，可能会向管道中添加更多处理程序，并最终将此匿名类提取到顶级类中。
 **/


/* attr()方法可以给服务端的 channel，也就是NioServerSocketChannel指定一些自定义属性，
                然后我们可以通过channel.attr()取出这个属性，比如，上面的代码我们指定我们服务端channel的一个serverName属性，属性值为nettyServer，
                其实说白了就是给NioServerSocketChannel维护一个map而已，通常情况下，我们也用不上这个方法。*/

/* childAttr可以给每一条连接指定自定义属性，然后后续我们可以通过channel.attr()取出该属性。*/

/* childOption()可以给每条连接设置一些TCP底层相关的属性，比如上面，我们设置了两种TCP属性，其中 */


/* 可以给服务端channel设置一些属性 */