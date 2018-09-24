package com.expansion.lg.kimaru.training.objs

/**
 * Created by kimaru on 2/21/18.
 */

class TrainingVenue {
    var id: String
    var name: String
    var mapping: String
    var country: String
    var dateAdded: String
    var metaData: String
    var lat: Double? = null
    var lon: Double? = null
    var inspected: Int? = null
    var capacity: Int? = null
    var addedBy: Int? = null
    var clientTime: Long? = null
    var isArchived: Boolean = false
    var isSelected: Boolean = false

    constructor() {}

    constructor(id: String, name: String, mapping: String, country: String,
                dateAdded: String, metaData: String, lat: Double?, lon: Double?,
                inspected: Int?, capacity: Int?, addedBy: Int?, clientTime: Long?,
                archived: Boolean, selected: Boolean) {
        this.id = id
        this.name = name
        this.mapping = mapping
        this.country = country
        this.dateAdded = dateAdded
        this.metaData = metaData
        this.lat = lat
        this.lon = lon
        this.inspected = inspected
        this.capacity = capacity
        this.addedBy = addedBy
        this.clientTime = clientTime
        this.isArchived = archived
        this.isSelected = selected
    }
}
