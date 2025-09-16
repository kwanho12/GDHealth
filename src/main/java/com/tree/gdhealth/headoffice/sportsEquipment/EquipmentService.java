package com.tree.gdhealth.headoffice.sportsEquipment;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.tree.gdhealth.domain.SportsEquipmentImg;
import com.tree.gdhealth.utils.enumtype.ImageType;
import com.tree.gdhealth.utils.exception.ImageNotDeleteException;
import com.tree.gdhealth.utils.imagesave.ImageSaveUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.tree.gdhealth.domain.SportsEquipment;
import com.tree.gdhealth.dto.AddSportsEquipmentDto;
import com.tree.gdhealth.dto.PaginationDto;
import com.tree.gdhealth.dto.UpdateSportsEquipmentDto;
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
    private final ImageSaveUtil imageSaveUtil;

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

    public void addEquipment(AddSportsEquipmentDto dto, String path) {

        if (dto.getNote() == null) {
            dto.setNote("");
        }

        SportsEquipment equipment = SportsEquipment.builder()
                .employeeNo(dto.getEmployeeNo())
                .itemName(dto.getItemName())
                .itemPrice(dto.getItemPrice())
                .note(dto.getNote())
                .build();
        equipmentMapper.insertEquipment(equipment);

        MultipartFile file = dto.getEquipmentFile();
        if (file != null && !file.isEmpty()) {
            saveEquipmentImg(file, path, equipment.getSportsEquipmentNo(), true);
        }
    }

    public void modifyEquipment(UpdateSportsEquipmentDto dto, String newPath, String oldPath) {

        SportsEquipment sportsEquipment = SportsEquipment.builder()
                .itemName(dto.getItemName())
                .itemPrice(dto.getItemPrice())
                .note(dto.getNote())
                .sportsEquipmentNo(dto.getSportsEquipmentNo())
                .build();
        equipmentMapper.updateEquipment(sportsEquipment);

        MultipartFile file = dto.getEquipmentFile();
        if (file != null && !file.isEmpty()) {
            try {
                Files.delete(Paths.get(oldPath));
            } catch (IOException e) {
                throw new ImageNotDeleteException("기존 장비 이미지를 삭제하지 못했습니다.", e);
            }
            saveEquipmentImg(file, newPath, dto.getSportsEquipmentNo(), false);
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

    private void saveEquipmentImg(MultipartFile file, String path, Integer equipmentNo, boolean isInsert) {
        String originalName = Optional.ofNullable(file.getOriginalFilename())
                .orElseThrow(() -> new IllegalArgumentException("파일 이름이 없습니다."));
        String fileName = imageSaveUtil.getFileName(originalName);

        SportsEquipmentImg img = SportsEquipmentImg.builder()
                .sportsEquipmentNo(equipmentNo)
                .sportsEquipmentImgOriginName(originalName)
                .sportsEquipmentImgSize(file.getSize())
                .sportsEquipmentImgType(ImageType.fromText(file.getContentType()))
                .sportsEquipmentImgFileName(fileName)
                .build();

        if (isInsert) {
            equipmentMapper.insertEquipmentImg(img);
        } else {
            equipmentMapper.updateEquipmentImg(img);
        }

        imageSaveUtil.saveFileToS3(file, fileName, "equipment");
    }
}
