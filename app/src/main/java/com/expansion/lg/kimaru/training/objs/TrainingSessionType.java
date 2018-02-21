package com.expansion.lg.kimaru.training.objs;

/**
 * Created by kimaru on 2/21/18.
 */

public class TrainingSessionType {
    String id, sessionName, country, dateCreated, comment;
    Integer classId,createdBy;
    boolean archived;
    Long clientTime;

    public TrainingSessionType() {
    }

    public TrainingSessionType(String id, String sessionName, String country, String dateCreated,
                               String comment, Integer classId, Integer createdBy,
                               boolean archived, Long clientTime) {
        this.id = id;
        this.sessionName = sessionName;
        this.country = country;
        this.dateCreated = dateCreated;
        this.comment = comment;
        this.classId = classId;
        this.createdBy = createdBy;
        this.archived = archived;
        this.clientTime = clientTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSessionName() {
        return sessionName;
    }

    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
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

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public Long getClientTime() {
        return clientTime;
    }

    public void setClientTime(Long clientTime) {
        this.clientTime = clientTime;
    }
}
