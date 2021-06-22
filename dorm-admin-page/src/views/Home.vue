<template>
    <div>
        <el-container style="height: 500px; border: 1px solid #eee">
            <el-header>
                <div>广软宿舍管理系统</div>
                <div>
                    <el-dropdown
                        @command="commandHandler"
                    >
                        <span class="el-dropdown-link">
                            {{user.name}}
                        </span>
                        <el-dropdown-menu slot="dropdown">
                            <el-dropdown-item command="profile">个人中心</el-dropdown-item>
                            <el-dropdown-item
                                command="logout"
                                divided
                            >注销登录</el-dropdown-item>
                        </el-dropdown-menu>
                    </el-dropdown>
                </div>
            </el-header>
            <el-container>
                <el-aside width="200px">
                    <el-menu
                        router
                        unique-opened
                    >
                        <el-submenu
                            :index="index+''"
                            v-for="(item,index) in routes"
                            v-if="!item.hidden"
                            :key="index"
                        >
                            <template slot="title">
                                <i
                                    style="color: #409eff;margin-right: 5px"
                                    :class="item.iconCls"
                                ></i>
                                <span>{{item.name}}</span>
                            </template>
                            <el-menu-item
                                :index="child.path"
                                v-for="(child,indexj) in item.children"
                                :key="indexj"
                            >
                                {{child.name}}
                            </el-menu-item>
                        </el-submenu>
                    </el-menu>
                </el-aside>
                <el-main>
                    <el-breadcrumb
                        separator-class="el-icon-arrow-right"
                        v-if="this.$router.currentRoute.path!='/home'"
                    >
                        <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
                        <el-breadcrumb-item>{{this.$router.currentRoute.name}}</el-breadcrumb-item>
                    </el-breadcrumb>
                    <div
                        v-if="this.$router.currentRoute.path=='/home'"
                    >
                        系统主页
                    </div>
                    <router-view />
                </el-main>
            </el-container>
        </el-container>
    </div>
</template>

<script>
export default {
  name: "Home",
  data() {
    return {
      user: JSON.parse(window.sessionStorage.getItem("user"))
    };
  },
  computed: {
    routes() {
      return this.$store.state.routes;
    },
    user() {
      return this.$store.state.currentUser;
    }
  },
  methods: {
    commandHandler(cmd) {
      if (cmd == "logout") {
        this.$confirm("此操作将注销登录, 是否继续?", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        })
          .then(() => {
            this.getRequest("/api/logout");
            window.sessionStorage.removeItem("user");
            this.$store.commit("initRoutes", []);
            this.$router.replace("/");
          })
          .catch(() => {
            this.$message({
              type: "info",
              message: "已取消操作"
            });
          });
      } else if (cmd == "profile") {
        this.$router.push("/profile");
      }
    }
  }
};
</script>

<style>
</style>
