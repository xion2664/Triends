package com.ssafy.triends.attraction.model;

import lombok.Data;

@Data
public class Attraction {
	private int contentId,contentTypeId,readcount,sidoCode,gugunCode;
	private String title,addr1,addr2,zipcode,tel,firstImage,firstImage2,mlevel,homepage,overview,telname;
	private double latitude,longitude;
}
/*
시도코드 값				타입 아이디 값
1	서울				12	그냥 여행지? 잘모르겠다 산도있고 해변도있고 절도있고
2	인천				14	문화원, 박물관, 예술회관 등
3	대전				15	축제
4	대구				25	컨셉 여행지? (ex: 삼척의 영화 촬영 명소를 찾아서)
5	광주				28	스키장, 클럽, 카지노, 저수지 등 유흥시설
6	부산				32	호텔, 리조트 등 숙박시설
7	울산				38	시장
8	세종특별자치시		39	식당
31	경기도
32	강원도
33	충청북도
34	충청남도
35	경상북도
36	경상남도
37	전라북도
38	전라남도
39	제주도
*/