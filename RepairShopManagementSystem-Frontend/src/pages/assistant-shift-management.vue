<template>
  <div class="root">
    <!--Add or update shift.-->
    <section-title title="Work 996?" sub-title="Let's roll."></section-title>
    <div>
      <div class="view-info-row">

        <div class="view-info-row-description">Shift selected</div>
        <transition name="fade" mode="out-in">
          <div v-if="!this.selectedShift" key="shiftSelected" class="order-info-row-information">Not selected.</div>
          <div v-else class="order-info-row-information" key="shiftNotSelected">
            Shift No:{{ this.selectedShift.shiftId }} Interval: {{ this.selectedShift.startTime }} -
            {{ this.selectedShift.endTime }}
          </div>
        </transition>
      </div>
      <div class="view-info-row">

        <div class="view-info-row-description">Date (yyyy-mm-dd):</div>
        <div class="view-info-row-information">
          <input class="form-input" v-model="date" key="input">
        </div>
      </div>
      <div class="view-info-row">
        <div class="view-info-row-description">Start Time (HH:mm):</div>
        <transition name="fade" mode="out-in">

          <div class="view-info-row-information">
            <input class="form-input" v-model="startTime" key="input">
          </div>
        </transition>
      </div>
      <div class="view-info-row">
        <div class="view-info-row-description">EndT Time (HH:mm):</div>
        <transition name="fade" mode="out-in">

          <div class="view-info-row-information">
            <input class="form-input" v-model="endTime" key="input">
          </div>
        </transition>

      </div>
      <div
          style="display: flex; width: 30%; height: 100px; flex-direction: row; align-items: center; justify-content: space-around; margin-top: 30px; margin-left: 28%">
        <div style="width: max-content">
          <my-button background-color="black" text="Add" @button-clicked="addShiftClicked" style="width: 150px"
                     :is-loading="isLoading"></my-button>
        </div>
        <div style="width: max-content">
          <my-button background-color="black" text="Update" @button-clicked="updateShiftClicked" style="width: 150px"
                     :is-loading="isLoading"></my-button>
        </div>
      </div>
    </div>

    <!--View shifts.-->
    <section-title title="Your shifts" sub-title="Select and edit above."></section-title>
    <div class="date-picking-container">
      <div class="date-picking-shifts">
        <shifts-table :shifts="this.shifts" :allow-deletable="true" v-on:selected="onShiftSelected"
                      @delete-shift="deleteShiftClicked"></shifts-table>
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

import ShiftsTable from ".././components/shifts-table"

export default {
  name: "shift-management-page",
  data: function () {
    return {
      date: "",
      startTime: "",
      endTime: "",
      shifts: [],
      selectedShift: null,
      userInfo: Object,
      isLoading: false,
    }
  },
  components: {
    "ShiftsTable": ShiftsTable
  },
  created() {
    let userInfo = JSON.parse(localStorage.getItem('userInformation'));
    this.shifts = userInfo.shifts;
    this.userInfo = userInfo;
  },
  methods: {
    onShiftSelected(event) {
      for (let i = 0; i < this.shifts.length; i++)
        if (this.shifts[i].shiftId === event)
          this.selectedShift = this.shifts[i];
    },
    /**
     * Add a shift to the backend.
     */
    addShiftClicked() {
      this.isLoading = true;
      let date = this.date;
      let startTime = this.startTime;
      let endTime = this.endTime;
      let username = this.userInfo.username
      AXIOS.post("/schedules/shifts/create", {}, {
        params: {
          date: date,
          startTime: startTime,
          endTime: endTime,
          username: username
        }
      }).then(resp => {
        this.isLoading = false;
        let shiftData = resp.data;
        if (shiftData.hasError)
          throw new Error(shiftData.error);

        this.userInfo = JSON.parse(localStorage.getItem('userInformation'));
        this.shifts = this.userInfo.shifts;
        this.shifts.push(shiftData);
        localStorage.setItem('userInformation', JSON.stringify(this.userInfo));
        this.date = "";
        this.startTime = "";
        this.endTime = "";
      }).catch(e => {
        this.isLoading = false;
        console.error(e.toString())
        this.$alert(e.toString());
      });
    },
    /**
     * Update a shift to the backend.
     * A shift must be selected at
     * first for this method to work.
     */
    updateShiftClicked() {

      if (!this.selectedShift) {
        this.$alert("Please select a shift first!");
        return;
      }

      this.isLoading = true;
      let date = this.date;
      let startTime = this.startTime;
      let endTime = this.endTime;
      let shiftId = this.selectedShift.shiftId;
      AXIOS.put("/schedules/shifts/change", {}, {
        params: {
          newDate: date,
          newStartTime: startTime,
          newEndTime: endTime,
          shiftId: shiftId
        }
      }).then(resp => {
        this.isLoading = false;
        let shiftData = resp.data;
        if (shiftData.hasError)
          throw new Error(shiftData.error);

        this.userInfo = JSON.parse(localStorage.getItem('userInformation'));
        this.shifts = this.userInfo.shifts;
        for (let i = 0; i < this.shifts.length; i++)
          if (this.shifts[i].shiftId === shiftId) {
            this.shifts[i] = shiftData;
            break;
          }
        localStorage.setItem('userInformation', JSON.stringify(this.userInfo));
        this.date = "";
        this.startTime = "";
        this.endTime = "";
        this.selectedShift = null;
      }).catch(e => {
        this.isLoading = false;
        console.error(e.toString());
        this.$alert(e.toString());
      });
    },
    /**
     * Delete a shift.
     * @param event: shift ID.
     */
    deleteShiftClicked(event) {

      let deletedShiftId = event;

      AXIOS.post("/schedules/shifts/delete", {}, {
        params: {
          shiftId: deletedShiftId
        }
      }).then(resp => {
        if (resp.data.hasError)
          throw new Error(resp.data.error);

        for (let i = 0; i < this.shifts.length; i++)
          if (this.shifts[i].shiftId === deletedShiftId) {
            this.shifts.splice(i, 1);
            this.$alert("Done");
            localStorage.setItem("userInformation", JSON.stringify(this.userInfo));
            break;
          }
      }).catch(e => {
        console.error(e.toString());
        this.$alert(e.toString());
      });
    }
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
  transition: .5s ease;
}

.show-selected-service span {
  font-size: 35px;
  font-weight: 600;
  font-style: italic;
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
  margin-left: 15%;
  margin-right: auto;
  margin-top: 40px;
  margin-bottom: 150px;
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
  display: table;
  margin-left: auto;
  margin-right: auto;
}

.order-info-row-description {
  height: 100%;
  display: table-cell;
  text-align: right;
  line-height: 2em;
  font-size: 20px;
  font-family: Roboto, sans-serif;
}

.order-info-row-information {
  width: 60%;
  height: 100%;
  padding-left: 10px;
  display: table-cell;
  text-align: left;
  line-height: 2em;
  font-size: 20px;
  font-family: "Times New Roman", serif;
}

.fade-enter-active, .fade-leave-active {
  transition: opacity 0.5s ease-in-out;
}

.fade-enter, .fade-leave-to /* .fade-leave-active below version 2.1.8 */
{
  opacity: 0;
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
  font-family: sans-serif;
  font-size: 18px;
  text-decoration: none;
  transition: border-color .4s ease, box-shadow .4s ease;
}

</style>