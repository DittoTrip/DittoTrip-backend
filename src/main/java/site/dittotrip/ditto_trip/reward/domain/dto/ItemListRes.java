package site.dittotrip.ditto_trip.reward.domain.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ItemListRes {

    private List<ItemData> itemDataList;

}
