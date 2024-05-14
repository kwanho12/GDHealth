package com.tree.gdhealth.domain;

import lombok.Data;

@Data
public class ProgramImg {
	private int programImgNo;
	private int programNo;
	private String originName;
	private String filename;
	private long programImgSize;
	private String programImgType;
	private String createdate;
	private String updatedate;
}
