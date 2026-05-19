package fabriziopesaresi.CapstoneProject_GymPulse.controller;

import fabriziopesaresi.CapstoneProject_GymPulse.dto.request.CrowdReportRequest;
import fabriziopesaresi.CapstoneProject_GymPulse.dto.response.GymZoneResponse;
import fabriziopesaresi.CapstoneProject_GymPulse.entity.GymZone;
import fabriziopesaresi.CapstoneProject_GymPulse.repository.GymZoneRepository;
import fabriziopesaresi.CapstoneProject_GymPulse.service.CrowdMonitoringService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/zones")
@RequiredArgsConstructor
public class CrowdMonitoringController {

    private final CrowdMonitoringService crowdMonitoringService;
    private final GymZoneRepository gymZoneRepository;

    @GetMapping
    public ResponseEntity<List<GymZoneResponse>> getAllZones() {
        return ResponseEntity.ok(crowdMonitoringService.getAllZonesWithOccupancy());
    }

    @PostMapping("/report")
    public ResponseEntity<GymZoneResponse> reportCrowd(
            @Valid @RequestBody CrowdReportRequest request) {
        return ResponseEntity.ok(crowdMonitoringService.reportCrowd(request));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GymZone> createZone(@RequestBody GymZone zone) {
        return ResponseEntity.ok(gymZoneRepository.save(zone));
    }
}