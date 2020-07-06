<template>
  <div class="app-container">
    <el-container>
      <el-aside width="30%">
        <role-tree @getClickRoleUk="getClickRoleUkM"></role-tree>
      </el-aside>
      <el-main>
        <role-permission :role-uk="roleUk" @getCheckedPermissions="getCheckedPermissionsM"></role-permission>
      </el-main>
      <el-aside width="20%">
        <div class="tip">
          <p>操作</p>
        </div>
        <el-row>
          <el-button type="primary" size="small" @click="updateRolePermissionsM">更新角色权限</el-button>
        </el-row>
      </el-aside>
    </el-container>
  </div>
</template>

<script>
import RoleTree from './components/RoleTree'
import RolePermission from './components/RolePermission'
import { updateAdminRolePermissions } from '@/api/adminPermission'
export default {
  name: 'RoleManage',
  components: {
    RoleTree,
    RolePermission
  },
  data() {
    return {
      roleUk: '',
      checkedPermission: []
    }
  },
  created() {},
  mounted() {},
  methods: {
    getClickRoleUkM(data) {
      this.roleUk = data.toString()
    },
    getCheckedPermissionsM(data) {
      this.checkedPermission = data
    },
    updateRolePermissionsM() {
      this.$confirm('是否更新角色权限', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const data = {
          roleUk: this.roleUk,
          permissions: this.checkedPermission
        }
        updateAdminRolePermissions(data).then(response => {
          if (response.success) {
            this.$message.success('更新成功')
          }
        })
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.app-container {
  & .el-aside {
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
    padding: 0;
    margin: 0px 5px;
    // width: 50%;
  }
}
</style>
