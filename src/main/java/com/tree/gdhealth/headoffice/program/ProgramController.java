package com.tree.gdhealth.headoffice.program;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tree.gdhealth.dto.Program;
import com.tree.gdhealth.dto.ProgramDate;
import com.tree.gdhealth.dto.ProgramImg;
import com.tree.gdhealth.employee.login.LoginEmployee;
import com.tree.gdhealth.utils.auth.Auth;
import com.tree.gdhealth.utils.auth.Authority;
import com.tree.gdhealth.utils.customvalidation.group.DateGroup;
import com.tree.gdhealth.utils.customvalidation.group.DatesGroup;
import com.tree.gdhealth.utils.pagination.HeadofficePagination;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

/**
 * @author 진관호
 */
@RequestMapping("/headoffice/program")
@RequiredArgsConstructor
@Controller
public class ProgramController {

	private final ProgramService programService;

	/**
	 * 전체 프로그램 목록을 나타내는 페이지로 이동합니다.
	 * 
	 * @return 프로그램 목록 페이지
	 */
	@Auth(AUTHORITY = Authority.HEAD_EMP_ONLY)
	@GetMapping
	public String getProgramList() {
		return "headoffice/programList";
	}

	/**
	 * 페이지네이션 후의 프로그램 목록 영역을 리턴합니다.
	 * 
	 * @param pageNum 이동할 페이지 번호
	 * @return 페이지네이션 후의 프로그램 목록
	 * @apiNote 페이지 전체가 아닌 프로그램의 목록을 나타내는 영역만 리턴합니다.
	 */
	@GetMapping("/pagination")
	public String getPagination(Model model, int pageNum) {

		int programCnt = programService.getProgramCnt();
		HeadofficePagination pagination = programService.getPagination(pageNum, programCnt);
		
		List<Map<String, Object>> programList = programService.getProgramList(pagination.getBeginRow(),
				pagination.getRowPerPage());

		pagination.addModelAttributes(model, pagination);
		model.addAttribute("programList", programList);

		return "headoffice/fragment/programList";
	}
	
	/**
	 * 검색 결과가 반영된 페이지네이션 후의 프로그램 목록 영역을 리턴합니다.
	 * 
	 * @param type    검색할 keyword의 속성(programName,programDetail...)
	 * @param keyword 검색 내용
	 * @param pageNum 이동할 페이지 번호
	 * @return 페이지네이션 후의 프로그램 목록
	 * @apiNote 페이지 전체가 아닌 프로그램의 목록을 나타내는 영역만 리턴합니다.
	 */
	@GetMapping("/searchPagination")
	public String getPagination(Model model, String type, String keyword, int pageNum) {

		int searchCnt = programService.getProgramCnt(type, keyword);
		HeadofficePagination pagination = programService.getPagination(pageNum, searchCnt);

		List<Map<String, Object>> searchList = programService.getProgramList(pagination.getBeginRow(),
				pagination.getRowPerPage(), type, keyword);

		pagination.addModelAttributes(model, pagination);
		model.addAttribute("programList", searchList);
		model.addAttribute("type", type);
		model.addAttribute("keyword", keyword);

		return "headoffice/fragment/searchProgramList";
	}

	/**
	 * 프로그램 추가 페이지로 이동합니다.
	 * 
	 * @return 프로그램 추가 페이지
	 */
	@Auth(AUTHORITY = Authority.HEAD_EMP_ONLY)
	@GetMapping("/addProgram")
	public String addProgram() {
		return "headoffice/addProgram";
	}

	/**
	 * 데이터베이스에서 해당 날짜들을 검색하여 프로그램 날짜들의 중복 여부를 확인합니다. 
	 * 입력한 날짜들이 모두 데이터베이스에 존재하지 않는다면 false를 반환합니다.
	 * 
	 * @param programDates 프로그램 날짜 목록
	 * @return 입력한 날짜들 중 최소 1개가 이미 존재한다면 true, 존재하지 않는다면 false
	 */
	@ResponseBody
	@PostMapping("/checkDates")
	public boolean checkDates(@RequestBody List<String> programDates) {
		return programService.getResultOfDatesCheck(programDates);
	}

