package AWB.espaceadmin.dto;

import java.util.List;

public class ApplicationDTO {
    private Long id;
    private String name;
    private List<UserRoleDTO> userRoles;

    // Getters and setters


    public List<UserRoleDTO> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<UserRoleDTO> userRoles) {
        this.userRoles = userRoles;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public static class UserRoleDTO {
        private Long userId;
        private String username;
        private Long roleId;
        private String roleName;

        // Getters and setters

        public String getRoleName() {
            return roleName;
        }

        public void setRoleName(String roleName) {
            this.roleName = roleName;
        }

        public Long getRoleId() {
            return roleId;
        }

        public void setRoleId(Long roleId) {
            this.roleId = roleId;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }

        public void setUserName(String username) {
            this.username=username;
        }
    }
}