import { localAxios } from "../utils/http-common";

const local = localAxios();

function attractionList(sidoCode, contentTypeId, success, fail) {
  local
    .get(`attraction/${sidoCode}/${contentTypeId}`)
    .then(success)
    .catch(fail);
}

function searchList(param, success, fail) {
  local.get(`attraction/`, { params: param }).then(success).catch(fail);
}

function attractionDetail(contentId, success, fail) {
  local.get(`attraction/${contentId}`).then(success).catch(fail);
}

export { attractionList, searchList, attractionDetail };
