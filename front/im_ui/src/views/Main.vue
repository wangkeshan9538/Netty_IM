<template>
  <div class="main container">
    <router-view />
    <van-tabbar v-model="active" >
      <van-tabbar-item icon="friends-o" to="/Main/Friends">朋友</van-tabbar-item>
      <van-tabbar-item icon="add-o" to="/Main/Room">在线</van-tabbar-item>
    </van-tabbar>
  </div>
</template>

<script>
import { Tabbar, TabbarItem } from "vant";
export default {
  name: "Main",
  props: {},
  data() {
    return {
      active: 0
    };
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>
