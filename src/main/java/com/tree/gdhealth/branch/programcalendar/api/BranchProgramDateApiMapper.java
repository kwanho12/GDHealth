package com.tree.gdhealth.branch.programcalendar.api;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.Map;

/**
 * @author 정인호
 */
@Mapper
public interface BranchProgramDateApiMapper {

    Map<String, Object> getBranchProgramDate(@Param("date") LocalDate date, @Param("branchNo") int branchNo);

    int selectManager(@Param("programDateNo") int programDateNo,@Param("managerNo") int managerNo);

    int changeManager(@Param("programDateNo") int programDateNo,@Param("managerNo") int managerNo);

    int insertManager(@Param("programDateNo") int programDateNo,@Param("managerNo") int managerNo);
}
