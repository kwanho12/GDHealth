package com.tree.gdhealth.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class SportsEquipment {
	
    private int sportsEquipmentNo;
    private int employeeNo;
    private String itemName;
    private Integer itemPrice;
    private String note;
    private LocalDateTime createdate;
    private LocalDateTime updatedate;
    
}
