package com.tree.gdhealth.domain;

import com.tree.gdhealth.utils.enumtype.ImageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
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
