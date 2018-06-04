package com.expansion.lg.kimaru.training.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.daimajia.androidanimations.library.Techniques;
import com.expansion.lg.kimaru.training.R;
import com.expansion.lg.kimaru.training.database.DatabaseHelper;
import com.expansion.lg.kimaru.training.network.JsonParser;
import com.expansion.lg.kimaru.training.network.TrainingDataSync;
import com.expansion.lg.kimaru.training.utils.Constants;
import com.expansion.lg.kimaru.training.utils.SessionManagement;
import com.gadiness.kimarudg.awesome.splash.lib.activity.AwesomeSplash;
import com.gadiness.kimarudg.awesome.splash.lib.cnst.Flags;
import com.gadiness.kimarudg.awesome.splash.lib.model.ConfigSplash;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by kimaru on 5/8/18 using Android Studio.
 * Maintainer: David Kimaru
 *
 * @github https://github.com/kimarudg
 * @email kimarudg@gmail.com
 * @phone +254722384549
 * @web gakuu.co.ke
 */

public class TremapSplash extends AwesomeSplash {
    HashMap<String, String> progress = new HashMap<String, String>();
    String currentStatus ="";

    @Override
    public void initSplash(ConfigSplash configSplash){
        currentStatus = "Getting ready for sail";
        changeSubTitleText();

        startApp();

        configSplash.setBackgroundColor(R.color.error_stroke_color);
        configSplash.setAnimCircularRevealDuration(2000);
        configSplash.setRevealFlagX(Flags.REVEAL_RIGHT);  //or Flags.REVEAL_LEFT
        configSplash.setRevealFlagY(Flags.REVEAL_BOTTOM);

        //Customize Path
        configSplash.setPathSplash(Constants.LG_LOGO); //set path String
        configSplash.setOriginalHeight(1200); //in relation to your svg (path) resource
        configSplash.setOriginalWidth(1200); //in relation to your svg (path) resource
        configSplash.setAnimPathStrokeDrawingDuration(3000);
        configSplash.setPathSplashStrokeSize(3); //I advise value be <5
        configSplash.setPathSplashStrokeColor(R.color.primary_material_dark); //any color you want form colors.xml
        configSplash.setAnimPathFillingDuration(3000);
        configSplash.setPathSplashFillColor(R.color.primary_material_dark); //path object filling color

//
//        //Customize Title
        configSplash.setTitleSplash(getBaseContext().getString(R.string.app_name));
        configSplash.setTitleTextColor(R.color.white);
        configSplash.setTitleTextSize(30f); //float value
        configSplash.setAnimTitleDuration(3000);
        configSplash.setAnimTitleTechnique(Techniques.FlipInX);
        configSplash.setTitleFont("fonts/Cool_Crayon.ttf"); //provide string to your font located in assets/fonts/


        configSplash.setSubTitleSplash(getBaseContext().getString(R.string.app_name));
        configSplash.setSubTitleTextColor(R.color.white);
        configSplash.setSubTitleTextSize(30f); //float value
        configSplash.setAnimSubTitleDuration(3000);
        configSplash.setAnimSubTitleTechnique(Techniques.Landing);
        configSplash.setSubTitleFont("fonts/Cool_Crayon.ttf"); //provide string to your font located in assets/fonts/
    }

    @Override
    public void animationsFinished(){
        Intent i = new Intent(TremapSplash.this, MainActivity.class);
        startActivity(i);
        finish();
    }

