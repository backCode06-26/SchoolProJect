const path = require("path"); // path 모듈을 불러옵니다.

module.exports = {
  transpileDependencies: true, // 의존성 트랜스파일링 활성화
  configureWebpack: {
    resolve: {
      alias: {
        "vue-draggable-resizable": path.resolve(
          __dirname,
          "node_modules/vue-draggable-resizable"
        ), // alias 설정
      },
      fallback: {
        fs: false, // fs 모듈 사용 안 함
        path: require.resolve("path-browserify"), // path 모듈을 path-browserify로 대체
      },
    },
  },
};
