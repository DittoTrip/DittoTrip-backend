package site.dittotrip.ditto_trip.profile.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PasswordModifyReq {
    private String originPassword;
    private String newPassword;
}