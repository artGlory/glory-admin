<template>
  <div class="app-container">
    <el-container>
      <el-header>
        <div class="tip">
          <p>用户管理</p>
        </div>
      </el-header>
      <el-main>
        <el-form :inline="true" :model="formData" size="small">
          <el-form-item>
            <el-input v-model="formData.uk" placeholder="用户标识"></el-input>
          </el-form-item>
          <el-form-item>
            <el-input v-model="formData.username" placeholder="用户名称"></el-input>
          </el-form-item>
          <el-form-item>
            <el-input v-model="formData.roleName" placeholder="角色名称"></el-input>
          </el-form-item>
          <el-form-item>
            <el-date-picker
              v-model="formData.timeRange"
              type="datetimerange"
              :picker-options="formData.pickerOptions"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              align="right"
            ></el-date-picker>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="handleSearchM">查询</el-button>
          </el-form-item>
          <el-form-item>
            <el-button
              type="primary"
              @click="dialogAdd.dialogFormVisible=!dialogAdd.dialogFormVisible"
            >添加用户</el-button>
          </el-form-item>
        </el-form>
        <el-table :data="tableDate.data" style="width: 100%" size="medium">
          <el-table-column prop="uk" label="标识"></el-table-column>
          <el-table-column prop="username" label="用户名"></el-table-column>
          <el-table-column prop="roleName" label="角色名称"></el-table-column>
          <el-table-column prop="createTime" label="创建时间"></el-table-column>
          <el-table-column v-if="false" prop="updateTime" label="修改时间"></el-table-column>
          <el-table-column fixed="right" label="操作" width="200">
            <template slot-scope="scope">
              <el-button type="text" size="small" @click="showInfoM(scope.row)">查看与编辑</el-button>
              <el-button type="text" size="small" @click="deleteInfoM(scope.row)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
        <el-pagination
          :current-page="paginationData.currentPage"
          :page-sizes="[10, 20, 50, 100]"
          :page-size="paginationData.pageSize"
          :total="paginationData.total"
          layout="total, sizes, prev, pager, next, jumper"
          @size-change="handleSizeChangeM"
          @current-change="handleCurrentChangeM"
        ></el-pagination>
      </el-main>
    </el-container>
    <!-- 用户信息查看与更改 -->
    <el-dialog title="用户信息" :visible.sync="dialog01.dialogFormVisible">
      <el-form :model="dialog01.form" label-position="left" label-width="80px" size="mini">
        <el-form-item label="用户名称">
          <el-input v-model="dialog01.form.username" autocomplete="off" disabled></el-input>
        </el-form-item>
        <el-form-item label="角色名称">
          <el-select v-model="dialog01.form.roleUk" placeholder="请选择">
            <el-option
              v-for="item in roleList"
              :key="item.uk"
              :label="item.roleName"
              :value="item.uk"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="创建时间">
          <el-input v-model="dialog01.form.createTime" autocomplete="off" disabled></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialog01.dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="updateAdminInfoM">确 定</el-button>
      </div>
    </el-dialog>
    <!-- 添加用户 -->
    <el-dialog title="用户信息" :visible.sync="dialogAdd.dialogFormVisible">
      <el-form :model="dialogAdd.form" label-position="left" label-width="80px" size="mini">
        <el-form-item label="用户名称">
          <el-input v-model="dialogAdd.form.username" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="角色名称">
          <el-select v-model="dialogAdd.form.roleUk" placeholder="请选择">
            <el-option
              v-for="item in roleList"
              :key="item.uk"
              :label="item.roleName"
              :value="item.uk"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogAdd.dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="addAdminInfoM">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import { parseTime } from '@/utils/index'
