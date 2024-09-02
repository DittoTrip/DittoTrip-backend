package site.dittotrip.ditto_trip.spot.service;

import lombok.RequiredArgsConstructor;
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
import site.dittotrip.ditto_trip.spot.domain.dto.SpotApplySaveReq;
import site.dittotrip.ditto_trip.spot.repository.SpotApplyRepository;
import site.dittotrip.ditto_trip.spot.repository.SpotRepository;
import site.dittotrip.ditto_trip.user.domain.User;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SpotApplyService {

    private final SpotRepository spotRepository;
    private final SpotApplyRepository spotApplyRepository;
    private final CategoryRepository categoryRepository;
    private final HashtagRepository hashtagRepository;

    @Transactional(readOnly = false)
    public void saveSpotApply(User user, SpotApplySaveReq saveReq, MultipartFile multipartFile, List<MultipartFile> multipartFiles) {

        SpotApply spotApply = saveReq.toEntity(user);
        spotApplyRepository.save(spotApply);

        for (Long categoryId : saveReq.getCategoryIds()) {
            Category category = categoryRepository.findById(categoryId).orElseThrow(NoSuchElementException::new);
            spotApply.getCategorySpotApplies().add(new CategorySpotApply(category, spotApply));
        }

        for (String hashtagName : saveReq.getHashtagNames()) {
            Optional<Hashtag> optionalHashtag = hashtagRepository.findByName(hashtagName);
            Hashtag hashtag = null;
            if (optionalHashtag.isEmpty()) {
                hashtag = new Hashtag(hashtagName);
                hashtagRepository.save(hashtag);
            } else {
                hashtag = optionalHashtag.get();
            }
            spotApply.getHashtagSpotApplies().add(new HashtagSpotApply(hashtag, spotApply));
        }

        // 이미지 처리

    }

    @Transactional(readOnly = false)
    public void removeSpotApply(Long spotApplyId) {
        SpotApply spotApply = spotApplyRepository.findById(spotApplyId).orElseThrow(NoSuchElementException::new);
        spotApplyRepository.delete(spotApply);
    }

}
