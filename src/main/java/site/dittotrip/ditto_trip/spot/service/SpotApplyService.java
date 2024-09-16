package site.dittotrip.ditto_trip.spot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import site.dittotrip.ditto_trip.category.domain.Category;
import site.dittotrip.ditto_trip.category.repository.CategoryRepository;
import site.dittotrip.ditto_trip.hashtag.domain.Hashtag;
import site.dittotrip.ditto_trip.hashtag.domain.HashtagSpotApply;
import site.dittotrip.ditto_trip.hashtag.repository.HashtagRepository;
import site.dittotrip.ditto_trip.spot.domain.CategorySpotApply;
import site.dittotrip.ditto_trip.spot.domain.SpotApply;
import site.dittotrip.ditto_trip.spot.domain.SpotApplyImage;
import site.dittotrip.ditto_trip.spot.domain.dto.SpotApplyListRes;
import site.dittotrip.ditto_trip.spot.domain.dto.SpotApplySaveReq;
import site.dittotrip.ditto_trip.spot.repository.SpotApplyRepository;
import site.dittotrip.ditto_trip.spot.repository.SpotRepository;
import site.dittotrip.ditto_trip.user.domain.User;
import site.dittotrip.ditto_trip.user.repository.UserRepository;
import site.dittotrip.ditto_trip.utils.S3Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SpotApplyService {

    private final UserRepository userRepository;
    private final SpotRepository spotRepository;
    private final SpotApplyRepository spotApplyRepository;
    private final CategoryRepository categoryRepository;
    private final HashtagRepository hashtagRepository;
    private final S3Service s3Service;

    public void findSpotApplyList(Pageable pageable) {
    }

    public SpotApplyListRes findMySpotApplyList(Long reqUserId) {
        User reqUser = userRepository.findById(reqUserId).orElseThrow(NoSuchElementException::new);
        List<SpotApply> spotApplies = spotApplyRepository.findByUser(reqUser);
        return SpotApplyListRes.fromEntities(spotApplies);
    }

    @Transactional(readOnly = false)
    public void saveSpotApply(Long reqUserId, SpotApplySaveReq saveReq, MultipartFile multipartFile, List<MultipartFile> multipartFiles) {
        User reqUser = userRepository.findById(reqUserId).orElseThrow(NoSuchElementException::new);
        SpotApply spotApply = saveReq.toEntity(reqUser);
        spotApplyRepository.save(spotApply);

        // image 처리
        String mainImagePath = s3Service.uploadFile(multipartFile);
        spotApply.setImagePath(mainImagePath);

        for (MultipartFile file : multipartFiles) {
            String imagePath = s3Service.uploadFile(file);
            spotApply.getSpotApplyImages().add(new SpotApplyImage(imagePath, spotApply));
        }

        // categorySpotApply 처리
        for (Long categoryId : saveReq.getCategoryIds()) {
            Category category = categoryRepository.findById(categoryId).orElseThrow(NoSuchElementException::new);
            spotApply.getCategorySpotApplies().add(new CategorySpotApply(category, spotApply));
        }

        // hashtag 처리
        for (String hashtagName : saveReq.getHashtagNames()) {
            Hashtag hashtag = hashtagRepository.findByName(hashtagName).orElse(null);
            if (hashtag == null) {
                hashtag = new Hashtag(hashtagName);
                hashtagRepository.save(hashtag);
            }
            spotApply.getHashtagSpotApplies().add(new HashtagSpotApply(hashtag, spotApply));
        }

    }

    @Transactional(readOnly = false)
    public void removeSpotApply(Long spotApplyId) {
        SpotApply spotApply = spotApplyRepository.findById(spotApplyId).orElseThrow(NoSuchElementException::new);
        s3Service.deleteFile(spotApply.getImagePath());
        for (SpotApplyImage spotApplyImage : spotApply.getSpotApplyImages()) {
            s3Service.deleteFile(spotApplyImage.getImagePath());
        }

        spotApplyRepository.delete(spotApply);
    }

}
