package site.dittotrip.ditto_trip.spot.domain.dto;

import org.springframework.data.geo.Point;
import site.dittotrip.ditto_trip.hashtag.domain.entity.Hashtag;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class SpotData {

    private Long spotId;
    private String spotName;
    // 이미지 URI
    private float rating;
    private String address;
    private Point point;
    private List<Hashtag> hashtags = new ArrayList<>();

}
