package com.expansion.lg.kimaru.training.objs

/**
 * Created by kimaru on 2/21/18.
 */

class TrainingRole {
    var id: Int? = null
    var createdBy: Int? = null
    var roleName: String
    var country: String
    var dateCreated: String
    var comment: String
    var isArchived: Boolean = false
    var isReadonly: Boolean = false
    var clientTime: Long? = null

    constructor() {}

    constructor(id: Int?, createdBy: Int?, roleName: String, country: String,
                dateCreated: String, comment: String, archived: Boolean, readonly: Boolean,
                clientTime: Long?) {
        this.id = id
        this.createdBy = createdBy
        this.roleName = roleName
        this.country = country
        this.dateCreated = dateCreated
        this.comment = comment
        this.isArchived = archived
        this.isReadonly = readonly
        this.clientTime = clientTime
    }
}
