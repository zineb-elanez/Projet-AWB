package AWB.espaceadmin.controller;

import AWB.espaceadmin.dto.RoleDetailDTO;
import AWB.espaceadmin.model.Role;
import AWB.espaceadmin.Service.RoleService;
import AWB.espaceadmin.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;
   /* @GetMapping
    public List<Role> getAllRoles() {
        return roleService.getAllRoles();
    }




    @GetMapping("/{id}")
    public ResponseEntity<Role> getRoleById(@PathVariable Long id) {
        Optional<Role> role = roleService.getRoleById(id);
        return role.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }*/

    @GetMapping
    public ResponseEntity<List<RoleDetailDTO>> getAllRoles() {
        return ResponseEntity.ok(roleService.getAllRolesWithUsers());
    }
    @PostMapping
    public Role createRole(@RequestBody Role role) {
        return roleService.createRole(role);
    }
}
