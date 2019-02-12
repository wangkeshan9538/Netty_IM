## netty 的组件
* handler
```apple js
第一个子接口是 ChannelInboundHandler，从字面意思也可以猜到，他是处理读数据的逻辑，比如，我们在一端读到一段数据，首先要解析这段数据，然后对这些数据做一系列逻辑处理，最终把响应写到对端， 在开始组装响应之前的所有的逻辑，都可以放置在 ChannelInboundHandler 里处理，它的一个最重要的方法就是 channelRead()。读者可以将 ChannelInboundHandler 的逻辑处理过程与 TCP 的七层协议的解析联系起来，收到的数据一层层从物理层上升到我们的应用层。

第二个子接口 ChannelOutBoundHandler 是处理写数据的逻辑，它是定义我们一端在组装完响应之后，把数据写到对端的逻辑，比如，我们封装好一个 response 对象，接下来我们有可能对这个 response 做一些其他的特殊逻辑，然后，再编码成 ByteBuf，最终写到对端，它里面最核心的一个方法就是 write()，读者可以将 ChannelOutBoundHandler 的逻辑处理过程与 TCP 的七层协议的封装过程联系起来，我们在应用层组装响应之后，通过层层协议的封装，直到最底层的物理层。

为什么 read 方法中msg 类型是 Object，因为pipeline 采用的是责任链模型，上一个msg 会传入到下一个handler，所以第一个handler的实例其实是byteBuffer，而后面的handler的msg 取决于前面的handler传入类型

基于 SimpleChannelInboundHandler，我们可以实现每一种指令的处理，不再需要强转，不再有冗长乏味的 if else 逻辑，不需要手动传递对象。

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

* 解码器

基于 ByteToMessageDecoder，我们可以实现自定义解码，而不用关心 ByteBuf 的强转和 解码结果的传递。
基于 MessageToByteEncoder，我们可以实现自定义编码，而不用关心 ByteBuf 的创建，不用每次向对端写 Java 对象都进行一次编码。
且encode 和decode 可以自动回收buffer
    
* 拆包
```apple js
1. 固定长度的拆包器 FixedLengthFrameDecoder
如果你的应用层协议非常简单，每个数据包的长度都是固定的，比如 100，那么只需要把这个拆包器加到 pipeline 中，Netty 会把一个个长度为 100 的数据包 (ByteBuf) 传递到下一个 channelHandler。

2. 行拆包器 LineBasedFrameDecoder
从字面意思来看，发送端发送数据包的时候，每个数据包之间以换行符作为分隔，接收端通过 LineBasedFrameDecoder 将粘过的 ByteBuf 拆分成一个个完整的应用层数据包。

3. 分隔符拆包器 DelimiterBasedFrameDecoder
DelimiterBasedFrameDecoder 是行拆包器的通用版本，只不过我们可以自定义分隔符。

4. 基于长度域拆包器 LengthFieldBasedFrameDecoder
最后一种拆包器是最通用的一种拆包器，只要你的自定义协议中包含长度域字段，均可以使用这个拆包器来实现应用层拆包。由于上面三种拆包器比较简单，读者可以自行写出 demo，接下来，我们就结合我们小册的自定义协议，来学习一下如何使用基于长度域的拆包器来拆解我们的数据包。

```