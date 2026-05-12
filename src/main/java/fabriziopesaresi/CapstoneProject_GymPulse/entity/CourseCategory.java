package fabriziopesaresi.CapstoneProject_GymPulse.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "course_categories")
@Data
public class CourseCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    private String icon;
}