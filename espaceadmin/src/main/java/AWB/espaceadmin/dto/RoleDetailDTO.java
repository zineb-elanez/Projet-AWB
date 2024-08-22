package AWB.espaceadmin.dto;

import java.util.List;

public class RoleDetailDTO {

    private Long id;
    private String name;
    private List<UserSimpleDTO> users;

    public RoleDetailDTO(){

    }

    public RoleDetailDTO(Long id) {
        this.id = id;
    }

    public RoleDetailDTO(String name) {
        this.name = name;
    }

    public RoleDetailDTO(List<UserSimpleDTO> users) {
        this.users = users;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<UserSimpleDTO> getUsers() {
        return users;
    }

    public void setUsers(List<UserSimpleDTO> users) {
        this.users = users;
    }
}
