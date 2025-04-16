package com.ssafy.triends.team.controller;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.triends.team.model.Plan;
import com.ssafy.triends.team.model.PlanPlace;
import com.ssafy.triends.team.model.service.PlanService;

@CrossOrigin("*")
@RestController
@RequestMapping("/plan")
public class PlanController {
	private final PlanService planService;
	public PlanController(PlanService planService) {
		super();
		this.planService = planService;
	}
	
	@PostMapping("save/{teamId}")
    public ResponseEntity<?> save(@PathVariable int teamId, @RequestBody List<Map<String, Object>> plans) {
		System.out.println("save!");
		System.out.println(plans);
		try {
			planService.savePlans(teamId, plans);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return ResponseEntity.ok().build();
    }
	
//	@PostMapping("insertPlan")
//	public ResponseEntity<?> insertPlan(@RequestBody Plan plan) {
//		try {
//			planService.insertPlan(plan);
//			return new ResponseEntity<Void>(HttpStatus.CREATED);
//		} catch (Exception e) {
//			return exceptionHandling(e);
//		}
//	}
//	
//	@PostMapping("insertPlanPlace")
//	public ResponseEntity<?> insertPlanPlace(@RequestBody PlanPlace planPlace) {
//		try {
//			planService.insertPlanPlace(planPlace);
//			return new ResponseEntity<Void>(HttpStatus.CREATED);
//		} catch (Exception e) {
//			return exceptionHandling(e);
//		}
//	}
	
	@GetMapping("team/{teamId}")
	public ResponseEntity<?> listPlan(@PathVariable int teamId) {
		try {
			List<Plan> planList = planService.listPlan(teamId);
			System.out.println("호출됨");
			System.out.println(planList);
			HttpHeaders header = new HttpHeaders();
			header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
			return ResponseEntity.ok().headers(header).body(planList);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@GetMapping("place/{planId}")
	public ResponseEntity<?> listPlanPlace(@PathVariable int planId) {
		try {
			List<PlanPlace> planPlaceList = planService.listPlanPlace(planId);
			System.out.println("planPlaceList");
			System.out.println(planPlaceList);
			HttpHeaders header = new HttpHeaders();
			header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
			return ResponseEntity.ok().headers(header).body(planPlaceList);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
