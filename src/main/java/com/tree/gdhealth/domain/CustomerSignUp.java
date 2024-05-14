package com.tree.gdhealth.domain;

import lombok.Data;

@Data
public class CustomerSignUp {
	private int customerNo;
	private String customerId;
	private String customerPw;
	private String customerActive;
	private String customerName;
	private String customerGender;
	private double customerHeight;
	private double customerWeight;
	private int customerAge;
	private String customerBirth;
	private String customerPhone;
	private String customerAddress;
	private String customerAddressDetail;
	private String customerEmail;
	private int customerImgNo;
	private String customerImgOriginName;
	private String customerImgFileName;
	private int customerImgSize;
	private String customerImgType;
	private String createdate;
	private String updatedate;
	
}
