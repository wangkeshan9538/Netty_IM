<template>
  <div class="container">
 
      <template v-for="curr in userList.list">
        <van-index-anchor  v-bind:index="curr.letter" v-bind:key="curr.letter" />

        <template v-for="{userName,userId} in curr.data">
          <van-cell v-bind:title="userName" v-bind:key="userId" />
        </template>
      </template>
    </van-index-bar>
  </div>
</template>

<script>
import { pySegSort, userList, refreshList } from "@/ws/getUserList.js";

export default {
  name: "Room",
  data: () => {
    return {
      userList
    };
  }
};
</script>