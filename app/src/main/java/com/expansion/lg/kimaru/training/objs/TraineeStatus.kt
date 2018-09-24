package com.expansion.lg.kimaru.training.objs

/**
 * Created by kimaru on 4/10/18 using Android Studio.
 * Maintainer: David Kimaru
 *
 * @github https://github.com/kimarudg
 * @email kimarudg@gmail.com
 * @phone +254722384549
 * @web gakuu.co.ke
 */

class TraineeStatus {
    var id: Int = 0
    var isArchived: Boolean = false
    var isReadonly: Boolean = false
    var name: String
    var country: String
    var dateCreated: String
    var clientTime: Long? = null
    var createdBy: Int = 0

    constructor() {}

    constructor(id: Int, archived: Boolean, readonly: Boolean, name: String, country: String,
                dateCreated: String, clientTime: Long?, createdBy: Int) {
        this.id = id
        this.isArchived = archived
        this.isReadonly = readonly
        this.name = name
        this.country = country
        this.dateCreated = dateCreated
        this.clientTime = clientTime
        this.createdBy = createdBy
    }
}
