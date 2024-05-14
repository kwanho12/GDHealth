package com.tree.gdhealth.answer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.tree.gdhealth.domain.Answer;
import com.tree.gdhealth.employee.login.LoginEmployee;
import com.tree.gdhealth.question.QuestionService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class AnswerController {
	@Autowired AnswerService answerService;
	@Autowired QuestionService questionService;
	
	@GetMapping("/answer/answerOne")
	public String answerOne(Answer answer, HttpSession session, Model model,int answerNo) {
		
		LoginEmployee loginEmployee = (LoginEmployee)session.getAttribute("loginEmployee");
		int branchLevel = 0;
		   if(loginEmployee != null) {
			   branchLevel = loginEmployee.getBranchLevel();
			   System.out.println("branchLevel : "+ branchLevel);
		   }
		   
		   if(branchLevel != 1) {
			   return "redirect:/employee/login";
		   }
		
		Answer ansOne = answerService.answerOne(answerNo);	
		System.out.println("ansOne : "+ ansOne);
		
		model.addAttribute("branchLevel", branchLevel);
		model.addAttribute("ansOne", ansOne);
		
		return "/question/questionOne";
	}
	
	@PostMapping("/answer/answerOne")
	public String answerOne(HttpSession session, int branchLevel) {
		if(branchLevel != 1) {
			return "redirect:/employee/login";
		}
		int questionNo = 1;

	
		
		return "/redirect:/question/questionOne?questionNo=" + questionNo;
	}
	
	
	
	@PostMapping("/answer/addAnswer")
	public String addAnswer(HttpSession session, int questionNo, Answer answer) {
		LoginEmployee loginEmployee = (LoginEmployee)session.getAttribute("loginEmployee");
		int branchLevel = 0;
		int employeeNo = 0;
		   if(loginEmployee != null) {
			   branchLevel = loginEmployee.getBranchLevel();
			   employeeNo = loginEmployee.getEmployeeNo();
			   System.out.println("branchLevel : "+ branchLevel);
			   System.out.println("employeeNo: " + employeeNo);
		   }
		   
		   if(branchLevel != 1) {
			   return "redirect:/employee/login";
		   }
		
		int row = answerService.addAnswer(answer);
		return "redirect:/question/questionOne?questionNo=" + questionNo;
	}
	
	@PostMapping("/answer/updateAnswer")
	public String updateAnswer(Model model, HttpSession session, Answer answer, Integer questionNo, HttpServletRequest request){
		LoginEmployee loginEmployee = (LoginEmployee)session.getAttribute("loginEmployee");
		int branchLevel = 0;
		int employeeNo = 0;
			if(loginEmployee != null) {
				branchLevel = loginEmployee.getBranchLevel();
				employeeNo = loginEmployee.getEmployeeNo();
				System.out.println("branchLevel: "+branchLevel);
				System.out.println("employeeNo: "+ employeeNo);
			}
			if(branchLevel != 1) {
				return "redirect:/employee/login";
			}
			answer.setAnswerContent(request.getParameter("answerContent"));
			model.addAttribute("questionNo", questionNo);
			int row = answerService.updateAnswer(answer);
			return "redirect:/question/questionOne?questionNo=" + questionNo;
	}
	
	@GetMapping("/answer/deleteAnswer")
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public String deleteAnswer(Model model, HttpSession session, Answer answer, Integer answerNo, Integer questionNo) {
		LoginEmployee loginEmployee = (LoginEmployee)session.getAttribute("loginEmployee");
		int branchLevel = 0;
		int employeeNo = 0;
			if(loginEmployee != null) {
				branchLevel = loginEmployee.getBranchLevel();
				employeeNo = loginEmployee.getEmployeeNo();
				System.out.println("branchLevel: "+branchLevel);
				System.out.println("employeeNo: "+ employeeNo);
			}
			if(branchLevel != 1) {
				return "redirect:/employee/login";
			}
			int row = answerService.deleteAnswer(answer);
			
			model.addAttribute("answerNo", answerNo);
			model.addAttribute("questionNo", questionNo);
			
			return "redirect:/question/questionOne?questionNo=" + questionNo;
	}
	
}
