<template>
  <div class="root">
    <div class="container">

      <!--The title row.-->
      <div class="shift-row">
        <div class="date-column title-font">DATE</div>
        <div class="start-time-column title-font">START TIME</div>
        <div class="end-time-column title-font">END TIME</div>
        <div class="service-type-column title-font">SERVICE TYPE</div>
        <div class="price-column title-font">PRICE</div>
        <div class="select-column title-font">PAY</div>
        <div class="select-column title-font">Cancel</div>
      </div>

      <!--Contents of the table. If there is no content, show message instead.-->
      <transition name="fade" mode="out-in">
        <div v-if="this.appointments.length === 0"
             style="width: 100%; height: 2em; font-size: 30px; text-align: center; margin-top: 40px">
          Sorry, you do not have any appointment yet.
        </div>
        <transition-group name="list-complete" tag="div" v-else>
          <div class="shift-row" v-for="appointment in this.appointments" :key="appointment.appointmentId">
            <div class="date-column">{{ getAppointmentDate(appointment) }}</div>
            <div class="start-time-column">{{ getAppointmentStartTime(appointment) }}</div>
            <div class="end-time-column">{{ getAppointmentEndTime(appointment) }}</div>
            <div class="service-type-column">{{ getAppointmentServiceType(appointment) }}</div>
            <div class="price-column">{{ getAppointmentPrice(appointment) }}</div>
            <transition name="pay" mode="out-in">
              <div class="select-column select-button" v-if="!appointment.isPaid"
                   @click="$emit('payment-made', `${appointment.appointmentId}`)" key="pay">
                Pay
              </div>
              <div class="select-column" v-else key="paid">
                Paid
              </div>
            </transition>
            <transition name="delete" mode="out-in">
              <div class="select-column select-button" v-if="appointment.isDeletable"
                   @click="$emit('delete-made', appointment.appointmentId)" key="cancel">
                Cancel
              </div>
              <div class="select-column" v-else key="canceled">
                Over
              </div>
            </transition>
          </div>
        </transition-group>
      </transition>
    </div>
  </div>
</template>

<script>

export default {
  name: "appointment-table",
  data: function () {
    return {
      appointments: Array
    }
  },
  props: {
    customerInfo: Object,
  },
  created() {
    // Setting up
    this.appointments = this.customerInfo.appointments
  },
  methods: {
    /**
     * Get the date of the appointment.
     * @param appointment: An appointment object.
     * @returns {*}
     */
    getAppointmentDate: function (appointment) {
      let shift = appointment.shift;
      return shift.date;
    },
    /**
     * Get the start time of the appointment.
     * @param appointment: An appointment object.
     * @returns {*}
     */
    getAppointmentStartTime: function (appointment) {
      let shift = appointment.shift;
      return shift.startTime;
    },
    /**
     * Get the end time of the appointment.
     * @param appointment: An appointment object.
     * @returns {*}
     */
    getAppointmentEndTime: function (appointment) {
      let shift = appointment.shift;
      return shift.endTime;
    },

    /**
     * Get the service type of the appointment.
     * @param appointment: An appointment object.
     * @returns {default.methods.selectedService}
     */
    getAppointmentServiceType: function (appointment) {
      let service = appointment.service;
      return service.serviceType;
    },
    /**
     * Return the total bill price of an appointment.
     * @param appointment: An appointment object.
     * @returns {number}
     */
    getAppointmentPrice: function (appointment) {
      let totalBillPrice = 0;
      let bills = appointment.bill;
      for (let i = 0; i < bills.length; i++)
        if (!bills[i].isPaid)
          totalBillPrice += bills[i].price;
      return totalBillPrice;
    },
  },
  computed: {
    getUserAppointments: function () {
      return this.customerInfo.appointments
    },
  }
}
</script>

<style scoped>
* {
  font-family: Roboto, sans-serif;
}

.container {
  width: 85%;
  height: max-content;
  margin-top: 20px;
  padding-left: 60px;
}

.shift-row {
  width: 95%;
  height: 2.7em;
  display: table;
  border-bottom: gray dashed 1px;
}

.date-column {
  display: table-cell;
  width: 17%;
  height: 100%;
  font-size: 20px;
  line-height: 2.7em;
  text-align: center;
}

.start-time-column {
  display: table-cell;
  width: 17%;
  height: 100%;
  font-size: 20px;
  line-height: 2.7em;
  text-align: center;
}

.end-time-column {
  display: table-cell;
  width: 17%;
  height: 100%;
  font-family: Roboto, sans-serif;
  font-size: 20px;
  line-height: 2.7em;
  text-align: center;
}

.service-type-column {
  display: table-cell;
  width: 17%;
  height: 100%;
  font-size: 20px;
  line-height: 2.7em;
  text-align: center;
}

.price-column {
  display: table-cell;
  width: 10%;
  height: 100%;
  font-size: 20px;
  line-height: 2.7em;
  text-align: center;
}

.select-column {
  display: table-cell;
  width: 10%;
  height: 100%;
  font-family: Roboto, sans-serif;
  font-size: 20px;
  line-height: 2.7em;
  text-align: center;
}

.title-font {
  font-family: "Playfair Display SC", serif;
}

.select-button {
  color: lightblue;
  font-style: italic;
  transition: .5s ease;
}

.select-button:hover {
  color: blue;
  transition: .5s ease;
}

.list-complete-enter-from,
.list-complete-leave-to {
  opacity: 0;
}

.list-complete-leave-active {
  position: absolute;
}

.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.7s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}

.pay-enter-active,
.pay-leave-active {
  transition: opacity 0.7s ease;
}

.pay-enter-from,
.pay-leave-to {
  opacity: 0;
}

.delete-enter-active,
.delete-leave-active {
  transition: opacity 0.7s ease;
}

.delete-enter-from,
.delete-leave-to {
  opacity: 0;
}
</style>