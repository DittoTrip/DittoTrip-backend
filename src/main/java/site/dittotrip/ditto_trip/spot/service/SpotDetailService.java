package site.dittotrip.ditto_trip.spot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.dittotrip.ditto_trip.spot.repository.SpotRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SpotDetailService {

    private final SpotRepository spotRepository;


}
