<template>
    <div>
        <el-button
            @click="addClick(row)"
            type="success"
        >添加</el-button>
        <el-table
            :data="tableData"
            stripe
            style="width: 100%"
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
            </el-table-column>
            <el-table-column
                align="center"
                prop="dorm"
                label="宿舍"
                width="120"
            >
            </el-table-column>
            <el-table-column
                align="center"
                label="操作"
                width="300"
            >
                <el-button
                    @click="editClick(row)"
                    type="primary"
                >编辑</el-button>
                <el-button
                    @click="deleteClick(row)"
                    type="danger"
                >删除</el-button>

            </el-table-column>

        </el-table>
        <el-pagination
            @size-change="handleSizeChange"
            @current-change="changePage"
            :page-sizes="[5, 10, 25, 50]"
            :page-size="100"
            layout="total, sizes, prev, pager, next, jumper"
            :total="10"
        >
        </el-pagination>
    </div>

</template>

<script>
export default {
  methods: {
    editClick(row) {
      console.log(row);
    },
    deleteClick(row) {
      console.log(row);
    },
    handleSizeChange(val) {
        console.log(`每页 ${val} 条`);
      },
    changePage(e) {
      console.log(e);
    },
    handleSelectionChange(row) {
      console.log(row);
    }
  },

  data() {
    return {
      tableData: [
        {
          id: 1,
          studentNo: "1840706342",
          studentName: "zky",
          sex: "男",
          dorm: "M2317"
        },
        {
          id: 2,
          studentNo: "1840706342",
          studentName: "zky",
          sex: "男",
          dorm: "M2317"
        }
      ]
    };
  }
};
</script>