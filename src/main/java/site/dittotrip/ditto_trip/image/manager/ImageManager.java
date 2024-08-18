package site.dittotrip.ditto_trip.image.manager;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Slf4j
@Component
public class ImageManager {

    @Value("${image.host-name}")
    private String hostName;

    @Value("${image.connect-path}")
    private String connectPath;

    @Value("${image.save-path}")
    private String savePath;

    public String saveImage(MultipartFile file) {
        if (file.isEmpty()) {
            throw new RuntimeException("파일이 비어있습니다.");
        }

        String originalFilename = file.getOriginalFilename();
        String uuid = UUID.randomUUID().toString();
        String filename = uuid + "_" + originalFilename;

        try {
            file.transferTo(new File(savePath + filename));
            return hostName + connectPath + filename;
        } catch (Exception e) {
            log.error("이미지 처리 중 실패", e);
            throw new RuntimeException("이미지 처리 중 실패");
        }
    }

}
