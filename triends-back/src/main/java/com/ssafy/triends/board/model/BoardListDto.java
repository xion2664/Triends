package com.ssafy.triends.board.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardListDto {
	private List<BoardDto> articles;
}
