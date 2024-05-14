package com.tree.gdhealth.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SportsEquipment {
	
    private int sportsEquipmentNo;
    private int employeeNo;
    private String itemName;
    private Integer itemPrice;
    private String note;
    private LocalDateTime createdate;
    private LocalDateTime updatedate;
    
}
