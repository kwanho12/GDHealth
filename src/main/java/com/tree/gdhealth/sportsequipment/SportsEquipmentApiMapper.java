package com.tree.gdhealth.sportsequipment;

import com.tree.gdhealth.dto.SportsEquipment;
import com.tree.gdhealth.dto.SportsEquipmentImg;
import com.tree.gdhealth.sportsequipment.dto.SportsEquipmentOrderAddRequest;
import com.tree.gdhealth.sportsequipment.dto.SportsEquipmentOrderInformation;
import com.tree.gdhealth.sportsequipment.dto.SportsEquipmentOrderRetrieveCriteria;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
//@autor 정인호

@Mapper
public interface SportsEquipmentApiMapper {
    List<SportsEquipment> selectSportsEquipmentAll();
    SportsEquipment selectSportsEquipmentOne(int sportsEquipmentNo);

    List<SportsEquipmentOrderInformation> selectSportsEquipmentOrderListByCriteria(SportsEquipmentOrderRetrieveCriteria criteria);

    int countSportsEquipmentOrderListLastPage(SportsEquipmentOrderRetrieveCriteria criteria);

    int insertSportsEquipmentOrder(SportsEquipmentOrderAddRequest reqDto);

    SportsEquipmentOrderInformation selectSportsEquipmentOrderOneByOrderNo(@Param("orderNo") Integer orderNo);

    int updateSportsEquipmentOrderStatus(@Param("orderNo") Integer orderNo, @Param("changeOrderStatus") String changeOrderStatus);

    SportsEquipmentImg selectSportsEquipmentImage(int sportsEquipmentNo);
}
