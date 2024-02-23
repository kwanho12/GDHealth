package com.tree.gdhealth.employee.login;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tree.gdhealth.customer.login.LoginMapper;
import com.tree.gdhealth.dto.Employee;

import java.util.Map;

/**
 * @author 이은택
 * @contributor: 정인호
 */
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class EmpLoginService {
	private final EmpLoginMapper mapper;

	public LoginEmployee login(Employee employee) {
		Integer LoginEmployeeNo = mapper.employeeLoginCk(employee);
		if(LoginEmployeeNo == null){
			return null;
		}
        return mapper.getLoginEmployeeInfo(LoginEmployeeNo);
	}
}
