package AWB.espaceadmin.controller;

import AWB.espaceadmin.dto.UserDetailDTO;
import AWB.espaceadmin.model.User;
import AWB.espaceadmin.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;
    /*@GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }



    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{userId}/roles/{roleId}")
    public ResponseEntity<User> assignRoleToUser(@PathVariable Long userId, @PathVariable Long roleId) {
        User user = userService.assignRoleToUser(userId, roleId);
        return ResponseEntity.ok(user);
    }*/
    @GetMapping
    public ResponseEntity<List<UserDetailDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsersWithRoles());
    }
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @PutMapping("/{userId}/roles/{roleId}")
    public ResponseEntity<UserDetailDTO> assignRoleToUser(@PathVariable Long userId, @PathVariable Long roleId) {
        UserDetailDTO updatedUser = userService.assignRoleToUser(userId, roleId);
        return ResponseEntity.ok(updatedUser);
    }
}
