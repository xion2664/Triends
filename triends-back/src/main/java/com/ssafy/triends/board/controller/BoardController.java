package com.ssafy.triends.board.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.UUID;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.triends.board.model.BoardDto;
import com.ssafy.triends.board.model.BoardListDto;
import com.ssafy.triends.board.model.service.BoardService;

//http://localhost/vue/swagger-ui.html
@CrossOrigin(origins = { "*" }, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.POST} , maxAge = 6000)
@RestController
@RequestMapping("/board")
public class BoardController {
	
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);

	private BoardService boardService;

	public BoardController(BoardService boardService) {
		super();
		this.boardService = boardService;
	}

	@PostMapping
	public ResponseEntity<?> writeArticle(BoardDto boardDto) {
		logger.info("writeArticle boardDto - {}", boardDto);
		logger.info("filename : " + boardDto.getFile().getOriginalFilename());
		try {
			logger.info("writeArticle boardDto - {}", boardDto);
			logger.info("filename : " + boardDto.getFile().getOriginalFilename());
			MultipartFile mfile = boardDto.getFile();
			
//			String directoryPath = "C:/ssafy/triends-front/src/assets/savedImage";
//			String directoryPath = "C:/triends/triends-front/src/assets/img/savedImage";
			String directoryPath = "C:/Users/User/OneDrive/Document/SSAFY/Semester1/Project/FinalProject/triends/triends-front/src/assets/img/savedImage";
			File folder = new File(directoryPath);
			if (!folder.exists()) folder.mkdirs();
	        String originalFilename = mfile.getOriginalFilename();
	        String newFilename = UUID.randomUUID().toString() + originalFilename.substring(originalFilename.lastIndexOf('.'));
	        File fileToSave = new File(directoryPath + File.separator + newFilename);
	        try (FileOutputStream fos = new FileOutputStream(fileToSave)) {
	            fos.write(mfile.getBytes());
	        } catch (IOException e) {
	            throw new IOException("Error saving MultipartFile", e);
	        }
	        directoryPath = directoryPath.replaceAll("C:/triends/triends-front", "");
//	        directoryPath = directoryPath.replaceAll("C:/ssafy/triends-front", "");
	        boardDto.setImagePath(directoryPath + "/" + newFilename);
	        boardService.writeArticle(boardDto);
	        return new ResponseEntity<Void>(HttpStatus.CREATED);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@GetMapping
	public ResponseEntity<?> listArticle(Map<String, String> map) {
		try {
			BoardListDto boardListDto = boardService.listArticle();
//			System.out.println(boardListDto);
			HttpHeaders header = new HttpHeaders();
			header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
			return ResponseEntity.ok().headers(header).body(boardListDto);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@GetMapping("/like")
	public ResponseEntity<?> listArticleByLike(Map<String, String> map) {
		try {
			BoardListDto boardListDto = boardService.listArticleByLike();
//			System.out.println(boardListDto);
			HttpHeaders header = new HttpHeaders();
			header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
			return ResponseEntity.ok().headers(header).body(boardListDto);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@DeleteMapping("{articleNo}")
	public ResponseEntity<?> deleteArticle(@PathVariable int articleNo) {
		try {
			System.out.println("d");
			boardService.deleteArticle(articleNo);
	        return new ResponseEntity<Void>(HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

//
//	@ApiOperation(value = "수정 할 글 얻기", notes = "글번호에 해당하는 게시글의 정보를 반환한다.", response = BoardDto.class)
//	@GetMapping("/modify/{articleno}")
//	public ResponseEntity<BoardDto> getModifyArticle(
//			@PathVariable("articleno") @ApiParam(value = "얻어올 글의 글번호.", required = true) int articleno)
//			throws Exception {
//		logger.info("getModifyArticle - 호출 : " + articleno);
//		return new ResponseEntity<BoardDto>(boardService.getArticle(articleno), HttpStatus.OK);
//	}
//
//	@ApiOperation(value = "게시판 글수정", notes = "수정할 게시글 정보를 입력한다. 그리고 DB수정 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
//	@PutMapping
//	public ResponseEntity<String> modifyArticle(
//			@RequestBody @ApiParam(value = "수정할 글정보.", required = true) BoardDto boardDto) throws Exception {
//		logger.info("modifyArticle - 호출 {}", boardDto);
//
//		boardService.modifyArticle(boardDto);
//		return ResponseEntity.ok().build();
//	}
//	
//	@ApiOperation(value = "게시판 글삭제", notes = "글번호에 해당하는 게시글의 정보를 삭제한다. 그리고 DB삭제 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
//	@DeleteMapping("/{articleno}")
//	public ResponseEntity<String> deleteArticle(@PathVariable("articleno") @ApiParam(value = "살제할 글의 글번호.", required = true) int articleno) throws Exception {
//		logger.info("deleteArticle - 호출");
//		boardService.deleteArticle(articleno);
//		return ResponseEntity.ok().build();
//
//	}

	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}