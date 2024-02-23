package com.tree.gdhealth.headoffice.sportsEquipment;

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
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tree.gdhealth.dto.SportsEquipment;
import com.tree.gdhealth.dto.SportsEquipmentImg;
import com.tree.gdhealth.employee.login.LoginEmployee;
import com.tree.gdhealth.utils.auth.Auth;
import com.tree.gdhealth.utils.auth.Authority;
import com.tree.gdhealth.utils.pagination.HeadofficePagination;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

/**
 * @author 진관호
 */
@RequestMapping("/headoffice/equipment")
@RequiredArgsConstructor
@Controller
public class EquipmentController {

	private final EquipmentService equipmentService;

	/**
	 * 전체 물품 목록을 나타내는 페이지로 이동합니다.
	 * 
	 * @return 물품 목록 페이지
	 */
	@Auth(AUTHORITY = Authority.HEAD_EMP_ONLY)
	@GetMapping
	public String getEquipmentList() {
		return "headoffice/equipmentList";
	}

	/**
	 * 페이지네이션 후의 물품 목록 영역을 리턴합니다.
	 * 
	 * @param pageNum 이동할 페이지 번호
	 * @return 페이지네이션 후의 물품 목록
	 * @apiNote 페이지 전체가 아닌 물품의 목록을 나타내는 영역만 리턴합니다.
	 */
	@GetMapping("/pagination")
	public String getPagination(Model model, int pageNum) {

		int equipmentCnt = equipmentService.getEquipmentCnt();
		HeadofficePagination pagination = equipmentService.getPagination(pageNum, equipmentCnt);

		List<Map<String, Object>> equipmentList = equipmentService.getEquipmentList(pagination.getBeginRow(),
				pagination.getRowPerPage());

		pagination.addModelAttributes(model, pagination);
		model.addAttribute("equipmentList", equipmentList);

		return "headoffice/fragment/equipmentList";
	}

	/**
	 * 검색 결과가 반영된 페이지네이션 후의 물품 목록 영역을 리턴합니다.
	 * 
	 * @param type    검색할 keyword의 속성(itemName,note...)
	 * @param keyword 검색 내용
	 * @param pageNum 이동할 페이지 번호
	 * @return 페이지네이션 후의 물품 목록
	 * @apiNote 페이지 전체가 아닌 물품의 목록을 나타내는 영역만 리턴합니다.
	 */
	@GetMapping("/searchPagination")
	public String getPagination(Model model, String type, String keyword, int pageNum) {

		int searchCnt = equipmentService.getEquipmentCnt(type, keyword);
		HeadofficePagination pagination = equipmentService.getPagination(pageNum, searchCnt);

		List<Map<String, Object>> searchList = equipmentService.getEquipmentList(pagination.getBeginRow(),
				pagination.getRowPerPage(), type, keyword);

		pagination.addModelAttributes(model, pagination);
		model.addAttribute("equipmentList", searchList);
		model.addAttribute("type", type);
		model.addAttribute("keyword", keyword);

		return "headoffice/fragment/searchEquipmentList";
	}

	/**
	 * 물품 추가 페이지로 이동합니다.
	 * 
	 * @return 물품 추가 페이지
	 */
	@Auth(AUTHORITY = Authority.HEAD_EMP_ONLY)
	@GetMapping("/addEquipment")
	public String addEquipment() {
		return "headoffice/addEquipment";
	}

