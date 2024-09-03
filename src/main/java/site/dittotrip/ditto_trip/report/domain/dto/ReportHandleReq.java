package site.dittotrip.ditto_trip.report.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReportHandleReq {

    private Boolean shouldDeleteContent;
    private Boolean shouldPermanentlyBan;
    private Integer SuspensionDays;

}