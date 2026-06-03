package fabriziopesaresi.CapstoneProject_GymPulse.controller;

import fabriziopesaresi.CapstoneProject_GymPulse.dto.response.BookingResponse;
import fabriziopesaresi.CapstoneProject_GymPulse.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @GetMapping("/me")
    public ResponseEntity<List<BookingResponse>> getMyBookings() {
        return ResponseEntity.ok(bookingService.getMyBookings());
    }

    @PostMapping("/{timeSlotId}")
    public ResponseEntity<BookingResponse> bookCourse(
            @PathVariable Long timeSlotId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(bookingService.bookCourse(timeSlotId, date));
    }

    @DeleteMapping("/{bookingId}")
    public ResponseEntity<Void> cancelBooking(@PathVariable Long bookingId) {
        bookingService.cancelBooking(bookingId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{bookingId}/delete")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long bookingId) {
        bookingService.deleteBooking(bookingId);
        return ResponseEntity.noContent().build();
    }
}