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

import com.tree.gdhealth.dto.Program;
import com.tree.gdhealth.dto.ProgramDate;
import com.tree.gdhealth.dto.ProgramImg;
import com.tree.gdhealth.utils.imagesave.HeadofficeImageSaver;
import com.tree.gdhealth.utils.pagination.HeadofficePagination;

import lombok.RequiredArgsConstructor;

/**
 * @author 진관호
 */
@RequiredArgsConstructor
@Transactional
@Service
public class ProgramService {

	private final ProgramMapper programMapper;

	/**
	 * 전체 프로그램 목록을 리턴합니다.
	 * 
	 * @param beginRow   해당 페이지 내에서의 첫번째 프로그램
	 * @param rowPerPage 한 페이지에 나타낼 프로그램 수
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<Map<String, Object>> getProgramList(int beginRow, int rowPerPage) {

		Map<String, Object> map = new HashMap<>();
		map.put("beginRow", beginRow);
		map.put("rowPerPage", rowPerPage);

		return programMapper.selectProgramList(map);
	}
	
	/**
	 * 전체 프로그램의 개수를 리턴합니다.
	 * 
	 * @return 전체 프로그램 개수
	 */
	@Transactional(readOnly = true)
	public int getProgramCnt() {
		return programMapper.selectProgramCnt();
	}

	/**
	 * 검색 조건을 만족하는 프로그램 목록을 리턴합니다.
	 * 
	 * @param beginRow   해당 페이지 내에서의 첫번째 프로그램
	 * @param rowPerPage 한 페이지에 나타낼 프로그램의 수
	 * @param type       검색할 keyword의 속성(programName,programDetail...)
	 * @param keyword    검색 내용
	 * @return 검색 후의 프로그램 목록
	 */
	@Transactional(readOnly = true)
	public List<Map<String, Object>> getProgramList(int beginRow, int rowPerPage, String type, String keyword) {

		Map<String, Object> map = new HashMap<>();
		map.put("beginRow", beginRow);
		map.put("rowPerPage", rowPerPage);
		map.put("type", type);
		map.put("keyword", keyword);

		return programMapper.selectProgramList(map);
	}

	/**
	 * 검색 조건을 만족하는 프로그램 개수를 리턴합니다.
	 * 
	 * @param type    검색할 keyword의 속성(programName,programDetail...)
	 * @param keyword 검색 내용
	 * @return 검색 조건을 만족하는 프로그램 개수
	 */
	@Transactional(readOnly = true)
	public int getProgramCnt(String type, String keyword) {

		Map<String, Object> map = new HashMap<>();
		map.put("type", type);
		map.put("keyword", keyword);

		return programMapper.selectSearchCnt(map);
	}

	/**
	 * 프로그램 상세 정보를 리턴합니다.
	 * 
	 * @param programNo   조회할 프로그램의 번호
	 * @param programDate 조회할 프로그램의 날짜
	 * @return 프로그램 상세 정보
	 */
	@Transactional(readOnly = true)
	public Map<String, Object> getProgramOne(int programNo, String programDate) {

		Map<String, Object> map = new HashMap<>();
		map.put("programNo", programNo);
		map.put("programDate", programDate);

		return programMapper.selectProgramOne(map);
	}

	/**
	 * 데이터베이스에서 해당 날짜들을 검색하여 프로그램 날짜들의 중복 여부를 확인합니다. 
	 * 입력한 날짜들이 모두 데이터베이스에 존재하지 않는다면 false를 반환합니다.
	 * 
	 * @param programDates 프로그램 날짜 목록
	 * @return 입력한 날짜들 중 최소 1개가 이미 존재한다면 true, 존재하지 않는다면 false
	 */
	@Transactional(readOnly = true)
	public boolean getResultOfDatesCheck(List<String> programDates) {
		return programMapper.selectResultOfDatesCheck(programDates);
	}

	/**
	 * 데이터베이스에서 해당 날짜를 검색하여 프로그램 날짜의 중복 여부를 확인합니다. 
	 * 입력한 날짜가 데이터베이스에 존재하지 않는다면 false를 반환합니다.
	 * 
	 * @param programDate 프로그램 날짜
	 * @return 입력한 날짜가 이미 존재한다면 false, 존재하지 않는다면 true
	 */
	@Transactional(readOnly = true)
	public boolean getResultOfDateOneCheck(String programDate) {
		return programMapper.selectResultOfDateOneCheck(programDate);
	}

	/**
	 * 프로그램 정보를 데이터베이스에 삽입합니다. 
	 * 중복된 프로그램 날짜가 있는 경우에는 RuntimeException을 발생시켜 Transactional 어노테이션이 작동하도록 합니다.
	 *
	 * @param program     삽입할 프로그램의 정보를 담은 Program 객체
	 * @param programDate 삽입할 프로그램의 날짜 정보를 담은 ProgramDate 객체
	 * @param programImg  삽입할 프로그램의 이미지 정보를 담은 ProgramImg 객체
	 * @param path        프로그램 이미지 파일을 저장할 경로
	 * @throws RuntimeException 중복된 프로그램 날짜가 있는 경우 발생
	 */
	public void addProgram(Program program, ProgramDate programDate, ProgramImg programImg, String path) {

		programMapper.insertProgram(program);

		List<String> dates = programDate.getProgramDates();
		Set<String> datesSet = new HashSet<>(dates);
		if (dates.size() != datesSet.size()) {
			throw new RuntimeException();
		}

		List<ProgramDate> dateList = new ArrayList<>();
		for (String date : dates) {
			ProgramDate dateOne = new ProgramDate();
			dateOne.setProgramNo(program.getProgramNo());
			dateOne.setProgramDate(date);
			dateList.add(dateOne);
		}

		programMapper.insertProgramDates(dateList);

		addProgramImg(programImg.getProgramFile(), path, program.getProgramNo());
	}

