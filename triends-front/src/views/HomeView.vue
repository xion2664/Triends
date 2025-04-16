<script setup>
import { ref, onMounted, onUnmounted } from "vue";
import { useRouter } from "vue-router";
import "@/assets/css/main.css";
import "https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js";
const router = useRouter();
// 상태 정의
const isScrolledBeyond50 = ref(false);
const isResponsiveMenuVisible = ref(false);
const activeProduct = ref(1);

// 헤더 이미지 소스 변경을 위한 상태
const headerImageSrc = ref("/src/assets/img/icon/main-logo.svg");
const headerImageSrcBlack = ref("https://i.ibb.co/0jM3ZvH/reverse-black.png");

// 스크롤 이벤트 핸들러
const handleScroll = () => {
  if (window.scrollY > 50) {
    isScrolledBeyond50.value = true;
    headerImageSrc.value = headerImageSrcBlack.value;
  } else {
    isScrolledBeyond50.value = false;
    headerImageSrc.value = "/src/assets/img/icon/main-logo.svg";
  }
};

// 반응형 메뉴 토글
const toggleResponsiveMenu = () => {
  isResponsiveMenuVisible.value = !isResponsiveMenuVisible.value;
};

// 제품 섹션 표시 변경
const showProduct = (productNumber) => {
  activeProduct.value = productNumber;
};

// 스크롤 이벤트 리스너 추가
onMounted(() => {
  window.addEventListener("scroll", handleScroll);
});

// 컴포넌트 언마운트 시 스크롤 이벤트 리스너 제거
onUnmounted(() => {
  window.removeEventListener("scroll", handleScroll);
});

const goToMemberRegist = () => {
  router.push({ name: "member-regist" });
};
</script>

