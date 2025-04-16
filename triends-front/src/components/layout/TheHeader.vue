<script setup>
import { RouterLink, useRouter } from "vue-router";
import { storeToRefs } from "pinia";
import { useMemberStore } from "@/stores/member";

import "@/assets/css/header.css";

const memberStore = useMemberStore();

const { userLogout } = memberStore;
const { isLogin, isValidToken, isAlert, userInfo } = storeToRefs(memberStore);

const router = useRouter();

function logout() {
  userLogout();
  console.log("로그아웃", isLogin.value, isAlert.value);
  router.push({ name: "home" });
}
</script>

<template>
  <header>
    <nav>
      <div class="logo-space">
        <RouterLink :to="{ name: 'home' }">
          <a href="#">
            <img
              class="clickable-img"
              src="/src/assets/img/icon/main-logo.svg"
              alt="main-logo"
              id="main-logo"
            />
          </a>
        </RouterLink>
      </div>
      <div class="menu-spaces">
        <div class="menu-space" v-show="!isLogin">
          <RouterLink
            class="menu-btn light clickable"
            :to="{ name: 'member-login' }"
          >
            Login
          </RouterLink>
          <RouterLink
            class="menu-btn dark clickable"
            :to="{ name: 'member-regist' }"
          >
            Sign up
          </RouterLink>
        </div>
        <div class="menu-space" v-show="isLogin">
          <span v-if="isLogin" class="user-name">
            <b>{{ userInfo.userName }}</b
            >님 환영합니다!
          </span>
          <RouterLink :to="{ name: 'attraction-list' }">
            <a class="menu-icon" href="">
              <img
                class="clickable-img"
                src="@/assets/img/icon/compass.svg"
                alt=""
              />
            </a>
          </RouterLink>
          <RouterLink :to="{ name: 'board' }">
            <a class="menu-icon" href="">
              <img
                class="clickable-img"
                src="@/assets/img/icon/board.svg"
                alt=""
              />
            </a>
          </RouterLink>
          <RouterLink :to="{ name: 'team-list' }">
            <a v-if="!isAlert" class="menu-icon" href="">
              <img
                class="clickable-img"
                src="@/assets/img/icon/team.svg"
                alt=""
              />
            </a>
            <a v-if="isAlert" class="menu-icon" href="">
              <img
                class="clickable-img"
                src="@/assets/img/icon/team-alert.svg"
                alt=""
              />
            </a>
          </RouterLink>
          <a
            class="menu-icon"
            href="#"
            id="navbarDropdown"
            role="button"
            data-bs-toggle="dropdown"
            aria-expanded="false"
          >
            <img
              class="clickable-img"
              src="@/assets/img/icon/user.svg"
              alt=""
              srcset=""
            />
          </a>
          <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
            <li>
              <RouterLink :to="{ name: 'member-info' }">
                <a class="dropdown-item">My Page</a>
              </RouterLink>
            </li>
            <li>
              <a href="#" @click.prevent="logout" class="dropdown-item"
                >Logout</a
              >
            </li>
          </ul>
        </div>
      </div>
    </nav>
  </header>
</template>

<style scoped></style>