	/**
	 * 프로그램 정보를 수정합니다. 이미지 파일이 수정되었을 경우, 기존의 이미지 파일을 삭제하고 새로운 이미지 파일을 저장합니다.
	 *
	 * @param program     수정할 프로그램의 정보를 담은 Program 객체
	 * @param programDate 수정할 프로그램의 날짜 정보를 담은 ProgramDate 객체
	 * @param programImg  수정할 프로그램의 이미지 정보를 담은 ProgramImg 객체
	 * @param newPath     새로운 이미지 파일을 저장할 경로
	 * @param oldPath     기존 이미지 파일의 경로
	 */
	public void modifyProgram(Program program, ProgramDate programDate, ProgramImg programImg, String newPath,
			String oldPath) {

		programMapper.updateProgram(program);
		programMapper.updateProgramDate(programDate);

		MultipartFile programFile = programImg.getProgramFile();

		if (!programFile.isEmpty()) {

			File file = new File(oldPath);
			file.delete();

			int programNo = program.getProgramNo();

			modifyProgramImg(programFile, newPath, programNo);
		}
	}

	/**
	 * 데이터베이스에서 해당 프로그램을 비활성화 상태로 변경하고 1을 리턴합니다.
	 * 
	 * @param programNo 비활성화할 프로그램의 번호
	 * @return 비활성화 상태로 정상적으로 변경되었다면 1
	 */
	public int modifyDeactivation(int programNo) {
		return programMapper.updateToDeactiveProgram(programNo);
	}

	/**
	 * 데이터베이스에서 해당 프로그램을 활성화 상태로 변경하고 1을 리턴합니다.
	 * 
	 * @param programNo 활성화할 프로그램의 번호
	 * @return 활성화 상태로 정상적으로 변경되었다면 1
	 */
	public int modifyActivation(int programNo) {
		return programMapper.updateToActiveProgram(programNo);
	}
	
	/**
	 * 프로그램에 이미지 파일을 추가합니다.
	 * 
	 * @param programFile 추가할 이미지 파일
	 * @param path 이미지 파일을 저장할 경로
	 * @param programNo 이미지가 속한 프로그램의 번호
	 */
	public void addProgramImg(MultipartFile programFile, String path, int programNo) {
		addOrModifyProgramImg(programFile, path, programNo, true);
	}
	
	/**
	 * 프로그램의 이미지 파일을 수정합니다.
	 * 
	 * @param programFile 수정할 새 이미지 파일
	 * @param path 수정된 이미지 파일을 저장할 경로
	 * @param programNo 이미지가 속한 프로그램의 번호
	 */
	public void modifyProgramImg(MultipartFile programFile, String path, int programNo) {
		addOrModifyProgramImg(programFile, path, programNo, false);
	}


	/**
	 * 프로그램 이미지 파일을 데이터베이스에 삽입하거나 수정합니다. 
	 * 주어진 프로그램 번호를 기준으로 이미지 정보를 데이터베이스에 삽입하거나 수정 후, 해당 이미지 파일을 지정된 경로에 저장합니다.
	 *
	 * @param programFile 프로그램 이미지 파일을 나타내는 MultipartFile 객체
	 * @param path        이미지 파일을 저장할 경로
	 * @param programNo   이미지가 속하는 프로그램의 번호
	 * @param isInsert    이미지 정보를 삽입할지 수정할지 여부를 나타내는 boolean 값
	 */
	public void addOrModifyProgramImg(MultipartFile programFile, String path, int programNo, boolean isAdd) {

		HeadofficeImageSaver imgSave = new HeadofficeImageSaver();

		ProgramImg img = new ProgramImg();
		img.setProgramNo(programNo);
		img.setOriginName(programFile.getOriginalFilename());
		img.setProgramImgSize(programFile.getSize());
		img.setProgramImgType(programFile.getContentType());

		String filename = imgSave.getFilename(programFile);
		img.setFilename(filename);

		int result = isAdd ? programMapper.insertProgramImg(img) : programMapper.updateProgramImg(img);

		imgSave.saveFile(programFile, path, filename);
	}
	
	/**
	 * 페이지네이션 정보를 생성하여 페이지네이션 객체를 리턴합니다.
	 *
	 * @param pageNum     현재 페이지 번호
	 * @param customerCnt 고객 수
	 * @return 페이지네이션 정보
	 */
	public HeadofficePagination getPagination(int pageNum, int programCnt) {
		
		HeadofficePagination pagination = HeadofficePagination.builder()
				.numberOfPaginationToShow(10)
				.rowPerPage(8)
				.currentPageNum(pageNum)
				.rowCnt(programCnt)
				.build();
		pagination.calculateProperties();
		
		return pagination;
	}

}
