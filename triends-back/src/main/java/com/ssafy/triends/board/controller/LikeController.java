package com.ssafy.triends.board.controller;

import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.triends.board.model.LikeDto;
import com.ssafy.triends.board.model.service.LikeService;

@CrossOrigin(origins = { "*" }, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE} , maxAge = 6000)
@RestController
@RequestMapping("/like")
public class LikeController {
	
	private static final Logger logger = LoggerFactory.getLogger(LikeController.class);

	private LikeService likeService;

	public LikeController(LikeService likeService) {
		super();
		this.likeService = likeService;
	}
	
	@GetMapping("/{userId}/{boardId}")
	public ResponseEntity<?> checkLikeStatus(@PathVariable("userId") String userId, @PathVariable("boardId") int boardId) {
	    try {
	    	LikeDto likeDto = new LikeDto();
	    	likeDto.setBoardId(boardId);
	    	likeDto.setUserId(userId);
	        boolean isLiked = likeService.checkLikeStatus(likeDto);
	        HttpHeaders header = new HttpHeaders();
			header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
			System.out.println(isLiked);
			return ResponseEntity.ok().headers(header).body(isLiked);
//	        return ResponseEntity.ok().body(new LikeStatusResponse(isLiked));
	    } catch (Exception e) {
	        logger.error("Error checking like status", e);
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}

	@PostMapping
	public ResponseEntity<?> insertLike(@RequestBody LikeDto likeDto) {
		System.out.println(likeDto.getUserId() + " " + likeDto.getBoardId());
		try {
			likeService.insertLike(likeDto);
	        return new ResponseEntity<Void>(HttpStatus.CREATED);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@DeleteMapping("{userId}/{boardId}")
	public ResponseEntity<?> deleteLike(@PathVariable String userId, @PathVariable int boardId) {
		System.out.println(userId);
		System.out.println(boardId);
		
		LikeDto likeDto = new LikeDto();
		likeDto.setUserId(userId);
		likeDto.setBoardId(boardId);
		//		System.out.println(likeDto.getUserId() + " " + likeDto.getBoardId());
		try {
			likeService.deleteLike(likeDto);
	        return new ResponseEntity<Void>(HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@PutMapping("/increase/{boardId}")
	public ResponseEntity<String> increaseBoardLike(@PathVariable int boardId) throws Exception {
		likeService.increaseBoardLike(boardId);
		return ResponseEntity.ok().build();
	}

//	@PutMapping("/increase")
//	public ResponseEntity<String> increaseBoardLike(@RequestBody LikeDto likeDto) throws Exception {
//		likeService.increaseBoardLike(likeDto.getBoardId());
//		return ResponseEntity.ok().build();
//	}

	@PutMapping("/decrease/{boardId}")
	public ResponseEntity<String> decreaseBoardLike(@PathVariable int boardId) throws Exception {
		likeService.decreaseBoardLike(boardId);
		return ResponseEntity.ok().build();
	}

	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}