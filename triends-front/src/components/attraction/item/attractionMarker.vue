<script setup>
import { onMounted, ref } from "vue";
import { attractionList } from "@/api/attraction";
import { NaverMarker, NaverInfoWindow } from "vue3-naver-maps";

const marker = ref();
const infoWindow = ref();
const isOpen = ref(true);

const onLoadMarker = (markerObject) => {
  marker.value = markerObject;
};

const onLoadInfoWindow = (infoWindowObject) => {
  infoWindow.value = infoWindowObject;
};

const sidoCode = ref(1);
const contentTypeId = ref(12);
const attractions = ref([]);

onMounted(() => {
  getAttractionList();
});

function getAttractionList() {
  attractionList(
    sidoCode.value,
    contentTypeId.value,
    ({ data }) => {
      attractions.value = data;
    },
    (error) => console.error()
  );
}
</script>

<template>
  <naver-marker
    v-for="attraction in attractions"
    :key="attraction.contentId"
    :latitude="attraction.latitude"
    :longitude="attraction.longitude"
    @onLoad="onLoadMarker($event)"
    @mouseover="isOpen = !isOpen"
    @mouseout="isOpen = !isOpen"
    @click="moveDetail(attraction.contentId)"
  >
  </naver-marker>
  <naver-info-window
    :marker="marker"
    :open="!isOpen"
    @onLoad="onLoadInfoWindow($event)"
  >
    <div class="infoWindow-content" style="width: 250px">
      <img :src="attraction.firstImage" alt="" style="width: 100%" />
      <h4>{{ attraction.title }}</h4>
      <p>{{ attraction.addr1 }}</p>
      <p style="overflow: hidden; text-overflow: ellipsis; white-space: nowrap">
        {{ attraction.overview }}
      </p>
    </div>
  </naver-info-window>
</template>

<style scoped></style>
