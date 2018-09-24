package com.expansion.lg.kimaru.training.network

import android.content.Context
import android.os.AsyncTask
import android.os.Handler
import android.os.Looper
import android.util.Log

import com.expansion.lg.kimaru.training.database.DatabaseHelper
import com.expansion.lg.kimaru.training.objs.Training
import com.expansion.lg.kimaru.training.utils.Constants
import com.expansion.lg.kimaru.training.utils.SessionManagement
import com.kimarudg.async.http.AsyncHttpClient
import com.kimarudg.async.http.AsyncHttpPost
import com.kimarudg.async.http.body.JSONObjectBody

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

import java.util.ArrayList
import java.util.Timer
import java.util.TimerTask

/**
 * Created by kimaru on 2/27/18.
 */

class TrainingDataSync(internal var context: Context) {
    internal var databaseHelper: DatabaseHelper
    internal var session: SessionManagement

    init {
        this.databaseHelper = DatabaseHelper(context)
        session = SessionManagement(context)
    }

    fun pollNewTrainings() {
        syncTrainings().execute()
    }


    fun startPollNewTrainingsTask() {

        val handler = Handler(Looper.getMainLooper())
        val timer = Timer()
        val getTrainingsTask = object : TimerTask() {
            override fun run() {
                handler.post { syncTrainings().execute() }
            }
        }
        timer.schedule(getTrainingsTask, 0, (60 * 500 * 1).toLong()) //every 30 minutes
    }


    private inner class syncTrainings : AsyncTask<Void, Void, Void>() {
        override fun onPreExecute() {
            super.onPreExecute()
        }

        override fun doInBackground(vararg arg0: Void): Void? {
            val jsonParser = JsonParser()
            val sessionManagement = SessionManagement(context)

            val json = jsonParser.getJSONFromUrl(sessionManagement.apiUrl + sessionManagement.trainingEndpoint)
            if (json != null) {
                try {
                    val reader = JSONObject(json)
                    val recs = reader.getJSONArray(sessionManagement.trainingJSONRoot)
                    for (x in 0 until recs.length()) {
                        Log.d("Tremap", "===================")
                        Log.d("Tremap", recs.getString(x))
                        Log.d("Tremap", "===================")
                        databaseHelper.trainingFromJson(recs.getJSONObject(x))
                    }
                } catch (e: JSONException) {
                    Log.d("TREMAP", "TRAINING ERROR " + e.message)
                }

            }
            return null
        }

        override fun onPostExecute(result: Void) {
            super.onPostExecute(result)
        }
    }

    /**
     * TRAINEES
     *
     */
    fun pollNewTrainingTrainees() {
        syncTrainingTrainees().execute()
    }

    fun startPollNewTraineesTask() {

        val handler = Handler(Looper.getMainLooper())
        val timer = Timer()
        val getTrainingTraineesTask = object : TimerTask() {
            override fun run() {
                handler.post { syncTrainingTrainees().execute() }
            }
        }
        timer.schedule(getTrainingTraineesTask, 0, (60 * 500 * 1).toLong()) //every 30 minutes
    }

    private inner class syncTrainingTrainees : AsyncTask<Void, Void, Void>() {
        override fun onPreExecute() {
            super.onPreExecute()
        }

        override fun doInBackground(vararg arg0: Void): Void? {
            val jsonParser = JsonParser()
            val sessionManagement = SessionManagement(context)

            val json = jsonParser.getJSONFromUrl(sessionManagement.apiUrl + sessionManagement.trainingTraineesEndpoint)
            if (json != null) {
                try {
                    val reader = JSONObject(json)
                    val recs = reader.getJSONArray(sessionManagement.trainingJSONRoot)
                    for (x in 0 until recs.length()) {
                        databaseHelper.trainingTraineeFromJson(recs.getJSONObject(x))
                    }
                } catch (e: JSONException) {
                    Log.d("TREMAP", "TRAINEE ERROR " + e.message)
                }

            }
            return null
        }

        override fun onPostExecute(result: Void) {
            super.onPostExecute(result)
        }
    }


    fun getTrainingDetailsJson(trainingId: String) {
        //new syncTrainings().execute();
        getTrainingDetails().execute(trainingId)
    }

