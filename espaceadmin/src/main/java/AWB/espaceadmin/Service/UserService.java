package AWB.espaceadmin.Service;

import AWB.espaceadmin.dto.RoleSimpleDTO;
import AWB.espaceadmin.dto.UserDetailDTO;
import AWB.espaceadmin.model.Role;
import AWB.espaceadmin.model.User;
import AWB.espaceadmin.repository.RoleRepository;
import AWB.espaceadmin.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jdk.internal.access.JavaLangInvokeAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    public User createUser(User user) {
        return userRepository.save(user);
    }

    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }

    /*public User assignRoleToUser(Long userId, Long roleId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Role role = roleRepository.findById(roleId).orElseThrow(() -> new RuntimeException("Role not found"));
        user.getRoles().add(role);
        return userRepository.save(user);
    }*/
    public User addRoleToUser(Long userId, Long roleId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        Role role = roleRepository.findById(roleId).orElseThrow(() -> new EntityNotFoundException("Role not found"));

        user.getRoles().add(role);
        return userRepository.save(user);
    }

    public List<UserDetailDTO> getAllUsersWithRoles() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::convertToUserDetailDTO)
                .collect(Collectors.toList());
    }

    private UserDetailDTO convertToUserDetailDTO(User user) {
        UserDetailDTO dto = new UserDetailDTO();
        dto.setId(user.getUserId());
        dto.setName(user.getUserName());

        dto.setRoles(user.getRoles().stream()
                .map(role -> new RoleSimpleDTO(role.getRoleId(), role.getRoleName()))
                .collect(Collectors.toList()));

        return dto;
    }
    @Transactional
    public UserDetailDTO assignRoleToUser(Long userId, Long roleId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new EntityNotFoundException("Role not found"));

        user.getRoles().add(role);
        userRepository.save(user);

        return convertToUserDetailDTO(user);
    }
}
