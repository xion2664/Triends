package com.ssafy.triends.attraction.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ssafy.triends.attraction.model.Attraction;

@Mapper
public interface AttractionMapper {

	List<Attraction> selectList(Attraction attraction);

	List<Attraction> searchList(Map<String, Object> param);

	Attraction select(int contentId);

}
