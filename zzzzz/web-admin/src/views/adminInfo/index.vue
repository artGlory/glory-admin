<template>
  <div class="app-container">
    <el-container>
      <el-header>
        <div class="tip">
          <p>个人信息</p>
        </div>
      </el-header>
      <el-main>
        <el-form :inline="true" :model="formInline" class="demo-form-inline" size="mini">
          <el-form-item>
            <el-button type="primary" @click="updatePasswordDialog.dialogFormVisible=true">修改密码</el-button>
          </el-form-item>
          <el-form-item v-if="isBindGoogleAuthentication==false">
            <el-button type="primary" @click="getGoogleKeyM">开通google</el-button>
          </el-form-item>
          <el-form-item v-if="isBindGoogleAuthentication&&isLoginWithGoogleAuthentication==false">
            <el-button type="primary" @click="loginWithGooleAuthenticationM">开通google登陆</el-button>
          </el-form-item>
          <el-form-item v-if="isBindGoogleAuthentication&&isLoginWithGoogleAuthentication">
            <el-button type="primary" @click="loginWithGooleAuthenticationM">关闭google登陆</el-button>
          </el-form-item>
        </el-form>
        <el-card class="operate-log">
          <div slot="header" class="clearfix">
            <span>操作日志</span>
          </div>
          <el-timeline>
            <el-timeline-item
              v-for="(activity, index) in operateActivities"
              :key="index"
              :icon="activity.icon"
              :color="activity.color"
              :size="activity.size"
              :timestamp="activity.logTime"
            >{{ activity.content }}</el-timeline-item>
          </el-timeline>
        </el-card>
      </el-main>
    </el-container>
    <!-- 更新密码 -->
    <el-dialog title="更新密码" :visible.sync="updatePasswordDialog.dialogFormVisible">
      <el-form
        :model="updatePasswordDialog.form"
        label-position="left"
        label-width="80px"
        size="mini"
      >
        <el-form-item label="旧密码">
          <el-input v-model="updatePasswordDialog.form.passwordOld" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="新密码">
          <el-input
            v-model="updatePasswordDialog.form.passwordNew1"
            autocomplete="off"
            show-password
          ></el-input>
        </el-form-item>
        <el-form-item label="请重复新密码">
          <el-input
            v-model="updatePasswordDialog.form.passwordNew2"
            autocomplete="off"
            show-password
          ></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="updatePasswordDialog.dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="updatePasswordM">确 定</el-button>
      </div>
    </el-dialog>
    <!-- 绑定Google身份验证 -->
    <el-dialog
      title="绑定Google身份验证"
      :visible.sync="googleAuthenticationDialog.dialogFormVisible"
      class="googleKeyDialog"
    >
      <div class="step-01">
        <h4>
          <svg-icon icon-class="copy_1" />&nbsp;下载Google身份验证器
          <a
            href="https://support.google.com/accounts/answer/1066447"
            target="_black"
            style="color:#1890ff"
          >（点击查看使用说明）</a>
        </h4>
        <div
          v-for="(item,i) in googleAuthenticationDialog.downloadAddress"
          :key="i"
          class="download-block"
        >
          <span>{{ item.name }}</span>
          <qrcode-vue :value="item.url" :size="150" level="H"></qrcode-vue>
        </div>
      </div>
      <div class="step-02">
        <h4>
          <svg-icon icon-class="copy_2" />&nbsp;用Google身份验证器扫描下方二维码绑定，绑定完毕后输入谷歌动态密码
        </h4>
        <div>
          <div class="block">
            <el-image :src="googleAuthenticationDialog.googleAuthenInfo.googleImg" :fit="imgType"></el-image>
            <span></span>
          </div>
          <div class="input-block">
            <span>动态密码：</span>
            <el-input
              v-model="googleAuthenticationDialog.form.googleCode"
              autofocus
              autocomplete="off"
              maxlength="6"
              show-word-limit
            ></el-input>
          </div>
        </div>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="googleAuthenticationDialog.dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="bindGoogleKeyM">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import {
  getSelfOperateTimeline,
  updateSelfPassword,
  getGoogleKey,
  bindGoogleKey,
  loginWithGooleAuthentication
} from '@/api/admin'
import { getGoogleAuthenticationDownload } from '@/api/system'
import QrcodeVue from 'qrcode.vue'
import { mapGetters } from 'vuex'
export default {
  name: 'AdminInfo',
  components: {
    QrcodeVue
  },
  data() {
    return {
      imgType: 'fill',
      formInline: {},
      operateActivities: [],
      updatePasswordDialog: {
        form: {
          passwordOld: '',
          passwordNew1: '',
          passwordNew2: ''
        },
        dialogFormVisible: false
      },
      googleAuthenticationDialog: {
        googleAuthenInfo: {
          googleKey: '',
          googleImg: ''
        },
        form: {
          googleCode: ''
        },
        dialogFormVisible: false,
        downloadAddress: []
      }
    }
  },
  computed: {
    ...mapGetters([
      'isBindGoogleAuthentication',
      'isLoginWithGoogleAuthentication'
    ])
  },
  mounted() {
    this.getSelfOperateTimelineM()
  },
  methods: {
    getSelfOperateTimelineM() {
      getSelfOperateTimeline().then(response => {
        if (response.data) {
          this.operateActivities = response.data
        }
      })
    },
    updatePasswordM() {
      if (
        this.updatePasswordDialog.form.passwordNew1 !==
        this.updatePasswordDialog.form.passwordNew2
      ) {
        this.$message.error('两次输入的新密码不一致')
        return
      }
      const data = {
        passwordOld: this.updatePasswordDialog.form.passwordOld.trim(),
        passwordNew: this.updatePasswordDialog.form.passwordNew1.trim()
      }
      updateSelfPassword(data).then(response => {
        if (response.success) {
          this.$message.success('密码更新成功!! 5秒后退出系统,请重新登陆')
          this.updatePasswordDialog.dialogFormVisible = false
          setTimeout(() => {
            this.logout()
          }, 5000)
        }
      })
    },
    async logout() {
      await this.$store.dispatch('admin/logout')
      this.$router.push(`/login?redirect=${this.$route.fullPath}`)
    },
    getGoogleKeyM() {
      this.googleAuthenticationDialog.dialogFormVisible = true
      this.getGoogleAuthenticationDownloadM()
      getGoogleKey().then(response => {
        if (response.data) {
          this.googleAuthenticationDialog.googleAuthenInfo.googleKey =
            response.data.googleKey
          this.googleAuthenticationDialog.googleAuthenInfo.googleImg =
            response.data.googleKeyPicture
        }
      })
    },
    bindGoogleKeyM() {
      const data = {
        googleKey: this.googleAuthenticationDialog.googleAuthenInfo.googleKey,
        googleCode: this.googleAuthenticationDialog.form.googleCode
      }
      bindGoogleKey(data).then(response => {
        if (response.success) {
          this.$message.success('绑定成功')
          this.googleAuthenticationDialog.dialogFormVisible = false
          this.changeUserInfoM()
        }
      })
    },
    getGoogleAuthenticationDownloadM() {
      getGoogleAuthenticationDownload().then(response => {
        if (response.data) {
          this.googleAuthenticationDialog.downloadAddress = response.data
        }
      })
    },
    loginWithGooleAuthenticationM() {
      var message
      if (this.isLoginWithGoogleAuthentication) {
        message = '取消Google两步登陆，会降低你的账号安全系数。是否继续？'
      } else {
        message = '增加Google两步登陆。是否继续？'
      }
      this.$confirm(message, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          const data = {
            loginWithGoogle: !this.isLoginWithGoogleAuthentication
          }
          loginWithGooleAuthentication(data).then(response => {
            if (response.success) {
              this.$message.success('修改成功!! 5秒后退出系统,请重新登陆')
              this.changeUserInfoM()
              setTimeout(() => {
                this.logout()
              }, 5000)
            }
          })
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '已取消操作'
          })
        })
    },
    changeUserInfoM() {
      this.$store.dispatch('admin/getInfo')
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
      & .operate-log {
        width: 30%;
        overflow: auto;
      }
    }
  }
  & .googleKeyDialog {
    & .svg-icon {
      color: #fa8c16;
      width: 1.5em;
      height: 1.5em;
    }
    & .step-01 {
      & .download-block {
        display: inline-block;
        width: 50%;
        text-align: center;
        vertical-align: top;
        & span {
          display: block;
          text-align: center;
          font-weight: 600;
          font-size: 17px;
        }
      }
    }
    & .step-02 {
      & .block {
        display: inline-block;
        width: 50%;
        text-align: center;
        vertical-align: top;
      }
      & .input-block {
        display: inline-block;
        width: 49%;
        text-align: center;
        padding-top: 8%;
        & span {
          width: 20%;
          font-weight: 700;
        }
        & .el-input {
          width: 40%;
        }
      }
    }
  }
}
</style>
