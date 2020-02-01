import { commands, registerHandle } from '@/ws/ws.js';
import { friendsList } from "@/ws/addFriend.js";
import { Notify } from 'vant';

function offLineHandle(obj) {
    var data = obj.data
    var user=friendsList.list.find((v)=>{return  v.userId===data.userId})
    user.status='0'
    Notify({
        type: 'success',
        message: '好友下线:'+data.userName,
        duration: 1000
    });
}


registerHandle(commands.get('OFFLINE_NOTIFY'), offLineHandle)