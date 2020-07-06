<template>
  <div>
    <div class="tip">
      <p>角色管理</p>
    </div>
    <el-tree
      ref="roleTreeE"
      v-loading="roleTree.loading"
      :data="roleTree.data"
      node-key="uk"
      default-expand-all
      highlight-current
      :props="roleTree.defaultProps"
      :expand-on-click-node="false"
      :default-checked-keys="roleTree.nowClickNode.uk"
      @node-click="handleNodeClickM"
    >
      <span slot-scope="{ node, data }" class="tree-node">
        <span>{{ data.roleName }}</span>
        <span>
          <el-button type="text" size="mini" @click="() => showUpdateRoleM(data)">查看与编辑</el-button>
          <el-button type="text" size="mini" @click="() => handleAddRoleM(data)">添加下级</el-button>
          <el-button type="text" size="mini" @click="() => removeRoleM(data)">删除</el-button>
        </span>
      </span>
    </el-tree>
    <!-- 角色详细和更改 -->
    <el-dialog title="角色详细" :visible.sync="roleTree.nowRoleDialogVisible">
      <el-form :model="roleTree.nowClickNode" label-position="left" label-width="60px" size="mini">
        <el-form-item label="名称">
          <el-input v-model="roleTree.nowClickNode.roleName"></el-input>
        </el-form-item>
        <el-form-item label="详细">
          <el-input v-model="roleTree.nowClickNode.roleDesc" type="textarea"></el-input>
        </el-form-item>
        <el-form-item label="cTime">
          <el-input v-model="roleTree.nowClickNode.createTime" disabled></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="roleTree.nowRoleDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="updateRoleInfoM ">更 新</el-button>
      </div>
    </el-dialog>
    <!-- 角色添加 -->
    <el-dialog title="添加角色" :visible.sync="roleTree.addRoleDialogVisible">
      <el-form :model="roleTree.newRole" label-position="left" label-width="60px" size="mini">
        <el-form-item label="名称">
          <el-input v-model="roleTree.newRole.roleName"></el-input>
        </el-form-item>
        <el-form-item label="详细">
          <el-input v-model="roleTree.newRole.roleDesc" type="textarea"></el-input>
        </el-form-item>
        <el-form-item label="父角色">
          <el-input v-model="roleTree.nowClickNode.roleName" disabled></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="roleTree.addRoleDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="addRoleInfoM ">新 增</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  getRolesTree,
  updateRoleInfo,
  deleteRoleInfo,
  addRoleInfo
} from '@/api/adminRole'
export default {
  name: 'RoleTree',
  data() {
    return {
      roleTree: {
        data: [],
        defaultProps: {
          label: 'roleName',
          children: 'children'
        },
        loading: false,
        nowClickNode: {
          uk: [],
          roleName: '',
          roleDesc: '',
          parentUk: '',
          createTime: '',
          updateTime: ''
        },
        nowRoleDialogVisible: false,
        addRoleDialogVisible: false,
        newRole: {
          uk: '',
          roleName: '',
          roleDesc: '',
          parentUk: '',
          createTime: '',
          updateTime: ''
        }
      }
    }
  },
  computed: {
    nowUk() {
      this.roleTree.nowClickNode.roleName
      return this.roleTree.nowClickNode.uk[0]
    }
  },
  watch: {
    nowUk(val, oldVal) {
      this.$emit('getClickRoleUk', val)
    }
  },
  created() {},
  mounted() {
    this.getAllRolesTreeM()
  },
  methods: {
    handleNodeClickM(data) {
      this.roleTree.nowClickNode.uk[0] = data.uk
      this.roleTree.nowClickNode.roleName = data.roleName
      this.roleTree.nowClickNode.roleDesc = data.roleDesc
      this.roleTree.nowClickNode.parentUk = data.parentUk
      this.roleTree.nowClickNode.createTime = data.createTime
      this.roleTree.nowClickNode.updateTime = data.updateTime
    },
    getAllRolesTreeM() {
      this.roleTree.loading = true
      getRolesTree().then(response => {
        if (response.data) {
          this.roleTree.data = response.data
          this.roleTree.nowClickNode.uk[0] = this.roleTree.data[0].uk
          this.roleTree.nowClickNode.roleName = this.roleTree.data[0].roleName
          this.roleTree.nowClickNode.roleDesc = this.roleTree.data[0].roleDesc
          this.roleTree.nowClickNode.parentUk = this.roleTree.data[0].parentUk
          this.roleTree.nowClickNode.createTime = this.roleTree.data[0].createTime
          this.roleTree.nowClickNode.updateTime = this.roleTree.data[0].updateTime
        } else {
          this.$message.error('获取角色失败')
        }
      })
      this.roleTree.loading = false
    },
    showUpdateRoleM(data) {
      this.handleNodeClickM(data)
      this.roleTree.nowRoleDialogVisible = true
    },
    removeRoleM(data) {
      this.handleNodeClickM(data)
      const requestData = {
        roleUk: this.roleTree.nowClickNode.uk[0]
      }
      deleteRoleInfo(requestData).then(response => {
        this.roleTree.nowRoleDialogVisible = false
        this.$message.success('操作成功')
        this.getAllRolesTreeM()
      })
    },
    updateRoleInfoM() {
      const data = {
        uk: this.roleTree.nowClickNode.uk[0],
        roleName: this.roleTree.nowClickNode.roleName,
        roleDesc: this.roleTree.nowClickNode.roleDesc
      }
      updateRoleInfo(data).then(response => {
        if (response.success === true) {
          this.roleTree.nowRoleDialogVisible = false
          this.$message.success('操作成功')
          this.getAllRolesTreeM()
        }
      })
    },
    handleAddRoleM(data) {
      this.handleNodeClickM(data)
      this.roleTree.addRoleDialogVisible = true
    },
    addRoleInfoM() {
      const data = {
        roleName: this.roleTree.newRole.roleName,
        roleDesc: this.roleTree.newRole.roleDesc,
        parentUk: this.roleTree.nowClickNode.uk[0]
      }
      addRoleInfo(data).then(response => {
        if (response.success === true) {
          this.roleTree.addRoleDialogVisible = false
          this.$message.success('操作成功')
          this.getAllRolesTreeM()
        }
      })
    },
    sentToParent() {
      this.$emit('uk', this.roleTree.nowClickNode.uk[0])
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
.tree-node {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: space-between;
  font-size: 14px;
  padding-right: 8px;
}
</style>
