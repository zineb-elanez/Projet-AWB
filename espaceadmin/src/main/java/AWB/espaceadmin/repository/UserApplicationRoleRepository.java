package AWB.espaceadmin.repository;

import AWB.espaceadmin.model.Application;
import AWB.espaceadmin.model.User;
import AWB.espaceadmin.model.UserApplicationRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserApplicationRoleRepository extends JpaRepository<UserApplicationRole, Long> {
    List<UserApplicationRole> findByApplicationId(Long applicationId);
    List<UserApplicationRole> findByUserId(Long userId);
    List<UserApplicationRole> findByRoleId(Long roleId);
    UserApplicationRole findByUserIdAndRoleIdAndApplicationId(Long userId, Long roleId, Long applicationId);

    Optional<UserApplicationRole> findByUserAndApplication(User user, Application application);

    List<UserApplicationRole> findByUser(User user);

    void deleteByUserId(Long id);

    void deleteByRoleId(Long id);

    void deleteByApplicationId(Long id);
}