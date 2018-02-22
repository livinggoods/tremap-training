package com.expansion.lg.kimaru.training.objs;

/**
 * Created by kimaru on 2/21/18.
 */

public class TrainingVenue {
    String id, name, mapping, country, dateAdded, metaData;
    Double lat, lon;
    Integer inspected, capacity, addedBy;
    Long clientTime;
    boolean archived, selected;

    public TrainingVenue() {
    }

    public TrainingVenue(String id, String name, String mapping, String country,
                         String dateAdded, String metaData, Double lat, Double lon,
                         Integer inspected, Integer capacity, Integer addedBy, Long clientTime,
                         boolean archived, boolean selected) {
        this.id = id;
        this.name = name;
        this.mapping = mapping;
        this.country = country;
        this.dateAdded = dateAdded;
        this.metaData = metaData;
        this.lat = lat;
        this.lon = lon;
        this.inspected = inspected;
        this.capacity = capacity;
        this.addedBy = addedBy;
        this.clientTime = clientTime;
        this.archived = archived;
        this.selected = selected;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMapping() {
        return mapping;
    }

    public void setMapping(String mapping) {
        this.mapping = mapping;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getMetaData() {
        return metaData;
    }

    public void setMetaData(String metaData) {
        this.metaData = metaData;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Integer getInspected() {
        return inspected;
    }

    public void setInspected(Integer inspected) {
        this.inspected = inspected;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Integer getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(Integer addedBy) {
        this.addedBy = addedBy;
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

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
