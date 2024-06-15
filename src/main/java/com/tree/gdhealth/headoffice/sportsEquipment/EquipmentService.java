package com.tree.gdhealth.headoffice.sportsEquipment;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.tree.gdhealth.domain.SportsEquipment;
import com.tree.gdhealth.domain.SportsEquipmentImg;
import com.tree.gdhealth.dto.AddSportsEquipmentServiceDto;
import com.tree.gdhealth.dto.PaginationDto;
import com.tree.gdhealth.dto.UpdateSportsEquipmentServiceDto;
import com.tree.gdhealth.utils.enumtype.ImageType;
import com.tree.gdhealth.utils.imagesave.ImageSaveUtil;
import com.tree.gdhealth.utils.pagination.HeadofficePagination;

import lombok.RequiredArgsConstructor;

/**
 * @author 진관호
 */
@RequiredArgsConstructor
@Transactional
@Service
public class EquipmentService {

	private final EquipmentMapper equipmentMapper;

	/**
	 * 전체 물품 목록을 리턴합니다.
	 * 
	 * @param beginRow   해당 페이지 내에서의 첫번째 물품
	 * @param rowPerPage 한 페이지에 나타낼 물품 수
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<Map<String, Object>> getEquipmentList(int beginRow, int rowPerPage) {	
		PaginationDto paginationDto = new PaginationDto();
		paginationDto.setBeginRow(beginRow);
		paginationDto.setRowPerPage(rowPerPage);
		return equipmentMapper.selectEquipmentList(paginationDto);
	}

	/**
	 * 전체 물품의 개수를 리턴합니다.
	 * 
	 * @return 전체 물품의 수
	 */
	@Transactional(readOnly = true)
	public int getEquipmentCnt() {
		return equipmentMapper.selectEquipmentCnt();
	}

	/**
	 * 검색 조건을 만족하는 물품 목록을 리턴합니다.
	 * 
	 * @param beginRow   해당 페이지 내에서의 첫번째 물품
	 * @param rowPerPage 한 페이지에 나타낼 물품의 수
	 * @param type       검색할 keyword의 속성(itemName,note...)
	 * @param keyword    검색 내용
	 * @return 검색 후의 물품 목록
	 */
	@Transactional(readOnly = true)
	public List<Map<String, Object>> getEquipmentList(int beginRow, int rowPerPage, String type, String keyword) {
		PaginationDto paginationDto = new PaginationDto();
		paginationDto.setBeginRow(beginRow);
		paginationDto.setRowPerPage(rowPerPage);
		paginationDto.setType(type);
		paginationDto.setKeyword(keyword);
		return equipmentMapper.selectEquipmentList(paginationDto);
	}

	/**
	 * 검색 조건을 만족하는 물품 개수를 리턴합니다.
	 * 
	 * @param type    검색할 keyword의 속성(itemName,note...)
	 * @param keyword 검색 내용
	 * @return 검색 조건을 만족하는 물품 개수
	 */
	@Transactional(readOnly = true)
	public int getEquipmentCnt(String type, String keyword) {
		return equipmentMapper.selectSearchCnt(type, keyword);
	}

	/**
	 * 물품 상세 정보를 리턴합니다.
	 * 
	 * @param equipmentNo 조회할 물품의 번호
	 * @return 물품의 상세 정보
	 */
	@Transactional(readOnly = true)
	public Map<String, Object> getEquipmentOne(int equipmentNo) {
		return equipmentMapper.selectEquipmentOne(equipmentNo);
	}

	/**
	 * 데이터베이스에서 해당 물품을 비활성화 상태로 변경하고 1을 리턴합니다.
	 * 
	 * @param sportsEquipmentNo 비활성화할 물품의 번호
	 * @return 비활성화 상태로 정상적으로 변경되었다면 1
	 */
	public int modifyDeactivation(int sportsEquipmentNo) {
		return equipmentMapper.updateToDeactiveEquipment(sportsEquipmentNo);
	}

	/**
	 * 데이터베이스에서 해당 물품을 활성화 상태로 변경하고 1을 리턴합니다.
	 * 
	 * @param sportsEquipmentNo 활성화할 물품의 번호
	 * @return 활성화 상태로 정상적으로 변경되었다면 1
	 */
	public int modifyActivation(int sportsEquipmentNo) {
		return equipmentMapper.updateToActiveEquipment(sportsEquipmentNo);
	}

