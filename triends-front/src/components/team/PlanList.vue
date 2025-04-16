<template>
  <div class="playground">
    <div v-for="(item, idx) in state.lists" :key="item.id" class="col"
    @drop.prevent="onDrop($event, idx)"
    @dragenter.prevent
    @dragover.prevent>
    <div class="title-and-delete-container">
    <div class="box-title">{{ item.date }}</div>
      <div v-if="item.id > 1" class="delete-box-button">
        <button class="delete-button delete-box-button-position" @click="deleteBox(idx)">
            <svg viewBox="0 0 22 22">
                <circle cx="11" cy="11" r="10"></circle>
            </svg>
            <div class="cross"></div>
        </button>
      </div>
    </div>
      <div v-if="item.id === 1" class="travel-destination">
      <div class="plus-search">


        <!-- <div class="search-space">
          <input
            type="text"
            placeholder="우리 언제 갈까?"
            readonly
          />
          <a @click="toggleNewItemInput">
            <img src="@/assets/img/icon/plus-icon.svg" alt="검색" />
          </a>
        </div> -->
        <div class="search-space">
          <input
            type="text"
            id="customInput"
            readonly
          />
          <span id="placeholderText">우리 <span class="highlight">언제</span> 갈까?</span>
          <a @click="toggleNewItemInput">
            <img src="@/assets/img/icon/plus-icon.svg" alt="검색" />
          </a>
        </div>

        <!-- 조건부 렌더링된 날짜 입력 및 추가 버튼 컨테이너 -->
        <div v-if="isNewItemInputVisible" class="new-item-container">
          <div class="input-button-container">
            <input type="date" v-model="newItem.date" placeholder="날짜 선택" />
            <button id="save" @click="addNewBox">추가</button>
          </div>
        </div>
        <hr class="hr-3">
      <div class="input-space">
        <div class="select-space">
          <select
            name="selectSidoCode"
            id="selectSidoCode"
            v-model="sidoCode"
            @change="getAttractionList()"
            >
            <optgroup label="시/도 선택">
              <option v-for="option in selectSido" :key="option.value" :value="option.value">
                {{ option.text }}
              </option>
            </optgroup>
          </select>
        </div>
        
        

        <div class="search-space">
          <input
            type="text"
            id="customInput1"
            v-model="param.keyword"
            @keyup.enter="searchAttractionList"
            @focus="hidePlaceholder" @blur="showPlaceholder"
          />
          <span id="placeholderText1">우리 <span class="highlight">어디</span> 갈까?</span>
          <a @click="searchAttractionList">
            <img src="@/assets/img/icon/search-icon.svg" alt="검색" />
          </a>
        </div>
      </div>
      </div>
    
      </div>
      <div v-for="(numItem, numIdx) in item.numberList" :key="numIdx" class="box" draggable="true" @dragstart="startDrag($event, numItem, idx)">
      <div class="content-with-delete-button">
        <div class="content-container">
        <img :src="numItem.image" class="box-image">
        <p>{{ numItem.title }}</p>
        </div>
        <button class="delete-button delete-item-button-position" @click="deleteItem(numItem, idx)">
            <svg viewBox="0 0 22 22">
                <circle cx="11" cy="11" r="10"></circle>
            </svg>
            <div class="cross"></div>
        </button>
      
      </div>
    </div>
    </div>
  </div>

  <div class="new-item-input">
  <div class="save-button-container">
    <button id="save" @click="savePlans">Save</button>   
  </div>
  <li v-for="team in plans" :key="team.teamId" class="team-item">
    <h1>{{team}}</h1>
  </li>

</div>
</template>


  
<script setup>
import { reactive, ref, onMounted, watch } from 'vue';
import { planList, placeList, saveNewPlan } from "@/api/team";
import { searchList } from "@/api/attraction";
import { useRoute } from 'vue-router';

const route = useRoute();
console.log(route.params.teamId); // teamId 정보 가져옴

const plans = ref([]);

