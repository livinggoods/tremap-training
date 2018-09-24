package com.expansion.lg.kimaru.training.objs

/**
 * Created by kimaru on 2/21/18.
 */

class Training {
    var id: String? = null
    var trainingName: String? = null
    var country: String? = null
    var subCountyId: String? = null
    var wardId: String? = null
    var district: String? = null
    var recruitmentId: String? = null
    var parishId: String? = null
    var trainingVenueId: String? = null
    var dateCreated: String? = null
    var comment: String? = null
    var countyId: Int = 0
    var locationId: Int = 0
    var trainingStatusId: Int = 0
    var createdBy: Int = 0
    var lat: Double = 0.toDouble()
    var lon: Double = 0.toDouble()
    var clientTime: Long? = null
    var dateCommenced: Long? = null
    var dateCompleted: Long? = null
    var isArchived: Boolean = false

    var color = -1

    constructor() {}

    constructor(id: String, trainingName: String, country: String, subCountyId: String,
                wardId: String, district: String, recruitmentId: String, parishId: String,
                trainingVenueId: String, dateCreated: String, countyId: Int, locationId: Int,
                trainingStatusId: Int, createdBy: Int, comment: String, lat: Double, lon: Double,
                clientTime: Long?, dateCommenced: Long?, dateCompleted: Long?, archived: Boolean) {
        this.id = id
        this.trainingName = trainingName
        this.country = country
        this.subCountyId = subCountyId
        this.wardId = wardId
        this.district = district
        this.recruitmentId = recruitmentId
        this.parishId = parishId
        this.trainingVenueId = trainingVenueId
        this.dateCreated = dateCreated
        this.countyId = countyId
        this.locationId = locationId
        this.trainingStatusId = trainingStatusId
        this.createdBy = createdBy
        this.comment = comment
        this.lat = lat
        this.lon = lon
        this.clientTime = clientTime
        this.dateCommenced = dateCommenced
        this.dateCompleted = dateCompleted
        this.isArchived = archived
    }
}
