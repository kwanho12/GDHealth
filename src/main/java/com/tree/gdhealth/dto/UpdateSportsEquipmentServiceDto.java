package com.tree.gdhealth.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateSportsEquipmentServiceDto {
	int sportsEquipmentNo;
	private String sportsEquipmentImgFileName;
	private String itemName;
	private Integer itemPrice;
	private MultipartFile equipmentFile;
	private String note;
}
