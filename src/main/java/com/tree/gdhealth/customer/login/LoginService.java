package com.tree.gdhealth.customer.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tree.gdhealth.dto.Customer;

@Service
@Transactional
public class LoginService {
	@Autowired LoginMapper loginMapper;
	
	public int login(Customer customer) {
		int n;
		Integer customerNoCkResult = loginMapper.customerLoginCk(customer);
		if (customerNoCkResult == null || customerNoCkResult == 0) {	// customerNo가 널값이면 0으로 대체
		    n = 0;
		    return n;
		}
		
		n = loginMapper.customerLoginCk(customer);
		
		return n;
	}
	
	public int findPw(Customer customer) {
		int n;
		Integer customerFindPwResult = loginMapper.customerFindPwCk(customer);
		if (customerFindPwResult == null || customerFindPwResult == 0) {
			n = 0;
			return n;
		}
		n = loginMapper.customerFindPwCk(customer);
		
		return n;
	}

	public int resetPw(Customer customer) {
		int n = loginMapper.customerResetPw(customer);
		return n;
	}
	
	
	
}
