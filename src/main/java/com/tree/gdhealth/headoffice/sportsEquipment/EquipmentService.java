package com.tree.gdhealth.headoffice.sportsEquipment;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.tree.gdhealth.domain.SportsEquipment;
import com.tree.gdhealth.domain.SportsEquipmentImg;
import com.tree.gdhealth.dto.AddSportsEquipmentDto;
import com.tree.gdhealth.dto.PaginationDto;
import com.tree.gdhealth.dto.UpdateSportsEquipmentDto;
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

	@Transactional(readOnly = true)
	public List<Map<String, Object>> getEquipmentList(int beginRow, int rowPerPage) {	
		PaginationDto paginationDto = new PaginationDto();
		paginationDto.setBeginRow(beginRow);
		paginationDto.setRowPerPage(rowPerPage);
		return equipmentMapper.selectEquipmentList(paginationDto);
	}

	@Transactional(readOnly = true)
	public int getEquipmentCnt() {
		return equipmentMapper.selectEquipmentCnt();
	}

	@Transactional(readOnly = true)
	public List<Map<String, Object>> getEquipmentList(int beginRow, int rowPerPage, String type, String keyword) {
		PaginationDto paginationDto = new PaginationDto();
		paginationDto.setBeginRow(beginRow);
		paginationDto.setRowPerPage(rowPerPage);
		paginationDto.setType(type);
		paginationDto.setKeyword(keyword);
		return equipmentMapper.selectEquipmentList(paginationDto);
	}

	@Transactional(readOnly = true)
	public int getEquipmentCnt(String type, String keyword) {
		return equipmentMapper.selectSearchCnt(type, keyword);
	}

	@Transactional(readOnly = true)
	public Map<String, Object> getEquipmentOne(int equipmentNo) {
		return equipmentMapper.selectEquipmentOne(equipmentNo);
	}

	public int modifyDeactivation(int sportsEquipmentNo) {
		return equipmentMapper.updateToDeactiveEquipment(sportsEquipmentNo);
	}

	public int modifyActivation(int sportsEquipmentNo) {
		return equipmentMapper.updateToActiveEquipment(sportsEquipmentNo);
	}

	public void addEquipment(AddSportsEquipmentDto addSportsEquipmentDto, String path) {

		if (addSportsEquipmentDto.getNote() == null) {
			addSportsEquipmentDto.setNote("");
		}

		SportsEquipment sportsEquipment = SportsEquipment.builder()
											.employeeNo(addSportsEquipmentDto.getEmployeeNo())
											.itemName(addSportsEquipmentDto.getItemName())
											.itemPrice(addSportsEquipmentDto.getItemPrice())
											.note(addSportsEquipmentDto.getNote())
											.build();
		equipmentMapper.insertEquipment(sportsEquipment);

		MultipartFile equipmentFile = addSportsEquipmentDto.getEquipmentFile();
						
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


	public void modifyEquipment(UpdateSportsEquipmentDto updateSportsEquipmentDto, String newPath, String oldPath) {

		SportsEquipment sportsEquipment = SportsEquipment.builder()
											.itemName(updateSportsEquipmentDto.getItemName())
											.itemPrice(updateSportsEquipmentDto.getItemPrice())
											.note(updateSportsEquipmentDto.getNote())
											.sportsEquipmentNo(updateSportsEquipmentDto.getSportsEquipmentNo())
											.build();
		equipmentMapper.updateEquipment(sportsEquipment);

		MultipartFile equipmentFile = updateSportsEquipmentDto.getEquipmentFile();
		if (!equipmentFile.isEmpty()) {
			
			new File(oldPath).delete();
				
			String originalName = equipmentFile.getOriginalFilename();
			String fileName = ImageSaveUtil.getFileName(originalName);

			SportsEquipmentImg img = SportsEquipmentImg.builder()
										.sportsEquipmentNo(updateSportsEquipmentDto.getSportsEquipmentNo())
										.sportsEquipmentImgOriginName(originalName)
										.sportsEquipmentImgSize(equipmentFile.getSize())
										.sportsEquipmentImgType(ImageType.fromText(equipmentFile.getContentType()))
										.sportsEquipmentImgFileName(fileName)
										.build();
			equipmentMapper.updateEquipmentImg(img);

			ImageSaveUtil.saveFile(equipmentFile, newPath, fileName);
		}
	}

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
