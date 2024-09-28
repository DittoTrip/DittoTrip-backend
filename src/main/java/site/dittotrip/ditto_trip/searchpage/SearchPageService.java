package site.dittotrip.ditto_trip.searchpage;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.dittotrip.ditto_trip.category.domain.Category;
import site.dittotrip.ditto_trip.category.repository.CategoryRepository;
import site.dittotrip.ditto_trip.searchpage.dto.CategorySearchPageData;
import site.dittotrip.ditto_trip.searchpage.dto.SearchPageRes;
import site.dittotrip.ditto_trip.utils.RedisConstants;
import site.dittotrip.ditto_trip.utils.RedisService;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class SearchPageService {

    private final CategoryRepository categoryRepository;
    private final RedisService redisService;

    public SearchPageRes findSearchPage() {
        List<String> words = redisService.getRankingList(RedisConstants.ZSET_SEARCH_RANKING_KEY, RedisConstants.ZSET_SEARCH_FIND_SIZE);

        List<String> categoryIds = redisService.getRankingList(RedisConstants.ZSET_CATEGORY_RANKING_KEY, RedisConstants.ZSET_CATEGORY_FIND_SIZE);
        List<CategorySearchPageData> categorySearchPageDataList = new ArrayList<>();
        for (String categoryId : categoryIds) {
            Category category = categoryRepository.findById(Long.parseLong(categoryId)).orElseThrow(NoSuchElementException::new);
            categorySearchPageDataList.add(CategorySearchPageData.fromEntity(category));
        }

        return SearchPageRes.builder()
                .words(words)
                .categorySearchPageDataList(categorySearchPageDataList)
                .build();
    }

}
