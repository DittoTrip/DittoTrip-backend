package site.dittotrip.ditto_trip.ditto.domain.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;
import site.dittotrip.ditto_trip.ditto.domain.Ditto;
import site.dittotrip.ditto_trip.user.domain.User;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class DittoListRes {

    @Builder.Default
    private List<DittoMiniData> dittoMiniDataList = new ArrayList<>();
    private Boolean isMine;
    private Integer totalPage;

    public static DittoListRes fromEntities(Page<Ditto> page, Boolean isMine) {
        DittoListRes dittoListRes = DittoListRes.builder()
                .isMine(isMine)
                .totalPage(page.getTotalPages())
                .build();

        for (Ditto ditto : page.getContent()) {
            dittoListRes.getDittoMiniDataList().add(DittoMiniData.fromEntity(ditto));
        }

        return dittoListRes;
    }

    public static DittoListRes fromEntities(Page<Ditto> page) {
        DittoListRes dittoListRes = DittoListRes.builder()
                .totalPage(page.getTotalPages())
                .build();

        for (Ditto ditto : page.getContent()) {
            dittoListRes.getDittoMiniDataList().add(DittoMiniData.fromEntity(ditto));
        }

        return dittoListRes;
    }

    public static DittoListRes fromEntities(List<Ditto> dittos) {
        DittoListRes dittoListRes = DittoListRes.builder()
                .totalPage(null)
                .build();

        for (Ditto ditto : dittos) {
            dittoListRes.getDittoMiniDataList().add(DittoMiniData.fromEntity(ditto));
        }

        return dittoListRes;
    }

}
