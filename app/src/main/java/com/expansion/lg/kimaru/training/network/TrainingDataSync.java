package com.expansion.lg.kimaru.training.network;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.expansion.lg.kimaru.training.database.DatabaseHelper;
import com.expansion.lg.kimaru.training.objs.Training;
import com.expansion.lg.kimaru.training.utils.SessionManagement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by kimaru on 2/27/18.
 */

public class TrainingDataSync {
    Context context;

    public TrainingDataSync(Context context){
        this.context = context;
    }

    public void pollNewTrainings(){
        new syncTrainings().execute();
    }


    public void startPollNewTrainingsTask(){

        final Handler handler = new Handler(Looper.getMainLooper());
        Timer timer = new Timer();
        TimerTask getTrainingsTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        new syncTrainings().execute();
                    }
                });
            }
        };
        timer.schedule(getTrainingsTask, 0, 60*500 * 1); //every 30 minutes
    }



    private class syncTrainings extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0){
            JsonParser jsonParser = new JsonParser();
            SessionManagement sessionManagement = new SessionManagement(context);

            String json = jsonParser.getJSONFromUrl(sessionManagement.getApiUrl()
                    +sessionManagement.getTrainingEndpoint());
            if(json !=null){
                try{
                    JSONObject reader= new JSONObject(json);
                    JSONArray recs = reader.getJSONArray(sessionManagement.getTrainingJSONRoot());
                    DatabaseHelper databaseHelper = new DatabaseHelper(context);
                    for (int x = 0; x < recs.length(); x++){
                        Log.d("Tremap", "===================");
                        Log.d("Tremap", recs.getString(x));
                        Log.d("Tremap", "===================");
                        databaseHelper.trainingFromJson(recs.getJSONObject(x));
                    }
                }catch(JSONException e){
                    Log.d("TREMAP", "TRAINING ERROR "+ e.getMessage());
                }
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
        }
    }
    /**
     * TRAINEES
     *
     */
    public void pollNewTrainingTrainees(){
        new syncTrainingTrainees().execute();
    }
    public void startPollNewTraineesTask(){

        final Handler handler = new Handler(Looper.getMainLooper());
        Timer timer = new Timer();
        TimerTask getTrainingTraineesTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        new syncTrainingTrainees().execute();
                    }
                });
            }
        };
        timer.schedule(getTrainingTraineesTask, 0, 60*500 * 1); //every 30 minutes
    }
    private class syncTrainingTrainees extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0){
            JsonParser jsonParser = new JsonParser();
            SessionManagement sessionManagement = new SessionManagement(context);

            String json = jsonParser.getJSONFromUrl(sessionManagement.getApiUrl()
                    +sessionManagement.getTrainingTraineesEndpoint());
            if(json !=null){
                try{
                    JSONObject reader= new JSONObject(json);
                    JSONArray recs = reader.getJSONArray(sessionManagement.getTrainingJSONRoot());
                    DatabaseHelper databaseHelper = new DatabaseHelper(context);
                    for (int x = 0; x < recs.length(); x++){
                        databaseHelper.trainingTraineeFromJson(recs.getJSONObject(x));
                    }
                }catch(JSONException e){
                    Log.d("TREMAP", "TRAINEE ERROR "+ e.getMessage());
                }
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
        }
    }


    public void getTrainingDetailsJson(String trainingId){
        //new syncTrainings().execute();
        new getTrainingDetails().execute(trainingId);
    }

    private class getTrainingDetails extends AsyncTask<String, Void, String>{
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(String... strings){
            String trainigId = strings[0];
            JsonParser jsonParser = new JsonParser();
            SessionManagement sessionManagement = new SessionManagement(context);
            String url = sessionManagement
                    .getApiUrl()+sessionManagement.getTrainingDetailsEndpoint()+"/"+trainigId;

            String json = jsonParser.getJSONFromUrl(url);
            if (json != null){
                try{
                    JSONObject training = new JSONObject(json).getJSONObject(sessionManagement.getTrainingDetailsJsonRoot());
                    DatabaseHelper databaseHelper = new DatabaseHelper(context);
                    int x;
                    if (!training.isNull("classes")) {
                        JSONArray trainingClassesJson = training.getJSONArray("classes");
                        for (x = 0; x < trainingClassesJson.length(); x++) {
                            databaseHelper.trainingClassFromJson(trainingClassesJson.getJSONObject(x));
                        }
                    }

                    if (!training.isNull("trainees")) {
                        JSONArray trainingTrainees = training.getJSONArray("trainees");
                        for (x=0; x<trainingTrainees.length(); x++){
                            databaseHelper.trainingTraineeFromJson(trainingTrainees.getJSONObject(x));
                        }
                    }

                    if (!training.isNull("training_sessions")) {
                        JSONArray trainingSessions = training.getJSONArray("training_sessions");
                        for (x = 0; x < trainingSessions.length(); x++) {
                            databaseHelper.trainingSessionFromJson(trainingSessions.getJSONObject(x));

                        }
                    }
                    if (!training.isNull("training_venue_details")) {
                        JSONObject trainingVenueDetails = training.getJSONObject("training_venue_details");
                        databaseHelper.trainingVenueFromJson(trainingVenueDetails);
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
        protected void onPostExecute(String result) {
        }
    }


    //Session Topics
    public void syncSessionTopics(){
        new getTrainingTopics().execute();
    }
    private class getTrainingTopics extends AsyncTask<Void, Void, Void>{
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }
        @Override
        protected Void doInBackground(Void... args0){
            JsonParser jsonParser = new JsonParser();
            SessionManagement sessionManagement = new SessionManagement(context);
            String url = sessionManagement
                    .getApiUrl()+sessionManagement.getSessionTopicsEndpoint();
            String json = jsonParser.getJSONFromUrl(url);
            Log.d("Tremap", json);
            if (json != null){
                try{
                    JSONArray sessionTopics = new JSONObject(json).getJSONArray(sessionManagement.getSessionTopicsJsonRoot());
                    DatabaseHelper databaseHelper = new DatabaseHelper(context);
                    for (int x = 0; x < sessionTopics.length(); x++){
                        databaseHelper.sessionTopicFromJson(sessionTopics.getJSONObject(x));
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



    public void getSessionAttendance(String trainingId){
        new getSessionAttendances().execute(trainingId);
    }

    private class getSessionAttendances extends AsyncTask<String, Void, String>{
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings){
            String trainingId = strings[0];
            JsonParser jsonParser = new JsonParser();
            DatabaseHelper databaseHelper = new DatabaseHelper(context);
            SessionManagement sessionManagement = new SessionManagement(context);
            String url = sessionManagement.getApiUrl()+sessionManagement
                    .getSessionAttendanceEndpoint() + "/"+trainingId;
            String json = jsonParser.getJSONFromUrl(url);
            if (json != null){
                try{
                    JSONArray attendances = new JSONObject(json).getJSONArray(sessionManagement.getSessionAttendanceJSONRoot());
                    for (int x = 0; x <attendances.length(); x++){
                        databaseHelper.sessionAttendanceFromJSON(attendances.getJSONObject(x));
                    }
                }catch (Exception e){
                    Log.d("Tremap","///////////////////////////////");
                    Log.d("Tremap", e.getMessage());
                    Log.d("Tremap","///////////////////////////////");
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result){}
    }
}
