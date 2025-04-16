package com.ssafy.triends.board.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.triends.board.model.BoardDto;

@Mapper
public interface BoardMapper {

	void writeArticle(BoardDto boardDto) throws SQLException;

	List<BoardDto> listArticle() throws SQLException;
	
	List<BoardDto> listArticleByLike() throws SQLException;
	
	void deleteArticle(int articleNo) throws SQLException;
	
}
