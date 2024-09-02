package site.dittotrip.ditto_trip.report.domain.dto;

import lombok.Data;
import site.dittotrip.ditto_trip.report.domain.Report;

import java.util.ArrayList;
import java.util.List;

@Data
public class ReportListRes {

    private List<ReportData> reportDataList = new ArrayList<>();
    private Integer totalPage;

    public static ReportListRes fromEntities(List<Report> reports, Integer totalPage) {
        ReportListRes reportListRes = new ReportListRes();
        reportListRes.setTotalPage(totalPage);

        for (Report report : reports) {
            reportListRes.getReportDataList().add(ReportData.fromEntity(report));
        }
        return reportListRes;
    }

}
