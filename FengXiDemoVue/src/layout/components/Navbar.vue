<template>
  <div class="navbar">
    <hamburger :is-active="sidebar.opened" class="hamburger-container" @toggleClick="toggleSideBar"/>
    <div class="searchStyle">
      <el-select placeholder="搜索菜单"
                 value=""
                 filterable
                 @change="selectMenu">
        <el-option v-for="item in menuData" :value="item.menuUrl" :key="item.id" :label="item.menuName"></el-option>
      </el-select>
    </div>
    <!--    <breadcrumb class="breadcrumb-container"/>-->

    <div class="right-menu">
      <el-dropdown class="avatar-container" trigger="click">
        <div class="avatar-wrapper">
          <img :src="imageUrl" class="user-avatar">
          <i class="el-icon-caret-bottom"/>
        </div>
        <el-dropdown-menu slot="dropdown" class="user-dropdown">
          <router-link to="/">
            <el-dropdown-item>
              主页
            </el-dropdown-item>
          </router-link>
          <!--          <a target="_blank" href="https://github.com/PanJiaChen/vue-admin-template/">-->
          <!--            <el-dropdown-item>Github</el-dropdown-item>-->
          <!--          </a>-->
          <!--          <a target="_blank" href="https://panjiachen.github.io/vue-element-admin-site/#/">-->
          <!--            <el-dropdown-item>Docs</el-dropdown-item>-->
          <!--          </a>-->
          <el-dropdown-item divided @click.native="logout">
            <span style="display:block;">登出</span>
          </el-dropdown-item>
          <el-dropdown-item divided @click.native="openUpdatePassword">
            <span style="display: block">修改密码</span>
          </el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
    <el-dialog :visible.sync="isShowUpdatePassword"
               :close-on-click-modal="false"
               width="500px"
               :append-to-body='true'
               title="修改密码">
      <el-form :model="updatePasswordData"
               ref="updateFormRef">
        <el-form-item label="旧密码:" prop="oldPassword" :rules="[{ required: true, message: '不能为空'}]">
          <el-input v-model="updatePasswordData.oldPassword"></el-input>
        </el-form-item>
        <el-form-item label="新密码:" prop="newPasswordOne" :rules="[{ required: true, message: '不能为空'}]">
          <el-input show-password v-model="updatePasswordData.newPasswordOne"></el-input>
        </el-form-item>
        <el-form-item label="再次输入:" prop="newPasswordTwo" :rules="[{ required: true, message: '不能为空'}]">
          <el-input show-password v-model="updatePasswordData.newPasswordTwo"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button @click="()=>{isShowUpdatePassword = false}">取 消</el-button>
        <el-button type="primary" @click="updatePassword" :loading="loading">提 交</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import {mapGetters} from 'vuex'
  import Breadcrumb from '@/components/Breadcrumb'
  import Hamburger from '@/components/Hamburger'
  import {getImage} from '@/api/file'
  import {updatePassword} from '@/api/user'
  import {getMenuByCurrent} from "@/api/menu";
  import {getToken, removeToken} from '@/utils/auth'
  import {socket} from '@/utils/websocket'
  // 基础后端地址
  const baseURL = process.env.VUE_APP_WEBSOCKET_API
  const user = JSON.parse(localStorage.getItem('user'))
  export default {
    data() {
      return {
        imageUrl: null,
        isShowUpdatePassword: false,
        updatePasswordData: {},
        loading: false,
        menuData: [],
      }
    },
    components: {
      Breadcrumb,
      Hamburger
    },
    computed: {
      ...mapGetters([
        'sidebar',
        'avatar'
      ])
    },
    created() {
      //初始化websocket对象
      //window.location.host获取ip和端口，
      //process.env.VUE_APP_WEBSOCKET_BASE_API获取请求前缀
      socket.initWebSocket(
        `${baseURL}/` + user.id
      );
      //绑定接收消息方法
      socket.websocket.onmessage = this.websocketOnMessage;
    },
    mounted() {
      this.getImageUrl(JSON.parse(localStorage.getItem('user')).headPictureId)
      this.getMenuByCurrent()
    },
    methods: {
      // 以后如果用到即使通讯，可以使用全局变量监听来刷新数据
      websocketOnMessage(event) {
        console.log(event.data)
        this.$root.webStocketMessage = false
        this.$root.webStocketMessage = true
      },
      toggleSideBar() {
        this.$store.dispatch('app/toggleSideBar')
      },
      async logout() {
        await this.$store.dispatch('user/logout')
        // this.$router.push(`/login?redirect=${this.$route.fullPath}`)
        this.$router.push(`/login`)
      },
      getImageUrl(id) {
        getImage(id).then(res => {
          // 创建头像的url到img控件
          this.imageUrl = window.URL.createObjectURL(res)
        }).catch(err => {
          console.log(err.message)
        })
      },
      openUpdatePassword() {
        this.updatePasswordData = {}
        this.isShowUpdatePassword = true
      },
      updatePassword() {
        this.$refs['updateFormRef'].validate(valid => {
          if (valid) {
            const data = {...this.updatePasswordData}
            data.newPassword = data.newPasswordOne
            if (data.oldPassword === data.newPasswordOne) {
              this.$notify.error({
                title: '新密码与旧密码不能相同',
                message: ''
              })
              return
            }

            if (data.newPasswordTwo !== data.newPasswordOne) {
              this.$notify.error({
                title: '两次输入密码不相同',
                message: ''
              })
              return
            }
            this.loading = true
            updatePassword(data).then(res => {
              this.$notify.success({
                title: '修改成功'
              })
              this.loading = false
              this.isShowUpdatePassword = false
              this.logout()
            }).catch(err => {
              this.$notify.error({
                title: '修改失败',
                message: err.message
              })
              this.loading = false
            })
          } else {
            return false
          }
        })
      },
      // 获取可搜索的菜单
      getMenuByCurrent() {
        getMenuByCurrent().then(res => {
          this.menuData = res.filter(t => t.menuUrl && t.menuUrl !== "/")
        }).catch(err => {
          this.$notify.error({
            title: '获取菜单失败',
            message: err.message
          })
        })
      },
      // 选择菜单后跳转
      selectMenu(value) {
        console.log(value)
        this.$router.push({path: value})
      }
    }
  }
