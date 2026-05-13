package fabriziopesaresi.CapstoneProject_GymPulse.dto.response;

import lombok.Data;
import java.time.LocalTime;

@Data
public class TimeSlotResponse {
    private Long id;
    private String dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;
    private Integer availableSlots;
}