const sidoCode = ref(1);
watch(sidoCode, (newVal) => {
  param.value.sido = newVal;
});
const param = ref({
  sido: sidoCode.value,
  keyword: "",
});
const selectSido = ref([
  { text: "서울특별시", value: "1" },
  { text: "인천광역시", value: "2" },
  { text: "대전광역시", value: "3" },
  { text: "대구광역시", value: "4" },
  { text: "광주광역시", value: "5" },
  { text: "부산광역시", value: "6" },
  { text: "울산광역시", value: "7" },
  { text: "세종특별자치시", value: "8" },
  { text: "경기도", value: "31" },
  { text: "강원도", value: "32" },
  { text: "충청북도", value: "33" },
  { text: "충청남도", value: "34" },
  { text: "경상북도", value: "35" },
  { text: "경상남도", value: "36" },
  { text: "전라북도", value: "37" },
  { text: "전라남도", value: "38" },
  { text: "제주도", value: "39" },
]);

const searchAttractionList = () => {
  console.log("검색합니다.", param.value);
  const firstItem = state.lists.find(item => item.id === 1);
  if (firstItem) {
    firstItem.numberList = [];
  }
  searchList(
    param.value,
    ({ data }) => {
      console.log(data);
      const transformedData = data.map(item => ({
        title: item.title,
        address: item.addr1,
        image: item.firstImage,
        content: item.contentId,
      }));

      const firstItem = state.lists.find(item => item.id === 1);
      if (firstItem) {
        firstItem.numberList.push(...transformedData);
      }
    },
    (error) => {
      console.log(error);
    }
  );
};


onMounted(() => {
  getPlanList();
});

const getPlanList = () => {
  planList(route.params.teamId,
    ({ data }) => {
      console.log(data);
      data.forEach(plan => {
          addPlanToStateLists(plan);
      });
    },
      (error) => {
      console.log(error)
      }
  );
}

const addPlanToStateLists = (plan) => {
  // YYYYMMDD 형식에서 YYYY, MM, DD 추출
  const year = plan.date.substring(0, 4);
  const month = plan.date.substring(4, 6);
  const day = plan.date.substring(6, 8);

  // YYYY-MM-DD 형식으로 조합
  const formattedDate = `${year}-${month}-${day}`;
  console.log(formattedDate);
  // 빈 numberList로 초기화
  const newBox = {
    id: state.lists.length + 1,
    date: formattedDate,
    numberList: [] 
  };
  // state.lists = state.lists.filter(item => item.id === 1);
  // placeList 함수를 호출하여 관광지 데이터 가져오기
  placeList(plan.planId,
    ({ data }) => {
      newBox.numberList = data.map(place => ({
      content: place.placeId, // placeId (혹은 적절한 속성 이름)를 content로 설정
      title: place.title,      // title 속성 추가
      image: place.image
    }));

      // newBox.numberList = data.map(place => ({ content: place.placeId }));
      state.lists.push(newBox);
      state.lists.sort((a, b) => new Date(a.date) - new Date(b.date));
    },
    (error) => {
      console.error('Error fetching place list:', error);
    }
    );
};

const state = reactive({
  lists: [
    {
      id: 1,
      numberList: []
    },
  ]
});

const addNewBox = () => {
  if (newItem.date) {
    const newBox = {
      id: state.lists.length + 1,
      date: newItem.date,
      numberList: []
    };
    state.lists.push(newBox); // 새 박스를 리스트에 추가
    newItem.date = ''; // 날짜 입력 필드 초기화

    // 리스트를 날짜 기준으로 오름차순 정렬
    state.lists.sort((a, b) => new Date(a.date) - new Date(b.date));
  }
};

const newItem = reactive({
    content: '',
    date: ''
});

const startDrag = (event, item, listIndex) => {
  event.dataTransfer.setData('draggedItem', JSON.stringify(item));
  event.dataTransfer.setData('fromListIndex', listIndex.toString());
};
const onDrop = (event, toListIndex) => {
  const draggedItem = JSON.parse(event.dataTransfer.getData('draggedItem'));
  const fromListIndex = parseInt(event.dataTransfer.getData('fromListIndex'));

  const fromList = state.lists[fromListIndex];
  const itemIndex = fromList.numberList.findIndex(item => item.content === draggedItem.content);
  if (itemIndex > -1) {
    fromList.numberList.splice(itemIndex, 1);
  }

  const toList = state.lists[toListIndex];
  toList.numberList.push(draggedItem);
};
import Swal from 'sweetalert2'
const savePlans = () => {
  const newPlans = state.lists
  .filter(item => item.id >= 2 && !plans.value.some(p => p.date === item.date))
  .map(item => {
    const formattedDate = item.date.replace(/-/g, '');
    const places = item.numberList.map(numItem => ({
      title: numItem.title,  // 제목 추가
      content: numItem.content, // 콘텐츠 ID
      image: numItem.image,
    }));
    console.log(places);
      return {
        date: formattedDate,
        places: places
      };
    });
    saveNewPlan(route.params.teamId, newPlans);
    Swal.fire({
        title: '저장 완료',
        text: '친구와 여행계획을 나눠보아요!',
        icon: 'success',
        confirmButtonColor: '#84B891', // 여기에 원하는 색상 코드를 입력하세요
    })
};





