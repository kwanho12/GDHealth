package com.tree.gdhealth.headoffice.sportsEquipment;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.tree.gdhealth.dto.SportsEquipment;
import com.tree.gdhealth.dto.SportsEquipmentImg;

/**
 * @author 진관호
 */
@Mapper
public interface EquipmentMapper {

	List<Map<String, Object>> selectEquipmentList(Map<String, Object> map);

	int selectEquipmentCnt();

	int selectSearchCnt(Map<String, Object> map);

	Map<String, Object> selectEquipmentOne(int equipmentNo);

	int insertEquipment(SportsEquipment sportsEquipment);

	int insertEquipmentImg(SportsEquipmentImg sportsEquipmentImg);

	int updateEquipment(SportsEquipment sportsEquipment);

	int updateEquipmentImg(SportsEquipmentImg sportsEquipmentImg);

	int updateToDeactiveEquipment(int sportsEquipmentNo);

	int updateToActiveEquipment(int sportsEquipmentNo);

}
