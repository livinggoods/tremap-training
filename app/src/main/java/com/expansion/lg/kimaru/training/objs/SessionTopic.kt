package com.expansion.lg.kimaru.training.objs

/**
 * Created by kimaru on 2/21/18.
 */

class SessionTopic {

    var name: String
    var country: String
    var metaData: String
    var comment: String
    var dateAdded: String
    var isArchived: Boolean = false
    var id: Int? = null
    var addedBy: Int? = null

    constructor() {}

    constructor(name: String, country: String, metaData: String, comment: String,
                dateAdded: String, archived: Boolean, id: Int?, addedBy: Int?) {
        this.name = name
        this.country = country
        this.metaData = metaData
        this.comment = comment
        this.dateAdded = dateAdded
        this.isArchived = archived
        this.id = id
        this.addedBy = addedBy
    }
}