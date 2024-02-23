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
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/headoffice/customer")
@RequiredArgsConstructor
@Controller
public class CustomerController {

	private final CustomerService customerService;

	@Auth(AUTHORITY = Authority.HEAD_EMP_ONLY)
	@GetMapping
	public String getCustomerList() {

		return "headoffice/customerList";
	}

	@GetMapping("/pagination")
	public String getPagination(Model model, int page) {

		// 전체 고객 수
		int customerCnt = customerService.getCustomerCnt();
		// 디버깅
		log.debug("전체 고객 수 : " + customerCnt);

		// 페이징
		HeadofficePagination pagination = HeadofficePagination.builder().pageNumCnt(10) // 한번에 표시할 페이징 번호의 갯수
				.rowPerPage(8) // 한 페이지에 나타낼 row 수
				.currentPage(page) // 현재 페이지
				.cnt(customerCnt) // 전체 row 수
				.build();
		pagination.calculation();

		List<Map<String, Object>> customerList = customerService.getCustomerList(pagination.getBeginRow(),
				pagination.getRowPerPage());
		model.addAttribute("customerList", customerList);

		// 페이징(model 추가)
		pagination.paginationAttributes(model, pagination, page);

		return "headoffice/fragment/customerList";
	}

	@GetMapping("/searchPagination")
	public String getSearchPagination(Model model, String type, String keyword, int page) {

		// 검색 결과 개수
		int searchCnt = customerService.getSearchCnt(type, keyword);
		// 디버깅
		log.debug("검색 결과 개수(searchPagination) : " + searchCnt);

		HeadofficePagination pagination = HeadofficePagination.builder().pageNumCnt(10) // 한번에 표시할 페이징 번호의 갯수
				.rowPerPage(8) // 한 페이지에 나타낼 row 수
				.currentPage(page) // 현재 페이지
				.cnt(searchCnt) // 전체 row 수
				.build();
		pagination.calculation();

		List<Map<String, Object>> searchList = customerService.getSearchList(pagination.getBeginRow(),
				pagination.getRowPerPage(), type, keyword);
		model.addAttribute("customerList", searchList);

		// 페이징(model 추가)
		pagination.paginationAttributes(model, pagination, page);

		// search parameter 추가
		model.addAttribute("type", type);
		model.addAttribute("keyword", keyword);

		return "headoffice/fragment/searchCustomerList";

	}

}
