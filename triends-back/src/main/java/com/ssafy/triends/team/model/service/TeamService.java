package com.ssafy.triends.team.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.triends.member.model.Member;
import com.ssafy.triends.team.model.Team;
import com.ssafy.triends.team.model.TeamInvite;
import com.ssafy.triends.team.model.TeamMember;

@Service
public interface TeamService {

	List<Team> listTeamByUserId(String userId);

	void registTeam(Team t, String userId);

	void registMember(TeamMember teamMember);

	List<Member> teamInviteList(int teamId);

	void leaveTeam(int teamId, String userId);

	void inviteTeam(TeamInvite teamInvite);

	List<TeamInvite> myInviteList(String userId);

	void deleteInvite(int teamId, String userId);

}
