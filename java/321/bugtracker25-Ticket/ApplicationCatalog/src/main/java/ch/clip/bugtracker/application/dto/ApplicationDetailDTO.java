package ch.clip.bugtracker.application.dto;

public class ApplicationDetailDTO {
    private Integer id;
    private String name;
    private String description;
    private Integer ownerId;
    private String ownerName;
    private String ownerRole;

    public ApplicationDetailDTO() {
    }

    public ApplicationDetailDTO(Integer id, String name, String description, Integer ownerId, String ownerName, String ownerRole) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.ownerId = ownerId;
        this.ownerName = ownerName;
        this.ownerRole = ownerRole;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerRole() {
        return ownerRole;
    }

    public void setOwnerRole(String ownerRole) {
        this.ownerRole = ownerRole;
    }
}
