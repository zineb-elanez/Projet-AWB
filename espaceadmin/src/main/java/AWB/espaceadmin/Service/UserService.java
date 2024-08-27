package AWB.espaceadmin.Service;

import AWB.espaceadmin.dto.UserDTO;
import AWB.espaceadmin.model.Application;
import AWB.espaceadmin.model.Role;
import AWB.espaceadmin.model.User;
import AWB.espaceadmin.model.UserApplicationRole;
import AWB.espaceadmin.repository.ApplicationRepository;
import AWB.espaceadmin.repository.RoleRepository;
import AWB.espaceadmin.repository.UserApplicationRoleRepository;
import AWB.espaceadmin.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private ApplicationRepository applicationRepository;
    @Autowired
    private UserApplicationRoleRepository userApplicationRoleRepository;

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        return convertToDTO(user);
    }

    public UserDTO createUser(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        return convertToDTO(userRepository.save(user));
    }

    /*public UserDTO updateUser(Long id, UserDTO userDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        user.setUsername(userDTO.getUsername());
        return convertToDTO(userRepository.save(user));

    } au cas ou */
    /*public UserDTO updateUser(Long id, UserDTO userDTO) {
        // Récupérer l'utilisateur à mettre à jour
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        // Mettre à jour le nom d'utilisateur
        user.setUsername(userDTO.getUsername());

        // Supprimer les anciennes associations de rôles d'application
        List<UserApplicationRole> existingRoles = userApplicationRoleRepository.findByUserId(id);
        for (UserApplicationRole role : existingRoles) {
            userApplicationRoleRepository.delete(role);
        }

        // Ajouter les nouveaux rôles d'application
        for (UserDTO.ApplicationRoleDTO roleDTO : userDTO.getApplicationRoles()) {
            Application application = applicationRepository.findById(roleDTO.getApplicationId())
                    .orElseThrow(() -> new EntityNotFoundException("Application not found"));

            // Si vous devez mettre à jour le nom de l'application, assurez-vous que c'est vraiment nécessaire
            if (!application.getName().equals(roleDTO.getApplicationName())) {
                application.setName(roleDTO.getApplicationName());
                applicationRepository.save(application);
            }

            Role role = roleRepository.findById(roleDTO.getRoleId())
                    .orElseThrow(() -> new EntityNotFoundException("Role not found"));

            // Si vous devez mettre à jour le nom du rôle, assurez-vous que c'est vraiment nécessaire
            if (!role.getName().equals(roleDTO.getRoleName())) {
                role.setName(roleDTO.getRoleName());
                roleRepository.save(role);
            }

            // Créer une nouvelle association UserApplicationRole
            UserApplicationRole newRole = new UserApplicationRole();
            newRole.setUser(user);
            newRole.setApplication(application);
            newRole.setRole(role);

            userApplicationRoleRepository.save(newRole);
        }

        // Sauvegarder l'utilisateur avec les nouvelles informations
        userRepository.save(user);

        // Convertir et renvoyer le DTO mis à jour
        return convertToDTO(user);
    }*/

    @Transactional
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));

        user.setUsername(userDTO.getUsername());

        // Supprimer les anciennes associations
        userApplicationRoleRepository.deleteByUserId(id);

        for (UserDTO.ApplicationRoleDTO appRoleDTO : userDTO.getApplicationRoles()) {
            // Trouver ou créer l'application
            Application application = applicationRepository
                    .findByName(appRoleDTO.getApplicationName())
                    .orElseGet(() -> {
                        Application newApp = new Application();
                        newApp.setName(appRoleDTO.getApplicationName());
                        return applicationRepository.save(newApp);
                    });

            // Trouver ou créer le rôle
            Role role = roleRepository
                    .findByName(appRoleDTO.getRoleName())
                    .orElseGet(() -> {
                        Role newRole = new Role();
                        newRole.setName(appRoleDTO.getRoleName());
                        return roleRepository.save(newRole);
                    });

            // Créer et sauvegarder la nouvelle association
            UserApplicationRole userAppRole = new UserApplicationRole();
            userAppRole.setUser(user);
            userAppRole.setApplication(application);
            userAppRole.setRole(role);
            userApplicationRoleRepository.save(userAppRole);
        }

        userRepository.save(user);

        return convertToDTO(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public UserDTO assignRoleToUserInApplication(Long userId, Long roleId, Long applicationId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new EntityNotFoundException("Role not found"));
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new EntityNotFoundException("Application not found"));

        UserApplicationRole userApplicationRole = new UserApplicationRole();
        userApplicationRole.setUser(user);
        userApplicationRole.setRole(role);
        userApplicationRole.setApplication(application);

        userApplicationRoleRepository.save(userApplicationRole);

        return convertToDTO(user);
    }

    public UserDTO removeRoleFromUserInApplication(Long userId, Long roleId, Long applicationId) {
        UserApplicationRole userApplicationRole = userApplicationRoleRepository
                .findByUserIdAndRoleIdAndApplicationId(userId, roleId, applicationId);
        if (userApplicationRole != null) {
            userApplicationRoleRepository.delete(userApplicationRole);
        }
        return getUserById(userId);
    }

    private UserDTO convertToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());

        List<UserApplicationRole> userRoles = userApplicationRoleRepository.findByUserId(user.getId());
        dto.setApplicationRoles(userRoles.stream()
                .map(this::convertToApplicationRoleDTO)
                .collect(Collectors.toList()));

        return dto;
    }

    private UserDTO.ApplicationRoleDTO convertToApplicationRoleDTO(UserApplicationRole uar) {
        UserDTO.ApplicationRoleDTO dto = new UserDTO.ApplicationRoleDTO();
        dto.setApplicationId(uar.getApplication().getId());
        dto.setApplicationName(uar.getApplication().getName());
        dto.setRoleId(uar.getRole().getId());
        dto.setRoleName(uar.getRole().getName());
        return dto;
    }
}