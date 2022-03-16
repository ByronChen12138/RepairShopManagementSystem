<template>
  <div id="app">
    <my-header></my-header>
    <transition name="fade" mode="out-in">
      <router-view>
      </router-view>
    </transition>
    <my-footer></my-footer>
  </div>
</template>

<script>
import Header from "@/components/general-header";
import Footer from "@/components/general-footer"

export default {
  name: 'App',
  components: {
    'my-header': Header,
    'my-footer': Footer
  },
}
</script>

<style>
/*Available fonts.*/
@import url('https://fonts.googleapis.com/css2?family=Raleway:wght@200&display=swap');
@import url('https://fonts.googleapis.com/css?family=Roboto+Condensed');
@import url('https://fonts.googleapis.com/css2?family=Roboto:wght@300&display=swap');
@import url('https://fonts.googleapis.com/css2?family=Playfair+Display+SC&display=swap');
@import url('https://fonts.googleapis.com/css2?family=Train+One&display=swap');
@import url("https://s3-us-west-2.amazonaws.com/s.cdpn.io/85648/IBMPlexSansVar-Roman.ttf");

.fade-enter-active,
.fade-leave-active {
  transition-duration: 0.3s;
  transition-property: opacity;
  transition-timing-function: ease;
}

.fade-enter,
.fade-leave-active {
  opacity: 0
}

.slide-fade-enter-active {
  transition: all .3s ease;
}

.slide-fade-leave-active {
  transition: all .8s cubic-bezier(1.0, 0.5, 0.8, 1.0);
}

.slide-fade-enter, .slide-fade-leave-to
  /* .slide-fade-leave-active for <2.1.8 */
{
  transform: translateX(10px);
  opacity: 0;
}
</style>