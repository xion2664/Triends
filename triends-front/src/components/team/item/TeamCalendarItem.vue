<script setup>
import { reactive, onMounted, onUnmounted } from "vue";

import "@/assets/css/team/teamCalendar.css";

// CalendarApp 데이터와 메소드를 Vue 방식으로 정의
const calendarData = reactive({
  days: [
    "Sunday",
    "Monday",
    "Tuesday",
    "Wednesday",
    "Thursday",
    "Friday",
    "Saturday",
  ],
  months: [
    "Jan",
    "Feb",
    "Mar",
    "Apr",
    "May",
    "Jun",
    "Jul",
    "Aug",
    "Sep",
    "Oct",
    "Nov",
    "Dec",
  ],
  // 기타 필요한 데이터 정의
});

// CalendarApp 메소드를 Vue 방식으로 정의
function showView(date) {
  // 이 메소드의 로직을 Vue 방식으로 작성
}

// 라이프사이클 훅을 사용하여 초기화 및 정리 작업
onMounted(() => {
  // 필요한 초기화 작업
  showView(new Date());
});

onUnmounted(() => {
  // 필요한 정리 작업
});

// 기타 필요한 메소드 정의
</script>

<template>
  <div class="calendar--day-view" id="day-view">
    <span class="day-view-exit" id="day-view-exit">&times;</span>
    <span class="day-view-date" id="day-view-date">MAY 29 2016</span>
    <div class="day-view-content">
      <div class="day-highlight">
        You
        <span class="day-events" id="day-events">had no events for today</span>.
        &nbsp;
        <span
          tabindex="0"
          onkeyup="if(event.keyCode != 13) return; this.click();"
          class="day-events-link"
          id="add-event"
          data-date
          >Add a new event?</span
        >
      </div>
      <div class="day-add-event" id="add-day-event-box" data-active="false">
        <div class="row">
          <div class="half">
            <label class="add-event-label">
              Name of event
              <input
                type="text"
                class="add-event-edit add-event-edit--long"
                placeholder="New event"
                id="input-add-event-name"
              />
            </label>
          </div>
          <div class="qtr">
            <label class="add-event-label">
              Start Time
              <input
                type="text"
                class="add-event-edit"
                placeholder="8:15"
                id="input-add-event-start-time"
                data-options="1,2,3,4,5,6,7,8,9,10,11,12"
                data-format="datetime"
              />
              <input
                type="text"
                class="add-event-edit"
                placeholder="am"
                id="input-add-event-start-ampm"
                data-options="a,p,am,pm"
              />
            </label>
          </div>
          <div class="qtr">
            <label class="add-event-label">
              End Time
              <input
                type="text"
                class="add-event-edit"
                placeholder="9"
                id="input-add-event-end-time"
                data-options="1,2,3,4,5,6,7,8,9,10,11,12"
                data-format="datetime"
              />
              <input
                type="text"
                class="add-event-edit"
                placeholder="am"
                id="input-add-event-end-ampm"
                data-options="a,p,am,pm"
              />
            </label>
          </div>
          <div class="half">
            <a
              onkeyup="if(event.keyCode != 13) return; this.click();"
              tabindex="0"
              id="add-event-save"
              class="event-btn--save event-btn"
              >save</a
            >
            <a
              tabindex="0"
              id="add-event-cancel"
              class="event-btn--cancel event-btn"
              >cancel</a
            >
          </div>
        </div>
      </div>
      <div id="day-events-list" class="day-events-list"></div>
      <div class="day-inspiration-quote" id="inspirational-quote">
        Every child is an artist. The problem is how to remain an artist once he
        grows up. –Pablo Picasso
      </div>
    </div>
  </div>
  <div class="calendar--view" id="calendar-view">
    <div class="cview__month">
      <span class="cview__month-last" id="calendar-month-last">Apr</span>
      <span class="cview__month-current" id="calendar-month">May</span>
      <span class="cview__month-next" id="calendar-month-next">Jun</span>
    </div>
    <div class="cview__header">Sun</div>
    <div class="cview__header">Mon</div>
    <div class="cview__header">Tue</div>
    <div class="cview__header">Wed</div>
    <div class="cview__header">Thu</div>
    <div class="cview__header">Fri</div>
    <div class="cview__header">Sat</div>
    <div class="calendar--view" id="dates"></div>
  </div>
</template>

<style scoped></style>
