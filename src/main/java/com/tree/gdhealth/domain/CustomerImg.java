package com.tree.gdhealth.domain;

import lombok.Data;

@Data
public class CustomerImg {
	private int customerNo;
	private int customerImgNo;
	private String customerImgOriginName;
	private String customerImgFileName;
	private int customerImgSize;
	private String customerImgType;
	private String createdate;
	private String updatedate;
}
