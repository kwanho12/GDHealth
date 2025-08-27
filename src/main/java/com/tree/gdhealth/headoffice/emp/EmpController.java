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

import com.tree.gdhealth.dto.AddEmpDto;
import com.tree.gdhealth.dto.PageDto;
import com.tree.gdhealth.utils.auth.Auth;
import com.tree.gdhealth.utils.auth.Authority;
import com.tree.gdhealth.utils.exception.EmpNotFoundException;
import com.tree.gdhealth.utils.pagination.HeadofficePagination;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 진관호
 */
@Slf4j
@RequestMapping("/headoffice/emp")
@RequiredArgsConstructor
@Controller
public class EmpController {

	private final EmpService empService;

	@Auth(AUTHORITY = Authority.HEAD_EMP_ONLY)
	@GetMapping
	public String getEmpList() {
		return "headoffice/empList";
	}

	@GetMapping("/pagination")
	public String getPagination(Model model, @ModelAttribute PageDto pageDto) {

		HeadofficePagination pagination = empService.getPagination(pageDto.getPageNum(), empService.getEmployeeCnt());

		model.addAttribute("lastPage", pagination.getLastPageNum());
		model.addAttribute("currentPage", pagination.getCurrentPageNum());
		model.addAttribute("startPageNum", pagination.getStartPageNum());
		model.addAttribute("endPageNum", pagination.getEndPageNum());
		model.addAttribute("prev", pagination.isPrev());
		model.addAttribute("next", pagination.isNext());
		
		model.addAttribute("empList", empService.getEmployeeList(pagination.getBeginRow(), pagination.getRowPerPage()));

		return "headoffice/fragment/empList";
	}

	@GetMapping("/searchPagination")
	public String getSearchPagination(Model model, @ModelAttribute PageDto pageDto) {

		HeadofficePagination pagination = empService.getPagination(pageDto.getPageNum(),
				empService.getEmployeeCnt(pageDto.getType(), pageDto.getKeyword()));

		List<Map<String, Object>> searchList = empService.getEmployeeList(pagination.getBeginRow(),
				pagination.getRowPerPage(), pageDto.getType(), pageDto.getKeyword());

		model.addAttribute("lastPage", pagination.getLastPageNum());
		model.addAttribute("currentPage", pagination.getCurrentPageNum());
		model.addAttribute("startPageNum", pagination.getStartPageNum());
		model.addAttribute("endPageNum", pagination.getEndPageNum());
		model.addAttribute("prev", pagination.isPrev());
		model.addAttribute("next", pagination.isNext());
		
		model.addAttribute("empList", searchList);
		model.addAttribute("type", pageDto.getType());
		model.addAttribute("keyword", pageDto.getKeyword());

		return "headoffice/fragment/searchEmpList";
	}

	@ResponseBody
	@GetMapping("/branchList")
	public List<String> getBranchList() {
		return empService.getBranchList();
	}

	@ResponseBody
	@PostMapping("/existingId")
	public int checkExistingId(@RequestParam String employeeId) {
		return empService.getResultOfIdCheck(employeeId);
	}

	@Auth(AUTHORITY = Authority.HEAD_EMP_ONLY)
	@GetMapping("/add")
	public String addEmp() {
		return "headoffice/addEmp";
	}

	@Auth(AUTHORITY = Authority.HEAD_EMP_ONLY)
	@PostMapping("/add")
	public String addEmp(@Validated @ModelAttribute AddEmpDto addEmpDto, BindingResult bindingResult,
			HttpSession session, Model model) {

		if (bindingResult.hasErrors()) {
			log.error("errors = {}", bindingResult);
			return "headoffice/addEmp";
		}

		String path = session.getServletContext().getRealPath("/upload/emp");
		
		empService.addEmployee(addEmpDto, path);

		return "redirect:/headoffice/emp";
	}

	@Auth(AUTHORITY = Authority.HEAD_EMP_ONLY)
	@GetMapping("/{employeeId}")
	public String getEmpOne(Model model, @PathVariable String employeeId) {
		
		Map<String, Object> employeeOne = empService.getEmployeeOne(employeeId);
		if (employeeOne == null) {
			throw new EmpNotFoundException(String.format("직원 ID[%s]의 상세 정보를 찾지 못하였습니다.", employeeId));
		}
		model.addAttribute("empOne", employeeOne);
		return "headoffice/empOne";
	}
	
}
