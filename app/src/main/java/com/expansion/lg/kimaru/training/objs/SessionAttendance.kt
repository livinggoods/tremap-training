package com.expansion.lg.kimaru.training.objs

/**
 * Created by kimaru on 2/21/18.
 */

class SessionAttendance {
    var id: String
    var trainingSessionId: String
    var traineeId: String
    var trainingId: String
    var country: String
    var dateCreated: String
    var metaData: String
    var comment: String
    var trainingSessionTypeId: Int? = null
    var createdBy: Int? = null
    var isAttended: Boolean = false
    var isArchived: Boolean = false
    var clientTime: Long? = null

    constructor() {}

    constructor(id: String, trainingSessionId: String, traineeId: String,
                trainingId: String, country: String, dateCreated: String,
                metaData: String, comment: String, trainingSessionTypeId: Int?,
                createdBy: Int?, attended: Boolean, archived: Boolean,
                clientTime: Long?) {
        this.id = id
        this.trainingSessionId = trainingSessionId
        this.traineeId = traineeId
        this.trainingId = trainingId
        this.country = country
        this.dateCreated = dateCreated
        this.metaData = metaData
        this.comment = comment
        this.trainingSessionTypeId = trainingSessionTypeId
        this.createdBy = createdBy
        this.isAttended = attended
        this.isArchived = archived
        this.clientTime = clientTime
    }
}
