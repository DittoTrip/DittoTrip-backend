package site.dittotrip.ditto_trip.review.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 필요없을 수도
 */
@Service
@Transactional(readOnly = false)
@RequiredArgsConstructor
public class ReviewModifyService {
}
