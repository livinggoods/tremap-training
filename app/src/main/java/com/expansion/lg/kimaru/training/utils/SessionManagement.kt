package com.expansion.lg.kimaru.training.utils

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import com.expansion.lg.kimaru.training.utils.SessionManagement.Companion.API_PREFIX
import com.expansion.lg.kimaru.training.utils.SessionManagement.Companion.API_SUFFIX
import com.expansion.lg.kimaru.training.utils.SessionManagement.Companion.API_VERSION
import com.expansion.lg.kimaru.training.utils.SessionManagement.Companion.CLOUD_URL
import com.expansion.lg.kimaru.training.utils.SessionManagement.Companion.SESSION_ATTENDANCE_ENDPOINT
import com.expansion.lg.kimaru.training.utils.SessionManagement.Companion.SESSION_ATTENDANCE_JSON_ROOT
import com.expansion.lg.kimaru.training.utils.SessionManagement.Companion.SESSION_TOPIC_ENDPOINT
import com.expansion.lg.kimaru.training.utils.SessionManagement.Companion.SESSION_TOPIC_JSON_ROOT
import com.expansion.lg.kimaru.training.utils.SessionManagement.Companion.TRAINING_DETAILS_ENDPOINT
import com.expansion.lg.kimaru.training.utils.SessionManagement.Companion.TRAINING_DETAILS_JSON_ROOT
import com.expansion.lg.kimaru.training.utils.SessionManagement.Companion.TRAINING_ENDPOINT
import com.expansion.lg.kimaru.training.utils.SessionManagement.Companion.TRAINING_EXAMS_JSON_ROOT
import com.expansion.lg.kimaru.training.utils.SessionManagement.Companion.TRAINING_JSON_ROOT
import com.expansion.lg.kimaru.training.utils.SessionManagement.Companion.TRAINING_TRAINEE_ENDPOINT
import com.expansion.lg.kimaru.training.utils.SessionManagement.Companion.UPLOAD_SESSION_ATTENDANCE_ENDPOINT
import com.expansion.lg.kimaru.training.utils.SessionManagement.Companion.USERS_ENDPOINT

import java.util.HashMap

/**
 * Created by kimaru on 2/27/18.
 */

class SessionManagement(private val context: Context) {
    private val pref: SharedPreferences
    private val editor: Editor
    private val PRIVATE_MODE = 0


    val apiUrl: String
        get() {
            val url = this.cloudUrl
            val urlBuilder = StringBuilder(url)
            if (this.apiPrefix != null) {
                urlBuilder.append("/")
                        .append(this.apiPrefix)
            }
            urlBuilder.append("/").append(this.apiVersion)

            if (this.apiSuffix != null) {
                urlBuilder.append("/")
                        .append(this.apiSuffix)
            }
            urlBuilder.append("/")
            return urlBuilder.toString()
        }
    val cloudUrl: String
        get() = pref.getString(CLOUD_URL, "https://expansion.lg-apps.com")
    val apiPrefix: String
        get() = pref.getString(API_PREFIX, "api")
    val apiVersion: String
        get() = pref.getString(API_VERSION, "v1")
    val apiSuffix: String?
        get() = pref.getString(API_SUFFIX, null)
    val trainingEndpoint: String
        get() = pref.getString(TRAINING_ENDPOINT, "sync/trainings")
    val trainingDetailsEndpoint: String
        get() = pref.getString(TRAINING_DETAILS_ENDPOINT, "get/training")
    val sessionTopicsEndpoint: String
        get() = pref.getString(SESSION_TOPIC_ENDPOINT, "get/session-topics")
    val sessionTopicsJsonRoot: String
        get() = pref.getString(SESSION_TOPIC_JSON_ROOT, "topics")
    val traineeStatusJSONRoot: String
        get() = pref.getString(TRAINEE_STATUS_JSON_ROOT, "training_status")
    val trainingDetailsJsonRoot: String
        get() = pref.getString(TRAINING_DETAILS_JSON_ROOT, "training")
    val trainingJSONRoot: String
        get() = pref.getString(TRAINING_JSON_ROOT, "trainings")
    val usersEndpoint: String
        get() = pref.getString(USERS_ENDPOINT, "users/json")
    val trainingTraineesEndpoint: String
        get() = pref.getString(TRAINING_TRAINEE_ENDPOINT, "sync/trainees")
    val traineeStatusEndpoint: String
        get() = pref.getString(TRAINEE_STATUS_ENDPOINT, "sync/trainee-status")
    val sessionAttendanceEndpoint: String
        get() = pref.getString(SESSION_ATTENDANCE_ENDPOINT, "get/training/session-attendances")
    val uploadSessionAttendanceEndpoint: String
        get() = pref.getString(UPLOAD_SESSION_ATTENDANCE_ENDPOINT, "sync/training/session-attendances")
    val sessionAttendanceJSONRoot: String
        get() = pref.getString(SESSION_ATTENDANCE_JSON_ROOT, "attendance")
    val trainingExamsJSONRoot: String
        get() = pref.getString(TRAINING_EXAMS_JSON_ROOT, "exams")
    val trainingExamsEndpoint: String
        get() = pref.getString(TRAINING_EXAMS_ENDPOINT, "training/%s/exams")
    val trainingExamResultsEndpoint: String
        get() = pref.getString(TRAINING_EXAMS_RESULTS_ENDPOINT, "exam/%s/results")
    val trainingExamResultsJSONRoot: String
        get() = pref.getString(TRAINING_EXAMS_RESULTS_JSON_ROOT, "results")

