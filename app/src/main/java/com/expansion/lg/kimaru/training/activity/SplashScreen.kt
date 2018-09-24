package com.expansion.lg.kimaru.training.activity

import android.content.Intent
import android.os.AsyncTask
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast

import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.expansion.lg.kimaru.training.R
import com.expansion.lg.kimaru.training.database.DatabaseHelper
import com.expansion.lg.kimaru.training.network.JsonParser
import com.expansion.lg.kimaru.training.network.TrainingDataSync
import com.expansion.lg.kimaru.training.objs.User
import com.expansion.lg.kimaru.training.receivers.ConnectivityReceiver
import com.expansion.lg.kimaru.training.receivers.TremapApp
import com.expansion.lg.kimaru.training.utils.CircleTransform
import com.expansion.lg.kimaru.training.utils.SessionManagement

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

import butterknife.ButterKnife

class SplashScreen : AppCompatActivity(), ConnectivityReceiver.ConnectivityReceiverListener {
    internal var now_playing: String? = null
    internal var earned: String? = null
    internal var imageView: ImageView
    internal var aniRotateClk: Animation
    internal var canProceed = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        imageView = findViewById<View>(R.id.imageView) as ImageView
        aniRotateClk = AnimationUtils.loadAnimation(applicationContext, R.anim.rotate_clockwise)
        aniRotateClk.start()
        imageView.startAnimation(aniRotateClk)
        if (savedInstanceState == null) {
            startApp()
        }


    }

    private fun startApp() {
        //load the photo in the placeholder
        Glide.with(this).load(getImage("lg_bg"))
                .crossFade()
                .thumbnail(0.5f)
                .bitmapTransform(CircleTransform(this))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView)
        //Init the animation
        aniRotateClk.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {
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
                    canProceed = true
                } catch (e: Exception) {
                }


            }

            override fun onAnimationEnd(animation: Animation) {
                if (canProceed) {
                    val i = Intent(this@SplashScreen, MainActivity::class.java)
                    i.putExtra("now_playing", "Kimaru")
                    i.putExtra("earned", "millions")
                    startActivity(i)
                    finish()
                } else {
                    animateLogo()
                }
            }

            override fun onAnimationRepeat(animation: Animation) {

            }
        })
        animateLogo()

    }

    private fun checkConnection() {
        val isConnected = ConnectivityReceiver.isConnected
    }

    private inner class PrefetchData : AsyncTask<Void, Void, Void>() {
        override fun onPreExecute() {
            super.onPreExecute()
        }

        override fun doInBackground(vararg arg0: Void): Void? {
            /*
			 * Will make http call here This call will download required data
			 * before launching the app
			 * the downloaded data include:
			 * 1. Downloading login details and storing them in the App
			 * 2. Download initial training data {training, classes and trainees}
			 * 2. Downloading images
			 * 3. Parsing the xml / json
			 * 4. Sending device information to server
			 * 5. etc.,
			 */
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
            canProceed = true
        }
    }

    override fun onResume() {
        super.onResume()
        startApp()
        TremapApp.getmInstance().setConnectivityListener(this)
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {

    }

    fun getImage(imageName: String): Int {
        return resources.getIdentifier(imageName, "drawable", packageName)
    }

    fun animateLogo() {
        imageView.startAnimation(aniRotateClk)
    }
}
