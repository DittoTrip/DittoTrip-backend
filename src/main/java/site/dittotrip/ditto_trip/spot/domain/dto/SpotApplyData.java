package site.dittotrip.ditto_trip.spot.domain.dto;

import lombok.Builder;
import lombok.Data;
import site.dittotrip.ditto_trip.category.domain.dto.CategoryMiniData;
import site.dittotrip.ditto_trip.hashtag.domain.HashtagSpotApply;
import site.dittotrip.ditto_trip.spot.domain.CategorySpotApply;
import site.dittotrip.ditto_trip.spot.domain.SpotApply;
import site.dittotrip.ditto_trip.spot.domain.SpotApplyImage;
import site.dittotrip.ditto_trip.spot.domain.enums.SpotApplyStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class SpotApplyData {

    private Long spotApplyId;
    private String name;
    private String address;
    private Double pointX;
    private Double pointY;
    private String imagePath;
    private SpotApplyStatus spotApplyStatus;
    private LocalDateTime createdDateTime;

    @Builder.Default
    private List<CategoryMiniData> categoryDataList = new ArrayList<>();
    @Builder.Default
    private List<String> hashtags = new ArrayList<>();
    @Builder.Default
    private List<String> imagePaths = new ArrayList<>();

    public static SpotApplyData fromEntity(SpotApply spotApply) {
        SpotApplyData spotApplyData = SpotApplyData.builder()
                .spotApplyId(spotApply.getId())
                .name(spotApply.getName())
                .address(spotApply.getAddress())
                .pointX(spotApply.getPointX())
                .pointY(spotApply.getPointY())
                .imagePath(spotApply.getImagePath())
                .spotApplyStatus(spotApply.getSpotApplyStatus())
                .createdDateTime(spotApply.getCreatedDateTime())
                .build();

        for (CategorySpotApply categorySpotApply : spotApply.getCategorySpotApplies()) {
            spotApplyData.getCategoryDataList().add(CategoryMiniData.fromEntity(categorySpotApply.getCategory()));
        }

        for (HashtagSpotApply hashtagSpotApply : spotApply.getHashtagSpotApplies()) {
            spotApplyData.getHashtags().add(hashtagSpotApply.getHashtag().getName());
        }

        for (SpotApplyImage spotApplyImage : spotApply.getSpotApplyImages()) {
            spotApplyData.getImagePaths().add(spotApplyImage.getImagePath());
        }

        return spotApplyData;
    }

}
