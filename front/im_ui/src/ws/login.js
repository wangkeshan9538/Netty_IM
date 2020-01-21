import {send,commands,packet,MsgHandle} from '@/ws/ws.js';

class req {
    constructor(userName,passwd) { this.userName = userName, this.passwd =passwd }
}

class resp { constructor(userId) { this.userId = userId } }

export {req,resp}


function loginFuc(userName,passwd,func){
    var traceId=new Date().getTime();
    //组req
    var r=new req(userName,passwd)
    //组packet
    var p=new packet(commands.get('login'),traceId,r)

    //组handle
    var msg=new MsgHandle(p,func);
    send(msg)
}


export{loginFuc}