	/**
	 * 데이터베이스에서 해당 날짜를 검색하여 프로그램 날짜의 중복 여부를 확인합니다. 
	 * 입력한 날짜가 데이터베이스에 존재하지 않는다면 false를 반환합니다.
	 * 
	 * @param programDate 프로그램 날짜
	 * @return 입력한 날짜가 이미 존재한다면 false, 존재하지 않는다면 true
	 */
	@ResponseBody
	@PostMapping("/checkDateOne")
	public boolean checkDateOne(String programDate) {
		return programService.getResultOfDateOneCheck(programDate);
	}

	/**
	 * 성공적으로 프로그램을 추가했을 경우 프로그램 목록 페이지로 리다이렉트합니다.
	 * 유효성 검사 실패로 인해 추가가 중단된 경우 다시 프로그램 추가 페이지로 이동합니다.
	 * 
	 * @param program        추가할 프로그램의 정보를 담은 Program 객체
	 * @param bindingResult1 program 매개변수에 대한 유효성 검사 결과를 담은 BindingResult 객체
	 * @param programDate    추가할 프로그램의 날짜 정보를 담은 ProgramDate 객체
	 * @param bindingResult2 programDate 매개변수에 대한 유효성 검사 결과를 담은 BindingResult 객체
	 * @param programImg     추가할 프로그램의 이미지 정보를 담은 ProgramImg 객체
	 * @param bindingResult3 programImg 매개변수에 대한 유효성 검사 결과를 담은 BindingResult 객체
	 * @param empInfo        로그인한 직원의 정보를 담은 LoginEmployee 객체
	 * @return 프로그램 목록 페이지로 리다이렉트
	 */
	@Auth(AUTHORITY = Authority.HEAD_EMP_ONLY)
	@PostMapping("/addProgram")
	public String addProgram(@Validated Program program, BindingResult bindingResult1,
			@Validated(DatesGroup.class) ProgramDate programDate, BindingResult bindingResult2,
			@Validated ProgramImg programImg, BindingResult bindingResult3, HttpSession session,
			@SessionAttribute(name = "loginEmployee") LoginEmployee empInfo) {

		if (bindingResult1.hasErrors()) {
			return "headoffice/addProgram";
		} else if (bindingResult2.hasErrors()) {
			return "headoffice/addProgram";
		} else if (bindingResult3.hasErrors()) {
			return "headoffice/addProgram";
		}

		int employeeNo = empInfo.getEmployeeNo();
		program.setEmployeeNo(employeeNo);

		String path = session.getServletContext().getRealPath("/upload/program");
		programService.addProgram(program, programDate, programImg, path);

		return "redirect:/headoffice/program";
	}

	/**
	 * 특정 프로그램의 상세 정보 페이지로 이동합니다.
	 * 
	 * @param programNo   조회할 프로그램의 번호
	 * @param programDate 조회할 프로그램의 날짜
	 * @return 프로그램 상세 정보 페이지
	 */
	@Auth(AUTHORITY = Authority.HEAD_EMP_ONLY)
	@GetMapping("/programOne/{programNo}/{programDate}")
	public String getProgramOne(Model model, @PathVariable int programNo, @PathVariable String programDate) {

		Map<String, Object> programOne = programService.getProgramOne(programNo, programDate);
		model.addAttribute("programOne", programOne);

		return "headoffice/programOne";
	}

	/**
	 * 특정 프로그램의 업데이트 페이지로 이동합니다.
	 * 
	 * @param programNo   업데이트할 프로그램의 번호
	 * @param programDate 업데이트할 프로그램의 날짜
	 * @return 프로그램 업데이트 페이지
	 */
	@Auth(AUTHORITY = Authority.HEAD_EMP_ONLY)
	@GetMapping("/update/{programNo}/{programDate}")
	public String modifyProgram(Model model, @PathVariable int programNo, @PathVariable String programDate) {

		Map<String, Object> programOne = programService.getProgramOne(programNo, programDate);
		model.addAttribute("programOne", programOne);

		return "headoffice/updateProgram";
	}

