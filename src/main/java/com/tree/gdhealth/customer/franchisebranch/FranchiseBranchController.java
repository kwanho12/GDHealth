package com.tree.gdhealth.customer.franchisebranch;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tree.gdhealth.dto.Branch;

import jakarta.servlet.http.HttpSession;

@Controller
public class FranchiseBranchController {
	@Autowired
	FranchiseBranchService franchiseBranchService;
	
	@GetMapping("/customer/franchiseBranch")
	public String franchiseBranchPage(Model model, HttpSession session) {
		// 지점리스트
		List<Branch> branch = franchiseBranchService.branchInfo();
		model.addAttribute("branch", branch);
		
		// 전 지점 인원수
		Branch cnt = franchiseBranchService.branchMemberCnt();
		// 인원수 임의로 값 넣기 
		// cnt.setCount(1000);
		model.addAttribute("cnt", cnt);
		System.out.println(cnt.toString());
		System.out.println(branch.toString());
		return "customer/franchiseBranch";
	}
	
	
	// 선택된 지점의 정보를 출력 (ajax)
	@ResponseBody
	@PostMapping("/customer/branchCk")
	public Branch branchInfoOne(Branch branch) {
		System.out.println(branch.getBranchNo());
		Branch data = franchiseBranchService.branchInfoOne(branch);
		System.out.println(data.toString());
		return data;
	}
}
