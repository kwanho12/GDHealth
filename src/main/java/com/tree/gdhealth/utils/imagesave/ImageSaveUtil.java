package com.tree.gdhealth.utils.imagesave;

import java.io.IOException;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.tree.gdhealth.utils.exception.ExtensionNotMatchException;
import com.tree.gdhealth.utils.exception.ImageNotSaveException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

/**
 * 이미지 파일 저장과 관련된 작업을 수행하는 유틸리티 클래스
 * 
 * @author 진관호
 */
@Component
@RequiredArgsConstructor
public class ImageSaveUtil {

    private final S3Client s3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
	
	private static final String PNG = ".png";
	private static final String JPG = ".jpg";
	private static final String JPEG = ".jpeg";
	private static final String GIF = ".gif";
	private static final String WEBP = ".webp";
	private static final String TIF = ".tif";

	public String getFileName(String originalName) {

		String uniqueName = UUID.randomUUID().toString();
		String extension = originalName.substring(originalName.lastIndexOf("."));

		if (!(extension.equals(PNG) || extension.equals(JPG) || extension.equals(JPEG)
				|| extension.equals(GIF) || extension.equals(WEBP) || extension.equals(TIF))) {
			throw new ExtensionNotMatchException(extension.substring(1) + "은(는) 이미지 형식의 확장자가 아닙니다.");
		}

		return uniqueName + extension;
	}

    public void saveFileToS3(MultipartFile multipartFile, String keyName, String folder) {
        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(folder + "/" + keyName)
                    .contentType(multipartFile.getContentType())
                    .acl(ObjectCannedACL.PUBLIC_READ) // 업로드 시 자동으로 퍼블릭 읽기
                    .build();

            s3Client.putObject(
                    putObjectRequest,
                    RequestBody.fromInputStream(multipartFile.getInputStream(), multipartFile.getSize())
            );

        } catch (IOException e) {
            throw new ImageNotSaveException("S3 업로드 실패", e);
        }
    }
}
