package sec.project.domain.models;

public class AdminsModel {
    private String name;
    private String username;

    public AdminsModel(String name, String username) {
        this.name = name;
        this.username = username;
    }

    public AdminsModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return username;
    }

    public void setId(String username) {
        this.username = username;
    }
}
