package com.expansion.lg.kimaru.training.objs

/**
 * Created by kimaru on 2/21/18.
 */

class TrainingStatus {
    var id: Int? = null
    var createdBy: Int? = null
    var name: String
    var country: String
    var comment: String
    var isArchived: Boolean = false
    var isReadonly: Boolean = false
    var clientTime: Long? = null
    var dateCreated: Long? = null

    constructor() {}

    constructor(id: Int?, createdBy: Int?, name: String, country: String,
                comment: String, archived: Boolean, readonly: Boolean, clientTime: Long?,
                dateCreated: Long?) {
        this.id = id
        this.createdBy = createdBy
        this.name = name
        this.country = country
        this.comment = comment
        this.isArchived = archived
        this.isReadonly = readonly
        this.clientTime = clientTime
        this.dateCreated = dateCreated
    }
}
