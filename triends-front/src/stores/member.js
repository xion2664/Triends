import { ref } from "vue";
import { defineStore } from "pinia";
import { jwtDecode } from "jwt-decode";

import { userConfirm, findById, check } from "@/api/member";
import { httpStatusCode } from "@/utils/http-status";

export const useMemberStore = defineStore(
  "memberStore",
  () => {
    const isLogin = ref(false);
    const isLoginError = ref(false);
    const userInfo = ref(null);
    const isValidToken = ref(false);
    const isAlert = ref(false);

    const userLogin = async (loginInfo) => {
      await userConfirm(
        loginInfo,
        async (response) => {
          if (response.status === httpStatusCode.CREATE) {
            let { data } = response;

            let accessToken = data["access-token"];
            isLogin.value = true;
            isLoginError.value = false;
            isValidToken.value = true;
            sessionStorage.setItem("accessToken", accessToken);
            await getUserInfo(accessToken);
          } else {
            console.log("로그인 실패했다");
            isLogin.value = false;
            isLoginError.value = true;
            isValidToken.value = false;
          }
        },
        (error) => {
          console.error(error);
        }
      );
    };

    const getUserInfo = async (token) => {
      let decodeToken = jwtDecode(token);
      console.log("2. decodeToken", decodeToken);
      console.log("2. decodeToken.userId", decodeToken.userId);
      await findById(
        decodeToken.userId,
        (response) => {
          if (response.status === httpStatusCode.OK) {
            userInfo.value = response.data.userInfo;
            console.log("3. getUserInfo data >> ", response.data);
          } else {
            console.log("유저 정보 없음!!!!");
          }
        },
        async (error) => {
          console.error(
            "getUserInfo() error code [토큰 만료되어 사용 불가능.] ::: ",
            error.response.status
          );
          userLogout();
        }
      );
    };

    const checkToken = (token) => {
      check(
        (response) => {
          console.log("checkToken", response.status);
        },
        async (error) => {
          userLogout();
        }
      );
    };

    const userLogout = () => {
      isLogin.value = false;
      userInfo.value = null;
      isValidToken.value = false;
      isAlert.value = false;
    };

    return {
      isAlert,
      isLogin,
      isLoginError,
      userInfo,
      isValidToken,
      userLogin,
      getUserInfo,
      checkToken,
      userLogout,
    };
  },
  {
    persist: {
      storage: sessionStorage,
    },
  }
);
