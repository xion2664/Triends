<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import { registArticle } from "@/api/board";

const router = useRouter();

const props = defineProps({ type: String, userId: String });

const article = ref({
  title: "",
  content: "",
  userId: "",
  file: null,
});


const subjectErrMsg = ref("");
const contentErrMsg = ref("");


function onSubmit() {
  if (subjectErrMsg.value) {
    alert(subjectErrMsg.value);
  } else if (contentErrMsg.value) {
    alert(contentErrMsg.value);
  } else {
    props.type === "regist" ? writeArticle() : updateArticle();
  }
}

function writeArticle() {
  const form = new FormData();
  form.append("file", article.value.file);
  form.append("title", article.value.title);
  form.append("content", article.value.content);
  form.append("userId", props.userId);

  registArticle(
    form,
    (response) => {
      console.log(response);
      router.replace({ name: "article-list" });
    },
    (error) => {
      console.log(error);
    }
  );
}

function onFileChange(e) {
  article.value.file = e.target.files[0];
}
</script>

<template>
  
  
<div id="form-main">
  <div id="form-div">
    <form @submit.prevent="onSubmit" class="form" id="form1">
      
      <p class="name">
        <input name="name" type="text" v-model="article.title" class="validate[required,custom[onlyLetter],length[0,100]] feedback-input" placeholder="제목" id="name" />
      </p>
      
      <p class="text">
        <textarea name="text" v-model="article.content" class="validate[required,length[6,300]] feedback-input" id="comment" placeholder="트렌즈와 함께 한 후기를 남겨주세요."></textarea>
      </p>
      
      <div class="file-wrapper">
        <div class="file-upload">
          <input type="file" @change="onFileChange"/>
          <img src="@/assets/img/icon/file-upload.svg" class="fa fa-arrow-up" width="100px" />
          <!-- <i class="fa fa-arrow-up"></i> -->
        </div>
      </div>
      
      <div class="submit" v-if="type === 'regist'">
        <input type="submit" value="SEND" id="button-blue"/>
        <div class="ease"></div>
      </div>
    </form>
  </div>
</div>



  <!-- <form @submit.prevent="onSubmit">
      <input type="text" class="form-control" v-model="article.title" placeholder="제목..." />
      <textarea class="form-control" v-model="article.content" rows="10"></textarea>
    <input type="file" @change="onFileChange" />
      <button type="submit" class="btn btn-outline-primary mb-3" v-if="type === 'regist'">
        글작성
      </button>
  </form> -->
</template>

<style scoped>

#feedback-page{
	text-align:center;
}

#form-div {
  padding-left: 35px;
  padding-right: 35px;
  padding-top: 20px; /* 여기를 조절해 높이를 줄일 수 있습니다 */
  padding-bottom: 25px; /* 여기를 조절해 높이를 줄일 수 있습니다 */
  width: 600px;
  height: 300px;
  float: left;
  left: 50%;
  position: absolute;
  margin-top: 20px;
  margin-left: -260px;
}

.feedback-input {
	color:#3c3c3c;
	font-family: Helvetica, Arial, sans-serif;
  font-weight:500;
	font-size: 18px;
	border-radius: 0;
	line-height: 22px;
	background-color: #fbfbfb;
	padding: 13px 13px 13px 54px;
	width:100%;
	-webkit-box-sizing: border-box;
	-moz-box-sizing: border-box;
	-ms-box-sizing: border-box;
	box-sizing: border-box;
  border: 3px solid rgba(0,0,0,0);
}

.feedback-input:focus{
	background: #fff;
	box-shadow: 0;
	border: 3px solid #b0d9b1;
	color: #b0d9b1;
	outline: none;
  padding: 13px 13px 13px 54px;
}

.focused{
	color:#30aed6;
	border:#30aed6 solid 3px;
}

textarea {
    width: 100%;
    height: 150px;
    line-height: 150%;
    resize:vertical;
}

input:hover, textarea:hover,
input:focus, textarea:focus {
	background-color:white;
}

#button-blue{
	font-family: 'Montserrat', Arial, Helvetica, sans-serif;
	float:left;
	width: 100%;
	border: #fbfbfb solid 4px;
	cursor:pointer;
	background-color: #b0d9b1;
	color:white;
	font-size:24px;
	padding-top:22px;
	padding-bottom:22px;
	-webkit-transition: all 0.3s;
	-moz-transition: all 0.3s;
	transition: all 0.3s;
  margin-top:-4px;
  font-weight:700;
}

#button-blue:hover{
	background-color: rgba(0,0,0,0);
	color: #b0d9b1;
}
	
.submit:hover {
	color: #b0d9b1;
}
	
.ease {
	width: 0px;
	height: 74px;
	background-color: #fbfbfb;
	-webkit-transition: .3s ease;
	-moz-transition: .3s ease;
	-o-transition: .3s ease;
	-ms-transition: .3s ease;
	transition: .3s ease;
}

.submit:hover .ease{
  width:100%;
  background-color:white;
}

@media only screen and (max-width: 580px) {
	#form-div{
		left: 3%;
		margin-right: 3%;
		width: 88%;
		margin-left: 0;
		padding-left: 3%;
		padding-right: 3%;
	}
}

.file-wrapper {
	width: 100%;
	height: 100%;
	display: flex;
	align-items: center;
	justify-content: center;

  }
  .file-wrapper .file-upload {
  margin-top: -10px;
	height: 100px;
	width: 100px;
	border-radius: 100px;
	position: relative;
	display: flex;
	justify-content: center;
	align-items: center;
	border: 4px solid #FFFFFF;
	overflow: hidden;
	background-image: linear-gradient(to bottom, #b0d9b1 50%, #FFFFFF 50%);
	background-size: 100% 200%;
	transition: all 1s;
	color: #FFFFFF;
	font-size: 100px;
	margin-bottom: 15px;
  }
  .file-wrapper .file-upload input[type=file] {
	height: 200px;
	width: 200px;
	position: absolute;
	top: 0;
	left: 0;
	opacity: 0;
	cursor: pointer;
  }
  .file-wrapper .file-upload:hover {
	background-position: 0 -100%;
	color: #b0d9b1;
  }
  .file-wrapper img {
    width: 50px; 
    height: auto; 
}


</style>