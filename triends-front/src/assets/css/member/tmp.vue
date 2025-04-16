<script setup>

</script>

<template>
  <div class="container">
    <div class="row">
      <!-- 사진 div (왼쪽) -->
      <div class="col-md-6"></div>
      <!-- 개인정보 div (오른쪽) -->
      <div class="col-md-2">
        <h1 style="margin: 32px 0 32px 70px">ID</h1>
        <h1 style="margin: 32px 0 32px 70px">Name</h1>
        <h1 style="margin: 32px 0 32px 70px">c.Pass</h1>
        <h1 style="margin: 32px 0 32px 70px">Email</h1>
        <h1 style="margin: 32px 0 32px 70px">Phone</h1>
      </div>
      <div class="col-md-4">
        <div class="col">
          <!-- 계획 제목 -->
          <!-- <h1>My Info</h1> -->
          <div class="plan-title">
            <div style="padding: 8px">
              {{ changeUserInfo.userId }}
            </div>
            <!-- <input v-else v-model="changeUserInfo.userId" @blur="stopEditing(0)" placeholder="변경할 아이디" /> -->
          </div>
          <div class="plan-title">
            <div
              class="editable-text"
              v-if="!isEditing[1]"
              @click="startEditing(1)"
            >
              {{ changeUserInfo.userName }}
            </div>
            <input
              v-else
              v-model="changeUserInfo.userName"
              @blur="stopEditing(1)"
              placeholder="변경할 이름"
            />
          </div>
          <div class="plan-title">
            <div
              class="editable-text"
              v-if="!isEditing[2]"
              @click="startEditing(2)"
            >
              <span v-if="!changeUserInfo.userPass">비밀번호 변경</span>
              <span v-else>새 비밀번호: {{ changeUserInfo.userPass }}</span>
            </div>
            <input
              v-else
              v-model="changeUserInfo.userPass"
              @blur="stopEditing(2)"
              placeholder="새 비밀번호"
            />
          </div>
          <div class="plan-title">
            <span>ID</span>
            <div
              class="editable-text"
              v-if="!isEditing[3]"
              @click="startEditing(3)"
            >
              {{ changeUserInfo.email }}
            </div>
            <input
              v-else
              v-model="changeUserInfo.email"
              @blur="stopEditing(3)"
              placeholder="변경할 이메일"
            />
          </div>
          <div class="plan-title">
            <div
              class="editable-text"
              v-if="!isEditing[4]"
              @click="startEditing(4)"
            >
              {{ changeUserInfo.phone }}
            </div>
            <input
              v-else
              v-model="changeUserInfo.phone"
              @blur="stopEditing(4)"
              placeholder="변경할 전화번호"
            />
          </div>
          <button class="modify-button" v-if="changed" @click="modify">
            수정하기
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>

</style>