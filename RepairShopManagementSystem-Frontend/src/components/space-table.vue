<template>
  <div class="root">
    <div class="container">
      <!--Table row.-->
      <div class="space-row">
        <div class="id-column title-font">ID</div>
        <div class="max-load-column title-font">MAX LOAD(kg)</div>
      </div>

      <!--Contents of the table. If there is no content, show message instead.-->
      <transition-group name="list-complete" tag="div">
        <div class="space-row" v-for="s in this.space" :key="s.maxWeightLoad">
          <div class="id-column">{{ s.spaceId }}</div>
          <div class="max-load-column">{{ s.maxWeightLoad }}</div>
        </div>
      </transition-group>
    </div>
  </div>
</template>

<script>
export default {
  name: "space-table",
  props: {
    space: Array
  },
}
</script>

<style scoped>
* {
  font-family: Roboto, sans-serif;
}

.container {
  width: 85%;
  height: max-content;
  margin-top: 20px;
  padding-left: 60px;
}

.space-row {
  width: 60%;
  height: 2.7em;
  display: table;
  border-bottom: gray dashed 1px;
}

.id-column {
  display: table-cell;
  width: 50%;
  height: 100%;
  font-size: 20px;
  line-height: 2.7em;
  text-align: center;
}

.max-load-column {
  display: table-cell;
  width: 50%;
  height: 100%;
  font-size: 20px;
  line-height: 2.7em;
  text-align: center;
}

.title-font {
  font-family: "Playfair Display SC", serif;
}

.list-complete-enter-from,
.list-complete-leave-to {
  opacity: 0;
}

.list-complete-leave-active {
  position: absolute;
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