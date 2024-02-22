package com.tree.gdhealth.headoffice.sportsEquipment;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.tree.gdhealth.vo.SportsEquipment;
import com.tree.gdhealth.vo.SportsEquipmentImg;

@Mapper
public interface EquipmentMapper {

	List<Map<String, Object>> equipmentList(Map<String, Object> map);

	int equipmentCnt();

	int searchCnt(Map<String, Object> map);

	Map<String, Object> equipmentOne(int equipmentNo);

	int insertEquipment(SportsEquipment sportsEquipment);

	int insertEquipmentImg(SportsEquipmentImg sportsEquipmentImg);

	int updateEquipment(SportsEquipment sportsEquipment);

	int updateEquipmentImg(SportsEquipmentImg sportsEquipmentImg);

	int deactiveEquipment(int sportsEquipmentNo);

	int activeEquipment(int sportsEquipmentNo);

}
