package fabriziopesaresi.CapstoneProject_GymPulse.repository;

import fabriziopesaresi.CapstoneProject_GymPulse.entity.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TimeSlotRepository extends JpaRepository<TimeSlot, Long> {
    List<TimeSlot> findByCourseId(Long courseId);
    List<TimeSlot> findByDayOfWeek(String dayOfWeek);
}