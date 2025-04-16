<script setup>
import { ref, onMounted, reactive } from "vue";
import { useRouter, useRoute } from "vue-router";
import { teamInviteList, teamInvite, leaveTeam } from "@/api/team";
import { storeToRefs } from "pinia";

import { useMemberStore } from "@/stores/member";

import TeamInviteSearch from "./TeamInviteSearchItem.vue";
import RandomCatProfile from "@/components/member/item/randomCatProfile.vue";

import "@/assets/css/team/teamMember.css";

const router = useRouter();
const route = useRoute();

const memberStore = useMemberStore();
const { userInfo } = storeToRefs(memberStore);

const props = defineProps({
  team: Object,
});

function movePlan(teamId) {
  router.push({ name: "plan-list", params: { teamId: teamId } });
}

const showTemplate = ref(true);
const modalState = ref(false);

const openModal = (teamId) => {
  teamInviteList(
    teamId,
    ({ data }) => {
      inviteList.value = data;
    },
    console.error()
  );
  modalState.value = true;
};

const closeModal = () => {
  modalState.value = false;
};

const inviteList = ref([]);

import Swal from "sweetalert2";
function leave(teamId) {
  // leaveTeam(
  //   teamId,
  //   userInfo.value.userId,
  //   () => {
  //     showTemplate.value = false;
  //   },
  //   console.error()
  // );

  Swal.fire({
    title: "정말로 나가시겠습니까?",
    text: "트렌즈와의 여행계획을 더이상 볼 수 없어요",
    icon: "warning",
    showCancelButton: true,
    confirmButtonColor: "#B3B3B3",
    cancelButtonColor: "#84B891",
    confirmButtonText: "팀 나가기",
    cancelButtonText: "인연 유지하기",
  }).then((result) => {
    if (result.isConfirmed) {
      leaveTeam(
        teamId,
        userInfo.value.userId,
        () => {
          showTemplate.value = false;
          Swal.fire({
            title: "나가기 완료",
            text: "팀을 나갔습니다.",
            icon: "success",
            confirmButtonColor: "#84B891", // 여기에 원하는 색상 코드를 입력하세요
          });
        },
        (error) => {
          console.error();
        }
      );
    }
  });
}

function moveChat(teamId) {
  // console.log(props.team.teamList);
  router.push({
    name: "team-chat",
    params: {
      teamId: teamId,
      teamName: JSON.stringify(props.team.teamName),
      teamList: JSON.stringify(props.team.teamList),
    },
  });
}
</script>

<template>
  <div v-if="showTemplate">
    <div class="team">
      <div class="team-top">
        <div class="team-info">
          <h2 class="team-name">{{ props.team.teamName }} ·</h2>
          <a id="to-jitsi" href="#" @click="moveChat(props.team.teamId)">
            <img id="chat" src="@/assets/img/icon/chat.svg" alt="영통" />
          </a>
        </div>
        <div class="team-plan">
          <a @click="movePlan(props.team.teamId)" href="#">
            <img
              id="go"
              class="clickable-img"
              src="@/assets/img/icon/go.svg"
              alt=""
            />
          </a>
        </div>
      </div>
      <hr />
      <div class="team-calendar">
        <div
          v-for="teamMember in props.team.teamList"
          :key="teamMember.userId"
          class="member-item"
        >
          <div class="member-info">
            <random-cat-profile />
            <span id="member-name">{{ teamMember.userName }}</span>
            <span id="member-id">[{{ teamMember.userId }}]</span>
          </div>
          <span id="member-email">{{ teamMember.email }}</span>
        </div>
        <hr />
      </div>
      <div class="team-bottom">
        <a
          class="invite-btn"
          @click.prevent="() => openModal(props.team.teamId)"
          href=""
        >
          <img src="@/assets/img/icon/user-plus.svg" alt="" />
        </a>
        <a
          class="invite-btn"
          @click.prevent="() => openModal(props.team.teamId)"
          href=""
        >
          <img id="users" src="@/assets/img/icon/users.svg" alt="팀" />
        </a>
        <a class="leave-btn" @click.prevent="leave(props.team.teamId)" href="">
          <img src="@/assets/img/icon/out.svg" alt="" />
        </a>
      </div>
    </div>

    <div v-show="modalState" class="modal-overlay" @click="closeModal">
      <div v-show="modalState" class="modal" @click.stop>
        <TeamInviteSearch :inviteList="inviteList" :team="team" />
        <a @click="closeModal" href="#" class="close-btn">
          <img src="@/assets/img/icon/close.svg" alt="" />
        </a>
      </div>
    </div>
  </div>

  <!-- <li
    v-for="teamMember in props.team.teamList"
    :key="teamMember.userId"
    class="member-item"
  >
    <span class="member-info"
      >{{ teamMember.userName }}<br />{{ teamMember.userId }}</span
    >
  </li>
  <li class="member-item">
    <a href="#" class="invite-link" @click="() => openModal(props.team.teamId)"
      >초대하기</a
    >
  </li> -->
</template>

<style scoped></style>
