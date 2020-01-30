<template>
  <div class="container">
    <van-nav-bar left-arrow :title="sendToName" fixed @click-left="back" />
    <div class="contents">
      <van-list finished-text="没有更多了">
        <template v-for="{time,direct,status,msg,readOrNot} in msg[sendToId]">
          <Message
            v-bind:Messagetype="direct=='send'?'chat-mine':'chat-other'"
            v-bind:Content="msg"
            v-bind:time="time"
            v-bind:key="time"
          ></Message>
        </template>
      </van-list>
    </div>

    <van-cell-group class="inputFiels">
      <van-field v-model="inputMsg" rows="1" autosize border clearable type="textarea">
        <van-button slot="button" size="small" type="primary" @click="send">发送</van-button>
      </van-field>
    </van-cell-group>
  </div>
</template>


<script>
import Message from "@/components/Message.vue";
import router from "@/router";
import { msgBox, sendMsg } from "@/ws/sendMsg.js";

export default {
  name: "Chat",
  props: ["sendToId", "sendToName"],
  data: function() {
    return {
      msg: msgBox,
      inputMsg: ""
    };
  },
  methods: {
    back() {
      router.push("/main");
    },
    send() {
      sendMsg(this.sendToId, this.inputMsg);
      this.inputMsg = "";
    }
  },
  components: {
    Message
  },
  created:function(){
    if(undefined == this.msg[this.sendToId] || null==this.msg[this.sendToId]){
      this.$set(this.msg,this.sendToId,[])
    }
  }
};
</script>

<style>
.inputFiels {
  position: fixed;
  bottom: 0px;
  width: 100%;
  z-index: 1;
}

.contents {
  top: 46px;
  position: absolute;
  width: 100%;
  overflow-y: scroll;
  overflow-x: hidden;
  bottom: 50px;
}

*::-webkit-scrollbar {
  /*滚动条整体样式*/
  width: 5px; /*高宽分别对应横竖滚动条的尺寸*/
  height: 1px;
}
*::-webkit-scrollbar-thumb {
  /*滚动条里面小方块*/
  border-radius: 10px;
  -webkit-box-shadow: inset 0 0 5px rgba(0, 0, 0, 0.2);
  background: #1eddaa;
}
*::-webkit-scrollbar-track {
  /*滚动条里面轨道*/
  -webkit-box-shadow: inset 0 0 5px rgba(0, 0, 0, 0.2);
  border-radius: 10px;
  background: #ededed;
}
</style>