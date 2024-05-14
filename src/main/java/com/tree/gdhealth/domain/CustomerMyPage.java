package com.tree.gdhealth.domain;

import lombok.Data;

@Data
public class CustomerMyPage {
	private Integer customerNo;
	private String customerId;
	private String customerPw;
	private String customerBirth;
	private String customerGender;
	private String customerPhone;
	private int customerHeight;
	private int customerWeight;
	private int attendanceCount;
	private int reviewCount;
	private int questionCount;
	private double customerBmi;
	private String customerName;
	private String customerImgFileName;
	private String customerEmail;
	private String customerAddress;
	private String customerUpdatedate;
	private String imgFileName;
}
