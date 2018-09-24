package com.expansion.lg.kimaru.training.activity

import android.content.Intent
import android.os.AsyncTask
import android.util.Log

import com.daimajia.androidanimations.library.Techniques
import com.expansion.lg.kimaru.training.R
import com.expansion.lg.kimaru.training.database.DatabaseHelper
import com.expansion.lg.kimaru.training.network.JsonParser
import com.expansion.lg.kimaru.training.network.TrainingDataSync
import com.expansion.lg.kimaru.training.utils.Constants
import com.expansion.lg.kimaru.training.utils.SessionManagement
import com.gadiness.kimarudg.awesome.splash.lib.activity.AwesomeSplash
import com.gadiness.kimarudg.awesome.splash.lib.cnst.Flags
import com.gadiness.kimarudg.awesome.splash.lib.model.ConfigSplash


import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

import java.util.HashMap

/**
 * Created by kimaru on 5/8/18 using Android Studio.
 * Maintainer: David Kimaru
 *
 * @github https://github.com/kimarudg
 * @email kimarudg@gmail.com
 * @phone +254722384549
 * @web gakuu.co.ke
 */

class TremapSplash : AwesomeSplash() {
    internal var progress = HashMap<String, String>()
    internal var currentStatus = ""

    override fun initSplash(configSplash: ConfigSplash) {
        currentStatus = "Getting ready for sail"
        changeSubTitleText()

        startApp()

        configSplash.backgroundColor = R.color.error_stroke_color
        configSplash.animCircularRevealDuration = 2000
        configSplash.revealFlagX = Flags.REVEAL_RIGHT  //or Flags.REVEAL_LEFT
        configSplash.revealFlagY = Flags.REVEAL_BOTTOM

        //Customize Path
        configSplash.pathSplash = Constants.LG_LOGO //set path String
        configSplash.originalHeight = 1200 //in relation to your svg (path) resource
        configSplash.originalWidth = 1200 //in relation to your svg (path) resource
        configSplash.animPathStrokeDrawingDuration = 3000
        configSplash.pathSplashStrokeSize = 3 //I advise value be <5
        configSplash.pathSplashStrokeColor = R.color.primary_material_dark //any color you want form colors.xml
        configSplash.animPathFillingDuration = 3000
        configSplash.pathSplashFillColor = R.color.primary_material_dark //path object filling color

        //
        //        //Customize Title
        configSplash.titleSplash = baseContext.getString(R.string.app_name)
        configSplash.titleTextColor = R.color.white
        configSplash.titleTextSize = 30f //float value
        configSplash.animTitleDuration = 3000
        configSplash.animTitleTechnique = Techniques.FlipInX
        configSplash.titleFont = "fonts/Cool_Crayon.ttf" //provide string to your font located in assets/fonts/


        configSplash.subTitleSplash = baseContext.getString(R.string.app_name)
        configSplash.subTitleTextColor = R.color.white
        configSplash.subTitleTextSize = 30f //float value
        configSplash.animSubTitleDuration = 3000
        configSplash.animSubTitleTechnique = Techniques.Landing
        configSplash.subTitleFont = "fonts/Cool_Crayon.ttf" //provide string to your font located in assets/fonts/
    }

    override fun animationsFinished() {
        val i = Intent(this@TremapSplash, MainActivity::class.java)
        startActivity(i)
        finish()
    }

    private fun startApp() {
        currentStatus = "Placing the satellites"
        changeSubTitleText()
        Thread(Runnable {
            try {
                TrainingDataSync(applicationContext).syncSessionTopics()
                TrainingDataSync(applicationContext).getTraineeStatusFromCloud()
                PrefetchData().execute()
                Log.d("Tremap", "GETTING Training data from the Cloud")
            } catch (e: Exception) {
                Log.d("Tremap", "ERROR GETTING TRAINING DATA")
                Log.d("Tremap", e.message)
            }
        }).start()
        try {
            Thread.sleep(5000)
        } catch (e: Exception) {
        }

    }

