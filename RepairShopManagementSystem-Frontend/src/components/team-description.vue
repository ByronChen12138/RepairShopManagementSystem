<template>
  <div class="container">
    <div class="picture" :style="{'backgroundImage': `url('${this.imageUrl}')`}"></div>
    <div class="description">{{ personDescription }}</div>
  </div>
</template>

<script>
export default {
  name: "team-description",
  props: {
    filename: String,
    personDescription: String,
  },
  methods: {},
  computed: {
    imageUrl: function () {
      return require(".././assets/img/" + this.filename);
    }
  }
}
</script>

<style scoped>
.container {
  width: 100%;
  height: max-content;
  display: flex;
  flex-direction: row;
  align-items: flex-start;
  margin-bottom: 150px;
}

.picture {
  width: 300px;
  height: 600px;
  background-size: cover;
  background-position: center;
  margin-right: 100px;
  margin-left: 20%;

  transition: ease .3s;
}

.description {
  max-width: 600px;
  height: max-content;
  font-size: 30px;
  text-align: left;
  font-family: Roboto, sans-serif;

  transition: width 2s, height 3s, transform 2s;
}
</style>