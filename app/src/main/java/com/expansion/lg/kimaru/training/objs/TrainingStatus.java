package com.expansion.lg.kimaru.training.objs;

/**
 * Created by kimaru on 2/21/18.
 */

public class TrainingStatus {
    Integer id, createdBy;
    String name, country, comment;
    boolean archived, readonly;
    Long clientTime, dateCreated;

    public TrainingStatus() {
    }

    public TrainingStatus(Integer id, Integer createdBy, String name, String country,
                          String comment, boolean archived, boolean readonly, Long clientTime,
                          Long dateCreated) {
        this.id = id;
        this.createdBy = createdBy;
        this.name = name;
        this.country = country;
        this.comment = comment;
        this.archived = archived;
        this.readonly = readonly;
        this.clientTime = clientTime;
        this.dateCreated = dateCreated;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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

    public Long getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Long dateCreated) {
        this.dateCreated = dateCreated;
    }
}
