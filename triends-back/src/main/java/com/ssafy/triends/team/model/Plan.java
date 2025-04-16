package com.ssafy.triends.team.model;

import lombok.Data;

@Data
public class Plan {
	private int planId;
	private String planName;	
	private int teamId;
	private String date;
}

