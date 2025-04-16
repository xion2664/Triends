import { localAxios } from "../utils/http-common";

const local = localAxios();

async function userConfirm(param, success, fail) {
  console.log("param", param);
  await local.post(`/member/login`, param).then(success).catch(fail);
  console.log("userConfirm ok");
}

function memberRegist(param, success, fail) {
  local.post(`/member`, JSON.stringify(param)).then(success).catch(fail);
}

function memberDelete(userid, success, fail) {
  local.delete(`/member/${userid}`).then(success).catch(fail);
}

async function findById(userid, success, fail) {
  local.defaults.headers["Authorization"] = sessionStorage.getItem("accessToken");
  await local.get(`/member/info/${userid}`).then(success).catch(fail);
}

async function check(success,fail){
  local.defaults.headers["Authorization"] = sessionStorage.getItem("accessToken");
  await local.get(`/member/check`).then(success).catch(fail);
}

async function userModify(param,success,fail){
  await local.put(`/member`,JSON.stringify(param)).then(success).catch(fail);
}

export { userConfirm, memberRegist, findById, check, userModify, memberDelete };
