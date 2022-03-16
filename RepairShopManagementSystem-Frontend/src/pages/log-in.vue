<template>
  <div class="root" style="height: 1200px; width: 100%; position: relative;">
    <div class="login-form">
      <div class="log-in-sign-up-selector">
        <div class="selector" v-bind:style="getSignUpSelectorStyle" @click="signUpSelectorClicked">
          <div class="selector-box">SIGN UP</div>
        </div>
        <div class="selector" v-bind:style="getLogInSelectorStyle" @click="logInSelectorClicked">
          <div class="selector-box">LOG IN</div>
        </div>
      </div>

      <!--Log in or Sign up form.-->
      <transition name="fade" mode="out-in">
        <div v-if="displaySignUp" class="form-container" key="signUpForm">
          <div class="title">
            Sign Up For Free!
          </div>

          <div style="height: max-content; width: 375px;">
            <my-input class="form-input" @input-received="firstName=$event;" place-holder="First Name" box-width="150px"
                      style="float: left"></my-input>
            <my-input class="form-input" @input-received="lastName=$event;" place-holder="Last Name" box-width="150px"
                      style="float: right"></my-input>
          </div>
          <my-input class="form-input" @input-received="email=$event;" place-holder="Email"></my-input>
          <my-input class="form-input" @input-received="phoneNo=$event;" place-holder="Phone No"></my-input>
          <my-input class="form-input" @input-received="address=$event;" place-holder="Address"></my-input>
          <my-input class="form-input" @input-received="username=$event;" place-holder="Username"></my-input>
          <my-input class="form-input" @input-received="password=$event;" place-holder="Password"></my-input>
          <my-button background-color="black" text="sign up" :is-loading="isLoading" style="width: 200px; height: 60px"
                     @button-clicked="signUpButtonClicked"></my-button>
        </div>
        <div v-else class="form-container" key="logInForm">
          <div class="title">
            Log In Now!
          </div>
          <my-input class="form-input" @input-received="username=$event;" place-holder="Username"></my-input>
          <my-input class="form-input" @input-received="password=$event;" place-holder="Password"
                    input-type="password"></my-input>
          <my-button background-color="black" text="log in" :is-loading="isLoading" style="width: 200px; height: 60px"
                     @button-clicked="LoginButtonClicked"></my-button>
        </div>
      </transition>
    </div>

    <div style="position: absolute; bottom: -50px; width: 100%; height: max-content">
      <div class="logo">
        AUCTA NON VERBA
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios"

const config = require("../configuration");

const AXIOS = axios.create({
  baseURL: config.springServer.baseUrl,
})

