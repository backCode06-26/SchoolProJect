<script setup>
import { reactive } from "vue";
import VueDraggableResizable from "vue-draggable-resizable";

const props = defineProps({
  cardWidth: {
    type: Number,
    required: true,
  },
  cardHeight: {
    type: Number,
    required: true,
  },
  cardX: {
    type: Number,
    required: true,
  },
  cardY: {
    type: Number,
    required: true,
  },
  cardTitle: {
    type: String,
    required: false,
    default: "Default Title"
  },
});

// props로 받은 값을 reactive 객체로 만듭니다
const state = reactive({
  title: props.cardTitle,
  width: props.cardWidth,
  height: props.cardHeight,
  x: props.cardX,
  y: props.cardY,
});

// 이벤트 핸들러를 정의합니다
function onDragging(newX, newY) {
  state.x = newX;
  state.y = newY;
}

function onResize(newWidth, newHeight) {
  state.width = newWidth;
  state.height = newHeight;
}
</script>

<template>
  <vue-draggable-resizable
    v-model:width="state.width"
    v-model:height="state.height"
    :x="state.x"
    :y="state.y"
    :min-width="200"
    :min-height="100"
    :bounds="'parent'"
    class="business-card"
    @dragging="onDragging"
    @resize="onResize"
  >
    <div class="card-content">
      <h2>{{ state.title }}</h2>
    </div>
  </vue-draggable-resizable>
</template>

<style scoped></style>
