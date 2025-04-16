package com.ssafy.triends.team.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.triends.team.model.Plan;
import com.ssafy.triends.team.model.PlanPlace;

@Mapper
public interface PlanMapper {
//	void insertPlan(Plan plan) throws Exception;
	List<Plan> listPlan(int teamId) throws Exception;
//	void insertPlanPlace(PlanPlace planPlace) throws Exception;
	List<PlanPlace> listPlanPlace(int planId) throws Exception;
	
    void deleteByTeamId(int teamId) throws Exception;
    void insertPlan(Plan plan) throws Exception;
    void insertPlanPlace(PlanPlace planPlace) throws Exception;
}
