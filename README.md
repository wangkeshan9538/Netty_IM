一个**粗糙**的，**发上来都觉得很羞耻**的，使用vue ,netty 基于websocket的 即时通讯系统。  
github: https://github.com/wangkeshan9538/Netty_IM  
site: http://47.105.88.240:8080/  

# 使用示例

![](https://user-gold-cdn.xitu.io/2020/2/5/1701190ea03bb599?w=1228&h=785&f=gif&s=642413)

# 主要结构

![](https://user-gold-cdn.xitu.io/2020/2/5/17011920019b9837?w=885&h=701&f=png&s=50652)


##  数据包结构为json形式：
```java
    private String command;

    private String traceId;

    private Object data;
```
command 表示数据包指令类型，  
因为websocket协议是全双工，并不能像http协议一样一问一答的形式，所以对于client，在原生协议上无法链接一个request和一个response,所以使用traceId来判断，  
data为数据  

## 前端结构说明
如果是一问一答的需要traceId 的应答模式，则对req,以及对应的回调方法打包进队列，如果resp回应，则回调处理  
如果是单向的，则根据command 来找到注册的处理方法处理

## 后端结构
handler:

``` java
        pipeline.addLast(new IMIdleStateHandler()); //空闲连接处理，超时则关闭
        pipeline.addLast(new HttpServerCodec());//http协议编解码器
        pipeline.addLast(new HttpObjectAggregator(65536)); //聚合 htp requet中的chunk内容，
        pipeline.addLast(new ChunkedWriteHandler());//聚合response中的大量数据内容
        pipeline.addLast(new WebSocketServerCompressionHandler());//处理websocket的扩展以及判断协议升级
        pipeline.addLast(new WebSocketServerProtocolHandler(WEBSOCKET_PATH, null, true));//会自动添加websocket握手handler,握手完成会添加websocket的编解码器
        pipeline.addLast(new HttpStaticFileServerHandler());//静态页面服务，
        pipeline.addLast(new WebSocketFrameHandler()); //业务流程的开始 
```

业务方法举例：

``` java
@Command(ADD_FRIENDS)
public class AddFriendsService extends BaseService<AddFriendReq, AddFriendResp> {

    @Override
    AddFriendResp process(MsgContext context, AddFriendReq addFriendReq) {
        UserService.addFriend(addFriendReq.getUserId(), addFriendReq.getAddId());

        //响应被添加者
        UserInfo userInfo = UserService.getUserInfo(addFriendReq.getUserId());
        Packet p = new Packet(ADD_NOTIRY, null, new AddFriendNotify(userInfo.getUserId(), userInfo.getUserName()));
        UserInfo addedUserInfo = UserService.getUserInfo(addFriendReq.getAddId());
        send(addedUserInfo.getChannel(), p);

        return new AddFriendResp("SUCCESS", "SUCCESS", addedUserInfo.getUserId(), addedUserInfo.getUserName());
    }
}

```
@Command为自定义注解，标注处理的command类型，泛型为req,resp的类型，

## 运行
前端： vue ui直接打包
后端：  java -jar -Dfront_dir=E:\code\Netty_IM\front\im_ui\dist -Dport=8080 netty_im-1.0.jar
front_dir 为前端目录
port

## TODO
其实看起来完全就是个粗糙的玩具，列一些后续可以继续做的方向吧，虽然我这么懒估计也不会去做
1. netty 用起来还是有点太底层了，静态服务器的代码都要自己找，所以可以考虑换成vertx,比较小清新，还能做集群
2. 想做一个压测工具，来压测下性能，
3. ssl的支持，