export default {
  name: "log-in",
  data: function () {
    return {
      title: "Sign Up Now!",
      displaySignUp: false,
      displayLogIn: true,
      firstName: "",
      lastName: "",
      email: "",
      phoneNo: "",
      username: "",
      password: "",
      address: "",
      selectedStyle: {
        backgroundColor: "black"
      },
      isLoading: false
    }
  },
  computed: {
    getSignUpSelectorStyle: function () {
      return this.displaySignUp ? this.selectedStyle : null;
    },
    getLogInSelectorStyle: function () {
      return this.displayLogIn ? this.selectedStyle : null;
    }
  },
  methods: {
    signUpSelectorClicked: function () {
      this.displayLogIn = false;
      this.displaySignUp = true;
    },
    logInSelectorClicked: function () {
      this.displayLogIn = true;
      this.displaySignUp = false;
    },

    /**
     * Sign up a customer.
     */
    signUpButtonClicked: function () {
      let name = this.firstName + " " + this.lastName;
      let username = this.username;
      let password = this.password;
      let email = this.email;
      let address = this.address;
      let phoneNo = this.phoneNo;
      AXIOS.post("users/customers/create_to_most_recent_system",
          {
            // Request body
            username: username,
            password: password,
            name: name,
            phoneNo: phoneNo,
            homeAddress: address,
            email: email
          },
      ).then(resp => {
        let response = resp.data;
        if (response.hasError) {
          throw new Error(response.error);
        }
        this.displaySignUp = false;
        this.displayLogIn = true;
        this.email = "";
        this.address = "";
        this.phoneNo = "";
        this.$alert("Done!");
      }).catch(e => {
        this.isLoading = false;
        console.error(e.toString());
        this.$alert(e.toString());
      })
    },

    /**
     * Log in a customer.
     * @constructor
     */
    LoginButtonClicked: function () {
      this.isLoading = true;
      AXIOS.get("users/get_user_info", {
        params: {
          username: this.username
        }
      }).then(resp => {

        this.isLoading = false;

        let userType = resp.data;

        if (userType.hasError)
          throw new Error(userType.error);

        if (userType === "notExist") {
          // The username entered does not exist in the database.
          throw new Error("Username entered is not correct.");
        } else if (userType === "owner") {
          AXIOS.get("/users/owners/get_by_username", {
            params: {
              username: this.username
            }
          }).then(resp => {
            let ownerInformation = resp.data;

            if (ownerInformation.hasError)
              throw new Error(ownerInformation.error);

            let password = ownerInformation.password;
            if (this.password === password) {
              localStorage.setItem('userInformation', JSON.stringify(ownerInformation));
              localStorage.setItem('userRole', userType)
              this.$router.push("/user")
            } else
              throw new Error("Password is incorrect.")
          }).catch(e => {
            console.error(`ERROR: ${e.toString()}`);
            this.$alert(e.toString());
          })
        } else if (userType === "assistant") {
          AXIOS.get("/users/assistants/get_by_username", {
            params: {
              username: this.username
            }
          }).then(resp => {
            let assistantInformation = resp.data;

            if (assistantInformation.hasError)
              throw new Error(assistantInformation.error);

            let password = assistantInformation.password;
            if (this.password === password) {
              localStorage.setItem('userInformation', JSON.stringify(assistantInformation));
              localStorage.setItem('userRole', userType)
              this.$router.push("/user")
            } else
              throw new Error("Password is incorrect.")
          }).catch(e => {
            console.error(`ERROR: ${e.toString()}`)
            this.$alert(e.toString());
          })
        } else if (userType === "customer") {
          AXIOS.get("/users/customers/get_by_username", {
            params: {
              username: this.username
            }
          }).then(resp => {
            let customerInformation = resp.data;

            if (customerInformation.hasError)
              throw new Error(customerInformation.error);

            let password = customerInformation.password;
            if (this.password === password) {
              let appointments = customerInformation.appointments;
              // eslint-disable-next-line no-debugger
              debugger;
              for (let i = 0; i < appointments.length; i++) {
                appointments[i].isPaid = this.getAppointmentIsPaid(appointments[i].bill);
                appointments[i].isDeletable = this.isAppointmentDeletable(appointments[i].shift);
              }
              localStorage.setItem('userInformation', JSON.stringify(customerInformation));
              localStorage.setItem('userRole', userType)
              this.$router.push("/user")
            } else
              throw new Error("Password is incorrect.")
          }).catch(e => {
            console.error(`ERROR: ${e.toString()}`)
            this.$alert(e.toString());
          })
        }
      }).catch(e => {
        this.isLoading = false;
        console.error(`ERROR: ${e.toString()}`)
        this.$alert(e.toString());
      })
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
  }
}
</script>

<style scoped>
.login-form {
  width: 40em;
  height: max-content;
  padding-bottom: 40px;
  background-color: whitesmoke;
  border-radius: 10px;
  margin-left: auto;
  margin-right: auto;
  margin-top: 100px;
  display: flex;
  flex-direction: column;
  align-items: center;
  align-content: space-around;
  justify-content: flex-start;
}

.log-in-sign-up-selector {
  width: 80%;
  height: 5em;
  margin-top: 50px;
  margin-left: auto;
  margin-right: auto;
  display: table;
  vertical-align: center;
}

.selector {
  display: table-cell;
  vertical-align: middle;
  background-color: silver;
  color: white;
  width: 50%;
}

.selector-box {
  height: 100%;
  width: 100%;
  text-align: center;
  line-height: 100px;
  font-size: 1.5em;
  font-family: Roboto, sans-serif;
  transition: ease .5s;
}

.selector-box:hover {
  background-color: darkgray;
}

.title {
  width: 80%;
  height: 80px;
  margin-top: 30px;
  margin-left: auto;
  margin-right: auto;
  text-align: center;
  line-height: 80px;
  font-size: 40px;
  font-family: Roboto, sans-serif;
}

.form-container {
  width: 80%;
  height: max-content;
  margin-top: 40px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-around;
}

.form-input {
  margin-bottom: 15px;
}

.logo {
  width: 100%;
  height: 100px;
  margin-left: auto;
  margin-right: auto;
  margin-top: 80px;
  margin-bottom: 100px;
  background-color: white;
  font-family: 'Train One', cursive;
  font-size: 50px;
  text-align: center;
  font-weight: bold;
}
</style>