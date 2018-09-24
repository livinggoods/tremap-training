package com.expansion.lg.kimaru.training.objs

/**
 * Created by kimaru on 2/21/18.
 */

class TrainingSession {
    var id: String
    var trainingId: String
    var country: String
    var dateCreated: String
    var comment: String
    var trainingSessionTypeId: Int? = null
    var classId: Int? = null
    var trainerId: Int? = null
    var sessionTopicId: Int? = null
    var sessionLeadTrainer: Int? = null
    var createdBy: Int? = null
    var isArchived: Boolean = false
    var sessionStartTime: Long? = null
    var sessionEndTime: Long? = null
    var clientTime: Long? = null

    var color = -1

    constructor() {}

    constructor(id: String, trainingId: String, country: String, dateCreated: String,
                comment: String, trainingSessionTypeId: Int?, classId: Int?,
                trainerId: Int?, sessionTopicId: Int?, sessionLeadTrainer: Int?,
                createdBy: Int?, archived: Boolean, sessionStartTime: Long?,
                sessionEndTime: Long?, clientTime: Long?) {
        this.id = id
        this.trainingId = trainingId
        this.country = country
        this.dateCreated = dateCreated
        this.comment = comment
        this.trainingSessionTypeId = trainingSessionTypeId
        this.classId = classId
        this.trainerId = trainerId
        this.sessionTopicId = sessionTopicId
        this.sessionLeadTrainer = sessionLeadTrainer
        this.createdBy = createdBy
        this.isArchived = archived
        this.sessionStartTime = sessionStartTime
        this.sessionEndTime = sessionEndTime
        this.clientTime = clientTime
    }
}
