package com.expansion.lg.kimaru.training.objs;

/**
 * Created by kimaru on 2/22/18.
 */

public class TrainingComment {
    String Id;
    String TraineeId;
    String TrainingId;
    String Country;
    Integer AddedBy;
    String DateCreated;
    Long ClientTime;
    boolean Archived;
    String Comment;

    public TrainingComment() {
    }

    public TrainingComment(String id, String traineeId, String trainingId, String country,
                           Integer addedBy, String dateCreated, Long clientTime, boolean archived,
                           String comment) {
        Id = id;
        TraineeId = traineeId;
        TrainingId = trainingId;
        Country = country;
        AddedBy = addedBy;
        DateCreated = dateCreated;
        ClientTime = clientTime;
        Archived = archived;
        Comment = comment;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getTraineeId() {
        return TraineeId;
    }

    public void setTraineeId(String traineeId) {
        TraineeId = traineeId;
    }

    public String getTrainingId() {
        return TrainingId;
    }

    public void setTrainingId(String trainingId) {
        TrainingId = trainingId;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public Integer getAddedBy() {
        return AddedBy;
    }

    public void setAddedBy(Integer addedBy) {
        AddedBy = addedBy;
    }

    public String getDateCreated() {
        return DateCreated;
    }

    public void setDateCreated(String dateCreated) {
        DateCreated = dateCreated;
    }

    public Long getClientTime() {
        return ClientTime;
    }

    public void setClientTime(Long clientTime) {
        ClientTime = clientTime;
    }

    public boolean isArchived() {
        return Archived;
    }

    public void setArchived(boolean archived) {
        Archived = archived;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }
}
