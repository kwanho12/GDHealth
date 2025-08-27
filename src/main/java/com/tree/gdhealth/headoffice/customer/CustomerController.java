package com.tree.gdhealth.headoffice.customer;

import java.util.List;
import java.util.Map;

import com.tree.gdhealth.employee.login.LoginEmployee;
import com.tree.gdhealth.utils.exception.UnauthorizedException;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

	@Auth(AUTHORITY = Authority.HEAD_EMP_ONLY)
	@GetMapping
	public String getCustomerList() {
		return "headoffice/customerList";
	}

    @Auth(AUTHORITY = Authority.HEAD_EMP_ONLY)
    @GetMapping("/pagination")
	public String getPagination(Model model, @RequestParam int pageNum, HttpSession session) {
        LoginEmployee loginEmployee = (LoginEmployee) session.getAttribute("loginEmployee");
        if(loginEmployee == null || loginEmployee.getBranchLevel() != 1){
            throw new UnauthorizedException();
        }

		HeadofficePagination pagination = customerService.getPagination(pageNum, customerService.getCustomerCnt());

		List<Map<String, Object>> customerList = customerService.getCustomerList(pagination.getBeginRow(),
				pagination.getRowPerPage());

		model.addAttribute("lastPage", pagination.getLastPageNum());
		model.addAttribute("currentPage", pagination.getCurrentPageNum());
		model.addAttribute("startPageNum", pagination.getStartPageNum());
		model.addAttribute("endPageNum", pagination.getEndPageNum());
		model.addAttribute("prev", pagination.isPrev());
		model.addAttribute("next", pagination.isNext());
		
		model.addAttribute("customerList", customerList);

		return "headoffice/fragment/customerList";
	}

	@GetMapping("/searchPagination")
	public String getPagination(Model model, @RequestParam String type, @RequestParam String keyword,
			@RequestParam int pageNum) {

		HeadofficePagination pagination = customerService.getPagination(pageNum,
				customerService.getCustomerCnt(type, keyword));

		List<Map<String, Object>> searchList = customerService.getCustomerList(pagination.getBeginRow(),
				pagination.getRowPerPage(), type, keyword);

		model.addAttribute("lastPage", pagination.getLastPageNum());
		model.addAttribute("currentPage", pagination.getCurrentPageNum());
		model.addAttribute("startPageNum", pagination.getStartPageNum());
		model.addAttribute("endPageNum", pagination.getEndPageNum());
		model.addAttribute("prev", pagination.isPrev());
		model.addAttribute("next", pagination.isNext());
		
		model.addAttribute("customerList", searchList);
		model.addAttribute("type", type);
		model.addAttribute("keyword", keyword);

		return "headoffice/fragment/searchCustomerList";
	}

}
