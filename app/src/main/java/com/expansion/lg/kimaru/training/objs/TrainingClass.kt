package com.expansion.lg.kimaru.training.objs

/**
 * Created by kimaru on 2/21/18.
 */

class TrainingClass {

    var id: Int? = null
    var createdBy: Int? = null
    var trainingId: String
    var className: String
    var country: String
    var dateCreated: String
    var comment: String
    var clientTime: Long? = null
    var isArchived: Boolean = false
    var color = -1

    constructor() {}

    constructor(id: Int?, createdBy: Int?, trainingId: String,
                className: String, country: String, dateCreated: String,
                comment: String, clientTime: Long?, archived: Boolean) {
        this.id = id
        this.createdBy = createdBy
        this.trainingId = trainingId
        this.className = className
        this.country = country
        this.dateCreated = dateCreated
        this.comment = comment
        this.clientTime = clientTime
        this.isArchived = archived
    }
}
