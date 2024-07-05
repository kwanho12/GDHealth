package com.tree.gdhealth.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class SportsEquipment {

	private Integer sportsEquipmentNo;
	private int employeeNo;
	private String itemName;
	private Integer itemPrice;
	private String note;
	private LocalDateTime createdate;
	private LocalDateTime updatedate;

}
