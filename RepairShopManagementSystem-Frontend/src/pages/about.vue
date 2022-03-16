<template>
  <div class="about">

    <div class="background">
      <div class="page-title">
        Best team in the world
      </div>
    </div>

    <section-title title="Meet our teams" sub-title="Look how handsome we are!"></section-title>
    <horizontal-gallery :images="this.people" :maximum-length="this.people.length"
                        style="margin-bottom: 100px" v-on:image-selected="personSelected"></horizontal-gallery>
    <section-title title="Details" sub-title="More"></section-title>
    <transition name="fade" mode="out-in">
      <team-description :filename="this.selectedPerson.fileName"
                        :personDescription="this.selectedPerson.detail" v-if="this.selectedPerson"></team-description>
      <div style="width: 100%; text-align: center; font-size: 3em; font-style: italic; margin-bottom: 2em" v-else>Select
        one perhaps?
      </div>
    </transition>
  </div>
</template>

<script>

import TeamDescription from ".././components/team-description"

export default {
  name: 'about',
  props: {},
  components: {
    'TeamDescription': TeamDescription
  },
  data: function () {
    return {
      people: [
        {
          fileName: "byron.jpg",
          title: "Byron Chen",
          description: "Please donate.",
          detail: "Please donate. That's it.",
          id: 1
        },
        {
          fileName: "kevin.jpg",
          title: "Kevin Li",
          description: "Vegetable dog.",
          detail: "321, being a human means having limits. I've learned something... The more carefully you scheme, the more unexpected events come along. As long as you're human... I REJECT MY HUMANITY, 321!",
          id: 2
        },
        {
          fileName: "ztian.jpg",
          title: "Ing Tian",
          description: "Homo Sapiens",
          detail: "I am watching you.",
          id: 3
        },
        {
          fileName: "max.jpg",
          title: "Max Shen",
          description: "Our diplomat",
          detail: "Please do not donate to that guy. ",
          id: 4
        },
        {
          fileName: "xli.jpg",
          title: "Xiang Li",
          description: "A soccer amateur.",
          detail: "I have never used a debugger in my life since my code never go wrong—-Xiang li",
          id: 5
        },
      ],

      selectedPerson: null
    }
  },
  mounted() {
  },
  methods: {
    personSelected(event) {
      for (let i = 0; i < this.people.length; i++)
        if (this.people[i].title === event)
          this.selectedPerson = this.people[i];
    }
  },
  computed: {}
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

.about {
  width: 100%;
  height: max-content;
  font-family: Roboto, sans-serif;
}

.background {
  width: 100%;
  height: 80vh;

  background-image: url(".././assets/img/rainy_road.png");
  background-attachment: fixed;
  background-repeat: no-repeat;
  background-position: center;
  background-size: cover;

  display: flex;
  align-items: center;
  justify-content: space-between;
}

.page-title {
  color: white;
  font-family: "Playfair Display SC", serif;
  font-size: 3.5em;
  width: 100%;
  height: max-content;
  text-align: center;
}

</style>
