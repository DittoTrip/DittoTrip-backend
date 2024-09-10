package site.dittotrip.ditto_trip.auth.domain.dto;

import lombok.Data;
import lombok.NonNull;

@Data
public class VerifyCodeReq {
  @NonNull
  private String email;
  @NonNull
  private String code;

}
