package com.expansion.lg.kimaru.training.objs;

/**
 * Created by kimaru on 2/21/18.
 */

public class TrainingTrainer {
    Integer id, classId, trainerId, createdBy, trainingRoleId;
    String trainingId, country, dateCreated, comment;
    Long clientTime;
    boolean archived;

    public TrainingTrainer() {
    }

    public TrainingTrainer(Integer id, Integer classId, Integer trainerId, Integer createdBy,
                           Integer trainingRoleId, String trainingId, String country,
                           String dateCreated, String comment, Long clientTime, boolean archived) {
        this.id = id;
        this.classId = classId;
        this.trainerId = trainerId;
        this.createdBy = createdBy;
        this.trainingRoleId = trainingRoleId;
        this.trainingId = trainingId;
        this.country = country;
        this.dateCreated = dateCreated;
        this.comment = comment;
        this.clientTime = clientTime;
        this.archived = archived;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getTrainingRoleId() {
        return trainingRoleId;
    }

    public void setTrainingRoleId(Integer trainingRoleId) {
        this.trainingRoleId = trainingRoleId;
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

    public Long getClientTime() {
        return clientTime;
    }

    public void setClientTime(Long clientTime) {
        this.clientTime = clientTime;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }
}
