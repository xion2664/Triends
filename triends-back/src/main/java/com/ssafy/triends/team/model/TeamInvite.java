package com.ssafy.triends.team.model;

import lombok.Data;

@Data
public class TeamInvite {
	private String teamName, fromUserId, fromUserName, toUserId;
	private int teamId;
}
