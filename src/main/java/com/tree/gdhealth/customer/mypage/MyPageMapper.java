package com.tree.gdhealth.customer.mypage;

import org.apache.ibatis.annotations.Mapper;

import com.tree.gdhealth.dto.Customer;
import com.tree.gdhealth.dto.CustomerDetail;
import com.tree.gdhealth.dto.CustomerImg;
import com.tree.gdhealth.dto.CustomerMyPage;

@Mapper
public interface MyPageMapper {
	
	CustomerMyPage info(int customerNo);
	CustomerImg customerImgCk(int customerNo);
	int attendanceCount(int customerNo);
	int reviewCount(int customerNo);
	int questionCount(int customerNo);
	String membership(int customerNo);
	Integer customerDeleteCk(Customer customerNo);
	CustomerImg customerImgCk(Customer cusotomer);
	int customerDelete(Customer customer);
	int customerDetailDelete(Customer customer);
	int customerImgDelete(Customer customer);
	int updateMyPage(CustomerMyPage customerMyPage);
	int updatePw(Customer customer);
	Integer updateEmailCk(CustomerMyPage customerMyPage);
	Integer EmailCheck(String customerEmail);
	String selectPhone(int customerNo);
	CustomerImg selectCustomerImg(int customerNo);
}
