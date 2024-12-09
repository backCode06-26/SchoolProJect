<template>
  <div :style="divStyle">
    <vue-draggable-resizable
      :w="width"
      :h="height"
      :parent="true"
      :active="isActive"
      @resize="onResize"
      class="box"
    >
      <input type="text" v-model="texts" placeholder="Enter some text" />
    </vue-draggable-resizable>
  </div>
</template>

<script setup>
import { ref, computed } from "vue";
import VueDraggableResizable from "vue-draggable-resizable";

const isActive = ref(true);
const texts = ref([]); // 'texts'는 배열로 초기화

const onResize = (newWidth, newHeight) => {
  console.log("Resized to:", newWidth, newHeight);
};

const width = ref(340);
const height = ref(189);

// computed를 사용해 divStyle을 동적으로 계산
const divStyle = computed(() => ({
  position: "relative",
  width: `${width.value * 2}px`, // width를 동적으로 설정
  height: `${height.value * 2}px`, // height를 동적으로 설정
  border: "1px solid blue",
  margin: "1em auto",
  "box-sizing": "border-box",
}));
</script>

<style scoped>
.box {
  border: 1px solid red;
}
</style>
