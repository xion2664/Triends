package com.ssafy.triends.board.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.triends.board.model.BoardDto;
import com.ssafy.triends.board.model.LikeDto;

@Mapper
public interface LikeMapper {
	void insertLike(LikeDto likeDto) throws SQLException;
	void deleteLike(LikeDto likeDto) throws SQLException;
	void increaseBoardLike(int boardId) throws SQLException;
	void decreaseBoardLike(int boardId) throws SQLException;
	int checkLikeStatus(LikeDto likeDto);
}
