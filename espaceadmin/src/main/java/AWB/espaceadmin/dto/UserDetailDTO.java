package AWB.espaceadmin.dto;

import java.util.List;

public class UserDetailDTO {
    private Long id;
    private String name;
    private List<RoleSimpleDTO> roles;

    // constructeur, getters et setters
    public UserDetailDTO(){

    }

    public UserDetailDTO(Long id) {
        this.id = id;
    }

    public UserDetailDTO(String name) {
        this.name = name;
    }

    public UserDetailDTO(List<RoleSimpleDTO> roles) {
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<RoleSimpleDTO> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleSimpleDTO> roles) {
        this.roles = roles;
    }
}
