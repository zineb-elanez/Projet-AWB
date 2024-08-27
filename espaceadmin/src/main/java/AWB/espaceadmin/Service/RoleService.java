package AWB.espaceadmin.Service;

import AWB.espaceadmin.dto.RoleDTO;
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
public class RoleService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ApplicationRepository applicationRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserApplicationRoleRepository userApplicationRoleRepository;

    public List<RoleDTO> getAllRoles() {
        return roleRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public RoleDTO getRoleById(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Role not found"));
        return convertToDTO(role);
    }

    public RoleDTO createRole(RoleDTO roleDTO) {
        Role role = new Role();
        role.setName(roleDTO.getName());
        return convertToDTO(roleRepository.save(role));
    }

   /* public RoleDTO updateRole(Long id, RoleDTO roleDTO) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Role not found"));
        role.setName(roleDTO.getName());
        return convertToDTO(roleRepository.save(role));
    }*/
   @Transactional
   public RoleDTO updateRole(Long id, RoleDTO roleDTO) {
       Role role = roleRepository.findById(id)
               .orElseThrow(() -> new EntityNotFoundException("Role not found with id: " + id));

       role.setName(roleDTO.getName());

       // Supprimer les anciennes associations utilisateur-application-rôle
       userApplicationRoleRepository.deleteByRoleId(id);

       for (RoleDTO.UserApplicationDTO userAppDTO : roleDTO.getUserApplications()) {
           // Trouver ou créer l'application
           Application application = applicationRepository
                   .findByName(userAppDTO.getApplicationName())
                   .orElseGet(() -> {
                       Application newApp = new Application();
                       newApp.setName(userAppDTO.getApplicationName());
                       return applicationRepository.save(newApp);
                   });

           // Trouver ou créer l'utilisateur
           User user = userRepository
                   .findByUsername(userAppDTO.getUsername())
                   .orElseGet(() -> {
                       User newUser = new User();
                       newUser.setUsername(userAppDTO.getUsername());
                       return userRepository.save(newUser);
                   });

           // Créer et sauvegarder la nouvelle association
           UserApplicationRole newAssociation = new UserApplicationRole();
           newAssociation.setRole(role);
           newAssociation.setApplication(application);
           newAssociation.setUser(user);
           userApplicationRoleRepository.save(newAssociation);
       }

       roleRepository.save(role);

       return convertToDTO(role);
   }





    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }

    private RoleDTO convertToDTO(Role role) {
        RoleDTO dto = new RoleDTO();
        dto.setId(role.getId());
        dto.setName(role.getName());

        List<UserApplicationRole> userRoles = userApplicationRoleRepository.findByRoleId(role.getId());
        dto.setUserApplications(userRoles.stream()
                .map(this::convertToUserApplicationDTO)
                .collect(Collectors.toList()));

        return dto;
    }

    private RoleDTO.UserApplicationDTO convertToUserApplicationDTO(UserApplicationRole uar) {
        RoleDTO.UserApplicationDTO dto = new RoleDTO.UserApplicationDTO();
        dto.setUserId(uar.getUser().getId());
        dto.setUserName(uar.getUser().getUsername());
        dto.setApplicationId(uar.getApplication().getId());
        dto.setApplicationName(uar.getApplication().getName());
        return dto;
    }
}
