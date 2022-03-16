<template>
  <div class="root">
    <div class="welcome-title-section">
      <div class="welcome-title">
        WE ARE READY
      </div>
      <div class="welcome-subtitle">
        to serve in your best interests.
      </div>
      <div class="welcome-title-section-divider"></div>
    </div>
    <section-title title="How can we serve?" sub-title="Choose a service type."></section-title>
    <div style="width: 85%; margin-left: auto; margin-right: auto">
      <horizontal-gallery :images="this.serviceImages" :maximum-length="galleryMaxLength"
                          v-on:image-selected="onServiceSelected"></horizontal-gallery>
    </div>
    <transition name="fade" mode="out-in">
      <transition v-if="this.selectedService" name="slide-fade" mode="out-in">
        <div class="show-selected-service" :key="this.selectedService">You have selected
          <span>
                    {{ this.selectedService }}
        </span>
        </div>
      </transition>
    </transition>
    <section-title title="Which car?" sub-title="Always a pleasure to see one."></section-title>
    <div style="width: 85%; margin-left: auto; margin-right: auto">
      <car-table :customer-info="this.userInfo" :selecting="true" v-on:carSelected="onCarSelected"></car-table>
    </div>
    <section-title title="What date?" sub-title="Choose a preferred date."></section-title>
    <div class="date-picking-container">
      <div class="date-picking-calendar">
        <calendar :attributes="attributes" @dayclick="onDayClick"/>
        <my-button text="View Shifts" background-color="black" style="margin-top: 40px; margin-left: 15%;"
                   @button-clicked="onViewShiftsClicked" :is-loading="isLoading"></my-button>
      </div>
      <div class="date-picking-shifts">
        <shifts-table :shifts="this.shifts" :allow-deletable="false" v-on:selected="onShiftSelected"></shifts-table>
      </div>
    </div>
    <section-title title="Almost there..." sub-title="Double check your order."></section-title>
    <div class="order-preview">
      <div class="order-info-row">
        <div class="order-info-row-description">Start Time</div>
        <transition name="fade" mode="out-in">
          <div v-if="!this.selectedShift" key="shiftSelected" class="order-info-row-information">Not selected.</div>
          <div v-else class="order-info-row-information" key="shiftNotSelected">{{ this.selectedShift.startTime }}</div>
        </transition>
      </div>
      <div class="order-info-row">
        <div class="order-info-row-description">End Time</div>
        <transition name="fade" mode="out-in">
          <div v-if="!this.selectedShift" key="shiftSelected" class="order-info-row-information">Not selected.</div>
          <div v-else class="order-info-row-information" key="shiftNotSelected">{{ this.selectedShift.endTime }}</div>
        </transition>
      </div>
      <div class="order-info-row">
        <div class="order-info-row-description">Service Type</div>
        <transition name="fade" mode="out-in">
          <div v-if="!this.selectedService" key="shiftSelected" class="order-info-row-information">Not selected.</div>
          <div v-else class="order-info-row-information" key="shiftNotSelected">{{ this.selectedService }}</div>
        </transition>
      </div>
      <div class="order-info-row">
        <div class="order-info-row-description">Car</div>
        <transition name="fade" mode="out-in">
          <div v-if="!this.selectedCar" key="shiftSelected" class="order-info-row-information">Not selected.</div>
          <div v-else class="order-info-row-information" key="shiftNotSelected">{{ this.selectedCar }}</div>
        </transition>
      </div>
    </div>
    <div style="width: max-content; margin-bottom: 100px; margin-left: auto; margin-right: auto;">
      <my-button text="Book!" background-color="black" style="width: 200px"
                 @button-clicked="onMakeAppointment" :is-loading="isLoading"></my-button>
    </div>
  </div>
</template>

<script>

import axios from "axios"

var config = require("../configuration")

var AXIOS = axios.create({
  baseURL: config.springServer.baseUrl,
})

import ShiftsTable from ".././components/shifts-table";
import CarTable from ".././components/car-table"