import { getAllSelfSubRole } from '@/api/adminRole'
import {
  getPageAdmin,
  updateAdminInfo,
  addAdminInfo,
  delAdminInfo
} from '@/api/admin'
export default {
  name: 'AdminUser',
  data() {
    return {
      formData: {
        uk: '',
        username: '',
        roleName: '',
        timeRange: '',
        pickerOptions: {
          shortcuts: [
            {
              text: '最近一周',
              onClick(picker) {
                const end = new Date()
                const start = new Date()
                start.setTime(start.getTime() - 3600 * 1000 * 24 * 7)
                picker.$emit('pick', [start, end])
              }
            },
            {
              text: '最近一个月',
              onClick(picker) {
                const end = new Date()
                const start = new Date()
                start.setTime(start.getTime() - 3600 * 1000 * 24 * 30)
                picker.$emit('pick', [start, end])
              }
            },
            {
              text: '最近三个月',
              onClick(picker) {
                const end = new Date()
                const start = new Date()
                start.setTime(start.getTime() - 3600 * 1000 * 24 * 90)
                picker.$emit('pick', [start, end])
              }
            }
          ]
        }
      },
      tableDate: {
        data: []
      },
      paginationData: {
        currentPage: 1,
        pageSize: 10,
        total: 10
      },
      roleList: [],
      dialog01: {
        form: {
          uk: '',
          username: '',
          roleUk: '',
          roleName: '',
          createTime: '',
          updateTime: ''
        },
        dialogFormVisible: false
      },
      dialogAdd: {
        form: {
          uk: '',
          username: '',
          roleUk: '',
          roleName: '',
          createTime: '',
          updateTime: ''
        },
        dialogFormVisible: false
      }
    }
  },
  computed: {},
  watch: {},
  mounted() {
    this.getPageAdminUserM()
    this.getAllSelfSubRoleM()
  },
  methods: {
    showInfoM(row) {
      this.dialog01.form.uk = row.uk
      this.dialog01.form.username = row.username
      this.dialog01.form.roleUk = row.roleUk
      this.dialog01.form.roleName = row.roleName
      this.dialog01.form.createTime = row.createTime
      this.dialog01.form.updateTime = row.updateTime
      this.dialog01.dialogFormVisible = true
    },
    handleSearchM() {
      this.getPageAdminUserM()
    },
    handleSizeChangeM(val) {
      this.paginationData.pageSize = val
      this.getPageAdminUserM()
    },
    handleCurrentChangeM(val) {
      this.paginationData.currentPage = val
      this.getPageAdminUserM()
    },
    getPageAdminUserM() {
      const data = {
        size: this.paginationData.pageSize,
        page: this.paginationData.currentPage,
        username: this.formData.username.trim(),
        roleName: this.formData.roleName.trim(),
        uk: this.formData.uk.trim(),
        startTime: parseTime(this.formData.timeRange[0]),
        endTime: parseTime(this.formData.timeRange[1])
      }
      getPageAdmin(data).then(response => {
        if (response.data) {
          this.paginationData.total = response.data.allNum
          this.paginationData.currentPage = response.data.pageNum
          this.paginationData.pageSize = response.data.pageSize
          this.tableDate.data = response.data.data
        }
      })
    },
    getAllSelfSubRoleM() {
      getAllSelfSubRole().then(response => {
        if (response.data) {
          this.roleList = response.data
        }
      })
    },
    updateAdminInfoM() {
      const data = {
        uk: this.dialog01.form.uk,
        roleUk: this.dialog01.form.roleUk
      }
      updateAdminInfo(data).then(response => {
        if (response.success) {
          this.$message.success('更新成功')
          this.getPageAdminUserM()
          this.dialog01.dialogFormVisible = false
        }
      })
    },
    addAdminInfoM() {
      const data = {
        username: this.dialogAdd.form.username.trim(),
        roleUk: this.dialogAdd.form.roleUk.trim()
      }
      addAdminInfo(data).then(response => {
        if (response.success) {
          this.$message.success(response.message)
          this.dialogAdd.dialogFormVisible = false
        }
        this.getPageAdminUserM()
      })
    },
    deleteInfoM(data) {
      console.log(data)
      const params = {
        uk: data.uk
      }
      this.$confirm('用户删除后将无法恢复', '是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          delAdminInfo(params).then(response => {
            if (response.success) {
              this.$message({
                type: 'success',
                message: '删除成功!'
              })
            }
            this.getPageAdminUserM()
          })
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          })
        })
    }
  }
}
</script>
<style lang="scss" scoped>
.app-container {
  & .el-header {
    & .tip {
      padding: 8px 16px;
      background-color: #ecf8ff;
      border-radius: 4px;
      border-left: 5px solid #50bfff;
      margin: 10px 0;
      & p {
        font-size: 14px;
        color: #5e6d82;
        line-height: 1.5em;
        margin: 0px;
      }
    }
  }
  & .el-main {
    & .el-form {
    }
    & .el-table {
    }
    & .el-pagination {
      float: right;
      margin-top: 10px;
    }
  }
}
</style>