	/**
	 * 물품을 성공적으로 추가했을 경우 물품 목록 페이지로 이동합니다. 
	 * 유효성 검사 실패로 인해 추가가 중단된 경우 다시 물품 추가 페이지로 이동합니다.
	 * 
	 * @param sportsEquipment    추가할 물품의 정보를 담은 SportsEquipment 객체
	 * @param bindingResult1     sportsEquipment 매개변수에 대한 유효성 검사 결과를 담은 BindingResult 객체
	 * @param sportsEquipmentImg 추가할 물품의 이미지 정보를 담은 SportsEquipmentImg 객체
	 * @param bindingResult2     sportsEquipmentImg 매개변수에 대한 유효성 검사 결과를 담은 BindingResult 객체
	 * @param empInfo            로그인한 직원의 정보를 담은 LoginEmployee 객체
	 * @return
	 */
	@Auth(AUTHORITY = Authority.HEAD_EMP_ONLY)
	@PostMapping("/addEquipment")
	public String addEquipment(@Validated SportsEquipment sportsEquipment, BindingResult bindingResult1,
			@Validated SportsEquipmentImg sportsEquipmentImg, BindingResult bindingResult2, HttpSession session,
			@SessionAttribute(name = "loginEmployee") LoginEmployee empInfo) {

		if (bindingResult1.hasErrors()) {
			return "headoffice/addEquipment";
		} else if (bindingResult2.hasErrors()) {
			return "headoffice/addEquipment";
		}

		String path = session.getServletContext().getRealPath("/upload/equipment");

		int writerNo = empInfo.getEmployeeNo();
		sportsEquipment.setEmployeeNo(writerNo);

		equipmentService.addEquipment(sportsEquipment, sportsEquipmentImg, path);

		return "redirect:/headoffice/equipment";
	}

	/**
	 * 특정 물품의 업데이트 페이지로 이동합니다.
	 * 
	 * @param equipmentNo 업데이트할 물품의 번호
	 * @return 물품 업데이트 페이지
	 */
	@Auth(AUTHORITY = Authority.HEAD_EMP_ONLY)
	@GetMapping("/update/{equipmentNo}")
	public String modifyEquipment(@PathVariable int equipmentNo, Model model) {

		Map<String, Object> equipmentOne = equipmentService.getEquipmentOne(equipmentNo);
		model.addAttribute("equipmentOne", equipmentOne);

		return "headoffice/updateEquipment";
	}

	/**
	 * 물품 업데이트를 성공했을 경우 물품 목록 페이지로 리다이렉트합니다. 
	 * 유효성 검사 실패로 인해 업데이트가 중단된 경우 다시 물품 업데이트 페이지로 리다이렉트합니다.
	 * 
	 * @param equipment          업데이트할 물품의 정보를 담은 SportsEquipment 객체
	 * @param bindingResult      equipment 매개변수에 대한 유효성 검사 결과를 담은 BindingResult 객체
	 * @param sportsEquipmentImg 업데이트할 물품의 이미지 정보를 담은 SportsEquipmentImg 객체
	 * @return 물품 목록 페이지로 리다이렉트
	 */
	@Auth(AUTHORITY = Authority.HEAD_EMP_ONLY)
	@PostMapping("/update")
	public String modifyEquipment(@Validated SportsEquipment equipment, BindingResult bindingResult,
			SportsEquipmentImg sportsEquipmentImg, HttpSession session, RedirectAttributes redirectAttributes) {

		int equipmentNo = equipment.getSportsEquipmentNo();

		if (bindingResult.hasErrors()) {
			redirectAttributes.addAttribute("equipmentNo", equipmentNo);
			return "redirect:/headoffice/equipment/update/{equipmentNo}";
		}

		String oldPath = session.getServletContext()
				.getRealPath("/upload/equipment/" + sportsEquipmentImg.getSportsEquipmentImgFileName());
		String newPath = session.getServletContext().getRealPath("/upload/equipment");

		equipmentService.modifyEquipment(equipment, sportsEquipmentImg, newPath, oldPath);

		return "redirect:/headoffice/equipment";
	}

	/**
	 * 물품을 성공적으로 비활성화하면 1을 리턴합니다.
	 * 
	 * @param equipmentNo 비활성화할 물품 번호
	 * @return 비활성화 상태로 정상적으로 변경되었다면 1
	 */
	@Auth(AUTHORITY = Authority.HEAD_EMP_ONLY)
	@ResponseBody
	@GetMapping("/deactivate")
	public int deactivateEquipment(int equipmentNo) {
		return equipmentService.modifyDeactivation(equipmentNo);
	}

	/**
	 * 물품을 성공적으로 활성화하면 1을 리턴합니다.
	 * 
	 * @param equipmentNo 활성화할 물품 번호
	 * @return 활성화 상태로 정상적으로 변경되었다면 1
	 */
	@ResponseBody
	@GetMapping("/activate")
	public int activateEquipment(int equipmentNo) {
		return equipmentService.modifyActivation(equipmentNo);
	}

}
