<template>
  <div class="app-container">
    <el-container>
      <el-header>
        <div class="tip">
          <p>用户登陆日志--(操作结果：1成功，0失败)</p>
        </div>
      </el-header>
      <el-main>
        <el-form :inline="true" :model="formInline" class="demo-form-inline" size="small">
          <el-form-item>
            <el-input v-model="formInline.operator" placeholder="操作人"></el-input>
          </el-form-item>
          <el-form-item>
            <el-input v-model="formInline.operateName" placeholder="操作名称"></el-input>
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
            <el-select v-model="resultValue" placeholder="请选择">
              <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="handleSearchM">查询</el-button>
          </el-form-item>
        </el-form>
        <el-table :data="tableData" style="width: 100%" size="medium">
          <el-table-column type="expand">
            <template slot-scope="props">
              <el-form label-position="left" inline class="demo-table-expand">
                <el-form-item label="操作人">
                  <span>{{ props.row.operator }}</span>
                </el-form-item>
                <el-form-item label="角色名称">
                  <span>{{ props.row.operatorRoleName }}</span>
                </el-form-item>
                <el-form-item label="操作类型">
                  <span>{{ props.row.operateName }}</span>
                </el-form-item>
                <el-form-item label="操作结果">
                  <span>{{ props.row.result===1?'成功':'失败' }}</span>
                </el-form-item>
                <el-form-item label="操作路径">
                  <span>{{ props.row.operatePath }}</span>
                </el-form-item>
                <el-form-item label="参数">
                  <span>{{ props.row.params }}</span>
                </el-form-item>
                <el-form-item label="失败原因">
                  <span>{{ props.row.failReason }}</span>
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
          <el-table-column label="操作人" prop="operator"></el-table-column>
          <el-table-column label="操作类型" prop="operateName"></el-table-column>
          <el-table-column label="操作结果" prop="result" :formatter="formatResultM"></el-table-column>
          <el-table-column label="操作时间" prop="createTime"></el-table-column>
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
import { getPageOperateLog } from '@/api/adminOperateLog'
import { parseTime } from '@/utils/index'
export default {
  name: 'AdminLoginLog',
  data() {
    return {
      formInline: {
        operator: '',
        operateName: '',
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
      },
      options: [
        {
          value: '',
          label: '全部'
        },
        {
          value: '1',
          label: '成功'
        },
        {
          value: '0',
          label: '失败'
        }
      ],
      resultValue: ''
    }
  },
  mounted() {
    this.getPageOperateLogM()
  },
  methods: {
    handleSearchM() {
      this.getPageOperateLogM()
    },
    handleCurrentChangeM(val) {
      this.paginationData.currentPage = val
      this.getPageOperateLogM()
    },
    handleSizeChangeM(val) {
      this.paginationData.pageSize = val
      this.getPageOperateLogM()
    },
    getPageOperateLogM() {
      const data = {
        page: this.paginationData.currentPage,
        size: this.paginationData.pageSize,
        operator: this.formInline.operator.trim(),
        operateName: this.formInline.operateName.trim(),
        result: this.resultValue,
        startTime: parseTime(this.formInline.timeRange[0]),
        endTime: parseTime(this.formInline.timeRange[1])
      }
      getPageOperateLog(data).then(response => {
        if (response.data) {
          this.tableData = response.data.data
          this.paginationData.total = response.data.allNum
          this.paginationData.currentPage = response.data.pageNum
          this.paginationData.pageSize = response.data.pageSize
        }
      })
    },
    formatResultM(row) {
      let showInfo = row.result
      if (row.result === 1) {
        showInfo = '成功'
      } else if (row.result === 0) {
        showInfo = '失败'
      }
      return showInfo
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
