package com.expansion.lg.kimaru.training.objs;

/**
 * Created by kimaru on 2/21/18.
 */

public class TrainingSession {
    String id, trainingId, country, dateCreated, comment;
    Integer trainingSessionTypeId, classId, trainerId, sessionTopicId, sessionLeadTrainer,createdBy;
    boolean archived;
    Long sessionStartTime, sessionEndTime, clientTime;

    public TrainingSession() {
    }

    public TrainingSession(String id, String trainingId, String country, String dateCreated,
                           String comment, Integer trainingSessionTypeId, Integer classId,
                           Integer trainerId, Integer sessionTopicId, Integer sessionLeadTrainer,
                           Integer createdBy, boolean archived, Long sessionStartTime,
                           Long sessionEndTime, Long clientTime) {
        this.id = id;
        this.trainingId = trainingId;
        this.country = country;
        this.dateCreated = dateCreated;
        this.comment = comment;
        this.trainingSessionTypeId = trainingSessionTypeId;
        this.classId = classId;
        this.trainerId = trainerId;
        this.sessionTopicId = sessionTopicId;
        this.sessionLeadTrainer = sessionLeadTrainer;
        this.createdBy = createdBy;
        this.archived = archived;
        this.sessionStartTime = sessionStartTime;
        this.sessionEndTime = sessionEndTime;
        this.clientTime = clientTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public Integer getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(Integer trainerId) {
        this.trainerId = trainerId;
    }

    public Integer getSessionTopicId() {
        return sessionTopicId;
    }

    public void setSessionTopicId(Integer sessionTopicId) {
        this.sessionTopicId = sessionTopicId;
    }

    public Integer getSessionLeadTrainer() {
        return sessionLeadTrainer;
    }

    public void setSessionLeadTrainer(Integer sessionLeadTrainer) {
        this.sessionLeadTrainer = sessionLeadTrainer;
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

    public Long getSessionStartTime() {
        return sessionStartTime;
    }

    public void setSessionStartTime(Long sessionStartTime) {
        this.sessionStartTime = sessionStartTime;
    }

    public Long getSessionEndTime() {
        return sessionEndTime;
    }

    public void setSessionEndTime(Long sessionEndTime) {
        this.sessionEndTime = sessionEndTime;
    }

    public Long getClientTime() {
        return clientTime;
    }

    public void setClientTime(Long clientTime) {
        this.clientTime = clientTime;
    }
}
