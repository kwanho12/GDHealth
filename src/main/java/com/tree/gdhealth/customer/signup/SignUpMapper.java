package com.tree.gdhealth.customer.signup;

import org.apache.ibatis.annotations.Mapper;

import com.tree.gdhealth.domain.Customer;
import com.tree.gdhealth.domain.CustomerSignUp;

@Mapper
public interface SignUpMapper {
	
	int customerIn(CustomerSignUp customerSignUp);
	int customerDetailIn(CustomerSignUp customerSignUp);
	int customerImgIn(CustomerSignUp customerSignUp);
	Integer customerNoCk(String customerId);
	Integer customerEmailCk(String customerEmail);
	
}
