package site.dittotrip.ditto_trip.auth.contoroller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class SignupReq {
  @NotBlank
  @Email
  private String email;

  @NotBlank
  private String password;

  @NotBlank
  private String nickname;

  @NotBlank
  private String code;
}