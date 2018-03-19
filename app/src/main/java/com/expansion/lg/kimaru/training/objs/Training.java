package com.expansion.lg.kimaru.training.objs;

/**
 * Created by kimaru on 2/21/18.
 */

public class Training {
    private String id, trainingName, country, subCountyId, wardId, district, recruitmentId, parishId;
    private String trainingVenueId, dateCreated, comment;
    private int countyId, locationId, trainingStatusId, createdBy;
    private double lat,lon;
    private Long clientTime, dateCommenced, dateCompleted;
    private boolean archived;

    int color = -1;

    public Training() {}

    public Training(String id, String trainingName, String country, String subCountyId,
                    String wardId, String district, String recruitmentId, String parishId,
                    String trainingVenueId, String dateCreated, int countyId, int locationId,
                    int trainingStatusId, int createdBy, String comment, double lat, double lon,
                    Long clientTime, Long dateCommenced, Long dateCompleted, boolean archived) {
        this.id = id;
        this.trainingName = trainingName;
        this.country = country;
        this.subCountyId = subCountyId;
        this.wardId = wardId;
        this.district = district;
        this.recruitmentId = recruitmentId;
        this.parishId = parishId;
        this.trainingVenueId = trainingVenueId;
        this.dateCreated = dateCreated;
        this.countyId = countyId;
        this.locationId = locationId;
        this.trainingStatusId = trainingStatusId;
        this.createdBy = createdBy;
        this.comment = comment;
        this.lat = lat;
        this.lon = lon;
        this.clientTime = clientTime;
        this.dateCommenced = dateCommenced;
        this.dateCompleted = dateCompleted;
        this.archived = archived;
    }

    public String getId() {
        return id;
    }

    public String getTrainingName() {
        return trainingName;
    }

    public String getCountry() {
        return country;
    }

    public String getSubCountyId() {
        return subCountyId;
    }

    public String getWardId() {
        return wardId;
    }

    public String getDistrict() {
        return district;
    }

    public String getRecruitmentId() {
        return recruitmentId;
    }

    public String getParishId() {
        return parishId;
    }

    public String getTrainingVenueId() {
        return trainingVenueId;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public int getCountyId() {
        return countyId;
    }

    public int getLocationId() {
        return locationId;
    }

    public int getTrainingStatusId() {
        return trainingStatusId;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public String getComment() {
        return comment;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public Long getClientTime() {
        return clientTime;
    }

    public Long getDateCommenced() {
        return dateCommenced;
    }

    public Long getDateCompleted() {
        return dateCompleted;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTrainingName(String trainingName) {
        this.trainingName = trainingName;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setSubCountyId(String subCountyId) {
        this.subCountyId = subCountyId;
    }

    public void setWardId(String wardId) {
        this.wardId = wardId;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public void setRecruitmentId(String recruitmentId) {
        this.recruitmentId = recruitmentId;
    }

    public void setParishId(String parishId) {
        this.parishId = parishId;
    }

    public void setTrainingVenueId(String trainingVenueId) {
        this.trainingVenueId = trainingVenueId;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void setCountyId(int countyId) {
        this.countyId = countyId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public void setTrainingStatusId(int trainingStatusId) {
        this.trainingStatusId = trainingStatusId;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public void setClientTime(Long clientTime) {
        this.clientTime = clientTime;
    }

    public void setDateCommenced(Long dateCommenced) {
        this.dateCommenced = dateCommenced;
    }

    public void setDateCompleted(Long dateCompleted) {
        this.dateCompleted = dateCompleted;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
