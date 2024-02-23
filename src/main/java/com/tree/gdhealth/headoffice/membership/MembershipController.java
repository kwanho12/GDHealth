package com.tree.gdhealth.headoffice.membership;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.tree.gdhealth.dto.Membership;

import jakarta.servlet.http.HttpSession;

@Controller
public class MembershipController {

	@Autowired
	private MembershipService membershipService;
	
	@GetMapping("/headoffice/membershipList")
	public String membershipList(Model model) {
		
		List<Membership> membershipList = membershipService.membershipList();
		model.addAttribute("membershipList", membershipList);
		
		return "headoffice/membershipList";
	}
	
	@GetMapping("/headoffice/addMembership")
	public String addMembership(HttpSession session, Model model) {
		
		return "headoffice/addMembership";
		
	}
	
	@PostMapping("/headoffice/addMembership")
	public String addMembership(HttpSession session, Membership membership) {
		
		membershipService.addMembership(membership);
		
		return "redirect:/headoffice/membershipList";

	}
}
