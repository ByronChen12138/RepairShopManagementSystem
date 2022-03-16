<template>
  <div class="root">
    <div class="title">Update Car Info</div>
    <input class="form-input" v-model="model" placeholder="Model" style="float: left; width: 150px">
    <input class="form-input" v-model="year" placeholder="Year" style="float: left; width: 150px">
    <input class="form-input" v-model="manufacturer" placeholder="Manufacturer" style="float: left; width: 150px">
    <my-button background-color="black" text="Update Car" @button-clicked="updateCar"
               style="margin-left: 50px; width: 200px; margin-top: 30px"></my-button>
  </div>
</template>

<script>

import axios from "axios"

var config = require("../configuration")

var AXIOS = axios.create({
  baseURL: config.springServer.baseUrl,
})

export default {
  name: "update-car-form",
  data: function () {
    return {
      model: "",
      year: "",
      manufacturer: "",
    }
  },
  props: {
    plateNo: String,
    username: String
  },
  methods: {
    /**
     * Update the car information.
     */
    updateCar() {
      AXIOS.put("/users/cars/update", {
        plateNo: this.plateNo
      }, {
        params: {
          newUsername: this.username,
          newModel: this.model,
          newYear: this.year,
          newManufacturer: this.manufacturer
        }
      }).then(resp => {
        if (resp.data.hasError)
          throw new Error(resp.data.error);
        let userInfo = JSON.parse(localStorage.getItem('userInformation'))
        let cars = userInfo.cars;
        for (let i = 0; i < cars.length; i++)
          if (cars[i].plateNo === this.plateNo) {
            cars[i].model = this.model;
            cars[i].year = this.year;
            cars[i].manufacturer = this.manufacturer;
            localStorage.setItem('userInformation', JSON.stringify(userInfo));
            this.$emit('closePanel');
            this.$alert("Done!");
            break;
          }
      }).catch(err => {
        console.error(err.toString());
        this.$alert(err.toString());
      })
    }
  }
}
</script>

<style scoped>

.root {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  justify-content: space-between;
}

.title {
  font-family: Roboto, sans-serif;
  font-size: 30px;
  padding-left: 50px;
  margin-top: 50px;
  margin-bottom: 50px;
}

.form-input {
  margin-bottom: 20px;
  margin-left: 50px;
  display: block;
  height: 1.5em;
  padding: 5px 10px;
  background-color: white;
  outline: none;
  border: gray solid 1px;
  font-family: sans-serif;
  font-size: 18px;
  text-decoration: none;
  transition: border-color .4s ease, box-shadow .4s ease;
}
</style>