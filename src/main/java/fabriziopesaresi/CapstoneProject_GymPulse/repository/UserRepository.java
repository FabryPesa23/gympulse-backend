package fabriziopesaresi.CapstoneProject_GymPulse.repository;

import fabriziopesaresi.CapstoneProject_GymPulse.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}