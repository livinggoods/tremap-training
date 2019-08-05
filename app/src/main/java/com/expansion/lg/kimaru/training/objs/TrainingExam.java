package com.expansion.lg.kimaru.training.objs;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Date;

/**
 * Created by kimaru on 6/5/18 using Android Studio.
 * Maintainer: David Kimaru
 *
 * @github https://github.com/kimarudg
 * @email kimarudg@gmail.com
 * @phone +254722384549
 * @web gakuu.co.ke
 */

public class TrainingExam {
    Integer id, examId, createdBy, passmark, statusId;
    boolean archived;
    String dateAdministered, dateCreated, trainingId, country, title;
    String examCode = "";
    int color = -1;
    JSONObject cloudExamJson;

    JSONObject status;
    JSONArray questions;

    int certificationTypeId = -1;

    public TrainingExam() {}

    public TrainingExam(Integer id, Integer examId, Integer createdBy, Integer passmark,
                        boolean archived, String dateAdministered, String dateCreated,
                        String trainingId, String country, String title,
                        JSONObject cloudExamJson, int certificationTypeId) {
        this.id = id;
        this.examId = examId;
        this.createdBy = createdBy;
        this.passmark = passmark;
        this.archived = archived;
        this.dateAdministered = dateAdministered;
        this.dateCreated = dateCreated;
        this.trainingId = trainingId;
        this.country = country;
        this.title = title;
        this.cloudExamJson = cloudExamJson;
        this.certificationTypeId = certificationTypeId;
        try {
            this.examCode = cloudExamJson.getString("code");
            this.questions = cloudExamJson.getJSONArray("questions");
            this.status = cloudExamJson.getJSONObject("exam_status");
            this.statusId = cloudExamJson.getInt("exam_status_id");
        }catch (Exception e){}

    }

    public Integer getId() {
        return id;
    }

    public Integer getExamId() {
        return examId;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public Integer getPassmark() {
        return passmark;
    }

    public boolean isArchived() {
        return archived;
    }

    public String getDateAdministered() {
        return dateAdministered;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public String getTrainingId() {
        return trainingId;
    }

    public String getCountry() {
        return country;
    }

    public String getTitle() {
        return title;
    }

    public int getColor() {
        return color;
    }

    public String getExamCode() {
        return examCode;
    }

    public JSONObject getCloudExamJson() {
        return cloudExamJson;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public JSONObject getStatus() {
        return status;
    }

    public JSONArray getQuestions() {
        JSONArray questions = null;
        try{
             return cloudExamJson.getJSONArray("questions");
        }catch (Exception e){
            return questions;
        }
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setExamId(Integer examId) {
        this.examId = examId;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public void setPassmark(Integer passmark) {
        this.passmark = passmark;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public void setDateAdministered(String dateAdministered) {
        this.dateAdministered = dateAdministered;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void setTrainingId(String trainingId) {
        this.trainingId = trainingId;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setExamCode(String examCode) {
        this.examCode = examCode;
    }

    public void setCloudExamJson(JSONObject cloudExamJson) {
        this.cloudExamJson = cloudExamJson;
        try {
            this.examCode = cloudExamJson.getString("code");
            this.questions = cloudExamJson.getJSONArray("questions");
            this.status = cloudExamJson.getJSONObject("exam_status");
            this.statusId = cloudExamJson.getInt("exam_status_id");
        }catch (Exception e){}
    }

    public int getCertificationTypeId() {
        return certificationTypeId;
    }

    public void setCertificationTypeId(int certificationTypeId) {
        this.certificationTypeId = certificationTypeId;
    }
}
