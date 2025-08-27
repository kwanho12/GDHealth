package com.tree.gdhealth.headoffice.sportsEquipment;

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
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tree.gdhealth.dto.AddSportsEquipmentDto;
import com.tree.gdhealth.dto.PageDto;
import com.tree.gdhealth.dto.UpdateSportsEquipmentDto;
import com.tree.gdhealth.employee.login.LoginEmployee;
import com.tree.gdhealth.utils.auth.Auth;
import com.tree.gdhealth.utils.auth.Authority;
import com.tree.gdhealth.utils.pagination.HeadofficePagination;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 진관호
 */
@Slf4j
@RequestMapping("/headoffice/equipment")
@RequiredArgsConstructor
@Controller
public class EquipmentController {

	private final EquipmentService equipmentService;

	@Auth(AUTHORITY = Authority.HEAD_EMP_ONLY)
	@GetMapping
	public String getEquipmentList() {
		return "headoffice/equipmentList";
	}

	@GetMapping("/pagination")
	public String getPagination(Model model, @ModelAttribute PageDto pageDto) {

		HeadofficePagination pagination = equipmentService.getPagination(pageDto.getPageNum(),
				equipmentService.getEquipmentCnt());

		List<Map<String, Object>> equipmentList = equipmentService.getEquipmentList(pagination.getBeginRow(),
				pagination.getRowPerPage());

		model.addAttribute("lastPage", pagination.getLastPageNum());
		model.addAttribute("currentPage", pagination.getCurrentPageNum());
		model.addAttribute("startPageNum", pagination.getStartPageNum());
		model.addAttribute("endPageNum", pagination.getEndPageNum());
		model.addAttribute("prev", pagination.isPrev());
		model.addAttribute("next", pagination.isNext());
		
		model.addAttribute("equipmentList", equipmentList);

		return "headoffice/fragment/equipmentList";
	}

	@GetMapping("/searchPagination")
	public String getSearchPagination(Model model, @ModelAttribute PageDto pageDto) {

		HeadofficePagination pagination = equipmentService.getPagination(pageDto.getPageNum(),
				equipmentService.getEquipmentCnt(pageDto.getType(), pageDto.getKeyword()));

		List<Map<String, Object>> searchList = equipmentService.getEquipmentList(pagination.getBeginRow(),
				pagination.getRowPerPage(), pageDto.getType(), pageDto.getKeyword());

		model.addAttribute("lastPage", pagination.getLastPageNum());
		model.addAttribute("currentPage", pagination.getCurrentPageNum());
		model.addAttribute("startPageNum", pagination.getStartPageNum());
		model.addAttribute("endPageNum", pagination.getEndPageNum());
		model.addAttribute("prev", pagination.isPrev());
		model.addAttribute("next", pagination.isNext());
		
		model.addAttribute("equipmentList", searchList);
		model.addAttribute("type", pageDto.getType());
		model.addAttribute("keyword", pageDto.getKeyword());

		return "headoffice/fragment/searchEquipmentList";
	}

	@Auth(AUTHORITY = Authority.HEAD_EMP_ONLY)
	@GetMapping("/add")
	public String addEquipment() {
		return "headoffice/addEquipment";
	}

	@Auth(AUTHORITY = Authority.HEAD_EMP_ONLY)
	@PostMapping("/add")
	public String addEquipment(@Validated @ModelAttribute AddSportsEquipmentDto addSportsEquipmentDto,
			BindingResult bindingResult, HttpSession session,
			@SessionAttribute(name = "loginEmployee") LoginEmployee empInfo) {

		if (bindingResult.hasErrors()) {
			log.error("errors = {}", bindingResult);
			return "headoffice/addEquipment";
		}

		addSportsEquipmentDto.setEmployeeNo(empInfo.getEmployeeNo());
		String path = session.getServletContext().getRealPath("/upload/equipment");
		
		equipmentService.addEquipment(addSportsEquipmentDto, path);

		return "redirect:/headoffice/equipment";
	}

	@Auth(AUTHORITY = Authority.HEAD_EMP_ONLY)
	@GetMapping("/update/{equipmentNo}")
	public String modifyEquipment(Model model, @PathVariable Integer equipmentNo) {
		model.addAttribute("equipmentOne", equipmentService.getEquipmentOne(equipmentNo));
		return "headoffice/updateEquipment";
	}

	@Auth(AUTHORITY = Authority.HEAD_EMP_ONLY)
	@PostMapping("/update")
	public String modifyEquipment(@Validated @ModelAttribute UpdateSportsEquipmentDto updateSportsEquipmentDto,
			BindingResult bindingResult, HttpSession session, RedirectAttributes redirectAttributes) {

		if (bindingResult.hasErrors()) {
			log.error("errors = {}", bindingResult);
			redirectAttributes.addAttribute("equipmentNo", updateSportsEquipmentDto.getSportsEquipmentNo());
			return "redirect:/headoffice/equipment/update/{equipmentNo}";
		}

		String oldPath = session.getServletContext()
				.getRealPath("/upload/equipment/" + updateSportsEquipmentDto.getSportsEquipmentImgFileName());
		String newPath = session.getServletContext().getRealPath("/upload/equipment");

		equipmentService.modifyEquipment(updateSportsEquipmentDto, newPath, oldPath);

		return "redirect:/headoffice/equipment";
	}

	@Auth(AUTHORITY = Authority.HEAD_EMP_ONLY)
	@ResponseBody
	@PostMapping("/deactivation")
	public int deactivateEquipment(@RequestParam Integer equipmentNo) {
		return equipmentService.modifyDeactivation(equipmentNo);
	}

	@ResponseBody
	@PostMapping("/activation")
	public int activateEquipment(@RequestParam Integer equipmentNo) {
		return equipmentService.modifyActivation(equipmentNo);
	}
	
}
