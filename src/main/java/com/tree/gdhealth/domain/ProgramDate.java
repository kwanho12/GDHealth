package com.tree.gdhealth.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProgramDate {
	private Integer programDateNo;
	private Integer programNo;
	private String programDate;
	private String createdate;
	private String updatedate;
	private String originDate;
}
