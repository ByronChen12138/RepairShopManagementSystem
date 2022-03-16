<template>
  <div class="root">
    <!--The front page.-->
    <div class="main-info-section">
      <div class="blur">
        <div class="shade"></div>
        <div class="motto">
          <h1>One word: Perfection</h1>
        </div>
      </div>
    </div>

    <!--Gallery of our services.-->
    <div class="Shop-Information">
      <section-title title="Got troubles?" sub-title="We got it. Chill"></section-title>
      <horizontal-gallery :images="serviceImages" :maximum-length="galleryMaxLength"></horizontal-gallery>
    </div>

    <!--A show of our advantages.-->
    <section-title title="Our advantages" sub-title="Sorry, we are no ordinary."></section-title>
    <div class="projects">
      <div class="project" v-for="activity in activities" :key="activity.id"
           :style="{'backgroundImage': `url('${getImagePath(activity.imageFileName)}')`}">
        <div class="activity-promotion">
          <div class="activity-dash"></div>
          <div class="activity-flag">{{ activity.titleFlag }}</div>
          <div class="activity-title">{{ activity.title }}</div>
          <div class="activity-description">{{ activity.description }}</div>
        </div>
      </div>
    </div>
  </div>

</template>

<script>

import axios from "axios"

const config = require("../configuration")

const AXIOS = axios.create({
  baseURL: config.springServer.baseUrl,
})

export default {
  name: "home-page",
  data: function () {
    return {
      activities: [
        {
          imageFileName: "LeetCode500.png",
          titleFlag: "Quick, Cheap, To the point.",
          title: "All in your hand",
          description: "Services automated with software.",
          id: 1
        },
        {
          imageFileName: "django_development.png",
          titleFlag: "It's simple, we are the best.",
          title: "Professionals + Genius",
          description: "Crew from world-tier institutions",
          id: 2
        },
        {
          imageFileName: "ml_goal.jpg",
          titleFlag: "AI-powered Car Inspection",
          title: "Embrace the Age of AI",
          description: "Computer Vision with robots",
          id: 3
        },
      ],

      galleryMaxLength: 6,

      serviceImages: [
        {
          fileName: "clean_car.jpg",
          title: "Car Wash",
          description: "You car looks anew.",
          id: 1
        },
        {
          fileName: "old_car.jpg",
          title: "Maintenance",
          description: "Should last another 50 years.",
          id: 2
        },
        {
          fileName: "guardian_angel.jpg",
          title: "Road Assistance",
          description: "Come as needed",
          id: 3
        },
        {
          fileName: "michelin_restaurants.jpg",
          title: "Tire Change",
          description: "Michelin Star",
          id: 4
        },
        {
          fileName: "city_map.jpg",
          title: "Towing",
          description: "Any time, Anywhere",
          id: 5
        },
        {
          fileName: "car_engine_blueprint.jpg",
          title: "Car Inspection",
          description: "We the experts",
          id: 6
        },
      ]
    };
  },
  created() {
    AXIOS.get("/system/most_recent", {}).then(resp => {
      localStorage.setItem('businessInfo', JSON.stringify(resp.data));
    }).catch(e => {
      console.error(e.toString());
    });
  },
  methods: {
    getImagePath: function (imageFileName) {
      return require(`.././assets/img/${imageFileName}`)
    }
  }
}
</script>

<style scoped>

.main-info-section {
  height: 90vh;
  width: 100%;
  background-image: url("../../src/assets/img/sports_car.jpg");

  background-attachment: fixed;
  background-repeat: no-repeat;
  background-position: center;
  background-size: cover;

  display: flex;
  align-items: center;
  flex-direction: row;
  justify-content: center;
}

.blur {
  background: rgba(0, 0, 0, 0.05);
  border-radius: 15px;
  backdrop-filter: blur(1px);
  margin-top: 300px;
  height: max-content;
  width: max-content;
}

.Shop-Information {
  align-items: center;
}

.motto {
  margin-top: 0;
  font-size: 35px;
  font-family: "Times New Roman", sans-serif;

  color: white;
}

.projects {
  width: 85%;
  margin-left: auto;
  margin-right: auto;
  margin-bottom: 150px;
  border-radius: 8px;
  overflow: hidden;
}

.project {
  width: 100%;
  height: 500px;
  position: relative;

  background-size: cover;
  background-position: center;
  filter: sepia(50%);
  transition: 0.4s ease;
}

.project:hover {
  filter: sepia(0);
  transition: 0.8s ease;
}

.activity-promotion {
  width: 30%;
  height: 100%;
  margin-left: 80px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: flex-start;
}

.activity-dash {
  background: #b10000;
  height: 3px;
  margin-bottom: 10px;
  width: 30px
}

.activity-flag, .activity-title, .activity-description {
  color: white;
}

.activity-flag, .activity-description {
  font-family: Roboto, sans-serif;
}

.activity-flag {
  font-size: 35px;
}

.activity-description {
  font-size: 40px;
}

.activity-title {
  font-size: 50px;
  font-family: "Playfair Display SC", serif;
  font-weight: 600;
}

</style>