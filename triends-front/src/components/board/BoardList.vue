<script setup>
import { ref, onMounted, computed } from "vue";
import { useRouter } from "vue-router";
import { listArticle, listArticleByLike } from "@/api/board";
import { storeToRefs } from "pinia";
import { useMemberStore } from "@/stores/member";

import BoardListItem from "@/components/board/item/BoardListItem.vue";

const router = useRouter();
const articles = ref([]);
onMounted(() => {
  getArticleList();
});
const memberStore = useMemberStore();

const { userInfo } = storeToRefs(memberStore);

// computed 속성을 사용하여 userInfo가 있으면 userId를, 없으면 null을 반환
const userId = computed(() => {
  return userInfo.value ? userInfo.value.userId : "비회원";
});

const getArticleList = () => {
  listArticle(
    ({ data }) => {
      articles.value = data.articles;
    },
    (error) => {
      console.log(error);
    }
  );
};

const getArticleListByLike = () => {
  listArticleByLike(
    ({ data }) => {
      articles.value = data.articles;
    },
    (error) => {
      console.log(error);
    }
  );
};

const moveWrite = () => {
  router.push({ name: "article-write" });
};

// 삭제 이벤트 핸들러
const handleDeleteArticle = (articleNo) => {
  // 해당하는 글 번호를 가진 아이템을 배열에서 제거
  articles.value = articles.value.filter((article) => article.no !== articleNo);
};

const selectedOption = ref("1"); // 기본 선택 옵션
const sortChange = (event) => {
  selectedOption.value = event.target.value;
  if (selectedOption.value == "1") {
    getArticleList();
  } else {
    getArticleListByLike();
  }
};

const handleLikeClicked = () => {
  if (selectedOption.value == "2") {
    getArticleListByLike();
  }
};
</script>

<template>
  <div class="container">
    <div class="row justify-content-center">
      <div class="col-lg-10">
        <div class="action-buttons">
          <select id="sortBox" @change="sortChange">
            <option value="1">최신순</option>
            <option value="2">인기순</option>
          </select>
          <button id="save" @click="moveWrite">리뷰 작성</button>
        </div>

        <div class="board-list">
          <BoardListItem
            v-for="article in articles"
            :key="article.no"
            :article="article"
            :userId="userId"
            @delete="handleDeleteArticle"
            @like-clicked="handleLikeClicked"
          ></BoardListItem>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.board-list {
  display: flex;
  justify-content: space-between;
  flex-wrap: wrap;
}
#save {
  margin-top: 30px;
  margin-left: 910px;
  width: 150px;
  background-color: #b0d9b1;
  border: 2px solid #b0d9b1;
  border-radius: 25px; /* 더 큰 둥근 모서리 */
  font-size: 16px; /* 글꼴 크기를 더 크게 */
  padding: 10px 20px; /* 상하, 좌우 패딩을 늘림 */
  cursor: pointer;
}

#save:hover {
  box-shadow: inset -5px 5px 15px -5px #b0d9b1, inset 0 -5px 15px -5px #b0d9b1,
    2px 5px 5px darkgreen;
  cursor: pointer;
}

#sortBox {
  margin-top: 30px;
  margin-left: 20px;
  width: 150px;
  border: 2px solid #b0d9b1;
  border-radius: 25px;
  font-size: 16px;
  padding: 10px 20px;
  cursor: pointer;
}

.action-buttons {
  display: flex; /* Flexbox 적용 */
  justify-content: space-between; /* 양 끝 정렬 */
  /* margin-top: 30px; */
  margin-bottom: 20px;
}

#save,
#sortBox {
  margin-left: 20px;
}

#save {
  margin-right: 15px;
}
</style>
