package com.ssafy.triends.attraction.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ssafy.triends.attraction.model.Attraction;
import com.ssafy.triends.attraction.model.mapper.AttractionMapper;

@Service
public class AttractionServiceImpl implements AttractionService {
	private final AttractionMapper attractionMapper;

	public AttractionServiceImpl(AttractionMapper attractionMapper) {
		super();
		this.attractionMapper = attractionMapper;
	}

	@Override
	public List<Attraction> listAttraction(Attraction attraction) {
		return attractionMapper.selectList(attraction);
	}

	@Override
	public List<Attraction> searchAttraction(Map<String, String> map) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("keyword", map.get("keyword") == null ? "" : map.get("keyword"));
		int sido = Integer.parseInt(map.get("sido"));
		param.put("sido", sido);

		System.out.println("~~~~");
		System.out.println(param);
		List<Attraction> list = attractionMapper.searchList(param);
		return list;
	}

	@Override
	public Attraction detailAttraciton(int contentId) {
		return attractionMapper.select(contentId);
	}
}
