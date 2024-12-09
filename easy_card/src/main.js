import { createApp } from "vue";
import App from "./App.vue";
import "./registerServiceWorker";
import router from "./router";
import store from "./store";

// FontAwesome 관련 패키지 import
import { library } from "@fortawesome/fontawesome-svg-core";
import { faUser, faCoffee, faGear } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";

import VueDraggableResizable from "vue-draggable-resizable";

// 아이콘들을 라이브러리에 추가
library.add(faUser, faCoffee, faGear);

createApp(App)
  .component("font-awesome-icon", FontAwesomeIcon)
  .component("vue-draggable-resizable", VueDraggableResizable)
  .use(store)
  .use(router)
  .mount("#app");
