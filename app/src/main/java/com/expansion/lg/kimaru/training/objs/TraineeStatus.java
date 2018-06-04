package com.expansion.lg.kimaru.training.objs;

/**
 * Created by kimaru on 4/10/18 using Android Studio.
 * Maintainer: David Kimaru
 *
 * @github https://github.com/kimarudg
 * @email kimarudg@gmail.com
 * @phone +254722384549
 * @web gakuu.co.ke
 */

public class TraineeStatus {
    int id;
    boolean archived, readonly;
    String name, country, dateCreated;
    Long clientTime;
    int createdBy;

    public TraineeStatus(){}

    public TraineeStatus(int id, boolean archived, boolean readonly, String name, String country,
                         String dateCreated, Long clientTime, int createdBy) {
        this.id = id;
        this.archived = archived;
        this.readonly = readonly;
        this.name = name;
        this.country = country;
        this.dateCreated = dateCreated;
        this.clientTime = clientTime;
        this.createdBy = createdBy;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Long getClientTime() {
        return clientTime;
    }

    public void setClientTime(Long clientTime) {
        this.clientTime = clientTime;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int created_by) {
        this.createdBy = created_by;
    }
}
