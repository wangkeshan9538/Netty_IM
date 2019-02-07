## NIO 的主要组件和使用
* Selector
  
  选择器不多说

* ServerSocketChannel
       
    Channels read data into Buffers, and Buffers write data into Channels
    channel 表示的是 与一个连接。
    ServerSocketChannel 是服务器 对socket的监控通道，相似于serverSocket
* SelectionKey
    
    表示 SelectableChannel 在 Selector 中的注册的标记。 
    通过select 监控key 来判断channel的而状态

* SocketChannel
    
    表示一个Client连接 
    
* Buffer
    
    承接 从Channel 读取的数据，或将此数据传输到Channel

## 出现的问题
1. 用浏览器 对NIOServer 进行请求 ，发现 Server的key accept了多次，猜测是因为 http的TCP 连接会有不同，
   如果 直接写socket 进行连接的话，那么 serverKey 只会accept 一次。
   
2. NIO 对应的通信模型是IO多路复用，但是多路复用在 拷贝数据阶段，是会阻塞线程的，那这个阻塞时间，会不会导致延时太久？
   因为是直接从内核态到用户态的内存传输，所以这并不会耗费太多时间，而且系统还有直接映射，不通过内核态的系统调用可以解决这个问题，