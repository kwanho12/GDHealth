package com.tree.gdhealth.headoffice.program;

import org.apache.ibatis.annotations.Mapper;

import com.tree.gdhealth.dto.Program;
import com.tree.gdhealth.dto.ProgramDate;
import com.tree.gdhealth.dto.ProgramImg;

import java.util.List;
import java.util.Map;

/**
 * @author 진관호
 */
@Mapper
public interface ProgramMapper {

	List<Map<String, Object>> selectProgramList(Map<String, Object> map);

	int selectProgramCnt();

	int selectSearchCnt(Map<String, Object> map);

	boolean selectResultOfDatesCheck(List<String> dateList);

	boolean selectResultOfDateOneCheck(String date);

	Map<String, Object> selectProgramOne(Map<String, Object> map);

	int insertProgram(Program program);

	int insertProgramDates(List<ProgramDate> dateList);

	int insertProgramImg(ProgramImg programImg);

	int updateProgram(Program program);

	int updateProgramDate(ProgramDate programDate);

	int updateProgramImg(ProgramImg programImg);

	int updateToDeactiveProgram(int programNo);

	int updateToActiveProgram(int programNo);

}