const deleteItem = (item, listIndex) => {
  const list = state.lists[listIndex];
  const itemIndex = list.numberList.findIndex(numItem => numItem.content === item.content);
  if (itemIndex > -1) {
    list.numberList.splice(itemIndex, 1);
  }
};

const deleteBox = (index) => {
  state.lists.splice(index, 1);
};


// 데이터 속성 추가
const isNewItemInputVisible = ref(false);

// 날짜 입력 표시를 토글하는 메서드
const toggleNewItemInput = () => {
  isNewItemInputVisible.value = !isNewItemInputVisible.value;
};

const hidePlaceholder = () => {
  document.getElementById('placeholderText1').style.display = 'none';
};

const showPlaceholder = () => {
  if (!document.getElementById('customInput1').value) {
    document.getElementById('placeholderText1').style.display = 'block';
  }
};



  </script>
  
  <style scoped>
.content-with-delete-button {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.box p {
  margin: 0; /* p 태그의 기본 마진 제거 */
  font-size: 1.1em; /* 폰트 크기 증가 */
  color: darkslategray; /* 폰트 색상 */
}


.box {
  width: 200px; /* 박스 너비 설정 */
  min-height: 300px; /* 박스 최소 높이 설정 */
  margin-top: 10px; /* 박스 간의 상단 마진 */
  padding: 15px; /* 내부 패딩 증가 */
  background-color: #fff; /* 박스 배경색을 흰색으로 변경 */
  border-radius: 10px; /* 박스 테두리 둥글기 증가 */
  box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19); /* 그림자 효과 추가 */
  box-sizing: border-box; /* 패딩을 포함한 박스 크기 계산 */
  display: flex;
  flex-direction: column;
  justify-content: center; /* 세로 방향 중앙 정렬 */
  align-items: center; /* 가로 방향 중앙 정렬 */
  transition: transform 0.3s; /* 부드러운 변환 효과 추가 */
  margin-top: 10px; /* 상단 마진 추가 */
  margin-bottom: 10px; /* 하단 마진 추가 */
}
.box:hover {
  transform: scale(1.05); /* 마우스 오버 시 약간 확대 */
}

.col {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 150px; /* 혹은 적절한 너비 */
  margin-left: 5px;
  background-color: #fff;
  border: 1px solid lightgrey;
  border-radius: 8px;
  max-height: 600px; /* 최대 높이 설정 */
  overflow-y: auto; /* 세로 스크롤바가 필요할 때 나타남 */
  flex: 0 0 300px;
  margin-left: 10px; /* 왼쪽에 마진 추가 */
  margin-right: 10px; /* 오른쪽에 마진 추가 */
}

.playground {
  display: flex;
  flex-direction: row;
  overflow-x: auto;
}



.box-image {
  width: 150px; /* 이미지 너비 고정 */
  height: 150px; /* 이미지 높이 고정 */
  object-fit: cover; /* 이미지를 컨테이너에 맞추고 필요한 경우 잘라냄 */
  object-position: center; /* 이미지의 중앙 부분을 항상 표시 */
  display: block;
  margin: 30px auto 0; /* 상단 여백 30px, 나머지는 자동으로 조정 */
  border-radius: 10px;
  margin-bottom: 10px; /* 상단 마진 추가 */
}


.playground {
  display: flex;
  flex-direction: row;
  overflow-x: auto; /* 요소들이 넘칠 때 가로 스크롤바 생성 */
}

.content-container {
  display: flex; /* Flexbox 레이아웃 사용 */
  flex-direction: column; /* 자식 요소들을 세로 방향으로 정렬 */
  justify-content: center; /* 세로 방향 중앙 정렬 */
  align-items: center; /* 가로 방향 중앙 정렬 */
  width: 100%;
  text-align: center; /* 텍스트 중앙 정렬 */
}


