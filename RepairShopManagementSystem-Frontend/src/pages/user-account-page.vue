<template>
  <div class="root">
    <div class="container">
      <div class="section">
        <section-title title="View Basic Info" sub-title="You looks great!"></section-title>
        <div class="view-info-row">
          <div class="view-info-row-description">Username:</div>
          <div class="view-info-row-information">{{ getUsername }}</div>
        </div>
        <div class="view-info-row">
          <div class="view-info-row-description">Password:</div>
          <transition name="fade" mode="out-in">
            <div class="view-info-row-information" v-if="!isUpdatingBasicInformation" key="display">{{
                getPassword
              }}
            </div>
            <div class="view-info-row-information" v-else>
              <input class="form-input" v-model="updatedPassword" key="input">
            </div>
          </transition>
        </div>
        <div class="view-info-row">
          <div class="view-info-row-description">Name:</div>
          <transition name="fade" mode="out-in">
            <div class="view-info-row-information" v-if="!isUpdatingBasicInformation" key="display">{{
                getName
              }}
            </div>
            <div class="view-info-row-information" v-else>
              <input class="form-input" v-model="updatedName" key="input">
            </div>
          </transition>
        </div>
        <div class="view-info-row" v-if="this.userRole === 'customer'">
          <div class="view-info-row-description">Address:</div>
          <transition name="fade" mode="out-in">
            <div class="view-info-row-information" v-if="!isUpdatingBasicInformation" key="display">{{
                getAddress
              }}
            </div>
            <div class="view-info-row-information" v-else>
              <input class="form-input" v-model="updatedAddress" key="input">
            </div>
          </transition>
        </div>
        <div class="view-info-row" v-if="this.userRole === 'customer'">
          <div class="view-info-row-description">Phone No:</div>
          <transition name="fade" mode="out-in">
            <div class="view-info-row-information" v-if="!isUpdatingBasicInformation" key="display">{{
                getPhoneNo
              }}
            </div>
            <div class="view-info-row-information" v-else>
              <input class="form-input" v-model="updatedPhoneNo" key="input">
            </div>
          </transition>
        </div>
        <div class="view-info-row" v-if="this.userRole === 'customer'">
          <div class="view-info-row-description">Email:</div>
          <transition name="fade" mode="out-in">
            <div class="view-info-row-information" v-if="!isUpdatingBasicInformation" key="display">{{
                getEmail
              }}
            </div>
            <div class="view-info-row-information" v-else>
              <input class="form-input" v-model="updatedEmail" key="input">
            </div>
          </transition>
        </div>
        <div
            style="display: flex; width: 60%; height: 100px; flex-direction: row; align-items: center; margin-top: 30px; margin-left: 32%"
            :style="getAssistantBottomMargin">
          <div style="width: max-content; margin-right: 1.5em">
            <my-button background-color="black" @button-clicked="isUpdatingBasicInformation=!isUpdatingBasicInformation"
                       :text="getEditInfoButtonText" style="width: 150px">
            </my-button>
          </div>
          <div style="width: max-content">
            <my-button background-color="black" @button-clicked="updateUserInformationClicked"
                       text="Update" style="width: 150px">
            </my-button>
          </div>
        </div>
      </div>
      <div class="section" style="margin-bottom: 150px" v-if="this.userRole === 'customer'">
        <section-title title="Appointments" sub-title="see booked appointments"></section-title>
        <appointment-table v-bind:appointments="getAppointments" :customer-info="this.userInfo"
                           @payment-made="makePaymentClicked" @delete-made="onDeleteAppointment"></appointment-table>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";

var config = require("../configuration");

var AXIOS = axios.create({
  baseURL: config.springServer.baseUrl,
});

import AppointmentTable from ".././components/appointment-table"

