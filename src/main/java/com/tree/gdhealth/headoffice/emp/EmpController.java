package com.tree.gdhealth.headoffice.emp;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tree.gdhealth.dto.Employee;
import com.tree.gdhealth.dto.EmployeeDetail;
import com.tree.gdhealth.dto.EmployeeImg;
import com.tree.gdhealth.utils.auth.Auth;
import com.tree.gdhealth.utils.auth.Authority;
import com.tree.gdhealth.utils.pagination.HeadofficePagination;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

/**
 * @author 진관호
 */
@RequestMapping("/headoffice/emp")
@RequiredArgsConstructor
@Controller
public class EmpController {

	private final EmpService empService;

	/**
	 * 전체 직원 목록을 나타내는 페이지로 이동합니다.
	 * 
	 * @return 직원 목록 페이지
	 */
	@Auth(AUTHORITY = Authority.HEAD_EMP_ONLY)
	@GetMapping
	public String getEmpList() {
		return "headoffice/empList";
	}

	/**
	 * 페이지네이션 후의 직원 목록 영역을 리턴합니다.
	 * 
	 * @param pageNum 이동할 페이지 번호
	 * @return 페이지네이션 후의 직원 목록
	 * @apiNote 페이지 전체가 아닌 직원의 목록을 나타내는 영역만 리턴합니다.
	 */
	@GetMapping("/pagination")
	public String getPagination(Model model, int pageNum) {

		int employeeCnt = empService.getEmployeeCnt();
		HeadofficePagination pagination = empService.getPagination(pageNum, employeeCnt);

		List<Map<String, Object>> empList = empService.getEmployeeList(pagination.getBeginRow(),
				pagination.getRowPerPage());

		pagination.addModelAttributes(model, pagination);
		model.addAttribute("empList", empList);

		return "headoffice/fragment/empList";
	}

	/**
	 * 검색 결과가 반영된 페이지네이션 후의 직원 목록 영역을 리턴합니다.
	 * 
	 * @param type    검색할 keyword의 속성(id,active...)
	 * @param keyword 검색 내용
	 * @param pageNum 이동할 페이지 번호
	 * @return 페이지네이션 후의 직원 목록
	 * @apiNote 페이지 전체가 아닌 직원의 목록을 나타내는 영역만 리턴합니다.
	 */
	@GetMapping("/searchPagination")
	public String getPagination(Model model, String type, String keyword, int pageNum) {

		int searchCnt = empService.getEmployeeCnt(type, keyword);
		HeadofficePagination pagination = empService.getPagination(pageNum, searchCnt);

		List<Map<String, Object>> searchList = empService.getEmployeeList(pagination.getBeginRow(),
				pagination.getRowPerPage(), type, keyword);

		pagination.addModelAttributes(model, pagination);
		model.addAttribute("empList", searchList);
		model.addAttribute("type", type);
		model.addAttribute("keyword", keyword);

		return "headoffice/fragment/searchEmpList";
	}

	/**
	 * 전체 지점 목록을 리턴합니다.
	 * 
	 * @return 지점 목록
	 */
	@ResponseBody
	@GetMapping("/branchList")
	public List<String> getBranchList() {
		return empService.getBranchList();
	}

	/**
	 * 데이터베이스에서 해당 id를 검색하여 직원 id의 중복 여부를 확인합니다.
	 * 입력한 id가 데이터베이스에 존재하지 않는다면 0을 리턴합니다.
	 * 
	 * @param employeeId 입력한 직원id
	 * @return 입력한 id가 데이터베이스에 존재하지 않는다면 0, 이미 존재한다면 1
	 */
	@ResponseBody
	@PostMapping("/checkExistingId")
	public int checkExistingId(String employeeId) {

		int result = empService.getResultOfIdCheck(employeeId);

		return result;
	}

	/**
	 * 직원 추가 페이지로 이동합니다.
	 * 
	 * @return 직원 추가 페이지
	 */
	@Auth(AUTHORITY = Authority.HEAD_EMP_ONLY)
	@GetMapping("/add")
	public String addEmp() {
		return "headoffice/addEmp";
	}

	/**
	 * 성공적으로 직원 정보를 추가했을 경우 직원 목록 페이지로 리다이렉트합니다.
	 * 유효성 검사 실패로 인해 추가가 중단된 경우 다시 직원 추가 페이지로 이동합니다.
	 * 
	 * @param employee       추가할 직원의 기본 정보를 담은 Employee 객체
	 * @param bindingResult1 employee 매개변수에 대한 유효성 검사 결과를 담은 BindingResult 객체
	 * @param employeeDetail 추가할 직원의 상세 정보를 담은 EmployeeDetail 객체
	 * @param bindingResult2 employeeDetail 매개변수에 대한 유효성 검사 결과를 담은 BindingResult 객체
	 * @param employeeImg    추가할 직원의 이미지 정보를 담은 EmployeeImg 객체
	 * @param bindingResult3 employeeImg 매개변수에 대한 유효성 검사 결과를 담은 BindingResult 객체
	 * @return 직원 목록 페이지로 리다이렉트
	 */
	@Auth(AUTHORITY = Authority.HEAD_EMP_ONLY)
	@PostMapping("/add")
	public String addEmp(@Validated Employee employee, BindingResult bindingResult1,
			@Validated EmployeeDetail employeeDetail, BindingResult bindingResult2, @Validated EmployeeImg employeeImg,
			BindingResult bindingResult3, HttpSession session, Model model) {

		if (bindingResult1.hasErrors()) {
			return "headoffice/addEmp";
		}

		if (bindingResult2.hasErrors()) {
			return "headoffice/addEmp";
		}

		if (bindingResult3.hasErrors()) {
			return "headoffice/addEmp";
		}

		String path = session.getServletContext().getRealPath("/upload/emp");
		empService.addEmployee(employee, employeeDetail, employeeImg, path);

		return "redirect:/headoffice/emp";
	}

	/**
	 * 직원의 상세 정보 페이지로 이동합니다.
	 *
	 * @param employeeId 조회할 직원의 ID
	 * @return 직원의 상세 정보 페이지
	 */
	@Auth(AUTHORITY = Authority.HEAD_EMP_ONLY)
	@GetMapping("/{employeeId}")
	public String getEmpOne(Model model, @PathVariable String employeeId) {

		Map<String, Object> employeeOne = empService.getEmployeeOne(employeeId);
		model.addAttribute("empOne", employeeOne);

		return "headoffice/empOne";
	}

}
