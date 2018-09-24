package com.expansion.lg.kimaru.training.objs

/**
 * Created by kimaru on 2/21/18.
 */

class Branch {
    var id: String
    var branchName: String
    var branchCode: String
    var mappingId: String
    var lat: Double? = null
    var lon: Double? = null
    var isArchived: Boolean = false

    constructor() {}

    constructor(id: String, branchName: String, branchCode: String, mappingId: String, lat: Double?,
                lon: Double?, archived: Boolean) {
        this.id = id
        this.branchName = branchName
        this.branchCode = branchCode
        this.mappingId = mappingId
        this.lat = lat
        this.lon = lon
        this.isArchived = archived
    }
}