package AWB.espaceadmin.repository;

import AWB.espaceadmin.model.Application;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    Optional<Application> findByName(String applicationName);
}
