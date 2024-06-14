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
import com.tree.gdhealth.dto.AddProgramServiceDto;
import com.tree.gdhealth.dto.PageDto;
import com.tree.gdhealth.dto.UpdateProgramDto;
import com.tree.gdhealth.dto.UpdateProgramServiceDto;
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
	 * @param pageDto 페이지네이션과 관련한 데이터를 전송하기 위한 객체
	 * @return 페이지네이션 후의 프로그램 목록
	 * @apiNote 페이지 전체가 아닌 프로그램의 목록을 나타내는 영역만 리턴합니다.
	 */
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

	/**
	 * 검색 결과가 반영된 페이지네이션 후의 프로그램 목록 영역을 리턴합니다.
	 * 
	 * @param pageDto 페이지네이션과 관련한 데이터를 전송하기 위한 객체
	 * @return 페이지네이션 후의 프로그램 목록
	 * @apiNote 페이지 전체가 아닌 프로그램의 목록을 나타내는 영역만 리턴합니다.
	 */
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

	/**
	 * 프로그램 추가 페이지로 이동합니다.
	 * 
	 * @return 프로그램 추가 페이지
	 */
	@Auth(AUTHORITY = Authority.HEAD_EMP_ONLY)
	@GetMapping("/add")
	public String addProgram() {
		return "headoffice/addProgram";
	}

	/**
	 * 데이터베이스에서 해당 날짜들을 검색하여 프로그램 날짜들의 중복 여부를 확인합니다. 입력한 날짜들이 모두 데이터베이스에 존재하지 않는다면
	 * false를 반환합니다.
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
	 * 데이터베이스에서 해당 날짜를 검색하여 프로그램 날짜의 중복 여부를 확인합니다. 입력한 날짜가 데이터베이스에 존재하지 않는다면 false를
	 * 반환합니다.
	 * 
	 * @param programDate 프로그램 날짜
	 * @return 입력한 날짜가 이미 존재한다면 false, 존재하지 않는다면 true
	 */
	@ResponseBody
	@PostMapping("/checkDateOne")
	public boolean checkDateOne(@RequestParam String programDate) {
		return programService.getResultOfDateOneCheck(programDate);
	}

	/**
	 * 성공적으로 프로그램을 추가했을 경우 프로그램 목록 페이지로 리다이렉트합니다. 유효성 검사 실패로 인해 추가가 중단된 경우 다시 프로그램
	 * 추가 페이지로 이동합니다.
	 * 
	 * @param addProgramDto 프로그램을 추가하기 위해 필요한 데이터를 전송하기 위한 객체
	 * @param empInfo       로그인한 직원의 정보를 담은 LoginEmployee 객체
	 * @return 프로그램 목록 페이지로 리다이렉트
	 */
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
		
		AddProgramServiceDto serviceDto = toAddProgramServiceDto(addProgramDto);
		programService.addProgram(serviceDto, path);

		return "redirect:/headoffice/program";
	}

	/**
	 * 프로그램의 상세 정보 페이지로 이동합니다.
	 * 
	 * @param programNo   조회할 프로그램의 번호
	 * @param programDate 조회할 프로그램의 날짜
	 * @return 프로그램 상세 정보 페이지
	 */
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

	/**
	 * 프로그램의 업데이트 페이지로 이동합니다.
	 * 
	 * @param programNo   업데이트할 프로그램의 번호
	 * @param programDate 업데이트할 프로그램의 날짜
	 * @return 프로그램 업데이트 페이지
	 */
	@Auth(AUTHORITY = Authority.HEAD_EMP_ONLY)
	@GetMapping("/update/{programNo}/{programDate}")
	public String modifyProgram(Model model, @PathVariable Integer programNo, @PathVariable String programDate) {
		model.addAttribute("programOne", programService.getProgramOne(programNo, programDate));
		return "headoffice/updateProgram";
	}

	/**
	 * 프로그램 업데이트를 성공했을 경우 프로그램 상세 페이지로 리다이렉트합니다. 유효성 검사 실패로 인해 업데이트가 중단된 경우 다시 프로그램
	 * 업데이트 페이지로 리다이렉트합니다.
	 * 
	 * @param updateProgramDto 프로그램을 수정하기 위해 필요한 데이터를 전송하기 위한 객체
	 * @return 프로그램 상세 정보 페이지로 리다이렉트
	 */
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
		
		UpdateProgramServiceDto serviceDto = toUpdateProgramServiceDto(updateProgramDto);
		
		programService.modifyProgram(serviceDto, newPath, oldPath);

		return "redirect:/headoffice/program/{programNo}/{programDate}";
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
	public String deactivateProgram(@PathVariable Integer programNo, @PathVariable String programDate) {
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
	public String activateProgram(@PathVariable Integer programNo, @PathVariable String programDate) {
		programService.modifyActivation(programNo);
		return "redirect:/headoffice/program/programOne/{programNo}/{programDate}";
	}
	
	private UpdateProgramServiceDto toUpdateProgramServiceDto(UpdateProgramDto dto) {
		UpdateProgramServiceDto serviceDto = new UpdateProgramServiceDto();
		serviceDto.setOriginDate(dto.getOriginDate());
		serviceDto.setProgramNo(dto.getProgramNo());
		serviceDto.setFilename(dto.getFilename());
		serviceDto.setProgramFile(dto.getProgramFile());
		serviceDto.setProgramMaxCustomer(dto.getProgramMaxCustomer());
		serviceDto.setProgramDate(dto.getProgramDate());
		serviceDto.setProgramName(dto.getProgramName());
		serviceDto.setProgramDetail(dto.getProgramDetail());
		return serviceDto;
	}
	
	private AddProgramServiceDto toAddProgramServiceDto(AddProgramDto dto) {
		AddProgramServiceDto serviceDto = new AddProgramServiceDto();
		serviceDto.setEmployeeNo(dto.getEmployeeNo());
		serviceDto.setProgramName(dto.getProgramName());
		serviceDto.setProgramDetail(dto.getProgramDetail());
		serviceDto.setProgramMaxCustomer(dto.getProgramMaxCustomer());
		serviceDto.setProgramDates(dto.getProgramDates());
		serviceDto.setProgramFile(dto.getProgramFile());
		return serviceDto;
	}
	
}
