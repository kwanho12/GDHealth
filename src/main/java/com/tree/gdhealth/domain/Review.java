package com.tree.gdhealth.domain;

import lombok.Data;

@Data
public class Review {
	private int reviewNo;
	private int programRsNo;
	private String customerId;
	private String reviewTitle;
	private String reviewContent;
	private String createdate;
	private String updatedate;
	private Integer customerNo;
	private int count;
}
