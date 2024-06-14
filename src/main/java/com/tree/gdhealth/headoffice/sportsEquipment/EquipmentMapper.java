package com.tree.gdhealth.headoffice.sportsEquipment;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.tree.gdhealth.domain.SportsEquipment;
import com.tree.gdhealth.domain.SportsEquipmentImg;
import com.tree.gdhealth.dto.PaginationDto;

/**
 * @author 진관호
 */
@Mapper
public interface EquipmentMapper {

	List<Map<String, Object>> selectEquipmentList(PaginationDto paginationDto);

	int selectEquipmentCnt();

	int selectSearchCnt(@Param("type") String type, @Param("keyword") String keyword);

	Map<String, Object> selectEquipmentOne(int equipmentNo);

	int insertEquipment(SportsEquipment sportsEquipment);

	int insertEquipmentImg(SportsEquipmentImg sportsEquipmentImg);

	int updateEquipment(SportsEquipment sportsEquipment);

	int updateEquipmentImg(SportsEquipmentImg sportsEquipmentImg);

	int updateToDeactiveEquipment(int sportsEquipmentNo);

	int updateToActiveEquipment(int sportsEquipmentNo);

}
