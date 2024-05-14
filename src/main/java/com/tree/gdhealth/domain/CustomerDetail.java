package com.tree.gdhealth.domain;

import lombok.Data;

@Data
public class CustomerDetail {
	private int customerNo;
	private String customerName;
	private String customerGender;
	private int customerHeight;
	private int customerWeight;
	private int customerAge;
	private String customerBirth;
	private String customerPhone;
	private String customerAddress;
	private String customerEmail;
	private String createdate;
	private String updatedate;
}
