package fabriziopesaresi.CapstoneProject_GymPulse.controller;

import fabriziopesaresi.CapstoneProject_GymPulse.dto.response.TimeSlotResponse;
import fabriziopesaresi.CapstoneProject_GymPulse.entity.Booking;
import fabriziopesaresi.CapstoneProject_GymPulse.entity.TimeSlot;
import fabriziopesaresi.CapstoneProject_GymPulse.repository.BookingRepository;
import fabriziopesaresi.CapstoneProject_GymPulse.repository.TimeSlotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/timeslots")
@RequiredArgsConstructor
public class TimeSlotController {

    private final TimeSlotRepository timeSlotRepository;
    private final BookingRepository bookingRepository;

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<TimeSlotResponse>> getByCourse(
            @PathVariable Long courseId,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        LocalDate targetDate = date != null ? date : LocalDate.now();

        List<TimeSlotResponse> slots = timeSlotRepository.findByCourseId(courseId)
                .stream()
                .map(slot -> toResponse(slot, targetDate))
                .toList();

        return ResponseEntity.ok(slots);
    }

    @GetMapping("/day/{dayOfWeek}")
    public ResponseEntity<List<TimeSlotResponse>> getByDay(
            @PathVariable String dayOfWeek) {
        List<TimeSlotResponse> slots = timeSlotRepository.findByDayOfWeek(dayOfWeek.toUpperCase())
                .stream()
                .map(slot -> toResponse(slot, LocalDate.now()))
                .toList();
        return ResponseEntity.ok(slots);
    }

    @PostMapping("/course/{courseId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TimeSlotResponse> createTimeSlot(
            @PathVariable Long courseId,
            @RequestBody TimeSlot timeSlot) {
        timeSlot.getCourse().setId(courseId);
        TimeSlot saved = timeSlotRepository.save(timeSlot);
        return ResponseEntity.ok(toResponse(saved, LocalDate.now()));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteTimeSlot(@PathVariable Long id) {
        timeSlotRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private TimeSlotResponse toResponse(TimeSlot slot, LocalDate date) {
        TimeSlotResponse response = new TimeSlotResponse();
        response.setId(slot.getId());
        response.setDayOfWeek(slot.getDayOfWeek());
        response.setStartTime(slot.getStartTime());
        response.setEndTime(slot.getEndTime());

        int booked = bookingRepository.countByTimeSlotIdAndDateAndStatus(
                slot.getId(), date, Booking.Status.CONFIRMED);
        response.setAvailableSlots(slot.getCourse().getMaxCapacity() - booked);

        return response;
    }
}