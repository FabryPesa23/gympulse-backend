package fabriziopesaresi.CapstoneProject_GymPulse.controller;

import fabriziopesaresi.CapstoneProject_GymPulse.dto.response.CourseCategoryResponse;
import fabriziopesaresi.CapstoneProject_GymPulse.entity.CourseCategory;
import fabriziopesaresi.CapstoneProject_GymPulse.repository.CourseCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CourseCategoryController {

    private final CourseCategoryRepository categoryRepository;

    @GetMapping
    public ResponseEntity<List<CourseCategoryResponse>> getAll() {
        return ResponseEntity.ok(
                categoryRepository.findAll().stream().map(c -> {
                    CourseCategoryResponse r = new CourseCategoryResponse();
                    r.setId(c.getId());
                    r.setName(c.getName());
                    r.setIcon(c.getIcon());
                    return r;
                }).toList()
        );
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CourseCategoryResponse> create(@RequestBody CourseCategory category) {
        CourseCategory saved = categoryRepository.save(category);
        CourseCategoryResponse r = new CourseCategoryResponse();
        r.setId(saved.getId());
        r.setName(saved.getName());
        r.setIcon(saved.getIcon());
        return ResponseEntity.ok(r);
    }
}