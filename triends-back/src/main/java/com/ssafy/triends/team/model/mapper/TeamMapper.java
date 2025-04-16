package com.ssafy.triends.team.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.triends.member.model.Member;
import com.ssafy.triends.team.model.Team;
import com.ssafy.triends.team.model.TeamInvite;
import com.ssafy.triends.team.model.TeamMember;

@Mapper
public interface TeamMapper {

	void insertTeam(Team team);

	void insertTeamMember(TeamMember tMem);

	List<Team> selectTeamByUserId(String userId);
	
	List<Member> selectTeamMember(int teamId);

	List<Member> selectNotTeamMember(int teamId);

	void deleteTeamMember(int teamId, String userId);

	void deleteEmptyTeam();

	void insertTeamInvite(TeamInvite teamInvite);

	List<TeamInvite> selectTeamInviteByUserId(String userId);

	void deleteTeamInvite(int teamId, String userId);
}
