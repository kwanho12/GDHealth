package com.tree.gdhealth.domain;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class ReviewForm {
	private String reviewTitle;
	private String reviewContent;
	private List<MultipartFile> reviewfileList;
}
