<template>
  <div>
    <el-button
      style="margin-top:20px;margin-bottom:10px;"
      @click="showAddBuilding"
      type="success"
    >添加</el-button>
    <el-button
      type="warning"
      @click="deleteBuildings"
      :disabled="this.ids.length === 0"
    >
      批量删除
    </el-button>
    <div style="display: flex;justify-content: space-between">
      <el-input
        placeholder="请输入宿舍楼名进行搜索"
        prefix-icon="el-icon-search"
        clearable
        @clear="initBuilding"
        style="width: 350px;margin-right: 10px"
        v-model="keyword"
        @keydown.enter.native="initBuilding"
      >
      </el-input>
      <el-button
        icon="el-icon-search"
        type="primary"
        @click="initBuilding"
      >
        搜索
      </el-button>
    </div>
    <el-table
      :data="buildingList"
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
        label="宿舍楼名"
        align="center"
        width="150"
      >
      </el-table-column>
      <el-table-column
        prop="buildingType"
        label="类型"
        align="center"
        width="100"
      >
        <template slot-scope="scope">
          <span v-if="scope.row.buildingType == 1">男生宿舍楼</span>
          <span v-else>女生宿舍楼</span>
        </template>
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
            @click="showEditBuilding(scope.row)"
            type="primary"
          >编辑</el-button>
          <el-button
            icon="el-icon-delete"
            size="mini"
            @click="deleteBuilding(scope.row)"
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
        ref="buildingForm"
        :model="building"
        :rules="buildingFormRules"
        label-width="70px"
      >
        <el-form-item
          label="宿舍楼名"
          prop="name"
        >
          <el-input v-model="building.name"></el-input>
        </el-form-item>
        <el-form-item
          label="宿舍楼类型"
          prop="buildingType"
        >
          <el-radio-group v-model="building.buildingType">
            <el-radio :label="1">男生宿舍楼</el-radio>
            <el-radio :label="0">女生宿舍楼</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item
          label="备注"
          prop="remark"
        >
          <el-input v-model="building.remark"></el-input>
        </el-form-item>
      </el-form>
      <span
        slot="footer"
        class="dialog-footer"
      >
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button
          type="primary"
          @click="addOrEditBuilding"
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
      this.initBuilding();
    },
    changePage(e) {
      this.currentPage = e;
      this.initBuilding();
    },
    handleSelectionChange(row) {
      this.ids = row;
    },
    initBuilding() {
      this.loading = true;
      const params = {
        page: this.currentPage,
        size: this.pageSize
      };
      if (this.keyword != null && this.keyword != "") {
        params.name = this.keyword;
      }
      this.getRequest("/api/building/list", params).then(resp => {
        this.loading = false;
        if (resp) {
          this.buildingList = resp.data;
          this.total = resp.total;
        }
      });
    },
    emptyBuilding() {
      this.building = {
        name: "",
        buildingType: 1,
        remark: ""
      };
    },
    showAddBuilding() {
      this.title = "添加宿舍楼";
      this.emptyBuilding();
      this.dialogVisible = true;
    },
    showEditBuilding(data) {
      this.title = "编辑宿舍楼";
      this.building = data;
      this.dialogVisible = true;
    },
    addOrEditBuilding() {
      if (this.building.id) {
        this.$refs["buildingForm"].validate(valid => {
          if (valid) {
            this.postRequest("/api/building/add", this.building).then(resp => {
              if (resp) {
                this.dialogVisible = false;
                this.initBuilding();
              }
            });
          }
        });
      } else {
        this.$refs["buildingForm"].validate(valid => {
          if (valid) {
            this.postRequest("/api/building/add", this.building).then(resp => {
              if (resp) {
                this.dialogVisible = false;
                this.initBuilding();
              }
            });
          }
        });
      }
    },
    deleteBuilding(data) {
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
          this.postRequest("/api/building/del", [data]).then(resp => {
            if (resp) {
              this.$message({
                type: "info",
                message: resp.msg
              });
              this.initBuilding();
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
    deleteBuildings() {
      this.$confirm("此操作将永久删除选择数据, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          this.postRequest("/api/building/del", this.ids).then(resp => {
            if (resp) {
              this.$message({
                type: "info",
                message: resp.msg
              });
              this.ids = [];
              this.initBuilding();
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
    this.initBuilding();
  },
  data() {
    return {
      ids: [],
      building: {
        name: "",
        buildingType: 1,
        remark: ""
      },
      buildingList: [],
      pageSizes: [5, 10, 25, 50],
      pageSize: 5,
      total: 0,
      currentPage: 1,
      keyword: "",
      dialogVisible: false,
      title: "",
      // 添加表单的验证规则对象
      buildingFormRules: {
        name: [
          { required: true, message: "请输入宿舍楼名", trigger: "blur" },
          { min: 2, max: 10, message: "长度 2-10 个字符", trigger: "blur" }
        ],
        buildingType: [{ required: true, message: "请选择宿舍楼类型", trigger: "blur" }]
      }
    };
  }
};
</script>