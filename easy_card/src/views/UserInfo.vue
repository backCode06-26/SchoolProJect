<script setup>
import { reactive } from "vue";
import { useRouter } from "vue-router";

const router = useRouter();

// 모든 입력 필드를 하나의 reactive 객체로 관리
const userInfo = reactive({
  firstName: "",
  lastName: "",
  phoneWork: "",
  email: "",
  address: "",
  city: "",
  country: "",
  company: "",
  title: "",
  birthday: ""
});

// 전화번호 자동 하이픈
const autoHyphen = (event) => {
  const target = event.target;
  target.value = target.value
    .replace(/[^0-9]/g, "")
    .replace(/^(\d{2,3})(\d{3,4})(\d{4})$/, `$1-$2-$3`);
  userInfo.phoneWork = target.value; // userInfo 업데이트
};

// 생일 포맷 처리
const dateFormats = () => {
  const parts = userInfo.birthday.split("-");
  return `${parts[0]}-${parts[1]}-${parts[2]}`;
};

// 입력값 검증
const checkValue = () => {
  let valid = true;
  if (!userInfo.firstName) {
    valid = false;
    alert("이름을 입력해주세요!");
  }
  else if (!userInfo.lastName) {
    valid = false;
    alert("성을 입력해주세요!");
  }
  else if (!userInfo.phoneWork) {
    valid = false;
    alert("전화번호을 입력해주세요!")
  }
  return valid;
};

// 정보 제출
const submitInfo = () => {
  if (checkValue()) {
    userInfo.birthday = dateFormats();
    sessionStorage.setItem("userInfoSave", JSON.stringify(userInfo));
    router.push("/qr_create");
  }
};
</script>

<template>
  <h2>정보 수정</h2>
  <form @submit.prevent="submitInfo">
    <div>
      <label>성(필수): </label>
      <input
        v-model="userInfo.lastName"
        type="text"
        name="lastName"
      >
    </div>
    <div>
      <label>이름(필수): </label>
      <input
        v-model="userInfo.firstName"
        type="text"
        name="firstName"
      >
    </div>
    <div>
      <label>전화번호(필수): </label>
      <input
        v-model="userInfo.phoneWork"
        type="text"
        name="phoneWork"
        @input="autoHyphen"
      >
    </div>
    <div>
      <label>이메일: </label>
      <input
        v-model="userInfo.email"
        type="email"
      >
    </div>
    <div>
      <label>주소: </label>
      <input
        v-model="userInfo.address"
        type="text"
      >
    </div>
    <div>
      <label>도시: </label>
      <input
        v-model="userInfo.city"
        type="text"
      >
    </div>
    <div>
      <label>국가: </label>
      <input
        v-model="userInfo.country"
        type="text"
      >
    </div>
    <div>
      <label>회사: </label>
      <input
        v-model="userInfo.company"
        type="text"
      >
    </div>
    <div>
      <label>직책: </label>
      <input
        v-model="userInfo.title"
        type="text"
      >
    </div>
    <div>
      <label>생일: </label>
      <input
        v-model="userInfo.birthday"
        type="date"
      >
    </div>
    <button type="submit">
      제출
    </button>
  </form>
</template>

<style scoped>
h2 {
  margin: 10px 0;
  text-align: center;
}

form {
  display: flex;
  flex-direction: column;
  align-items: center;
}

div {
  margin-bottom: 10px;
}

label {
  margin-right: 10px;
}

input {
  padding: 5px;
  border: 1px solid #ccc;
  border-radius: 4px;
}

button {
  padding: 10px 20px;
  border: none;
  border-radius: 4px;
  background-color: #4caf50;
  color: white;
  cursor: pointer;
}

button:hover {
  background-color: #45a049;
}
</style>
