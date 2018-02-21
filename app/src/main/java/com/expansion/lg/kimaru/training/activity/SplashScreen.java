package com.expansion.lg.kimaru.training.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.expansion.lg.kimaru.training.R;
import com.expansion.lg.kimaru.training.network.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

public class SplashScreen extends AppCompatActivity {
    String now_playing, earned;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new PrefetchData().execute();
    }

    private class PrefetchData extends AsyncTask<Void, Void, Void>{
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
			/*
			 * Will make http call here This call will download required data
			 * before launching the app
			 * example:
			 * 1. Downloading login details and storing them in the App
			 * 2. Download initial training data {training, classes and trainees}
			 * 2. Downloading images
			 * 3. Parsing the xml / json
			 * 4. Sending device information to server
			 * 5. etc.,
			 */
            JsonParser jsonParser = new JsonParser();
            String json = jsonParser
                    .getJSONFromUrl("https://api.androidhive.info/game/game_stats.json");

            Log.v("Response: ", "> " + json);
            Log.d("TREMAP", "HEEW");
            Log.d("TREMAP", "===================================");

            if (json != null) {
                try {
                    JSONObject jObj = new JSONObject(json)
                            .getJSONObject("game_stat");
                    now_playing = jObj.getString("now_playing");
                    earned = jObj.getString("earned");

                    Log.e("JSON", "> " + now_playing + earned);

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }

            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // After completing http call
            // will close this activity and lauch main activity
            Intent i = new Intent(SplashScreen.this, MainActivity.class);
            i.putExtra("now_playing", now_playing);
            i.putExtra("earned", earned);
            startActivity(i);

            // close this activity
            finish();
        }
    }
}