.box {
  position: relative; /* 상대적 위치 설정 */
  /* 기존 스타일 유지 */
}


 .delete-button {
  position: relative;
  width: 50px;
  height: 50px;
  border-radius: 50%;
  cursor: pointer;
  background: transparent;
  border: none;
  opacity: 0.5;
  filter: gray;
  -webkit-filter: grayscale(100%);
  transition: all 0.3s ease;
}

.delete-button svg {
  display: block;
  width: 100%;
  fill: none;
  height: 100%;
  stroke: #F26175;
  stroke-width: 2;
}
.delete-button svg circle {
  stroke-dasharray: 64;
  transition: all 0.3s ease;
}

.delete-button .cross {
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%) rotate(135deg);
  transition: all 0.3s ease;
}

.delete-button .cross:before, .delete-button .cross:after {
  content: "";
  position: absolute;
  background: #F26175; /* 십자가 색상 */
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%) rotate(0deg); /* 수직 교차로 X 모양 생성 */
  transition: all 0.3s ease;
}

.delete-button .cross:before {
  width: 15px; /* 가로선 길이 */
  height: 3px; /* 가로선 두께 */
}

.delete-button .cross:after {
  width: 3px; /* 세로선 두께 */
  height: 15px; /* 세로선 길이 */
}


.delete-button:hover {
  filter: none;
  -webkit-filter: grayscale(0%);
  opacity: 1;
} 
/* .delete-button {
  position: absolute; 
  top: 10px; 
  right: 10px; 
 } */
 .box-title {
  font-size: 1.4em; /* 폰트 크기를 증가시켜 제목처럼 보이게 함 */
  font-weight: bold; /* 굵은 글씨체로 표시 */
  color: #333; /* 제목의 색상 */
  margin-bottom: 15px; /* 제목 아래에 여백 추가 */
  text-align: center; /* 중앙 정렬 */
  width: 100%; /* 전체 너비 사용 */
}
/* 첫 번째 delete-button 위치 */
/* .delete-box-button-position {
  position: absolute;
  top: 10px;
  right: 10px;
} */


/* 두 번째 delete-button 위치 */
.delete-item-button-position {
  position: absolute;
  top: 2px;
  right: 2px;
}
.title-and-delete-container {
  display: flex;
  align-items: center;
  width: 100%;
  position: relative; /* 상대적 위치 설정 */
}

.box-title {
  font-size: 1.4em;
  font-weight: bold;
  color: #333;
  margin-bottom: 15px;
  margin-top: 20px; /* 상단 여백 추가 */
  text-align: center;
  width: 100%;
}


.delete-box-button {
  position: absolute;
  top: 10px;
  right: 10px;
}
.playground {
  display: flex;
  flex-direction: row;
  overflow-x: auto; /* 요소들이 넘칠 때 가로 스크롤바 생성 */
}

/* col 클래스에 스크롤바 스타일 적용 */
.col {
  /* 기존 스타일 유지 */
  overflow-y: scroll; /* 스크롤바 활성화 */
}

.col::-webkit-scrollbar {
  width: .4em; 
}

.col::-webkit-scrollbar,
.col::-webkit-scrollbar-thumb {
  overflow: visible;
  border-radius: 4px;
}

.col::-webkit-scrollbar-thumb {
  background: rgba(0,0,0,.2); 
}

/* 스크롤바 숨김 효과를 위한 커버 스타일 */
.col-cover {
  position: absolute;
  background: #fff;
  height: 100%;  
  top: 0;
  right: 0;
  width: .4em;
  transition: all .5s;
  opacity: 1;
}

/* 마우스 오버하지 않았을 때 스크롤바 숨김 */
.col::-webkit-scrollbar-thumb {
  background-color: transparent; /* 투명한 스크롤바 */
}

/* 마우스 오버 시 스크롤바 표시 */
.col:hover::-webkit-scrollbar-thumb {
  background-color: rgba(0, 0, 0, 0.2); /* 스크롤바 색상 설정 */
}

/* 가로 스크롤바가 있는 요소에 대한 기본 스타일 */
.playground {
  display: flex;
  flex-direction: row;
  overflow-x: auto; /* 가로 스크롤바 활성화 */
}

