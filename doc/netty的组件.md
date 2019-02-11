## netty 的组件
* handler
```apple js
第一个子接口是 ChannelInboundHandler，从字面意思也可以猜到，他是处理读数据的逻辑，比如，我们在一端读到一段数据，首先要解析这段数据，然后对这些数据做一系列逻辑处理，最终把响应写到对端， 在开始组装响应之前的所有的逻辑，都可以放置在 ChannelInboundHandler 里处理，它的一个最重要的方法就是 channelRead()。读者可以将 ChannelInboundHandler 的逻辑处理过程与 TCP 的七层协议的解析联系起来，收到的数据一层层从物理层上升到我们的应用层。

第二个子接口 ChannelOutBoundHandler 是处理写数据的逻辑，它是定义我们一端在组装完响应之后，把数据写到对端的逻辑，比如，我们封装好一个 response 对象，接下来我们有可能对这个 response 做一些其他的特殊逻辑，然后，再编码成 ByteBuf，最终写到对端，它里面最核心的一个方法就是 write()，读者可以将 ChannelOutBoundHandler 的逻辑处理过程与 TCP 的七层协议的封装过程联系起来，我们在应用层组装响应之后，通过层层协议的封装，直到最底层的物理层。

为什么 read 方法中msg 类型是 Object，因为pipeline 采用的是责任链模型，上一个msg 会传入到下一个handler，所以第一个handler的实例其实是byteBuffer，而后面的handler的msg 取决于前面的handler传入类型
```

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
    继承于future，
* buffer

* ChannelHandlerContext


