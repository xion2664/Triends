package com.ssafy.triends.member.model.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.triends.member.model.Member;

@Mapper
public interface MemberMapper {

	Member select(String userId, String userPass);

	void insert(Member member);

	Member selectById(String userId);

	void update(Member member);

	String selectPassById(String userId);

	void delete(String userId);
}
