package fabriziopesaresi.CapstoneProject_GymPulse.repository;

import fabriziopesaresi.CapstoneProject_GymPulse.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByCategoryId(Long categoryId);
}