    private inner class getTrainingDetails : AsyncTask<String, Void, String>() {
        override fun onPreExecute() {
            super.onPreExecute()
        }

        override fun doInBackground(vararg strings: String): String? {
            val trainigId = strings[0]
            val jsonParser = JsonParser()
            val sessionManagement = SessionManagement(context)
            val url = sessionManagement
                    .apiUrl + sessionManagement.trainingDetailsEndpoint + "/" + trainigId

            val json = jsonParser.getJSONFromUrl(url)
            if (json != null) {
                try {
                    val training = JSONObject(json).getJSONObject(sessionManagement.trainingDetailsJsonRoot)
                    var x: Int
                    if (!training.isNull("classes")) {
                        val trainingClassesJson = training.getJSONArray("classes")
                        x = 0
                        while (x < trainingClassesJson.length()) {
                            databaseHelper.trainingClassFromJson(trainingClassesJson.getJSONObject(x))
                            x++
                        }
                    }

                    if (!training.isNull("trainees")) {
                        val trainingTrainees = training.getJSONArray("trainees")
                        x = 0
                        while (x < trainingTrainees.length()) {
                            databaseHelper.trainingTraineeFromJson(trainingTrainees.getJSONObject(x))
                            x++
                        }
                    }

                    if (!training.isNull("training_sessions")) {
                        val trainingSessions = training.getJSONArray("training_sessions")
                        x = 0
                        while (x < trainingSessions.length()) {
                            databaseHelper.trainingSessionFromJson(trainingSessions.getJSONObject(x))
                            x++

                        }
                    }
                    if (!training.isNull("training_venue_details")) {
                        val trainingVenueDetails = training.getJSONObject("training_venue_details")
                        databaseHelper.trainingVenueFromJson(trainingVenueDetails)
                    }


                } catch (e: JSONException) {
                    Log.d("Tremap", "-----------ERROR----------------")
                    Log.d("Tremap", e.message)
                }

            } else {
                Log.d("Tremap", "--------NULL JSON-------------------")
            }
            return null
        }

        override fun onPostExecute(result: String) {}
    }


    //Session Topics
    fun syncSessionTopics() {
        getTrainingTopics().execute()
    }

    private inner class getTrainingTopics : AsyncTask<Void, Void, Void>() {
        override fun onPreExecute() {
            super.onPreExecute()
        }

        override fun doInBackground(vararg args0: Void): Void? {
            val jsonParser = JsonParser()
            val sessionManagement = SessionManagement(context)
            val url = sessionManagement
                    .apiUrl + sessionManagement.sessionTopicsEndpoint
            val json = jsonParser.getJSONFromUrl(url)
            Log.d("Tremap", json)
            if (json != null) {
                try {
                    val sessionTopics = JSONObject(json).getJSONArray(sessionManagement.sessionTopicsJsonRoot)
                    for (x in 0 until sessionTopics.length()) {
                        databaseHelper.sessionTopicFromJson(sessionTopics.getJSONObject(x))
                    }
                } catch (e: JSONException) {
                    Log.d("Tremap", "-----------ERROR----------------")
                    Log.d("Tremap", e.message)
                }

            } else {
                Log.d("Tremap", "--------NULL JSON-------------------")
            }
            return null
        }

        override fun onPostExecute(result: Void) {}
    }


    fun getSessionAttendance(trainingId: String) {
        getSessionAttendances().execute(trainingId)
    }

    private inner class getSessionAttendances : AsyncTask<String, Void, String>() {
        override fun onPreExecute() {
            super.onPreExecute()
        }

        override fun doInBackground(vararg strings: String): String? {
            val trainingId = strings[0]
            val jsonParser = JsonParser()
            val sessionManagement = SessionManagement(context)
            val url = sessionManagement.apiUrl + sessionManagement
                    .sessionAttendanceEndpoint + "/" + trainingId
            val json = jsonParser.getJSONFromUrl(url)
            if (json != null) {
                try {
                    val attendances = JSONObject(json).getJSONArray(sessionManagement.sessionAttendanceJSONRoot)
                    for (x in 0 until attendances.length()) {
                        databaseHelper.sessionAttendanceFromJSON(attendances.getJSONObject(x))
                    }
                } catch (e: Exception) {
                    Log.d("Tremap", "///////////////////////////////")
                    Log.d("Tremap", e.message)
                    Log.d("Tremap", "///////////////////////////////")
                }

            }
            return null
        }

        override fun onPostExecute(result: String) {}
    }


    fun getTraineeStatusFromCloud() {
        getTraineeStatusFromUpstream().execute()
    }

    private inner class getTraineeStatusFromUpstream : AsyncTask<Void, Void, Void>() {
        override fun onPreExecute() {
            super.onPreExecute()
        }

        override fun doInBackground(vararg voids: Void): Void? {
            val jsonParser = JsonParser()
            val session = SessionManagement(context)
            val url = session.apiUrl + session.traineeStatusEndpoint
            Log.d("Tremap", "____________________---URL-------------____________")
            Log.d("Tremap", url)
            Log.d("Tremap", "____________________----------------____________")

            val json = jsonParser.getJSONFromUrl(url)
            if (json != null) {
                try {
                    val statuses = JSONObject(json).getJSONArray(session.traineeStatusJSONRoot)
                    for (x in 0 until statuses.length()) {
                        databaseHelper.traineeStatusFromJson(statuses.getJSONObject(x))
                    }
                } catch (e: Exception) {
                    Log.d("Tremap", "____________________----------------____________")
                    Log.d("Tremap", e.message)
                    Log.d("Tremap", "____________________----------------____________")
                }

            }
            return null
        }
    }


