package com.expansion.lg.kimaru.training.objs

/**
 * Created by kimaru on 2/21/18.
 */

class TrainingTrainer {
    var id: Int? = null
    var classId: Int? = null
    var trainerId: Int? = null
    var createdBy: Int? = null
    var trainingRoleId: Int? = null
    var trainingId: String
    var country: String
    var dateCreated: String
    var comment: String
    var clientTime: Long? = null
    var isArchived: Boolean = false

    constructor() {}

    constructor(id: Int?, classId: Int?, trainerId: Int?, createdBy: Int?,
                trainingRoleId: Int?, trainingId: String, country: String,
                dateCreated: String, comment: String, clientTime: Long?, archived: Boolean) {
        this.id = id
        this.classId = classId
        this.trainerId = trainerId
        this.createdBy = createdBy
        this.trainingRoleId = trainingRoleId
        this.trainingId = trainingId
        this.country = country
        this.dateCreated = dateCreated
        this.comment = comment
        this.clientTime = clientTime
        this.isArchived = archived
    }
}
