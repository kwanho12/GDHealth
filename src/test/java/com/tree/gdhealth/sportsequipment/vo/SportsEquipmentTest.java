package com.tree.gdhealth.sportsequipment.vo;

import com.tree.gdhealth.dto.SportsEquipment;
import com.tree.gdhealth.dto.SportsEquipmentImg;
import com.tree.gdhealth.dto.SportsEquipmentOrder;
import com.tree.gdhealth.utils.enumtype.ImageType;
import com.tree.gdhealth.utils.enumtype.OrderStatus;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

/**<p>Vo와 마이바티스간의 타입 안정성 확인을 위한 테스트</p>
 * @author 정인호
 */
@Slf4j
@SpringBootTest
class SportsEquipmentTest {
    @Autowired
    SportEquipmentMapperTest sportEquipmentMapperTest;

    @Transactional
    @Test
    void SportsEquipmentVo가_DB에_입력되고_조회된다(){
        //given
        SportsEquipment insertVo = SportsEquipment.builder()
                .itemPrice(500).itemName("testName").note("testNote").build();
        SportsEquipment selectVo;

        //when
        sportEquipmentMapperTest.insertSportsEquipment(insertVo);
        selectVo = sportEquipmentMapperTest.findSportsEquipmentByNo(insertVo.getSportsEquipmentNo());

        //then
        log.debug(selectVo.toString());
        assertEquals(selectVo.getSportsEquipmentNo(), insertVo.getSportsEquipmentNo());

    }

    /**
     * 시드 데이터로 테스트를 수행하므로 //given 의 해당 데이터가 변경되면 테스트가 실패할 우려가 있습니다.
     */
    @Transactional
    @Test
    void SportsEquipmentOrderVo가_DB에_입력되고_조회된다(){
        //given
        SportsEquipmentOrder insertVo = SportsEquipmentOrder.builder()
                .sportsEquipmentNo(1)
                .employeeOrderer(2)
                .branchNo(2)
                .quantity(1)
                .totalPrice(1240000)
                .employeeApprover(1)
                .orderStatus(OrderStatus.WAITING).build();

        SportsEquipmentOrder selectVo;

        //when
        int affectedRows = sportEquipmentMapperTest.insertSportsEquipmentOrder(insertVo);
        selectVo = sportEquipmentMapperTest.findSportsEquipmentOrderByNo(insertVo.getOrderNo());
        insertVo.setCreatedate(selectVo.getCreatedate());
        insertVo.setUpdatedate(selectVo.getUpdatedate());

        //then
        log.debug(selectVo.toString());
        assertEquals(1, affectedRows);
        assertEquals(insertVo.getOrderNo(), selectVo.getOrderNo());
        assertEquals(selectVo, insertVo);
    }
    @Transactional
    @Test
    void sportsEquipmentImgVo가_DB입력되고_조회된다(){
        //given
        SportsEquipmentImg insertVo = SportsEquipmentImg.builder()
                .sportsEquipmentNo(1)
                .sportsEquipmentImgSize(4)
                .sportsEquipmentImgType(ImageType.JPG)
                .sportsEquipmentImgFileName("testFilename")
                .sportsEquipmentImgOriginName("testFileOriginName")
                .build();
        SportsEquipmentImg selectVo = null;

        //when
        int affectedRows = sportEquipmentMapperTest.insertSportsEquipmentImg(insertVo);
        selectVo = sportEquipmentMapperTest.findSportsEquipmentImgByNo(insertVo.getSportsEquipmentImgNo());
        insertVo.setCreatedate(selectVo.getCreatedate());
        insertVo.setUpdatedate(selectVo.getUpdatedate());

        //then
        log.debug(insertVo.toString());
        log.debug(selectVo.toString());
        assertEquals(insertVo, selectVo);
    }

}