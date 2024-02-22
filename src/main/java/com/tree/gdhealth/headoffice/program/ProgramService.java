package com.tree.gdhealth.headoffice.program;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.tree.gdhealth.utils.ImageSave;
import com.tree.gdhealth.vo.Program;
import com.tree.gdhealth.vo.ProgramDate;
import com.tree.gdhealth.vo.ProgramImg;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ProgramService {

	// DI
	private final ProgramMapper programMapper;

	@Transactional(readOnly = true)
	public List<Map<String, Object>> getProgramList(int beginRow, int rowPerPage) {

		Map<String, Object> map = new HashMap<>();
		map.put("beginRow", beginRow);
		map.put("rowPerPage", rowPerPage);

		List<Map<String, Object>> programList = programMapper.programList(map);

		return programList;

	}

	@Transactional(readOnly = true)
	public int getProgramCnt() {

		int programCnt = programMapper.programCnt();
		// 디버깅
		log.debug("전체 프로그램 수 : " + programCnt);

		return programCnt;
	}

	@Transactional(readOnly = true)
	public List<Map<String, Object>> getSearchList(int beginRow, int rowPerPage, String type, String keyword) {

		Map<String, Object> map = new HashMap<>();
		map.put("beginRow", beginRow);
		map.put("rowPerPage", rowPerPage);
		map.put("type", type);
		map.put("keyword", keyword);

		List<Map<String, Object>> searchList = programMapper.programList(map);

		return searchList;

	}

	@Transactional(readOnly = true)
	public int getSearchCnt(String type, String keyword) {

		Map<String, Object> map = new HashMap<>();
		map.put("type", type);
		map.put("keyword", keyword);

		int searchCnt = programMapper.searchCnt(map);
		// 디버깅
		log.debug("검색 결과 개수 : " + searchCnt);

		return searchCnt;

	}

	@Transactional(readOnly = true)
	public Map<String, Object> getProgramOne(int programNo, String programDate) {

		Map<String, Object> map = new HashMap<>();
		map.put("programNo", programNo);
		map.put("programDate", programDate);

		Map<String, Object> programOne = programMapper.programOne(map);

		return programOne;
	}

	@Transactional(readOnly = true)
	public boolean checkDatesExists(List<String> programDates) {

		boolean checkDatesExists = programMapper.checkDatesExists(programDates);
		// 디버깅
		log.debug("dates 존재 확인(존재:true,존재x:false) : " + checkDatesExists);

		return checkDatesExists;
	}

	@Transactional(readOnly = true)
	public boolean checkDateOneExists(String programDate) {

		boolean checkDateOneExists = programMapper.checkDateOneExists(programDate);
		// 디버깅
		log.debug("dateOne 존재 확인(존재:true,존재x:false) : " + checkDateOneExists);

		return checkDateOneExists;
	}

	public void insertProgram(Program program, ProgramDate programDate, ProgramImg programImg, String path) {

		int result = programMapper.insertProgram(program);
		// 디버깅
		log.debug("program 추가(성공:1) : " + result);

		List<String> dates = programDate.getProgramDates();
		log.debug("dates : " + dates);

		Set<String> set = new HashSet<>(dates);
		// dates내에 서로 중복된 값이 있을 때
		if (dates.size() != set.size()) {
			// insert를 실패하였을 때 강제로 예외를 발생시켜 애노테이션 Transactiona이 작동하도록 한다.
			throw new RuntimeException();
		}

		List<ProgramDate> dateList = new ArrayList<>();
		for (String date : dates) {
			if (!date.equals("")) { // list의 요소가 공백이 아닐 경우
				ProgramDate p = new ProgramDate();
				p.setProgramNo(program.getProgramNo()); // programMapper.xml에서 selectKey로 얻어 온 program table의 auto
														// increment 값
				p.setProgramDate(date);
				dateList.add(p);
			}
		}

		int dateResult = programMapper.insertProgramDates(dateList);
		log.debug("programDate 추가(추가 개수) : " + dateResult);

		if (dateResult == 0) {
			// insert를 실패하였을 때 강제로 예외를 발생시켜 애노테이션 Transactiona이 작동하도록 한다.
			throw new RuntimeException();
		}

		MultipartFile programFile = programImg.getProgramFile();
		// 파일 저장
		insertOrUpdateProgramImg(programFile, path, program.getProgramNo(), true);

	}

	public void updateProgram(Program program, ProgramDate programDate, ProgramImg programImg, String newPath,
			String oldPath) {

		int result = programMapper.updateProgram(program);
		log.debug("프로그램 수정(성공:1) : " + result);

		int dateResult = programMapper.updateProgramDate(programDate);
		log.debug("프로그램 date 수정(성공:1) : " + dateResult);

		MultipartFile programFile = programImg.getProgramFile();

		// 수정한 파일이 존재할 때
		if (!programFile.isEmpty()) {

			// 기존 파일 삭제
			File file = new File(oldPath);
			boolean isDelete = file.delete();
			// 디버깅
			log.debug("기존 파일 삭제 여부 : " + isDelete);

			int programNo = program.getProgramNo();

			// 수정한 파일 저장
			insertOrUpdateProgramImg(programFile, newPath, programNo, false);
		}

	}

	public int deactiveProgram(int programNo) {

		int result = programMapper.deactiveProgram(programNo);
		// 디버깅
		log.debug("프로그램 비활성화(성공:1,실패:0) : " + result);

		return result;
	}

	public int activeProgram(int programNo) {

		int result = programMapper.activeProgram(programNo);
		// 디버깅
		log.debug("프로그램 활성화(성공:1,실패:0) : " + result);

		return result;
	}

	public void insertOrUpdateProgramImg(MultipartFile programFile, String path, int programNo, boolean isInsert) {

		ImageSave imgSave = new ImageSave();

		ProgramImg img = new ProgramImg();
		img.setProgramNo(programNo);
		img.setOriginName(programFile.getOriginalFilename());
		img.setProgramImgSize(programFile.getSize());
		img.setProgramImgType(programFile.getContentType());

		String filename = imgSave.getFilename(programFile);
		img.setFilename(filename);

		if (isInsert) {
			int imgResult = programMapper.insertProgramImg(img);
			log.debug("programImg 추가(성공:1) : " + imgResult);
		} else {
			int imgResult = programMapper.updateProgramImg(img);
			log.debug("programImgUpdate 추가(성공:1) : " + imgResult);
		}

		// 파일 저장
		imgSave.saveFile(programFile, path, filename);

	}

}
