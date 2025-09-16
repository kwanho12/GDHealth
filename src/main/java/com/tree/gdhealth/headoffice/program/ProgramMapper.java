package com.tree.gdhealth.headoffice.program;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.tree.gdhealth.domain.Program;
import com.tree.gdhealth.domain.ProgramDate;
import com.tree.gdhealth.domain.ProgramImg;
import com.tree.gdhealth.dto.PaginationDto;

import java.util.List;
import java.util.Map;

/**
 * @author 진관호
 */
@Mapper
public interface ProgramMapper {

	List<Map<String, Object>> selectProgramList(PaginationDto paginationDto);

	int selectProgramCnt();

	int selectSearchCnt(@Param("type") String type, @Param("keyword") String keyword);

	boolean selectResultOfDatesCheck(List<String> dateList);

	boolean selectResultOfDateOneCheck(String date);

	Map<String, Object> selectProgramOne(ProgramDate programDate);

	void insertProgram(Program program);

	void insertProgramDates(List<ProgramDate> dateList);

	void insertProgramImg(ProgramImg programImg);

	void updateProgram(Program program);

	void updateProgramDate(ProgramDate programDate);

	void updateProgramImg(ProgramImg programImg);

	void updateToDeactiveProgram(int programNo);

	void updateToActiveProgram(int programNo);

}