	/**
	 * 물품 정보를 데이터베이스에 삽입합니다.
	 * 
	 * @param addSportsEquipmentDto 물품을 추가하기 위해 필요한 데이터를 전송하기 위한 객체
	 * @param path                  물품 이미지 파일을 저장할 경로
	 * @apiNote 물품의 메모(note)가 null인 경우 빈 문자열로 설정하여 데이터베이스에 저장합니다.
	 */
	public void addEquipment(AddSportsEquipmentServiceDto addSportsEquipmenServicetDto, String path) {

		if (addSportsEquipmenServicetDto.getNote() == null) {
			addSportsEquipmenServicetDto.setNote("");
		}

		SportsEquipment sportsEquipment = SportsEquipment.builder()
											.employeeNo(addSportsEquipmenServicetDto.getEmployeeNo())
											.itemName(addSportsEquipmenServicetDto.getItemName())
											.itemPrice(addSportsEquipmenServicetDto.getItemPrice())
											.note(addSportsEquipmenServicetDto.getNote())
											.build();
		equipmentMapper.insertEquipment(sportsEquipment);

		MultipartFile equipmentFile = addSportsEquipmenServicetDto.getEquipmentFile();
						
		String originalName = equipmentFile.getOriginalFilename();
		String fileName = ImageSaveUtil.getFileName(originalName);

		SportsEquipmentImg img = SportsEquipmentImg.builder()
									.sportsEquipmentNo(sportsEquipment.getSportsEquipmentNo())
									.sportsEquipmentImgOriginName(originalName)
									.sportsEquipmentImgSize(equipmentFile.getSize())
									.sportsEquipmentImgType(ImageType.fromText(equipmentFile.getContentType()))
									.sportsEquipmentImgFileName(fileName)
									.build();
		equipmentMapper.insertEquipmentImg(img);

		ImageSaveUtil.saveFile(equipmentFile, path, fileName);
	}

	/**
	 * 물품 정보를 수정합니다. 이미지 파일이 수정되었을 경우, 기존의 이미지 파일을 삭제하고 새로운 이미지 파일을 저장합니다.
	 * 
	 * @param addSportsEquipmentDto 물품을 추가하기 위해 필요한 데이터를 전송하기 위한 객체
	 * @param newPath               새로운 이미지 파일을 저장할 경로
	 * @param oldPath               기존 이미지 파일의 경로
	 */
	public void modifyEquipment(UpdateSportsEquipmentServiceDto updateSportsEquipmentServiceDto, String newPath, String oldPath) {

		SportsEquipment sportsEquipment = SportsEquipment.builder()
											.itemName(updateSportsEquipmentServiceDto.getItemName())
											.itemPrice(updateSportsEquipmentServiceDto.getItemPrice())
											.note(updateSportsEquipmentServiceDto.getNote())
											.sportsEquipmentNo(updateSportsEquipmentServiceDto.getSportsEquipmentNo())
											.build();
		equipmentMapper.updateEquipment(sportsEquipment);

		MultipartFile equipmentFile = updateSportsEquipmentServiceDto.getEquipmentFile();
		if (!equipmentFile.isEmpty()) {
			
			new File(oldPath).delete();
				
			String originalName = equipmentFile.getOriginalFilename();
			String fileName = ImageSaveUtil.getFileName(originalName);

			SportsEquipmentImg img = SportsEquipmentImg.builder()
										.sportsEquipmentNo(updateSportsEquipmentServiceDto.getSportsEquipmentNo())
										.sportsEquipmentImgOriginName(originalName)
										.sportsEquipmentImgSize(equipmentFile.getSize())
										.sportsEquipmentImgType(ImageType.fromText(equipmentFile.getContentType()))
										.sportsEquipmentImgFileName(fileName)
										.build();
			equipmentMapper.updateEquipmentImg(img);

			ImageSaveUtil.saveFile(equipmentFile, newPath, fileName);
		}
	}
	
	/**
	 * 페이지네이션 정보를 생성하여 페이지네이션 객체를 리턴합니다.
	 *
	 * @param pageNum     현재 페이지 번호
	 * @param customerCnt 고객 수
	 * @return 페이지네이션 정보
	 */
	public HeadofficePagination getPagination(int pageNum, int equipmentCnt) {

		HeadofficePagination pagination = HeadofficePagination.builder()
											.numberOfPaginationToShow(10)
											.rowPerPage(8)
											.currentPageNum(pageNum)
											.rowCnt(equipmentCnt).build();
		pagination.calculateProperties();

		return pagination;
	}

}
