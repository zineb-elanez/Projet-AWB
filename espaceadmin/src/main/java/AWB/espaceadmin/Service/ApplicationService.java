package AWB.espaceadmin.Service;

import AWB.espaceadmin.dto.ApplicationDTO;
import AWB.espaceadmin.model.Application;
import AWB.espaceadmin.model.User;
import AWB.espaceadmin.model.Role;
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
public class ApplicationService {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private UserApplicationRoleRepository userApplicationRoleRepository;

    public List<ApplicationDTO> getAllApplications() {
        return applicationRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ApplicationDTO getApplicationById(Long id) {
        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Application not found"));
        return convertToDTO(application);
    }

    public ApplicationDTO createApplication(ApplicationDTO applicationDTO) {
        Application application = new Application();
        application.setName(applicationDTO.getName());
        return convertToDTO(applicationRepository.save(application));
    }

   /* public ApplicationDTO updateApplication(Long id, ApplicationDTO applicationDTO) {
        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Application not found"));
        application.setName(applicationDTO.getName());
        return convertToDTO(applicationRepository.save(application));
    }*/
   @Transactional
   public ApplicationDTO updateApplication(Long id, ApplicationDTO applicationDTO) {
       Application application = applicationRepository.findById(id)
               .orElseThrow(() -> new EntityNotFoundException("Application not found with id: " + id));

       application.setName(applicationDTO.getName());

       // Supprimer les anciennes associations utilisateur-application-rôle
       userApplicationRoleRepository.deleteByApplicationId(id);

       for (ApplicationDTO.UserRoleDTO userRoleDTO : applicationDTO.getUserRoles()) {
           // Trouver ou créer l'utilisateur
           User user = userRepository
                   .findByUsername(userRoleDTO.getUsername())
                   .orElseGet(() -> {
                       User newUser = new User();
                       newUser.setUsername(userRoleDTO.getUsername());
                       return userRepository.save(newUser);
                   });

           // Trouver ou créer le rôle
           Role role = roleRepository
                   .findByName(userRoleDTO.getRoleName())
                   .orElseGet(() -> {
                       Role newRole = new Role();
                       newRole.setName(userRoleDTO.getRoleName());
                       return roleRepository.save(newRole);
                   });

           // Créer et sauvegarder la nouvelle association
           UserApplicationRole newAssociation = new UserApplicationRole();
           newAssociation.setApplication(application);
           newAssociation.setRole(role);
           newAssociation.setUser(user);
           userApplicationRoleRepository.save(newAssociation);
       }

       applicationRepository.save(application);

       return convertToDTO(application);
   }




    public void deleteApplication(Long id) {
        applicationRepository.deleteById(id);
    }

    private ApplicationDTO convertToDTO(Application application) {
        ApplicationDTO dto = new ApplicationDTO();
        dto.setId(application.getId());
        dto.setName(application.getName());

        List<UserApplicationRole> userRoles = userApplicationRoleRepository.findByApplicationId(application.getId());
        dto.setUserRoles(userRoles.stream()
                .map(this::convertToUserRoleDTO)
                .collect(Collectors.toList()));

        return dto;
    }

    private ApplicationDTO.UserRoleDTO convertToUserRoleDTO(UserApplicationRole uar) {
        ApplicationDTO.UserRoleDTO dto = new ApplicationDTO.UserRoleDTO();
        dto.setUserId(uar.getUser().getId());
        dto.setUserName(uar.getUser().getUsername());
        dto.setRoleId(uar.getRole().getId());
        dto.setRoleName(uar.getRole().getName());
        return dto;
    }
}
