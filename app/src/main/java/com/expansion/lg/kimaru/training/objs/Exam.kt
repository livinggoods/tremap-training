package com.expansion.lg.kimaru.training.objs

import org.json.JSONArray
import org.json.JSONObject

/**
 * Created by kimaru on 6/27/18 using Android Studio.
 * Maintainer: David Kimaru
 *
 * @github https://github.com/kimarudg
 * @email kimarudg@gmail.com
 * @phone +254722384549
 * @web gakuu.co.ke
 */

class Exam {
    var isArchived: Boolean = false
    var country: String
    var dateCreated: String
    var createdBy: Int = 0
    var examStatusId: Int = 0
    var id: Int = 0
    var passmark: Double = 0.toDouble()
    var examStatus: JSONObject
    var exam: JSONObject
    var questions: JSONArray
}