	/**
	 * 프로그램 업데이트를 성공했을 경우 프로그램 상세 페이지로 리다이렉트합니다. 
	 * 유효성 검사 실패로 인해 업데이트가 중단된 경우 다시 프로그램 업데이트 페이지로 리다이렉트합니다.
	 * 
	 * @param program        업데이트할 프로그램의 정보를 담은 Program 객체
	 * @param bindingResult1 program 매개변수에 대한 유효성 검사 결과를 담은 BindingResult 객체
	 * @param programDate    업데이트할 프로그램의 날짜 정보를 담은 ProgramDate 객체
	 * @param bindingResult2 programDate 매개변수에 대한 유효성 검사 결과를 담은 BindingResult 객체
	 * @param programImg     업데이트할 프로그램의 이미지 정보를 담은 ProgramImg 객체
	 * @return 프로그램 상세 정보 페이지로 리다이렉트
	 */
	@Auth(AUTHORITY = Authority.HEAD_EMP_ONLY)
	@PostMapping("/update")
	public String modifyProgram(@Validated Program program, BindingResult bindingResult1,
			@Validated(DateGroup.class) ProgramDate programDate, BindingResult bindingResult2, ProgramImg programImg,
			HttpSession session, RedirectAttributes redirectAttributes) {

		int programNo = program.getProgramNo();
		redirectAttributes.addAttribute("programNo", programNo);

		String originDate = programDate.getOriginDate();

		if (bindingResult1.hasErrors()) {
			redirectAttributes.addAttribute("originDate", originDate);
			return "redirect:/headoffice/program/update/{programNo}/{originDate}";
		} else if (bindingResult2.hasErrors()) {
			redirectAttributes.addAttribute("originDate", originDate);
			return "redirect:/headoffice/program/update/{programNo}/{originDate}";
		}

		String oldPath = session.getServletContext().getRealPath("/upload/program/" + programImg.getFilename());
		String newPath = session.getServletContext().getRealPath("/upload/program");

		programService.modifyProgram(program, programDate, programImg, newPath, oldPath);

		String date = programDate.getProgramDate();
		redirectAttributes.addAttribute("programDate", date);

		return "redirect:/headoffice/program/programOne/{programNo}/{programDate}";
	}

	/**
	 * 프로그램을 비활성화하고 프로그램 상세 정보 페이지로 리다이렉트합니다.
	 * 
	 * @param programNo   비활성화할 프로그램 번호
	 * @param programDate 비활성화할 프로그램 날짜
	 * @return 프로그램 상세 정보 페이지로 리다이렉트
	 */
	@Auth(AUTHORITY = Authority.HEAD_EMP_ONLY)
	@GetMapping("/deactivate/{programNo}/{programDate}")
	public String deactivateProgram(@PathVariable int programNo, @PathVariable String programDate) {
		programService.modifyDeactivation(programNo);
		return "redirect:/headoffice/program/programOne/{programNo}/{programDate}";
	}

	/**
	 * 프로그램을 활성화하고 프로그램 상세 정보 페이지로 리다이렉트합니다.
	 * 
	 * @param programNo   활성화할 프로그램 번호
	 * @param programDate 활성화할 프로그램 날짜
	 * @return 프로그램 상세 정보 페이지로 리다이렉트
	 */
	@Auth(AUTHORITY = Authority.HEAD_EMP_ONLY)
	@GetMapping("/activate/{programNo}/{programDate}")
	public String activateProgram(@PathVariable int programNo, @PathVariable String programDate) {
		programService.modifyActivation(programNo);
		return "redirect:/headoffice/program/programOne/{programNo}/{programDate}";
	}

}
