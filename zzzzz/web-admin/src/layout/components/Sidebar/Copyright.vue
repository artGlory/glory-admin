<template>
  <div class="sidebar-copyright-container" :class="{'collapse':collapse}">
    <transition name="sidebarCopyrightFade">
      <div v-if="!collapse" key="collapse" class="sidebar-copyright-item">
        <div class="sidebar-copytight-title">{{ system_copyright }}</div>
      </div>
    </transition>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
export default {
  name: 'Copyright',
  props: {
    collapse: {
      type: Boolean,
      required: true
    }
  },
  computed: {
    ...mapGetters(['system_copyright'])
  }
}
</script>

<style lang="scss" scoped>
.sidebarCopyrightFade-enter-active {
  transition: opacity 1.5s;
}

.sidebarCopyrightFade-enter,
.sidebarCopyrightFade-leave-to {
  opacity: 0;
}

.sidebar-copyright-container {
  position: relative;
  width: 100%;
  height: 30px;
  line-height: 30px;
  background: #304156;
  text-align: center;
  overflow: hidden;
  & .sidebar-copyright-item {
    height: 100%;
    width: 100%;
    & .sidebar-copytight-title {
      display: inline-block;
      margin: 0;
      color: #6f7b8a;
      font-weight: 600;
      line-height: 30px;
      font-size: 12px;
      font-family: Avenir, Helvetica Neue, Arial, Helvetica, sans-serif;
      vertical-align: middle;
    }
  }
}
</style>
