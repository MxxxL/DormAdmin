<template>
    <div>
        <div style="margin-top: 10px;display: flex;justify-content: center">
            <el-input
                v-model="keyword"
                placeholder="通过用户昵称搜索用户..."
                prefix-icon="el-icon-search"
                style="width: 400px;margin-right: 10px"
                @keydown.enter.native="doSearch"
            ></el-input>
            <el-button
                icon="el-icon-search"
                type="primary"
                @click="doSearch"
            >搜索</el-button>
        </div>
        <div class="user-container">
            <el-card
                class="user-card"
                v-for="(user,index) in users"
                :key="index"
            >
                <div
                    slot="header"
                    class="clearfix"
                >
                    <span>{{user.name}}</span>
                    <el-button
                        style="float: right; padding: 3px 0;color: #e30007;"
                        type="text"
                        icon="el-icon-delete"
                        @click="deleteUser(user)"
                    ></el-button>
                </div>
                <div>
                    <div class="userinfo-container">
                        <div>用户昵称：{{user.name}}</div>
                        <div>手机号码：{{user.phone}}</div>
                        <div>用户状态：
                            <el-switch
                                v-model="user.enabled"
                                active-text="启用"
                                @change="enabledChange(user)"
                                active-color="#13ce66"
                                inactive-color="#ff4949"
                                inactive-text="禁用"
                            >
                            </el-switch>
                        </div>
                        <div>用户角色：
                            <el-tag
                                type="success"
                                style="margin-right: 4px"
                                v-for="(role,indexj) in user.roles"
                                :key="indexj"
                            >{{role.nameZh}}
                            </el-tag>
                            <el-popover
                                placement="right"
                                title="角色列表"
                                @show="showPop(user)"
                                @hide="hidePop(user)"
                                width="200"
                                trigger="click"
                            >
                                <el-select
                                    v-model="selectedRoles"
                                    multiple
                                    placeholder="请选择"
                                >
                                    <el-option
                                        v-for="(r,indexk) in allroles"
                                        :key="indexk"
                                        :label="r.nameZh"
                                        :value="r.id"
                                    >
                                    </el-option>
                                </el-select>
                                <el-button
                                    slot="reference"
                                    icon="el-icon-more"
                                    type="text"
                                ></el-button>
                            </el-popover>
                        </div>
                        <div>备注：{{user.remark}}</div>
                    </div>
                </div>
            </el-card>

        </div>
    </div>
</template>

<script>
export default {
  name: "SysUser",
  data() {
    return {
      keyword: "",
      users: [],
      selectedRoles: [],
      allroles: []
    };
  },
  mounted() {
    this.initUsers();
  },
  methods: {
    deleteUser(user) {
      this.$confirm(
        "此操作将永久删除【" + user.name + "】用户, 是否继续?",
        "提示",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }
      )
        .then(() => {
          this.postRequest("/api/user/del", user).then(resp => {
            if (resp) {
              this.initUsers();
            }
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除"
          });
        });
    },
    doSearch() {
      this.initUsers();
    },
    hidePop(user) {
      let roles = [];
      Object.assign(roles, user.roles);
      let flag = false;
      if (roles.length != this.selectedRoles.length) {
        flag = true;
      } else {
        for (let i = 0; i < roles.length; i++) {
          let role = roles[i];
          for (let j = 0; j < this.selectedRoles.length; j++) {
            let sr = this.selectedRoles[j];
            if (role.id == sr) {
              roles.splice(i, 1);
              i--;
              break;
            }
          }
        }
        if (roles.length != 0) {
          flag = true;
        }
      }
      if (flag) {
        let url = "/api/user/role?uid=" + user.id;
        this.selectedRoles.forEach(sr => {
          url += "&rids=" + sr;
        });
        this.putRequest(url).then(resp => {
          if (resp) {
            this.initUsers();
          }
        });
      }
    },
    showPop(user) {
      this.initAllRoles();
      let roles = user.roles;
      this.selectedRoles = [];
      roles.forEach(r => {
        this.selectedRoles.push(r.id);
      });
    },
    enabledChange(user) {
      delete user.roles;
      this.postRequest("/api/user/add", user).then(resp => {
        if (resp) {
          this.initUsers();
        }
      });
    },
    initAllRoles() {
      this.getRequest("/api/user/roles").then(resp => {
        if (resp) {
          this.allroles = resp;
        }
      });
    },
    initUsers() {
      const params = {};
      if (this.keyword != null && this.keyword != "") {
        params.name = this.keyword;
      }
      this.getRequest("/api/user/list", params).then(resp => {
        if (resp) {
          this.users = resp;
        }
      });
    }
  }
};
</script>

<style>
.userinfo-container div {
  font-size: 12px;
  color: #409eff;
}

.userinfo-container {
  margin-top: 20px;
}

.img-container {
  width: 100%;
  display: flex;
  justify-content: center;
}

.userface-img {
  width: 72px;
  height: 72px;
  border-radius: 72px;
}

.user-container {
  margin-top: 10px;
  display: flex;
  flex-wrap: wrap;
  justify-content: space-around;
}

.user-card {
  width: 350px;
  margin-bottom: 20px;
}
</style>