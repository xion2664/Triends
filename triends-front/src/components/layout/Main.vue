<script setup>
import { ref } from "vue";
import { useRoute } from "vue-router";

const route = useRoute();
</script>

<template>
  <div class="ad-banner">
    <a href=""><img src="ad-1" alt="메인 이벤트" /></a>
  </div>
  <div class="main-content">
    <a href=""><img src="aboutus" alt="사이트 소개" /></a>
    <a href=""><img src="places" alt="여행지 탐색" /></a>
    <a href=""><img src="board" alt="여행 후기 게시판" /></a>
  </div>
</template>

<style scoped></style>
