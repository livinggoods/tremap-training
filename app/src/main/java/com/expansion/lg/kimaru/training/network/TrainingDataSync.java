package com.expansion.lg.kimaru.training.network;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.expansion.lg.kimaru.training.database.DatabaseHelper;
import com.expansion.lg.kimaru.training.objs.Training;
import com.expansion.lg.kimaru.training.objs.TrainingExam;
import com.expansion.lg.kimaru.training.objs.TrainingExamResult;
import com.expansion.lg.kimaru.training.utils.Constants;
import com.expansion.lg.kimaru.training.utils.SessionManagement;
import com.kimarudg.async.http.AsyncHttpClient;
import com.kimarudg.async.http.AsyncHttpPost;
import com.kimarudg.async.http.body.JSONArrayBody;
import com.kimarudg.async.http.body.JSONObjectBody;

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
    DatabaseHelper databaseHelper;
    SessionManagement session;

    public TrainingDataSync(Context context){
        this.context = context;
        this.databaseHelper = new DatabaseHelper(context);
        session = new SessionManagement(context);
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

    public void startPollUploadExamResults(final String trainingId){

        final Handler handler = new Handler(Looper.getMainLooper());
        Timer timer = new Timer();
        TimerTask getTrainingsTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        new UploadCertificationsResults().execute(trainingId);
                    }
                });
            }
        };
        timer.schedule(getTrainingsTask, 0, 60*500 * 1);
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


    public void getTraineeStatusFromCloud(){
        new getTraineeStatusFromUpstream().execute();
    }

    private class getTraineeStatusFromUpstream extends AsyncTask<Void, Void, Void>{
        @Override
        protected void onPreExecute(){super.onPreExecute();}

        @Override
        protected Void doInBackground(Void... voids){
            JsonParser jsonParser = new JsonParser();
            SessionManagement session =  new SessionManagement(context);
            String url = session.getApiUrl()+session.getTraineeStatusEndpoint();
            Log.d("Tremap", "____________________---URL-------------____________");
            Log.d("Tremap", url);
            Log.d("Tremap", "____________________----------------____________");

            String json = jsonParser.getJSONFromUrl(url);
            if (json != null){
                try{
                    JSONArray statuses = new JSONObject(json).getJSONArray(session.getTraineeStatusJSONRoot());
                    for (int x=0; x < statuses.length(); x++){
                        databaseHelper.traineeStatusFromJson(statuses.getJSONObject(x));
                    }
                }catch (Exception e){
                    Log.d("Tremap", "____________________----------------____________");
                    Log.d("Tremap", e.getMessage());
                    Log.d("Tremap", "____________________----------------____________");
                }
            }
            return null;
        }
    }


    public void getTrainingExams(String trainingId){
        new getTrainingExamsFromUpstream().execute(trainingId);
        new GetTrainingCertifications().execute(trainingId);
    }

    private class GetTrainingCertifications extends AsyncTask<String, Void, String> {
        protected String doInBackground(String ...strings) {
            JsonParser jsonParser = new JsonParser();
            String trainingId = strings[0];
            SessionManagement session =  new SessionManagement(context);
            String url = session.getApiUrl()+ String.format(session.getTrainingCertificationsEndpoint(), trainingId);
            Log.d("TREMAP", url);
            Log.d("TREMAP", url);
            Log.d("TREMAP", url);

            String json = jsonParser.getJSONFromUrl(url);
            if (json != null){
                try{
                    JSONArray exams = new JSONObject(json).getJSONArray(session.getTrainingExamsJSONRoot());
                    for (int x=0; x < exams.length(); x++){
                        databaseHelper.trainingExamFromJson(exams.getJSONObject(x));
                    }
                }catch (Exception e){
                    Log.d("TREMAPEXAMS", "ERROR TRAINING EXAMS");
                    Log.d("Tremap", e.getMessage());
                }
            }
            return null;
        }
    }

    private class getTrainingExamsFromUpstream extends AsyncTask<String, Void, String>{
        @Override
        protected void onPreExecute(){super.onPreExecute();}

        @Override
        protected String doInBackground(String... strings){
            JsonParser jsonParser = new JsonParser();
            String trainingId = strings[0];
            SessionManagement session =  new SessionManagement(context);
            String url = session.getApiUrl()+ String.format(session.getTrainingExamsEndpoint(), trainingId);
            Log.d("TREMAP", url);
            Log.d("TREMAP", url);
            Log.d("TREMAP", url);

            String json = jsonParser.getJSONFromUrl(url);
            if (json != null){
                try{
                    JSONArray exams = new JSONObject(json).getJSONArray(session.getTrainingExamsJSONRoot());
                    for (int x=0; x < exams.length(); x++){
                        databaseHelper.trainingExamFromJson(exams.getJSONObject(x));
                    }
                }catch (Exception e){
                    Log.d("TREMAPEXAMS", "ERROR TRAINING EXAMS");
                    Log.d("Tremap", e.getMessage());
                }
            }
            return null;
        }
    }


    public void getTrainingExamResults(String examId){
        new getTrainingExamResultsFromUpstream().execute(examId);
    }

    private class getTrainingExamResultsFromUpstream extends AsyncTask<String, Void, String>{
        @Override
        protected void onPreExecute(){super.onPreExecute();}

        @Override
        protected String doInBackground(String... strings){
            JsonParser jsonParser = new JsonParser();
            String examId = strings[0];
            SessionManagement session =  new SessionManagement(context);
            String url = session.getApiUrl()+ String.format(session.getTrainingExamResultsEndpoint(), examId);
            Log.d("Tremap", "************************************************************************");
            Log.d("Tremap", url);
            Log.d("Tremap", "************************************************************************");
            String json = jsonParser.getJSONFromUrl(url);
            if (json != null){
                try{
                    JSONArray results = new JSONObject(json).getJSONArray(session.getTrainingExamResultsJSONRoot());
                    for (int x=0; x < results.length(); x++){
                        databaseHelper.trainingExamResultFromJson(results.getJSONObject(x));
                    }
                }catch (Exception e){
                    Log.d("TREMAPEXAMS", "ERROR TRAINING EXAM RESULTS");
                    Log.d("Tremap", e.getMessage());
                }
            }
            return null;
        }
    }

    public void startUploadAttendanceTask(){
        final Handler handler = new Handler(Looper.getMainLooper());
        Timer timer = new Timer();
        TimerTask getTrainingsTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run()     {
                        new uploadSessionAttendanceToUpstream().execute("all");
                    }
                });
            }
        };
        timer.schedule(getTrainingsTask, 0, 600000 * 60); // every one hour
    }
    //SessionAttendance
    public void uploadSessionAttendance(String trainingId){
        JSONObject json =databaseHelper.trainingSessionAttendanceToUploadJson(trainingId);
        try{
            String syncResults = syncClient(json,
                    session.getUploadSessionAttendanceEndpoint()+"/"+trainingId);
        }catch (Exception e){}
    }

    private class UploadCertificationsResults extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {

            String trainingId = strings[0];
            DatabaseHelper db = new DatabaseHelper(context);
            String endpoint = session.getApiUrl() + "training/exam/result/save";

            List<TrainingExam> exams = db.getTrainingExamsByTrainingId(trainingId);

            try {

                JSONArray params = new JSONArray();
                for (TrainingExam exam: exams) {
                    if (exam.getCertificationTypeId() > 0) {
                        List<TrainingExamResult> results = db.getTrainingExamResultsByExam(exam.getId().toString());
                        for (TrainingExamResult result: results) {
                            JSONObject param = new JSONObject();


                            param.put("training_exam_id", result.getTrainingExamId());
                            param.put("trainee_id", result.getTraineeId());
                            param.put("question_id", result.getQuestionId());
                            param.put("question_score", result.getQuestionScore());
                            param.put("country", result.getCountry());
                            param.put("answer", result.getAnswer());
                            param.put("choice_id", result.getChoiceId());
                            param.put("id", result.getId());

                            params.put(param);
                        }

                    }
                }

                AsyncHttpPost p = new AsyncHttpPost(endpoint);
                Log.e("Tremap", params.toString());
                p.setBody(new JSONArrayBody(params));
                JSONObject ret = AsyncHttpClient.getDefaultInstance().executeJSONObject(p, null).get();
                Log.e("RESULTS : Sync", ret.toString());

            } catch (Exception ex) {

                ex.printStackTrace();
            }

            return null;
        }
    }

    private class uploadSessionAttendanceToUpstream extends AsyncTask<String, Void, String>{
        @Override
        protected void onPreExecute(){super.onPreExecute();}

        @Override
        protected String doInBackground(String... strings){
            String trainingId = strings[0];
            String syncResults;
            JSONObject json;
            try{
                if (strings[0].equalsIgnoreCase("all")){
                    json = databaseHelper.allTrainingSessionAttendanceToJson();
                    syncResults = syncClient(json, session.getUploadSessionAttendanceEndpoint());
                }else{
                    json =databaseHelper.trainingSessionAttendanceToUploadJson(trainingId);
                    syncResults = syncClient(json,
                            session.getUploadSessionAttendanceEndpoint()+"/"+trainingId);
                }
            }catch (Exception e){
                syncResults = null;
            }
            return syncResults;
        }
    }

    // Callback for the API
    private String syncClient(JSONObject json, String apiEndpoint) throws Exception {
        SessionManagement session = new SessionManagement(context);
        String url = session.getApiUrl()+apiEndpoint;
        Log.d("RESULTS : Sync", "+++++++++++++++++++++++++++");
        Log.d("RESULTS : Sync", url);
        Log.d("RESULTS : Sync", "+++++++++++++++++++++++++++");
        AsyncHttpPost p = new AsyncHttpPost(url);
        p.setBody(new JSONObjectBody(json));
        JSONObject ret = AsyncHttpClient.getDefaultInstance().executeJSONObject(p, null).get();
//        return ret.getString(expectedJsonRoot);
        Log.d("RESULTS : Sync", ret.toString());
        return ret.toString();
    }
}