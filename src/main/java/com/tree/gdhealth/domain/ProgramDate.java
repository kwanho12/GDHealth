package com.tree.gdhealth.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ProgramDate {
	private int programDateNo;
	private int programNo;
	private String programDate;
	private String createdate;
	private String updatedate;
	private String originDate;
}
