package com.ssafy.triends.team.model;

import java.util.List;

import com.ssafy.triends.member.model.Member;

import lombok.Data;

@Data
public class Team {
	private int teamId;
	private String teamName;
	private List<Member> teamList;
}
