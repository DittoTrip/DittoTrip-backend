package site.dittotrip.ditto_trip.image.manager;

import org.springframework.web.multipart.MultipartFile;

public interface ImageManager {

    String saveImage(MultipartFile multipartFile);

}
