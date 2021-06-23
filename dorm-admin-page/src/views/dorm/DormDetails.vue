<template>
    <div>
        <el-button
            style="margin-top:20px;margin-bottom:10px;"
            @click="showAddDorm"
            type="success"
        >添加</el-button>
        <el-button
            type="warning"
            @click="deleteDorms"
            :disabled="this.ids.length === 0"
        >
            批量删除
        </el-button>
        <div style="display: flex;justify-content: space-between">
            <el-input
                placeholder="请输入宿舍名进行搜索"
                prefix-icon="el-icon-search"
                clearable
                @clear="initDorm"
                style="width: 350px;margin-right: 10px"
                v-model="keyword"
                @keydown.enter.native="initDorm"
            >
            </el-input>
            <el-button
                icon="el-icon-search"
                type="primary"
                @click="initDorm"
            >
                搜索
            </el-button>
        </div>
        <el-table
            :data="dormList"
            stripe
            style="width: 100%"
            ref="multipleTable"
            @selection-change="handleSelectionChange"
        >
            <el-table-column
                type="selection"
                width="55"
            >
            </el-table-column>
            <el-table-column
                v-if="false"
                prop="id"
                label="id"
                align="center"
            >
            </el-table-column>
            <el-table-column
                prop="name"
                label="宿舍名"
                align="center"
                width="150"
            >
            </el-table-column>
            <el-table-column
                prop="building.id"
                label="buildingId"
                align="center"
                v-if="false"
            >
            </el-table-column>
            <el-table-column
                prop="building.name"
                label="所属宿舍楼"
                align="center"
                width="120"
            >
            </el-table-column>
            <el-table-column
                prop="maxNum"
                label="最大人数"
                align="center"
                width="120"
            >
            </el-table-column>
            <el-table-column
                prop="remark"
                label="备注"
                align="center"
                width="70"
            >
            </el-table-column>
            <el-table-column
                align="center"
                label="操作"
                width="300"
            >
                <template slot-scope="scope">
                    <el-button
                        icon="el-icon-edit"
                        size="mini"
                        @click="showEditDorm(scope.row)"
                        type="primary"
                    >编辑</el-button>
                    <el-button
                        icon="el-icon-delete"
                        size="mini"
                        @click="deleteDorm(scope.row)"
                        type="danger"
                    >删除</el-button>
                </template>
            </el-table-column>

        </el-table>
        <el-pagination
            style="margin-top:20px;"
            @size-change="handleSizeChange"
            @current-change="changePage"
            :page-sizes="pageSizes"
            :page-size="pageSize"
            :current-page="currentPage"
            layout="total, sizes, prev, pager, next, jumper"
            :total="total"
        >
        </el-pagination>

        <el-dialog
            :title="title"
            :visible.sync="dialogVisible"
            width="50%"
        >
            <el-form
                ref="dormForm"
                :model="dorm"
                :rules="dormFormRules"
                label-width="70px"
            >
                <el-form-item
                    label="宿舍名"
                    prop="name"
                >
                    <el-input v-model="dorm.name"></el-input>
                </el-form-item>
                <el-form-item
                    label="所属宿舍楼"
                    prop="buildingId"
                >
                    <el-select
                        v-model="dorm.buildingId"
                        placeholder="请选择所属宿舍楼"
                        clearable
                    >
                        <el-option
                            v-for="building in buildingList"
                            :key="building.id"
                            :label="building.name"
                            :value="building.id"
                        >
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item
                    label="最大人数"
                    prop="maxNum"
                >
                    <el-input v-model="dorm.maxNum"></el-input>
                </el-form-item>
                <el-form-item
                    label="备注"
                    prop="remark"
                >
                    <el-input v-model="dorm.remark"></el-input>
                </el-form-item>
            </el-form>
            <span
                slot="footer"
                class="dialog-footer"
            >
                <el-button @click="dialogVisible = false">取 消</el-button>
                <el-button
                    type="primary"
                    @click="addOrEditDorm"
                >确 定</el-button>
            </span>
        </el-dialog>
    </div>

</template>

<script>
export default {
  methods: {
    handleSizeChange(val) {
      this.pageSize = val;
      this.initDorm();
    },
    changePage(e) {
      this.currentPage = e;
      this.initDorm();
    },
    handleSelectionChange(row) {
      this.ids = row;
    },
    initDorm() {
      this.loading = true;
      const params = {
        page: this.currentPage,
        size: this.pageSize
      };
      if (this.keyword != null && this.keyword != "") {
        params.name = this.keyword;
      }
      this.getRequest("/api/dorm/list", params).then(resp => {
        this.loading = false;
        if (resp) {
          this.dormList = resp.data;
          this.total = resp.total;
        }
      });
    },
    initBuilding() {
      this.getRequest("/api/building/all").then(resp => {
        if (resp) {
          this.buildingList = resp;
        }
      });
    },
    emptyDorm() {
      this.dorm = {
        name: "",
        buildingId: null,
        maxNum: 4,
        remark: ""
      };
    },
    showAddDorm() {
      this.title = "添加宿舍";
      this.emptyDorm();
      this.dialogVisible = true;
    },
    showEditDorm(data) {
      this.title = "编辑宿舍";
      this.dorm = data;
      this.dialogVisible = true;
    },
    addOrEditDorm() {
      if (this.dorm.id) {
        this.$refs["dormForm"].validate(valid => {
          if (valid) {
            this.postRequest("/api/dorm/add", this.dorm).then(resp => {
              if (resp) {
                this.dialogVisible = false;
                this.initDorm();
              }
            });
          }
        });
      } else {
        this.$refs["dormForm"].validate(valid => {
          if (valid) {
            this.postRequest("/api/dorm/add", this.dorm).then(resp => {
              if (resp) {
                this.dialogVisible = false;
                this.initDorm();
              }
            });
          }
        });
      }
    },
    deleteDorm(data) {
      this.$confirm(
        "此操作将永久删除【" + data.name + "】这行数据, 是否继续?",
        "提示",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }
      )
        .then(() => {
          this.postRequest("/api/dorm/del", [data]).then(resp => {
            if (resp) {
              this.$message({
                type: "info",
                message: resp.msg
              });
              this.initDorm();
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
    deleteDorms() {
      this.$confirm("此操作将永久删除选择数据, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          this.postRequest("/api/dorm/del", this.ids).then(resp => {
            if (resp) {
              this.$message({
                type: "info",
                message: resp.msg
              });
              this.ids = [];
              this.initDorm();
            }
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除"
          });
        });
    }
  },
  mounted() {
    this.initDorm();
    this.initBuilding();
  },
  data() {
    return {
      ids: [],
      dorm: {
        name: "",
        buildingId: null,
        maxNum: 4,
        remark: ""
      },
      dormList:[],
      buildingList: [],
      pageSizes: [5, 10, 25, 50],
      pageSize: 5,
      total: 0,
      currentPage: 1,
      keyword: "",
      dialogVisible: false,
      title: "",
      // 添加表单的验证规则对象
      dormFormRules: {
        name: [
          { required: true, message: "请输入宿舍名", trigger: "blur" },
          { min: 2, max: 10, message: "长度 2-10 个字符", trigger: "blur" }
        ]
      }
    };
  }
};
</script>