package site.dittotrip.ditto_trip.ditto.domain.dto;

import lombok.Builder;
import lombok.Data;
import site.dittotrip.ditto_trip.ditto.domain.Ditto;
import site.dittotrip.ditto_trip.ditto.domain.DittoComment;
import site.dittotrip.ditto_trip.ditto.domain.DittoImage;
import site.dittotrip.ditto_trip.user.domain.User;
import site.dittotrip.ditto_trip.user.domain.dto.UserData;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class DittoData {

    private Long dittoId;
    private String title;
    private String body;
    private LocalDateTime createdDateTime;

    private UserData userData;
    @Builder.Default
    private List<String> imagePaths = new ArrayList<>();

    private Boolean isMine;
    private Long myBookmarkId;

    public static DittoData fromEntity(Ditto ditto, Boolean isMine, Long myBookmarkId) {
        DittoData dittoData = DittoData.builder()
                .dittoId(ditto.getId())
                .title(ditto.getTitle())
                .body(ditto.getBody())
                .createdDateTime(ditto.getCreatedDateTime())
                .userData(UserData.fromEntity(ditto.getUser()))
                .isMine(isMine)
                .myBookmarkId(myBookmarkId)
                .build();

        dittoData.putImageDataList(ditto.getDittoImages());
        return dittoData;
    }


    private void putImageDataList(List<DittoImage> dittoImages) {
        for (DittoImage dittoImage : dittoImages) {
            this.getImagePaths().add(dittoImage.getImagePath());
        }
    }

}
