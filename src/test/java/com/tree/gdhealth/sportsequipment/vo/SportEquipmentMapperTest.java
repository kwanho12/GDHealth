package com.tree.gdhealth.sportsequipment.vo;

import org.apache.ibatis.annotations.Mapper;

import com.tree.gdhealth.dto.SportsEquipment;
import com.tree.gdhealth.dto.SportsEquipmentImg;
import com.tree.gdhealth.dto.SportsEquipmentOrder;

/**
 * vo 의 db 연동성 유닛테스트를 위한 테스트 매퍼
 * @author 정인호
 */
@Mapper
public interface SportEquipmentMapperTest {
    int insertSportsEquipment(SportsEquipment sportsEquipment);
    SportsEquipment findSportsEquipmentByNo(int sportsEquipmentNo);

    int insertSportsEquipmentOrder(SportsEquipmentOrder sportsEquipmentOrder);
    SportsEquipmentOrder findSportsEquipmentOrderByNo(int sportsEquipmentOrderNo);

    int insertSportsEquipmentImg(SportsEquipmentImg sportsEquipmentImg);

    SportsEquipmentImg findSportsEquipmentImgByNo(int sportEquipmentImgNO);

}
