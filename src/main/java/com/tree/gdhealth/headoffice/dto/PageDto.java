package com.tree.gdhealth.headoffice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
public class PageDto {
	private String type;
	private String keyword;
	private int pageNum;
}
