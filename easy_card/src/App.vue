<template>
  <header id="header">
    <h1>
      <router-link to="/">
        EasyCard
      </router-link>
    </h1>
    <router-link to="/card_create">
      <font-awesome-icon
        :icon="['fas', 'gear']"
        class="custom-class"
      />
    </router-link>
  </header>
  <hr>
  <nav id="nav">
    <ul>
      <li>
        <router-link to="/qr_create">
          QR Code 생성
        </router-link>
      </li>
      <li>
        <router-link to="/user_info">
          사용자 정보
        </router-link>
      </li>
    </ul>
  </nav>
  <router-view />
</template>

<script setup></script>

<style>
@import "vue-draggable-resizable/dist/style.css";

* {
  margin: 0;
  padding: 0;
}

#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

a {
  text-decoration: none;
  font-weight: bold;
  color: #2c3e50;
}

#header {
  display: flex; /* flexbox 사용 */
  justify-content: space-between; /* h1과 아이콘을 양쪽 끝으로 배치 */
  align-items: center; /* 아이콘과 텍스트를 수평 중앙 정렬 */
  padding: 10px;
}

nav ul {
  display: flex;
  justify-content: center;
}

nav ul li {
  list-style: none;
  margin: 10px;
}

nav a.router-link-exact-active {
  color: #42b983;
}
</style>
