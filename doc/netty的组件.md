## netty 的组件
* handler
* EventLoopGroup
```
是处理I/O操作的多线程事件循环。 Netty为不同类型的传输提供了各种EventLoopGroup实现。 在此示例中，实现的是服务器端应用程序，因此将使用两个NioEventLoopGroup。
第一个通常称为“boss”，接受传入连接。 第二个通常称为“worker”，当“boss”接受连接并且向“worker”注册接受连接，则“worker”处理所接受连接的流量。
使用多少个线程以及如何将它们映射到创建的通道取决于EventLoopGroup实现，甚至可以通过构造函数进行配置。
```

* ServerBootstrap

    
* channel

    与NIO中的概念一样,代表一个连接,
* pipeline

    pipeline() 返回的是和这条连接相关的逻辑处理链，采用了责任链模式
    pipline 的链中顺序对channel 和message 进行处理
* ChannelFuture

* buffer

* ChannelHandlerContext