</script>

<style lang="scss" scoped>
  .navbar {
    height: 50px;
    overflow: hidden;
    position: relative;
    background: #fff;
    box-shadow: 0 1px 4px rgba(0, 21, 41, .08);

    .hamburger-container {
      line-height: 46px;
      height: 100%;
      float: left;
      cursor: pointer;
      transition: background .3s;
      -webkit-tap-highlight-color: transparent;

      &:hover {
        background: rgba(0, 0, 0, .025)
      }
    }

    .breadcrumb-container {
      float: left;
    }

    .right-menu {
      float: right;
      height: 100%;
      line-height: 50px;

      &:focus {
        outline: none;
      }

      .right-menu-item {
        display: inline-block;
        padding: 0 8px;
        height: 100%;
        font-size: 18px;
        color: #5a5e66;
        vertical-align: text-bottom;

        &.hover-effect {
          cursor: pointer;
          transition: background .3s;

          &:hover {
            background: rgba(0, 0, 0, .025)
          }
        }
      }

      .avatar-container {
        margin-right: 30px;

        .avatar-wrapper {
          margin-top: 5px;
          position: relative;

          .user-avatar {
            cursor: pointer;
            width: 40px;
            height: 40px;
            border-radius: 10px;
          }

          .el-icon-caret-bottom {
            cursor: pointer;
            position: absolute;
            right: -20px;
            top: 25px;
            font-size: 12px;
          }
        }
      }
    }

    .searchStyle {
      float: left;
      height: 100%;
      display: flex;
      align-items: center;
    }
  }
</style>
