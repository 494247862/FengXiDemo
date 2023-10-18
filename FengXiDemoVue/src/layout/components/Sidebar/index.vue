<template>
  <div :class="{ 'has-logo': showLogo }">
    <logo v-if="showLogo" :collapse="isCollapse" />
    <el-scrollbar wrap-class="scrollbar-wrapper">
      <el-menu
        :default-active="$route.name"
        :collapse="isCollapse"
        :background-color="variables.menuBg"
        :text-color="variables.menuText"
        :unique-opened="true"
        :active-text-color="variables.menuActiveText"
        :collapse-transition="true"
        mode="vertical"
      >
        <sidebar-item
          v-for="menu in menus"
          :key="menu.id"
          :item="menu"
          :base-path="menu.menuUrl"
        />
      </el-menu>
    </el-scrollbar>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
import Logo from "./Logo";
import SidebarItem from "./SidebarItem";
import variables from "@/styles/variables.scss";
import { MessageBox, Message } from "element-ui";
import { getMenuTreesByCurrents } from "@/api/menu";

export default {
  data(){
    return{
      menus:[]
    }
  },
  created: function () {
    // this.GetPrices(this.s_tag,this.s_title);
    this.getMenus();
  },
  methods:{
    async getMenus(){
     await getMenuTreesByCurrents()
        .then((data) => {
          if (!data) {
            Message({
              message: "获取菜单失败",
              type: "error",
              duration: 5 * 1000,
            });
          }
          this.menus = data;
        })
        .catch((error) => {
          Message({
            message: error.message || "获取菜单失败",
            type: "error",
            duration: 5 * 1000,
          });
        });
    }
  },

  components: { SidebarItem, Logo },
  computed: {
    ...mapGetters(["sidebar"]),
    activeMenu() {
      const route = this.$route;
      const { meta, path } = route;
      // if set path, the sidebar will highlight the path you set
      if (meta.activeMenu) {
        return meta.activeMenu;
      }
      return path;
    },
    showLogo() {
      return this.$store.state.settings.sidebarLogo;
    },
    variables() {
      return variables;
    },
    isCollapse() {
      return !this.sidebar.opened;
    },
  },
};
</script>
