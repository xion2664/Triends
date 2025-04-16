<script setup>
import { ref } from "vue";
import { useRoute } from "vue-router";
import { attractionDetail } from "@/api/attraction";

const route = useRoute();

const attraction = ref({});

function getAttractionDetail() {
  attractionDetail(
    route.params.contentId,
    ({ data }) => {
      attraction.value = data;
    },
    console.error()
  );
}

getAttractionDetail();
</script>

<template>
  <div class="container">
    <h1 class="attr-title">{{ attraction.title }}</h1>
    <img class="attr-image" :src="attraction.firstImage" alt="" />
    <div class="info-row">
      <div class="info-item">
        <img class="icon" src="@/assets/img/icon/location-icon.png" alt="" />
        <p>{{ attraction.addr1 }}</p>
      </div>
      <div class="info-item">
        <img class="icon" src="@/assets/img/icon/phone-icon.png" alt="" />
        <p v-if="attraction.tel">{{ attraction.tel }}</p>
        <p v-else>전화번호 정보가 없습니다.</p>
      </div>
    </div>
    <p>{{ attraction.homepage }}</p>
    <div class="overview">
      <img
        class="icon book-icon"
        src="@/assets/img/icon/book-icon.png"
        alt=""
      />
      <p>{{ attraction.overview }}</p>
    </div>
  </div>
</template>

<style scoped>
.attr-image {
  align-self: center;
}
.overview {
  width: 100%;
}
.attr-title {
  margin: 30px 0;
}

.container {
  width: 60%;
  display: flex;
  flex-direction: column;
}

.icon {
  height: 40px;
  width: auto;
}

.book-icon {
  margin-bottom: 10px;
}

.info-row {
  display: flex;
  justify-content: space-around;
  margin: 20px 0;
}

.info-item {
  display: flex;
  align-items: center;
}

.info-column {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  width: 50%;
  /* 각 컬럼의 너비 */
}

.info-row p {
  font-size: 20px;
  margin: 10px;
}
</style>
