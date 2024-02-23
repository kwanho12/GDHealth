package com.tree.gdhealth.headoffice.emp;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.tree.gdhealth.vo.Employee;
import com.tree.gdhealth.vo.EmployeeDetail;
import com.tree.gdhealth.vo.EmployeeImg;

@Mapper
public interface EmpMapper {

	List<Map<String, Object>> selectEmployeeList(Map<String, Object> map);

	int selectEmployeeCnt();

	int selectSearchCnt(Map<String, Object> map);

	List<String> selectBranchList();

	Map<String, Object> selectEmployeeOne(String employeeId);

	int selectIsIdExists(String employeeId);

	int insertEmployee(Employee employee);

	int insertEmployeeDetail(EmployeeDetail employeeDetail);

	int insertEmployeeImg(EmployeeImg employeeImg);

}
