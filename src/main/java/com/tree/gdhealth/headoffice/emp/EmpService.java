package com.tree.gdhealth.headoffice.emp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.tree.gdhealth.dto.Employee;
import com.tree.gdhealth.dto.EmployeeDetail;
import com.tree.gdhealth.dto.EmployeeImg;
import com.tree.gdhealth.utils.imagesave.HeadofficeImageSaver;
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

	/**
	 * 전체 직원 목록을 리턴합니다.
	 * 
	 * @param beginRow   해당 페이지 내에서의 첫번째 직원
	 * @param rowPerPage 한 페이지에 나타낼 직원의 수
	 * @return 직원 목록
	 */
	@Transactional(readOnly = true)
	public List<Map<String, Object>> getEmployeeList(int beginRow, int rowPerPage) {

		Map<String, Object> map = new HashMap<>();
		map.put("beginRow", beginRow);
		map.put("rowPerPage", rowPerPage);

		return empMapper.selectEmployeeList(map);
	}

	/**
	 * 전체 직원 수를 리턴합니다.
	 * 
	 * @return 직원 수
	 */
	@Transactional(readOnly = true)
	public int getEmployeeCnt() {
		return empMapper.selectEmployeeCnt();
	}

	/**
	 * 검색 조건을 만족하는 직원 목록을 리턴합니다.
	 * 
	 * @param beginRow   해당 페이지 내에서의 첫번째 직원
	 * @param rowPerPage 한 페이지에 나타낼 직원의 수
	 * @param type       검색할 keyword의 속성(id,active...)
	 * @param keyword    검색 내용
	 * @return 검색 후의 직원 목록
	 */
	@Transactional(readOnly = true)
	public List<Map<String, Object>> getEmployeeList(int beginRow, int rowPerPage, String type, String keyword) {

		Map<String, Object> map = new HashMap<>();
		map.put("beginRow", beginRow);
		map.put("rowPerPage", rowPerPage);
		map.put("type", type);
		map.put("keyword", keyword);

		return empMapper.selectEmployeeList(map);
	}

	/**
	 * 검색 조건을 만족하는 직원 수를 리턴합니다.
	 * 
	 * @param type    검색할 keyword의 속성(id,active...)
	 * @param keyword 검색 내용
	 * @return 검색 조건을 만족하는 직원 수
	 */
	@Transactional(readOnly = true)
	public int getEmployeeCnt(String type, String keyword) {

		Map<String, Object> map = new HashMap<>();
		map.put("keyword", keyword);
		map.put("type", type);

		return empMapper.selectSearchCnt(map);
	}

	/**
	 * 전체 지점 목록을 리턴합니다.
	 * 
	 * @return 지점 목록
	 */
	@Transactional(readOnly = true)
	public List<String> getBranchList() {
		return empMapper.selectBranchList();
	}

	/**
	 * 직원 상세 정보를 리턴합니다.
	 * 
	 * @param employeeId 조회할 직원의 id
	 * @return 직원 상세 정보
	 */
	@Transactional(readOnly = true)
	public Map<String, Object> getEmployeeOne(String employeeId) {
		return  empMapper.selectEmployeeOne(employeeId);
	}

	/**
	 * 데이터베이스에서 해당 id를 검색하여 직원 id의 중복 여부를 확인합니다.
	 * 입력한 id가 존재하지 않는다면 0을 리턴합니다.
	 * 
	 * @param employeeId
	 * @return 입력한 id가 존재하지 않는다면 0, 이미 존재한다면 1
	 */
	@Transactional(readOnly = true)
	public int getResultOfIdCheck(String employeeId) {
		return empMapper.selectIsIdExists(employeeId);
	}

	/**
	 * 직원 정보를 데이터베이스에 삽입합니다.
	 *
	 * @param employee       삽입할 직원의 기본 정보를 담은 Employee 객체
	 * @param employeeDetail 삽입할 직원의 상세 정보를 담은 EmployeeDetail 객체
	 * @param employeeImg    삽입할 직원의 이미지 정보를 담은 EmployeeImg 객체
	 * @param path           직원 이미지 파일을 저장할 경로
	 */
	public void addEmployee(Employee employee, EmployeeDetail employeeDetail, EmployeeImg employeeImg, String path) {

		empMapper.insertEmployee(employee);

		employeeDetail.setEmployeeNo(employee.getEmployeeNo());
		empMapper.insertEmployeeDetail(employeeDetail);

		MultipartFile employeeFile = employeeImg.getEmployeeFile();
		addEmpImg(employeeFile, path, employee.getEmployeeNo());
	}

	/**
	 * 직원 이미지 파일을 서버에 저장하고 데이터베이스에 이미지 정보를 삽입합니다.
	 *
	 * @param employeeFile 직원 이미지 파일을 나타내는 MultipartFile 객체
	 * @param path         직원 이미지 파일을 저장할 경로
	 * @param employeeNo   이미지가 속하는 직원의 번호
	 */
	public void addEmpImg(MultipartFile employeeFile, String path, int employeeNo) {

		HeadofficeImageSaver imgSave = new HeadofficeImageSaver();

		EmployeeImg img = new EmployeeImg();
		img.setEmployeeNo(employeeNo);
		img.setEmployeeImgOriginName(employeeFile.getOriginalFilename());
		img.setEmployeeImgSize(employeeFile.getSize());
		img.setEmployeeImgType(employeeFile.getContentType());

		String filename = imgSave.getFilename(employeeFile);
		img.setEmployeeImgFilename(filename);

		empMapper.insertEmployeeImg(img);

		imgSave.saveFile(employeeFile, path, filename);
	}
	
	/**
	 * 페이지네이션 정보를 생성하여 페이지네이션 객체를 리턴합니다.
	 *
	 * @param pageNum     현재 페이지 번호
	 * @param customerCnt 고객 수
	 * @return 페이지네이션 정보
	 */
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
