<template>
  <div id="app">
    <h1>명함 디자인 툴</h1>

    <!-- 명함을 추가할 버튼들 -->
    <input
      type="button"
      value="title"
      @click="save('title')"
    >
    <input
      type="button"
      value="content"
      @click="save('content')"
    >

    <!-- 명함을 디자인할 영역 (canvas 영역) -->
    <div class="canvas">
      <!-- CardBox 컴포넌트를 cards 배열에 따라 반복 렌더링 -->
      <CardBox
        v-for="card in store.state.cards"
        :key="card.id"
        :card-data="card"
        @click="showCard(card)"
      />
    </div>
  </div>
</template>

<script setup>
import { useStore } from "vuex";
import CardBox from "@/components/CardBox.vue";

function showCard(card) {
  alert(JSON.stringify(card))
}

// Vuex store 사용
const store = useStore();

// save 함수 정의: 카드의 타입을 전달하여 카드 추가
function save(type) {
  store.dispatch('addCard', type)
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  text-align: center;
  margin-top: 50px;
}

.canvas {
  position: relative; /* 부모 영역에 상대적인 위치 */
  width: 1000px;
  height: 500px;
  border: 2px solid #ccc;
  margin: 10px auto;
}

.business-card {
  border: 1px solid #000;
  background-color: #f4f4f4;
  padding: 10px;
  position: absolute; /* 자식 요소는 부모에 상대적인 절대 위치 */
}

.card-content {
  text-align: center;
}

button {
  margin-top: 20px;
  padding: 10px;
  font-size: 16px;
}
</style>