    fun getTrainingExams(trainingId: String) {
        getTrainingExamsFromUpstream().execute(trainingId)
    }

    private inner class getTrainingExamsFromUpstream : AsyncTask<String, Void, String>() {
        override fun onPreExecute() {
            super.onPreExecute()
        }

        override fun doInBackground(vararg strings: String): String? {
            val jsonParser = JsonParser()
            val trainingId = strings[0]
            val session = SessionManagement(context)
            val url = session.apiUrl + String.format(session.trainingExamsEndpoint, trainingId)
            Log.d("TREMAP", url)
            Log.d("TREMAP", url)
            Log.d("TREMAP", url)

            val json = jsonParser.getJSONFromUrl(url)
            if (json != null) {
                try {
                    val exams = JSONObject(json).getJSONArray(session.trainingExamsJSONRoot)
                    for (x in 0 until exams.length()) {
                        databaseHelper.trainingExamFromJson(exams.getJSONObject(x))
                    }
                } catch (e: Exception) {
                    Log.d("TREMAPEXAMS", "ERROR TRAINING EXAMS")
                    Log.d("Tremap", e.message)
                }

            }
            return null
        }
    }


    fun getTrainingExamResults(examId: String) {
        getTrainingExamResultsFromUpstream().execute(examId)
    }

    private inner class getTrainingExamResultsFromUpstream : AsyncTask<String, Void, String>() {
        override fun onPreExecute() {
            super.onPreExecute()
        }

        override fun doInBackground(vararg strings: String): String? {
            val jsonParser = JsonParser()
            val examId = strings[0]
            val session = SessionManagement(context)
            val url = session.apiUrl + String.format(session.trainingExamResultsEndpoint, examId)
            Log.d("Tremap", "************************************************************************")
            Log.d("Tremap", url)
            Log.d("Tremap", "************************************************************************")
            val json = jsonParser.getJSONFromUrl(url)
            if (json != null) {
                try {
                    val results = JSONObject(json).getJSONArray(session.trainingExamResultsJSONRoot)
                    for (x in 0 until results.length()) {
                        databaseHelper.trainingExamResultFromJson(results.getJSONObject(x))
                    }
                } catch (e: Exception) {
                    Log.d("TREMAPEXAMS", "ERROR TRAINING EXAM RESULTS")
                    Log.d("Tremap", e.message)
                }

            }
            return null
        }
    }

    fun startUploadAttendanceTask() {
        val handler = Handler(Looper.getMainLooper())
        val timer = Timer()
        val getTrainingsTask = object : TimerTask() {
            override fun run() {
                handler.post { uploadSessionAttendanceToUpstream().execute("all") }
            }
        }
        timer.schedule(getTrainingsTask, 0, (600000 * 60).toLong()) // every one hour
    }

    //SessionAttendance
    fun uploadSessionAttendance(trainingId: String) {
        val json = databaseHelper.trainingSessionAttendanceToUploadJson(trainingId)
        try {
            val syncResults = syncClient(json,
                    session.uploadSessionAttendanceEndpoint + "/" + trainingId)
        } catch (e: Exception) {
        }

    }

    private inner class uploadSessionAttendanceToUpstream : AsyncTask<String, Void, String>() {
        override fun onPreExecute() {
            super.onPreExecute()
        }

        override fun doInBackground(vararg strings: String): String? {
            val trainingId = strings[0]
            var syncResults: String?
            val json: JSONObject
            try {
                if (strings[0].equals("all", ignoreCase = true)) {
                    json = databaseHelper.allTrainingSessionAttendanceToJson()
                    syncResults = syncClient(json, session.uploadSessionAttendanceEndpoint)
                } else {
                    json = databaseHelper.trainingSessionAttendanceToUploadJson(trainingId)
                    syncResults = syncClient(json,
                            session.uploadSessionAttendanceEndpoint + "/" + trainingId)
                }
            } catch (e: Exception) {
                syncResults = null
            }

            return syncResults
        }
    }

    // Callback for the API
    @Throws(Exception::class)
    private fun syncClient(json: JSONObject, apiEndpoint: String): String {
        val session = SessionManagement(context)
        val url = session.apiUrl + apiEndpoint
        Log.d("RESULTS : Sync", "+++++++++++++++++++++++++++")
        Log.d("RESULTS : Sync", url)
        Log.d("RESULTS : Sync", "+++++++++++++++++++++++++++")
        val p = AsyncHttpPost(url)
        p.body = JSONObjectBody(json)
        val ret = AsyncHttpClient.getDefaultInstance().executeJSONObject(p, null).get()
        //        return ret.getString(expectedJsonRoot);
        Log.d("RESULTS : Sync", ret.toString())
        return ret.toString()
    }
}