    private inner class PrefetchData : AsyncTask<Void, Void, Void>() {
        override fun onPreExecute() {
            super.onPreExecute()
            currentStatus = "Fetching upstream data"
            changeSubTitleText()
        }

        override fun doInBackground(vararg arg0: Void): Void? {

            val jsonParser = JsonParser()
            val sessionManagement = SessionManagement(baseContext)

            val json = jsonParser
                    .getJSONFromUrl(sessionManagement.apiUrl + sessionManagement.usersEndpoint)

            Log.v("Tremap: ", ">> " + sessionManagement.apiUrl + sessionManagement.usersEndpoint)
            Log.v("Tremap: ", "> " + json!!)

            if (json != null) {
                try {
                    val reader = JSONObject(json)
                    val databaseHelper = DatabaseHelper(baseContext)
                    val recs = reader.getJSONArray("users")
                    for (x in 0 until recs.length()) {
                        // pass it to dbHelper for save
                        databaseHelper.usersFromJson(recs.getJSONObject(x))
                    }
                    Log.d("Tremap", " Data >>>> $json")
                } catch (e: JSONException) {
                    // TODO Auto-generated catch block
                    e.printStackTrace()
                }

            }

            return null
        }

        override fun onPostExecute(result: Void) {
            super.onPostExecute(result)
        }
    }


    private inner class getTrainingTopics : AsyncTask<Void, HashMap<*, *>, Void>() {
        override fun onPreExecute() {
            super.onPreExecute()
            currentStatus = "Fetching upstream topics"
            changeSubTitleText()
        }

        override fun doInBackground(vararg args0: Void): Void? {
            val totalRecords: Int?
            var processedRecords: Int?
            val jsonParser = JsonParser()
            val sessionManagement = SessionManagement(baseContext)
            val url = sessionManagement
                    .apiUrl + sessionManagement.sessionTopicsEndpoint
            val json = jsonParser.getJSONFromUrl(url)
            Log.d("Tremap", json)
            if (json != null) {
                try {
                    val sessionTopics = JSONObject(json).getJSONArray(sessionManagement.sessionTopicsJsonRoot)
                    val databaseHelper = DatabaseHelper(baseContext)
                    totalRecords = sessionTopics.length()
                    for (x in 0 until sessionTopics.length()) {
                        databaseHelper.sessionTopicFromJson(sessionTopics.getJSONObject(x))
                        processedRecords = x
                        progress = HashMap()
                        progress["total"] = totalRecords.toString()
                        progress["processed"] = processedRecords!!.toString()
                        progress["type"] = "session topics"
                        publishProgress(progress)
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

    private inner class getTraineeStatusFromUpstream : AsyncTask<Void, HashMap<*, *>, Void>() {
        override fun onPreExecute() {
            super.onPreExecute()
            currentStatus = "Fetching upstream training status"
            changeSubTitleText()
        }

        override fun doInBackground(vararg voids: Void): Void? {
            val totalRecords: Int?
            var processedRecords: Int?
            val jsonParser = JsonParser()
            val databaseHelper = DatabaseHelper(baseContext)
            val session = SessionManagement(baseContext)
            val url = session.apiUrl + session.traineeStatusEndpoint
            Log.d("Tremap", "____________________---URL-------------____________")
            Log.d("Tremap", url)
            Log.d("Tremap", "____________________----------------____________")

            val json = jsonParser.getJSONFromUrl(url)
            if (json != null) {
                try {
                    val statuses = JSONObject(json).getJSONArray(session.traineeStatusJSONRoot)
                    totalRecords = statuses.length()
                    for (x in 0 until statuses.length()) {
                        databaseHelper.traineeStatusFromJson(statuses.getJSONObject(x))
                        processedRecords = x
                        progress = HashMap()
                        progress["total"] = totalRecords.toString()
                        progress["processed"] = processedRecords!!.toString()
                        progress["type"] = "session topics"
                        publishProgress(progress)
                    }
                } catch (e: Exception) {
                    Log.d("Tremap", "____________________----------------____________")
                    Log.d("Tremap", e.message)
                    Log.d("Tremap", "____________________----------------____________")
                }

            }
            return null
        }

        override fun onProgressUpdate(vararg updates: HashMap<*, *>) {
            super.onProgressUpdate(*updates)
            val progress = updates[0]
            if (progress.containsKey("action")) {
                //                progressDialog.setProgress(0);
                //                progressDialog.setMax(100);
                //                progressDialog.setMessage(progress.get("message"));
                //                progressDialog.setTitle(progress.get("title"));
            } else {
                //                progressDialog.setProgress(Integer.valueOf(progress.get("processed")));
                //                progressDialog.setMax(Integer.valueOf(progress.get("total")));
                //                progressDialog.setMessage("Syncing "+ progress.get("type") +"\n Please wait ... ");
                //                progressDialog.setTitle("Syncing "+ progress.get("type"));
            }

        }
    }

    override fun animationIsStarted() {
        // add your code that will be running in the background while the animation is running.
        //The code is executed in a different thread
    }

    override fun canEndAnimation(): Boolean {
        // DO you want us to end the animation or we continue?
        //if yes, return true, else, return false
        return true
    }

    override fun changeSubTitleText(): String {
        return currentStatus
    }

}
