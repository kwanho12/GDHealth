package com.tree.gdhealth.headoffice.program;

import java.util.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.tree.gdhealth.domain.Program;
import com.tree.gdhealth.domain.ProgramDate;
import com.tree.gdhealth.domain.ProgramImg;
import com.tree.gdhealth.dto.AddProgramDto;
import com.tree.gdhealth.dto.PaginationDto;
import com.tree.gdhealth.dto.UpdateProgramDto;
import com.tree.gdhealth.utils.exception.DatesDuplicatedException;
import com.tree.gdhealth.utils.imagesave.ImageSaveUtil;
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
    private final ImageSaveUtil imageSaveUtil;

	@Transactional(readOnly = true)
	public List<Map<String, Object>> getProgramList(int beginRow, int rowPerPage) {
		PaginationDto paginationDto = new PaginationDto();
		paginationDto.setBeginRow(beginRow);
		paginationDto.setRowPerPage(rowPerPage);
		return programMapper.selectProgramList(paginationDto);
	}

	@Transactional(readOnly = true)
	public int getProgramCnt() {
		return programMapper.selectProgramCnt();
	}

	@Transactional(readOnly = true)
	public List<Map<String, Object>> getProgramList(int beginRow, int rowPerPage, String type, String keyword) {
		PaginationDto paginationDto = new PaginationDto();
		paginationDto.setBeginRow(beginRow);
		paginationDto.setRowPerPage(rowPerPage);
		paginationDto.setType(type);
		paginationDto.setKeyword(keyword);
		return programMapper.selectProgramList(paginationDto);
	}

	@Transactional(readOnly = true)
	public int getProgramCnt(String type, String keyword) {
		return programMapper.selectSearchCnt(type, keyword);
	}

	@Transactional(readOnly = true)
	public Map<String, Object> getProgramOne(Integer programNo, String date) {
		ProgramDate programDate = ProgramDate.builder()
									.programNo(programNo)
									.programDate(date)
									.build();
		return programMapper.selectProgramOne(programDate);
	}

	@Transactional(readOnly = true)
	public boolean getResultOfDatesCheck(List<String> programDates) {
		return programMapper.selectResultOfDatesCheck(programDates);
	}

	@Transactional(readOnly = true)
	public boolean getResultOfDateOneCheck(String programDate) {
		return programMapper.selectResultOfDateOneCheck(programDate);
	}

	public void addProgram(AddProgramDto addProgramDto, String path) {
		
		Program program = Program.builder()
							.employeeNo(addProgramDto.getEmployeeNo())
							.programName(addProgramDto.getProgramName())
							.programDetail(addProgramDto.getProgramDetail())
							.programMaxCustomer(addProgramDto.getProgramMaxCustomer())
							.build();
		programMapper.insertProgram(program);

		List<String> dates = addProgramDto.getProgramDates();
		Set<String> datesSet = new HashSet<>(dates);
		if (dates.size() != datesSet.size()) {
			throw new DatesDuplicatedException("선택한 날짜들 중에서 중복된 프로그램 날짜가 존재합니다.");
		}

		List<ProgramDate> dateList = new ArrayList<>();
		for (String date : dates) {
			ProgramDate dateOne = ProgramDate.builder()
									.programNo(program.getProgramNo())
									.programDate(date)
									.build();
			dateList.add(dateOne);
		}
		programMapper.insertProgramDates(dateList);

		addProgramImg(addProgramDto.getProgramFile(), path, program.getProgramNo());
	}

    public void modifyProgram(UpdateProgramDto updateProgramDto, String newPath, String oldPath) {
        updateProgramInfo(updateProgramDto);

        MultipartFile programFile = updateProgramDto.getProgramFile();
        if (programFile != null && !programFile.isEmpty()) {
            modifyProgramImage(programFile, newPath, oldPath, updateProgramDto.getProgramNo());
        }
    }

	public void modifyDeactivation(int programNo) {
        programMapper.updateToDeactiveProgram(programNo);
    }

	public void modifyActivation(int programNo) {
        programMapper.updateToActiveProgram(programNo);
    }

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

    private void addProgramImg(MultipartFile programFile, String path, Integer programNo) {

        String originalName = programFile.getOriginalFilename();
        String fileName = imageSaveUtil.getFileName(Objects.requireNonNull(originalName));

        ProgramImg img = ProgramImg.builder()
                .programNo(programNo)
                .originName(originalName)
                .programImgSize(programFile.getSize())
                .programImgType(programFile.getContentType())
                .filename(fileName)
                .build();
        programMapper.insertProgramImg(img);
        imageSaveUtil.saveFileToS3(programFile, fileName, "program");
    }


    private void updateProgramInfo(UpdateProgramDto dto) {
        Program program = Program.builder()
                .programNo(dto.getProgramNo())
                .employeeNo(dto.getWriterEmployeeNo())
                .programName(dto.getProgramName())
                .programDetail(dto.getProgramDetail())
                .programMaxCustomer(dto.getProgramMaxCustomer())
                .build();
        programMapper.updateProgram(program);

        ProgramDate programDate = ProgramDate.builder()
                .programDate(dto.getProgramDate())
                .programNo(dto.getProgramNo())
                .originDate(dto.getOriginDate())
                .build();
        programMapper.updateProgramDate(programDate);
    }

    private void modifyProgramImage(MultipartFile programFile, String newPath, String oldPath, Integer programNo) {
        String newOriginalName = Objects.requireNonNull(programFile.getOriginalFilename());
        String newFileName = imageSaveUtil.getFileName(newOriginalName);

        ProgramImg newImg = ProgramImg.builder()
                .programNo(programNo)
                .originName(newOriginalName)
                .programImgSize(programFile.getSize())
                .programImgType(programFile.getContentType())
                .filename(newFileName)
                .build();
        programMapper.updateProgramImg(newImg);
        imageSaveUtil.saveFileToS3(programFile, newFileName, "program");
    }
}
