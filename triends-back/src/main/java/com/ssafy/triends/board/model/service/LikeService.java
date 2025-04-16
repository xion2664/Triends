package com.ssafy.triends.board.model.service;

import com.ssafy.triends.board.model.LikeDto;

public interface LikeService {

	void insertLike(LikeDto likeDto) throws Exception;
	void deleteLike(LikeDto likeDto) throws Exception;
	void increaseBoardLike(int boardId) throws Exception;
	void decreaseBoardLike(int boardId) throws Exception;
	boolean checkLikeStatus(LikeDto likeDto) throws Exception;

}
