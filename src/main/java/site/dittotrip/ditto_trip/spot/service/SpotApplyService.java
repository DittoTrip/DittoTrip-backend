package site.dittotrip.ditto_trip.spot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import site.dittotrip.ditto_trip.category.domain.Category;
import site.dittotrip.ditto_trip.category.repository.CategoryRepository;
import site.dittotrip.ditto_trip.spot.domain.SpotApply;
import site.dittotrip.ditto_trip.spot.domain.dto.SpotApplySaveReq;
import site.dittotrip.ditto_trip.spot.repository.SpotApplyRepository;
import site.dittotrip.ditto_trip.spot.repository.SpotRepository;
import site.dittotrip.ditto_trip.user.domain.User;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SpotApplyService {

    private final SpotRepository spotRepository;
    private final SpotApplyRepository spotApplyRepository;
    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = false)
    public void saveSpotApply(User user, SpotApplySaveReq saveReq, MultipartFile multipartFile, List<MultipartFile> multipartFiles) {

        List<Category> categories = new ArrayList<>();
        for (Long categoryId : saveReq.getCategoryIds()) {
            categories.add(categoryRepository.findById(categoryId).orElseThrow(NoSuchElementException::new));
        }

        SpotApply spotApply = saveReq.toEntity(user, categories);
        spotApplyRepository.save(spotApply);

        // 이미지 처리

    }



}
