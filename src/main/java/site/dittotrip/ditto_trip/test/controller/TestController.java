package site.dittotrip.ditto_trip.test.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import site.dittotrip.ditto_trip.auth.domain.CustomUserDetails;
import site.dittotrip.ditto_trip.auth.domain.enums.TokenType;
import site.dittotrip.ditto_trip.spot.domain.Spot;
import site.dittotrip.ditto_trip.spot.domain.SpotVisit;
import site.dittotrip.ditto_trip.spot.repository.SpotRepository;
import site.dittotrip.ditto_trip.spot.repository.SpotVisitRepository;
import site.dittotrip.ditto_trip.user.domain.User;
import site.dittotrip.ditto_trip.user.repository.UserRepository;
import site.dittotrip.ditto_trip.utils.JwtProvider;
import site.dittotrip.ditto_trip.utils.RedisConstants;
import site.dittotrip.ditto_trip.utils.RedisService;
import site.dittotrip.ditto_trip.utils.S3Service;

import java.util.List;
import java.util.NoSuchElementException;

import static site.dittotrip.ditto_trip.auth.domain.CustomUserDetails.getUserIdFromUserDetails;

/**
 * 1. 테스트용 액세스 토큰 발급
 */
@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TestController {

  private final JwtProvider jwtProvider;
  private final UserRepository userRepository;
  private final S3Service s3Service;
  private final SpotRepository spotRepository;
  private final SpotVisitRepository spotVisitRepository;
  private final RedisService redisService;

  @Transactional(readOnly = false)
  @PostMapping("/spot/{spotId}/visit")
  @Operation(summary = "스팟 방문 처리하기",
          description = "")
  public void spotVisitTest(@AuthenticationPrincipal CustomUserDetails userDetails,
                            @PathVariable(name = "spotId") Long spotId) {
    Long reqUserId = getUserIdFromUserDetails(userDetails, true);
    User user = userRepository.findById(reqUserId).orElseThrow(NoSuchElementException::new);
    Spot spot = spotRepository.findById(spotId).orElseThrow(NoSuchElementException::new);

    SpotVisit spotVisit = new SpotVisit(spot, user);
    spotVisitRepository.save(spotVisit);

    redisService.addIfAbsent(RedisConstants.ZSET_SPOT_RANKING_KEY, spot.getId().toString());
    redisService.incrementScore(RedisConstants.ZSET_SPOT_RANKING_KEY, spot.getId().toString());
  }

  @GetMapping
  @Operation(summary = "서버 정상 여부 확인",
          description = "")
  public String test() {
    return "server is ok";
  }

  @GetMapping("/access-token")
  @Operation(summary = "테스트를 위한 액세스 토큰 발급",
      description = "")
  public String issueTestToken(@RequestParam(name = "userId") Long userId) {
    User user = userRepository.findById(userId).get();
    return jwtProvider.generateToken(user.getId().toString(), user.getAuthorities(), TokenType.ACCESS_TOKEN);
  }


  @PostMapping("/upload")
  public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
    try {
      String fileUrl = s3Service.uploadFile(file);
      return ResponseEntity.ok(fileUrl);
    } catch (Exception e) {
      return ResponseEntity.status(500).body("File upload failed: " + e.getMessage());
    }
  }

  @DeleteMapping("/delete/{fileName}")
  public ResponseEntity<String> deleteFile(@PathVariable String fileName) {
    s3Service.deleteFile(fileName);
    return ResponseEntity.ok("File deleted successfully");
  }
}
