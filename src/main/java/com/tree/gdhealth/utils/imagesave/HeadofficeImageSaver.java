package com.tree.gdhealth.utils.imagesave;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

/**
 * 이미지 파일과 관련된 작업을 수행하는 유틸리티 클래스
 * 
 * @author 진관호
 */
public class HeadofficeImageSaver {

	/**
	 * {@link MultipartFile} 객체로부터 고유한 파일 이름을 생성하여 반환합니다.
	 * 
	 * @param multipartFile MultipartFile 객체
	 * @return 고유한 파일 이름
	 * @throws RuntimeException 이미지 파일이 아닌 경우
	 */
	public String getFilename(MultipartFile multipartFile) {

		String uuidName = UUID.randomUUID().toString();
		String originalName = multipartFile.getOriginalFilename();
		String fileNameExtension = originalName.substring(originalName.lastIndexOf("."));

		if (!(fileNameExtension.equals(".png") || fileNameExtension.equals(".jpg") || fileNameExtension.equals(".jpeg")
				|| fileNameExtension.equals(".gif") || fileNameExtension.equals(".webp"))) {
			throw new RuntimeException();
		}

		return uuidName + fileNameExtension;
	}

	/**
	 * MultipartFile을 지정된 경로에 저장합니다.
	 * 
	 * @param multipartFile 저장할 MultipartFile 객체
	 * @param path          저장할 경로
	 * @param filename      저장할 파일 이름
	 * @throws RuntimeException 파일 저장에 실패한 경우
	 */
	public void saveFile(MultipartFile multipartFile, String path, String filename) {

		File file = new File(path + "/" + filename);
		try {
			multipartFile.transferTo(file);
		} catch (IllegalStateException | IOException e) {
			throw new RuntimeException();
		}
	}
}
