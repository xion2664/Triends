<script setup>
import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { storeToRefs } from "pinia";

import { useMemberStore } from "@/stores/member";
import { userModify, memberDelete } from "@/api/member";

import RandomCatProfile2 from "@/components/member/item/randomCatProfile2.vue";

import "@/assets/css/member/memberInfo.css";

const route = useRoute();
const router = useRouter();
const memberStore = useMemberStore();

const { getUserInfo } = memberStore;

const startEditing = (no) => {
  isEditing.value[no] = true;
  changed.value = true;
};

const stopEditing = (no) => {
  isEditing.value[no] = false;
  changed.value = true;
};

const { userLogout, checkToken } = memberStore;
const { isLogin, isValidToken, userInfo } = storeToRefs(memberStore);

const isEditing = ref([false, false, false, false, false]);

const changed = ref(false);

const changeUserInfo = ref({
  userId: "",
  userPass: "",
  userName: "",
  email: "",
  phone: "",
});

function modify() {
  userModify(
    changeUserInfo.value,
    () => {
      getUserInfo(sessionStorage.getItem("accessToken"));
      changed.value = false;
    },
    (error) => console.log(error)
  );
  changeUserInfo.value.userPass = null;
}

onMounted(() => {
  changeUserInfo.value = { ...userInfo.value };
});

import Swal from "sweetalert2";
const confirmDelete = (userId) => {
  Swal.fire({
    title: "정말로 탈퇴하시겠습니까?",
    text: "더이상 트렌즈와 함께 할 수 없습니다.",
    icon: "warning",
    showCancelButton: true,
    confirmButtonColor: "#B3B3B3",
    cancelButtonColor: "#84B891",
    confirmButtonText: "트렌즈와 헤어지기",
    cancelButtonText: "트렌즈와 함께 하기",
  }).then((result) => {
    if (result.isConfirmed) {
      memberDelete(
        userId,
        (response) => {
          Swal.fire({
            title: "탈퇴 완료",
            text: "그동안 트렌즈와 함께 해서 행복했어요.",
            icon: "success",
            confirmButtonColor: "#84B891", // 여기에 원하는 색상 코드를 입력하세요
          });
          userLogout();
          router.push({ name: "home" });
        },
        (error) => {
          console.log(error);
        }
      );
    }
  });
};
</script>

<template>
  <div class="info-container">
    <div class="info-space">
      <div class="profile">
        <random-cat-profile-2></random-cat-profile-2>
        <div class="name-space">
          <p>{{ changeUserInfo.userId }}</p>
        </div>
      </div>
      <div class="infos">
        <div class="info id-space">
          <img src="@/assets/img/icon/user.svg" alt="" />
          <p
            class="editable-text"
            v-if="!isEditing[1]"
            @click="startEditing(1)"
          >
            {{ changeUserInfo.userName }}
          </p>
          <input
            v-else
            v-model="changeUserInfo.userName"
            @blur="stopEditing(1)"
            placeholder="변경할 이름"
          />
        </div>
        <hr />
        <div class="info email-space">
          <img src="@/assets/img/icon/message-solid.svg" alt="" />
          <p
            class="editable-text"
            v-if="!isEditing[3]"
            @click="startEditing(3)"
          >
            {{ changeUserInfo.email }}
          </p>
          <input
            v-else
            v-model="changeUserInfo.email"
            @blur="stopEditing(3)"
            placeholder="변경할 이메일"
          />
        </div>
        <hr />
        <div class="info phone-space">
          <img src="@/assets/img/icon/phone-solid.svg" alt="" />
          <p
            class="editable-text"
            v-if="!isEditing[4]"
            @click="startEditing(4)"
          >
            {{ changeUserInfo.phone }}
          </p>
          <input
            v-else
            v-model="changeUserInfo.phone"
            @blur="stopEditing(4)"
            placeholder="변경할 전화번호"
          />
        </div>
        <hr />
        <div class="info etcs">
          <div class="pw-space">
            <div v-if="!isEditing[2]" @click="startEditing(2)">
              <img
                v-if="!changeUserInfo.userPass"
                src="@/assets/img/icon/lock.svg"
                id="lock"
                alt=""
              />
              <span v-else>새 비밀번호: {{ changeUserInfo.userPass }}</span>
            </div>
            <input
              v-else
              v-model="changeUserInfo.userPass"
              @blur="stopEditing(2)"
              placeholder="새 비밀번호"
            />
          </div>
          <div class="delete">
            <a
              href="#"
              role="button"
              data-bs-toggle="dropdown"
              aria-expanded="false"
            >
              <img
                class="invite-icon clickable-img"
                src="@/assets/img/icon/dot-vertical.svg"
                alt=""
              />
            </a>
            <ul>
              <li
                class="dropdown-menu"
                aria-labelledby="navbarDropdown"
                @click="confirmDelete(changeUserInfo.userId)"
              >
                탈퇴하기
              </li>
            </ul>
          </div>
        </div>
        <div class="modify">
          <button class="modify-button" v-if="changed" @click="modify">
            수정하기
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
