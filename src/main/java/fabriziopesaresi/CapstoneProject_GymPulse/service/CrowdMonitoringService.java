package fabriziopesaresi.CapstoneProject_GymPulse.service;

import fabriziopesaresi.CapstoneProject_GymPulse.dto.request.CrowdReportRequest;
import fabriziopesaresi.CapstoneProject_GymPulse.dto.response.GymZoneResponse;
import fabriziopesaresi.CapstoneProject_GymPulse.entity.CrowdReport;
import fabriziopesaresi.CapstoneProject_GymPulse.entity.GymZone;
import fabriziopesaresi.CapstoneProject_GymPulse.entity.User;
import fabriziopesaresi.CapstoneProject_GymPulse.repository.CrowdReportRepository;
import fabriziopesaresi.CapstoneProject_GymPulse.repository.GymZoneRepository;
import fabriziopesaresi.CapstoneProject_GymPulse.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CrowdMonitoringService {

    private final GymZoneRepository gymZoneRepository;
    private final CrowdReportRepository crowdReportRepository;
    private final UserRepository userRepository;

    public List<GymZoneResponse> getAllZonesWithOccupancy() {
        return gymZoneRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public GymZoneResponse reportCrowd(CrowdReportRequest request) {
        String email = SecurityContextHolder.getContext()
                .getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));

        GymZone zone = gymZoneRepository.findById(request.getZoneId())
                .orElseThrow(() -> new RuntimeException("Zona non trovata"));

        CrowdReport report = new CrowdReport();
        report.setUser(user);
        report.setZone(zone);
        report.setLevel(request.getLevel());

        crowdReportRepository.save(report);

        return toResponse(zone);
    }

    private GymZoneResponse toResponse(GymZone zone) {
        GymZoneResponse response = new GymZoneResponse();
        response.setId(zone.getId());
        response.setName(zone.getName());
        response.setMaxCapacity(zone.getMaxCapacity());
        response.setImageUrl(zone.getImageUrl());

        crowdReportRepository.findTopByZoneIdOrderByTimestampDesc(zone.getId())
                .ifPresentOrElse(
                        report -> {
                            response.setCurrentLevel(report.getLevel());
                            response.setOccupancyStatus(getStatus(report.getLevel()));
                        },
                        () -> {
                            response.setCurrentLevel(0);
                            response.setOccupancyStatus("UNKNOWN");
                        }
                );

        return response;
    }

    private String getStatus(int level) {
        if (level <= 30) return "LOW";
        if (level <= 70) return "MEDIUM";
        return "HIGH";
    }
}