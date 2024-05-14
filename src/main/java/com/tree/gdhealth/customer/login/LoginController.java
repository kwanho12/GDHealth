package com.tree.gdhealth.customer.login;

import java.util.Enumeration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tree.gdhealth.domain.Customer;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {
	@Autowired LoginService loginService;
	
	@GetMapping("/customer/login")
	public String loginPage(HttpSession session) {
		// 로그인 세션 확인
		if(session.getAttribute("customerNo") != null) {
			return "customer/home";
		}
		return "customer/logIn";
	}
	
	@PostMapping("/customer/login")
	public String login(HttpSession session, Customer customer, RedirectAttributes red) {
		int customerNo = 0;
		customerNo = loginService.login(customer);
		System.out.println(customerNo);
		if(customerNo == 0) {
			red.addFlashAttribute("msg", "로그인에 실패했습니다. 아이디와 비밀번호를 확인하세요.");
	        return "redirect:/customer/login";
		}
		red.addFlashAttribute("msg", "로그인 되셨습니다.");
		
//		session.removeAttribute("loginEmployee");

		session.setAttribute("customerNo", customerNo);
		session.setAttribute("customerId", customer.getCustomerId());
		session.setAttribute("userLevel", 0);
		return "redirect:/customer/home";
	}
	
	@GetMapping("/customer/logout")
	public String logout(HttpSession session,RedirectAttributes red) {
		System.out.println("로그아웃");
		String msg = "로그아웃 되셨습니다.";
		red.addFlashAttribute("msg",msg);
		session.invalidate();
		return "redirect:/customer/home";
	}
	
	@GetMapping("/customer/findPw")
	public String findPwPage(HttpSession session) {
		
		return "customer/findPw";
	}
	
	@PostMapping("/customer/findPw")
	public String findPw(Model model,HttpSession session, Customer customer, RedirectAttributes red) {
		int customerNo = 0;
		customerNo = loginService.findPw(customer);
		System.out.println(customerNo);
		
		if(customerNo == 0) {
			red.addFlashAttribute("msg", "존재하지 않는 아이디이거나 생년월일이 잘못 입력 되었습니다.");
	        return "redirect:/customer/findPw";
		}
		model.addAttribute("customerNo", customerNo);
		return "customer/resetPw";
	}
	
	@PostMapping("/customer/resetPw")
	public String findPw(Customer customer, RedirectAttributes red) {
		int customerNo = loginService.resetPw(customer);
		System.out.println(customerNo);
		
		if(customerNo == 0) {
			red.addFlashAttribute("msg", "(ERROR) 관리자 문의 바랍니다.");
	        return "redirect:/customer/login";
		}
		red.addFlashAttribute("msg", "비밀번호 변경이 완료되었습니다.");
		return "redirect:/customer/login";
	}
	
}
