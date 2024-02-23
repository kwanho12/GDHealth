package com.tree.gdhealth.employee.login;

import org.apache.ibatis.annotations.Mapper;

import com.tree.gdhealth.dto.Employee;

@Mapper
public interface EmpLoginMapper {
	Integer employeeLoginCk(Employee employee);

	LoginEmployee getLoginEmployeeInfo(Integer loginEmployeeNo);
}
