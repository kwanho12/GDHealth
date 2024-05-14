package com.tree.gdhealth.answer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tree.gdhealth.domain.Answer;

@Service
@Transactional
public class AnswerService {
	@Autowired AnswerMapper answerMapper;
	// 답변 상세
	public Answer answerOne(int answerNo) {
		
		Answer resultAnswerOne = answerMapper.answerOne(answerNo); 
		
		return resultAnswerOne;
	}
	//답변 추가
	public int addAnswer(Answer answer) {
		int row = answerMapper.addAnswer(answer);
		return row;
	}
	
	// 답변 수정
	public int updateAnswer(Answer answer) {
		int row = answerMapper.updateAnswer(answer);
		return row;
	}
	// 답변 삭제
	public int deleteAnswer(Answer answer) {
		int row = answerMapper.deleteAnswer(answer);
		return row;
	}
}
