package com.expansion.lg.kimaru.training.objs

/**
 * Created by kimaru on 2/21/18.
 */

class TrainingSessionType {
    var id: String
    var sessionName: String
    var country: String
    var dateCreated: String
    var comment: String
    var classId: Int? = null
    var createdBy: Int? = null
    var isArchived: Boolean = false
    var clientTime: Long? = null

    constructor() {}

    constructor(id: String, sessionName: String, country: String, dateCreated: String,
                comment: String, classId: Int?, createdBy: Int?,
                archived: Boolean, clientTime: Long?) {
        this.id = id
        this.sessionName = sessionName
        this.country = country
        this.dateCreated = dateCreated
        this.comment = comment
        this.classId = classId
        this.createdBy = createdBy
        this.isArchived = archived
        this.clientTime = clientTime
    }
}
