package fabriziopesaresi.CapstoneProject_GymPulse.dto.response;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class BookingResponse {
    private Long id;
    private String status;
    private LocalDate date;
    private LocalDateTime createdAt;
    private String courseName;
    private String instructor;
    private LocalTime startTime;
    private LocalTime endTime;
}