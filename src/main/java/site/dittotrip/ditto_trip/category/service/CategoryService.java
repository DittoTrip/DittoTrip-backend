package site.dittotrip.ditto_trip.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import site.dittotrip.ditto_trip.category.domain.Category;
import site.dittotrip.ditto_trip.category.domain.CategoryBookmark;
import site.dittotrip.ditto_trip.category.domain.dto.*;
import site.dittotrip.ditto_trip.category.domain.enums.CategoryMajorType;
import site.dittotrip.ditto_trip.category.domain.enums.CategorySubType;
import site.dittotrip.ditto_trip.category.repository.CategoryBookmarkRepository;
import site.dittotrip.ditto_trip.category.repository.CategoryRepository;
import site.dittotrip.ditto_trip.hashtag.domain.Hashtag;
import site.dittotrip.ditto_trip.hashtag.domain.HashtagCategory;
import site.dittotrip.ditto_trip.hashtag.repository.HashtagRepository;
import site.dittotrip.ditto_trip.spot.domain.CategorySpot;
import site.dittotrip.ditto_trip.spot.domain.Spot;
import site.dittotrip.ditto_trip.spot.repository.CategorySpotRepository;
import site.dittotrip.ditto_trip.spot.repository.SpotRepository;
import site.dittotrip.ditto_trip.user.domain.User;
import site.dittotrip.ditto_trip.user.repository.UserRepository;
import site.dittotrip.ditto_trip.utils.S3Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryService {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final CategoryBookmarkRepository categoryBookmarkRepository;
    private final CategorySpotRepository categorySpotRepository;
    private final SpotRepository spotRepository;
    private final HashtagRepository hashtagRepository;
    private final S3Service s3Service;

    public CategoryPageRes findCategoryList(Long reqUserId, CategorySubType subType, Pageable pageable) {
        User reqUser = null;
        if (reqUserId != null) {
            reqUser = userRepository.findById(reqUserId).orElseThrow(NoSuchElementException::new);
        }

        Page<Category> page = categoryRepository.findBySubType(subType, pageable);

        CategoryPageRes res = new CategoryPageRes();
        res.setTotalPages(page.getTotalPages());

        for (Category category : page.getContent()) {
            CategoryBookmark reqUsersBookmark = getReqUsersBookmark(reqUser, category);
            res.getCategoryDataList().add(CategoryData.fromEntity(category, reqUsersBookmark));
        }

        return res;
    }

    public CategoryMajorTypeListRes findCategoryListByBookmark(Long reqUserId, CategoryMajorType majorType, Pageable pageable) {
        User reqUser = userRepository.findById(reqUserId).orElseThrow(NoSuchElementException::new);

        Page<CategoryBookmark> page = categoryBookmarkRepository.findByUserAndMajorType(reqUser, majorType, pageable);

        CategoryMajorTypeListRes res = new CategoryMajorTypeListRes();
        res.setTotalPages(page.getTotalPages());

        for (CategoryBookmark bookmark : page.getContent()) {
            res.getCategoryDataList().add(CategoryData.fromEntity(bookmark.getCategory(), bookmark));
        }

        return res;
    }

    /**
     * 카테고리 검색 조회
     *  - 단순 문자열 포함 검색
     */
    public CategoryMajorTypeListRes findCategoryListBySearch(Long reqUserId, String word, CategoryMajorType majorType, Pageable pageable) {
        User reqUser = null;
        if (reqUserId != null) {
            reqUser = userRepository.findById(reqUserId).orElseThrow(NoSuchElementException::new);
        }

        Page<Category> page = categoryRepository.findBySearchAndMajorType(word, majorType, pageable);

        CategoryMajorTypeListRes res = new CategoryMajorTypeListRes();
        res.setTotalPages(page.getTotalPages());

        for (Category category : page.getContent()) {
            CategoryBookmark reqUsersBookmark = getReqUsersBookmark(reqUser, category);
            res.getCategoryDataList().add(CategoryData.fromEntity(category, reqUsersBookmark));
        }

        return res;
    }

    /**
     * 카테고리 타입마다 나누지 않고 하나의 리스트로 반환합니다.
     */
    public CategoryListNoTypeRes findCategoryNoTypeListBySearch(Long reqUserId, String word, Pageable pageable) {
        User reqUser = null;
        if (reqUserId != null) {
            reqUser = userRepository.findById(reqUserId).orElseThrow(NoSuchElementException::new);
        }

        Page<Category> page = null;
        if (word != null) {
            page = categoryRepository.findByNameContaining(word, pageable);
        } else {
            page = categoryRepository.findAll(pageable);
        }

        CategoryListNoTypeRes res = new CategoryListNoTypeRes();
        res.setTotalPages(page.getTotalPages());
        for (Category category : page.getContent()) {
            CategoryBookmark reqUsersBookmark = getReqUsersBookmark(reqUser, category);
            res.getCategoryDataList().add(CategoryData.fromEntity(category, reqUsersBookmark));
        }

        return res;
    }

    public CategoryListForAdminRes findCategoryListForAdmin(String word, Pageable pageable) {
        Page<Category> page;
        if (word != null) {
            page = categoryRepository.findByNameContaining(word, pageable);
        } else {
            page = categoryRepository.findAll(pageable);
        }

        return CategoryListForAdminRes.fromEntities(page);
    }

    public CategoryDetailForAdminRes findCategoryDetailForAdmin(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(NoSuchElementException::new);
        return CategoryDetailForAdminRes.fromEntity(category);
    }

    @Transactional(readOnly = false)
    public void saveCategory(CategorySaveReq saveReq, MultipartFile multipartFile) {
        Category category = saveReq.toEntity();
        categoryRepository.save(category);

        // 이미지 처리
        String imagePath = s3Service.uploadFile(multipartFile);
        category.setImagePath(imagePath);

        // 스팟 처리
        List<Long> spotIds = saveReq.getSpotIds();
        for (Long spotId : spotIds) {
            Spot spot = spotRepository.findById(spotId).orElseThrow(NoSuchElementException::new);
            category.getCategorySpots().add(new CategorySpot(category, spot));
        }

        // 해시태그 처리
        for (String hashtagName : saveReq.getHashtagNames()) {
            Hashtag hashtag = hashtagRepository.findByName(hashtagName).orElse(null);
            if (hashtag == null) {
                hashtag = new Hashtag(hashtagName);
                hashtagRepository.save(hashtag);
            }
            category.getHashtagCategories().add(new HashtagCategory(hashtag, category));
        }

    }

    @Transactional(readOnly = false)
    public void modifyCategory(Long categoryId, CategoryModifyReq modifyReq, MultipartFile multipartFile) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(NoSuchElementException::new);
        modifyReq.modifyEntity(category);

        // 이미지 처리
        s3Service.deleteFile(category.getImagePath());
        String imagePath = s3Service.uploadFile(multipartFile);
        category.setImagePath(imagePath);

        // 스팟 처리
        List<CategorySpot> categorySpots = new ArrayList<>();
        for (Long spotId : modifyReq.getSpotIds()) {
            Spot spot = spotRepository.findById(spotId).orElseThrow(NoSuchElementException::new);
            categorySpots.add(new CategorySpot(category, spot));
        }
        category.setCategorySpots(categorySpots);

        // 해시태그 처리
        List<HashtagCategory> hashtagCategories = new ArrayList<>();
        for (String hashtagName : modifyReq.getHashtagNames()) {
            Hashtag hashtag = hashtagRepository.findByName(hashtagName).orElse(null);
            if (hashtag == null) {
                hashtag = new Hashtag(hashtagName);
                hashtagRepository.save(hashtag);
            }
            hashtagCategories.add(new HashtagCategory(hashtag, category));
        }
        category.setHashtagCategories(hashtagCategories);

    }

    @Transactional(readOnly = false)
    public void removeCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(NoSuchElementException::new);
        if (category.getImagePath() != null) {
            s3Service.deleteFile(category.getImagePath());
        }
        categoryRepository.delete(category);
    }

    private CategoryBookmark getReqUsersBookmark(User reqUser, Category category) {
        if (reqUser == null) {
            return null;
        } else {
            Optional<CategoryBookmark> categoryBookmark = categoryBookmarkRepository.findByCategoryAndUser(category, reqUser);
            return categoryBookmark.orElse(null);
        }
    }

}
