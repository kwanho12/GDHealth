package com.tree.gdhealth.customer.payment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.tree.gdhealth.dto.Membership;
import com.tree.gdhealth.headoffice.membership.MembershipService;

import jakarta.servlet.http.HttpSession;

@Controller
public class PaymentController {
	
	@Autowired
	private PaymentService paymentService;
	@Autowired
	private MembershipService membershipService;
	
	
	@GetMapping("/customer/membershipList")
	public String paymentList(Model model,HttpSession session) {
		
		List<Membership> membershipList = membershipService.membershipList();
		model.addAttribute("membershipList", membershipList);
		
		
		
		
		return "customer/membershipList";
	}
	
	@GetMapping("/customer/addPayment")
	public String addPayment(Model model, HttpSession session, int membershipNo, int membershipPrice, String membershipName) {
		
		if(session.getAttribute("customerNo") == null) {
			return "redirect:/customer/login";
		}
		
		int customerNo = (int)(session.getAttribute("customerNo"));
		
		model.addAttribute("customerNo", customerNo);
		model.addAttribute("membershipNo", membershipNo);
		model.addAttribute("membershipPrice", membershipPrice);
		model.addAttribute("membershipName", membershipName);


		

		
		return "customer/addPayment";
	}
	
	@GetMapping("/customer/updatePayment")
	public String updatePayment(HttpSession session, Model model, int membershipMonth, int membershipPrice, String membershipName) {

		int customerNo = (int)(session.getAttribute("customerNo"));
		
		model.addAttribute("customerNo", customerNo);
		model.addAttribute("membershipPrice", membershipPrice);
		model.addAttribute("membershipName", membershipName);
		model.addAttribute("membershipMonth", membershipMonth);
		
		return "customer/updatePayment";
	}

    @GetMapping("/customer/processPayment")
    public String processPayment(int membershipNo, HttpSession session) {
    	
		int customerNo = (int)(session.getAttribute("customerNo"));

    	System.out.println(membershipNo);
    	
    	paymentService.addPayment(membershipNo, customerNo);
    	
        return "redirect:/customer/success";
    }
    
    @GetMapping("/customer/tossPayment")
    public String tossPayment(HttpSession session, int membershipMonth) {
    	
		int customerNo = (int)(session.getAttribute("customerNo"));
		
		paymentService.updatePayment(customerNo, membershipMonth);
    	
        return "redirect:/customer/success";
    }
	
	@GetMapping("/customer/success")
	public String success() {

		
		return "customer/success";

	}


}	
