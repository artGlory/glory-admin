<template>
  <section class="app-main">
    <transition name="fade-transform" mode="out-in">
      <router-view :key="key" />
    </transition>
    <el-tooltip placement="top" content="返回顶部">
      <back-to-top
        :visibility-height="300"
        :back-position="0"
        transition-name="fade"
      />
    </el-tooltip>
  </section>
</template>

<script>
import BackToTop from '@/components/BackToTop'
export default {
  name: 'AppMain',
  components: { BackToTop },
  computed: {
    key() {
      return this.$route.path
    }
  }
}
</script>

<style scoped>
.app-main {
  /*50 = navbar  */
  min-height: calc(100vh - 50px);
  width: 100%;
  position: relative;
  overflow: hidden;
}
.fixed-header + .app-main {
  padding-top: 50px;
}
</style>

<style lang="scss">
// fix css style bug in open el-dialog
.el-popup-parent--hidden {
  .fixed-header {
    padding-right: 15px;
  }
}
</style>
