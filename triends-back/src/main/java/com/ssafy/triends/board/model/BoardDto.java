package com.ssafy.triends.board.model;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardDto {
	private int no;
	private String title;
	private String content;
	private String userId;
	private String userName;
	private int like;
	private String registerTime;
	private MultipartFile file;
	private String imagePath;
}
