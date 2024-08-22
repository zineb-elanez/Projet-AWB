package AWB.espaceadmin.Service;

import AWB.espaceadmin.dto.RoleDetailDTO;
import AWB.espaceadmin.dto.UserSimpleDTO;
import AWB.espaceadmin.model.Role;
import AWB.espaceadmin.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public Role createRole(Role role) {
        return roleRepository.save(role);
    }

    public Optional<Role> getRoleById(Long roleId) {
        return roleRepository.findById(roleId);
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();

    }
    public List<RoleDetailDTO> getAllRolesWithUsers() {
        List<Role> roles = roleRepository.findAll();
        return roles.stream()
                .map(this::convertToRoleDetailDTO)
                .collect(Collectors.toList());
    }

    private RoleDetailDTO convertToRoleDetailDTO(Role role) {
        RoleDetailDTO dto = new RoleDetailDTO();
        dto.setId(role.getRoleId());
        dto.setName(role.getRoleName());
        dto.setUsers(role.getUsers().stream()
                .map(user -> new UserSimpleDTO(user.getUserId(), user.getUserName()))
                .collect(Collectors.toList()));
        return dto;
    }
}
