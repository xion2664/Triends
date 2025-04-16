package com.ssafy.triends.team.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ssafy.triends.team.model.Plan;
import com.ssafy.triends.team.model.PlanPlace;

@Service
public interface PlanService {
	
//	void insertPlan(Plan plan) throws Exception;
	List<Plan> listPlan(int teamId) throws Exception;
//	void insertPlanPlace(PlanPlace planPlace) throws Exception;
	List<PlanPlace> listPlanPlace(int planId) throws Exception;
	public void savePlans(int teamId, List<Map<String, Object>> plansData) throws Exception;
	
}
