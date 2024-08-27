package AWB.espaceadmin.dto;

import java.util.List;

public class UserDTO {
    private Long id;
    private String username;
    private List<ApplicationRoleDTO> applicationRoles;

    // Getters and setters


    public List<ApplicationRoleDTO> getApplicationRoles() {
        return applicationRoles;
    }

    public void setApplicationRoles(List<ApplicationRoleDTO> applicationRoles) {
        this.applicationRoles = applicationRoles;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public static class ApplicationRoleDTO {
        private Long applicationId;
        private String applicationName;
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

        public String getApplicationName() {
            return applicationName;
        }

        public void setApplicationName(String applicationName) {
            this.applicationName = applicationName;
        }

        public Long getApplicationId() {
            return applicationId;
        }

        public void setApplicationId(Long applicationId) {
            this.applicationId = applicationId;
        }
    }
}