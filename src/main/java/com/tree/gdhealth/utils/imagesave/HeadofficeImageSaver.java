package com.tree.gdhealth.utils.imagesave;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.tree.gdhealth.utils.exception.ExtensionNotMatchException;
import com.tree.gdhealth.utils.exception.ImageNotSaveException;

/**
 * 이미지 파일과 관련된 작업을 수행하는 유틸리티 클래스
 * 
 * @author 진관호
 */
public class HeadofficeImageSaver {

	/**
	 * {@link MultipartFile} 객체로부터 고유한 파일 이름을 생성하여 리턴합니다.
	 * 
	 * @param originalName 원본 이미지명
	 * @return 고유한 파일 이름
	 * @throws ExtensionNotMatchException 이미지 파일이 아닌 경우
	 */
	public String getFileName(String originalName) {

		String uniqueName = UUID.randomUUID().toString();
		String extension = originalName.substring(originalName.lastIndexOf("."));

		if (!(extension.equals(".png") || extension.equals(".jpg") || extension.equals(".jpeg")
				|| extension.equals(".gif") || extension.equals(".webp"))) {
			throw new ExtensionNotMatchException("이미지 형식의 확장자가 아닙니다.");
		}

		return uniqueName + extension;
	}

	/**
	 * MultipartFile을 지정된 경로에 저장합니다.
	 * 
	 * @param multipartFile 저장할 MultipartFile 객체
	 * @param path          저장할 경로
	 * @param fileName      저장할 파일 이름
	 * @throws ImageNotSaveException 파일 저장에 실패한 경우
	 */
	public void saveFile(MultipartFile multipartFile, String path, String fileName) {

		File file = new File(path + "/" + fileName);
		try {
			multipartFile.transferTo(file);
		} catch (IllegalStateException | IOException e) {
			throw new ImageNotSaveException("이미지 저장 실패");
		}
	}
}
