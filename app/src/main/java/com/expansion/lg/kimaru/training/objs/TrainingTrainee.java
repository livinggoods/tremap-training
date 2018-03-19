package com.expansion.lg.kimaru.training.objs;

import org.json.JSONObject;

/**
 * Created by kimaru on 2/21/18.
 */

public class TrainingTrainee {
    String id, registrationId, dateCreated, trainingId, country, chpCode, comment, branch;
    Integer classId, addedBy, cohort;
    Long clientTime;
    boolean archived;
    JSONObject registration;
    int color =-1;

    public TrainingTrainee() {
    }

    public TrainingTrainee(String id, String registrationId, String dateCreated,
                           String trainingId, String country, String chpCode, String comment,
                           Integer classId, Integer addedBy, String branch, Integer cohort,
                           Long clientTime, boolean archived, JSONObject registration) {
        this.id = id;
        this.registrationId = registrationId;
        this.dateCreated = dateCreated;
        this.trainingId = trainingId;
        this.country = country;
        this.chpCode = chpCode;
        this.comment = comment;
        this.classId = classId;
        this.addedBy = addedBy;
        this.branch = branch;
        this.cohort = cohort;
        this.clientTime = clientTime;
        this.archived = archived;
        this.registration = registration;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
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

    public String getChpCode() {
        return chpCode;
    }

    public void setChpCode(String chpCode) {
        this.chpCode = chpCode;
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

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public Integer getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(Integer addedBy) {
        this.addedBy = addedBy;
    }

    public Integer getCohort() {
        return cohort;
    }

    public void setCohort(Integer cohort) {
        this.cohort = cohort;
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

    public JSONObject getRegistration() {
        return registration;
    }

    public void setRegistration(JSONObject registration) {
        this.registration = registration;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
