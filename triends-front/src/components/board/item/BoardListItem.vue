<script setup>
import { ref, reactive, onMounted } from 'vue';
import { insertLike, deleteLike, increaseBoardLike, decreaseBoardLike, checkLikeStatus, deleteArticle } from "@/api/board";
const props = defineProps({
  userId: String,
  article: Object
});
const likeCount = ref(props.article.like);

const isLiked = ref(false);

const handleClick = () => {
  like.userId = props.userId;
  like.boardId = props.article.no;
  if (!isLiked.value) {
    likeCount.value++;
    isLiked.value = true;

    insertLike(like, (response) => {
    },
      (error) => {
      console.log(error)
      }
    );
    increaseBoardLike(like, (response) => {
      emit('like-clicked');
    },
      (error) => {
      console.log(error)
      }
    );

    
  } else {
    deleteLike(like, (response) => {
    },
      (error) => {
      console.log(error)
      }
    );
    decreaseBoardLike(like, (response) => {
      emit('like-clicked');
    },
      (error) => {
      console.log(error)
      }
    );
    likeCount.value--;
    isLiked.value = false;
  }
};

onMounted(() => {
  checkIfLiked();
});

function checkIfLiked() {
  const likeInfo = {
    userId: props.userId,
    boardId: props.article.no
  };

  checkLikeStatus(likeInfo, (response) => {
    isLiked.value = response.data;
  }, (error) => {
    console.error(error);
  });
}

const like = reactive({
  userId: "",
  boardId: 0,
});

import { defineEmits } from 'vue';

import Swal from 'sweetalert2'
const confirmDelete = (articleNo) => {
  Swal.fire({
    title: '정말로 삭제하시겠습니까?',
    text: "트렌즈의 여행기록이 삭제됩니다.",
    icon: 'warning',
    showCancelButton: true,
    confirmButtonColor: '#B3B3B3',
    cancelButtonColor: '#84B891',
    confirmButtonText: '여행기록 삭제하기',
    cancelButtonText: '추억 보존하기'
  }).then((result) => {
    if (result.isConfirmed) {
      deleteArticle(articleNo, (response) => {
        emit('delete', articleNo);
        Swal.fire({
          title: '삭제 완료',
          text: '여행기록을 삭제하였습니다.',
          icon: 'success',
          confirmButtonColor: '#84B891', // 여기에 원하는 색상 코드를 입력하세요
        });
      }, (error) => {
        console.log(error);
      });
    }
  });
};

const emit = defineEmits(['delete', 'like-clicked']);


</script>

<template>
  
  <div class="box-container">
    <div id="box">
      <img
        class="img"
        :src="article.imagePath"
      />
      
      <button class="like-btn" :class="{ liked: isLiked }" @click.prevent="handleClick()">
        <span class="content">
          <img class="icon fa" v-if="isLiked" src="@/assets/img/icon/full-heart.svg" aria-hidden="true" style="height: 30px; width: 30px;">
          <img class="icon far" v-if="!isLiked" src="@/assets/img/icon/empty-heart.svg" aria-hidden="true" style="height: 30px; width: 30px;">
          <span class="text like">Like<span class="text d" v-if="isLiked">d</span></span>
          <span class="text number">{{ likeCount }}</span>
        </span>
      </button>

      <!-- <a href="" class="paw-button" :class="{ liked: isLiked }" @click.prevent="handleClick()">  
        <span>{{ likeCount }}</span>
      </a> -->
  <!-- Symbols and other HTML parts remain the same -->
  <!-- Symbols -->

      <h1 class="heading">{{ article.title }}</h1>
      <div class="data">
        <span class="user-id">{{ article.userName }}</span>
        <span class="date">{{ article.registerTime }}</span>
      </div>
      <p class="texts">
        {{ article.content }}
      </p>
      <button v-if="props.userId === props.article.userId" type="button" class="btn btn-danger" @click="confirmDelete(article.no)">
        <img src="@/assets/img/icon/trash.svg" alt="Delete Icon" style="height: 15px; width: 15px; margin-left:12px; padding:0;">
        <span>Delete</span>  
      </button>

    </div>
  </div>
</template>

<style scoped>
.box-container {
  display: flex;
  justify-content: space-around;
  flex-wrap: wrap;
}

#box {
  position: relative; /* 버튼의 위치를 이 박스에 대해 상대적으로 설정 */
  margin: 10px;
  width: 300px;
  border-radius: 8px;
  overflow: hidden;
  margin: 20px;
  transition: all 0.3s cubic-bezier(0.42, 0, 0.58, 1);
  
}

