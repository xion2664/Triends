<script setup>
import { defineComponent, onMounted, ref, watch } from "vue";
import { attractionList, searchList } from "@/api/attraction";
import { useRouter } from "vue-router";
import { NaverMap, NaverMarker, NaverInfoWindow } from "vue3-naver-maps";

// import heart from "@/components/attraction/item/heart.vue";

import "@/assets/css/attraction/map.css";
import "@/assets/css/attraction/list.css";

const router = useRouter();

onMounted(() => {
  getAttractionList();
});

const sidoCode = ref(1);
const contentTypeId = ref(12);
const attractions = ref([]);
const currentAttraction = ref({});

watch(sidoCode, (newVal) => {
  param.value.sido = newVal;
});

const selectSido = ref([
  { text: "서울특별시", value: "1", lat: "37.5666", lng: "126.9784" },
  { text: "인천광역시", value: "2", lat: "37.456", lng: "126.7052" },
  { text: "대전광역시", value: "3", lat: "36.35", lng: "127.3847" },
  { text: "대구광역시", value: "4", lat: "35.8498", lng: "128.5502" },
  { text: "광주광역시", value: "5", lat: "35.1596", lng: "126.8508" },
  { text: "부산광역시", value: "6", lat: "35.1796", lng: "129.0749" },
  { text: "울산광역시", value: "7", lat: "35.5397", lng: "129.3115" },
  { text: "세종특별자치시", value: "8", lat: "36.4801", lng: "127.289" },
  { text: "경기도", value: "31", lat: "37.2893", lng: "127.0535" },
  { text: "강원도", value: "32", lat: "37.8854", lng: "127.7298" },
  { text: "충청북도", value: "33", lat: "36.6358", lng: "127.4914" },
  { text: "충청남도", value: "34", lat: "36.6588", lng: "126.6735" },
  { text: "경상북도", value: "35", lat: "36.576", lng: "128.5059" },
  { text: "경상남도", value: "36", lat: "35.2383", lng: "128.6924" },
  { text: "전라북도", value: "37", lat: "35.8204", lng: "127.1087" },
  { text: "전라남도", value: "38", lat: "34.8162", lng: "126.4629" },
  { text: "제주도", value: "39", lat: "33.4995", lng: "126.5311" },
]);
const selectContentType = ref([
  { text: "관광지", value: "12", src: "/src/assets/img/icon/관광지.svg" },
  { text: "문화시설", value: "14", src: "/src/assets/img/icon/문화시설.svg" },
  { text: "축제", value: "15", src: "/src/assets/img/icon/축제.svg" },
  { text: "레저", value: "28", src: "/src/assets/img/icon/레저.svg" },
  { text: "숙박", value: "32", src: "/src/assets/img/icon/숙박.svg" },
  { text: "상점", value: "38", src: "/src/assets/img/icon/쇼핑.svg" },
  { text: "식당", value: "39", src: "/src/assets/img/icon/음식.svg" },
]);

function getAttractionList() {
  attractionList(
    sidoCode.value,
    contentTypeId.value,
    ({ data }) => {
      // console.log();
      attractions.value = data;
    },
    (error) => console.error()
  );
}

watch(sidoCode, (newVal) => {
  const selectedSido = selectSido.value.find((sido) => sido.value === newVal);
  if (selectedSido && map.value) {
    map.value.setCenter(
      new naver.maps.LatLng(selectedSido.lat, selectedSido.lng)
    );
  }
});

const param = ref({
  sido: sidoCode.value,
  keyword: "",
});

const searchAttractionList = () => {
  console.log("검색합니다.", param.value);
  searchList(
    param.value,
    ({ data }) => {
      attractions.value = data;
    },
    (error) => {
      console.log(error);
    }
  );
};

function moveDetail(contentId) {
  router.push({ name: "attraction-detail", params: { contentId } });
}

const map = ref();
const mapOptions = {
  latitude: 37.5666, // 지도 중앙 위도
  longitude: 126.9784, // 지도 중앙 경도
  zoom: 15,
  zoomControl: false,
  zoomControlOptions: { position: "TOP_RIGHT" },
};
const initLayers = [
  "BACKGROUND",
  "BACKGROUND_DETAIL",
  "POI_KOREAN",
  "TRANSIT",
  "ENGLISH",
];

const onLoadMap = (mapObject) => {
  map.value = mapObject;
};

const infoWindow = ref();
const isOpen = ref(false);

