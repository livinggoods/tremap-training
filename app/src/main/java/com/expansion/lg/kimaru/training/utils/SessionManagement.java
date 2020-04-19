package com.expansion.lg.kimaru.training.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.HashMap;

/**
 * Created by kimaru on 2/27/18.
 */

public class SessionManagement {
    private SharedPreferences pref;
    private Editor editor;
    private Context context;
    private int PRIVATE_MODE = 0;

    private static final String PREF_NAME= "tremap_training";

    private static final String CLOUD_URL = "cloud_url";
    private static final String API_PREFIX = "api_prefix";
    private static final String API_VERSION = "api_version";
    private static final String API_SUFFIX = "api_suffix";
    private static final String TRAINEE_STATUS_ENDPOINT = "trainee_status";

    private static final String TRAINING_ENDPOINT = "training_endpoint";
    private static final String TRAINING_DETAILS_ENDPOINT = "training_details_endpoint";
    private static final String TRAINING_DETAILS_JSON_ROOT = "training_details_json_root";
    private static final String TRAINING_JSON_ROOT = "training_json_root";
    private static final String TRAINEE_STATUS_JSON_ROOT = "trainee_status_json_root";
    private static final String USERS_ENDPOINT = "users_endpoint";
    private static final String TRAINING_TRAINEE_ENDPOINT = "training_trainee_endpoint";
    private static final String SESSION_TOPIC_ENDPOINT = "session_topic_endpoint";
    private static final String SESSION_TOPIC_JSON_ROOT = "session_topic_json_root";

    private static final String SESSION_ATTENDANCE_ENDPOINT = "session_attendance_endpoint";
    private static final String UPLOAD_SESSION_ATTENDANCE_ENDPOINT = "upload_session_attendance_endpoint";
    private static final String SESSION_ATTENDANCE_JSON_ROOT = "session_attendance_json_root";
    private static final String TRAINING_EXAMS_ENDPOINT = "training_exams_endpoint";
    private static final String TRAINING_EXAMS_JSON_ROOT = "training_exams_json_root";

    private static final String TRAINING_EXAMS_RESULTS_ENDPOINT = "training_exams_results_endpoint";
    private static final String TRAINING_CERTIFICATION_ENDPOINT = "training_certifications";
    private static final String TRAINING_EXAMS_RESULTS_JSON_ROOT = "results";

    private static final String IS_LOGIN = "IsLoggedIn";
    public static final String KEY_NAME = "name";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_USERID = "userid";
    public static final String KEY_USER_COUNTRY = "country";

    public static final String PREF_FILTER_BY_COUNTRY = "filter_by_country";

    public String getApiUrl(){
        String url = this.getCloudUrl();
        StringBuilder urlBuilder = new StringBuilder(url);
        if(this.getApiPrefix()!=null){
            urlBuilder.append("/")
                    .append(this.getApiPrefix());
        }
        urlBuilder.append("/").append(this.getApiVersion());

        if(this.getApiSuffix()!=null){
            urlBuilder.append("/")
                    .append(this.getApiSuffix());
        }
        urlBuilder.append("/");
        return urlBuilder.toString();
    }

