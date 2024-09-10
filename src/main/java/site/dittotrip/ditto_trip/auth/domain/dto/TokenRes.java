package site.dittotrip.ditto_trip.auth.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TokenRes {
  private String accessToken;
  private String refreshToken;
}
