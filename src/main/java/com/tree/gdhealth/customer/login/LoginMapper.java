package com.tree.gdhealth.customer.login;

import org.apache.ibatis.annotations.Mapper;

import com.tree.gdhealth.domain.Customer;

@Mapper
public interface LoginMapper {
	
	Integer customerLoginCk(Customer customer);
	Integer customerFindPwCk(Customer customer);
	int customerResetPw(Customer customer);
	
}
