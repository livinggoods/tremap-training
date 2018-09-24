package com.expansion.lg.kimaru.training.objs

import org.json.JSONObject

/**
 * Created by kimaru on 2/21/18.
 */

class TrainingTrainee {
    var id: String
    var registrationId: String
    var dateCreated: String
    var trainingId: String
    var country: String
    var chpCode: String
    var comment: String
    var branch: String
    var classId: Int? = null
    var addedBy: Int? = null
    var cohort: Int? = null
    var traineeStatus: Int? = null
    var clientTime: Long? = null
    var isArchived: Boolean = false
    var registration: JSONObject
    var color = -1

    constructor() {}

    constructor(id: String, registrationId: String, dateCreated: String,
                trainingId: String, country: String, chpCode: String, comment: String,
                classId: Int?, addedBy: Int?, branch: String, cohort: Int?,
                clientTime: Long?, archived: Boolean, registration: JSONObject, traineeStatus: Int) {
        this.id = id
        this.registrationId = registrationId
        this.dateCreated = dateCreated
        this.trainingId = trainingId
        this.country = country
        this.chpCode = chpCode
        this.comment = comment
        this.classId = classId
        this.addedBy = addedBy
        this.branch = branch
        this.cohort = cohort
        this.clientTime = clientTime
        this.isArchived = archived
        this.registration = registration
        this.traineeStatus = traineeStatus
    }
}
