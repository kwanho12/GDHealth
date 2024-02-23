package com.tree.gdhealth.customer.mypage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tree.gdhealth.dto.Customer;
import com.tree.gdhealth.dto.CustomerDetail;
import com.tree.gdhealth.dto.CustomerImg;
import com.tree.gdhealth.dto.CustomerMyPage;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class MyPageController {
   @Autowired MyPageService myPageService;
   
   // 마이페이지 입장
   @GetMapping("/customer/myPage")
   public String myPage(HttpSession session,Model model) {
      if(session.getAttribute("customerNo") == null) {
         return "customer/home";
      }
      int customerNo = (Integer)session.getAttribute("customerNo");
      System.out.println(customerNo);
      //고객정보
      CustomerMyPage info = myPageService.MyPage(customerNo);
      model.addAttribute("info", info);
      //전화번호 하이픈
      String phoneNumber = myPageService.selectPhone(customerNo);
      String phoneCustom = phoneNumber.substring(0,3)+"-"+
    		  			   phoneNumber.substring(3,7)+"-"+
    		  			   phoneNumber.substring(7);
       model.addAttribute("phoneCustom", phoneCustom);
      //출석
      int attendanceCount = myPageService.attendance(customerNo);
      model.addAttribute("attendanceCount", attendanceCount);
      //리뷰
      int reviewCount = myPageService.review(customerNo);
      model.addAttribute("reviewCount", reviewCount);
      //문의
      int questionCount = myPageService.question(customerNo);
      model.addAttribute("questionCount", questionCount);
      System.out.println("문의까지 됨");
      
      
      // 이미지 정보 가져오기
      System.out.println("이미지 정보 가져오기 시작");
      CustomerImg imgInfo = myPageService.selectCustomerImg(customerNo);
      System.out.println("이미지 정보 가져오기 끝");
      
      System.out.println(imgInfo.toString());
      System.out.println(info.toString());
      System.out.println(customerNo);
      
      model.addAttribute("imgInfo", imgInfo);
      
      //멤버십
      System.out.println("멤버십 시작");
      String membership = myPageService.membership(customerNo);
      System.out.println("멤버십 중간");
      if(membership == null) {
         membership = "만료";
      }
      System.out.println("멤버십 끝");
      model.addAttribute("membership", membership);
      
      return "customer/myPage";
   }
   
   @GetMapping("/customer/updateMyPage")
   public String updateMyPage(HttpSession session, Model model) {
      if(session.getAttribute("customerNo") == null) {
         return "customer/home";
      }
      int customerNo = (Integer)session.getAttribute("customerNo");
      //고객정보
      CustomerMyPage info = myPageService.MyPage(customerNo);
      model.addAttribute("info", info);
      //출석
      int attendanceCount = myPageService.attendance(customerNo);
      model.addAttribute("attendanceCount", attendanceCount);
      //리뷰
      int reviewCount = myPageService.review(customerNo);
      model.addAttribute("reviewCount", reviewCount);
      //문의
      int questionCount = myPageService.question(customerNo);
      model.addAttribute("questionCount", questionCount);
      //맴버십
      String membership = myPageService.membership(customerNo);
      if(membership == null) {
         membership = "만료";
      }
      model.addAttribute("membership", membership);
      // 이미지 정보 가져오기
      System.out.println("이미지 정보 가져오기 시작");
      CustomerImg imgInfo = myPageService.selectCustomerImg(customerNo);
      System.out.println("이미지 정보 가져오기 끝");
      model.addAttribute("imgInfo", imgInfo);
      
      return "customer/updateMyPage";
   }
   @PostMapping("/customer/updateMyPage")
   public String updateMyPage(HttpSession session, Model model, CustomerMyPage customerMyPage, Customer customer, HttpServletRequest request) {
	   int customerNo = (int)session.getAttribute("customerNo");
	   if(customerNo == 0) {
	         return "customer/home";
	      }
	   System.out.println("customerNo: " + customerNo);
	   if(customerNo != 0) {
		   System.out.println("dddddddd");
		   int customerHeight = Integer.valueOf(request.getParameter("customerHeight"));
		   int customerWeight = Integer.valueOf(request.getParameter("customerWeight"));
		   String customerName = request.getParameter("customerName");
		   String customerPhone = request.getParameter("customerPhone");
		   String customerAddress = request.getParameter("customerAddress");
		   String customerEmail = request.getParameter("customerEmail");
		   String customerPw = request.getParameter("customerPw");
		   customer.setCustomerNo(customerNo);
		   customerMyPage.setCustomerNo(customerNo);
		   customerMyPage.setCustomerHeight(customerHeight);
		   customerMyPage.setCustomerWeight(customerWeight);
		   customerMyPage.setCustomerName(customerName);
		   customerMyPage.setCustomerPhone(customerPhone);
		   customerMyPage.setCustomerAddress(customerAddress);
		   customerMyPage.setCustomerEmail(customerEmail);
		   customer.setCustomerPw(customerPw);
	       System.out.println(customerMyPage.toString());
		   myPageService.updateMyPage(customerMyPage);
		   myPageService.updatePw(customer);
		   
		   System.out.println("customerWeight: " + customerWeight);
		   System.out.println("customerHeight: " + customerHeight);
		   
		   return "redirect:/customer/myPage";
		   
		  
		   
	   };
	   return "redirect:/customer/myPage";
   }
   @GetMapping("/customer/delete")
   public String customerDeletePage(HttpSession session,Model model) {
      if(session.getAttribute("customerNo") == null) {
         return "customer/home";
      }
      model.addAttribute("open",0);
      return "customer/delete";
   }
   
   @PostMapping("/customer/delete")
   public String customerDelete(HttpSession session,Model model,Customer customer, RedirectAttributes red) {
      int result = myPageService.deleteCustomerCk(customer);
      if(result == 0) {
         System.out.println("틀림");
         model.addAttribute("open",0);
         red.addFlashAttribute("msg", "아이디 또는 비밀번호가 다릅니다.");
         return "redirect:/customer/delete";
      }
      model.addAttribute("open",1);
      return "customer/delete";
   }
   
   @GetMapping("/customer/deleteDo")
   public String customerDeleteDo(HttpSession session,Model model,Customer customer, RedirectAttributes red) {
      // 사진삭제를위한 패스 값 입력
      String path = session.getServletContext().getRealPath("/upload/customer");
      // 해당 고객번호 계정삭제
      customer.setCustomerNo((Integer)session.getAttribute("customerNo"));
      int result = myPageService.deleteCustomer(customer, path);
      
      System.out.println(result);
      if(result == 0) {
         red.addFlashAttribute("msg", "알수없는오류 관리자에게 문의하세요.");
         return "redirect:/customer/home";
      }
      session.invalidate();
      red.addFlashAttribute("msg", "회원탈퇴가 완료되었습니다.");
      return "redirect:/customer/home";
   }
   
    @ResponseBody
	@GetMapping("/customer/updateEmailCk")
	public int updateEmailCk(HttpSession session, CustomerMyPage customerMyPage){
		int customerNo = (Integer)session.getAttribute("customerNo");
		customerMyPage.setCustomerNo(customerNo);
		customerNo = myPageService.updateEmailCk(customerMyPage);
		return customerNo;
	}
   
}