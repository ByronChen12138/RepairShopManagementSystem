import Vue from 'vue'
import App from './App.vue'
import router from "./router"

// Local imports.
import SectionTitle from "@/components/section-title";
import HorizontalGallery from "@/components/horizontal-gallery";
import UpdateCarForm from "@/components/update-car-form";
import alert from './modules/alert';
import ButtonWrapper from "@/components/button/button-wrapper";
import InputBox from "@/components/input-box/input-box";

import Calendar from 'v-calendar/lib/components/calendar.umd';
import DatePicker from 'v-calendar/lib/components/date-picker.umd';
import VueSlideoutPanel from 'vue2-slideout-panel';

Vue.config.productionTip = false

Vue.use(alert);
Vue.use(VueSlideoutPanel);
Vue.component('calendar', Calendar);
Vue.component('date-picker', DatePicker);

Vue.component('MyInput', InputBox);
Vue.component("MyButton", ButtonWrapper);
Vue.component('car-update-form', UpdateCarForm);
Vue.component('section-title', SectionTitle);
Vue.component('horizontal-gallery', HorizontalGallery);
new Vue({
    router: router,
    render: h => h(App),
}).$mount('#app')