/* 웹킷 브라우저용 스크롤바 스타일 */
.playground::-webkit-scrollbar {
  height: .4em; /* 스크롤바 높이 설정 */
}

/* 기본 상태에서 스크롤바 숨김 */
.playground::-webkit-scrollbar-thumb {
  background-color: transparent; /* 투명한 스크롤바 */
}

/* 마우스 오버 시 스크롤바 표시 */
.playground:hover::-webkit-scrollbar-thumb {
  background-color: rgba(0, 0, 0, 0.2); /* 스크롤바 색상 설정 */
}
.search-space {
  position:relative;
  background-color: white;
  border: solid 1px;
  border-color: var(--gray);
  border-radius: 100px;
  padding: 5px 10px;
  display: flex;
  width: 270px;
  height: 40px;
  margin-top: 3px;
  flex-direction: row;
}
.search-space input {
  border: none;
}
.search-space input:focus {
  outline: none;
}
.search-space img {
  width: 20px;
  margin: 5px;
}



.input-space {
    display: flex;
    flex-direction: column;
}

.search-space input {
    width: 100%; /* 검색창의 너비 조정 */
    padding: 5px; /* 검색창의 패딩 조정 */
}

/* 기타 필요한 스타일 추가 */

.input-space {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
}

.save-button-container {
  position: fixed; /* 고정 위치 */
  bottom: 210px; /* 아래에서 20px */
  right: 60px; /* 오른쪽에서 20px */
}



#save {
  background-color: #b0d9b1;
  border: 2px solid #b0d9b1;
  border-radius: 25px; /* 더 큰 둥근 모서리 */
  font-size: 16px; /* 글꼴 크기를 더 크게 */
  padding: 10px 20px; /* 상하, 좌우 패딩을 늘림 */
  cursor: pointer;
}

#save:hover {
  box-shadow: inset -5px 5px 15px -5px #b0d9b1, inset 0 -5px 15px -5px #b0d9b1, 2px 5px 5px darkgreen;
  cursor: pointer;
}


input[type="date"] {
  background-color: #fafafa; /* 배경색 */
  border: 1px solid #ddd; /* 테두리 */
  border-radius: 30px; /* 테두리 둥글기 */
  padding: 8px 20px; /* 패딩 */
  font-size: 16px; /* 글꼴 크기 */
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); /* 그림자 */
  color: #333; /* 글꼴 색상 */
}

/* 포커스 시 스타일 */
input[type="date"]:focus {
  border-color: #a4c639; /* 포커스 시 테두리 색상 */
  box-shadow: 0 0 8px rgba(164, 198, 57, 0.5); /* 포커스 시 그림자 */
}

.input-button-container {
  display: flex; /* Flexbox 레이아웃 적용 */
  gap: 25px; /* 요소들 사이의 간격 */
}

hr.hr-3 {
  border: 0;
  height: 0;
  border-top: 1px solid #8c8c8c;
}
/* 
.select-space {
  width: 300px;
  margin-right: 100px;
  margin-top: 5px;
}
.select-space, .search-space {
    margin-bottom: 10px;
} */

.select-space {
  display: flex;
  flex-direction: row;
}
.select-space select {
  width: 175px;
  margin-right: 90px;
  border: solid 1px;
  border-color: var(--gray);
  border-radius: 100px;
  padding: 5px 10px;
  display: flex;
  flex-direction: row;
  height: 40px;
  background-color: white;
}
select:focus {
  outline: none;
}


.search-space {
    margin-bottom: 8px;
}
.select-space{
    margin-bottom: 7px;
}
#placeholderText {
  position: absolute;
  left: 15px; /* 입력 필드 내부의 여백 조정 */
  top: 7px;
  color: darkgray; /* 일반 텍스트 색상 */
  pointer-events: none; /* 텍스트 위에서의 클릭 방지 */
}
#placeholderText1 {
  position: absolute;
  left: 15px; /* 입력 필드 내부의 여백 조정 */
  top: 7px;
  color: darkgray; /* 일반 텍스트 색상 */
  pointer-events: none; /* 텍스트 위에서의 클릭 방지 */
}
.highlight {
  color: darkgreen; /* 강조 텍스트 색상 */
  font-weight: bold;
}
  </style>
  