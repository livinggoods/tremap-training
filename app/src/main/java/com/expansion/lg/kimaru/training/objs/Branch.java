package com.expansion.lg.kimaru.training.objs;

/**
 * Created by kimaru on 2/21/18.
 */

public class Branch {
    Integer id;
    String branchName;
    String branchCode;
    String mappingId;
    Double lat;
    Double lon;
    boolean archived;

    public Branch() {
    }

    public Branch(Integer id, String branchName, String branchCode, String mappingId, Double lat,
                  Double lon, boolean archived) {
        this.id = id;
        this.branchName = branchName;
        this.branchCode = branchCode;
        this.mappingId = mappingId;
        this.lat = lat;
        this.lon = lon;
        this.archived = archived;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getMappingId() {
        return mappingId;
    }

    public void setMappingId(String mappingId) {
        this.mappingId = mappingId;
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

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }
}