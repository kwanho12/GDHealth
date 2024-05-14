package com.tree.gdhealth.notice;



import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tree.gdhealth.domain.Notice;
import com.tree.gdhealth.employee.login.LoginEmployee;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class NoticeController {
   @Autowired NoticeService noticeService;

   
   @GetMapping("/notice/noticeList")
   public String noticeList(HttpSession session,Model model, @RequestParam(defaultValue="1")int currentPage) {
	  Map<String, Object> noticeData = noticeService.noticeList(currentPage);
	  List<Notice> list = (List<Notice>) noticeData.get("resultNoticeList");
	  int lastPage = (int) noticeData.get("lastPage");
	  
      model.addAttribute("list", list);
      model.addAttribute("currentPage", currentPage);
      model.addAttribute("lastPage", lastPage);
      
      LoginEmployee loginEmployee = (LoginEmployee)session.getAttribute("loginEmployee");
      
      if(loginEmployee != null) {
    	  int branchLevel = loginEmployee.getBranchLevel();
    	  model.addAttribute("branchLevel", branchLevel);
    	  
    	  System.out.println("branchLevel : "+  branchLevel);
    	
      }
      return "/notice/noticeList";

   }
  
   
   @GetMapping("/notice/noticeOne")
     public String noticeOne(HttpSession session, Model model, int noticeNo) {
	   
	   LoginEmployee loginEmployee = (LoginEmployee)session.getAttribute("loginEmployee");
	   int branchLevel = 0;
	   if(loginEmployee != null) {
		   branchLevel = loginEmployee.getBranchLevel();
	   }
	   
       System.out.println("noticeNo: "+ noticeNo); 
       Notice notiOne = noticeService.noticeOne(noticeNo);
         
        model.addAttribute("notiOne", notiOne);
        model.addAttribute("branchLevel", branchLevel);
        
        return "/notice/noticeOne";
    }
         
   
   @PostMapping("/notice/noticeOne")
   public String noticeOne(int noticeNo) {
      System.out.println("noticeNo: "+ noticeNo);
      return "redirect:/notice/noticeOne?noticeNo=" + noticeNo;
   }
   
   @GetMapping("/notice/addNotice")
   public String addNotice(HttpSession session, Model model, Notice notice) {
	   
	   LoginEmployee loginEmployee = (LoginEmployee)session.getAttribute("loginEmployee");
	   int branchLevel = loginEmployee.getBranchLevel();
	   int employeeNo = loginEmployee.getEmployeeNo();
	   
	   model.addAttribute("branchLevel", branchLevel);
	   model.addAttribute("employeeNo", employeeNo);
	   System.out.println("branchLevel: " + branchLevel);
	   System.out.println("employeeNo: "+  employeeNo);
	 
	   if(branchLevel != 1) {
		   return "redirect:/employee/login";
	   }
	   
	  
	   return "/notice/addNotice";
   }
   
   
   @PostMapping("/notice/addNotice")
   public String addNotice(HttpSession session, Notice notice,int branchLevel) {
	   if(branchLevel != 1) {
		   return "redirect:/employee/login";
	   }
	
	   
       int row = noticeService.addNotice(notice);
       
       return "redirect:/notice/noticeList"; 
   }
   

   @GetMapping("/notice/updateNotice")
   public String updateNotice(HttpSession session, int noticeNo, Model model, Notice notice) {
	   LoginEmployee loginEmployee = (LoginEmployee)session.getAttribute("loginEmployee");
	   int branchLevel = 0;
	   if(loginEmployee != null) {
		   branchLevel = loginEmployee.getBranchLevel();
	   }
	   if(branchLevel != 1) {
		   return "redirect:/employee/login";
	   }
	  model.addAttribute("branchLevel", branchLevel);
	  model.addAttribute("noticeNo", noticeNo);
      return "/notice/updateNotice";
   }
   
   @PostMapping("/notice/updateNotice")
   public String updateNotice(HttpSession session, int branchLevel, int noticeNo, Notice notice) {
	   if(branchLevel != 1) {
		   return "redirect:/employee/login";
	   }
      int row = noticeService.updateNotice(notice);
      return "redirect:/notice/noticeList";
   }
   
   @GetMapping("/notice/deleteNotice")
   public String deleteNotice(HttpSession session, Notice notice, Model model, int noticeNo) {
	   LoginEmployee loginEmployee = (LoginEmployee)session.getAttribute("loginEmployee");
	   int branchLevel = loginEmployee.getBranchLevel();
	   if(branchLevel != 1) {
		   return "redirect:/employee/login";
	   }
	   int row = noticeService.deleteNotice(notice);
	   
	   model.addAttribute("noticeNo", noticeNo);
      return "redirect:/notice/noticeList";
   }
 
}