<template>
  <div>
    <!-- Hero Image, text & button -->

    <div id="hero-header">
      <div id="hero-content">
        <h1 id="hero-title">즐거운 동행의 시작</h1>

        <p id="hero-text">
          트렌즈와 함께 여행지를 탐색하고, 계획을 세워보아요!
        </p>

        <a href="#presentation-container"
          ><button id="hero-button">지금 시작하기</button></a
        >
      </div>
    </div>

    <div id="main">
      <!-- Presentation Section -->

      <div id="presentation-container">
        <div id="features-container">
          <div id="features-description">
            <h1>트렌즈와 <span id="red-text">함께</span> 여행을 시작하세요!</h1>
          </div>
          <!-- Features Section Description -->

          <!-- Features of the Services -->
          <div id="service-features">
            <a href="#section-content-container" class="place">
              <div class="placeImg">
                <div class="cover">
                  <div class="title-cover">
                    <div class="title">Triends란?</div>
                    <p class="locate">
                      Triends에서 친구와 <br />
                      실시간으로 소통하며 <br />
                      여행 코스를 계획하는 법을 알아봐요.
                    </p>
                  </div>
                </div>
                <img src="@/assets/img/slide/about-triends-banner.png" alt="" />
              </div>
            </a>
            <a href="" class="place">
              <RouterLink :to="{ name: 'attraction-list' }">
                <div class="placeImg">
                  <div class="cover">
                    <div class="title-cover">
                      <div class="title">여행지 탐색</div>
                      <p class="locate">
                        지도를 보며 원하는 여행지를 <br />
                        지역별, 카테고리별로 탐색해봐요.
                      </p>
                    </div>
                  </div>
                  <img src="@/assets/img/slide/place-banner.png" alt="" />
                </div>
              </RouterLink>
            </a>
            <a href="detail.html" class="place">
              <RouterLink :to="{ name: 'board' }">
                <div class="placeImg">
                  <div class="cover">
                    <div class="title-cover">
                      <div class="title">여행 후기</div>
                      <p class="locate">
                        여행을 다녀왔다면, <br />
                        즐거웠던 여행 기록을 게시판에 남겨<br />
                        사람들과 추억과 정보를 나눠봐요.
                      </p>
                    </div>
                  </div>
                  <img
                    src="@/assets/img/slide/trip-journey-banner.png"
                    alt=""
                  />
                </div>
              </RouterLink>
            </a>
          </div>
        </div>
        <!-- /"Features Section" -->

        <div class="last-img">
          <a href="" class="flexflex">
            <img src="@/assets/img/slide/main-1.jpg" alt="" />
          </a>
        </div>

        <!-- Product Content Section -->
        <div id="section-content-container">
          <!-- Content Container 1 -->
          <div id="product-container-one" class="product-content">
            <div id="content-image-container">
              <img
                src="@/assets/img/slide/step1.gif"
                id="content-image"
                class="image1"
                alt=""
              />
            </div>

            <div id="content-title-text">
              <h2>1. 팀 생성하기</h2>

              <p id="centered-product-description">
                팀 작업을 시작하기 전에,<br />
                우선 팀을 생성해봅시다!<br /><br />
              </p>

              <div class="product-features-list" id="first-feature-list">
                <div id="product-feature-image">
                  <img
                    src="https://i.ibb.co/LzczvS3/correct.png"
                    alt="check-sign"
                    id="correct-icon"
                  />
                </div>

                <div id="inside-features-title-text">
                  <h4>팀 스페이스 접속</h4>

                  <p>
                    우측 상단의 팀 아이콘을 눌러,<br />
                    나의 팀 스페이스에 들어갑니다.
                  </p>
                </div>
              </div>

              <div class="product-features-list">
                <div id="product-feature-image">
                  <img
                    src="https://i.ibb.co/LzczvS3/correct.png"
                    alt="check-sign"
                    id="correct-icon"
                  />
                </div>

                <div id="inside-features-title-text">
                  <h4>팀 생성</h4>

                  <p>
                    팀 목록 상단에 위치한<br />
                    create new team 버튼을 눌러<br />
                    팀 이름을 입력하고 팀을 생성합니다.
                  </p>
                </div>
              </div>

            </div>
          </div>

          <!-- Content Container 2 -->
          <div id="product-container-two" class="product-content">
            <div id="content-image-container">
              <img
                src="@/assets/img/slide/step2.gif"
                id="content-image"
                class="image1"
                alt=""
              />
            </div>

            <div id="content-title-text">
              <h2>2. 친구 초대하기</h2>

              <p id="centered-product-description">
                같이 여행을 떠날<br />
                친구를 초대해요!<br /><br />
              </p>

              <div class="product-features-list" id="first-feature-list">
                <div id="product-feature-image">
                  <img
                    src="https://i.ibb.co/LzczvS3/correct.png"
                    alt="check-sign"
                    id="correct-icon"
                  />
                </div>

                <div id="inside-features-title-text">
                  <h4>친구 초대 아이콘 클릭</h4>

                  <p>
                    좌측 하단 아이콘을 눌러,<br />
                    친구 초대 창을 엽니다.
                  </p>
                </div>
              </div>

              <div class="product-features-list">
                <div id="product-feature-image">
                  <img
                    src="https://i.ibb.co/LzczvS3/correct.png"
                    alt="check-sign"
                    id="correct-icon"
                  />
                </div>

                <div id="inside-features-title-text">
                  <h4>아이디로 친구검색</h4>

                  <p>
                    아이디를 검색해<br />
                    친구를 찾고 초대합니다.<br />
                  </p>
                </div>
              </div>

            </div>
          </div>

          <!-- Content Container 3 -->
          <div id="product-container-three" class="product-content">
            <div id="content-image-container">
              <img
                src="@/assets/img/slide/step3.gif"
                id="content-image"
                class="image1"
                alt=""
              />
            </div>

            <div id="content-title-text">
              <h2>3. 팀 플랜 만들기</h2>

              <p id="centered-product-description">
                팀원끼리 공유할<br />
                여행 계획 입니다.<br /><br />
              </p>

              <div class="product-features-list" id="first-feature-list">
                <div id="product-feature-image">
                  <img
                    src="https://i.ibb.co/LzczvS3/correct.png"
                    alt="check-sign"
                    id="correct-icon"
                  />
                </div>

                <div id="inside-features-title-text">
                  <h4>팀 플랜 페이지</h4>

                  <p>
                    우측 상단의 아이콘을 눌러,<br />
                    나의 팀 플랜 페이지로 들어갑니다.
                  </p>
                </div>
              </div>

            </div>
          </div>

          <!-- Content Container 4 -->
          <div id="product-container-four" class="product-content">
            <div id="content-image-container">
              <img
                src="@/assets/img/slide/step4.gif"
                id="content-image"
                class="image1"
                alt=""
              />
            </div>

            <div id="content-title-text">
              <h2>4. 팀 플랜 편집하기</h2>

              <p id="centered-product-description">
                팀끼리 공유할<br />
                여행 계획을 만들어봅시다!<br /><br />
              </p>

              <div class="product-features-list" id="first-feature-list">
                <div id="product-feature-image">
                  <img
                    src="https://i.ibb.co/LzczvS3/correct.png"
                    alt="check-sign"
                    id="correct-icon"
                  />
                </div>

                <div id="inside-features-title-text">
                  <h4>날짜 선택</h4>

                  <p>
                    여행을 가고 싶은<br />
                    날짜를 선택합니다.
                  </p>
                </div>
              </div>

              <div class="product-features-list">
                <div id="product-feature-image">
                  <img
                    src="https://i.ibb.co/LzczvS3/correct.png"
                    alt="check-sign"
                    id="correct-icon"
                  />
                </div>

                <div id="inside-features-title-text">
                  <h4>관광지 선택</h4>

                  <p>
                    그 날에 가고싶은<br />
                    여행지를 검색해서<br />
                    일자에 추가합니다.
                  </p>
                </div>
              </div>

              <div class="product-features-list">
                <div id="product-feature-image">
                  <img
                    src="https://i.ibb.co/LzczvS3/correct.png"
                    alt="check-sign"
                    id="correct-icon"
                  />
                </div>

                <div id="inside-features-title-text">
                  <h4>저장하기</h4>

                  <p>
                    내가 만든 여행 계획을<br />
                    저장해서 팀원들과 공유합니다.
                  </p>
                </div>
              </div>
            </div>
          </div>

          <!-- Content Container 5 -->
          <div id="product-container-five" class="product-content">
            <div id="content-image-container">
              <img
                src="@/assets/img/slide/step5.gif"
                id="content-image"
                class="image1"
                alt=""
              />
            </div>

            <div id="content-title-text">
              <h2>5. 팀원과 만나기</h2>

              <p id="centered-product-description">
                팀원들과 실시간으로 소통해요!<br />
                <br /><br />
              </p>

              <div class="product-features-list" id="first-feature-list">
                <div id="product-feature-image">
                  <img
                    src="https://i.ibb.co/LzczvS3/correct.png"
                    alt="check-sign"
                    id="correct-icon"
                  />
                </div>

                <div id="inside-features-title-text">
                  <h4>대화방 이동</h4>

                  <p>
                    팀명 옆 말풍선을 클릭해<br />
                    팀 전용 대화방으로 이동합니다.
                  </p>
                </div>
              </div>

              <div class="product-features-list">
                <div id="product-feature-image">
                  <img
                    src="https://i.ibb.co/LzczvS3/correct.png"
                    alt="check-sign"
                    id="correct-icon"
                  />
                </div>

                <div id="inside-features-title-text">
                  <h4>소통 시작</h4>

                  <p>
                    내 이름을 입력하고<br />
                    대화방에 접속해서 팀원과 소통합니다.<br />
                  </p>
                </div>
              </div>
              
            </div>
          </div>
        </div>
      </div>

      <!-- Newsletter Section -->
      <div id="newsletter-container">
        <div id="newsletter-title-description">
          <h1>이제 여행을 떠나볼까요?</h1>

          <!-- <hr id="title-text-separator" /> -->
        </div>

        <div id="final-container">
          <label id="final-text"> 트렌즈와 함께 즐거운 시간을 보내봐요!</label>
          <!-- <input type="email" placeholder="Enter your e-mail" id="email" /> -->
          <button id="final-button" class="clickable" @click="goToMemberRegist">
            회원가입
          </button>
        </div>
      </div>
    </div>
    <div class="last-img">
      <a href="#hero-header" class="flexflex">
        <img src="@/assets/img/slide/main-1.jpg" alt="" />
        <img src="@/assets/img/slide/main-2.jpg" alt="" />
      </a>
    </div>
  </div>
</template>

<style scoped></style>
