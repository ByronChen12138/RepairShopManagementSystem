<template>
  <div class="root">
    <div class="container">
      <div class="section">
        <section-title title="View Business Info" sub-title="A new vision for your business."></section-title>
        <div class="view-info-row">
          <div class="view-info-row-description">Business Name:</div>
          <div class="view-info-row-information">{{ businessName }}</div>
        </div>
        <div class="view-info-row">
          <div class="view-info-row-description">Address:</div>
          <transition name="fade" mode="out-in">
            <div class="view-info-row-information" v-if="!isUpdatingBasicInformation" key="display">{{
                businessAddress
              }}
            </div>
            <div class="view-info-row-information" v-else>
              <input class="form-input" v-model="updatedAddress" key="input">
            </div>
          </transition>
        </div>
        <div class="view-info-row">
          <div class="view-info-row-description">Phone No:</div>
          <transition name="fade" mode="out-in">
            <div class="view-info-row-information" v-if="!isUpdatingBasicInformation" key="display">{{
                businessPhoneNo
              }}
            </div>
            <div class="view-info-row-information" v-else>
              <input class="form-input" v-model="updatedPhoneNo" key="input">
            </div>
          </transition>
        </div>
        <div
            style="display: flex; width: 60%; height: 100px; flex-direction: row; align-items: center; margin-top: 30px; margin-left: 32%; margin-bottom: 100px">
          <div style="width: max-content; margin-right: 1.5em">
            <my-button background-color="black" @button-clicked="isUpdatingBasicInformation=!isUpdatingBasicInformation"
                       :text="switchButtonText" style="width: 150px">
            </my-button>
          </div>
          <div style="width: max-content">
            <my-button background-color="black" @button-clicked="updateBusinessInformation"
                       text="Update" style="width: 150px">
            </my-button>
          </div>
        </div>
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
  name: "owner-editing-business-info-page",
  data: function () {
    return {
      isUpdatingBasicInformation: false,
      businessInfo: {},
      updatedAddress: "",
      updatedPhoneNo: "",
    };
  },
  created() {
    this.businessInfo = JSON.parse(localStorage.getItem('businessInfo'));
  },
  computed: {
    businessName() {
      return this.businessInfo.businessName;
    },
    businessAddress() {
      return this.businessInfo.businessAddress;
    },
    businessPhoneNo() {
      return this.businessInfo.businessPhoneNumber;
    },
    switchButtonText() {
      return this.isUpdatingBasicInformation ? "Abort" : "Edit";
    }
  },
  methods: {

    /**
     * Update the business information.
     */
    updateBusinessInformation() {
      AXIOS.put("/system/update_most_recent", {}, {
        params: {
          updatedAddress: this.updatedAddress,
          updatedPhoneNo: this.updatedPhoneNo
        }
      }).then(resp => {
        let re = resp.data;
        if (re.hasError)
          throw new Error(re.error);
        this.businessInfo = re;
        localStorage.setItem('businessInfo', JSON.stringify(this.businessInfo));
        this.updatedPhoneNo = "";
        this.updatedAddress = "";
        this.isUpdatingBasicInformation = false;
        this.$alert("Done!");
      }).catch(e => {
        console.error(e.toString());
        this.$alert(e.toString());
      });
    }
  }
}
</script>

<style scoped>

.root {
  width: 100%;
  height: max-content;
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