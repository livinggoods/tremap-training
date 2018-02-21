package com.expansion.lg.kimaru.training.objs;

/**
 * Created by kimaru on 2/21/18.
 */

public class Cohort {
    Integer id;
    String cohortNumber;
    String cohortName;
    Integer branchId;
    boolean archived;

    public Cohort() {
    }

    public Cohort(Integer id, String cohortNumber, String cohortName, Integer branchId,
                  boolean archived) {
        this.id = id;
        this.cohortNumber = cohortNumber;
        this.cohortName = cohortName;
        this.branchId = branchId;
        this.archived = archived;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCohortNumber() {
        return cohortNumber;
    }

    public void setCohortNumber(String cohortNumber) {
        this.cohortNumber = cohortNumber;
    }

    public String getCohortName() {
        return cohortName;
    }

    public void setCohortName(String cohortName) {
        this.cohortName = cohortName;
    }

    public Integer getBranchId() {
        return branchId;
    }

    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }
}
