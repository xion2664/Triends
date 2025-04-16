package com.ssafy.triends.board.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.triends.board.model.LikeDto;
import com.ssafy.triends.board.model.mapper.LikeMapper;

@Service
public class LikeServiceImpl implements LikeService {

	private LikeMapper likeMapper;

	@Autowired
	public LikeServiceImpl(LikeMapper likeMapper) {
		super();
		this.likeMapper = likeMapper;
	}

	@Override
	@Transactional
	public void insertLike(LikeDto likeDto) throws Exception {
		likeMapper.insertLike(likeDto);
	}

	@Override
	public void deleteLike(LikeDto likeDto) throws Exception {
		likeMapper.deleteLike(likeDto);
	}

	@Override
	public void increaseBoardLike(int boardId) throws Exception {
		likeMapper.increaseBoardLike(boardId);
	}

	@Override
	public void decreaseBoardLike(int boardId) throws Exception {
		likeMapper.decreaseBoardLike(boardId);
	}

	@Override
	public boolean checkLikeStatus(LikeDto likeDto) {
        return likeMapper.checkLikeStatus(likeDto) > 0;
    }


}
