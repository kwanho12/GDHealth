package com.tree.gdhealth.headoffice.customer;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tree.gdhealth.utils.auth.Auth;
import com.tree.gdhealth.utils.auth.Authority;
import com.tree.gdhealth.utils.pagination.HeadofficePagination;

import lombok.RequiredArgsConstructor;

/**
 * @author 진관호
 */
@RequestMapping("/headoffice/customer")
@RequiredArgsConstructor
@Controller
public class CustomerController {

	private final CustomerService customerService;

	/**
	 * 전체 회원 목록을 나타내는 페이지로 이동합니다.
	 * 
	 * @return 회원 목록 페이지
	 */
	@Auth(AUTHORITY = Authority.HEAD_EMP_ONLY)
	@GetMapping
	public String getCustomerList() {

		return "headoffice/customerList";
	}

	/**
	 * 페이지네이션 후의 회원 목록 영역을 리턴합니다.
	 * 
	 * @param pageNum 이동할 페이지 번호
	 * @return 페이지네이션 후의 회원 목록
	 * @apiNote 페이지 전체가 아닌 회원의 목록을 나타내는 영역만 리턴합니다.
	 */
	@GetMapping("/pagination")
	public String getPagination(Model model, int pageNum) {

		int customerCnt = customerService.getCustomerCnt();
		HeadofficePagination pagination = customerService.getPagination(pageNum, customerCnt);

		List<Map<String, Object>> customerList = customerService.getCustomerList(pagination.getBeginRow(),
				pagination.getRowPerPage());

		pagination.addModelAttributes(model, pagination);
		model.addAttribute("customerList", customerList);

		return "headoffice/fragment/customerList";
	}

	/**
	 * 검색 결과가 반영된 페이지네이션 후의 회원 목록 영역을 리턴합니다.
	 * 
	 * @param type    검색할 keyword의 속성(id,active...)
	 * @param keyword 검색 내용
	 * @param pageNum 이동할 페이지 번호
	 * @return 페이지네이션 후의 회원 목록
	 * @apiNote 페이지 전체가 아닌 회원의 목록을 나타내는 영역만 리턴합니다.
	 */
	@GetMapping("/searchPagination")
	public String getPagination(Model model, String type, String keyword, int pageNum) {

		int searchCnt = customerService.getCustomerCnt(type, keyword);
		HeadofficePagination pagination = customerService.getPagination(pageNum, searchCnt);

		List<Map<String, Object>> searchList = customerService.getCustomerList(pagination.getBeginRow(),
				pagination.getRowPerPage(), type, keyword);

		pagination.addModelAttributes(model, pagination);
		model.addAttribute("customerList", searchList);
		model.addAttribute("type", type);
		model.addAttribute("keyword", keyword);

		return "headoffice/fragment/searchCustomerList";
	}

}
