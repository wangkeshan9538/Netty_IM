## 开发心路历程
packet 主要想做成 **数据包** 的实体类，但为了简便，直接增加了decode和encode 的方法，所以packet 就是数据包entity 并 拥有与 buffer之间的 转换方法，

在开发中 一直在思考，如果 数据包结构变动 要怎么包容，举例来说，各部分的长度变动，
刚开始把内容全部变成byte[] , 但不能明确表现数据长度，所以如果 协议有变动，最好还是直接继承packet，甚至直接给出packet的接口，
出现一个问题是 ： packet 与 command SerializerAlgorithm 之间 还是有关系，这个关系要怎么确定，目前是直接 packet 里的command直接使用byte 与command
SerializerAlgorithm 直接对应 ，但以后如果这层关系需要解耦的话，就需要再加一层，可以把这个判断逻辑方法直接写在packet里

我做了一个packetAnalysis 的接口 定义decode encode 方法 以及 packet 与 command Serializer 的转换逻辑 ， 还想定义一个packet本身的接口 ，但问题在于packet应该是一个纯的entity，
那这个接口，就只有get  set 方法可以定义，但如果协议各部分长度不一样，那就影响到了get方法的返回值，所以协议entity之间并不应该有 继承关系，而PacketAnalysis 就带一个泛型 来指定packet类，

一个疑问：buffer 和channel 之间是怎么样的一个关系，buffer 的回收是怎样的


本来 担心 输入线程写channel 会和心跳检查的操作 冲突 
检查后发现channel 是线程安全的： 沿着调用链走 发现：
```AbstractChannelHandlerContext.java

    private void write(Object msg, boolean flush, ChannelPromise promise) {
        AbstractChannelHandlerContext next = findContextOutbound();
        final Object m = pipeline.touch(msg, next);
        EventExecutor executor = next.executor();
        if (executor.inEventLoop()) {
            if (flush) {
                next.invokeWriteAndFlush(m, promise);
            } else {
                next.invokeWrite(m, promise);
            }
        } else {
            AbstractWriteTask task;
            if (flush) {
                task = WriteAndFlushTask.newInstance(next, m, promise);
            }  else {
                task = WriteTask.newInstance(next, m, promise);
            }
            safeExecute(executor, task, promise, m);
        }
    }
```
如果channelContext 的当前线程 是在Event Loop 的线程，那么直接写，感觉就是在正常的event loop里，但如果当前线程不是EventLoop ，那么就把写任务放在Execute里，所以相当于任务的处理一直在Execute里，所以是线程安全的