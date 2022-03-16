<template>
  <div class="root">
    <transition name="component-fade" mode="out-in">
      <component :is="view" v-bind="buttonData" @button-clicked="$emit('button-clicked')"></component>
    </transition>
  </div>
</template>

<script>

import GeneralButton from "./general-button"
import LoadingSymbol from "./loading-symbol"

export default {
  name: "button-wrapper",
  data: function () {
    return {}
  },
  props: {
    backgroundColor: {
      required: false,
      default: "black",
      type: String
    },
    isLoading: {
      required: false,
      default: false,
      type: Boolean
    },
    text: {
      required: true,
      type: String
    }
  },
  components: {
    'gen-button': GeneralButton,
    'loading': LoadingSymbol
  },
  computed: {
    view() {
      return this.isLoading ? 'loading' : 'gen-button';
    },
    buttonData() {
      return {
        text: this.text,
        backgroundColor: this.backgroundColor
      }
    }
  }
}
</script>

<style scoped>
.root{
  max-width: 160px;
  max-height: 2em;
  margin-top: 0;
}

.component-fade-enter-active, .component-fade-leave-active {
  transition: opacity .3s ease;
}

.component-fade-enter, .component-fade-leave-to
  /* .component-fade-leave-active below version 2.1.8 */ {
  opacity: 0;
}

</style>