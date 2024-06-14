package com.tree.gdhealth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaginationDto {
	int beginRow;
	int rowPerPage;
	String type;
	String keyword;
}
