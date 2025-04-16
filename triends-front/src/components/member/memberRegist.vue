<script setup>
import { reactive, ref, watch } from "vue";
import { useRouter } from "vue-router";

import { memberRegist } from "@/api/member";

import "@/assets/css/member/memberSign.css";

const router = useRouter();

const param = reactive({
  userId: "",
  userPass: "",
  userName: "",
  email: "",
  phone: "",
});

const userPassCheck = ref("");
const diffPass = ref(false);

watch([param, userPassCheck], ([nV1, nV2], [oV1, oV2]) => {
  diffPass.value = nV1.userPass !== nV2;
});



import Swal from 'sweetalert2'
function regist() {
  memberRegist(
    param,
    (response) => {
      Swal.fire({
        title: '회원가입 완료',
        text: '트렌즈가 된 것을 환영합니다!',
        icon: 'success',
        confirmButtonColor: '#84B891', // 여기에 원하는 색상 코드를 입력하세요
      })
      moveLogin();
    },
    (error) => {
      alert("회원가입 실패!");
      console.error();
    }
  );
}

function moveLogin() {
  router.push({ name: "member-login" });
}
</script>

<template>
  <div class="container text-center">
    <div>
      <h1>회원가입</h1>
    </div>
    <div class="form">
      <input
        class="sign sign-input"
        type="text"
        placeholder="아이디"
        v-model="param.userId"
      />
      <input
        class="sign sign-input"
        type="password"
        placeholder="비밀번호"
        v-model="param.userPass"
      />
      <input
        class="sign sign-input"
        type="password"
        placeholder="비밀번호확인"
        v-model="userPassCheck"
      />
      <p class="diff-pass" :style="[diffPass ? '' : 'display:none']">
        비밀번호가 다릅니다
      </p>
      <input
        class="sign sign-input"
        type="text"
        placeholder="닉네임"
        v-model="param.userName"
      />
      <input
        class="sign sign-input"
        type="email"
        placeholder="이메일"
        v-model="param.email"
        required
      />

      <input
        class="sign sign-input"
        type="text"
        placeholder="전화번호"
        v-model="param.phone"
        @keyup.enter="regist"
      />
      <button class="sign sign-button" @click="regist">Register</button>
    </div>
  </div>
</template>
<style scoped>
h1 {
  margin-bottom: 40px;
}
.diff-pass {
  font-size: 11px;
  color: red;
  margin: 0px;
}
.regist-input {
  width: 350px;
  height: 45px;
  background-color: rgb(231, 231, 231);
  border: 0px;
  padding-left: 15px;
  border-radius: 15px;
  margin: 15px;
}

.regist-button {
  color: white;
  width: 350px;
  height: 45px;
  background-color: rgb(50, 62, 62);
  border: 0px;
  padding-left: 15px;
  border-radius: 15px;
  margin: 10px;
}
</style>
