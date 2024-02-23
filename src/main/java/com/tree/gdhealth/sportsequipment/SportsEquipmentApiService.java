package com.tree.gdhealth.sportsequipment;

import com.tree.gdhealth.dto.SportsEquipment;
import com.tree.gdhealth.dto.SportsEquipmentImg;
import com.tree.gdhealth.sportsequipment.dto.SportsEquipmentOrderAddRequest;
import com.tree.gdhealth.sportsequipment.dto.SportsEquipmentOrderInformation;
import com.tree.gdhealth.sportsequipment.dto.SportsEquipmentOrderRetrieveCriteria;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**<p>sportsEquipment domain영역을 담담하는 service </p>
 * @author 정인호
 */
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class SportsEquipmentApiService {
    private final SportsEquipmentApiMapper sportsEquipmentApiMapper;

    public List<SportsEquipment> getSportsEquipmentList() {
        return sportsEquipmentApiMapper.selectSportsEquipmentAll();
    }

    public SportsEquipment getSportsEquipmentOne(int sportsEquipmentNo) {
        return sportsEquipmentApiMapper.selectSportsEquipmentOne(sportsEquipmentNo);
    }

    public SportsEquipmentOrderInformation getSportsEquipmentOrderOne(Integer orderNo) {
        return sportsEquipmentApiMapper.selectSportsEquipmentOrderOneByOrderNo(orderNo);
    }

    /**
     * <p>조회조건에 맞는 발주리스트를 가져옵니다.</p>
     * @param criteria 조회조건
     */
    public List<SportsEquipmentOrderInformation> getSportsEquipmentOrderList(SportsEquipmentOrderRetrieveCriteria criteria){
        return sportsEquipmentApiMapper.selectSportsEquipmentOrderListByCriteria(criteria);
    }

    /** 조회조건에 맞는 발주리스트의 총페이지수를 구합니다.
     * @param criteria- 조회조건
     */
    public int getSportsEquipmentOrderListLastPage(SportsEquipmentOrderRetrieveCriteria criteria) {

        return sportsEquipmentApiMapper.countSportsEquipmentOrderListLastPage(criteria);
    }

    /**물품 이미지의 저장파일명을 조회
     */
    public String getSportsEquipmentImagePath(int sportsEquipmentNo) {
        return Optional.ofNullable(sportsEquipmentApiMapper.selectSportsEquipmentImage(sportsEquipmentNo)).map(SportsEquipmentImg::getSportsEquipmentImgFileName).orElse(null);

    }

    /**
     * <p>발주하나를 추가합니다.</p>
     * @param reqDto 발주정보가 담긴 객체
     */
    @Transactional
    public boolean addSportsEquipmentOrder(SportsEquipmentOrderAddRequest reqDto) {
        int affectedRows = sportsEquipmentApiMapper.insertSportsEquipmentOrder(reqDto);
        return affectedRows == 1;
    }

    /**
     * <p>발주상태를 변경합니다.</p>
     * @param changeOrderStatus
     */
    @Transactional
    public boolean changeSportsEquipmentOrderStatus(Integer orderNo, String changeOrderStatus) {
        int affectedRows = sportsEquipmentApiMapper.updateSportsEquipmentOrderStatus(orderNo, changeOrderStatus);
        return affectedRows == 1;
    }
}
