package com.tree.gdhealth.domain;

import com.tree.gdhealth.utils.enumtype.ImageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class SportsEquipmentImg {

	private int sportsEquipmentImgNo;
	private int sportsEquipmentNo;
	private String sportsEquipmentImgOriginName;
	private String sportsEquipmentImgFileName;
	private long sportsEquipmentImgSize;
	private ImageType sportsEquipmentImgType;
	private LocalDateTime createdate;
	private LocalDateTime updatedate;

}
