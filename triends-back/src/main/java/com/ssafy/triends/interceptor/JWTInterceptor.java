package com.ssafy.triends.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.ssafy.triends.exception.UnAuthorizedException;
import com.ssafy.triends.util.JWTUtil;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JWTInterceptor implements HandlerInterceptor {

	private final String HEADER_AUTH = "Authorization";
	
	private JWTUtil jwtUtil;

	public JWTInterceptor(JWTUtil jwtUtil) {
		super();
		this.jwtUtil = jwtUtil;
	}

	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if(request.getMethod().equals("OPTIONS")) {
			return true;
		}
		
		final String token = request.getHeader(HEADER_AUTH);
		log.info("전달된 토큰 : {}",token);
		if (token != null && jwtUtil.checkToken(token)) {
			log.info("토큰 사용 가능 : {}", token);
			request.setAttribute("userId", jwtUtil.getUserId(token));
			return true;
		} else {
			log.info("토큰 사용 불가능 : {}", token);
			throw new UnAuthorizedException();
		}
	}
}
