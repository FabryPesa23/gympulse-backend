package fabriziopesaresi.CapstoneProject_GymPulse.service;

import fabriziopesaresi.CapstoneProject_GymPulse.dto.response.BookingResponse;
import fabriziopesaresi.CapstoneProject_GymPulse.entity.*;
import fabriziopesaresi.CapstoneProject_GymPulse.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final TimeSlotRepository timeSlotRepository;
    private final UserRepository userRepository;
    private final WaitListRepository waitListRepository;
    private final MailgunService mailgunService;

    public List<BookingResponse> getMyBookings() {
        User user = getCurrentUser();
        return bookingRepository.findByUserId(user.getId())
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public BookingResponse bookCourse(Long timeSlotId, LocalDate date) {
        User user = getCurrentUser();

        TimeSlot timeSlot = timeSlotRepository.findById(timeSlotId)
                .orElseThrow(() -> new RuntimeException("Time slot non trovato"));

        bookingRepository.findByUserIdAndTimeSlotIdAndDate(
                        user.getId(), timeSlotId, date)
                .ifPresent(b -> { throw new RuntimeException("Hai già prenotato questo corso"); });

        int booked = bookingRepository.countByTimeSlotIdAndDateAndStatus(
                timeSlotId, date, Booking.Status.CONFIRMED);

        if (booked >= timeSlot.getCourse().getMaxCapacity()) {
            int position = waitListRepository.countByTimeSlotIdAndDate(timeSlotId, date) + 1;
            WaitList waitList = new WaitList();
            waitList.setUser(user);
            waitList.setTimeSlot(timeSlot);
            waitList.setDate(date);
            waitList.setPosition(position);
            waitListRepository.save(waitList);
            throw new RuntimeException("Corso pieno — sei in lista d'attesa alla posizione " + position);
        }

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setTimeSlot(timeSlot);
        booking.setDate(date);
        booking.setStatus(Booking.Status.CONFIRMED);

        Booking saved = bookingRepository.save(booking);

        // Invio email di conferma
        mailgunService.sendBookingConfirmation(
                user.getEmail(),
                user.getFirstName(),
                timeSlot.getCourse().getName(),
                date.toString(),
                timeSlot.getStartTime().toString()
        );

        return toResponse(saved);
    }

    @Transactional
    public void cancelBooking(Long bookingId) {
        User user = getCurrentUser();
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Prenotazione non trovata"));

        if (!booking.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Non puoi cancellare questa prenotazione");
        }

        booking.setStatus(Booking.Status.CANCELLED);
        bookingRepository.save(booking);

        List<WaitList> waitList = waitListRepository
                .findByTimeSlotIdAndDateOrderByPositionAsc(
                        booking.getTimeSlot().getId(), booking.getDate());

        if (!waitList.isEmpty()) {
            WaitList first = waitList.get(0);
            Booking newBooking = new Booking();
            newBooking.setUser(first.getUser());
            newBooking.setTimeSlot(booking.getTimeSlot());
            newBooking.setDate(booking.getDate());
            newBooking.setStatus(Booking.Status.CONFIRMED);
            bookingRepository.save(newBooking);
            waitListRepository.delete(first);

            // Invio email notifica waitlist
            mailgunService.sendWaitlistNotification(
                    first.getUser().getEmail(),
                    first.getUser().getFirstName(),
                    booking.getTimeSlot().getCourse().getName(),
                    booking.getDate().toString(),
                    booking.getTimeSlot().getStartTime().toString()
            );

            waitList.stream().skip(1).forEach(w -> {
                w.setPosition(w.getPosition() - 1);
                waitListRepository.save(w);
            });
        }
    }

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext()
                .getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));
    }

    private BookingResponse toResponse(Booking booking) {
        BookingResponse response = new BookingResponse();
        response.setId(booking.getId());
        response.setStatus(booking.getStatus().name());
        response.setDate(booking.getDate());
        response.setCreatedAt(booking.getCreatedAt());
        response.setCourseName(booking.getTimeSlot().getCourse().getName());
        response.setInstructor(booking.getTimeSlot().getCourse().getInstructor());
        response.setStartTime(booking.getTimeSlot().getStartTime());
        response.setEndTime(booking.getTimeSlot().getEndTime());
        return response;
    }
}