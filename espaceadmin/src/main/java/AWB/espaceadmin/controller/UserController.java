package AWB.espaceadmin.controller;

import AWB.espaceadmin.dto.UserDTO;
import AWB.espaceadmin.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.createUser(userDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.updateUser(id, userDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{userId}/roles/{roleId}/applications/{applicationId}")
    public ResponseEntity<UserDTO> assignRoleToUserInApplication(
            @PathVariable Long userId,
            @PathVariable Long roleId,
            @PathVariable Long applicationId) {
        return ResponseEntity.ok(userService.assignRoleToUserInApplication(userId, roleId, applicationId));
    }

    @DeleteMapping("/{userId}/roles/{roleId}/applications/{applicationId}")
    public ResponseEntity<UserDTO> removeRoleFromUserInApplication(
            @PathVariable Long userId,
            @PathVariable Long roleId,
            @PathVariable Long applicationId) {
        return ResponseEntity.ok(userService.removeRoleFromUserInApplication(userId, roleId, applicationId));
    }
}