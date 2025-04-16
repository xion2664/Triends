<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import { myInviteList, registMember, deleteInvite } from "@/api/team";
import { storeToRefs } from "pinia";
import { useMemberStore } from "@/stores/member";

import "@/assets/css/team/teamInvite.css";

const router = useRouter();
const memberStore = useMemberStore();

const props = defineProps({
  userId: String,
  inviteList: Array,
});

const emit = defineEmits(["acceptInvite", "updateInviteList"]);

function accept(teamId) {
  event.stopPropagation();
  registMember(
    teamId,
    props.userId,
    () => {
      emit("acceptInvite");
      emit("updateInviteList");
    },
    console.error()
  );
}

function reject(teamId) {
  event.stopPropagation();
  deleteInvite(
    teamId,
    props.userId,
    () => emit("updateInviteList"),
    console.error()
  );
}
</script>

<template>
  <!-- <ul style="display: inline-block">
    <li class="nav-item dropdown" style="list-style: none"> -->
  <a
    href="#"
    id="navbarDropdown"
    role="button"
    data-bs-toggle="dropdown"
    aria-expanded="false"
  >
    <img
      v-show="!inviteList.length"
      class="invite-icon clickable-img"
      src="@/assets/img/icon/message-solid.svg"
      alt=""
    />
    <img
      v-show="inviteList.length"
      class="invite-icon clickable-img"
      src="@/assets/img/icon/open-message.svg"
      alt=""
      id="smaller-img"
    />
  </a>
  <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
    <li v-for="invite in inviteList" :key="invite.teamId">
      <b>{{ invite.fromUserName }}</b> [{{ invite.fromUserId }}] 님이
      <b>{{ invite.teamName }}</b> 팀에 초대하였습니다.
      <br />
      <a
        href=""
        @click.prevent="
          accept(invite.teamId, invite.toUserId, invite.fromUserId)
        "
        >수락</a
      >
      /
      <a
        href=""
        @click.prevent="
          reject(invite.teamId, invite.toUserId, invite.fromUserId)
        "
        >거절</a
      >
      <hr class="dropdown-divider" />
    </li>
    <li v-if="inviteList.length == 0">받은 초대가 없습니다.</li>
  </ul>
  <!-- </li>
  </ul> -->
</template>

<style scoped></style>
