package com.tree.gdhealth.headoffice.program;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tree.gdhealth.dto.AddProgramDto;
import com.tree.gdhealth.dto.PageDto;
import com.tree.gdhealth.dto.UpdateProgramDto;
import com.tree.gdhealth.employee.login.LoginEmployee;
import com.tree.gdhealth.utils.auth.Auth;
import com.tree.gdhealth.utils.auth.Authority;
import com.tree.gdhealth.utils.exception.ProgramNotFoundException;
import com.tree.gdhealth.utils.pagination.HeadofficePagination;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 진관호
 */
@Slf4j
@RequestMapping("/headoffice/program")
@RequiredArgsConstructor
@Controller
public class ProgramController {

	private final ProgramService programService;

	@Auth(AUTHORITY = Authority.HEAD_EMP_ONLY)
	@GetMapping
	public String getProgramList() {
		return "headoffice/programList";
	}

	@GetMapping("/pagination")
	public String getPagination(Model model, @ModelAttribute PageDto pageDto) {

		HeadofficePagination pagination = programService.getPagination(pageDto.getPageNum(),
				programService.getProgramCnt());

		List<Map<String, Object>> programList = programService.getProgramList(pagination.getBeginRow(),
				pagination.getRowPerPage());

		model.addAttribute("lastPage", pagination.getLastPageNum());
		model.addAttribute("currentPage", pagination.getCurrentPageNum());
		model.addAttribute("startPageNum", pagination.getStartPageNum());
		model.addAttribute("endPageNum", pagination.getEndPageNum());
		model.addAttribute("prev", pagination.isPrev());
		model.addAttribute("next", pagination.isNext());
		
		model.addAttribute("programList", programList);

		return "headoffice/fragment/programList";
	}

	@GetMapping("/searchPagination")
	public String getSearchPagination(Model model, @ModelAttribute PageDto pageDto) {

		HeadofficePagination pagination = programService.getPagination(pageDto.getPageNum(),
				programService.getProgramCnt(pageDto.getType(), pageDto.getKeyword()));

		List<Map<String, Object>> searchList = programService.getProgramList(pagination.getBeginRow(),
				pagination.getRowPerPage(), pageDto.getType(), pageDto.getKeyword());

		model.addAttribute("lastPage", pagination.getLastPageNum());
		model.addAttribute("currentPage", pagination.getCurrentPageNum());
		model.addAttribute("startPageNum", pagination.getStartPageNum());
		model.addAttribute("endPageNum", pagination.getEndPageNum());
		model.addAttribute("prev", pagination.isPrev());
		model.addAttribute("next", pagination.isNext());
		
		model.addAttribute("programList", searchList);
		model.addAttribute("type", pageDto.getType());
		model.addAttribute("keyword", pageDto.getKeyword());

		return "headoffice/fragment/searchProgramList";
	}

	@Auth(AUTHORITY = Authority.HEAD_EMP_ONLY)
	@GetMapping("/add")
	public String addProgram() {
		return "headoffice/addProgram";
	}

	@ResponseBody
	@PostMapping("/dates")
	public boolean checkDates(@RequestBody List<String> programDates) {
		return programService.getResultOfDatesCheck(programDates);
	}

	@ResponseBody
	@PostMapping("/date")
	public boolean checkDate(@RequestParam String programDate) {
		return programService.getResultOfDateOneCheck(programDate);
	}

	@Auth(AUTHORITY = Authority.HEAD_EMP_ONLY)
	@PostMapping("/add")
	public String addProgram(@Validated @ModelAttribute AddProgramDto addProgramDto, BindingResult bindingResult,
			HttpSession session, @SessionAttribute(name = "loginEmployee") LoginEmployee empInfo) {
		
		if (bindingResult.hasErrors()) {
			log.error("errors = {}", bindingResult);
			return "headoffice/addProgram";
		}
		String path = session.getServletContext().getRealPath("/upload/program");
		addProgramDto.setEmployeeNo(empInfo.getEmployeeNo());
		
		programService.addProgram(addProgramDto, path);

		return "redirect:/headoffice/program";
	}

	@Auth(AUTHORITY = Authority.HEAD_EMP_ONLY)
	@GetMapping("/{programNo}/{programDate}")
	public String getProgramOne(Model model, @PathVariable Integer programNo, @PathVariable String programDate) {

		Map<String, Object> programOne = programService.getProgramOne(programNo, programDate);
		if (programOne == null) {
			throw new ProgramNotFoundException(String.format("프로그램[%d, %s]을 찾지 못하였습니다.", programNo, programDate));
		}
		model.addAttribute("programOne", programOne);

		return "headoffice/programOne";
	}

	@Auth(AUTHORITY = Authority.HEAD_EMP_ONLY)
	@GetMapping("/update/{programNo}/{programDate}")
	public String modifyProgram(Model model, @PathVariable Integer programNo, @PathVariable String programDate) {
		model.addAttribute("programOne", programService.getProgramOne(programNo, programDate));
		return "headoffice/updateProgram";
	}

	@Auth(AUTHORITY = Authority.HEAD_EMP_ONLY)
	@PostMapping("/update")
	public String modifyProgram(@Validated @ModelAttribute UpdateProgramDto updateProgramDto,
			BindingResult bindingResult, HttpSession session, RedirectAttributes redirectAttributes) {

		redirectAttributes.addAttribute("programNo", updateProgramDto.getProgramNo());
		redirectAttributes.addAttribute("programDate", updateProgramDto.getProgramDate());

		if (bindingResult.hasErrors()) {
			log.error("errors = {}", bindingResult);
			redirectAttributes.addAttribute("originDate", updateProgramDto.getOriginDate());
			return "redirect:/headoffice/program/update/{programNo}/{originDate}";
		}

		String oldPath = session.getServletContext().getRealPath("/upload/program/" + updateProgramDto.getFilename());
		String newPath = session.getServletContext().getRealPath("/upload/program");
				
		programService.modifyProgram(updateProgramDto, newPath, oldPath);

		return "redirect:/headoffice/program/{programNo}/{programDate}";
	}

	@Auth(AUTHORITY = Authority.HEAD_EMP_ONLY)
	@GetMapping("/deactivate/{programNo}/{programDate}")
	public String deactivateProgram(@PathVariable Integer programNo, @PathVariable String programDate) {
		programService.modifyDeactivation(programNo);
		return "redirect:/headoffice/program/programOne/{programNo}/{programDate}";
	}

	@Auth(AUTHORITY = Authority.HEAD_EMP_ONLY)
	@GetMapping("/activate/{programNo}/{programDate}")
	public String activateProgram(@PathVariable Integer programNo, @PathVariable String programDate) {
		programService.modifyActivation(programNo);
		return "redirect:/headoffice/program/programOne/{programNo}/{programDate}";
	}
	
}
