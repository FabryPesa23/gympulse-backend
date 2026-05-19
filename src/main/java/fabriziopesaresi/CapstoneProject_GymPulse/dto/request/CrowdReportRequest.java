package fabriziopesaresi.CapstoneProject_GymPulse.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CrowdReportRequest {

    @NotNull
    private Long zoneId;

    @NotNull
    @Min(0)
    @Max(100)
    private Integer level;
}