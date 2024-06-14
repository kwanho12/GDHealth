package com.tree.gdhealth.domain;

import com.tree.gdhealth.utils.enumtype.ImageType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class SportsEquipmentImg {

	private Integer sportsEquipmentImgNo;
	private int sportsEquipmentNo;
	private String sportsEquipmentImgOriginName;
	private String sportsEquipmentImgFileName;
	private long sportsEquipmentImgSize;
	private ImageType sportsEquipmentImgType;
	private LocalDateTime createdate;
	private LocalDateTime updatedate;

}
