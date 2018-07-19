package com.expansion.lg.kimaru.training.objs;

/**
 * Created by kimaru on 6/28/18 using Android Studio.
 * Maintainer: David Kimaru
 *
 * @github https://github.com/kimarudg
 * @email kimarudg@gmail.com
 * @phone +254722384549
 * @web gakuu.co.ke
 */

public class TrainingExamResult {
    String answer, country, dateCreated, traineeId;
    boolean archived;
    int choiceId, createdBy, id, questionId, questionScore, trainingExamId;
    int color = -1;

    public TrainingExamResult() {
    }

    public String getAnswer() {
        return answer;
    }

    public String getCountry() {
        return country;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public String getTraineeId() {
        return traineeId;
    }

    public boolean isArchived() {
        return archived;
    }

    public int getChoiceId() {
        return choiceId;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public int getId() {
        return id;
    }

    public int getQuestionId() {
        return questionId;
    }

    public int getQuestionScore() {
        return questionScore;
    }

    public int getTrainingExamId() {
        return trainingExamId;
    }


    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void setTraineeId(String traineeId) {
        this.traineeId = traineeId;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public void setChoiceId(int choiceId) {
        this.choiceId = choiceId;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public void setQuestionScore(int questionScore) {
        this.questionScore = questionScore;
    }

    public void setTrainingExamId(int trainingExamId) {
        this.trainingExamId = trainingExamId;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
