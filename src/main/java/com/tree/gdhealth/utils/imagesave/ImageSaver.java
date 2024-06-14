package com.tree.gdhealth.utils.imagesave;

import com.tree.gdhealth.utils.exception.MisMatchedContentTypeException;
import com.tree.gdhealth.utils.enumtype.ImageType;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**<p>이미지파일 저장을 위한 클래스</p>
 * @author 정인호
 * @apiNote 사용법: saveImage(MultipartFile imageFile)정적메소드를 실행하면 타입체크와 저장까지 마치고 저장시 활용된 정보가 담긴 ImageSaver 인스턴스를 반환한다.
 * <br> File I/O는 스프링 @Transactional에 제어되지 않으니 서비스레이어에서 예외처리에 주의해야함
 */
@ToString
@Slf4j
@Getter
public final class ImageSaver {
    private final static String DEFAULT_PATH = System.getProperty("user.dir")+"\\src\\main\\resources\\static\\img\\";
    private String originImageName;
    private String savedImageName;
    private String savedImagePath;
    private Long imageSize;
    private String contentType;

    private ImageSaver(String originImageName, String savedImageName, String savedImagePath, Long imageSize, String contentType){
        this.originImageName = originImageName;
        this.savedImageName = savedImageName;
        this.savedImagePath = savedImagePath;
        this.imageSize = imageSize;
        this.contentType = contentType;
    }

    private static boolean isImageType(String contentType){
        boolean result = false;
        for(ImageType imageType : ImageType.values()){
            result |= imageType.getContentType().equals(contentType);
        }
        return result;
    }

    /**
     * @param imageFile {@link ImageType}의 contentType에 해당하지 않으면 {@link MisMatchedContentTypeException}를 던짐
     * @return 저장이 성공하면 저장한 정보를 채운 {@link ImageSaver} 객체를 반환
     * @throws IOException 입력에 실패하거나 저장된 파일을 찾을 수 없을 때
     * @throws MisMatchedContentTypeException 런타임예외
     */
    public static ImageSaver saveImage(MultipartFile imageFile) throws IOException, MisMatchedContentTypeException {
        String originImageName = imageFile.getOriginalFilename();
        String savedImageName = UUID.randomUUID() + originImageName;
        String savedImagePath = DEFAULT_PATH + savedImageName;
        Long imageSize = imageFile.getSize();
        String contentType = imageFile.getContentType();

        if(!isImageType(contentType)){
            throw new MisMatchedContentTypeException("이미지타입이 아닙니다. 받은 contentType="+contentType);
        }

        try {
            imageFile.transferTo(new File(savedImagePath));
        } catch (IOException e) {
            log.error("이미지 저장에 실패하였습니다. 입력된 이미지 = {}", imageFile.toString());
            throw e;
        }

        log.debug("이미지 저장에 성공하였습니다. 저장정보가 담긴 객체를 반환합니다.");
        return new ImageSaver(
                originImageName,
                savedImageName,
                savedImagePath,
                imageSize,
                contentType
        );
    }

    /**<p>인스턴스의 정보를 가지고 파일을 삭제하고 필드를 null로 초기화하는 메소드</p>
     * @throws IOException 파일삭제에 실패한경우
     * @throws IllegalArgumentException 지정된 경로의 파일을 확인할 수 없을 때
     */
    public void deleteImage() throws IOException {
        File file = new File(savedImagePath);
        if(!file.exists()){
            throw new IllegalArgumentException("해당경로에 파일을 확인할 수 없습니다."+savedImagePath);
        }

        if(!file.delete()){
            throw new IOException("파일삭제에 실패했습니다.");
        }

        log.debug("파일 삭제완료, 필드가 null로 초기화됩니다.");
        originImageName = null;
        savedImageName = null;
        savedImagePath = null;
        imageSize = null;
        contentType = null;
    }


}
