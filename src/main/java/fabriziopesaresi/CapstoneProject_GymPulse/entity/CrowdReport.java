package fabriziopesaresi.CapstoneProject_GymPulse.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "crowd_reports")
@Data
public class CrowdReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "zone_id", nullable = false)
    private GymZone zone;

    @Column(nullable = false)
    private Integer level;

    @Column(nullable = false)
    private LocalDateTime timestamp = LocalDateTime.now();
}