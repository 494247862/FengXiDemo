<template>
  <div class="text">欢迎进入首页</div>
</template>

<script>
  import {socket} from '@/utils/websocket'
  // 基础后端地址
  const baseURL = process.env.VUE_APP_WEBSOCKET_API
  const user = JSON.parse(localStorage.getItem('user'))
  export default {
    name: "First",
    data() {
      return {}
    },
    watch: {
      // 监听全局变量
      '$root.webStocketMessage': {
        handler(newData, oldData) {
          if (newData === true) {
            this.$message({
              showClose: true,
              message: '有新消息传递',
              type: 'warning'
            })
          }
        },
        deep: true
      }
    },
    methods: {}
  }
</script>

<style scoped>
  .text {
    font-size: 50px;
    font-weight: bold;
  }
</style>
