<template>
  <div class="root">
    <input v-model="input" :style="{width: boxWidth}" class="input-box" :placeholder="placeHolder" :type="inputType"/>
    <transition name="slide-fade" mode="out-in">
      <p class="place-holder" :key="indicatingPlaceHolder">{{ indicatingPlaceHolder }}</p>
    </transition>
  </div>
</template>

<script>
export default {
  name: "input-box",
  data: function () {
    return {
      input: "",
    }
  },
  props: {
    boxWidth: {
      required: false,
      default: "350px",
      type: String
    },
    placeHolder: {
      required: true,
      type: String
    },
    inputType: {
      required: false,
      type: String,
      default: "text"
    }
  },
  methods: {},
  computed: {
    indicatingPlaceHolder() {
      return this.input ? this.placeHolder : "";
    }
  },
  watch: {
    input: function (newInput, oldInput) {
      console.debug(oldInput);
      this.$emit('input-received', newInput);
    }
  }
}
</script>

<style scoped>

.root {
  width: max-content;
  height: max-content;
  min-width: 150px;
}

.input-box {
  height: 1.7em;
  display: block;
  padding: 5px 10px;
  border: gray solid 1px;
  border-radius: 5px;
  background-color: white;
  font-size: 1.2em;
  font-family: Roboto, sans-serif;
  text-decoration: none;
  outline: none;

  transition: border-color .6s ease, box-shadow .6s ease;
}

.input-box:focus {
  border: red 1px solid;
  transition: border-color .6s ease, box-shadow .6s ease;
}

.place-holder {
  display: block;
  margin-top: 5px;
  margin-bottom: 0;
  color: gray;
  font-family: "Playfair Display SC", serif;
  font-size: 0.9em;
  font-style: italic;
  width: max-content;
}

.slide-fade-enter-active {
  transition: all .2s ease;
}

.slide-fade-leave-active {
  transition: all .2s cubic-bezier(1.0, 0.5, 0.8, 1.0);
}

.slide-fade-enter, .slide-fade-leave-to
  /* .slide-fade-leave-active for <2.1.8 */
{
  transform: translateX(10px);
  opacity: 0;
}

</style>