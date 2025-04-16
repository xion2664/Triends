<template>
  <div class="content">
    <div class="content__container">
      <!-- <span class="content__container__text"></span> -->
      <ul class="content__container__list">
        <li class="content__container__list__item">Hello Triends!</li>
        <li class="content__container__list__item">
          회원정보를 간단하게 수정할 수 있어요!
        </li>
        <li class="content__container__list__item">
          옆의 내 정보들을 클릭해보세요!
        </li>
        <li class="content__container__list__item">
          수정하기 버튼을 눌러보세요!
        </li>
        <!-- Add more list items as needed -->
      </ul>
    </div>
  </div>
</template>

<script>
export default {
  // Your component logic here
};
</script>

<style scoped>
.content {
  /* position: relative;
  top: 50%;
  left: 25%; */
  transform: translate(-50%, -50%);
  overflow: hidden;
  font-size: 40px;
  line-height: 40px;
  color: var(--main-black);
  width: 700px;
}

.content__container {
  font-weight: 600;
  /* overflow: hidden; */
  height: 40px;
  width: 700px;
  /* padding: 0 40px; */
}

.content__container:before {
  content: "[";
  left: 0;
}

.content__container:after {
  content: "]";
  /* position: absolute; */
  right: 0;
}

.content__container::after,
.content__container::before {
  /* position: absolute;
  top: 0; */
  color: #b0d9b1;
  font-size: 38px;
  line-height: 40px;
  /* animation-name: opacity;
  animation-duration: 2s;
  animation-iteration-count: infinite; */
}

.content__container__list {
  margin-top: 0;
  padding-left: 0px;
  text-align: center;
  list-style: none;
  animation-name: change;
  animation-duration: 10s;
  animation-iteration-count: infinite;
}

.content__container__list__item {
  line-height: 40px;
  margin: 0;
}

@keyframes opacity {
  0%,
  100% {
    opacity: 0;
  }
  50% {
    opacity: 1;
  }
}
@keyframes change {
  0%,
  12.66%,
  100% {
    transform: translate3d(0, 0, 0);
  }
  16.66%,
  29.32% {
    transform: translate3d(0, -25%, 0);
  }
  33.32%,
  45.98% {
    transform: translate3d(0, -50%, 0);
  }
  49.98%,
  62.64% {
    transform: translate3d(0, -75%, 0);
  }
  66.64%,
  79.3% {
    transform: translate3d(0, -50%, 0);
  }
  83.3%,
  95.96% {
    transform: translate3d(0, -25%, 0);
  }
}
</style>