const onLoadMarker = (index, markerObject) => {
  attractions.value[index].marker = markerObject;
};
const onShowInfoWindow = (index) => {
  isOpen.value = !isOpen.value;
  currentAttraction.value = attractions.value[index];
};
const onLoadInfoWindow = (infoWindowObject) => {
  infoWindow.value = infoWindowObject;
};

const infoWindowOptions = ref({
  maxWidth: 250,
  backgroundColor: "transparent",
  borderColor: "#2db400",
  borderWidth: 0,
  disableAnchor: true, //화살표 미사용
  // anchorSize: new naver.maps.Size(15, 15),  //아래 화살표 (깊이,넓이)
  anchorSkew: true, //화살표 기울이기
  anchorColor: "#000",
  // pixelOffset: new naver.maps.Point(100, 0) //정보 창의 꼬리에서 정보 창이 위치한 지점까지의 오프셋
});

function clickImageButton(value) {
  contentTypeId.value = value;
  getAttractionList();
}
</script>

<template>
  <div class="attraction-container">
    <div class="map">
      <naver-map
        style="width: 100%; height: 700px"
        :mapOptions="mapOptions"
        :initLayers="initLayers"
        @onLoad="onLoadMap($event)"
      >
        <div
          v-for="(attraction, index) in attractions"
          :key="attraction.contentId"
        >
          <naver-marker
            :latitude="attraction.latitude"
            :longitude="attraction.longitude"
            @onLoad="onLoadMarker(index, $event)"
            @mouseover="onShowInfoWindow(index)"
            @mouseout="isOpen = false"
            @click="moveDetail(attraction.contentId)"
          >
          </naver-marker>
        </div>
        <naver-info-window
          class="naver-info-window"
          :marker="currentAttraction.marker"
          :open="isOpen"
          :options="infoWindowOptions"
          @onLoad="onLoadInfoWindow($event)"
        >
          <div class="infoWindow-content">
            <img :src="currentAttraction.firstImage" alt="" />
            <h4>{{ currentAttraction.title }}</h4>
            <p class="addr">{{ currentAttraction.addr1 }}</p>
            <p
              style="
                overflow: hidden;
                text-overflow: ellipsis;
                white-space: nowrap;
              "
            >
              {{ currentAttraction.overview }}
            </p>
          </div>
        </naver-info-window>
      </naver-map>
    </div>

    <div class="condition-space">
      <h2>여행지 탐색</h2>
      <div class="input-space">
        <div class="select-space">
          <select
            name="selectSidoCode"
            id="selectSidoCode"
            v-model="sidoCode"
            @change="getAttractionList()"
          >
            <optgroup label="시/도 선택">
              <option
                v-for="option in selectSido"
                :key="option.value"
                :value="option.value"
              >
                {{ option.text }}
              </option>
            </optgroup>
          </select>
        </div>
        <div class="search-space">
          <input
            type="text"
            v-model="param.keyword"
            placeholder="키워드를 입력하세요!"
            @keyup.enter="searchAttractionList"
          />
          <a @click="searchAttractionList">
            <img src="@/assets/img/icon/search-icon.svg" alt="검색" />
          </a>
        </div>
      </div>
    </div>
    <div class="list-container">
      <div class="select-content-type-space">
        <a v-for="option in selectContentType" :key="option.value" href="#">
          <img
            class="content-type-image clickable-img"
            :src="option.src"
            :alt="option.text"
            @click="clickImageButton(option.value)"
          />
        </a>
      </div>
      <div class="list">
        <div
          class="list-item"
          v-for="attraction in attractions"
          :key="attraction.contentId"
        >
          <a class="detail">
            <div
              class="text-space"
              @click.prevent="moveDetail(attraction.contentId)"
            >
              <h5>{{ attraction.title }}</h5>
              <p class="addr">{{ attraction.addr1 }}</p>
              <p
                class="overview"
                style="
                  white-space: nowrap;
                  overflow: hidden;
                  text-overflow: ellipsis;
                "
              >
                {{ attraction.overview }}
              </p>
            </div>
            <!-- <heart></heart> -->
            <img
              id="attraction-img"
              :src="attraction.firstImage"
              @click.prevent="moveDetail(attraction.contentId)"
              alt="사진"
            />
          </a>
          <hr />
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
<!-- 시도코드 값				   타입 아이디 값
1	  서울				     12	관광지
2	  인천				     14	문화시설
3	  대전				     15	축제
4	  대구				     25	x
5	  광주				     28	레저
6	  부산				     32 숙박
7	  울산				     38	상점
8	  세종특별자치시		  39 식당
31	경기도
32	강원도
33	충청북도
34	충청남도
35	경상북도
36	경상남도
37	전라북도
38	전라남도
39	제주도 -->
