package com.tree.gdhealth.headoffice.emp;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tree.gdhealth.headoffice.dto.AddEmpDto;
import com.tree.gdhealth.headoffice.dto.PageDto;
import com.tree.gdhealth.utils.auth.Auth;
import com.tree.gdhealth.utils.auth.Authority;
import com.tree.gdhealth.utils.exception.EmpNotFoundException;
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
	 * @param pageDto 페이지네이션과 관련한 데이터를 전송하기 위한 객체
	 * @return 페이지네이션 후의 직원 목록
	 * @apiNote 페이지 전체가 아닌 직원의 목록을 나타내는 영역만 리턴합니다.
	 */
	@GetMapping("/pagination")
	public String getPagination(Model model, @ModelAttribute PageDto pageDto) {

		HeadofficePagination pagination = empService.getPagination(pageDto.getPageNum(), empService.getEmployeeCnt());

		pagination.addModelAttributes(model, pagination);
		model.addAttribute("empList", empService.getEmployeeList(pagination.getBeginRow(), pagination.getRowPerPage()));

		return "headoffice/fragment/empList";
	}

	/**
	 * 검색 결과가 반영된 페이지네이션 후의 직원 목록 영역을 리턴합니다.
	 * 
	 * @param pageDto 페이지네이션과 관련한 데이터를 전송하기 위한 객체
	 * @return 페이지네이션 후의 직원 목록
	 * @apiNote 페이지 전체가 아닌 직원의 목록을 나타내는 영역만 리턴합니다.
	 */
	@GetMapping("/searchPagination")
	public String getSearchPagination(Model model, @ModelAttribute PageDto pageDto) {

		HeadofficePagination pagination = empService.getPagination(pageDto.getPageNum(),
				empService.getEmployeeCnt(pageDto.getType(), pageDto.getKeyword()));

		List<Map<String, Object>> searchList = empService.getEmployeeList(pagination.getBeginRow(),
				pagination.getRowPerPage(), pageDto.getType(), pageDto.getKeyword());

		pagination.addModelAttributes(model, pagination);
		model.addAttribute("empList", searchList);
		model.addAttribute("type", pageDto.getType());
		model.addAttribute("keyword", pageDto.getKeyword());

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
	 * 데이터베이스에서 해당 id를 검색하여 직원 id의 중복 여부를 확인합니다. 입력한 id가 데이터베이스에 존재하지 않는다면 0을 리턴합니다.
	 * 
	 * @param employeeId 입력한 직원id
	 * @return 입력한 id가 데이터베이스에 존재하지 않는다면 0, 이미 존재한다면 1
	 */
	@ResponseBody
	@PostMapping("/checkExistingId")
	public int checkExistingId(@RequestParam String employeeId) {
		return empService.getResultOfIdCheck(employeeId);
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
	 * 성공적으로 직원 정보를 추가했을 경우 직원 목록 페이지로 리다이렉트합니다. 유효성 검사 실패로 인해 추가가 중단된 경우 다시 직원 추가
	 * 페이지로 이동합니다.
	 * 
	 * @param addEmpDto 직원을 추가하기 위해 필요한 데이터를 전송하기 위한 객체
	 * @return 직원 목록 페이지로 리다이렉트
	 */
	@Auth(AUTHORITY = Authority.HEAD_EMP_ONLY)
	@PostMapping("/add")
	public String addEmp(@Validated @ModelAttribute AddEmpDto addEmpDto, BindingResult bindingResult,
			HttpSession session, Model model) {

		if (bindingResult.hasErrors()) {
			return "headoffice/addEmp";
		}

		String path = session.getServletContext().getRealPath("/upload/emp");
		empService.addEmployee(addEmpDto, path);

		return "redirect:/headoffice/emp";
	}

	/**
	 * 직원의 상세 정보 페이지로 이동합니다.
	 *
	 * @param employeeId 조회할 직원의 ID
	 * @return 직원의 상세 정보 페이
	 */
	@Auth(AUTHORITY = Authority.HEAD_EMP_ONLY)
	@GetMapping("/{employeeId}")
	public String getEmpOne(Model model, @PathVariable String employeeId) {

		Map<String, Object> employeeOne = empService.getEmployeeOne(employeeId);
		if (employeeOne == null) {
			throw new EmpNotFoundException(String.format("직원 ID[%s]를 찾지 못하였습니다.", employeeId));
		}

		model.addAttribute("empOne", employeeOne);

		return "headoffice/empOne";
	}
}
