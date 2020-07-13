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
          <el-table-column type="expand">
            <template slot-scope="props">
              <el-form label-position="left" inline class="demo-table-expand">
                <el-form-item label="标识">
                  <span>{{ props.row.uk }}</span>
                </el-form-item>
                <el-form-item label="cTime">
                  <span>{{ props.row.createTime }}</span>
                </el-form-item>
                <el-form-item label="uTime">
                  <span>{{ props.row.updateTime }}</span>
                </el-form-item>
              </el-form>
            </template>
          </el-table-column>
          <el-table-column prop="username" label="用户名"></el-table-column>
          <el-table-column prop="roleName" label="角色名称"></el-table-column>
          <el-table-column prop="googleLogin" label="是否Google登陆" :formatter="formatGoogleLoginM"></el-table-column>
          <el-table-column prop="forbidLogin" label="登陆状态" :formatter="formatForbidLoginResultM"></el-table-column>
          <el-table-column prop="createTime" label="创建时间"></el-table-column>
          <el-table-column v-if="false" prop="updateTime" label="修改时间"></el-table-column>
          <el-table-column fixed="right" label="操作" width="200">
            <template slot-scope="scope">
              <el-dropdown>
                <span class="el-dropdown-link">
                  操作
                  <i class="el-icon-arrow-down el-icon--right"></i>
                </span>
                <el-dropdown-menu slot="dropdown">
                  <el-dropdown-item @click.native="showUserRoleM(scope.row)">修改用户角色</el-dropdown-item>
                  <el-dropdown-item @click.native="updateUserPasswordM(scope.row)">修改用户密码</el-dropdown-item>
                  <el-dropdown-item @click.native="updateAdminLoginStatusM(scope.row)">修改用户登陆状态</el-dropdown-item>
                  <el-dropdown-item @click.native="deleteGoogleBindM(scope.row)">删除Google绑定</el-dropdown-item>
                  <el-dropdown-item @click.native="deleteInfoM(scope.row)">删除用户</el-dropdown-item>
                </el-dropdown-menu>
              </el-dropdown>
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
    <!-- 修改用户角色 -->
    <el-dialog title="修改用户角色" :visible.sync="dialog01.dialogFormVisible">
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
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialog01.dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="updateAdminRoleM">确 定</el-button>
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
    <!-- 修改用户密码 -->
    <el-dialog title="修改用户密码" :visible.sync="dialogUpdatePassword.dialogFormVisible">
      <el-form
        :model="dialogUpdatePassword.form"
        label-position="left"
        label-width="80px"
        size="mini"
      >
        <el-form-item label="新密码">
          <el-input v-model="dialogUpdatePassword.form.passwordNew" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogUpdatePassword.dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="updateAdminPasswordM">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import { parseTime } from '@/utils/index'
import { getAllSubRole } from '@/api/adminRole'
import {
  getPageAdmin,
  updateAdminRole,
  addAdminInfo,
  delAdminInfo,
  updateAdminPassword,
  forbidAdminLogin,
  deleteAdminGoogleKey
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
      },
      dialogUpdatePassword: {
        form: {
          uk: '',
          passwordNew: ''
        },
        dialogFormVisible: false
      }
    }
  },
  computed: {},
  watch: {},
  mounted() {
    this.getPageAdminUserM()
    this.getAllSubRoleM()
  },
  methods: {
    updateUserPasswordM(data) {
      this.dialogUpdatePassword.form.uk = data.uk
      this.dialogUpdatePassword.dialogFormVisible = true
    },
    updateAdminPasswordM() {
      const data = {
        uk: this.dialogUpdatePassword.form.uk,
        passwordNew: this.dialogUpdatePassword.form.passwordNew
      }
      updateAdminPassword(data).then(response => {
        if (response.success) {
          this.$message.success('修改成功')
          this.dialogUpdatePassword.dialogFormVisible = false
        }
      })
    },
    showUserRoleM(row) {
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
    getAllSubRoleM() {
      getAllSubRole().then(response => {
        if (response.data) {
          this.roleList = response.data
        }
      })
    },
    updateAdminRoleM() {
      const data = {
        uk: this.dialog01.form.uk,
        roleUk: this.dialog01.form.roleUk
      }
      updateAdminRole(data).then(response => {
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
      const params = {
        uk: data.uk
      }
      this.$confirm('用户删除后将无法恢复, 是否继续', '提示', {
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
    },
    updateAdminLoginStatusM(data) {
      console.log(data)
      const params = {
        uk: data.uk
      }
      const showMessage =
        data.forbidLogin === 1
          ? '允许用户登陆, 是否继续'
          : '禁止用户登陆, 是否继续'
      this.$confirm(showMessage, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          forbidAdminLogin(params).then(response => {
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
            message: '已取消操作'
          })
        })
    },
    deleteGoogleBindM(data) {
      console.log(data)
      const params = {
        uk: data.uk
      }
      const showMessage = '删除Google绑定，会降低账号的安全系数, 是否继续'
      this.$confirm(showMessage, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          deleteAdminGoogleKey(params).then(response => {
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
            message: '已取消操作'
          })
        })
    },
    formatGoogleLoginM(row) {
      let showMessage = row.googleLogin
      if (row.googleLogin === 1) {
        showMessage = '是'
      } else if (row.googleLogin === 0) {
        showMessage = '否'
      }
      return showMessage
    },
    formatForbidLoginResultM(row) {
      let showMessage = row.forbidLogin
      if (row.forbidLogin === 1) {
        showMessage = '禁止登陆'
      } else if (row.forbidLogin === 0) {
        showMessage = '允许登陆'
      }
      return showMessage
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
      & .el-dropdown-link {
        cursor: pointer;
        color: #409eff;
      }
    }
    & .el-pagination {
      float: right;
      margin-top: 10px;
    }
  }
}
.demo-table-expand {
  font-size: 0;
}
.demo-table-expand label {
  width: 90px;
  color: #99a9bf;
}
.demo-table-expand .el-form-item {
  margin-right: 1%;
  margin-bottom: 0;
}
</style>
