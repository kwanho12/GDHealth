package com.tree.gdhealth.headoffice.emp;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tree.gdhealth.utils.Paging;
import com.tree.gdhealth.utils.auth.Auth;
import com.tree.gdhealth.utils.auth.Authority;
import com.tree.gdhealth.vo.Employee;
import com.tree.gdhealth.vo.EmployeeDetail;
import com.tree.gdhealth.vo.EmployeeImg;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/headoffice/emp")
@RequiredArgsConstructor
@Controller
public class EmpController {

	private final EmpService empService;

	@Auth(AUTHORITY = Authority.HEAD_EMP_ONLY)
	@GetMapping
	public String emp() {

		return "headoffice/empList";

	}

	@GetMapping("/paging")
	public String paging(Model model, int page) {

		// 전체 직원 수
		int employeeCnt = empService.getEmployeeCnt();
		// 디버깅
		log.debug("전체 직원 수 : " + employeeCnt);

		// 페이징
		Paging paging = Paging.builder().pageNumCnt(10) // 한번에 표시할 페이징 번호의 갯수
				.rowPerPage(8) // 한 페이지에 나타낼 row 수
				.currentPage(page) // 현재 페이지
				.cnt(employeeCnt) // 전체 row 수
				.build();
		paging.calculation();

		List<Map<String, Object>> empList = empService.getEmployeeList(paging.getBeginRow(), paging.getRowPerPage());
		model.addAttribute("empList", empList);

		// 페이징(model 추가)
		paging.pagingAttributes(model, paging, page);

		return "headoffice/fragment/emp";

	}

	@GetMapping("/searchPaging")
	public String searchPaging(Model model, String type, String keyword, int page) {

		// 검색 결과 개수
		int searchCnt = empService.getSearchCnt(type, keyword);
		// 디버깅
		log.debug("검색 결과 개수(searchPaging) : " + searchCnt);

		Paging paging = Paging.builder().pageNumCnt(10) // 한번에 표시할 페이징 번호의 갯수
				.rowPerPage(8) // 한 페이지에 나타낼 row 수
				.currentPage(page) // 현재 페이지
				.cnt(searchCnt) // 전체 row 수
				.build();
		paging.calculation();

		List<Map<String, Object>> searchList = empService.getSearchList(paging.getBeginRow(), paging.getRowPerPage(),
				type, keyword);
		model.addAttribute("empList", searchList);

		// 페이징(model 추가)
		paging.pagingAttributes(model, paging, page);

		// search parameter 추가
		model.addAttribute("type", type);
		model.addAttribute("keyword", keyword);

		return "headoffice/fragment/searchEmp";

	}

	@ResponseBody
	@GetMapping("/branchList")
	public List<String> branchList() {

		List<String> branchList = empService.getBranchList();

		return branchList;

	}

	@ResponseBody
	@PostMapping("/addEmpIdCheck")
	public int addEmpIdCheck(String employeeId) {

		int result = empService.idCheck(employeeId);

		log.debug("아이디 중복 체크(중복o:1,중복x:0) : " + result);

		return result;
	}

	@Auth(AUTHORITY = Authority.HEAD_EMP_ONLY)
	@GetMapping("/addEmp")
	public String addEmp(HttpSession session) {

		return "headoffice/addEmp";

	}

	@Auth(AUTHORITY = Authority.HEAD_EMP_ONLY)
	@PostMapping("/addEmp")
	public String addEmp(@Validated Employee employee, BindingResult bindingResult1,
			@Validated EmployeeDetail employeeDetail, BindingResult bindingResult2, @Validated EmployeeImg employeeImg,
			BindingResult bindingResult3, HttpSession session, Model model) {

		// 첫 번째 객체(Employee)의 유효성 검사 실패 시 처리
		if (bindingResult1.hasErrors()) {

			// 에러 메시지 출력
			for (ObjectError error : bindingResult1.getAllErrors()) {
				log.debug(error.getDefaultMessage());
			}

			return "headoffice/addEmp";
		}

		// 두 번째 객체(EmployeeDetail)의 유효성 검사 실패 시 처리
		if (bindingResult2.hasErrors()) {

			// 에러 메시지 출력
			for (ObjectError error : bindingResult2.getAllErrors()) {
				log.debug(error.getDefaultMessage());
			}

			return "headoffice/addEmp";
		}

		// 세 번째 객체(EmployeeImg)의 유효성 검사 실패 시 처리
		if (bindingResult3.hasErrors()) {

			// 에러 메시지 출력
			for (ObjectError error : bindingResult3.getAllErrors()) {
				log.debug(error.getDefaultMessage());
			}

			return "headoffice/addEmp";
		}

		String path = session.getServletContext().getRealPath("/upload/emp");
		// 디버깅
		log.debug("저장 경로 : " + path);
		empService.insertEmployee(employee, employeeDetail, employeeImg, path);

		return "redirect:/headoffice/emp";
	}

	@Auth(AUTHORITY = Authority.HEAD_EMP_ONLY)
	@GetMapping("/empOne/{employeeId}")
	public String empOne(Model model, @PathVariable String employeeId) {

		Map<String, Object> employeeOne = empService.getEmployeeOne(employeeId);
		// 디버깅
		log.debug("회원 상세 정보 : " + employeeOne);
		model.addAttribute("empOne", employeeOne);

		return "headoffice/empOne";
	}

}
