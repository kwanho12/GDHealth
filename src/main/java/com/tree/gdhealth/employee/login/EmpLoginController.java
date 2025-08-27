package com.tree.gdhealth.employee.login;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tree.gdhealth.domain.Employee;

import jakarta.servlet.http.HttpSession;

/**
 * @author 이은택
 * @contributor: 정인호
 */
@Slf4j
@RequiredArgsConstructor
@Controller
public class EmpLoginController {
	
	private final EmpLoginService empLoginService;
	
	@GetMapping("/employee/login")
	public String getLoginForm(@SessionAttribute(name = "loginEmployee", required = false) LoginEmployee loginEmployee) {
		// 로그인 세션 확인
		if(loginEmployee == null) {
			return "employee/logIn";
		} else if(loginEmployee.getBranchLevel() == 1) {
			return "redirect:/headoffice/emp";
		} else if(loginEmployee.getBranchLevel() == 0) {
			return "redirect:/branch/home";
		}
		return "customer/home";
	}
	
	@PostMapping("/employee/login")
	public String login(HttpSession session, @ModelAttribute Employee employee, RedirectAttributes red) {
		log.info("==========================  employee : " + employee);
		LoginEmployee loginEmployee = empLoginService.login(employee);
		if(loginEmployee == null) {
	        return "redirect:/employee/login";
		}
		red.addAttribute("msg", "로그인 되셨습니다."+loginEmployee.getEmployeeName()+" 님");
		log.debug("로그인된 직원정보 = "+loginEmployee.toString());
		session.removeAttribute("customerNo");
		session.removeAttribute("userLevel");
		
		session.setAttribute("loginEmployee", loginEmployee);
		// branchNo 체크해서 본사페이지 이동
		if(loginEmployee.getBranchNo() == 1) {
			return "redirect:/headoffice/emp";
		}
		return "redirect:/branch/home";
	}
	
	@GetMapping("/employee/logout")
	public String logout(HttpServletRequest request,
                         HttpSession session,
                         @RequestParam(name = "ref", required = false)boolean ref) {
        if (session != null) {
            session.invalidate();
        }

		if(ref){
			return "redirect:"+request.getHeader("referer");
		}
		return "redirect:/employee/login";
	}
}
