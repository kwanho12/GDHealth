package com.tree.gdhealth.domain;

import com.tree.gdhealth.utils.enumtype.ImageType;

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
