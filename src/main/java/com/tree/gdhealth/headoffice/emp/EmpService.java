package com.tree.gdhealth.headoffice.emp;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.tree.gdhealth.domain.Employee;
import com.tree.gdhealth.domain.EmployeeDetail;
import com.tree.gdhealth.domain.EmployeeImg;
import com.tree.gdhealth.dto.AddEmpDto;
import com.tree.gdhealth.dto.PaginationDto;
import com.tree.gdhealth.utils.imagesave.ImageSaveUtil;
import com.tree.gdhealth.utils.pagination.HeadofficePagination;

import lombok.RequiredArgsConstructor;

/**
 * @author 진관호
 */
@RequiredArgsConstructor
@Transactional
@Service
public class EmpService {

	private final EmpMapper empMapper;
    private final ImageSaveUtil imageSaveUtil;

	@Transactional(readOnly = true)
	public List<Map<String, Object>> getEmployeeList(int beginRow, int rowPerPage) {
		PaginationDto paginationDto = new PaginationDto();
		paginationDto.setBeginRow(beginRow);
		paginationDto.setRowPerPage(rowPerPage);
		return empMapper.selectEmployeeList(paginationDto);
	}

	@Transactional(readOnly = true)
	public int getEmployeeCnt() {
		return empMapper.selectEmployeeCnt();
	}

	@Transactional(readOnly = true)
	public List<Map<String, Object>> getEmployeeList(int beginRow, int rowPerPage, String type, String keyword) {
		PaginationDto paginationDto = new PaginationDto();
		paginationDto.setBeginRow(beginRow);
		paginationDto.setRowPerPage(rowPerPage);
		paginationDto.setType(type);
		paginationDto.setKeyword(keyword);
		return empMapper.selectEmployeeList(paginationDto);
	}

	@Transactional(readOnly = true)
	public int getEmployeeCnt(String type, String keyword) {
		return empMapper.selectSearchCnt(type, keyword);
	}

	@Transactional(readOnly = true)
	public List<String> getBranchList() {
		return empMapper.selectBranchList();
	}

	@Transactional(readOnly = true)
	public Map<String, Object> getEmployeeOne(String employeeId) {
		return empMapper.selectEmployeeOne(employeeId);
	}

	@Transactional(readOnly = true)
	public int getResultOfIdCheck(String employeeId) {
		return empMapper.selectIsIdExists(employeeId);
	}

	public void addEmployee(AddEmpDto addEmpDto, String path) {

		Employee employee = Employee.builder()
								.branchNo(addEmpDto.getBranchNo())
								.employeeId(addEmpDto.getEmployeeId())
								.employeePw(addEmpDto.getEmployeePw())
								.employeePosition(addEmpDto.getEmployeePosition())
								.build();
		empMapper.insertEmployee(employee);

		EmployeeDetail employeeDetail = EmployeeDetail.builder()
											.employeeNo(employee.getEmployeeNo())
											.employeeName(addEmpDto.getEmployeeName())
											.employeePhone(addEmpDto.getEmployeePhone())
											.employeeEmail(addEmpDto.getEmployeeEmail())
											.employeeGender(addEmpDto.getEmployeeGender())
											.build();
		empMapper.insertEmployeeDetail(employeeDetail);

		MultipartFile employeeFile = addEmpDto.getEmployeeFile();
		addEmpImg(employeeFile, path, employee.getEmployeeNo());
	}

	public void addEmpImg(MultipartFile employeeFile, String path, int employeeNo) {
		
		String originalName = employeeFile.getOriginalFilename();
		String fileName = imageSaveUtil.getFileName(Objects.requireNonNull(originalName));
		
		EmployeeImg img = EmployeeImg.builder()
							.employeeNo(employeeNo)
							.employeeImgOriginName(originalName)
							.employeeImgSize(employeeFile.getSize())
							.employeeImgType(employeeFile.getContentType())
							.employeeImgFilename(fileName)
							.build();
		empMapper.insertEmployeeImg(img);

		imageSaveUtil.saveFileToS3(employeeFile, fileName, "employee");
	}

	public HeadofficePagination getPagination(int pageNum, int employeeCnt) {

		HeadofficePagination pagination = HeadofficePagination.builder()
											.numberOfPaginationToShow(10)
											.rowPerPage(8)
											.currentPageNum(pageNum)
											.rowCnt(employeeCnt)
											.build();
		pagination.calculateProperties();
		
		return pagination;
	}
}
