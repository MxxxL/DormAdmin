<template>
  <div>
    <el-button
      style="margin-top:20px;margin-bottom:10px;"
      @click="showAddStudent"
      type="success"
    >添加</el-button>
    <el-button
      type="warning"
      @click="deleteStudents"
      :disabled="this.ids.length === 0"
    >
      批量删除
    </el-button>
    <div style="display: flex;justify-content: space-between">
      <el-input
        placeholder="请输入学生姓名进行搜索"
        prefix-icon="el-icon-search"
        clearable
        @clear="initStudent"
        style="width: 350px;margin-right: 10px"
        v-model="keyword"
        @keydown.enter.native="initStudent"
      >
      </el-input>
      <el-button
        icon="el-icon-search"
        type="primary"
        @click="initStudent"
      >
        搜索
      </el-button>
    </div>
    <el-table
      :data="studentList"
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
        prop="studentNo"
        label="学号"
        align="center"
        width="150"
      >
      </el-table-column>
      <el-table-column
        prop="studentName"
        label="姓名"
        align="center"
        width="100"
      >
      </el-table-column>
      <el-table-column
        prop="sex"
        label="性别"
        align="center"
        width="70"
      >
        <template slot-scope="scope">
          <span v-if="scope.row.sex == 1">男</span>
          <span v-else>女</span>
        </template>
      </el-table-column>
      <el-table-column
        align="center"
        prop="dorm.id"
        label="dormId"
        v-if="false"
      >
      </el-table-column>
      <el-table-column
        align="center"
        prop="dorm.name"
        label="宿舍"
        width="120"
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
            @click="showEditStudent(scope.row)"
            type="primary"
          >编辑</el-button>
          <el-button
            icon="el-icon-delete"
            size="mini"
            @click="deleteStudent(scope.row)"
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
        ref="studentForm"
        :model="student"
        :rules="studentFormRules"
        label-width="70px"
      >
        <el-form-item
          label="学号"
          prop="studentNo"
        >
          <el-input v-model="student.studentNo"></el-input>
        </el-form-item>
        <el-form-item
          label="姓名"
          prop="studentName"
        >
          <el-input v-model="student.studentName"></el-input>
        </el-form-item>
        <el-form-item
          label="性别"
          prop="sex"
        >
          <el-radio-group v-model="student.sex">
            <el-radio :label="1">男</el-radio>
            <el-radio :label="0">女</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item
          label="宿舍"
          prop="dormId"
        >
          <el-select
            v-model="student.dormId"
            placeholder="请选择所属宿舍"
            clearable
          >
            <el-option
              v-for="dorm in dormList"
              :key="dorm.id"
              :label="dorm.name"
              :value="dorm.id"
            >
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <span
        slot="footer"
        class="dialog-footer"
      >
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button
          type="primary"
          @click="addOrEditStudent"
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
      this.initStudent();
    },
    changePage(e) {
      this.currentPage = e;
      this.initStudent();
    },
    handleSelectionChange(row) {
      this.ids = row;
    },
    initStudent() {
      this.loading = true;
      const params = {
        page: this.currentPage,
        size: this.pageSize
      };
      if (this.keyword != null && this.keyword != "") {
        params.name = this.keyword;
      }
      this.getRequest("/api/student/list", params).then(resp => {
        this.loading = false;
        if (resp) {
          this.studentList = resp.data;
          this.total = resp.total;
        }
      });
    },
    initDorm() {
      this.getRequest("/api/dorm/all").then(resp => {
        if (resp) {
          this.dormList = resp;
        }
      });
    },
    emptyStudent() {
      this.student = {
        studentNo: "",
        studentName: "",
        sex: 1,
        dormId: null
      };
    },
    showAddStudent() {
      this.title = "添加学生";
      this.emptyStudent();
      this.dialogVisible = true;
    },
    showEditStudent(data) {
      this.title = "编辑学生";
      this.student = data;
      this.student.dormId = data.dormId;
      this.dialogVisible = true;
    },
    addOrEditStudent() {
      if (this.student.id) {
        this.$refs["studentForm"].validate(valid => {
          if (valid) {
            this.postRequest("/api/student/add", this.student).then(resp => {
              if (resp) {
                this.dialogVisible = false;
                this.initStudent();
              }
            });
          }
        });
      } else {
        this.$refs["studentForm"].validate(valid => {
          if (valid) {
            this.postRequest("/api/student/add", this.student).then(resp => {
              if (resp) {
                this.dialogVisible = false;
                this.initStudent();
              }
            });
          }
        });
      }
    },
    deleteStudent(data) {
      this.$confirm(
        "此操作将永久删除【" + data.studentName + "】这行数据, 是否继续?",
        "提示",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }
      )
        .then(() => {
          this.postRequest("/api/student/del", [data]).then(resp => {
            if (resp) {
              this.$message({
                type: "info",
                message: resp.msg
              });
              this.initStudent();
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
    deleteStudents() {
      this.$confirm("此操作将永久删除选择数据, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          this.postRequest("/api/student/del", this.ids).then(resp => {
            if (resp) {
              this.$message({
                type: "info",
                message: resp.msg
              });
              this.ids = [];
              this.initStudent();
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
    this.initStudent();
    this.initDorm();
  },
  data() {
    return {
      ids: [],
      student: {
        studentNo: "",
        studentName: "",
        sex: 1,
        dormId: 0
      },
      studentList: [],
      dormList: [],
      pageSizes: [5, 10, 25, 50],
      pageSize: 5,
      total: 0,
      currentPage: 1,
      keyword: "",
      dialogVisible: false,
      title: "",
      // 添加表单的验证规则对象
      studentFormRules: {
        studentNo: [
          { required: true, message: "请输入学号", trigger: "blur" },
          { min: 10, max: 10, message: "长度 10 个字符", trigger: "blur" }
        ],
        studentName: [
          { required: true, message: "请输入姓名", trigger: "blur" },
          { min: 2, max: 10, message: "长度在 2 到 10 个字符", trigger: "blur" }
        ],
        sex: [{ required: true, message: "请选择性别", trigger: "blur" }]
      }
    };
  }
};
</script>