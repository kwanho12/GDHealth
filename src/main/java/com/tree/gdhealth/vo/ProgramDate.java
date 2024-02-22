package com.tree.gdhealth.vo;

import java.util.List;

import com.tree.gdhealth.utils.customvalidation.FutureDate;
import com.tree.gdhealth.utils.customvalidation.FutureDates;
import com.tree.gdhealth.utils.customvalidation.ListPattern;
import com.tree.gdhealth.utils.customvalidation.group.DateGroup;
import com.tree.gdhealth.utils.customvalidation.group.DatesGroup;

import jakarta.validation.constraints.Pattern;
import lombok.Data;


@Data
public class ProgramDate {
	
	private int programDateNo;
	private int programNo;
	
	@Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "날짜 형식이 올바르지 않습니다.", groups = DateGroup.class)
	@FutureDate(groups = DateGroup.class)
	private String programDate;
	
	private String createdate;
	private String updatedate;
	
	/*
		커스텀 애노테이션(기존의 @Pattern 애노테이션으로는 List 타입 검증이 불가해서 생성)
		@ListPattern : List의 각각의 String에 대해 날짜 형식이 올바른지 검증
		@FutureDate : String에 대해 현재 날짜와 동일하거나 현재 날짜 이후인지 검증
		@FutureDates : List의 각각의 String에 대해 현재 날짜와 동일하거나 현재 날짜 이후인지 검증
	*/
	@ListPattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", groups = DatesGroup.class)
	@FutureDates(groups = DatesGroup.class)
	private List<String> programDates;
    
	// 프로그램 날짜를 수정하기 전의 날짜
	private String originDate;

}
