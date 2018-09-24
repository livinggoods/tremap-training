package com.expansion.lg.kimaru.training.objs

import org.json.JSONArray
import org.json.JSONObject

import java.util.Date

/**
 * Created by kimaru on 6/5/18 using Android Studio.
 * Maintainer: David Kimaru
 *
 * @github https://github.com/kimarudg
 * @email kimarudg@gmail.com
 * @phone +254722384549
 * @web gakuu.co.ke
 */

class TrainingExam {
    var id: Int? = null
    var examId: Int? = null
    var createdBy: Int? = null
    var passmark: Int? = null
    var statusId: Int? = null
        internal set
    var isArchived: Boolean = false
    var dateAdministered: String
    var dateCreated: String
    var trainingId: String
    var country: String
    var title: String
    var examCode = ""
    var color = -1
    internal var cloudExamJson: JSONObject

    var status: JSONObject
        internal set
    internal var questions: JSONArray

    constructor() {}

    constructor(id: Int?, examId: Int?, createdBy: Int?, passmark: Int?,
                archived: Boolean, dateAdministered: String, dateCreated: String,
                trainingId: String, country: String, title: String,
                cloudExamJson: JSONObject) {
        this.id = id
        this.examId = examId
        this.createdBy = createdBy
        this.passmark = passmark
        this.isArchived = archived
        this.dateAdministered = dateAdministered
        this.dateCreated = dateCreated
        this.trainingId = trainingId
        this.country = country
        this.title = title
        this.cloudExamJson = cloudExamJson
        try {
            this.examCode = cloudExamJson.getString("code")
            this.questions = cloudExamJson.getJSONArray("questions")
            this.status = cloudExamJson.getJSONObject("exam_status")
            this.statusId = cloudExamJson.getInt("exam_status_id")
        } catch (e: Exception) {
        }

    }

    fun getCloudExamJson(): JSONObject {
        return cloudExamJson
    }

    fun getQuestions(): JSONArray? {
        val questions: JSONArray? = null
        try {
            return cloudExamJson.getJSONArray("questions")
        } catch (e: Exception) {
            return questions
        }

    }

    fun setCloudExamJson(cloudExamJson: JSONObject) {
        this.cloudExamJson = cloudExamJson
        try {
            this.examCode = cloudExamJson.getString("code")
            this.questions = cloudExamJson.getJSONArray("questions")
            this.status = cloudExamJson.getJSONObject("exam_status")
            this.statusId = cloudExamJson.getInt("exam_status_id")
        } catch (e: Exception) {
        }

    }
}
