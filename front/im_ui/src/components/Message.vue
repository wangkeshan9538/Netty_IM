<template>
  <div>
    <div v-bind:class="Messagetype">
      <div class="time">
        <cite>
          <i>{{time}}</i>
        </cite>
      </div>
      <div class="chat-text">{{Content}}</div>
    </div>
  </div>
</template>>


<script>
export default {
  name: "Message",

  props: ["Messagetype", "Content","time"]
};
</script>

<style>
.time {
  font-size: 5px;
  padding-bottom: 4px;
  padding-top: 10px;
}
.chat-other {
  padding-left: 15px;
}

.chat-mine {
  padding-right: 15px;
  text-align: right;
}
.chat-mine .chat-text {
  text-align: left;
  background-color: #33df83;
  color: #fff;
}

.chat-text,
.chat-user {
  display: inline-block;
  vertical-align: top;
}

.chat-text {
  position: relative;
  line-height: 22px;
  padding: 10px 15px;
  background-color: #eee;
  border-radius: 3px;
  color: #333;
  word-break: break-all;
  max-width: 45%;
}

.chat-mine .chat-text:after {
  left: auto;
  right: -10px;
  border-top-color: #33df83;
}
.chat-text:after {
  content: "";
  position: absolute;
  left: -10px;
  top: 15px;
  width: 0;
  height: 0;
  border-style: solid dashed dashed;
  border-color: #eee transparent transparent;
  overflow: hidden;
  border-width: 10px;
}
</style>