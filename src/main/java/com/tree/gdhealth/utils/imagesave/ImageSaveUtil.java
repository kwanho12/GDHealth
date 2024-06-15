package com.tree.gdhealth.utils.imagesave;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.tree.gdhealth.utils.exception.ExtensionNotMatchException;
import com.tree.gdhealth.utils.exception.ImageNotSaveException;

/**
 * 이미지 파일 저장과 관련된 작업을 수행하는 유틸리티 클래스
 * 
 * @author 진관호
 */
public class ImageSaveUtil {
	
	private static final String PNG = ".png";
	private static final String JPG = ".jpg";
	private static final String JPEG = ".jpeg";
	private static final String GIF = ".gif";
	private static final String WEBP = ".webp";
	private static final String TIF = ".tif";

	/**
	 * 원본 이미지명으로부터 확장자명을 추출하여 고유한 이미지명을 리턴합니다.
	 * 
	 * @param originalName 원본 이미지명
	 * @return 고유한 파일 이름
	 * @throws ExtensionNotMatchException 이미지 파일이 아닌 경우
	 */
	public static String getFileName(String originalName) {

		String uniqueName = UUID.randomUUID().toString();
		String extension = originalName.substring(originalName.lastIndexOf("."));

		if (!(extension.equals(PNG) || extension.equals(JPG) || extension.equals(JPEG)
				|| extension.equals(GIF) || extension.equals(WEBP) || extension.equals(TIF))) {
			throw new ExtensionNotMatchException(extension.substring(1) + "은(는) 이미지 형식의 확장자가 아닙니다.");
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
	public static void saveFile(MultipartFile multipartFile, String path, String fileName) {

		File file = new File(path + "/" + fileName);
		try {
			multipartFile.transferTo(file);
		} catch (IllegalStateException | IOException e) {
			throw new ImageNotSaveException("이미지 저장을 실패하였습니다.");
		}
	}
}
