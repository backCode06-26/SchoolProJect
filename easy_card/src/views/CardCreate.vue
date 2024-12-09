<template>
  <div class="card">
    <div class="card-content" v-if="userInfo">
      <h2>{{ userInfo.lastName + "" + userInfo.firstName }}</h2>
      <p>전화번호 : {{ userInfo.phoneWork }}</p>
      <p>이메일 : {{ userInfo.email || "null" }}</p>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";

// userInfo 변수를 ref로 선언하여 반응형으로 만듭니다.
const userInfo = ref(null);

onMounted(() => {
  // 세션 스토리지에서 정보 불러오기
  const storedUserInfo = JSON.parse(sessionStorage.getItem("userInfoSave"));
  if (storedUserInfo) {
    userInfo.value = storedUserInfo;
  }
});
</script>

<style scoped>
.tel-container {
  margin: 10px;
}
.tel-container label {
  margin-right: 10px;
}

.card {
  border: 1px solid #ddd;
  padding: 20px;
  margin: 20px auto;
  border-radius: 8px;
  max-width: 400px;
}
.card-content h2 {
  margin: 0 0 10px 0;
}
.card-content p {
  margin: 5px 0;
}
</style>