    private void startApp(){
        currentStatus = "Placing the satellites";
        changeSubTitleText();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    new TrainingDataSync(getApplicationContext()).syncSessionTopics();
                    new TrainingDataSync(getApplicationContext()).getTraineeStatusFromCloud();
                    new PrefetchData().execute();
                    Log.d("Tremap", "GETTING Training data from the Cloud");
                } catch (Exception e) {
                    Log.d("Tremap", "ERROR GETTING TRAINING DATA");
                    Log.d("Tremap", e.getMessage());
                }
            }
        }).start();
        try {
            Thread.sleep(5000);
        }catch (Exception e){}
    }

    private class PrefetchData extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            currentStatus = "Fetching upstream data";
            changeSubTitleText();
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            JsonParser jsonParser = new JsonParser();
            SessionManagement sessionManagement = new SessionManagement(getBaseContext());

            String json = jsonParser
                    .getJSONFromUrl(sessionManagement.getApiUrl() +sessionManagement.getUsersEndpoint());

            Log.v("Tremap: ", ">> "+ sessionManagement.getApiUrl() +sessionManagement.getUsersEndpoint());
            Log.v("Tremap: ", "> " + json);

            if (json != null) {
                try {
                    JSONObject reader= new JSONObject(json);
                    DatabaseHelper databaseHelper = new DatabaseHelper(getBaseContext());
                    JSONArray recs = reader.getJSONArray("users");
                    for (int x = 0; x < recs.length(); x++){
                        // pass it to dbHelper for save
                        databaseHelper.usersFromJson(recs.getJSONObject(x));
                    }
                    Log.d("Tremap", " Data >>>> " + json);
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
        }
    }



    private class getTrainingTopics extends AsyncTask<Void, HashMap, Void>{
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            currentStatus = "Fetching upstream topics";
            changeSubTitleText();
        }
        @Override
        protected Void doInBackground(Void... args0){
            Integer totalRecords, processedRecords;
            JsonParser jsonParser = new JsonParser();
            SessionManagement sessionManagement = new SessionManagement(getBaseContext());
            String url = sessionManagement
                    .getApiUrl()+sessionManagement.getSessionTopicsEndpoint();
            String json = jsonParser.getJSONFromUrl(url);
            Log.d("Tremap", json);
            if (json != null){
                try{
                    JSONArray sessionTopics = new JSONObject(json).getJSONArray(sessionManagement.getSessionTopicsJsonRoot());
                    DatabaseHelper databaseHelper = new DatabaseHelper(getBaseContext());
                    totalRecords = sessionTopics.length();
                    for (int x = 0; x < sessionTopics.length(); x++){
                        databaseHelper.sessionTopicFromJson(sessionTopics.getJSONObject(x));
                        processedRecords = x;
                        progress = new HashMap<String, String>();
                        progress.put("total", totalRecords.toString());
                        progress.put("processed", processedRecords.toString());
                        progress.put("type", "session topics");
                        publishProgress(progress);
                    }
                }catch (JSONException e){
                    Log.d("Tremap", "-----------ERROR----------------");
                    Log.d("Tremap", e.getMessage());
                }

            }else {
                Log.d("Tremap", "--------NULL JSON-------------------");
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
        }
    }

    private class getTraineeStatusFromUpstream extends AsyncTask<Void, HashMap, Void>{
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            currentStatus = "Fetching upstream training status";
            changeSubTitleText();
        }

        @Override
        protected Void doInBackground(Void... voids){
            Integer totalRecords, processedRecords;
            JsonParser jsonParser = new JsonParser();
            DatabaseHelper databaseHelper = new DatabaseHelper(getBaseContext());
            SessionManagement session =  new SessionManagement(getBaseContext());
            String url = session.getApiUrl()+session.getTraineeStatusEndpoint();
            Log.d("Tremap", "____________________---URL-------------____________");
            Log.d("Tremap", url);
            Log.d("Tremap", "____________________----------------____________");

            String json = jsonParser.getJSONFromUrl(url);
            if (json != null){
                try{
                    JSONArray statuses = new JSONObject(json).getJSONArray(session.getTraineeStatusJSONRoot());
                    totalRecords = statuses.length();
                    for (int x=0; x < statuses.length(); x++){
                        databaseHelper.traineeStatusFromJson(statuses.getJSONObject(x));
                        processedRecords = x;
                        progress = new HashMap<String, String>();
                        progress.put("total", totalRecords.toString());
                        progress.put("processed", processedRecords.toString());
                        progress.put("type", "session topics");
                        publishProgress(progress);
                    }
                }catch (Exception e){
                    Log.d("Tremap", "____________________----------------____________");
                    Log.d("Tremap", e.getMessage());
                    Log.d("Tremap", "____________________----------------____________");
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(HashMap... updates){
            super.onProgressUpdate(updates);
            HashMap<String, String> progress = updates[0];
            if (progress.containsKey("action")){
//                progressDialog.setProgress(0);
//                progressDialog.setMax(100);
//                progressDialog.setMessage(progress.get("message"));
//                progressDialog.setTitle(progress.get("title"));
            }else{
//                progressDialog.setProgress(Integer.valueOf(progress.get("processed")));
//                progressDialog.setMax(Integer.valueOf(progress.get("total")));
//                progressDialog.setMessage("Syncing "+ progress.get("type") +"\n Please wait ... ");
//                progressDialog.setTitle("Syncing "+ progress.get("type"));
            }

        }
    }
    @Override
    public void animationIsStarted() {
        // add your code that will be running in the background while the animation is running.
        //The code is executed in a different thread
    }

    @Override
    public boolean canEndAnimation() {
        // DO you want us to end the animation or we continue?
        //if yes, return true, else, return false
        return true;
    }

    @Override
    public String changeSubTitleText(){
        return currentStatus;
    }

}
