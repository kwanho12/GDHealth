package com.tree.gdhealth.headoffice.program;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

import com.tree.gdhealth.vo.Program;
import com.tree.gdhealth.vo.ProgramDate;
import com.tree.gdhealth.vo.ProgramImg;

@Mapper
public interface ProgramMapper {

	List<Map<String, Object>> programList(Map<String, Object> map);

	int programCnt();

	int searchCnt(Map<String, Object> map);

	boolean checkDatesExists(List<String> dateList);

	boolean checkDateOneExists(String date);

	Map<String, Object> programOne(Map<String, Object> map);

	int insertProgram(Program program);

	int insertProgramDates(List<ProgramDate> dateList);

	int insertProgramImg(ProgramImg programImg);

	int updateProgram(Program program);

	int updateProgramDate(ProgramDate programDate);

	int updateProgramImg(ProgramImg programImg);

	int deactiveProgram(int programNo);

	int activeProgram(int programNo);

}
