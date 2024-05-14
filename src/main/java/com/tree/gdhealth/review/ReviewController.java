package com.tree.gdhealth.review;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tree.gdhealth.domain.Notice;
import com.tree.gdhealth.domain.Review;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ReviewController{
	@Autowired ReviewService reviewService;
	
	@GetMapping("/review/reviewList")
	public String reviewList(HttpSession session, Model model, @RequestParam(defaultValue="1")int currentPage) {
		Map<String, Object> reviewData = reviewService.reviewList(currentPage);
		List<Review> list = (List<Review>) reviewData.get("resultReviewList");
		int lastPage = (int) reviewData.get("lastPage");
		
		model.addAttribute("list", list);
		model.addAttribute("currentPage", currentPage);
	    model.addAttribute("lastPage", lastPage);
	    
	    Integer customerNo = ((Integer)session.getAttribute("customerNo"));
	    if(customerNo != null) {
		    model.addAttribute("customerNo", customerNo);
		    System.out.println("customerNo: " + customerNo);
	    }
		return "/review/reviewList";
	}
	
	
	@GetMapping("/review/reviewOne")
	public String reviewOne(Model model, int reviewNo, Review review) {
		Review revuOne = reviewService.reviewOne(reviewNo);
		model.addAttribute("revuOne", revuOne);
		System.out.println("revuOne: " + revuOne);
		
		return "/review/reviewOne";
	}
	
	
	@PostMapping("/review/reviewOne")
	   public String reviewOne(int reviewNo) {
		System.out.println("reviewNo: "+ reviewNo);
	      return "redirect:/review/reviewOne?reviewNo=" + reviewNo;
	   }
	
	@GetMapping("/review/addReview")
		public String addReview(Review review, Model model, HttpSession session) {
		Integer customerNo = ((Integer)session.getAttribute("customerNo"));
		if(customerNo == null) {
			return "redirect:/customer/login";
		}
		if(customerNo != null) {
			model.addAttribute("programRsNoList", reviewService.getProgramRsNoByCustomerNo(customerNo));
		    model.addAttribute("customerNo", customerNo);
		    System.out.println("customerNo: " + customerNo);
	    }
		
		return "/review/addReview";
	}
	
	@PostMapping("/review/addReview")
		public String addReview(HttpSession session, Review review, Integer customerNo) {
			if(customerNo == null) {
				return "redirect:/customer/login";
			}

		int row = reviewService.addReview(review);
		return "redirect:/review/reviewList";
	}
	
	@PostMapping("/review/updateReview")
	public String updateReview(Model model, HttpSession session, Review review, int reviewNo, HttpServletRequest request) {
		Integer customerNo = ((Integer)session.getAttribute("customerNo"));
		if(customerNo == null) {
			return "redirect:/customer/login";
		}
		review.setReviewTitle(request.getParameter("reviewTitle"));
		review.setReviewContent(request.getParameter("reviewContent"));
		model.addAttribute("reviewNo", reviewNo);
		int row = reviewService.updateReview(review);
		return "redirect:/review/reviewOne?reviewNo=" + reviewNo;
	}
	@GetMapping("/review/deleteReview")
	public String deleteReview(HttpSession session, Review review, Model model, int reviewNo) {
		Integer customerNo = ((Integer)session.getAttribute("customerNo"));
		if(customerNo == null) {
			return "redirect:/customer/login";
		}
		int row = reviewService.deleteReview(review);
		
		model.addAttribute("reviewNo", reviewNo);
		return "redirect:/review/reviewList";
	}
	
}
	
	
