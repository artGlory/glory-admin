<template>
  <div>
    <div class="tip">
      <p>角色权限管理</p>
    </div>
    <el-tree
      ref="permissionTreeE"
      v-loading="permissionTree.loading"
      :data="permissionTree.data"
      node-key="uk"
      default-expand-all
      show-checkbox
      :default-checked-keys="checkedKeysC"
      :props="permissionTree.defaultProps"
      :expand-on-click-node="false"
      @check-change="handleCheckChangeM"
    ></el-tree>
  </div>
</template>
<script>
import { getPermissionTree } from '@/api/adminPermission'
export default {
  nme: 'RolePermission',
  props: {
    roleUk: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
      permissionTree: {
        data: [],
        defaultProps: {
          children: 'children',
          label: 'privilegeName'
        },
        checkedKeys: [],
        loading: false
      }
    }
  },
  computed: {
    checkedKeysC(val, oldVal) {
      this.permissionTree.checkedKeys.length
      return this.permissionTree.checkedKeys
    }
  },
  watch: {
    roleUk(val, oldVal) {
      this.getPermissionDataM()
    },
    checkedKeysC(val, oldVal) {
      this.$emit('getCheckedPermissions', val)
    }
  },
  mounted() {
    // this.getPermissionDataM()
  },
  methods: {
    handleCheckChangeM(data, checked, indeterminate) {
      this.getCheckedKeys()
    },
    getCheckedKeys() {
      this.permissionTree.checkedKeys = this.$refs.permissionTreeE.getCheckedKeys()
    },
    getPermissionDataM() {
      this.permissionTree.loading = true
      let data = {}
      if (this.roleUk !== '') {
        data = {
          roleUk: this.roleUk
        }
      }
      getPermissionTree(data).then(response => {
        if (response.data) {
          this.permissionTree.data = response.data.adminPrivilegeTreeVOList
          this.permissionTree.checkedKeys = response.data.checkPermission
        } else {
          this.$message.error('获取角色权限失败')
        }
      })
      this.permissionTree.loading = false
    }
  }
}
</script>
<style lang="scss" scoped>
.tip {
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
</style>
