package com.expansion.lg.kimaru.training.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by kimaru on 2/21/18.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String LOG = "DatabaseHelper";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "training.db";

    public static String varchar_field = " varchar(512) ";
    public static String integer_field = " integer default 0 ";
    public static String text_field = " text ";
    public static String real_field = " REAL ";
    public static String primary_field = " id INTEGER PRIMARY KEY AUTOINCREMENT ";

    private static final String TABLE_TRAINING = "training";
    private static final String TABLE_TRAINING_VENUE = "training_venues";
    private static final String TABLE_SESSION_ATTENDANCE = "session_attendance";
    private static final String TABLE_SESSION_TOPIC = "session_topic";
    private static final String TABLE_TRAINING_SESSION = "training_session";
    private static final String TABLE_TRAINING_SESSION_TYPE = "training_session_type";
    private static final String TABLE_TRAINING_STATUS = "training_status";
    private static final String TABLE_TRAINING_ROLES = "training_roles";
    private static final String TABLE_TRAINING_TRAINERS = "training_trainers";
    private static final String TABLE_TRAINING_CLASSES = "training_classes";
    private static final String TABLE_TRAINING_TRAINEES = "trainees";

    // fields for Training
    private static final String ID = "id";
    private static final String TRAINING_NAME = "training_name";
    private static final String COUNTRY = "country";
    private static final String COUNTY_ID = "county_id";
    private static final String LOCATION_ID = "location_id";
    private static final String SUBCOUNTY_ID = "subcounty_id";
    private static final String WARD_ID = "ward_id";
    private static final String DISTRICT = "district";
    private static final String RECRUITMENT_ID = "recruitment_id";
    private static final String PARISH_ID = "parish_id";
    private static final String LAT = "lat";
    private static final String LON = "lon";
    private static final String TRAINING_VENUE_ID = "training_venue_id";
    private static final String TRAINING_STATUS_ID = "training_status_id";
    private static final String CLIENT_TIME = "client_time";
    private static final String CREATED_BY = "created_by";
    private static final String DATE_CREATED = "date_created";
    private static final String ARCHIVED = "archived";
    private static final String COMMENT = "comment";
    private static final String DATE_COMMENCED = "date_commenced";
    private static final String DATE_COMPLETED = "date_completed";
    public static final String CREATE_TABLE_TRAINING ="CREATE TABLE " + TABLE_TRAINING + "("
            + ID + varchar_field +", "
            + TRAINING_NAME + varchar_field + ", "
            + COUNTRY + varchar_field + ", "
            + COUNTY_ID + integer_field + ", "
            + LOCATION_ID + integer_field + ", "
            + SUBCOUNTY_ID + varchar_field + ", "
            + WARD_ID + varchar_field + ", "
            + DISTRICT + integer_field + ", "
            + RECRUITMENT_ID + varchar_field + ", "
            + PARISH_ID + varchar_field + ", "
            + LAT + real_field + ", "
            + LON + real_field + ", "
            + TRAINING_VENUE_ID + varchar_field + ", "
            + TRAINING_STATUS_ID + integer_field + ", "
            + CLIENT_TIME + real_field + ", "
            + CREATED_BY + real_field + ", "
            + DATE_CREATED + "DATETIME, "
            + ARCHIVED + integer_field + ", "
            + COMMENT + text_field + ", "
            + DATE_COMMENCED + real_field + ", "
            + DATE_COMPLETED + real_field + "); ";

    // training venue
    private static final String NAME = "name";
    private static final String MAPPING = "mapping";
    private static final String INSPECTED = "inspected";
    private static final String SELECTED = "selected";
    private static final String CAPACITY = "capacity";
    private static final String DATE_ADDED = "date_added";
    private static final String ADDED_BY = "added_by";
    private static final String META_DATA = "meta_data";

    public static final String CREATE_TABLE_TRAINING_VENUE ="CREATE TABLE " + TABLE_TRAINING_VENUE + "("
            + ID + varchar_field +", "
            + NAME + varchar_field + ", "
            + MAPPING + varchar_field + ", "
            + LAT + real_field + ", "
            + LON + real_field + ", "
            + INSPECTED + integer_field + ", "
            + COUNTRY + varchar_field + ", "
            + SELECTED + integer_field + ", "
            + CAPACITY + integer_field + ", "
            + DATE_ADDED + " DATETIME, " + " "
            + ADDED_BY + integer_field + ", "
            + CLIENT_TIME + real_field + ", "
            + META_DATA + text_field + ", "
            + ARCHIVED + integer_field + "); ";
    //sessionAttendance
    private static final String TRAINING_SESSION_ID = "training_session_id";
    private static final String TRAINEE_ID = "trainee_id";
    private static final String TRAINING_SESSION_TYPE_ID = "training_session_type_id";
    private static final String TRAINING_ID = "training_id";
    private static final String ATTENDED = "attended";
    public static final String CREATE_TABLE_SESSION_ATTENDANCE ="CREATE TABLE " + TABLE_SESSION_ATTENDANCE + "("
            + ID + varchar_field +", "
            + TRAINING_SESSION_ID + varchar_field + ", "
            + TRAINEE_ID + varchar_field + ", "
            + TRAINING_SESSION_TYPE_ID + integer_field + ", "
            + TRAINING_ID + varchar_field + ", "
            + COUNTRY + varchar_field + ", "
            + ATTENDED + integer_field + ", "
            + CREATED_BY + integer_field + ", "
            + CLIENT_TIME + real_field + ", "
            + DATE_CREATED + " DATETIME" + ", "
            + META_DATA + text_field + ", "
            + COMMENT + text_field + ", "
            + ARCHIVED + integer_field + "); ";

    public static final String CREATE_TABLE_SESSION_TOPIC ="CREATE TABLE " + TABLE_SESSION_TOPIC + "("
            + primary_field +", "
            + NAME + varchar_field + ", "
            + COUNTRY + varchar_field + ", "
            + ARCHIVED + integer_field + ", "
            + ADDED_BY + integer_field + ", "
            + DATE_ADDED + " DATETIME" + ", "
            + META_DATA + text_field + ", "
            + COMMENT + text_field + "); ";

    //training session
    private static final String CLASS_ID = "class_id";
    private static final String TRAINER_ID = "trainer_id";
    private static final String SESSION_START_TIME = "session_start_time";
    private static final String SESSION_END_TIME = "session_end_time";
    private static final String SESSION_TOPIC_ID = "session_topic_id";
    private static final String SESSION_LEAD_TRAINER = "session_lead_trainer";
    public static final String CREATE_TABLE_TRAINING_SESSION ="CREATE TABLE " + TABLE_TRAINING_SESSION + "("
            + ID + varchar_field+", "
            + TRAINING_SESSION_TYPE_ID + integer_field + ", "
            + CLASS_ID + integer_field + ", "
            + TRAINING_ID + varchar_field + ", "
            + TRAINER_ID + integer_field + ", "
            + COUNTRY + varchar_field + ", "
            + ARCHIVED + integer_field + ", "
            + SESSION_START_TIME + real_field + ", "
            + SESSION_END_TIME + real_field + ", "
            + SESSION_TOPIC_ID + integer_field + ", "
            + SESSION_LEAD_TRAINER + integer_field + ", "
            + CLIENT_TIME + real_field + ", "
            + CREATED_BY + real_field + ", "
            + DATE_CREATED + " DATETIME " + ", "
            + COMMENT + text_field + "); ";
    //training_session_type
    private static final String SESSION_NAME = "session_name";
    private static final String READONLY = "readonly";
    private static final String ROLE_NAME = "role_name";
    public static final String CREATE_TABLE_TRAINING_SESSION_TYPE ="CREATE TABLE " + TABLE_TRAINING_SESSION_TYPE + "("
            + ID + varchar_field+", "
            + SESSION_NAME + varchar_field + ", "
            + CLASS_ID + integer_field + ", "
            + COUNTRY + varchar_field + ", "
            + ARCHIVED + integer_field + ", "
            + CLIENT_TIME + real_field + ", "
            + CREATED_BY + integer_field + ", "
            + DATE_CREATED + real_field + ", "
            + COMMENT + text_field + "); ";

    public static final String CREATE_TABLE_TRAINING_STATUS ="CREATE TABLE " + TABLE_TRAINING_STATUS + "("
            + primary_field +", "
            + NAME + varchar_field + ", "
            + ARCHIVED + integer_field + ", "
            + READONLY + integer_field + ", "
            + COUNTRY + varchar_field + ", "
            + CLIENT_TIME + real_field + ", "
            + CREATED_BY + integer_field + ", "
            + DATE_CREATED + real_field + ", "
            + COMMENT + text_field + "); ";

    public static final String CREATE_TABLE_TRAINING_ROLES ="CREATE TABLE " + TABLE_TRAINING_ROLES + "("
            + primary_field +", "
            + ROLE_NAME + varchar_field + ", "
            + ARCHIVED + integer_field + ", "
            + READONLY + integer_field + ", "
            + COUNTRY + varchar_field + ", "
            + CLIENT_TIME + real_field + ", "
            + CREATED_BY + integer_field + ", "
            + DATE_CREATED + real_field + ", "
            + COMMENT + text_field + "); ";
    public static final String TRAINING_ROLE_ID = "training_role_id";
    public static final String CREATE_TABLE_TRAINING_TRAINERS ="CREATE TABLE " + TABLE_TRAINING_TRAINERS + "("
            + primary_field +", "
            + TRAINING_ID + varchar_field + ", "
            + CLASS_ID + integer_field + ", "
            + TRAINER_ID + integer_field + ", "
            + COUNTRY + varchar_field + ", "
            + CLIENT_TIME + real_field + ", "
            + CREATED_BY + integer_field + ", "
            + DATE_CREATED + real_field + ", "
            + ARCHIVED + integer_field + ", "
            + TRAINING_ROLE_ID + integer_field + ", "
            + COMMENT + text_field + "); ";
    public static final String CLASS_NAME = "class_name";
    public static final String CREATE_TABLE_TRAINING_CLASSES ="CREATE TABLE " + TABLE_TRAINING_CLASSES + "("
            + primary_field +", "
            + TRAINING_ID + varchar_field + ", "
            + CLASS_NAME + varchar_field + ", "
            + COUNTRY + varchar_field + ", "
            + CLIENT_TIME + real_field + ", "
            + CREATED_BY + integer_field + ", "
            + DATE_CREATED + " DATETIME " + ", "
            + ARCHIVED + integer_field + ", "
            + COMMENT + text_field + "); ";
    public static final String REGISTRATION_ID = "registration_id";
    public static final String BRANCH = "branch";
    public static final String COHORT = "cohort";
    public static final String CHP_CODE = "chp_code";
    public static final String CREATE_TABLE_TRAINING_TRAINEE ="CREATE TABLE " + TABLE_TRAINING_TRAINEES + "("
            + ID + varchar_field +", "
            + REGISTRATION_ID + varchar_field + ", "
            + CLASS_ID + integer_field + ", "
            + TRAINING_ID + varchar_field + ", "
            + COUNTRY + varchar_field + ", "
            + ADDED_BY + integer_field + ", "
            + DATE_CREATED + " DATETIME " + ", "
            + CLIENT_TIME + real_field + ", "
            + BRANCH + real_field + ", "
            + COHORT + real_field + ", "
            + CHP_CODE + real_field + ", "
            + ARCHIVED + integer_field + ", "
            + COMMENT + text_field + "); ";

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate (SQLiteDatabase db){

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }
}
