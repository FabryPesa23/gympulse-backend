package fabriziopesaresi.CapstoneProject_GymPulse.service;

import fabriziopesaresi.CapstoneProject_GymPulse.dto.request.CourseRequest;
import fabriziopesaresi.CapstoneProject_GymPulse.dto.response.CourseCategoryResponse;
import fabriziopesaresi.CapstoneProject_GymPulse.dto.response.CourseResponse;
import fabriziopesaresi.CapstoneProject_GymPulse.entity.Booking;
import fabriziopesaresi.CapstoneProject_GymPulse.entity.Course;
import fabriziopesaresi.CapstoneProject_GymPulse.entity.CourseCategory;
import fabriziopesaresi.CapstoneProject_GymPulse.repository.BookingRepository;
import fabriziopesaresi.CapstoneProject_GymPulse.repository.CourseCategoryRepository;
import fabriziopesaresi.CapstoneProject_GymPulse.repository.CourseRepository;
import fabriziopesaresi.CapstoneProject_GymPulse.repository.TimeSlotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final CourseCategoryRepository categoryRepository;
    private final BookingRepository bookingRepository;
    private final TimeSlotRepository timeSlotRepository;

    public List<CourseResponse> getAllCourses() {
        return courseRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public CourseResponse getCourseById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Corso non trovato"));
        return toResponse(course);
    }

    public List<CourseResponse> getCoursesByCategory(Long categoryId) {
        return courseRepository.findByCategoryId(categoryId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public CourseResponse createCourse(CourseRequest request) {
        CourseCategory category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Categoria non trovata"));

        Course course = new Course();
        course.setCategory(category);
        course.setName(request.getName());
        course.setDescription(request.getDescription());
        course.setInstructor(request.getInstructor());
        course.setMaxCapacity(request.getMaxCapacity());
        course.setDurationMinutes(request.getDurationMinutes());
        course.setDifficulty(request.getDifficulty());
        course.setImageUrl(request.getImageUrl());

        return toResponse(courseRepository.save(course));
    }

    public CourseResponse updateCourse(Long id, CourseRequest request) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Corso non trovato"));

        CourseCategory category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Categoria non trovata"));

        course.setCategory(category);
        course.setName(request.getName());
        course.setDescription(request.getDescription());
        course.setInstructor(request.getInstructor());
        course.setMaxCapacity(request.getMaxCapacity());
        course.setDurationMinutes(request.getDurationMinutes());
        course.setDifficulty(request.getDifficulty());
        course.setImageUrl(request.getImageUrl());

        return toResponse(courseRepository.save(course));
    }

    public void deleteCourse(Long id) {
        courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Corso non trovato"));
        courseRepository.deleteById(id);
    }

    public CourseResponse toResponse(Course course) {
        CourseResponse response = new CourseResponse();
        response.setId(course.getId());
        response.setName(course.getName());
        response.setDescription(course.getDescription());
        response.setInstructor(course.getInstructor());
        response.setMaxCapacity(course.getMaxCapacity());
        response.setDurationMinutes(course.getDurationMinutes());
        response.setDifficulty(course.getDifficulty());
        response.setImageUrl(course.getImageUrl());
        response.setCreatedAt(course.getCreatedAt());

        if (course.getCategory() != null) {
            CourseCategoryResponse cat = new CourseCategoryResponse();
            cat.setId(course.getCategory().getId());
            cat.setName(course.getCategory().getName());
            cat.setIcon(course.getCategory().getIcon());
            response.setCategory(cat);
        }

        // Calcola available slots per la data di oggi
        LocalDate today = LocalDate.now();
        var slots = timeSlotRepository.findByCourseId(course.getId());

        if (slots.isEmpty()) {
            response.setAvailableSlots(course.getMaxCapacity());
        } else {
            int minAvailable = slots.stream()
                    .mapToInt(slot -> {
                        int booked = bookingRepository.countByTimeSlotIdAndDateAndStatus(
                                slot.getId(), today, Booking.Status.CONFIRMED);
                        return course.getMaxCapacity() - booked;
                    })
                    .min()
                    .orElse(course.getMaxCapacity());
            response.setAvailableSlots(minAvailable);
        }

        return response;
    }
}