package com.tree.gdhealth.customer.signup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tree.gdhealth.domain.Customer;
import com.tree.gdhealth.domain.CustomerSignUp;

import jakarta.servlet.http.HttpSession;

@Controller
public class SignUpController {
	@Autowired private SignUpService signUpService;
	
	
	@GetMapping("/customer/signup")
	public String signUpPage() {
		
		return "customer/signUp";
	}
	
	@PostMapping("/customer/signup")
	public String signUp(HttpSession session,CustomerSignUp custoemrSignUp,@RequestParam("customerImg") MultipartFile imgFile, RedirectAttributes red) {
		
		String path = session.getServletContext().getRealPath("/upload/customer");
		

		System.out.println(custoemrSignUp.toString());
		
		// customer INSERT
		signUpService.SignUp(custoemrSignUp,imgFile,path);
		
		String msg = "회원가입이 완료되었습니다.";
		red.addFlashAttribute("msg",msg);
		
		return "redirect:/customer/login";
	}
	
	@ResponseBody
	@GetMapping("/customer/idCk")
	public Customer idCk(Customer customer) {
		signUpService.idCk(customer);
		
		return customer;
	}
	
	@ResponseBody
	@GetMapping("/customer/emailCk")
	public int emailCk(String customerEmail){
		int customerNo = signUpService.emailCk(customerEmail);
		return customerNo;
	}
	

	
}
