package com.tree.gdhealth.question;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tree.gdhealth.domain.Question;

@Transactional
@Service
public class QuestionService {
	@Autowired QuestionMapper questionMapper;
	
	//문의 리스트
	public  Map<String,Object> questionList(int currentPage){
		  int rowPerPage = 10;
	      int beginRow = (currentPage -1) * rowPerPage;
	      
	      Map<String, Object> paramMap = new HashMap<>();
	       
	      paramMap.put("beginRow", beginRow);
	      paramMap.put("rowPerPage", rowPerPage);
		
	      List<Question> resultQuestionList = questionMapper.questionList(paramMap);
	      
	      int temp = questionMapper.questionCount();
	      System.out.print("현재 질문 개수:" + temp);
	      int lastPage = temp / rowPerPage;
	      int value = temp % rowPerPage;
	      
	      if(value != 0) {
	    	  lastPage = lastPage + 1;
	      }
	      Map<String, Object> result = new HashMap<>();
	      result.put("resultQuestionList", resultQuestionList);
	      result.put("lastPage", lastPage);
	      System.out.print("마지막 페이지:" + lastPage);
	      return result;
	}
	
	//문의 상세
	public Question questionOne(int employeeNo) {
		
		Question resultQuestionOne = questionMapper.questionOne(employeeNo);
		
		return resultQuestionOne;
	}
	
	//본사확인
	public int getBranchLevel(int branchNo) {
		int row = questionMapper.getBranchLevel(branchNo);
		return row;
	}
	
	//문의 추가
	public int addQuestion(Question question) {
		int row = questionMapper.addQuestion(question);
		return row;
	}
	//문의 수정
	public int updateQuestion(Question question) {
		System.out.println("Update: " + question);
		int row = questionMapper.updateQuestion(question);
		return row;
	}
	//문의 삭제
	public int deleteQuestion(Question question) {
		int row = questionMapper.deleteQuestion(question);
		return row;
	}
}
