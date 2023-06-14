package app.mappers.dto;

public class OrgRoleDTO {
    private String description;

    public OrgRoleDTO(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return description;
    }
}
