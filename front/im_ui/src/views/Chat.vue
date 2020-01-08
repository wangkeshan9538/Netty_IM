<template>
  <div class="container">
    <van-nav-bar left-arrow title="王柯杉" fixed @click-left="back" />

    <div class="contents">
      <van-list finished-text="没有更多了">
        <template v-for="{time,type,content} in msg">
          <Message v-bind:Messagetype="type" v-bind:Content="content" v-bind:key="time"></Message>
        </template>
      </van-list>
    </div>

    <van-cell-group class="inputFiels">
      <van-field v-model="inputMsg" rows="1" autosize border clearable type="textarea">
        <van-button slot="button" size="small" type="primary" @click="send">发送</van-button>
      </van-field>
    </van-cell-group>
  </div>
</template>>


<script>
import Message from "@/components/Message.vue";
import router from "@/router";
export default {
  name: "Chat",
  data: function() {
    return {
      msg: [{ time: "0:00", type: "chat-mine", content: "你好" }],
      inputMsg: ""
    };
  },
  methods: {
    back() {
      router.push("/main");
    },
    send() {
      for (var i = 0; i < 20; i++) {
        this.msg.push({
          time: new Date().Format("HH:mm:ss"),
          type: "chat-mine",
          content: this.inputMsg
        });
      }

      this.inputMsg = "";
    }
  },
  components: {
    Message
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


*::-webkit-scrollbar {/*滚动条整体样式*/
    width: 5px;     /*高宽分别对应横竖滚动条的尺寸*/
    height: 1px;
}
*::-webkit-scrollbar-thumb {/*滚动条里面小方块*/
  border-radius: 10px;
  -webkit-box-shadow: inset 0 0 5px rgba(0,0,0,0.2);
  background: #1EDDAA;
}
*::-webkit-scrollbar-track {/*滚动条里面轨道*/
  -webkit-box-shadow: inset 0 0 5px rgba(0,0,0,0.2);
  border-radius: 10px;
  background: #EDEDED;
}
</style>