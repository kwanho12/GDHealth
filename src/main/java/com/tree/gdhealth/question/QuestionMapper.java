package com.tree.gdhealth.question;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.tree.gdhealth.dto.Question;

@Mapper
public interface QuestionMapper {
	
	//질문 리스트
	List<Question> questionList(Map<String, Object> paramMap);
	
	//질문 상세
	Question questionOne(int questionNo) ;
	
	//본사 확인
	int getBranchLevel(int employeeNo);
	
	//추가
	int addQuestion(Question question);
	
	//수정
	int updateQuestion(Question question);
	
	//삭제
	int deleteQuestion(Question question);
	
	//질문 개수
	int questionCount();
}
   