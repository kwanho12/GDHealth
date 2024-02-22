package com.tree.gdhealth.headoffice.sportsEquipment;

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
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tree.gdhealth.employee.login.LoginEmployee;
import com.tree.gdhealth.utils.Paging;
import com.tree.gdhealth.utils.auth.Auth;
import com.tree.gdhealth.utils.auth.Authority;
import com.tree.gdhealth.vo.SportsEquipment;
import com.tree.gdhealth.vo.SportsEquipmentImg;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/headoffice/equipment")
@RequiredArgsConstructor
@Controller
public class EquipmentController {

	// DI
	private final EquipmentService equipmentService;

	@Auth(AUTHORITY = Authority.HEAD_EMP_ONLY)
	@GetMapping
	public String equipment() {

		return "headoffice/equipmentList";
	}

	@GetMapping("/paging")
	public String paging(Model model, int page) {

		// 전체 물품 수
		int equipmentCnt = equipmentService.getEquipmentCnt();
		// 디버깅
		log.debug("전체 물품 수 : " + equipmentCnt);

		// 페이징
		Paging paging = Paging.builder().pageNumCnt(10) // 한번에 표시할 페이징 번호의 갯수
				.rowPerPage(8) // 한 페이지에 나타낼 row 수
				.currentPage(page) // 현재 페이지
				.cnt(equipmentCnt) // 전체 row 수
				.build();
		paging.calculation();

		List<Map<String, Object>> equipmentList = equipmentService.getEquipmentList(paging.getBeginRow(),
				paging.getRowPerPage());
		model.addAttribute("equipmentList", equipmentList);

		// 페이징(model 추가)
		paging.pagingAttributes(model, paging, page);

		return "headoffice/fragment/equipment";
	}

	@GetMapping("/searchPaging")
	public String searchPaging(Model model, String type, String keyword, int page) {

		// 검색 결과 개수
		int searchCnt = equipmentService.getSearchCnt(type, keyword);
		// 디버깅
		log.debug("검색 결과 개수(searchPaging) " + searchCnt);

		// 페이징
		Paging paging = Paging.builder().pageNumCnt(10) // 한번에 표시할 페이징 번호의 갯수
				.rowPerPage(8) // 한 페이지에 나타낼 row 수
				.currentPage(page) // 현재 페이지
				.cnt(searchCnt) // 전체 row 수
				.build();
		paging.calculation();

		List<Map<String, Object>> searchList = equipmentService.getSearchList(paging.getBeginRow(),
				paging.getRowPerPage(), type, keyword);
		model.addAttribute("equipmentList", searchList);

		// 페이징(model 추가)
		paging.pagingAttributes(model, paging, page);

		// search parameter 추가
		model.addAttribute("type", type);
		model.addAttribute("keyword", keyword);

		return "headoffice/fragment/searchEquipment";

	}

	@Auth(AUTHORITY = Authority.HEAD_EMP_ONLY)
	@GetMapping("/addEquipment")
	public String addEquipment() {

		return "headoffice/addEquipment";
	}

	@Auth(AUTHORITY = Authority.HEAD_EMP_ONLY)
	@PostMapping("/addEquipment")
	public String addEquipment(@Validated SportsEquipment sportsEquipment, BindingResult bindingResult1,
			@Validated SportsEquipmentImg sportsEquipmentImg, BindingResult bindingResult2, HttpSession session,
			@SessionAttribute(name = "loginEmployee") LoginEmployee empInfo) {

		// SportsEquipment의 유효성 검증 실패시 처리
		if (bindingResult1.hasErrors()) {

			// 에러 메시지 출력
			for (ObjectError error : bindingResult1.getAllErrors()) {
				log.debug(error.getDefaultMessage());
			}

			return "headoffice/addEquipment";
		}

		// SportsEquipmentImg의 유효성 검증 실패시 처리
		if (bindingResult2.hasErrors()) {

			// 에러 메시지 출력
			for (ObjectError error : bindingResult2.getAllErrors()) {
				log.debug(error.getDefaultMessage());
			}

			return "headoffice/addEquipment";
		}

		String path = session.getServletContext().getRealPath("/upload/equipment");
		// 디버깅
		log.debug("저장 경로 : " + path);

		int writerNo = empInfo.getEmployeeNo();
		sportsEquipment.setEmployeeNo(writerNo);

		equipmentService.insertEquipment(sportsEquipment, sportsEquipmentImg, path);

		return "redirect:/headoffice/equipment";
	}

	@Auth(AUTHORITY = Authority.HEAD_EMP_ONLY)
	@GetMapping("/update/{equipmentNo}")
	public String update(@PathVariable int equipmentNo, Model model) {

		Map<String, Object> equipmentOne = equipmentService.getEquipmentOne(equipmentNo);
		model.addAttribute("equipmentOne", equipmentOne);

		return "headoffice/updateEquipment";
	}

	@Auth(AUTHORITY = Authority.HEAD_EMP_ONLY)
	@PostMapping("/update")
	public String update(@Validated SportsEquipment equipment, BindingResult bindingResult1,
			SportsEquipmentImg sportsEquipmentImg, HttpSession session, RedirectAttributes redirectAttributes) {

		int equipmentNo = equipment.getSportsEquipmentNo();

		if (bindingResult1.hasErrors()) {

			redirectAttributes.addAttribute("equipmentNo", equipmentNo);

			// 에러 메시지 출력
			for (ObjectError error : bindingResult1.getAllErrors()) {
				log.debug(error.getDefaultMessage());
			}

			return "redirect:/headoffice/equipment/update/{equipmentNo}";
		}

		String oldPath = session.getServletContext()
				.getRealPath("/upload/equipment/" + sportsEquipmentImg.getSportsEquipmentImgFileName());
		log.debug("oldPath : " + oldPath);
		String newPath = session.getServletContext().getRealPath("/upload/equipment");

		equipmentService.updateEquipment(equipment, sportsEquipmentImg, newPath, oldPath);

		return "redirect:/headoffice/equipment";
	}

	@Auth(AUTHORITY = Authority.HEAD_EMP_ONLY)
	@ResponseBody
	@GetMapping("/deactive")
	public int deactive(int equipmentNo) {

		int result = equipmentService.deactiveEquipment(equipmentNo);
		// 디버깅
		log.debug("물품 비활성화(성공:1,실패:0) : " + result);

		return result;
	}

	@ResponseBody
	@GetMapping("/active")
	public int active(int equipmentNo) {

		int result = equipmentService.activeEquipment(equipmentNo);
		// 디버깅
		log.debug("물품 활성화(성공:1,실패:0) : " + result);

		return result;
	}

}
