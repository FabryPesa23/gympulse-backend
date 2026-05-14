package fabriziopesaresi.CapstoneProject_GymPulse.controller;

import fabriziopesaresi.CapstoneProject_GymPulse.dto.request.UpdateUserRequest;
import fabriziopesaresi.CapstoneProject_GymPulse.dto.response.UserResponse;
import fabriziopesaresi.CapstoneProject_GymPulse.entity.User;
import fabriziopesaresi.CapstoneProject_GymPulse.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getMe() {
        User user = getCurrentUser();
        return ResponseEntity.ok(toResponse(user));
    }

    @PutMapping("/me")
    public ResponseEntity<UserResponse> updateMe(
            @RequestBody UpdateUserRequest request) {
        User user = getCurrentUser();

        if (request.getFirstName() != null) user.setFirstName(request.getFirstName());
        if (request.getLastName() != null) user.setLastName(request.getLastName());
        if (request.getPhone() != null) user.setPhone(request.getPhone());
        if (request.getDateOfBirth() != null) user.setDateOfBirth(request.getDateOfBirth());

        userRepository.save(user);
        return ResponseEntity.ok(toResponse(user));
    }

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext()
                .getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));
    }

    private UserResponse toResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setEmail(user.getEmail());
        response.setPhone(user.getPhone());
        response.setDateOfBirth(user.getDateOfBirth());
        response.setProfileImageUrl(user.getProfileImageUrl());
        response.setRole(user.getRole().name());
        response.setCreatedAt(user.getCreatedAt());
        return response;
    }
}