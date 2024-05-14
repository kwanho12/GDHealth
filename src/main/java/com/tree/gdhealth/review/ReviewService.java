package com.tree.gdhealth.review;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tree.gdhealth.domain.Review;

@Service
@Transactional
public class ReviewService {
	@Autowired ReviewMapper reviewMapper;
	
	//리스트
	public Map<String, Object> reviewList(int currentPage){
		int rowPerPage = 10;
		int beginRow = (currentPage -1) * rowPerPage;
		
		Map<String, Object> paramMap = new HashMap<>();
		
		paramMap.put("beginRow", beginRow);
	    paramMap.put("rowPerPage", rowPerPage);
		
		List<Review> resultReviewList = reviewMapper.reviewList(paramMap);
		
		int temp = reviewMapper.reviewCount();
		System.out.print("현재 리뷰 개수:" + temp);
	      int lastPage = temp / rowPerPage;
	      int value = temp % rowPerPage;
	      
	      if(value != 0) {
	    	  lastPage = lastPage + 1;
	      }
	      Map<String, Object> result = new HashMap<>();
	      result.put("resultReviewList", resultReviewList);
	      result.put("lastPage", lastPage);
	      System.out.print("마지막 페이지:" + lastPage);
	      
	      return result;
	}
	//상세
	public Review reviewOne(int reviewNo) {
		Review resultReviewOne = reviewMapper.reviewOne(reviewNo);
		System.out.println("resultReviewOne: " + resultReviewOne);
		
		return resultReviewOne;
	}
	//추가
	public int addReview(Review review) {
		int row = reviewMapper.addReview(review);
		return row;
	}
	//수정
	public int updateReview(Review review) {
		int row = reviewMapper.updateReview(review);
		return row;
	}
	

	
	//삭제
	public int deleteReview(Review review) {
		int row = reviewMapper.deleteReview(review);
		return row;
	}
	
	public List<ProgramRsVo>  getProgramRsNoByCustomerNo(int customerNo) {

		List<ProgramRsVo> list = reviewMapper.selectProgramRsNoByCustomerNo(customerNo);
		list.forEach(System.out::println);
		return list;
		
	}
	
}


