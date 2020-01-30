<template>
  <div class="container">
    <template v-for="curr in userList.list">
      <van-index-anchor v-bind:index="curr.letter" v-bind:key="curr.letter" />

      <template v-for="{userName,userId} in curr.data">
        <van-swipe-cell v-bind:key="userId">
          <van-cell v-bind:title="userName" />
          <template slot="right">
            <van-button square type="danger" text="添加" @click="click(userId)" />
          </template>
        </van-swipe-cell>
      </template>
    </template>
  </div>
</template>

<script>
import { pySegSort, userList, refreshList } from "@/ws/getUserList.js";
import { addFriend, friendsList } from "@/ws/addFriend.js";

export default {
  name: "Room",
  data: () => {
    return {
      userList
    };
  },
  methods: {
    click(userid) {
      addFriend(userid);
    }
  }
};
</script>