    //UserName
    //email
    //userId
    //return user
    val userDetails: HashMap<String, String>
        get() {
            val user = HashMap<String, String>()
            user[KEY_NAME] = pref.getString(KEY_NAME, null)
            user[KEY_EMAIL] = pref.getString(KEY_EMAIL, null)
            user[KEY_USERID] = pref.getInt(KEY_USERID, 0).toString()

            user[KEY_USER_COUNTRY] = pref.getString(KEY_USER_COUNTRY, "ug").toString()
            return user
        }

    /**
     * Check for Login
     * It is reference in checkLogin()
     * THis is a boolean flag
     */
    val isLoggedIn: Boolean
        get() = pref.getBoolean(IS_LOGIN, false)

    init {
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        editor = pref.edit()
    }

    fun saveCloudUrl(cloudUrl: String) {
        editor.putString(CLOUD_URL, cloudUrl)
        editor.commit()
    }

    //api prefix
    fun saveApiPrefix(apiPrefix: String) {
        editor.putString(API_PREFIX, apiPrefix)
        editor.commit()
    }

    //api version
    fun saveApiVersion(apiVersion: String) {
        editor.putString(API_VERSION, apiVersion)
        editor.commit()
    }

    //api version
    fun saveApiSuffix(apiSuffix: String) {
        editor.putString(API_SUFFIX, apiSuffix)
        editor.commit()
    }

    //training Endpoint
    fun saveTrainingEndpoint(trainingEndpoint: String) {
        editor.putString(TRAINING_ENDPOINT, trainingEndpoint)
        editor.commit()
    }

    //trainingDetails Endpoint
    fun saveTrainingDetailsEndpoint(trainingDetailsEndpoint: String) {
        editor.putString(TRAINING_DETAILS_ENDPOINT, trainingDetailsEndpoint)
        editor.commit()
    }


    //sessionTopics Endpoint
    fun saveSessionTopicsEndpoint(sessionTopicsEndpoint: String) {
        editor.putString(SESSION_TOPIC_ENDPOINT, sessionTopicsEndpoint)
        editor.commit()
    }

    fun saveSessionTopicsJsonRoot(sessionTopicsJsonRoot: String) {
        editor.putString(SESSION_TOPIC_ENDPOINT, sessionTopicsJsonRoot)
        editor.commit()
    }


    //traineeStatusJsonRoot Endpoint
    fun saveTraineeStatusJsonRoot(traineeStatusJsonRoot: String) {
        editor.putString(TRAINEE_STATUS_JSON_ROOT, traineeStatusJsonRoot)
        editor.commit()
    }

    //trainingDetailsJsonRoot Endpoint
    fun saveTrainingDetailsJsonRoot(trainingDetailsJsonRoot: String) {
        editor.putString(TRAINING_DETAILS_JSON_ROOT, trainingDetailsJsonRoot)
        editor.commit()
    }

    //training JsonRoot
    fun saveTrainingJSONRoot(trainingJSONRoot: String) {
        editor.putString(TRAINING_JSON_ROOT, trainingJSONRoot)
        editor.commit()
    }

    //Users Endpoint
    fun saveUsersEndpoint(usersEndpoint: String) {
        editor.putString(USERS_ENDPOINT, usersEndpoint)
        editor.commit()
    }


    //Users Endpoint
    fun saveTrainingTraineesEndpoint(trainingTraineesEndpoint: String) {
        editor.putString(TRAINING_TRAINEE_ENDPOINT, trainingTraineesEndpoint)
        editor.commit()
    }

