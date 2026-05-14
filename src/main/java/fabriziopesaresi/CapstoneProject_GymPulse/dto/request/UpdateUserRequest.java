package fabriziopesaresi.CapstoneProject_GymPulse.dto.request;

import lombok.Data;
import java.time.LocalDate;

@Data
public class UpdateUserRequest {
    private String firstName;
    private String lastName;
    private String phone;
    private LocalDate dateOfBirth;
}