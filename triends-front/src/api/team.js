import { localAxios } from "../utils/http-common";

const local = localAxios();

async function myTeamList(userId, success, fail) {
  await local.get(`team/${userId}`).then(success).catch(fail);
}

//속한 팀+ 팀원
async function teamInviteList(teamId, success, fail) {
  await local.get(`/team/invite/${teamId}`).then(success).catch(fail);
}

async function teamInvite(teamId, fromUserId, toUserId, success, fail) {
  await local.get(`/team/invite/${teamId}/${fromUserId}/${toUserId}`).then(success).catch(fail);
}

async function deleteInvite(teamId, userId, success, fail) {
  await local.delete(`/team/invite/${teamId}/${userId}`).then(success).catch(fail);
}

//사용자가 받은 초대 목록 출력
async function myInviteList(userId, success, fail) {
  await local.get(`/team/inviteList/${userId}`).then(success).catch(fail);
}

//팀에 사용자를 추가
async function registMember(teamId, userId, success, fail) {
  await local.get(`/team/registMember/${teamId}/${userId}`).then(success).catch(fail);
}

async function registTeam(teamName, userId, success, fail) {
  await local.get(`/team/regist/${teamName}/${userId}`).then(success).catch(fail);
}

async function leaveTeam(teamId, userId, success, fail) {
  await local.delete(`/team/leave/${teamId}/${userId}`).then(success).catch(fail);
}

function planList(teamId, success, fail) {
  local.get(`plan/team/${teamId}`).then(success).catch(fail);
}

function placeList(planId, success, fail) {
  local.get(`plan/place/${planId}`).then(success).catch(fail);
}

function saveNewPlan(teamId, plans, success, fail) {
  local.post(`plan/save/${teamId}`, plans).then(success).catch(fail);
}
export {
  myTeamList,
  teamInviteList,
  teamInvite,
  registTeam,
  leaveTeam,
  myInviteList,
  registMember,
  deleteInvite,
  planList,
  placeList,
  saveNewPlan,
};