    public SessionManagement(Context context){
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setUserFilterByCountry(int country) {
        editor.putInt(PREF_FILTER_BY_COUNTRY, country);
        editor.commit();
    }

    public int getUserFilterByCountry() {
        return pref.getInt(PREF_FILTER_BY_COUNTRY, 0);
    }

    public void saveCloudUrl(String cloudUrl){
        editor.putString(CLOUD_URL, cloudUrl);
        editor.commit();
    }
    public String getCloudUrl (){
        String cloudUrl = pref.getString(CLOUD_URL, "https://expansion.lg-apps.com");
        return cloudUrl;
    }

    //api prefix
    public void saveApiPrefix(String apiPrefix){
        editor.putString(API_PREFIX, apiPrefix);
        editor.commit();
    }
    public String getApiPrefix (){
        String cloudUrl = pref.getString(API_PREFIX, "api");
        return cloudUrl;
    }

    //api version
    public void saveApiVersion(String apiVersion){
        editor.putString(API_VERSION, apiVersion);
        editor.commit();
    }
    public String getApiVersion (){
        String cloudUrl = pref.getString(API_VERSION, "v1");
        return cloudUrl;
    }

    //api version
    public void saveApiSuffix(String apiSuffix){
        editor.putString(API_SUFFIX, apiSuffix);
        editor.commit();
    }
    public String getApiSuffix (){
        String cloudUrl = pref.getString(API_SUFFIX, null);
        return cloudUrl;
    }

    //training Endpoint
    public void saveTrainingEndpoint(String trainingEndpoint){
        editor.putString(TRAINING_ENDPOINT, trainingEndpoint);
        editor.commit();
    }
    public String getTrainingEndpoint (){
        String cloudUrl = pref.getString(TRAINING_ENDPOINT, "sync/trainings");
        return cloudUrl;
    }

    //trainingDetails Endpoint
    public void saveTrainingDetailsEndpoint(String trainingDetailsEndpoint){
        editor.putString(TRAINING_DETAILS_ENDPOINT, trainingDetailsEndpoint);
        editor.commit();
    }
    public String getTrainingDetailsEndpoint (){
        String cloudUrl = pref.getString(TRAINING_DETAILS_ENDPOINT, "get/training");
        return cloudUrl;
    }


    //sessionTopics Endpoint
    public void saveSessionTopicsEndpoint(String sessionTopicsEndpoint){
        editor.putString(SESSION_TOPIC_ENDPOINT, sessionTopicsEndpoint);
        editor.commit();
    }
    public String getSessionTopicsEndpoint (){
        String cloudUrl = pref.getString(SESSION_TOPIC_ENDPOINT, "get/session-topics");
        return cloudUrl;
    }

    public void saveSessionTopicsJsonRoot(String sessionTopicsJsonRoot){
        editor.putString(SESSION_TOPIC_ENDPOINT, sessionTopicsJsonRoot);
        editor.commit();
    }
    public String getSessionTopicsJsonRoot (){
        String cloudUrl = pref.getString(SESSION_TOPIC_JSON_ROOT, "topics");
        return cloudUrl;
    }


    //traineeStatusJsonRoot Endpoint
    public void saveTraineeStatusJsonRoot(String traineeStatusJsonRoot){
        editor.putString(TRAINEE_STATUS_JSON_ROOT, traineeStatusJsonRoot);
        editor.commit();
    }
    public String getTraineeStatusJSONRoot (){
        return pref.getString(TRAINEE_STATUS_JSON_ROOT, "training_status");
    }

    //trainingDetailsJsonRoot Endpoint
    public void saveTrainingDetailsJsonRoot(String trainingDetailsJsonRoot){
        editor.putString(TRAINING_DETAILS_JSON_ROOT, trainingDetailsJsonRoot);
        editor.commit();
    }
    public String getTrainingDetailsJsonRoot (){
        String cloudUrl = pref.getString(TRAINING_DETAILS_JSON_ROOT, "training");
        return cloudUrl;
    }

    //training JsonRoot
    public void saveTrainingJSONRoot(String trainingJSONRoot){
        editor.putString(TRAINING_JSON_ROOT, trainingJSONRoot);
        editor.commit();
    }
    public String getTrainingJSONRoot (){
        String cloudUrl = pref.getString(TRAINING_JSON_ROOT, "trainings");
        return cloudUrl;
    }

    //Users Endpoint
    public void saveUsersEndpoint(String usersEndpoint){
        editor.putString(USERS_ENDPOINT, usersEndpoint);
        editor.commit();
    }
    public String getUsersEndpoint (){
        String cloudUrl = pref.getString(USERS_ENDPOINT, "users/json");
        return cloudUrl;
    }


    //Users Endpoint
    public void saveTrainingTraineesEndpoint(String trainingTraineesEndpoint){
        editor.putString(TRAINING_TRAINEE_ENDPOINT, trainingTraineesEndpoint);
        editor.commit();
    }
    public String getTrainingTraineesEndpoint (){
        String trainingTraineesEndpoint = pref.getString(TRAINING_TRAINEE_ENDPOINT, "sync/trainees");
        return trainingTraineesEndpoint;
    }

    //TraineeStatus Endpoint
    public void saveTraineeStatusEndpoint(String traineeStatusEndpoint){
        editor.putString(TRAINEE_STATUS_ENDPOINT, traineeStatusEndpoint);
        editor.commit();
    }
    public String getTraineeStatusEndpoint (){
        return pref.getString(TRAINEE_STATUS_ENDPOINT, "sync/trainee-status");
    }



    //Session Attendance Endpoint
    public void saveSessionAttendanceEndpoint(String sessionAttendanceEndpoint){
        editor.putString(SESSION_ATTENDANCE_ENDPOINT, sessionAttendanceEndpoint);
        editor.commit();
    }
    public String getSessionAttendanceEndpoint (){
        String endpoint = pref.getString(SESSION_ATTENDANCE_ENDPOINT, "get/training/session-attendances");
        return endpoint;
    }

    //Session Attendance Endpoint
    public void uploadSessionAttendanceEndpoint(String sessionAttendanceEndpoint){
        editor.putString(UPLOAD_SESSION_ATTENDANCE_ENDPOINT, sessionAttendanceEndpoint);
        editor.commit();
    }
    public String getUploadSessionAttendanceEndpoint (){
        String endpoint = pref.getString(UPLOAD_SESSION_ATTENDANCE_ENDPOINT, "sync/training/session-attendances");
        return endpoint;
    }

    //Session Attendance JSON Root
    public void saveSessionAttendanceJSONRoot(String sessionAttendanceJSONRoot){
        editor.putString(SESSION_ATTENDANCE_JSON_ROOT, sessionAttendanceJSONRoot);
        editor.commit();
    }
    public String getSessionAttendanceJSONRoot (){
        String root = pref.getString(SESSION_ATTENDANCE_JSON_ROOT, "attendance");
        return root;
    }

    //Training Exams JSON Root
    public void saveTrainingExamsJSONRoot(String trainingExamJSONRoot){
        editor.putString(TRAINING_EXAMS_JSON_ROOT, trainingExamJSONRoot);
        editor.commit();
    }
    public String getTrainingExamsJSONRoot (){
        String root = pref.getString(TRAINING_EXAMS_JSON_ROOT, "exams");
        return root;
    }

    //Training Exams Endpoint
    public void saveTrainingExamsEndpoint(String trainingExamEndpoint){
        editor.putString(TRAINING_EXAMS_ENDPOINT, trainingExamEndpoint);
        editor.commit();
    }
    public String getTrainingExamsEndpoint (){
        return  pref.getString(TRAINING_EXAMS_ENDPOINT, "training/%s/exams");
    }

    public String getTrainingCertificationsEndpoint() {
        return  pref.getString(TRAINING_CERTIFICATION_ENDPOINT, "training/%s/certifications");
    }

    public void saveTrainingExamResultsEndpoint(String trainingExamResultsEndpoint){
        editor.putString(TRAINING_EXAMS_RESULTS_ENDPOINT, trainingExamResultsEndpoint);
        editor.commit();
    }
    public String getTrainingExamResultsEndpoint (){
        return  pref.getString(TRAINING_EXAMS_RESULTS_ENDPOINT, "exam/%s/results");
    }

    public void saveTrainingExamResultsJSONRoot(String trainingExamResultsJsonRoot){
        editor.putString(TRAINING_EXAMS_RESULTS_JSON_ROOT, trainingExamResultsJsonRoot);
        editor.commit();
    }
    public String getTrainingExamResultsJSONRoot (){
        return  pref.getString(TRAINING_EXAMS_RESULTS_JSON_ROOT, "results");
    }

    public HashMap<String, String> getUserDetails (){
        HashMap<String, String> user = new HashMap<String, String>();
        //UserName
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));
        //email
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
        //userId
        user.put(KEY_USERID, String.valueOf(pref.getInt(KEY_USERID, 0)));

        user.put(KEY_USER_COUNTRY, String.valueOf(pref.getString(KEY_USER_COUNTRY, "ug")));

        //return user
        return user;
    }

    /**
     * Check for Login
     * It is reference in checkLogin()
     * THis is a boolean flag
     */
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }

    public void saveUserDetails (String name, String email, Integer userId, String country){
        //storing login values as TRUE
        editor.putBoolean(IS_LOGIN, true);

        //store name in pref
        editor.putString(KEY_NAME, name);

        //put email in pref
        editor.putString(KEY_EMAIL, email);

        //put userid
        editor.putInt(KEY_USERID, userId);

        //put user country
        editor.putString(KEY_USER_COUNTRY, country);

        //commit / Save the values
        editor.commit();

    }



}
