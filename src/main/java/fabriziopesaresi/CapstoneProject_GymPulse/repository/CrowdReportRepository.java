package fabriziopesaresi.CapstoneProject_GymPulse.repository;

import fabriziopesaresi.CapstoneProject_GymPulse.entity.CrowdReport;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CrowdReportRepository extends JpaRepository<CrowdReport, Long> {
    List<CrowdReport> findByZoneIdAndTimestampAfterOrderByTimestampDesc(
            Long zoneId, LocalDateTime after);
    Optional<CrowdReport> findTopByZoneIdOrderByTimestampDesc(Long zoneId);
}