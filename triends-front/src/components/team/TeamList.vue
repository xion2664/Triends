<script setup>
import { ref, onMounted } from "vue";
import { myInviteList, myTeamList, registTeam } from "@/api/team";
import { useRouter, useRoute } from "vue-router";
import { storeToRefs } from "pinia";
import { useMemberStore } from "@/stores/member";

import TeamInviteItem from "./item/TeamInviteItem.vue";
import TeamMemberItem from "./item/TeamMemberItem.vue";

import "@/assets/css/team/teamList.css";

const router = useRouter();
const route = useRoute();

const memberStore = useMemberStore();
const { checkToken } = memberStore;
const { isValidToken, userInfo, isAlert } = storeToRefs(memberStore);

const userId = ref();

const teamName = ref();

const teamList = ref([]);

const inviteList = ref([]);

function getTeamList() {
  myTeamList(
    userId.value,
    ({ data }) => {
      teamList.value = data;
    },
    console.error()
  );
}

function getInviteList() {
  myInviteList(
    userId.value,
    ({ data }) => {
      inviteList.value = data;
      isAlert.value = inviteList.value.length;
    },
    console.error()
  );
}

//팀 추가
function makeTeam() {
  if (teamName.value != null) {
    registTeam(
      teamName.value,
      userId.value,
      //success
      () => getTeamList(),
      //error
      console.error()
    );
  }
}

onMounted(() => {
  checkToken(sessionStorage.getItem("accessToken"));
  if (!isValidToken.value) {
    alert("토큰이 만료되었습니다.");
    router.replace({ name: "member-login" });
  } else {
    userId.value = userInfo.value.userId;
    getTeamList();
    getInviteList();
  }
});

const isCreating = ref(false);
function nowCreating() {
  isCreating.value = true;
}
function doneCreating() {
  isCreating.value = false;
  makeTeam();
}
</script>

<template>
  <div class="container">
    <div class="team-header">
      <h1>TeamSpace</h1>
      <TeamInviteItem
        :userId="userId"
        :inviteList="inviteList"
        @acceptInvite="getTeamList"
        @updateInviteList="getInviteList"
      />
    </div>

    <div class="team-list-space">
      <div class="create-team before-create" v-if="!isCreating">
        <a href="#" @click="nowCreating">
          <img src="@/assets/img/icon/plus-solid.svg" alt="" />
          <span class="create-txt">create new team</span>
        </a>
      </div>
      <div class="create-team do-create" v-if="isCreating">
        <input
          type="text"
          placeholder="팀 이름"
          v-model="teamName"
          @keyup.enter="doneCreating"
        />
        <div class="create-btn">
          <a href="#" @click.prevent="doneCreating">
            <img
              id="create"
              class="clickable-img"
              src="@/assets/img/icon/create-white.svg"
              alt=""
            />
          </a>
        </div>
      </div>
    </div>
    <ul class="team-list">
      <TeamMemberItem
        v-for="team in teamList"
        :key="team.teamId"
        :team="team"
      />
    </ul>
  </div>
</template>

<style scoped></style>
