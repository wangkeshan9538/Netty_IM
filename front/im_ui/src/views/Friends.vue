<template>
  <div class="container">
    <div class="friendList">
      <van-list finished-text="没有更多了">
        <template v-for="{userName,userId,status} in list">
          <van-cell v-bind:key="userId" @click="click(userId,userName)">
            <van-icon name="user-o" dot />
            {{userName}}
            <van-tag :type="status==1?'success':'primary'">在线</van-tag>
          </van-cell>
        </template>
      </van-list>
    </div>
  </div>
</template>


<script>
import router from "@/router";
import { friendsList } from "@/ws/addFriend.js";

export default {
  name: "Friends",
  data: () => {
    return {
      list: friendsList.list
    };
  },
  methods: {
    click(userId,userName) {
      router.push("/Chat/" + userId+"/"+userName);
    }
  }
};
</script>


<style scoped>
.van-icon {
  display: inline-block;
  margin-right: 20px;
}
</style>