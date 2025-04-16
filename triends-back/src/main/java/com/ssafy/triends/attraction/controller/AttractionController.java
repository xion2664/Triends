package com.ssafy.triends.attraction.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.triends.attraction.model.Attraction;
import com.ssafy.triends.attraction.model.service.AttractionService;

@CrossOrigin("*")
@RestController
@RequestMapping("/attraction")
public class AttractionController {
	private final AttractionService attractionService;

	public AttractionController(AttractionService attractionService) {
		super();
		this.attractionService = attractionService;
	}

	@GetMapping("{contentId}")
	public ResponseEntity<Attraction> detailAttraciton(@PathVariable int contentId) {
		return ResponseEntity.ok().body(attractionService.detailAttraciton(contentId));
	}

	@GetMapping("{sidoCode}/{contentTypeId}")
	public ResponseEntity<List<Attraction>> listAttraction(@PathVariable int sidoCode,
			@PathVariable int contentTypeId) {
		Attraction attraction = new Attraction();
		attraction.setSidoCode(sidoCode);
		attraction.setContentTypeId(contentTypeId);
		return ResponseEntity.ok().body(attractionService.listAttraction(attraction));
	}

	@GetMapping
	public ResponseEntity<?> searchAttraction(@RequestParam Map<String, String> map) {
		System.out.println("searchAttraction 실행");
		System.out.println(map);
		System.out.println(attractionService.searchAttraction(map));
		return ResponseEntity.ok(attractionService.searchAttraction(map));
	}
}