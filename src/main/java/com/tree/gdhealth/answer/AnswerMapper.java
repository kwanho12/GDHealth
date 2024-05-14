package com.tree.gdhealth.answer;

import org.apache.ibatis.annotations.Mapper;

import com.tree.gdhealth.domain.Answer;

@Mapper
public interface AnswerMapper {
	// 답변추가
	int addAnswer(Answer answer);
	//답변수정
	int updateAnswer(Answer answer);
	//답변삭제
	int deleteAnswer(Answer answer);
	//답변상세
	Answer answerOne(int answerNo);
}
