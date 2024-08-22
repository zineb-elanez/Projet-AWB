package AWB.espaceadmin.dto;

public class RoleSimpleDTO {
    private Long id;
    private String name;

    // constructeur, getters et setters
    public RoleSimpleDTO(Long roleId, String roleName) {
        this.id = roleId;
        this.name = roleName;
    }
    public RoleSimpleDTO(Long id) {
        this.id = id;
    }

    public RoleSimpleDTO(String name) {
        this.name = name;
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

}
