package fabriziopesaresi.CapstoneProject_GymPulse.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CourseRequest {

    @NotNull
    private Long categoryId;

    @NotBlank(message = "Il nome è obbligatorio")
    private String name;

    private String description;

    @NotBlank(message = "L'istruttore è obbligatorio")
    private String instructor;

    @NotNull
    @Min(1)
    private Integer maxCapacity;

    @NotNull
    @Min(1)
    private Integer durationMinutes;

    private String difficulty;

    private String imageUrl;
}