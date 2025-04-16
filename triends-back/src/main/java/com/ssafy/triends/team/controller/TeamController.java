package com.ssafy.triends.team.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.triends.member.model.Member;
import com.ssafy.triends.team.model.Team;
import com.ssafy.triends.team.model.TeamInvite;
import com.ssafy.triends.team.model.TeamMember;
import com.ssafy.triends.team.model.service.TeamService;

@CrossOrigin("*")
@RestController
@RequestMapping("/team")
public class TeamController {
	private final TeamService teamService;

	public TeamController(TeamService teamService) {
		super();
		this.teamService = teamService;
	}
	
	//초대 목록 삭제
	@DeleteMapping("invite/{teamId}/{userId}")
	public ResponseEntity<String> deleteInvite(@PathVariable int teamId, @PathVariable String userId) {
		teamService.deleteInvite(teamId,userId);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	//받은 초대 목록
	@GetMapping("inviteList/{userId}")
	public ResponseEntity<List<TeamInvite>> myInviteList(@PathVariable String userId){
		List<TeamInvite> list = teamService.myInviteList(userId);
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping("invite/{teamId}/{fromUserId}/{toUserId}")
	public ResponseEntity<String> invite(@PathVariable int teamId, @PathVariable String fromUserId, @PathVariable String toUserId) {
		TeamInvite teamInvite = new TeamInvite();
		teamInvite.setTeamId(teamId);
		teamInvite.setFromUserId(fromUserId);
		teamInvite.setToUserId(toUserId);
		System.out.println(teamInvite);
		teamService.inviteTeam(teamInvite);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@DeleteMapping("leave/{teamId}/{userId}")
	public ResponseEntity<String> leave(@PathVariable int teamId, @PathVariable String userId) {
		teamService.leaveTeam(teamId,userId);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@GetMapping("regist/{teamName}/{userId}")
	public ResponseEntity<String> regist(@PathVariable String teamName, @PathVariable String userId) {
		Team team = new Team();
		team.setTeamName(teamName);
		teamService.registTeam(team,userId);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@GetMapping("registMember/{teamId}/{userId}")
	public ResponseEntity<String> registMember(@PathVariable int teamId, @PathVariable String userId) {
		TeamMember teamMember =new TeamMember();
		teamMember.setTeamId(teamId);
		teamMember.setUserId(userId);
		System.out.println(teamMember);
		teamService.registMember(teamMember);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	//팀에 초대할 사람 목록
	@GetMapping("invite/{teamId}")
	public ResponseEntity<List<Member>> teamInviteList(@PathVariable int teamId) {
		return ResponseEntity.ok().body(teamService.teamInviteList(teamId));
	}

	//userId가 속한팀 + 그 팀들 목록
	@GetMapping("{userId}")
	public ResponseEntity<List<Team>> listTeamByUserId(@PathVariable String userId) {
		List<Team> list = teamService.listTeamByUserId(userId);
		return ResponseEntity.ok().body(list);
	}
}
