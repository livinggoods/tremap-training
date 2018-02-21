package com.expansion.lg.kimaru.training.objs;

/**
 * Created by kimaru on 2/21/18.
 */

public class SessionAttendance {
    String id, trainingSessionId, traineeId, trainingId, country, dateCreated, metaData, comment;
    Integer trainingSessionTypeId, createdBy;
    boolean attended, archived;
    Long clientTime;

    public SessionAttendance() {
    }

    public SessionAttendance(String id, String trainingSessionId, String traineeId,
                             String trainingId, String country, String dateCreated,
                             String metaData, String comment, Integer trainingSessionTypeId,
                             Integer createdBy, boolean attended, boolean archived,
                             Long clientTime) {
        this.id = id;
        this.trainingSessionId = trainingSessionId;
        this.traineeId = traineeId;
        this.trainingId = trainingId;
        this.country = country;
        this.dateCreated = dateCreated;
        this.metaData = metaData;
        this.comment = comment;
        this.trainingSessionTypeId = trainingSessionTypeId;
        this.createdBy = createdBy;
        this.attended = attended;
        this.archived = archived;
        this.clientTime = clientTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTrainingSessionId() {
        return trainingSessionId;
    }

    public void setTrainingSessionId(String trainingSessionId) {
        this.trainingSessionId = trainingSessionId;
    }

    public String getTraineeId() {
        return traineeId;
    }

    public void setTraineeId(String traineeId) {
        this.traineeId = traineeId;
    }

    public String getTrainingId() {
        return trainingId;
    }

    public void setTrainingId(String trainingId) {
        this.trainingId = trainingId;
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

    public String getMetaData() {
        return metaData;
    }

    public void setMetaData(String metaData) {
        this.metaData = metaData;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getTrainingSessionTypeId() {
        return trainingSessionTypeId;
    }

    public void setTrainingSessionTypeId(Integer trainingSessionTypeId) {
        this.trainingSessionTypeId = trainingSessionTypeId;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public boolean isAttended() {
        return attended;
    }

    public void setAttended(boolean attended) {
        this.attended = attended;
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
