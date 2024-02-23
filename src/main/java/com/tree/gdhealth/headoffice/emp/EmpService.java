package com.tree.gdhealth.headoffice.emp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.tree.gdhealth.utils.imagesave.HeadofficeImageSaver;
import com.tree.gdhealth.vo.Employee;
import com.tree.gdhealth.vo.EmployeeDetail;
import com.tree.gdhealth.vo.EmployeeImg;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class EmpService {

	// DI
	private final EmpMapper empMapper;

	@Transactional(readOnly = true)
	public List<Map<String, Object>> getEmployeeList(int beginRow, int rowPerPage) {

		Map<String, Object> map = new HashMap<>();
		map.put("beginRow", beginRow);
		map.put("rowPerPage", rowPerPage);

		List<Map<String, Object>> employeeList = empMapper.selectEmployeeList(map);

		return employeeList;
	}

	@Transactional(readOnly = true)
	public int getEmployeeCnt() {

		int employeeCnt = empMapper.selectEmployeeCnt();
		// 디버깅
		log.debug("전체 직원 수 : " + employeeCnt);

		return employeeCnt;
	}

	@Transactional(readOnly = true)
	public List<Map<String, Object>> getSearchList(int beginRow, int rowPerPage, String type, String keyword) {

		Map<String, Object> map = new HashMap<>();
		map.put("beginRow", beginRow);
		map.put("rowPerPage", rowPerPage);
		map.put("type", type);
		map.put("keyword", keyword);

		List<Map<String, Object>> searchList = empMapper.selectEmployeeList(map);

		return searchList;

	}

	@Transactional(readOnly = true)
	public int getSearchCnt(String type, String keyword) {

		Map<String, Object> map = new HashMap<>();
		map.put("keyword", keyword);
		map.put("type", type);

		int searchCnt = empMapper.selectSearchCnt(map);
		// 디버깅
		log.debug("검색 결과 개수 : " + searchCnt);

		return searchCnt;
	}

	@Transactional(readOnly = true)
	public List<String> getBranchList() {

		List<String> branchList = empMapper.selectBranchList();
		// 디버깅
		log.debug("지점 목록 : " + branchList);

		return branchList;
	}

	@Transactional(readOnly = true)
	public Map<String, Object> getEmployeeOne(String employeeId) {

		Map<String, Object> employeeOne = empMapper.selectEmployeeOne(employeeId);
		// 디버깅
		log.debug("직원 상세 정보 : " + employeeOne);

		return employeeOne;
	}

	@Transactional(readOnly = true)
	public int getIsIdExists(String employeeId) {

		int result = empMapper.selectIsIdExists(employeeId);

		return result;
	}

	public void insertEmployee(Employee employee, EmployeeDetail employeeDetail, EmployeeImg employeeImg, String path) {

		int result = empMapper.insertEmployee(employee);
		// 디버깅
		log.debug("employee 추가(성공:1) : " + result);

		// empMapper.xml에서 selectKey로 얻어 온 employee table의 auto increment 값
		employeeDetail.setEmployeeNo(employee.getEmployeeNo());
		int detailResult = empMapper.insertEmployeeDetail(employeeDetail);
		// 디버깅
		log.debug("employeeDetail 추가(성공:1) : " + detailResult);

		MultipartFile employeeFile = employeeImg.getEmployeeFile();
		// 파일 저장
		insertEmpImg(employeeFile, path, employee.getEmployeeNo());

	}

	public void insertEmpImg(MultipartFile employeeFile, String path, int employeeNo) {

		HeadofficeImageSaver imgSave = new HeadofficeImageSaver();

		EmployeeImg img = new EmployeeImg();
		img.setEmployeeNo(employeeNo);
		img.setEmployeeImgOriginName(employeeFile.getOriginalFilename());
		img.setEmployeeImgSize(employeeFile.getSize());
		img.setEmployeeImgType(employeeFile.getContentType());

		String filename = imgSave.getFilename(employeeFile);
		img.setEmployeeImgFilename(filename);

		int imgResult = empMapper.insertEmployeeImg(img);
		log.debug("employeeImg 추가(성공:1) : " + imgResult);

		// 파일 저장
		imgSave.saveFile(employeeFile, path, filename);

	}

}
