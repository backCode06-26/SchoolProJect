<script setup>
import { defineProps, reactive} from 'vue';
import { useStore } from 'vuex';
import VueDraggableResizable from 'vue-draggable-resizable';

// 부모 컴포넌트에서 전달된 props
const props = defineProps({
  cardData: {
    type: Object,
    required: true
  }
});

// 내부 상태로 reactive 객체로 변환
const cardData = reactive({ ...props.cardData });

const store = useStore();

// 이벤트 핸들러 정의
function onDragging(newX, newY) {
  cardData.x = newX;
  cardData.y = newY;
  console.log(newX, newY)
  updateCard(); // 카드 상태 업데이트
}

function onResize(newWidth, newHeight) {
  cardData.width = newWidth;
  cardData.height = newHeight;
  updateCard(); // 카드 상태 업데이트
}

// 카드 삭제 함수
function cardDelete() {
  store.dispatch('removeCard', cardData.id); // 카드 삭제
}

// 상태 업데이트 함수
function updateCard() {
  store.dispatch('updateCard', cardData); // 카드 업데이트
}
</script>

<template>
  <vue-draggable-resizable
    v-model:width="cardData.width"
    v-model:height="cardData.height"
    :x="cardData.x"
    :y="cardData.y"
    :min-width="10"
    :min-height="10"
    :bounds="'parent'"
    class="business-card"
    @dragging="onDragging"
    @resize="onResize"
  >
    <div class="card-content">
      <h2 v-if="cardData.type === 'title'">
        {{ cardData.content }}
      </h2>
      <p v-else>
        {{ cardData.content }}
      </p>
      <input
        type="button"
        value="삭제"
        @click="cardDelete"
      >
    </div>
  </vue-draggable-resizable>
</template>

<style scoped></style>
