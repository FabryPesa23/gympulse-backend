package fabriziopesaresi.CapstoneProject_GymPulse.dto.response;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CourseResponse {
    private Long id;
    private String name;
    private String description;
    private String instructor;
    private Integer maxCapacity;
    private Integer durationMinutes;
    private String difficulty;
    private String imageUrl;
    private LocalDateTime createdAt;
    private CourseCategoryResponse category;
    private Integer availableSlots;
}