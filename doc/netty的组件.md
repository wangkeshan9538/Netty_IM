## netty 的组建
* handler
* EventLoopGroup
```
是处理I/O操作的多线程事件循环。 Netty为不同类型的传输提供了各种EventLoopGroup实现。 在此示例中，实现的是服务器端应用程序，因此将使用两个NioEventLoopGroup。
第一个通常称为“boss”，接受传入连接。 第二个通常称为“worker”，当“boss”接受连接并且向“worker”注册接受连接，则“worker”处理所接受连接的流量。
使用多少个线程以及如何将它们映射到创建的通道取决于EventLoopGroup实现，甚至可以通过构造函数进行配置。
```

* ServerBootstrap
* channel
* pipeline
* ChannelFuture
* buffer
* ChannelHandlerContext