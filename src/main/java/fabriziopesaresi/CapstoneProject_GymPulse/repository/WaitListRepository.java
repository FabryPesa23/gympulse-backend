package fabriziopesaresi.CapstoneProject_GymPulse.repository;

import fabriziopesaresi.CapstoneProject_GymPulse.entity.WaitList;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface WaitListRepository extends JpaRepository<WaitList, Long> {
    List<WaitList> findByTimeSlotIdAndDateOrderByPositionAsc(Long timeSlotId, LocalDate date);
    Optional<WaitList> findByUserIdAndTimeSlotIdAndDate(Long userId, Long timeSlotId, LocalDate date);
    int countByTimeSlotIdAndDate(Long timeSlotId, LocalDate date);
}