    //TraineeStatus Endpoint
    fun saveTraineeStatusEndpoint(traineeStatusEndpoint: String) {
        editor.putString(TRAINEE_STATUS_ENDPOINT, traineeStatusEndpoint)
        editor.commit()
    }


    //Session Attendance Endpoint
    fun saveSessionAttendanceEndpoint(sessionAttendanceEndpoint: String) {
        editor.putString(SESSION_ATTENDANCE_ENDPOINT, sessionAttendanceEndpoint)
        editor.commit()
    }

    //Session Attendance Endpoint
    fun uploadSessionAttendanceEndpoint(sessionAttendanceEndpoint: String) {
        editor.putString(UPLOAD_SESSION_ATTENDANCE_ENDPOINT, sessionAttendanceEndpoint)
        editor.commit()
    }

    //Session Attendance JSON Root
    fun saveSessionAttendanceJSONRoot(sessionAttendanceJSONRoot: String) {
        editor.putString(SESSION_ATTENDANCE_JSON_ROOT, sessionAttendanceJSONRoot)
        editor.commit()
    }

    //Training Exams JSON Root
    fun saveTrainingExamsJSONRoot(trainingExamJSONRoot: String) {
        editor.putString(TRAINING_EXAMS_JSON_ROOT, trainingExamJSONRoot)
        editor.commit()
    }

    //Training Exams Endpoint
    fun saveTrainingExamsEndpoint(trainingExamEndpoint: String) {
        editor.putString(TRAINING_EXAMS_ENDPOINT, trainingExamEndpoint)
        editor.commit()
    }

    fun saveTrainingExamResultsEndpoint(trainingExamResultsEndpoint: String) {
        editor.putString(TRAINING_EXAMS_RESULTS_ENDPOINT, trainingExamResultsEndpoint)
        editor.commit()
    }

    fun saveTrainingExamResultsJSONRoot(trainingExamResultsJsonRoot: String) {
        editor.putString(TRAINING_EXAMS_RESULTS_JSON_ROOT, trainingExamResultsJsonRoot)
        editor.commit()
    }

    fun saveUserDetails(name: String, email: String, userId: Int?, country: String) {
        //storing login values as TRUE
        editor.putBoolean(IS_LOGIN, true)

        //store name in pref
        editor.putString(KEY_NAME, name)

        //put email in pref
        editor.putString(KEY_EMAIL, email)

        //put userid
        editor.putInt(KEY_USERID, userId!!)

        //put user country
        editor.putString(KEY_USER_COUNTRY, country)

        //commit / Save the values
        editor.commit()

    }

    companion object {

        private val PREF_NAME = "tremap_training"

        private val CLOUD_URL = "cloud_url"
        private val API_PREFIX = "api_prefix"
        private val API_VERSION = "api_version"
        private val API_SUFFIX = "api_suffix"
        private val TRAINEE_STATUS_ENDPOINT = "trainee_status"

        private val TRAINING_ENDPOINT = "training_endpoint"
        private val TRAINING_DETAILS_ENDPOINT = "training_details_endpoint"
        private val TRAINING_DETAILS_JSON_ROOT = "training_details_json_root"
        private val TRAINING_JSON_ROOT = "training_json_root"
        private val TRAINEE_STATUS_JSON_ROOT = "trainee_status_json_root"
        private val USERS_ENDPOINT = "users_endpoint"
        private val TRAINING_TRAINEE_ENDPOINT = "training_trainee_endpoint"
        private val SESSION_TOPIC_ENDPOINT = "session_topic_endpoint"
        private val SESSION_TOPIC_JSON_ROOT = "session_topic_json_root"

        private val SESSION_ATTENDANCE_ENDPOINT = "session_attendance_endpoint"
        private val UPLOAD_SESSION_ATTENDANCE_ENDPOINT = "upload_session_attendance_endpoint"
        private val SESSION_ATTENDANCE_JSON_ROOT = "session_attendance_json_root"
        private val TRAINING_EXAMS_ENDPOINT = "training_exams_endpoint"
        private val TRAINING_EXAMS_JSON_ROOT = "training_exams_json_root"

        private val TRAINING_EXAMS_RESULTS_ENDPOINT = "training_exams_results_endpoint"
        private val TRAINING_EXAMS_RESULTS_JSON_ROOT = "results"

        private val IS_LOGIN = "IsLoggedIn"
        val KEY_NAME = "name"
        val KEY_EMAIL = "email"
        val KEY_USERID = "userid"
        val KEY_USER_COUNTRY = "country"
    }


}
