package com.ssafy.triends.attraction.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.triends.attraction.model.Attraction;

public interface AttractionService {

	List<Attraction> listAttraction(Attraction attraction);

	List<Attraction> searchAttraction(Map<String, String> map);

	Attraction detailAttraciton(int contentId);
}
