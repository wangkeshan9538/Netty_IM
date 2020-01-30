import { send, commands, packet, MsgHandle, registerHandle } from '@/ws/ws.js';

class req { constructor(snedFromID, sendToId, msg) { this.snedFromID = snedFromID, this.sendToId = sendToId, this.msg = msg, this.time = new Date() } }


//发送
function sendMsg(sendToId, msg) {
    var userid = JSON.parse(sessionStorage.getItem('loginUser')).userId;
    var sendReq = new req(userid, sendToId, msg)

    var traceId = new Date().getTime();
    //组packet
    var p = new packet(commands.get('SEND_MSSAGE'), traceId, sendReq)


    //添加到消息box里
    var m = new Msg('send', msg, 'P',1)
    if (undefined == msgBox[sendToId] || null == msgBox[sendToId]) {
        msgBox[sendToId] = [m]
    } else {
        msgBox[sendToId].push(m)
    }
    var msgWithHandle = new MsgHandle(p, () => { m.status = 'S' });
    send(msgWithHandle)
}



//接收
function receiveMsg(obj) {

    //TODO 更新friend的消息提示status

    var data = obj.data;
    var snedFromID = data.fromId
    var m = new Msg('receive', data.msg, 'S',0)
    if (undefined == msgBox[snedFromID] || null == msgBox[snedFromID]) {
        msgBox[snedFromID] = [m]
    } else {
        msgBox[snedFromID].push(m)
    }
}
 
//注册接收信息的handle
registerHandle(commands.get('MESSAGE_NOTIFY'), receiveMsg)


class Msg {
    constructor(direct, msg, status, readOrNot) {
        this.direct = direct, 
        this.status = status, 
        this.msg = msg, 
        this.time=new Date().Format("HH:mm:ss"),
        this.readOrNot = readOrNot
    }
}

//结构为 
var msgBox = {}


export {msgBox,sendMsg}


