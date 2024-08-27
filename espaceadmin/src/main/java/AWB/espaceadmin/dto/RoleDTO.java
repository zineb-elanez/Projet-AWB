package AWB.espaceadmin.dto;

import java.util.List;

public class RoleDTO {
    private Long id;
    private String name;
    private List<UserApplicationDTO> userApplications;

    // Getters and setters


    public List<UserApplicationDTO> getUserApplications() {
        return userApplications;
    }

    public void setUserApplications(List<UserApplicationDTO> userApplications) {
        this.userApplications = userApplications;
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

    public static class UserApplicationDTO {
        private Long userId;
        private String username;
        private Long applicationId;
        private String applicationName;

        // Getters and setters

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