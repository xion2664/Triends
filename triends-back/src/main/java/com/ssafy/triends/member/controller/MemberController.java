package com.ssafy.triends.member.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.triends.member.model.Member;
import com.ssafy.triends.member.model.service.MemberService;
import com.ssafy.triends.util.JWTUtil;

import lombok.extern.slf4j.Slf4j;

@CrossOrigin("*")
@RestController
@RequestMapping("/member")
@Slf4j
public class MemberController {

	private final MemberService memberService;
	private JWTUtil jwtUtil;

	public MemberController(MemberService memberService, JWTUtil jwtUtil) {
		super();
		this.memberService = memberService;
		this.jwtUtil = jwtUtil;
	}
	
	@GetMapping("/check")
	public ResponseEntity<String> check() {
		return ResponseEntity.status(HttpStatus.ACCEPTED).build();
	}

	@GetMapping("/info/{userId}")
	public ResponseEntity<Map<String, Object>> getInfo(@PathVariable String userId, HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
//		final String token = request.get	Header("Authorization");
//		log.info("전달된 토큰 : {}",token);
		try {
//		로그인 사용자 정보.
			Member member = memberService.userInfo(userId);
			resultMap.put("userInfo", member);
			status = HttpStatus.OK;
		} catch (Exception e) {
			log.error("정보조회 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> login(@RequestBody Member member) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpStatus status = HttpStatus.ACCEPTED;
		try {
			Member loginUser = memberService.login(member.getUserId(), member.getUserPass());
			log.debug("loginUser ; {}", loginUser);
			if (loginUser != null) {
				String accessToken = jwtUtil.createAccessToken(loginUser.getUserId());
				log.debug("access token : {}", accessToken);

//				JSON으로 token 전달.
				resultMap.put("access-token", accessToken);

				status = HttpStatus.CREATED;
			} else {
				resultMap.put("message", "아이디 또는 패스워드를 확인해주세요.");
				status = HttpStatus.UNAUTHORIZED;
			}
		} catch (Exception e) {
			log.debug("로그인 에러 발생 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@PostMapping
	public ResponseEntity<String> regist(@RequestBody Member member) {
		memberService.regist(member);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@PutMapping
	public ResponseEntity<String> modify(@RequestBody Member member) {
		memberService.modify(member);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@DeleteMapping("{userId}")
	public ResponseEntity<String> delete(@PathVariable String userId) {
		memberService.delete(userId);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
}
