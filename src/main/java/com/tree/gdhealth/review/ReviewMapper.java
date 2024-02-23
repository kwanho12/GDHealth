package com.tree.gdhealth.review;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.tree.gdhealth.dto.Review;

@Mapper
public interface ReviewMapper {
	//추가
	int addReview(Review review);
	//수정
	int updateReview(Review review);
	//삭제
	int deleteReview(Review review);
	//리스트
	List<Review> reviewList(Map<String, Object> paramMap);
	//상세
	Review reviewOne(int reviewNo);
	//개수
	int reviewCount();

	List<ProgramRsVo> selectProgramRsNoByCustomerNo(int customerNo);
}