export default {
  name: "user-make-appointment",
  data: function () {
    return {
      userInfo: {},
      isLoading: false,
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
      ],
      galleryMaxLength: 5,
      selectedService: null,
      selectedCar: null,
      shifts: [],
      selectedShift: null,
      days: [],
    }
  },
  components: {
    "ShiftsTable": ShiftsTable,
    "CarTable": CarTable
  },
  created() {
    this.userInfo = JSON.parse(localStorage.getItem('userInformation'));
  },
  computed: {
    dates() {
      return this.days.map(day => day.date);
    },
    attributes() {
      return this.dates.map(date => ({
        highlight: true,
        dates: date,
      }));
    },
  },
  methods: {
    onDayClick(day) {
      const idx = this.days.findIndex(d => d.id === day.id);
      if (idx >= 0) {
        this.days.splice(idx, 1);
      } else {
        this.days.push({
          id: day.id,
          date: day.date,
        });
      }
      console.log(JSON.stringify(this.days))
    },
    onServiceSelected(event) {
      this.selectedService = event
    },
    onShiftSelected(event) {
      for (let i = 0; i < this.shifts.length; i++)
        if (this.shifts[i].shiftId === event)
          this.selectedShift = this.shifts[i];
    },
    onCarSelected(event) {
      this.selectedCar = event;
    },

    /**
     * Get all the shifts of those dates.
     */
    onViewShiftsClicked() {
      this.isLoading = true;
      let datesArray = [];
      for (let i = 0; i < this.days.length; i++)
        datesArray.push(this.days[i].id)
      AXIOS.get("/schedules/shifts/get_for_dates", {
        params: {
          dates: datesArray.join(',')
        }
      }).then(resp => {
        this.isLoading = false;
        let shiftData = resp.data;
        if (shiftData.hasError)
          throw new Error(shiftData.error);
        this.shifts = shiftData;
      }).catch(e => {
        this.isLoading = false;
        console.error(e.toString());
        this.$alert(e.toString());
      })
    },

    /**
     * Actually make an appointment.
     */
    onMakeAppointment() {
      this.isLoading = true;
      AXIOS.post("/appointment/make_appointment", {}, {
        params: {
          serviceType: this.selectedService,
          username: this.userInfo.username,
          plateNo: this.selectedCar,
          date: this.selectedShift.date,
          startTime: this.selectedShift.startTime.substr(0, 5),
          endTime: this.selectedShift.endTime.substr(0, 5),
          weight: 1000,
          operatorUsername: this.userInfo.username
        }
      }).then(resp => {
        this.isLoading = false;
        let appointmentData = resp.data;
        if (appointmentData.hasError)
          throw new Error(appointmentData.error);
        appointmentData.isPaid = this.getAppointmentIsPaid(appointmentData.bill);
        appointmentData.isDeletable = this.isAppointmentDeletable(appointmentData.shift);
        this.userInfo.appointments.push(appointmentData);
        this.$alert("Done!");
        this.selectedService = null;
        this.selectedCar = null;
        this.selectedShift = null;
        localStorage.setItem('userInformation', JSON.stringify(this.userInfo));
      }).catch(e => {
        this.isLoading = false;
        console.error(e.toString());
        this.$alert(e.toString());
      });
    },


    getAppointmentIsPaid: function (appointmentBills) {
      for (let i = 0; i < appointmentBills.length; i++)
        if (!appointmentBills[i].paid)
          return false;
      return true;
    },

    isAppointmentDeletable: function (appointmentShift) {
      let date = new Date(appointmentShift.date + "T00:00:00Z");
      let today = new Date();
      return today < date;
    },

  },
}
</script>

<style scoped>
.root {
  width: 100%;
  height: max-content;
  padding-left: 0;
}

.welcome-title-section {
  width: 100%;
  height: max-content;
  display: flex;
  margin-top: 180px;
  margin-bottom: 40px;
  flex-direction: column;
  align-items: center;
  justify-content: space-around;
}

.welcome-title {
  font-family: 'Train One', cursive;
  text-align: center;
  font-size: 4em;
}

.welcome-subtitle {
  font-family: "Playfair Display SC", serif;
  font-size: 1.7em;
}

.show-selected-service {
  width: max-content;
  height: 2.5em;
  margin-left: auto;
  margin-right: auto;
  margin-top: 70px;
  font-family: Roboto, sans-serif;
  font-size: 30px;
}

.show-selected-service span {
  font-size: 35px;
  font-weight: 600;
  font-style: italic;
  width: max-content;
  transition: width .5s;
}

.welcome-title-section-divider {
  margin-top: 80px;
  height: 1px;
  width: 85%;
  background-color: gray;
}

.date-picking-container {
  width: 90%;
  height: max-content;
  margin-left: auto;
  margin-right: auto;
  margin-top: 40px;
  margin-bottom: 0px;
  display: flex;
  flex-direction: row;
  align-items: flex-start;
}

.date-picking-calendar {
  width: 20%;
  padding-left: 10%;
}

.date-picking-shifts {
  width: 80%;
}

.order-preview {
  margin-bottom: 50px;
  width: 90%;
  margin-left: auto;
  margin-right: auto;
}

.order-info-row {
  height: 2.7em;
  margin-top: 10px;
  margin-bottom: 10px;
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
  margin-left: auto;
  margin-right: auto;
}

.order-info-row-description {
  height: 100%;
  width: 49%;
  text-align: right;
  line-height: 2em;
  font-size: 20px;
  font-family: Roboto, sans-serif;
}

.order-info-row-information {
  width: 51%;
  height: 100%;
  padding-left: 10px;
  text-align: left;
  line-height: 2em;
  font-size: 20px;
  font-family: "Times New Roman", serif;
}

</style>