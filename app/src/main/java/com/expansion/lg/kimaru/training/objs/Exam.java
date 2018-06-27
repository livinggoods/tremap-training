package com.expansion.lg.kimaru.training.objs;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by kimaru on 6/27/18 using Android Studio.
 * Maintainer: David Kimaru
 *
 * @github https://github.com/kimarudg
 * @email kimarudg@gmail.com
 * @phone +254722384549
 * @web gakuu.co.ke
 */

public class Exam {
    boolean archived;
    String country, dateCreated;
    int createdBy, examStatusId, id;
    double passmark;
    JSONObject examStatus, exam;
    JSONArray questions;

    public Exam() {}

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
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

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public int getExamStatusId() {
        return examStatusId;
    }

    public void setExamStatusId(int examStatusId) {
        this.examStatusId = examStatusId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPassmark() {
        return passmark;
    }

    public void setPassmark(double passmark) {
        this.passmark = passmark;
    }

    public JSONObject getExamStatus() {
        return examStatus;
    }

    public void setExamStatus(JSONObject examStatus) {
        this.examStatus = examStatus;
    }

    public JSONObject getExam() {
        return exam;
    }

    public void setExam(JSONObject exam) {
        this.exam = exam;
    }

    public JSONArray getQuestions() {
        return questions;
    }

    public void setQuestions(JSONArray questions) {
        this.questions = questions;
    }
}
