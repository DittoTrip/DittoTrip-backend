package site.dittotrip.ditto_trip.searchpage;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.dittotrip.ditto_trip.category.domain.Category;
import site.dittotrip.ditto_trip.category.domain.enums.CategoryMajorType;
import site.dittotrip.ditto_trip.category.repository.CategoryRepository;
import site.dittotrip.ditto_trip.searchpage.dto.CategorySearchPageData;
import site.dittotrip.ditto_trip.searchpage.dto.RankingCategoryRes;
import site.dittotrip.ditto_trip.searchpage.dto.RankingSearchRes;
import site.dittotrip.ditto_trip.utils.RedisConstants;
import site.dittotrip.ditto_trip.utils.RedisService;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class SearchPageService {

    private final CategoryRepository categoryRepository;
    private final RedisService redisService;

    public RankingSearchRes findRankingSearchList() {
        List<String> words = redisService.getRankingList(RedisConstants.ZSET_SEARCH_RANKING_KEY, RedisConstants.ZSET_SEARCH_FIND_SIZE);

        return RankingSearchRes.builder()
                .words(words)
                .build();
    }

    public RankingCategoryRes findRankingCategoryList(CategoryMajorType majorType) {
        List<String> categoryIds;
        if (majorType.equals(CategoryMajorType.CONTENT)) {
            categoryIds = redisService.getRankingList(RedisConstants.ZSET_CONTENT_RANKING_KEY, RedisConstants.ZSET_CATEGORY_FIND_SIZE);
        } else {
            categoryIds = redisService.getRankingList(RedisConstants.ZSET_PERSON_RANKING_KEY, RedisConstants.ZSET_CATEGORY_FIND_SIZE);
        }

        List<CategorySearchPageData> categorySearchPageDataList = new ArrayList<>();
        for (String categoryId : categoryIds) {
            Category category = categoryRepository.findById(Long.parseLong(categoryId)).orElseThrow(NoSuchElementException::new);
            categorySearchPageDataList.add(CategorySearchPageData.fromEntity(category));
        }

        return RankingCategoryRes.builder()
                .categorySearchPageDataList(categorySearchPageDataList)
                .build();
    }

}
