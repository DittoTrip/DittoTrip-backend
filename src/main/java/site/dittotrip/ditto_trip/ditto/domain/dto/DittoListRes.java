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

    private List<DittoMiniData> dittoMiniDataList = new ArrayList<>();
    private Integer totalPage;

    public static DittoListRes fromEntities(Page<Ditto> page, User user) {
        DittoListRes dittoListRes = DittoListRes.builder()
                .totalPage(page.getTotalPages())
                .build();

        for (Ditto ditto : page.getContent()) {
            dittoListRes.getDittoMiniDataList().add(DittoMiniData.fromEntity(ditto, user));
        }

        return dittoListRes;
    }

    public static DittoListRes fromEntities(List<Ditto> dittos, User user) {
        DittoListRes dittoListRes = DittoListRes.builder()
                .totalPage(null)
                .build();

        for (Ditto ditto : dittos) {
            dittoListRes.getDittoMiniDataList().add(DittoMiniData.fromEntity(ditto, user));
        }

        return dittoListRes;
    }

}
