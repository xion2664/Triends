package com.ssafy.triends.board.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.triends.board.model.BoardDto;
import com.ssafy.triends.board.model.BoardListDto;
import com.ssafy.triends.board.model.mapper.BoardMapper;

@Service
public class BoardServiceImpl implements BoardService {

	private BoardMapper boardMapper;

	@Autowired
	public BoardServiceImpl(BoardMapper boardMapper) {
		super();
		this.boardMapper = boardMapper;
	}

	@Override
	@Transactional
	public void writeArticle(BoardDto boardDto) throws Exception {
		boardMapper.writeArticle(boardDto);
	}

	@Override
	public BoardListDto listArticle() throws Exception {
		List<BoardDto> list = boardMapper.listArticle();
		BoardListDto boardListDto = new BoardListDto();
		boardListDto.setArticles(list);
		return boardListDto;
	}
	
	@Override
    public void deleteArticle(int articleNo) throws Exception {
        boardMapper.deleteArticle(articleNo);
    }

	@Override
	public BoardListDto listArticleByLike() throws Exception {
		List<BoardDto> list = boardMapper.listArticleByLike();
		BoardListDto boardListDto = new BoardListDto();
		boardListDto.setArticles(list);
		return boardListDto;
	}

}
