package com.tree.gdhealth.headoffice.program;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

import com.tree.gdhealth.vo.Program;
import com.tree.gdhealth.vo.ProgramDate;
import com.tree.gdhealth.vo.ProgramImg;

@Mapper
public interface ProgramMapper {

	List<Map<String, Object>> selectProgramList(Map<String, Object> map);

	int selectProgramCnt();

	int selectSearchCnt(Map<String, Object> map);

	boolean selectIsDatesExists(List<String> dateList);

	boolean selectIsDateOneExists(String date);

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
