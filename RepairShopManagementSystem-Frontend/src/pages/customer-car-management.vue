<template>
  <div class="root">
    <div class="container">
      <!--View all cars with the account.-->
      <section class="section">
        <section-title title="Your cars" sub-title="You look great!"></section-title>
        <car-table :customer-info="this.customerInfo" :selecting="false"></car-table>
      </section>
      <!--Add car.-->
      <div class="section" style="margin-bottom: 150px">
        <section-title title="Got new cars?" sub-title="Amaze us."></section-title>
        <div class="view-info-row">
          <div class="view-info-row-description">PlateNo:</div>
          <div class="view-info-row-information">
            <input class="form-input" v-model="plateNo" key="input">
          </div>
        </div>
        <div class="view-info-row">
          <div class="view-info-row-description">Model:</div>
          <transition name="fade" mode="out-in">

            <div class="view-info-row-information">
              <input class="form-input" v-model="model" key="input">
            </div>
          </transition>
        </div>
        <div class="view-info-row">
          <div class="view-info-row-description">Manufacturer:</div>
          <transition name="fade" mode="out-in">

            <div class="view-info-row-information">
              <input class="form-input" v-model="manufacturer" key="input">
            </div>
          </transition>
        </div>
        <div class="view-info-row">
          <div class="view-info-row-description">Year:</div>
          <transition name="fade" mode="out-in">
            <div class="view-info-row-information">
              <input class="form-input" v-model="year" key="input">
            </div>
          </transition>
        </div>
        <div
            style="display: flex; width: 60%; height: 100px; flex-direction: row; align-items: center; justify-content: space-around; margin-top: 30px; margin-left: 15%">
          <div style="width: max-content">
            <my-button background-color="black" text="Add" @button-clicked="addCarClicked" :is-loading="isLoading"
                       style="width: 150px"></my-button>
          </div>
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

import CarTable from ".././components/car-table"

export default {
  name: "car-page",
  data: function () {
    return {
      customerInfo: Object,
      plateNo: "",
      model: "",
      manufacturer: "",
      year: "",
      isLoading: false
    }
  },
  components: {
    "CarTable": CarTable
  },
  methods: {
    /**
     * Add a car to the database.
     */
    addCarClicked: function () {
      this.isLoading = true;
      let plateNo = this.plateNo;
      let model = this.model;
      let manufacturer = this.manufacturer;
      let year = this.year;
      let username = this.customerInfo.username;
      AXIOS.post("/users/cars/create",
          {
            // Request body
            plateNo: plateNo,
            model: model,
            manufacturer: manufacturer,
            year: year
          },
          {
            params: {
              username: username,
            }
          },
      ).then(resp => {
        this.isLoading = false;
        let carData = resp.data;
        if (carData.hasError)
          throw new Error(carData.error);

        this.cars.push({
          plateNo: plateNo,
          model: model,
          manufacturer: manufacturer,
          year: year
        });
        localStorage.setItem('userInformation', JSON.stringify(this.customerInfo));
        this.plateNo = "";
        this.model = "";
        this.manufacturer = "";
        this.year = "";

        this.$alert("Done!");

      }).catch(e => {
        this.isLoading = false;
        console.error(e.toString());
        this.$alert(e.toString());
      })
    }
  },

  created() {
    // Load user info from local storage.
    this.customerInfo = JSON.parse(localStorage.getItem('userInformation'));
    this.cars = this.customerInfo.cars;
  },
  computed: {
    getUsername: function () {
      return this.customerInfo.username;
    },

    getPassword: function () {
      return this.customerInfo.password;
    },

    getName: function () {
      return this.customerInfo.name;
    },

    getAddress: function () {
      return this.customerInfo.address;
    },

    getEmail: function () {
      return this.customerInfo.email;
    },

    getPhoneNo: function () {
      return this.customerInfo.phoneNo;
    },

    getAppointments: function () {
      return this.customerInfo.appointments;
    },


  }
}
</script>

<style scoped>
.root {
  width: 100%;
  height: max-content;
  margin-top: 40px;
}

.container {
  width: 90%;
  height: max-content;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-around;
  margin-left: auto;
  margin-right: auto;
}

.section {
  width: 90%;
  margin-top: 50px;
  margin-bottom: 20px;
}

.view-info-row {
  width: 100%;
  height: 2.7em;
  margin-top: 10px;
  margin-bottom: 10px;
  display: table;
}

.view-info-row-description {
  height: 100%;
  display: table-cell;
  text-align: right;
  line-height: 2em;
  font-size: 20px;
  font-family: Roboto, sans-serif;
}

.view-info-row-information {
  width: 60%;
  height: 100%;
  padding-left: 10px;
  display: table-cell;
  text-align: left;
  line-height: 2em;
  font-size: 20px;
  font-family: "Times New Roman", serif;
}

.form-input {
  display: block;
  height: 1.5em;
  padding: 5px 10px;
  background-color: white;
  outline: none;
  border: gray solid 1px;
  border-radius: 5px;

  font-family: Roboto, sans-serif;
  font-size: 18px;
  text-decoration: none;

  transition: border-color .4s ease, box-shadow .4s ease;
}

.form-input:focus {
  border: red 1px solid;
  transition: border-color .4s ease, box-shadow .4s ease;
}

</style>