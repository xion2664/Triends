<script setup>
import { ref, onMounted } from "vue";
import { storeToRefs } from "pinia";
import { useRouter } from "vue-router";
import { useMemberStore } from "@/stores/member";

import BoardFormItem from "./item/BoardFormItem.vue";

const router = useRouter();
const memberStore = useMemberStore();
const { checkToken } = memberStore;
const { isValidToken, userInfo } = storeToRefs(memberStore);

const userId = ref();

onMounted(() => {
  userId.value = userInfo.value.userId;
});
</script>

<template>
  <BoardFormItem type="regist" :userId="userId" />
</template>

<style scoped></style>
