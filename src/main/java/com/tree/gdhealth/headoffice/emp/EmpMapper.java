package com.tree.gdhealth.headoffice.emp;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.tree.gdhealth.domain.Employee;
import com.tree.gdhealth.domain.EmployeeDetail;
import com.tree.gdhealth.domain.EmployeeImg;
import com.tree.gdhealth.dto.PaginationDto;

/**
 * @author 진관호
 */
@Mapper
public interface EmpMapper {

	List<Map<String, Object>> selectEmployeeList(PaginationDto paginationDto);

	int selectEmployeeCnt();

	int selectSearchCnt(@Param("type") String type, @Param("keyword") String keyword);

	List<String> selectBranchList();

	Map<String, Object> selectEmployeeOne(String employeeId);

	int selectIsIdExists(String employeeId);

	int insertEmployee(Employee employee);

	int insertEmployeeDetail(EmployeeDetail employeeDetail);

	int insertEmployeeImg(EmployeeImg employeeImg);

}
