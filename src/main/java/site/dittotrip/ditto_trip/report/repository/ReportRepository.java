package site.dittotrip.ditto_trip.report.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.dittotrip.ditto_trip.report.domain.Report;

public interface ReportRepository extends JpaRepository<Report, Long> {


}
