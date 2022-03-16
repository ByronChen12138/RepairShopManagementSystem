<template>
  <div class="root">
    <user-navigation-panel :user-role="this.userRole"></user-navigation-panel>
    <transition name="fade" mode="out-in">
      <router-view class="content"></router-view>
    </transition>
  </div>
</template>

<script>

import UserNavigationPanel from ".././components/user-navigation-panel"

export default {
  name: "user-page",
  data: function () {
    return {
      userRole: ""
    }
  },
  components: {
    "UserNavigationPanel": UserNavigationPanel
  },
  created() {
    this.userRole = localStorage.getItem('userRole')
  },
  mounted() {
    this.$router.push('/user/welcome') // The default login page is the welcome page.
  }
}
</script>

<style scoped>
.root {
  width: 100%;
  height: 100%;

  display: grid;
  grid-template-columns: auto 1fr;
  grid-template-rows: auto;
  grid-template-areas:
    "nav content"
}

.content {
  grid-area: content;
  grid-template: none;
}

.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.4s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}

</style>