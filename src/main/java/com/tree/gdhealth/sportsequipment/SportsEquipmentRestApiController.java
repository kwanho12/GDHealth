package com.tree.gdhealth.sportsequipment;

import com.tree.gdhealth.domain.SportsEquipment;
import com.tree.gdhealth.utils.ResponseEntityGenerator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**<p>sportEquipmet에 대한 restApiController</p>
 * @author 정인호
 */
@Slf4j
@RequiredArgsConstructor
@RestController
public class SportsEquipmentRestApiController {
    private final SportsEquipmentApiService service;
    private final ResponseEntityGenerator responseEntityGenerator;

    /**<p>모든 물품리스트 get</p>
     */
    @GetMapping("/api/v1/sportsEquipment")
    public ResponseEntity<Object> getSportsEquipmentList(){
        return responseEntityGenerator.respGET(service.getSportsEquipmentList());
    }

    /**<p>특정 물품 하나를 Get</p>
     */
    @GetMapping("/api/v1/sportsEquipment/{sportsEquipmentNo}")
    public ResponseEntity<Object> getSportsEquipmentOne(@PathVariable int sportsEquipmentNo){
        return responseEntityGenerator.respGET(service.getSportsEquipmentOne(sportsEquipmentNo));
    }

    /**특정물품을 나타내는 이미지의 저장된 파일이름 Get
     */
    @GetMapping("/api/v1/sportsEquipmentImage/{sportsEquipmentNo}")
    public ResponseEntity<Object> getSportsEquipmentImagePath(@PathVariable int sportsEquipmentNo){
        return responseEntityGenerator.respGET(service.getSportsEquipmentImagePath(sportsEquipmentNo));
    }
}