#box:hover {
  box-shadow: 0 14px 28px rgba(0, 0, 0, 0.25), 0 10px 10px rgba(0, 0, 0, 0.22);
  transform: translateY(-10px);
}

#box * {
  padding: 10px;
}

#box .img {
  display: block;
  width: 100%;
  padding: 0;
}

#box .heading {
  font-size: 28px;
}

#box .data {
  display: flex;
  flex-direction: column;
  font-size: 12px;
  color: #666;
}

#box .data span {
  padding: 0;
}

#box .data .date {
  margin-bottom: 2px;
}

#box .data .user-id {
  font-size: 16px;
  color: #000;
  font-weight: 600;
}

#box .texts {
  font-size: 14px;
  line-height: 18px;
}


html {
  box-sizing: border-box;
  -webkit-font-smoothing: antialiased;
}

* {
  box-sizing: inherit;
}
*:before, *:after {
  box-sizing: inherit;
}

body {
  min-height: 100vh;
  display: flex;
  font-family: "Roboto", Arial;
  justify-content: center;
  align-items: center;
  background: #fff;
}
body .dribbble {
  position: fixed;
  display: block;
  right: 20px;
  bottom: 20px;
}
body .dribbble img {
  display: block;
  height: 28px;
}
#box {
  position: relative; /* 박스를 위치 지정 컨텍스트로 만듭니다. */
}

#box .img {
  display: block;
  width: 100%;
  position: relative; /* 이미지를 상대 위치로 설정 */
  z-index: 1; /* 이미지의 z-index를 낮춥니다. */
}

.paw-button {
  position: absolute; /* 버튼을 절대 위치하도록 설정합니다. */
  top: 10px; /* 버튼의 상단 위치 조정 */
  right: 10px; /* 버튼의 오른쪽 위치 조정 */
  z-index: 2; /* 버튼이 이미지 위에 오도록 z-index를 높입니다. */
}

.btn.btn-danger {
  display: flex; /* Flexbox를 사용하여 내부 요소를 정렬합니다 */
  align-items: center; /* 세로 방향으로 중앙 정렬 */
  justify-content: center;
  position: absolute;
  height: 35px;
  width: 80px;
  bottom: 5px;
  right: 5px; 
}
.like-btn {
  position: absolute;
  top: 10px;
  right: 10px;
  z-index: 3;
  background-color: white;
  width: 85px; /* 필요에 따라 조정 */
  height: 30px; /* 필요에 따라 조정 */
  display: flex;
  align-items: center; /* 세로 중앙 정렬 */
  justify-content: center; /* 가로 중앙 정렬 */
  border-radius: 5px; /* 모서리 둥글게 */
}

.like-btn .content {
  display: flex;
  align-items: center;
  padding: 0px;

}

.like-btn .icon {
  height: 20px; /* 아이콘 크기 조정 */
  width: 20px; /* 아이콘 크기 조정 */
  margin-right: 5px; /* 아이콘과 텍스트 사이의 여백 */
  margin-right: 0; /* 아이콘과 텍스트 사이의 여백 제거 */
}

.like-btn .text {
  font-size: 14px; /* 텍스트 크기 조정 */
  margin: 0; /* 텍스트의 마진 제거 */
}

.like-btn .number {
  margin-left: 0; /* 숫자와 텍스트 사이의 여백 제거 */
}


#box .like-btn .icon {
  padding: 4px; /* .like-btn 내부 요소의 패딩을 제거 */
}
#box .like-btn .number {
  margin-left: 4px;
}
#box .like-btn .text {
  padding: 0px;
}
.like-btn {
  border: none; /* 기본 테두리 제거 */
  outline: none; /* 포커스 아웃라인 제거 */
}
.like-btn {
  /* 기존 스타일 */
  transition: box-shadow 0.2s ease;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2); /* 기본 그림자 */
}

.like-btn:active {
  box-shadow: 0 1px 2px rgba(0, 0, 0, 0.2); /* 클릭 시 그림자 */
  transform: translateY(1px); /* 클릭 시 버튼을 아래로 이동 */
  border-color: #a4c639; /* 포커스 시 테두리 색상 */
  box-shadow: 0 0 8px rgba(164, 198, 57, 0.5); /* 포커스 시 그림자 */
}

#box .img {
  display: block;
  width: 100%;
  padding: 0;
  min-height: 200px; /* 최소 높이 설정 */
  max-height: 200px; /* 최대 높이 설정 */
}


</style>