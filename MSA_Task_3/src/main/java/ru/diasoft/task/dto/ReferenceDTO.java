package ru.diasoft.task.dto;

public class ReferenceDTO {

    private static Long counter = new Long(1L);

    private Long id;
    private String sysname;
    private String name;
    private String description;

    public ReferenceDTO(String sysname, String name, String description) {
        this.sysname = sysname;
        this.name = name;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId() {
        this.id = counter++;
    }

    public String getSysname() {
        return sysname;
    }

    public void setSysname(String sysname) {
        this.sysname = sysname;
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
}
