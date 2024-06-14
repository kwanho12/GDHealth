package com.tree.gdhealth.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProgramImg {
	private Integer programImgNo;
	private Integer programNo;
	private String originName;
	private String filename;
	private long programImgSize;
	private String programImgType;
	private String createdate;
	private String updatedate;
}
