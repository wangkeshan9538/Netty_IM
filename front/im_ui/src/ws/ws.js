
//初始化
var socket;
if (!window.WebSocket) {
    window.WebSocket = window.MozWebSocket;
}
if (window.WebSocket) {
    socket = new WebSocket("ws://"+window.location.host+"/websocket");
    socket.onmessage = function (event) {
        var packet=JSON.parse( event.data);
        //查找下有没有traceId
        if(undefined== packet.traceId ||null==packet.traceId){
            //直接进注册的handle
            var handle=cmdHandle.get(packet.command)
            handle(packet)
        }else{
            //进队列中原消息的handle
            var index=wsMsgQueue.findIndex((n)=> {
                return n.req.command==packet.command&&n.req.traceId==packet.traceId
            })
            var msghandle;
            //remove
            if(index>=0){
                msghandle=wsMsgQueue.splice(index, 1)[0]
                msghandle.resp=packet;
                msghandle.handle(msghandle);
            }

        }

    };
    socket.onopen = function (event) {
        console.info("websocket打开连接")
    };
    socket.onclose = function (event) {
        console.info("websocket关闭连接")
    };
} else {
    alert("Your browser does not support Web Socket.");
}


function send(msg) {
    if (!window.WebSocket) { return; }
    if (socket.readyState == WebSocket.OPEN) {

        if(msg instanceof packet){
            socket.send(JSON.stringify(msg));

        }else if(msg instanceof MsgHandle){
            socket.send(JSON.stringify( msg.req));
            //进队列
            wsMsgQueue.push(msg)
        }
        
    } else {
        alert("The socket is not open.");
    }
}

function registerHandle(command,handle){
    cmdHandle.set(command,handle)
}

var wsMsgQueue=[]


//数据包格式
class packet{
    constructor(command,traceId,data){
        this.command = command,
        this.traceId = traceId,
        this.data = data
    }
}

//消息带handler
class MsgHandle{
    constructor(req,handle){
        this.req=req, //packet
        this.resp={}, //packet
        this.handle=handle
    }
}


//存储命令
const commands =new Map([
    ['login','0'],
    ['getUserList','1'],
    ['SEND_MSSAGE','2'],
    ['addFriend','3'],
    ['ADD_NOTIRY','4'],
    ['MESSAGE_NOTIFY','5'],
    ['OFFLINE_NOTIFY','6']
])

//存储命令对应的处理器
 var cmdHandle=new Map([
     
])


export {send,commands,packet,MsgHandle,registerHandle}