<template>
  <div class="app-container">
    <el-container>
      <el-header>
        <div class="tip">
          <p>用户登陆日志</p>
        </div>
      </el-header>
      <el-main>
        <el-form :inline="true" :model="formInline" class="demo-form-inline" size="small">
          <el-form-item>
            <el-input v-model="formInline.userName" placeholder="用户名"></el-input>
          </el-form-item>
          <el-form-item>
            <el-input v-model="formInline.remoteIp" placeholder="登陆IP"></el-input>
          </el-form-item>
          <el-form-item>
            <el-date-picker
              v-model="formInline.timeRange"
              type="datetimerange"
              :picker-options="formInline.pickerOptions"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              align="right"
            ></el-date-picker>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="handleSearchM">查询</el-button>
          </el-form-item>
        </el-form>
        <el-table :data="tableData" style="width: 100%" size="medium">
          <el-table-column type="expand">
            <template slot-scope="props">
              <el-form label-position="left" inline class="demo-table-expand">
                <el-form-item label="用户名称">
                  <span>{{ props.row.userName }}</span>
                </el-form-item>
                <el-form-item label="用户角色">
                  <span>{{ props.row.userRoleName }}</span>
                </el-form-item>
                <el-form-item label="登陆IP">
                  <span>{{ props.row.remoteIp }}</span>
                </el-form-item>
                <el-form-item label="登陆地址">
                  <span>{{ props.row.remoteAddress }}</span>
                </el-form-item>
                <el-form-item label="备注">
                  <span>{{ props.row.remark }}</span>
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
          <el-table-column label="用户名" prop="userName"></el-table-column>
          <el-table-column label="登陆IP" prop="remoteIp"></el-table-column>
          <el-table-column label="登陆地址" prop="remoteAddress"></el-table-column>
          <el-table-column label="登陆时间" prop="createTime"></el-table-column>
        </el-table>
        <el-pagination
          :current-page="paginationData.currentPage"
          :page-sizes="[10, 20, 50, 100]"
          :page-size="paginationData.pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="paginationData.total"
          @current-change="handleCurrentChangeM"
          @size-change="handleSizeChangeM"
        ></el-pagination>
      </el-main>
    </el-container>
  </div>
</template>
<script>
import { getPageLoginLog } from '@/api/adminLoginLog'
import { parseTime } from '@/utils/index'
export default {
  name: 'AdminLoginLog',
  data() {
    return {
      formInline: {
        userName: '',
        remoteIp: '',
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
      tableData: [],
      paginationData: {
        currentPage: 1,
        pageSize: 10,
        total: 10
      }
    }
  },
  mounted() {
    this.getPageLoginLogM()
  },
  methods: {
    handleSearchM() {
      this.getPageLoginLogM()
    },
    handleCurrentChangeM(val) {
      this.paginationData.currentPage = val
      this.getPageLoginLogM()
    },
    handleSizeChangeM(val) {
      this.paginationData.pageSize = val
      this.getPageLoginLogM()
    },
    getPageLoginLogM() {
      const data = {
        page: this.paginationData.currentPage,
        size: this.paginationData.pageSize,
        userName: this.formInline.userName.trim(),
        remoteIp: this.formInline.remoteIp.trim(),
        startTime: parseTime(this.formInline.timeRange[0]),
        endTime: parseTime(this.formInline.timeRange[1])
      }
      getPageLoginLog(data).then(response => {
        if (response.data) {
          this.tableData = response.data.data
          this.paginationData.total = response.data.allNum
          this.paginationData.currentPage = response.data.pageNum
          this.paginationData.pageSize = response.data.pageSize
        }
      })
    }
  }
}
</script>
<style lang="scss" scoped>
.app-container {
  & .el-container {
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
      & .el-pagination {
        float: right;
        margin-top: 10px;
      }
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
