package com.expansion.lg.kimaru.training.objs

/**
 * Created by kimaru on 2/21/18.
 */

class Cohort {
    var id: Int? = null
    var cohortNumber: String
    var cohortName: String
    var branchId: Int? = null
    var isArchived: Boolean = false

    constructor() {}

    constructor(id: Int?, cohortNumber: String, cohortName: String, branchId: Int?,
                archived: Boolean) {
        this.id = id
        this.cohortNumber = cohortNumber
        this.cohortName = cohortName
        this.branchId = branchId
        this.isArchived = archived
    }
}
