package fabriziopesaresi.CapstoneProject_GymPulse.dto.response;

import lombok.Data;

@Data
public class GymZoneResponse {
    private Long id;
    private String name;
    private Integer maxCapacity;
    private String imageUrl;
    private Integer currentLevel;
    private String occupancyStatus;
}