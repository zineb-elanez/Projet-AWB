package AWB.espaceadmin.repository;

import AWB.espaceadmin.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<AWB.espaceadmin.model.Role, Long> {
    Optional<Role> findByName(String roleName);
}
