package fabriziopesaresi.CapstoneProject_GymPulse.repository;

import fabriziopesaresi.CapstoneProject_GymPulse.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUserId(Long userId);
    List<Booking> findByTimeSlotIdAndDate(Long timeSlotId, LocalDate date);
    Optional<Booking> findByUserIdAndTimeSlotIdAndDate(Long userId, Long timeSlotId, LocalDate date);
    int countByTimeSlotIdAndDateAndStatus(Long timeSlotId, LocalDate date, Booking.Status status);
}