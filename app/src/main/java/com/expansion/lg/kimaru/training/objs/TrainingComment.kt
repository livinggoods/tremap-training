package com.expansion.lg.kimaru.training.objs

/**
 * Created by kimaru on 2/22/18.
 */

class TrainingComment {
    var id: String
    var traineeId: String
    var trainingId: String
    var country: String
    var addedBy: Int? = null
    var dateCreated: String
    var clientTime: Long? = null
    var isArchived: Boolean = false
    var comment: String

    constructor() {}

    constructor(id: String, traineeId: String, trainingId: String, country: String,
                addedBy: Int?, dateCreated: String, clientTime: Long?, archived: Boolean,
                comment: String) {
        this.id = id
        this.traineeId = traineeId
        this.trainingId = trainingId
        this.country = country
        this.addedBy = addedBy
        this.dateCreated = dateCreated
        this.clientTime = clientTime
        isArchived = archived
        this.comment = comment
    }
}
