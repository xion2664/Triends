<template>
  <div class="profile-container">
    <img
      :src="catImageUrl"
      alt="Cat Profile Image"
      class="profile-image"
      :style="{ width: size + 'px', height: size + 'px' }"
    />
  </div>
</template>

<script setup>
import { ref, onMounted, defineProps } from "vue";
import axios from "axios";

const props = defineProps({
  size: {
    type: Number,
    default: 300, // 기본 크기는 200px
  },
});

const catImageUrl = ref("");

onMounted(() => {
  fetchCatImage();
});

const fetchCatImage = async () => {
  try {
    const response = await axios.get(
      "https://api.thecatapi.com/v1/images/search"
    );
    catImageUrl.value = response.data[0].url;
  } catch (error) {
    console.error("Error fetching cat image:", error);
  }
};
</script>

<style>
.profile-container {
  display: flex;
  justify-content: center;
  align-items: center;
}

.profile-image {
  border-radius: 50%;
  object-fit: cover;
}
</style>
