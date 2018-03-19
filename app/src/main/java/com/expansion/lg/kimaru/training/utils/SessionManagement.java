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

    private static final String PREF_NAME= "tremap";

    private static final String CLOUD_URL = "cloud_url";
    private static final String API_PREFIX = "api_prefix";
    private static final String API_VERSION = "api_version";
    private static final String API_SUFFIX = "api_suffix";

    private static final String TRAINING_ENDPOINT = "training_endpoint";
    private static final String TRAINING_DETAILS_ENDPOINT = "training_details_endpoint";
    private static final String TRAINING_DETAILS_JSON_ROOT = "training_details_json_root";
    private static final String TRAINING_JSON_ROOT = "training_json_root";
    private static final String USERS_ENDPOINT = "users_endpoint";
    private static final String TRAINING_TRAINEE_ENDPOINT = "training_trainee_endpoint";
    private static final String SESSION_TOPIC_ENDPOINT = "session_topic_endpoint";
    private static final String SESSION_TOPIC_JSON_ROOT = "session_topic_json_root";

    private static final String IS_LOGIN = "IsLoggedIn";
    public static final String KEY_NAME = "name";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_USERID = "userid";
    public static final String KEY_USER_COUNTRY = "country";


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
