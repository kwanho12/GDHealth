package com.tree.gdhealth.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddSportsEquipmentServiceDto {
	Integer employeeNo;
    private String itemName;
    private String note;
    private Integer itemPrice;
    private MultipartFile equipmentFile;
}
