package com.ssafy.triends.board.model.service;

import com.ssafy.triends.board.model.BoardDto;
import com.ssafy.triends.board.model.BoardListDto;

public interface BoardService {

	void writeArticle(BoardDto boardDto) throws Exception;
	BoardListDto listArticle() throws Exception;
	BoardListDto listArticleByLike() throws Exception;
    void deleteArticle(int articleNo) throws Exception;
}
