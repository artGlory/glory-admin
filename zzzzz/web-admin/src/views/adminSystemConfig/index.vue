<template>
  <div class="app-container">
    <el-container>
      <el-header>
        <div class="tip">
          <p>系统配置</p>
        </div>
      </el-header>
      <el-main>
        <el-form :inline="true" :model="formInline" class="demo-form-inline" size="small">
          <el-form-item>
            <el-input v-model="formInline.configArea" placeholder="域"></el-input>
          </el-form-item>
          <el-form-item>
            <el-input v-model="formInline.configGroup" placeholder="组"></el-input>
          </el-form-item>
          <el-form-item>
            <el-input v-model="formInline.configKey" placeholder="key"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="handleSearchM">查询</el-button>
          </el-form-item>
        </el-form>
        <el-table :data="tableData" style="width: 100%" size="medium">
          <el-table-column type="expand">
            <template slot-scope="props">
              <el-form label-position="left" inline class="demo-table-expand">
                <el-form-item label="描述">
                  <span>{{ props.row.configDesc }}</span>
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
          <el-table-column label="域" prop="configArea"></el-table-column>
          <el-table-column label="组" prop="configGroup"></el-table-column>
          <el-table-column label="key" prop="configKey"></el-table-column>
          <el-table-column label="值" prop="configValue"></el-table-column>
          <el-table-column fixed="right" label="操作" width="200">
            <template slot-scope="scope">
              <el-dropdown>
                <span class="el-dropdown-link">
                  操作
                  <i class="el-icon-arrow-down el-icon--right"></i>
                </span>
                <el-dropdown-menu slot="dropdown">
                  <el-dropdown-item @click.native="showInfoM(scope.row)">查看域编辑</el-dropdown-item>
                </el-dropdown-menu>
              </el-dropdown>
            </template>
          </el-table-column>
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
    <!-- 查看与更改 -->
    <el-dialog title="配置信息" :visible.sync="dialogInfo.dialogFormVisible">
      <el-form :model="dialogInfo.form" label-position="left" label-width="80px" size="mini">
        <el-form-item v-if="dialogInfo.ukShow" label="标识">
          <el-input v-model="dialogInfo.form.uk" autocomplete="off" disabled></el-input>
        </el-form-item>
        <el-form-item label="域">
          <el-input v-model="dialogInfo.form.configArea" autocomplete="off" disabled></el-input>
        </el-form-item>
        <el-form-item label="组">
          <el-input v-model="dialogInfo.form.configGroup" autocomplete="off" disabled></el-input>
        </el-form-item>
        <el-form-item label="key">
          <el-input v-model="dialogInfo.form.configKey" autocomplete="off" disabled></el-input>
        </el-form-item>
        <el-form-item label="值">
          <el-input v-model="dialogInfo.form.configValue" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="描述">
          <el-input v-model="dialogInfo.form.configDesc" autocomplete="off" type="textarea"></el-input>
        </el-form-item>
        <el-form-item label="插入时间">
          <el-input v-model="dialogInfo.form.createTime" autocomplete="off" disabled></el-input>
        </el-form-item>
        <el-form-item label="更新时间">
          <el-input v-model="dialogInfo.form.updateTime" autocomplete="off" disabled></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogInfo.dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="updateConfigInfoM">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import { getPageConfig, updateSystemConfig } from '@/api/system'
export default {
  name: 'AdminSystemConfig',
  data() {
    return {
      formInline: {
        configArea: '',
        configGroup: '',
        configKey: ''
      },
      tableData: [],
      paginationData: {
        currentPage: 1,
        pageSize: 10,
        total: 10
      },
      dialogInfo: {
        ukShow: false,
        form: {
          uk: '',
          configGroup: '',
          configArea: '',
          configKey: '',
          configValue: '',
          configDesc: '',
          createTime: '',
          updateTime: ''
        },
        dialogFormVisible: false
      }
    }
  },
  mounted() {
    this.getPageConfigM()
  },
  methods: {
    updateConfigInfoM() {
      const data = {
        uk: this.dialogInfo.form.uk,
        configValue: this.dialogInfo.form.configValue.trim(),
        configDesc: this.dialogInfo.form.configDesc.trim()
      }
      updateSystemConfig(data).then(response => {
        if (response.success) {
          this.$message.success('更新成功')
          this.dialogInfo.dialogFormVisible = false
          this.getPageConfigM()
        }
      })
    },
    showInfoM(data) {
      this.dialogInfo.form.uk = data.uk
      this.dialogInfo.form.configArea = data.configArea
      this.dialogInfo.form.configGroup = data.configGroup
      this.dialogInfo.form.configKey = data.configKey
      this.dialogInfo.form.configValue = data.configValue
      this.dialogInfo.form.configDesc = data.configDesc
      this.dialogInfo.form.createTime = data.createTime
      this.dialogInfo.form.updateTime = data.updateTime
      this.dialogInfo.dialogFormVisible = true
    },
    handleSearchM() {
      this.getPageConfigM()
    },
    handleCurrentChangeM(val) {
      this.paginationData.currentPage = val
      this.getPageConfigM()
    },
    handleSizeChangeM(val) {
      this.paginationData.pageSize = val
      this.getPageConfigM()
    },
    getPageConfigM() {
      const data = {
        page: this.paginationData.currentPage,
        size: this.paginationData.pageSize,
        configArea: this.formInline.configArea.trim(),
        configGroup: this.formInline.configGroup.trim(),
        configKey: this.formInline.configKey.trim()
      }
      getPageConfig(data).then(response => {
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
