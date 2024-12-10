<template>
  <div id="app">
    <h1>명함 디자인 툴</h1>

    <!-- 명함을 디자인할 영역 -->
    <div class="canvas"></div>
  </div>
</template>

<script setup></script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  text-align: center;
  margin-top: 50px;
}

.canvas {
  position: relative;
  width: 100%;
  height: 500px;
  border: 2px solid #ccc;
}

.business-card {
  border: 1px solid #000;
  background-color: #f4f4f4;
  padding: 10px;
  position: absolute;
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
