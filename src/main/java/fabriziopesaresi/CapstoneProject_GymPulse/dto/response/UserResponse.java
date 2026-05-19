package fabriziopesaresi.CapstoneProject_GymPulse.dto.response;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class UserResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private LocalDate dateOfBirth;
    private String profileImageUrl;
    private String role;
    private LocalDateTime createdAt;
}