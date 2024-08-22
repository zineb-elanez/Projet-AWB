package AWB.espaceadmin.dto;

public class UserSimpleDTO {
    private Long id;
    private String name;

    // constructeur, getters et setters

    public UserSimpleDTO(Long userId, String userName) {
        this.id = userId;
        this.name = userName;
    }
    public UserSimpleDTO(Long id) {
        this.id = id;
    }

    public UserSimpleDTO(String name) {
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
