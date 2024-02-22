package com.tree.gdhealth.utils;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ImageSave {

	public String getFilename(MultipartFile multipartFile) {

		// fileName : 임의의 문자 생성(유일한 식별자)
		String uName = UUID.randomUUID().toString(); // 파일이름

		String oName = multipartFile.getOriginalFilename();
		// lastIndexOf : parameter로 전달받은 문자열을 원본 문자열의 뒤에서부터 탐색하여,
		// 처음으로 파라미터의 문자열이 나오는 index를 리턴한다.
		// 확장자 구하기 : xx.xxx.pdf -> .pdf
		String extName = oName.substring(oName.lastIndexOf("."));
		log.debug("확장자 : " + extName);

		// 이미지 파일이 아니면 rollback
		if (!(extName.equals(".png") || extName.equals(".jpg") || extName.equals(".jpeg") || extName.equals(".gif")
				|| extName.equals(".webp"))) {
			// 강제로 예외를 발생시켜 애노테이션 @Transactional이 작동되게 한다.
			throw new RuntimeException();
		}

		return uName + extName;

	}

	public void saveFile(MultipartFile multipartFile, String path, String filename) {

		// 물리적file을 원하는 경로(path)에 저장
		File file = new File(path + "/" + filename); // 빈파일
		try {
			multipartFile.transferTo(file); // 물리적으로 파일 업로드가 됨.
		} catch (IllegalStateException | IOException e) {
			// 강제로 예외를 발생시켜 애노테이션 @Transactional이 작동되게 한다.
			throw new RuntimeException();
		}
	}

}