export default {
  name: "user-account-page",
  data: function () {
    return {
      userInfo: Object,
      userRole: String,
      isUpdatingBasicInformation: false,
      isLoading: false,
      updatedPassword: "",
      updatedEmail: "",
      updatedPhoneNo: "",
      updatedAddress: "",
      updatedName: "",
    }
  },
  components: {
    "AppointmentTable": AppointmentTable
  },
  methods: {

    /**
     * Update the selected user information.
     */
    updateUserInformationClicked: function () {
      this.isLoading = true;
      let password = this.updatedPassword;
      let name = this.updatedName;
      let address = this.updatedAddress;
      let phoneNo = this.updatedPhoneNo;
      let email = this.updatedEmail;

      let requestBody = Object;
      let requestParams = Object;
      let url = String;

      if (this.userRole === "customer") {
        requestBody = {
          //request body
          username: this.getUsername,
          password: this.getPassword,
          name: this.getName,
          phoneNo: this.getPhoneNo,
          homeAddress: this.getAddress,
          email: this.getEmail,
        };
        url = "users/customers/update_info";
        requestParams = {
          newUsername: this.getUsername,
          newPassword: password,
          newName: name,
          newPhoneNo: phoneNo,
          newAddress: address,
          newEmail: email,
        };
      } else {
        requestBody = {
          username: this.getUsername,
          password: this.getPassword,
          name: this.getName,
        };
        requestParams = {
          newUsername: this.getUsername,
          newPassword: password,
          newName: name
        };
        url = this.userRole === "assistant" ? "users/assistants/update_info" : "users/owners/update_info";
      }

      AXIOS.put(url, requestBody, {params: requestParams}).then(resp => {
        this.isLoading = false;
        let updatedInfo = resp.data;
        if (updatedInfo.hasError)
          throw new Error(updatedInfo.error);

        this.userInfo.password = password;
        this.userInfo.name = name;
        if (this.userRole === "customer") {
          this.userInfo.address = address;
          this.userInfo.phoneNo = phoneNo;
          this.userInfo.email = email;
        }
        this.updatedPassword = '';
        this.updatedName = '';
        this.updatedAddress = '';
        this.updatedPhoneNo = '';
        this.updatedEmail = '';
        this.isUpdatingBasicInformation = false;
        localStorage.setItem('userInformation', JSON.stringify(this.userInfo));
        this.$alert("Done!");
      }).catch(e => {
        this.isLoading = false;
        this.$alert(e.toString());
        console.error(e.toString());
      })
    },

    /**
     * Make a payment to the appointment.
     * @param event
     */
    makePaymentClicked: function (event) {
      let appointmentChosen = Object;
      let appointments = this.getAppointments;
      for (let i = 0; i < appointments.length; i++)
        if (appointments[i].appointmentId === parseInt(event)) {
          appointmentChosen = appointments[i];
          break;
        }

      let billPaid = appointmentChosen.bill[0];
      AXIOS.put("/appointment/make_payment", {}, {
        params: {
          id: billPaid.billNo
        }
      }).then(resp => {
        let responseData = resp.data;
        if (responseData.hasError)
          throw new Error(responseData.error);
        billPaid.isPaid = true;
        appointmentChosen.isPaid = true;
        localStorage.setItem('userInformation', JSON.stringify(this.userInfo));
        this.$alert("Done!");
      }).catch(e => {
        this.$alert(e.toString());
        console.error(e.toString());
      });

    },

    /**
     * Delete an appointment.
     * @param event
     */
    onDeleteAppointment: function (event) {
      let appointmentChosen = Object;
      let appointments = this.getAppointments;
      let index = 0;
      for (index = 0; index < appointments.length; index++)
        if (appointments[index].appointmentId === event) {
          appointmentChosen = appointments[index];
          break;
        }

      AXIOS.post("/appointment/delete", {}, {
        params: {
          id: appointmentChosen.appointmentId
        }
      }).then(resp => {
        if (resp.data.hasError)
          throw new Error(resp.data.error);

        appointments.splice(index, 1);
        localStorage.setItem('userInformation', JSON.stringify(this.userInfo))
      }).catch(e => {
        console.error(e.toString());
        this.$alert(e.toString());
      })
    }
  },
  mounted() {
    // Load user info from local storage.
    this.userInfo = JSON.parse(localStorage.getItem('userInformation'));
    this.userRole = localStorage.getItem('userRole')
  },
  computed: {
    getUsername: function () {
      return this.userInfo.username;
    },

    getPassword: function () {
      return this.userInfo.password;
    },

    getName: function () {
      return this.userInfo.name;
    },

    getAddress: function () {
      return this.userInfo.homeAddress;
    },

    getEmail: function () {
      return this.userInfo.email;
    },

    getPhoneNo: function () {
      return this.userInfo.phoneNo;
    },

    getAppointments: function () {
      return this.userInfo.appointments;
    },

    getEditInfoButtonText: function () {
      return this.isUpdatingBasicInformation ? "Abort" : "Edit";
    },

    isCustomer: function () {
      return this.userRole === "customer";
    },

    getAssistantBottomMargin() {
      return this.isCustomer ? null : {
        marginBottom: "100px"
      };
    }
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
  margin-top: 40px;
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
  border-radius: 5px;
  outline: none;
  border: gray solid 1px;

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