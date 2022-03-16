<template>
  <div class="root">
    <div class="container">

      <!--Space creation.-->
      <div class="section">
        <section-title title="Create a space" sub-title="after all, you need somewhere to work"></section-title>
        <div class="view-info-row">
          <div class="view-info-row-description">Maximum weight load:</div>
          <div class="view-info-row-information">
            <input class="form-input" v-model="weightLoad" key="input">
          </div>
        </div>
        <div
            style="display: flex; width: 60%; height: 100px; flex-direction: row; align-items: center; justify-content: space-around; margin-top: 30px; margin-left: 15%;">
          <div style="width: max-content">
            <my-button background-color="black" text="Add" @button-clicked="addSpaceClicked" style="width: 150px"
                       :is-loading="isLoading"></my-button>
          </div>

        </div>
      </div>

      <!--Space table. View all spaces registered.-->
      <div class="section" style="margin-bottom: 150px">
        <section-title title="All Spaces" sub-title="All space that exists now"></section-title>
        <space-table :space="this.allSpace"></space-table>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios"

const config = require("../configuration")

const AXIOS = axios.create({
  baseURL: config.springServer.baseUrl,
});

import SpaceTable from ".././components/space-table"

export default {
  name: "space-page",
  data: function () {
    return {
      userInfo: Object,
      weightLoad: "",
      allSpace: [],
      isLoading: false,
    }
  },
  created() {
    AXIOS.get("/appointment/space/get_all", {}).then(resp => {
      this.allSpace = resp.data;
    }).catch(e => {
      console.error(e.toString())
    });
  },
  components: {
    "SpaceTable": SpaceTable
  },
  methods: {
    /**
     * Add a space to the backend.
     */
    addSpaceClicked: function () {
      this.isLoading = true;
      let maxWeightLoad = this.weightLoad;
      AXIOS.post("appointment/space/create", {},
          {
            params: {
              weight: maxWeightLoad
            }
          }
      ).then(resp => {
        this.isLoading = false;
        let spaceData = resp.data;
        if (spaceData.hasError)
          throw new Error(spaceData.error);

        this.allSpace.push(spaceData);
      }).catch(e => {
        this.isLoading = false;
        console.error(e.toString());
        this.$alert(e.toString());
      })

    }
  },

  mounted() {
    // Load user info from local storage.
    this.userInfo = JSON.parse(localStorage.getItem('userInformation'));
  },
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

  font-family: sans-serif;
  font-size: 18px;
  text-decoration: none;

  transition: border-color .4s ease, box-shadow .4s ease;
}

.form-input:focus {
  border: red 1px solid;
  transition: border-color .4s ease, box-shadow .4s ease;
}

.fade-enter-active, .fade-leave-active {
  transition: opacity 0.3s ease-in-out;
}

.fade-enter, .fade-leave-to /* .fade-leave-active below version 2.1.8 */
{
  opacity: 0;
}


</style>