package sec.project.domain.models;

public class UserModel {
    private String name;
    private Long id;

    public UserModel(String name, Long id) {
        this.name = name;
        this.id = id;
    }

    public UserModel() {
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
}
