<script setup>
import { RouterLink, RouterView } from "vue-router";
import TheHeader from "@/components/layout/TheHeader.vue";
import TheFooter from "@/components/layout/TheFooter.vue";
</script>

<template>
  <TheHeader></TheHeader>

  <main>
    <RouterView></RouterView>
  </main>

  <TheFooter></TheFooter>
</template>

<style scoped></style>
