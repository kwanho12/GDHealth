package com.tree.gdhealth.notice;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.tree.gdhealth.dto.Notice;

@Mapper
public interface NoticeMapper {
		//공지추가
		int addNotice(Notice notice);
		
		//공지수정
		int updateNotice(Notice notice);
		
		//공지삭제
		int deleteNotice(Notice notice);
	
		//공지상세
		Notice noticeOne(int noticeNo);
		
		//공지 리스트
		List<Notice> noticeList(Map<String, Object> paramMap);
		
		//공지개수
		int noticeCount();
}
