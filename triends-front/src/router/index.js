import { createRouter, createWebHistory } from "vue-router";
import HomeView from "../views/HomeView.vue";
import { useMemberStore } from "@/stores/member";
import { storeToRefs } from "pinia";
import { myInviteList } from "../api/team";

const requireAuth = () => (to, from, next) => {
  const memberStore = useMemberStore();
  const { checkToken } = memberStore;
  const { isValidToken } = storeToRefs(memberStore);

  checkToken(sessionStorage.getItem("accessToken"));
  if (!isValidToken.value) {
    alert("로그인이 필요합니다.");
    router.replace({ name: "member-login" });
  } else {
    return next();
  }
};

const headerAlert = () => (to, from, next) => {
  const memberStore = useMemberStore();
  const { checkToken } = memberStore;
  const { userInfo, isAlert } = storeToRefs(memberStore);

  checkToken(sessionStorage.getItem("accessToken"));
  if (userInfo.value) {
    myInviteList(
      userInfo.value.userId,
      ({ data }) => {
        console.log(data);
        if (data.length != 0) {
          isAlert.value = true;
        } else {
          isAlert.value = false;
        }
      },
      (error) => {
        console.log(error);
      }
    );
    console.log("isAlert", isAlert.value);
  }
  return next();
};

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "home",
      component: HomeView,
      beforeEnter: headerAlert(),
    },
    // {
    //   path: "/about",
    //   name: "about",
    //   component: () => import("../views/AboutView.vue"),
    //   beforeEnter: headerAlert(),
    // },
    {
      path: "/board",
      name: "board",
      component: () => import("../views/BoardView.vue"),
      beforeEnter: headerAlert(),
      redirect: { name: "article-list" },
      children: [
        {
          path: "list",
          name: "article-list",
          component: () => import("@/components/board/BoardList.vue"),
        },
        {
          path: "write",
          name: "article-write",
          component: () => import("@/components/board/BoardWrite.vue"),
          beforeEnter: requireAuth(),
        },
      ],
    },
    {
      path: "/team",
      name: "team",
      component: () => import("../views/TeamView.vue"),
      beforeEnter: headerAlert(),
      children: [
        {
          path: "list",
          name: "team-list",
          component: () => import("@/components/team/teamList.vue"),
        },
        {
          path: "plan/:teamId",
          name: "plan-list",
          component: () => import("@/components/team/PlanList.vue"),
        },
        {
          path: "chat/:teamId/:teamName/:teamList",
          name: "team-chat",
          component: () => import("@/components/team/TeamChat.vue"),
        },
      ],
    },
    {
      path: "/attraction",
      name: "attraction",
      component: () => import("../views/AttractionView.vue"),
      beforeEnter: headerAlert(),
      children: [
        {
          path: "list",
          name: "attraction-list",
          component: () => import("@/components/attraction/attractionList.vue"),
        },
        {
          path: "detail/:contentId",
          name: "attraction-detail",
          component: () => import("@/components/attraction/attractionDetail.vue"),
        },
      ],
    },
    {
      path: "/member",
      name: "member",
      component: () => import("../views/MemberView.vue"),
      beforeEnter: headerAlert(),
      children: [
        {
          path: "login",
          name: "member-login",
          component: () => import("@/components/member/memberLogin.vue"),
        },
        {
          path: "info",
          name: "member-info",
          beforeEnter: requireAuth(),
          component: () => import("@/components/member/memberInfo.vue"),
        },
        {
          path: "regist",
          name: "member-regist",
          component: () => import("@/components/member/memberRegist.vue"),
        },
      ],
    },
  ],
});

export default router;
