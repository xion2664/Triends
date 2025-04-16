<script setup>
import { reactive, ref } from "vue";
import { useRouter } from "vue-router";
import { storeToRefs } from "pinia";
import { useMemberStore } from "@/stores/member";

import "@/assets/css/member/memberSign.css";

const router = useRouter();
const memberStore = useMemberStore();

const { isLogin } = storeToRefs(memberStore);
const { userLogin, getUserInfo } = memberStore;

const loginInfo = ref({
  userId: "",
  userPass: "",
});

const login = async () => {
  await userLogin(loginInfo.value);

  if (isLogin.value) {
    router.push("/");
  } else {
    alert("로그인 실패");
  }
};

function moveRegist() {
  router.push({ name: "member-regist" });
}
</script>

<template>
  <div class="container center">
    <h1>로그인</h1>
    <div class="follow-txt">
      <span>아이디가 없나요? 지금 바로 </span>
      <a href="" @click.prevent="moveRegist">회원가입</a>
    </div>
    <div class="form">
      <input
        class="sign sign-input"
        type="text"
        placeholder="ID"
        v-model="loginInfo.userId"
      />
      <input
        class="sign sign-input"
        type="password"
        placeholder="Password"
        v-model="loginInfo.userPass"
        @keyup.enter="login"
      />
      <button class="sign sign-button clickable" @click="login">Login</button>
    </div>
  </div>
</template>

<style scoped></style>
