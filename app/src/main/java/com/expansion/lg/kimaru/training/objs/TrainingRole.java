package com.expansion.lg.kimaru.training.objs;

/**
 * Created by kimaru on 2/21/18.
 */

public class TrainingRole {
    Integer id, createdBy;
    String roleName, country, dateCreated, comment;
    boolean archived, readonly;
    Long clientTime;

    public TrainingRole() {
    }

    public TrainingRole(Integer id, Integer createdBy, String roleName, String country,
                        String dateCreated, String comment, boolean archived, boolean readonly,
                        Long clientTime) {
        this.id = id;
        this.createdBy = createdBy;
        this.roleName = roleName;
        this.country = country;
        this.dateCreated = dateCreated;
        this.comment = comment;
        this.archived = archived;
        this.readonly = readonly;
        this.clientTime = clientTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public boolean isReadonly() {
        return readonly;
    }

    public void setReadonly(boolean readonly) {
        this.readonly = readonly;
    }

    public Long getClientTime() {
        return clientTime;
    }

    public void setClientTime(Long clientTime) {
        this.clientTime = clientTime;
    }
}
