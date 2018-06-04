package com.expansion.lg.kimaru.training.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Base64;
import android.util.Log;

import com.expansion.lg.kimaru.training.objs.Branch;
import com.expansion.lg.kimaru.training.objs.Cohort;
import com.expansion.lg.kimaru.training.objs.SessionAttendance;
import com.expansion.lg.kimaru.training.objs.SessionTopic;
import com.expansion.lg.kimaru.training.objs.TraineeStatus;
import com.expansion.lg.kimaru.training.objs.Training;
import com.expansion.lg.kimaru.training.objs.TrainingClass;
import com.expansion.lg.kimaru.training.objs.TrainingComment;
import com.expansion.lg.kimaru.training.objs.TrainingRole;
import com.expansion.lg.kimaru.training.objs.TrainingSession;
import com.expansion.lg.kimaru.training.objs.TrainingSessionType;
import com.expansion.lg.kimaru.training.objs.TrainingStatus;
import com.expansion.lg.kimaru.training.objs.TrainingTrainee;
import com.expansion.lg.kimaru.training.objs.TrainingTrainer;
import com.expansion.lg.kimaru.training.objs.TrainingVenue;
import com.expansion.lg.kimaru.training.objs.User;
import com.expansion.lg.kimaru.training.utils.DisplayDate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by kimaru on 2/21/18.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String LOG = "DatabaseHelper";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "training.db";

    private static String varchar_field = " varchar(512) ";
    private static String integer_field = " integer default 0 ";
    private static String text_field = " text ";
    private static String real_field = " REAL ";
    private static String datetime_field = " DATETIME ";
    private static String primary_field = " id INTEGER PRIMARY KEY AUTOINCREMENT ";

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
    private static final String TABLE_USERS = "users";
    private static final String TABLE_TRAINING_COMMENTS = "training_comments";
    private static final String TABLE_BRANCH = "branch";
    private static final String TABLE_COHORT = "cohort";
    private static final String TABLE_TRAINEE_STATUS = "trainee_status";

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
    private static final String BRANCH_NAME = "branch_name";
    private static final String BRANCH_CODE = "branch_code";
    private static final String COHORT_NUMBER = "cohort_number";
    private static final String COHORT_NAME = "cohort_name";
    private static final String TRAINEE_STATUS = "trainee_status";
    private static final String BRANCH_ID = "branch_id";
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
            + DATE_CREATED + datetime_field +", "
            + ARCHIVED + integer_field + ", "
            + COMMENT + text_field + ", "
            + DATE_COMMENCED + real_field + ", "
            + DATE_COMPLETED + real_field + "); ";

    // training venue
    private static final String NAME = "name";
    private static final String MAPPING = "mapping";
    private static final String MAPPING_ID = "mapping_id";
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
            + DATE_ADDED + datetime_field +", "
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
            + DATE_CREATED + datetime_field +", "
            + META_DATA + text_field + ", "
            + COMMENT + text_field + ", "
            + ARCHIVED + integer_field + "); ";

    public static final String CREATE_TABLE_SESSION_TOPIC ="CREATE TABLE " + TABLE_SESSION_TOPIC + "("
            + primary_field +", "
            + NAME + varchar_field + ", "
            + COUNTRY + varchar_field + ", "
            + ARCHIVED + integer_field + ", "
            + ADDED_BY + integer_field + ", "
            + DATE_ADDED + datetime_field +", "
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
            + DATE_CREATED + datetime_field +", "
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
            + DATE_CREATED + datetime_field +", "
            + ARCHIVED + integer_field + ", "
            + COMMENT + text_field + "); ";
    public static final String REGISTRATION_ID = "registration_id";
    public static final String BRANCH = "branch";
    public static final String COHORT = "cohort";
    public static final String CHP_CODE = "chp_code";
    public static final String REGISTRATION = "registration";
    public static final String CREATE_TABLE_TRAINING_TRAINEE ="CREATE TABLE " + TABLE_TRAINING_TRAINEES + "("
            + ID + varchar_field +", "
            + REGISTRATION_ID + varchar_field + ", "
            + CLASS_ID + integer_field + ", "
            + TRAINING_ID + varchar_field + ", "
            + COUNTRY + varchar_field + ", "
            + ADDED_BY + integer_field + ", "
            + DATE_CREATED + datetime_field +", "
            + CLIENT_TIME + real_field + ", "
            + BRANCH + varchar_field + ", "
            + COHORT + real_field + ", "
            + CHP_CODE + real_field + ", "
            + ARCHIVED + integer_field + ", "
            + TRAINEE_STATUS + integer_field + ", "
            + REGISTRATION + text_field + ", " //will be storing the JSON
            + COMMENT + text_field + "); ";

    public static final String CREATE_TABLE_TRAINEE_COMMENTS ="CREATE TABLE " + TABLE_TRAINING_COMMENTS + "("
            + ID + varchar_field +", "
            + TRAINEE_ID + varchar_field + ", "
            + TRAINING_ID + varchar_field + ", "
            + COUNTRY + varchar_field + ", "
            + ADDED_BY + integer_field + ", "
            + DATE_CREATED + datetime_field +", "
            + CLIENT_TIME + real_field + ", "
            + ARCHIVED + integer_field + ", "
            + COMMENT + text_field + "); ";

    private static final String EMAIL= "email";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";

    private static final String CREATE_TABLE_USERS="CREATE TABLE " + TABLE_USERS + "("
            + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + EMAIL + varchar_field + ", "
            + USERNAME + varchar_field + ", "
            + PASSWORD + varchar_field + ", "
            + NAME + varchar_field + ", "
            + COUNTRY + varchar_field + "); ";

    private static final String CREATE_TABLE_BRANCH="CREATE TABLE " + TABLE_BRANCH + "("
            + ID + varchar_field +", "
            + BRANCH_NAME + varchar_field + ", "
            + BRANCH_CODE + varchar_field + ", "
            + MAPPING + varchar_field + ", "
            + LAT + real_field + ", "
            + LON + real_field + ", "
            + ARCHIVED + integer_field + "); ";

    private static final String CREATE_TABLE_COHORT="CREATE TABLE " + TABLE_COHORT + "("
            + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COHORT_NAME + varchar_field + ", "
            + COHORT_NUMBER + varchar_field + ", "
            + BRANCH_ID + varchar_field + ", "
            + ARCHIVED + integer_field + "); ";


    private static final String CREATE_TABLE_TRAINEE_STATUS="CREATE TABLE " + TABLE_TRAINEE_STATUS + "("
            + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + NAME + varchar_field + ", "
            + ARCHIVED + integer_field + ", "
            + READONLY + integer_field + ", "
            + COUNTRY + varchar_field + ", "
            + CLIENT_TIME + real_field + ", "
            + CREATED_BY + integer_field + ", "
            + DATE_CREATED + varchar_field + "); ";

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate (SQLiteDatabase db){
        db.execSQL(CREATE_TABLE_TRAINING);
        db.execSQL(CREATE_TABLE_TRAINING_VENUE);
        db.execSQL(CREATE_TABLE_SESSION_ATTENDANCE);
        db.execSQL(CREATE_TABLE_SESSION_TOPIC);
        db.execSQL(CREATE_TABLE_TRAINING_SESSION);
        db.execSQL(CREATE_TABLE_TRAINING_SESSION_TYPE);
        db.execSQL(CREATE_TABLE_TRAINING_STATUS);
        db.execSQL(CREATE_TABLE_TRAINING_ROLES);
        db.execSQL(CREATE_TABLE_TRAINING_TRAINERS);
        db.execSQL(CREATE_TABLE_TRAINING_CLASSES);
        db.execSQL(CREATE_TABLE_TRAINING_TRAINEE);
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_TRAINEE_COMMENTS);
        db.execSQL(CREATE_TABLE_BRANCH);
        db.execSQL(CREATE_TABLE_COHORT);
        db.execSQL(CREATE_TABLE_TRAINEE_STATUS);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }

    private String [] trainingColumns = new String[]{ID, TRAINING_NAME, COUNTRY, COUNTY_ID, LOCATION_ID,
            SUBCOUNTY_ID, WARD_ID, DISTRICT, RECRUITMENT_ID, PARISH_ID, LAT, LON, TRAINING_VENUE_ID,
            TRAINING_STATUS_ID, CLIENT_TIME, CREATED_BY, DATE_CREATED, ARCHIVED, COMMENT,
            DATE_COMMENCED, DATE_COMPLETED};
    private String [] trainingVenueColumns = new String[] {ID, NAME, MAPPING, LAT, LON, INSPECTED, COUNTRY,
            SELECTED, CAPACITY, DATE_ADDED, ADDED_BY, CLIENT_TIME, META_DATA, ARCHIVED};
    private String[] sessionAttendanceColumns = new String[]{ID, TRAINING_SESSION_ID, TRAINEE_ID,
            TRAINING_SESSION_TYPE_ID, TRAINING_ID, COUNTRY, ATTENDED, CREATED_BY, CLIENT_TIME,
            DATE_CREATED, META_DATA, COMMENT, ARCHIVED};
    private String[] sessionTopicColumns = new String[] {ID, NAME, COUNTRY, ARCHIVED,ADDED_BY, DATE_ADDED,
            META_DATA, COMMENT};
    private String[] trainingSessionColumns = new String[]{ID,TRAINING_SESSION_TYPE_ID, CLASS_ID,
            TRAINING_ID, TRAINER_ID, COUNTRY, ARCHIVED, SESSION_START_TIME, SESSION_END_TIME,
            SESSION_TOPIC_ID, SESSION_LEAD_TRAINER, CLIENT_TIME, CREATED_BY, DATE_CREATED, COMMENT};
    private String[] trainingSessionTypeColumns = new String[]{ID, SESSION_NAME, CLASS_ID, COUNTRY,
            ARCHIVED, CLIENT_TIME, CREATED_BY, DATE_CREATED, COMMENT};
    private String[] trainingStatusColumns = new String[]{ID, NAME, ARCHIVED, READONLY, COUNTRY,
            CLIENT_TIME, CREATED_BY, DATE_CREATED, COMMENT};

    private String[] trainingRoleColumns = new String[]{ID, ROLE_NAME, ARCHIVED, READONLY,
            COUNTRY, CLIENT_TIME, CREATED_BY, DATE_CREATED, COMMENT};

    private String [] trainingTrainerColumns = new String[] {ID, TRAINING_ID, CLASS_ID, TRAINER_ID,
            COUNTRY, CLIENT_TIME, CREATED_BY, DATE_CREATED, ARCHIVED, TRAINING_ROLE_ID, COMMENT};

    private String [] trainingClassesColumns = new String[] {ID, TRAINING_ID, CLASS_NAME, COUNTRY,
            CLIENT_TIME, CREATED_BY, DATE_CREATED, ARCHIVED, COMMENT};

    private String [] trainingTraineeColumns = new String[] {ID, REGISTRATION_ID, CLASS_ID,
            TRAINING_ID, COUNTRY, ADDED_BY, DATE_CREATED, CLIENT_TIME, BRANCH, COHORT, CHP_CODE,
            ARCHIVED, REGISTRATION, COMMENT, TRAINEE_STATUS};
    private String [] userColumns = new String[] {ID, EMAIL, USERNAME, PASSWORD, NAME, COUNTRY};
    private String [] trainingCommentColumns = new String[] {ID,TRAINEE_ID, TRAINING_ID,
            COUNTRY, ADDED_BY, DATE_CREATED, CLIENT_TIME, ARCHIVED, COMMENT};
    private String[] branchColumns = new String[] {ID,BRANCH_NAME,BRANCH_CODE,MAPPING_ID,LAT,LON,ARCHIVED};
    private String[] cohortColumns = new String[] {ID,COHORT_NAME,COHORT_NUMBER,BRANCH_ID,ARCHIVED};
    private String[] traineeStatusColumns = new String[] {ID,NAME,ARCHIVED,READONLY,COUNTRY,
            CLIENT_TIME, CREATED_BY, DATE_CREATED};

    /**
     * **************************************
     * Training                             *
     * **************************************
     */


    /**
     *
     * @param training
     * @return id
     *
     */
    public long addTraining(Training training){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ID, training.getId());
        cv.put(TRAINING_NAME, training.getTrainingName());
        cv.put(COUNTRY, training.getCountry());
        cv.put(COUNTY_ID, training.getCountyId());
        cv.put(LOCATION_ID, training.getLocationId());
        cv.put(SUBCOUNTY_ID, training.getSubCountyId());
        cv.put(WARD_ID, training.getWardId());
        cv.put(DISTRICT, training.getDistrict());
        cv.put(RECRUITMENT_ID, training.getRecruitmentId());
        cv.put(PARISH_ID, training.getParishId());
        cv.put(LAT, training.getLat());
        cv.put(LON, training.getLon());
        cv.put(TRAINING_VENUE_ID, training.getTrainingVenueId());
        cv.put(TRAINING_STATUS_ID, training.getTrainingStatusId());
        cv.put(CLIENT_TIME, training.getClientTime());
        cv.put(CREATED_BY, training.getCreatedBy());
        cv.put(DATE_CREATED, training.getDateCreated());
        cv.put(ARCHIVED, training.isArchived());
        cv.put(COMMENT, training.getComment());
        cv.put(DATE_COMMENCED, training.getDateCommenced());
        cv.put(DATE_COMPLETED, training.getDateCompleted());
        long id;
        if(trainingExists(training)){
            id = db.update(TABLE_TRAINING, cv, ID+"='"+training.getId()+"'", null);
        }else{
            id = db.insertWithOnConflict(TABLE_TRAINING, null, cv, SQLiteDatabase.CONFLICT_REPLACE);
        }
        db.close();
        return id;
    }

    /**
     *
     * @param trainingId
     * @return training
     */
    public Training getTrainingById(String trainingId){
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = ID +" = ?";
        String[] whereArgs = new String[] {
                trainingId,
        };
        Cursor cursor=db.query(TABLE_TRAINING,trainingColumns,whereClause,whereArgs,null,
                null,null,null);
        if (!(cursor.moveToFirst()) || cursor.getCount() ==0){
            return null;
        }else{

            Training training = cursorToTraining(cursor);
            cursor.close();
            return training;
        }
    }

    /**
     *
     * @param country
     * @return training
     */
    public List<Training> getTrainingsByCountry(String country){
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = COUNTRY +" = ?";
        String[] whereArgs = new String[] {
                country,
        };
        Cursor cursor=db.query(TABLE_TRAINING,trainingColumns,whereClause,whereArgs,null,
                null,null,null);
        List<Training> trainingList = new ArrayList<>();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
            Training training = cursorToTraining(cursor);
            trainingList.add(training);
        }
        cursor.close();
        return trainingList;
    }

    /**
     *
     * @return Trainings
     */
    public List<Training> getTrainings(){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(TABLE_TRAINING,trainingColumns,null,null,null,null,null,null);
        List<Training> trainingList = new ArrayList<>();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
            Training training = cursorToTraining(cursor);
            trainingList.add(training);
        }
        cursor.close();
        return trainingList;
    }

    public void trainingFromJson(JSONObject jsonObject){
        Training training = new Training();
        try {
            training.setId(jsonObject.getString(ID));
            if (!jsonObject.isNull(TRAINING_NAME)){
                training.setTrainingName(jsonObject.getString(TRAINING_NAME));
            }
            if (!jsonObject.isNull(COUNTRY)){
                training.setCountry(jsonObject.getString(COUNTRY));
            }
            if (!jsonObject.isNull(COUNTY_ID)){
                training.setCountyId(jsonObject.getInt(COUNTY_ID));
            }
            if (!jsonObject.isNull(SUBCOUNTY_ID)){
                training.setSubCountyId(jsonObject.getString(SUBCOUNTY_ID));
            }
            if (!jsonObject.isNull(WARD_ID)){
                training.setWardId(jsonObject.getString(WARD_ID));
            }
            if (!jsonObject.isNull(DISTRICT)){
                training.setDistrict(jsonObject.getString(DISTRICT));
            }
            if (!jsonObject.isNull(PARISH_ID)){
                training.setParishId(jsonObject.getString(PARISH_ID));
            }
            if (!jsonObject.isNull(LOCATION_ID)){
                training.setLocationId(jsonObject.getInt(LOCATION_ID));
            }
            if (!jsonObject.isNull(CREATED_BY)){
                training.setCreatedBy(jsonObject.getInt(CREATED_BY));
            }
            if (!jsonObject.isNull(TRAINING_STATUS_ID)){
                training.setTrainingStatusId(jsonObject.getInt(TRAINING_STATUS_ID));
            }
            if (!jsonObject.isNull(RECRUITMENT_ID)){
                training.setRecruitmentId(jsonObject.getString(RECRUITMENT_ID));
            }
            if (!jsonObject.isNull(COMMENT)){
                training.setComment(jsonObject.getString(COMMENT));
            }
            if (!jsonObject.isNull(CLIENT_TIME)){
                training.setClientTime(jsonObject.getLong(CLIENT_TIME));
            }
            if (!jsonObject.isNull(LAT)){
                training.setLat(jsonObject.getDouble(LAT));
            }
            if (!jsonObject.isNull(LON)){
                training.setLon(jsonObject.getDouble(LON));
            }
            if (!jsonObject.isNull(TRAINING_VENUE_ID)){
                training.setTrainingVenueId(jsonObject.getString(TRAINING_VENUE_ID));
            }
            if (!jsonObject.isNull(DATE_CREATED)){
                training.setDateCreated(jsonObject.getString(DATE_CREATED));
            }
            if (!jsonObject.isNull(ARCHIVED)){
                training.setArchived(jsonObject.getInt(ARCHIVED)==1);
            }
            if (!jsonObject.isNull(DATE_COMMENCED)){
                training.setDateCommenced(jsonObject.getLong(DATE_COMMENCED));
            }
            if (!jsonObject.isNull(DATE_COMPLETED)){
                training.setDateCompleted(jsonObject.getLong(DATE_COMPLETED));
            }
            this.addTraining(training);
        }catch (Exception e){
            Log.d("Tremap", "=======ERR Creating Training============");
            Log.d("Tremap", e.getMessage());
            Log.d("Tremap", "===================");
        }
    }

    /**
     *
     * @param training
     * @return bool
     */
    public boolean trainingExists(Training training) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cur = db.rawQuery("SELECT "+ID+" FROM " + TABLE_TRAINING + " WHERE "+ID+" = '"
                + training.getId() + "'", null);
        boolean exist = (cur.getCount() > 0);
        cur.close();
        return exist;

    }

    /**
     *
     * @param cursor
     * @return Training
     */
    private Training cursorToTraining(Cursor cursor){
        Training training = new Training();
        training.setId(cursor.getString(cursor.getColumnIndex(ID)));
        training.setTrainingName(cursor.getString(cursor.getColumnIndex(TRAINING_NAME)));
        training.setCountry(cursor.getString(cursor.getColumnIndex(COUNTRY)));
        training.setCountyId(cursor.getInt(cursor.getColumnIndex(COUNTY_ID)));
        training.setLocationId(cursor.getInt(cursor.getColumnIndex(LOCATION_ID)));
        training.setSubCountyId(cursor.getString(cursor.getColumnIndex(SUBCOUNTY_ID)));
        training.setWardId(cursor.getString(cursor.getColumnIndex(WARD_ID)));
        training.setDistrict(cursor.getString(cursor.getColumnIndex(DISTRICT)));
        training.setRecruitmentId(cursor.getString(cursor.getColumnIndex(RECRUITMENT_ID)));
        training.setParishId(cursor.getString(cursor.getColumnIndex(PARISH_ID)));
        training.setLat(cursor.getDouble(cursor.getColumnIndex(LAT)));
        training.setLon(cursor.getDouble(cursor.getColumnIndex(LON)));
        training.setTrainingVenueId(cursor.getString(cursor.getColumnIndex(TRAINING_VENUE_ID)));
        training.setTrainingStatusId(cursor.getInt(cursor.getColumnIndex(TRAINING_STATUS_ID)));
        training.setClientTime(cursor.getLong(cursor.getColumnIndex(CLIENT_TIME)));
        training.setCreatedBy(cursor.getInt(cursor.getColumnIndex(CREATED_BY)));
        training.setDateCreated(cursor.getString(cursor.getColumnIndex(DATE_CREATED)));
        training.setArchived(cursor.getInt(cursor.getColumnIndex(ARCHIVED))==1);
        training.setComment(cursor.getString(cursor.getColumnIndex(COMMENT)));
        training.setDateCommenced(cursor.getLong(cursor.getColumnIndex(DATE_COMMENCED)));
        training.setDateCompleted(cursor.getLong(cursor.getColumnIndex(DATE_COMPLETED)));
        return training;
    }

    /**
     * ********************************************
     * Training Venue                             *
     * ********************************************
     */

    public long addTrainingVenue(TrainingVenue trainingVenue){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ID, trainingVenue.getId());
        cv.put(NAME, trainingVenue.getName());
        cv.put(MAPPING, trainingVenue.getMapping());
        cv.put(LAT, trainingVenue.getLat());
        cv.put(LON, trainingVenue.getLon());
        cv.put(INSPECTED, trainingVenue.getInspected());
        cv.put(COUNTRY, trainingVenue.getCountry());
        cv.put(SELECTED, trainingVenue.isSelected());
        cv.put(CAPACITY, trainingVenue.getCapacity());
        cv.put(DATE_ADDED, trainingVenue.getDateAdded());
        cv.put(ADDED_BY, trainingVenue.getAddedBy());
        cv.put(CLIENT_TIME, trainingVenue.getClientTime());
        cv.put(META_DATA, trainingVenue.getMetaData());
        cv.put(ARCHIVED, trainingVenue.isArchived());
        long id;
        if(trainingVenueExists(trainingVenue)){
            id = db.update(TABLE_TRAINING_VENUE, cv, ID+"='"+trainingVenue.getId()+"'", null);
        }else{
            id = db.insertWithOnConflict(TABLE_TRAINING_VENUE, null, cv,
                    SQLiteDatabase.CONFLICT_REPLACE);
        }
        db.close();
        return id;
    }

    /**
     *
     * @param trainingVenue
     * @return bool
     */
    public boolean trainingVenueExists(TrainingVenue trainingVenue) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cur = db.rawQuery("SELECT "+ID+" FROM " + TABLE_TRAINING_VENUE + " WHERE "+ID+" = '"
                + trainingVenue.getId() + "'", null);
        boolean exist = (cur.getCount() > 0);
        cur.close();
        return exist;

    }

    public TrainingVenue getTrainingVenueById(String trainingVenueId){
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = ID +" = ?";
        String[] whereArgs = new String[] {
                trainingVenueId,
        };
        Cursor cursor=db.query(TABLE_TRAINING_VENUE,trainingVenueColumns,whereClause,whereArgs,
                null,null,null,null);
        if (!(cursor.moveToFirst()) || cursor.getCount() ==0){
            return null;
        }else{

            TrainingVenue trainingVenue = cursorToTrainingVenue(cursor);
            cursor.close();
            return trainingVenue;
        }
    }

    /**
     *
     * @param cursor
     * @return TrainingVenue
     */
    private TrainingVenue cursorToTrainingVenue(Cursor cursor){
        TrainingVenue trainingVenue = new TrainingVenue();
        trainingVenue.setId(cursor.getString(cursor.getColumnIndex(ID)));
        trainingVenue.setName(cursor.getString(cursor.getColumnIndex(NAME)));
        trainingVenue.setMapping(cursor.getString(cursor.getColumnIndex(MAPPING)));
        trainingVenue.setLat(cursor.getDouble(cursor.getColumnIndex(LAT)));
        trainingVenue.setLon(cursor.getDouble(cursor.getColumnIndex(LON)));
        trainingVenue.setInspected(cursor.getInt(cursor.getColumnIndex(INSPECTED)));
        trainingVenue.setCountry(cursor.getString(cursor.getColumnIndex(COUNTRY)));
        trainingVenue.setSelected(cursor.getInt(cursor.getColumnIndex(SELECTED))==1);
        trainingVenue.setCapacity(cursor.getInt(cursor.getColumnIndex(CAPACITY)));
        trainingVenue.setDateAdded(cursor.getString(cursor.getColumnIndex(DATE_ADDED)));
        trainingVenue.setAddedBy(cursor.getInt(cursor.getColumnIndex(ADDED_BY)));
        trainingVenue.setClientTime(cursor.getLong(cursor.getColumnIndex(CLIENT_TIME)));
        trainingVenue.setMetaData(cursor.getString(cursor.getColumnIndex(META_DATA)));
        trainingVenue.setArchived(cursor.getInt(cursor.getColumnIndex(ARCHIVED))==1);
        return trainingVenue;
    }

    public List<TrainingVenue> getTrainingVenues(){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(TABLE_TRAINING_VENUE,trainingVenueColumns,null,null,null,null,
                null,null);
        List<TrainingVenue> trainingVenueList = new ArrayList<>();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
            TrainingVenue trainingVenue = cursorToTrainingVenue(cursor);
            trainingVenueList.add(trainingVenue);
        }
        cursor.close();
        return trainingVenueList;
    }

    public void trainingVenueFromJson(JSONObject jsonObject){
        TrainingVenue trainingVenue = new TrainingVenue();

        try {
            trainingVenue.setId(jsonObject.getString(ID));
            if (!jsonObject.isNull(NAME)){
                trainingVenue.setName(jsonObject.getString(NAME));
            }
            if (!jsonObject.isNull(MAPPING)){
                trainingVenue.setMapping(jsonObject.getString(MAPPING));
            }
            if (!jsonObject.isNull(LAT)){
                trainingVenue.setLat(jsonObject.getDouble(LAT));
            }
            if (!jsonObject.isNull(LON)){
                trainingVenue.setLon(jsonObject.getDouble(LON));
            }
            if (!jsonObject.isNull(INSPECTED)){
                trainingVenue.setInspected(jsonObject.getInt(INSPECTED));
            }
            if (!jsonObject.isNull(COUNTRY)){
                trainingVenue.setCountry(jsonObject.getString(COUNTRY));
            }
            if (!jsonObject.isNull(SELECTED)){
                trainingVenue.setSelected(jsonObject.getInt(SELECTED)==1);
            }
            if (!jsonObject.isNull(CAPACITY)){
                trainingVenue.setCapacity(jsonObject.getInt(CAPACITY));
            }
            if (!jsonObject.isNull(DATE_ADDED)){
                trainingVenue.setDateAdded(jsonObject.getString(DATE_ADDED));
            }
            if (!jsonObject.isNull(ADDED_BY)){
                trainingVenue.setAddedBy(jsonObject.getInt(ADDED_BY));
            }
            if (!jsonObject.isNull(CLIENT_TIME)){
                trainingVenue.setClientTime(jsonObject.getLong(CLIENT_TIME));
            }
            if (!jsonObject.isNull(META_DATA)){
                trainingVenue.setMetaData(jsonObject.getString(META_DATA));
            }
            if (!jsonObject.isNull(ARCHIVED)){
                trainingVenue.setArchived(jsonObject.getInt(ARCHIVED)==1);
            }
            this.addTrainingVenue(trainingVenue);
        }catch (Exception e){
            Log.d("Tremap", "=======ERR Creating Training Venue from JSON============");
            Log.d("Tremap", e.getMessage());
            Log.d("Tremap", "===================");
        }
    }


    /**
     * ************************************
     *        SESSION ATTENDANCE          *
     * ************************************
     */

    private SessionAttendance cursorToSessionAttendance(Cursor cursor){
        SessionAttendance sessionAttendance = new SessionAttendance();

        sessionAttendance.setId(cursor.getString(cursor.getColumnIndex(ID)));
        sessionAttendance.setTrainingSessionId(cursor.getString(cursor.getColumnIndex(
                TRAINING_SESSION_ID)));
        sessionAttendance.setTraineeId(cursor.getString(cursor.getColumnIndex(TRAINEE_ID)));
        sessionAttendance.setTrainingSessionTypeId(cursor.getInt(cursor.getColumnIndex(
                TRAINING_SESSION_TYPE_ID)));
        sessionAttendance.setTrainingId(cursor.getString(cursor.getColumnIndex(TRAINING_ID)));
        sessionAttendance.setCountry(cursor.getString(cursor.getColumnIndex(COUNTRY)));
        sessionAttendance.setAttended(cursor.getInt(cursor.getColumnIndex(ATTENDED))==1);
        sessionAttendance.setCreatedBy(cursor.getInt(cursor.getColumnIndex(CREATED_BY)));
        sessionAttendance.setClientTime(cursor.getLong(cursor.getColumnIndex(CLIENT_TIME)));
        sessionAttendance.setDateCreated(cursor.getString(cursor.getColumnIndex(DATE_CREATED)));
        sessionAttendance.setMetaData(cursor.getString(cursor.getColumnIndex(META_DATA)));
        sessionAttendance.setComment(cursor.getString(cursor.getColumnIndex(COMMENT)));
        sessionAttendance.setArchived(cursor.getInt(cursor.getColumnIndex(ARCHIVED))==1);
        return sessionAttendance;
    }

    public void generateSessionAttendance(String trainingId){
        /**
         * get all topics
         * for each topic, get trainees
         * for each trainee, create sesssion
         */
        for (TrainingSession session : getTrainingSessionsByTrainingId(trainingId)){
            for (TrainingTrainee trainee : getTrainingTraineesByTrainingId(trainingId)){
                //if training_id and session_id exists, do not create one
                if (getSessionAttendancesByTraineeIdAndTrainindId(trainee.getId(), trainingId, session.getId()) == null) {
                    Long currentDate = new Date().getTime();
                    SessionAttendance s = new SessionAttendance();
                    s.setId(UUID.randomUUID().toString());
                    s.setTrainingSessionId(session.getId());
                    s.setTraineeId(trainee.getId());
                    s.setTrainingId(trainingId);
                    s.setCountry(session.getCountry());
                    s.setDateCreated(new DisplayDate(currentDate).dateAndTime());
                    s.setCreatedBy(1);
                    s.setAttended(false);
                    s.setArchived(false);
                    s.setClientTime(currentDate);
                    addSessionAttendance(s);
                }
            }
        }
    }

    public long addSessionAttendance(SessionAttendance sessionAttendance){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ID, sessionAttendance.getId());
        cv.put(TRAINING_SESSION_ID, sessionAttendance.getTrainingSessionId());
        cv.put(TRAINEE_ID, sessionAttendance.getTraineeId());
        cv.put(TRAINING_SESSION_TYPE_ID, sessionAttendance.getTrainingSessionTypeId());
        cv.put(TRAINING_ID, sessionAttendance.getTrainingId());
        cv.put(COUNTRY, sessionAttendance.getCountry());
        cv.put(ATTENDED, sessionAttendance.isAttended());
        cv.put(CREATED_BY, sessionAttendance.getCreatedBy());
        cv.put(CLIENT_TIME, sessionAttendance.getClientTime());
        cv.put(DATE_CREATED, sessionAttendance.getDateCreated());
        cv.put(META_DATA, sessionAttendance.getMetaData());
        cv.put(COMMENT, sessionAttendance.getComment());
        cv.put(ARCHIVED, sessionAttendance.isArchived());
        long id;
        if(sessionAttendanceExists(sessionAttendance)){
            id = db.update(TABLE_SESSION_ATTENDANCE, cv, ID+"='"+sessionAttendance.getId()+"'",
                    null);
        }else{
            id = db.insertWithOnConflict(TABLE_SESSION_ATTENDANCE, null, cv,
                    SQLiteDatabase.CONFLICT_REPLACE);
        }
        db.close();
        return id;
    }

    public boolean sessionAttendanceExists(SessionAttendance sessionAttendance) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cur = db.rawQuery("SELECT "+ID+" FROM " + TABLE_SESSION_ATTENDANCE + " WHERE "+
                ID+" = '" + sessionAttendance.getId() + "'", null);
        boolean exist = (cur.getCount() > 0);
        cur.close();
        return exist;

    }

    public SessionAttendance getSessionAttendaceById(String sessionAttendanceId){
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = ID +" = ?";
        String[] whereArgs = new String[] {
                sessionAttendanceId,
        };
        Cursor cursor=db.query(TABLE_SESSION_ATTENDANCE, sessionAttendanceColumns, whereClause,
                whereArgs,null,null,null,null);
        if (!(cursor.moveToFirst()) || cursor.getCount() ==0){
            return null;
        }else{

            SessionAttendance sessionAttendance = cursorToSessionAttendance(cursor);
            cursor.close();
            return sessionAttendance;
        }
    }

    public List<SessionAttendance> getSessionAttendancesBySessionId(String sessionId){
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = TRAINING_SESSION_ID +" = ?";
        String[] whereArgs = new String[] {
                sessionId,
        };
        Cursor cursor=db.query(TABLE_SESSION_ATTENDANCE, sessionAttendanceColumns, whereClause,
                whereArgs,null,null,null,null);
        List<SessionAttendance> sessionAttendanceList = new ArrayList<>();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
            SessionAttendance sessionAttendance = cursorToSessionAttendance(cursor);
            sessionAttendanceList.add(sessionAttendance);
        }
        cursor.close();
        return sessionAttendanceList;
    }

    public List<SessionAttendance> getSessionAttendancesByTraineeId(String traineeId){
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = TRAINEE_ID +" = ?";
        String[] whereArgs = new String[] {
                traineeId,
        };
        Cursor cursor=db.query(TABLE_SESSION_ATTENDANCE, sessionAttendanceColumns, whereClause,
                whereArgs,null,null,null,null);
        List<SessionAttendance> sessionAttendanceList = new ArrayList<>();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
            SessionAttendance sessionAttendance = cursorToSessionAttendance(cursor);
            sessionAttendanceList.add(sessionAttendance);
        }
        cursor.close();
        return sessionAttendanceList;
    }

    public SessionAttendance getSessionAttendancesByTraineeIdAndTrainindId(String traineeId,
                                                                           String trainingId, String sessionId){
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = TRAINEE_ID +" = ? AND " + TRAINING_ID +" = ? AND "+TRAINING_SESSION_ID+" = ?";
        String[] whereArgs = new String[] {
                traineeId,
                trainingId,
                sessionId
        };
        Cursor cursor=db.query(TABLE_SESSION_ATTENDANCE, sessionAttendanceColumns, whereClause,
                whereArgs,null,null,null,null);
        if (!(cursor.moveToFirst()) || cursor.getCount() ==0){
            return null;
        }else{

            SessionAttendance sessionAttendance = cursorToSessionAttendance(cursor);
            cursor.close();
            return sessionAttendance;
        }
    }

    public List<SessionAttendance> getSessionAttendances(){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(TABLE_SESSION_ATTENDANCE,sessionAttendanceColumns,null,null,
                null,null,null,null);
        List<SessionAttendance> sessionAttendanceList = new ArrayList<>();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
            SessionAttendance sessionAttendance = cursorToSessionAttendance(cursor);
            sessionAttendanceList.add(sessionAttendance);
        }
        cursor.close();
        return sessionAttendanceList;
    }

    public void sessionAttendanceFromJSON(JSONObject jsonObject){
        SessionAttendance sessionAttendance = new SessionAttendance();
        try {
            sessionAttendance.setId(jsonObject.getString(ID));
            if (!jsonObject.isNull(TRAINING_SESSION_ID)){
                sessionAttendance.setTrainingSessionId(jsonObject.getString(TRAINING_SESSION_ID));
            }
            if (!jsonObject.isNull(TRAINEE_ID)){
                sessionAttendance.setTraineeId(jsonObject.getString(TRAINEE_ID));
            }
            if (!jsonObject.isNull(TRAINING_SESSION_TYPE_ID)){
                sessionAttendance.setTrainingSessionTypeId(jsonObject.getInt(TRAINING_SESSION_TYPE_ID));
            }
            if (!jsonObject.isNull(TRAINING_ID)){
                sessionAttendance.setTrainingId(jsonObject.getString(TRAINING_ID));
            }
            if (!jsonObject.isNull(COUNTRY)){
                sessionAttendance.setCountry(jsonObject.getString(COUNTRY));
            }
            if (!jsonObject.isNull(ATTENDED)){
                sessionAttendance.setAttended(jsonObject.getInt(ATTENDED)==1);
            }
            if (!jsonObject.isNull(CREATED_BY)){
                sessionAttendance.setCreatedBy(jsonObject.getInt(CREATED_BY));
            }
            if (!jsonObject.isNull(CLIENT_TIME)){
                sessionAttendance.setClientTime(jsonObject.getLong(CLIENT_TIME));
            }
            if (!jsonObject.isNull(DATE_CREATED)){
                sessionAttendance.setDateCreated(jsonObject.getString(DATE_CREATED));
            }
            if (!jsonObject.isNull(META_DATA)){
                sessionAttendance.setMetaData(jsonObject.getString(META_DATA));
            }
            if (!jsonObject.isNull(COMMENT)){
                sessionAttendance.setComment(jsonObject.getString(COMMENT));
            }
            if (!jsonObject.isNull(ARCHIVED)){
                sessionAttendance.setArchived(jsonObject.getInt(ARCHIVED)==1);
            }
            this.addSessionAttendance(sessionAttendance);
        }catch (Exception e){
            Log.d("TremapJSON", e.getMessage());
        }
    }


    /**
     * ************************************
     *              SESSION_TOPIC         *
     * ************************************
     */

    private SessionTopic cursorToSessionTopic(Cursor cursor){
        SessionTopic sessionTopic = new SessionTopic();
        sessionTopic.setId(cursor.getInt(cursor.getColumnIndex(ID)));
        sessionTopic.setName(cursor.getString(cursor.getColumnIndex(NAME)));
        sessionTopic.setCountry(cursor.getString(cursor.getColumnIndex(COUNTRY)));
        sessionTopic.setArchived(cursor.getInt(cursor.getColumnIndex(ARCHIVED))==1);
        sessionTopic.setAddedBy(cursor.getInt(cursor.getColumnIndex(ADDED_BY)));
        sessionTopic.setDateAdded(cursor.getString(cursor.getColumnIndex(DATE_ADDED)));
        sessionTopic.setMetaData(cursor.getString(cursor.getColumnIndex(META_DATA)));
        sessionTopic.setComment(cursor.getString(cursor.getColumnIndex(COMMENT)));
        return sessionTopic;
    }

    public void sessionTopicFromJson(JSONObject jsonObject){
        SessionTopic sessionTopic = new SessionTopic();
        try {
            sessionTopic.setId(jsonObject.getInt(ID));
            if (!jsonObject.isNull(NAME)){
                sessionTopic.setName(jsonObject.getString(NAME));
            }
            if (!jsonObject.isNull(COUNTRY)){
                sessionTopic.setCountry(jsonObject.getString(COUNTRY));
            }
            if (!jsonObject.isNull(ARCHIVED)){
                sessionTopic.setArchived(jsonObject.getInt(ARCHIVED)==0);
            }
            if (!jsonObject.isNull(ADDED_BY)){
                sessionTopic.setAddedBy(jsonObject.getInt(ADDED_BY));
            }
            if (!jsonObject.isNull(META_DATA)){
                sessionTopic.setMetaData(jsonObject.getString(META_DATA));
            }
            if (!jsonObject.isNull(COMMENT)){
                sessionTopic.setComment(jsonObject.getString(COMMENT));
            }
            this.addSessionTopic(sessionTopic);
        }catch (Exception e){
            Log.d("Tremap", "=======ERR Creating Training session ============");
            Log.d("Tremap", e.getMessage());
            Log.d("Tremap", "===================");
        }
    }

    public long addSessionTopic(SessionTopic sessionTopic){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ID, sessionTopic.getId());
        cv.put(NAME, sessionTopic.getName());
        cv.put(COUNTRY, sessionTopic.getCountry());
        cv.put(ARCHIVED, sessionTopic.isArchived());
        cv.put(ADDED_BY, sessionTopic.getAddedBy());
        cv.put(DATE_ADDED, sessionTopic.getDateAdded());
        cv.put(META_DATA, sessionTopic.getMetaData());
        cv.put(COMMENT, sessionTopic.getComment());
        long id;
        if(sessionTopicExists(sessionTopic)){
            id = db.update(TABLE_SESSION_TOPIC, cv, ID+"='"+sessionTopic.getId()+"'", null);
        }else{
            id = db.insertWithOnConflict(TABLE_SESSION_TOPIC, null, cv,
                    SQLiteDatabase.CONFLICT_REPLACE);
        }
        db.close();
        return id;
    }

    public boolean sessionTopicExists(SessionTopic sessionTopic) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cur = db.rawQuery("SELECT "+ID+" FROM " + TABLE_SESSION_TOPIC + " WHERE "+ID+" = '"
                + sessionTopic.getId() + "'", null);
        boolean exist = (cur.getCount() > 0);
        cur.close();
        return exist;

    }

    public SessionTopic getSessionTopicById(String sessionTopicId){
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = ID +" = ?";
        String[] whereArgs = new String[] {
                sessionTopicId,
        };
        Cursor cursor=db.query(TABLE_SESSION_TOPIC, sessionTopicColumns, whereClause,
                whereArgs,null,null,null,null);
        if (!(cursor.moveToFirst()) || cursor.getCount() ==0){
            return null;
        }else{

            SessionTopic sessionTopic = cursorToSessionTopic(cursor);
            cursor.close();
            return sessionTopic;
        }
    }

    public List<SessionTopic> getSessionTopics(){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(TABLE_SESSION_TOPIC,sessionTopicColumns,null,null,
                null,null,null,null);
        List<SessionTopic> sessionTopicList = new ArrayList<>();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
            SessionTopic sessionTopic = cursorToSessionTopic(cursor);
            sessionTopicList.add(sessionTopic);
        }
        cursor.close();
        return sessionTopicList;
    }


    /**
     * ************************************
     *           TRAINING_SESSION         *
     * ************************************
     */

    private TrainingSession cursorToTrainingSession(Cursor cursor){
        TrainingSession trainingSession = new TrainingSession();
        trainingSession.setId(cursor.getString(cursor.getColumnIndex(ID)));
        trainingSession.setTrainingSessionTypeId(cursor.getInt(cursor.getColumnIndex(
                TRAINING_SESSION_TYPE_ID)));
        trainingSession.setClassId(cursor.getInt(cursor.getColumnIndex(CLASS_ID)));
        trainingSession.setTrainingId(cursor.getString(cursor.getColumnIndex(TRAINING_ID)));
        trainingSession.setTrainerId(cursor.getInt(cursor.getColumnIndex(TRAINER_ID)));
        trainingSession.setCountry(cursor.getString(cursor.getColumnIndex(COUNTRY)));
        trainingSession.setArchived(cursor.getInt(cursor.getColumnIndex(ARCHIVED))==1);
        trainingSession.setSessionStartTime(cursor.getLong(cursor.getColumnIndex(SESSION_START_TIME)));
        trainingSession.setSessionEndTime(cursor.getLong(cursor.getColumnIndex(SESSION_END_TIME)));
        trainingSession.setSessionTopicId(cursor.getInt(cursor.getColumnIndex(SESSION_TOPIC_ID)));
        trainingSession.setSessionLeadTrainer(cursor.getInt(cursor.getColumnIndex(SESSION_LEAD_TRAINER)));
        trainingSession.setClientTime(cursor.getLong(cursor.getColumnIndex(CLIENT_TIME)));
        trainingSession.setCreatedBy(cursor.getInt(cursor.getColumnIndex(CREATED_BY)));
        trainingSession.setDateCreated(cursor.getString(cursor.getColumnIndex(DATE_CREATED)));
        trainingSession.setComment(cursor.getString(cursor.getColumnIndex(COMMENT)));
        return trainingSession;
    }

    public long addTrainingSession(TrainingSession trainingSession){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(ID, trainingSession.getId());
        cv.put(TRAINING_SESSION_TYPE_ID, trainingSession.getTrainingSessionTypeId());
        cv.put(CLASS_ID, trainingSession.getClassId());
        cv.put(TRAINING_ID, trainingSession.getTrainingId());
        cv.put(TRAINER_ID, trainingSession.getTrainerId());
        cv.put(COUNTRY, trainingSession.getCountry());
        cv.put(ARCHIVED, trainingSession.isArchived());
        cv.put(SESSION_START_TIME, trainingSession.getSessionStartTime());
        cv.put(SESSION_END_TIME, trainingSession.getSessionEndTime());
        cv.put(SESSION_TOPIC_ID, trainingSession.getSessionTopicId());
        cv.put(SESSION_LEAD_TRAINER, trainingSession.getSessionLeadTrainer());
        cv.put(CLIENT_TIME, trainingSession.getClientTime());
        cv.put(CREATED_BY, trainingSession.getCreatedBy());
        cv.put(DATE_CREATED, trainingSession.getDateCreated());
        cv.put(COMMENT, trainingSession.getComment());
        long id;
        if(trainingSessionExists(trainingSession)){
            id = db.update(TABLE_TRAINING_SESSION, cv, ID+"='"+trainingSession.getId()+"'",
                    null);
        }else{
            id = db.insertWithOnConflict(TABLE_TRAINING_SESSION, null, cv,
                    SQLiteDatabase.CONFLICT_REPLACE);
        }
        db.close();
        return id;
    }

    public boolean trainingSessionExists(TrainingSession trainingSession) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cur = db.rawQuery("SELECT "+ID+" FROM " + TABLE_TRAINING_SESSION + " WHERE "+
                ID+" = '" + trainingSession.getId() + "'", null);
        boolean exist = (cur.getCount() > 0);
        cur.close();
        return exist;

    }

    public TrainingSession getTrainingSessionById(String trainingSessionId){
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = ID +" = ?";
        String[] whereArgs = new String[] {
                trainingSessionId,
        };
        Cursor cursor=db.query(TABLE_TRAINING_SESSION, trainingSessionColumns, whereClause,
                whereArgs,null,null,null,null);
        if (!(cursor.moveToFirst()) || cursor.getCount() ==0){
            return null;
        }else{

            TrainingSession trainingSession = cursorToTrainingSession(cursor);
            cursor.close();
            return trainingSession;
        }
    }

    public List<TrainingSession> getTrainingSessions(){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(TABLE_TRAINING_SESSION,trainingSessionColumns,null,null,
                null,null,null,null);
        List<TrainingSession> trainingSessionList = new ArrayList<>();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
            TrainingSession trainingSession = cursorToTrainingSession(cursor);
            trainingSessionList.add(trainingSession);
        }
        cursor.close();
        return trainingSessionList;
    }

    public List<TrainingSession> getTrainingSessionsByTrainingId(String trainingId){
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = TRAINING_ID +" = ?";
        String[] whereArgs = new String[] {
                trainingId,
        };

        Cursor cursor = db.query(TABLE_TRAINING_SESSION,trainingSessionColumns,whereClause,whereArgs,
                null,null,null,null);
        List<TrainingSession> trainingSessionList = new ArrayList<>();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
            TrainingSession trainingSession = cursorToTrainingSession(cursor);
            trainingSessionList.add(trainingSession);
        }
        cursor.close();
        return trainingSessionList;
    }

    public void trainingSessionFromJson(JSONObject jsonObject){
        TrainingSession trainingSession = new TrainingSession();
        try {
            trainingSession.setId(jsonObject.getString(ID));
            if (!jsonObject.isNull(TRAINING_SESSION_TYPE_ID)){
                trainingSession.setTrainingSessionTypeId(jsonObject.getInt(TRAINING_SESSION_TYPE_ID));
            }
            if (!jsonObject.isNull(CLASS_ID)){
                trainingSession.setClassId(jsonObject.getInt(CLASS_ID));
            }
            if (!jsonObject.isNull(TRAINING_ID)){
                trainingSession.setTrainingId(jsonObject.getString(TRAINING_ID));
            }
            if (!jsonObject.isNull(TRAINER_ID)){
                trainingSession.setTrainerId(jsonObject.getInt(TRAINER_ID));
            }
            if (!jsonObject.isNull(COUNTRY)){
                trainingSession.setCountry(jsonObject.getString(COUNTRY));
            }
            if (!jsonObject.isNull(ARCHIVED)){
                trainingSession.setArchived(jsonObject.getInt(ARCHIVED)==1);
            }
            if (!jsonObject.isNull(SESSION_START_TIME)){
                trainingSession.setSessionStartTime(jsonObject.getLong(SESSION_START_TIME));
            }
            if (!jsonObject.isNull(SESSION_END_TIME)){
                trainingSession.setSessionEndTime(jsonObject.getLong(SESSION_END_TIME));
            }
            if (!jsonObject.isNull(SESSION_TOPIC_ID)){
                trainingSession.setSessionTopicId(jsonObject.getInt(SESSION_TOPIC_ID));
            }
            if (!jsonObject.isNull(SESSION_LEAD_TRAINER)){
                trainingSession.setSessionLeadTrainer(jsonObject.getInt(SESSION_LEAD_TRAINER));
            }
            if (!jsonObject.isNull(CLIENT_TIME)){
                trainingSession.setClientTime(jsonObject.getLong(CLIENT_TIME));
            }
            if (!jsonObject.isNull(CREATED_BY)){
                trainingSession.setCreatedBy(jsonObject.getInt(CREATED_BY));
            }
            if (!jsonObject.isNull(DATE_CREATED)){
                trainingSession.setDateCreated(jsonObject.getString(DATE_CREATED));
            }
            if (!jsonObject.isNull(COMMENT)){
                trainingSession.setComment(jsonObject.getString(COMMENT));
            }
            this.addTrainingSession(trainingSession);
        }catch (Exception e){
            Log.d("Tremap", e.getMessage());
        }
    }

    /**
     * ************************************
     *           TRAINING SESSION TYPE    *
     * ************************************
     */

    private TrainingSessionType cursorToTrainingSessionType(Cursor cursor){
        TrainingSessionType trainingSessionType = new TrainingSessionType();
        trainingSessionType.setId(cursor.getString(cursor.getColumnIndex(ID)));
        trainingSessionType.setSessionName(cursor.getString(cursor.getColumnIndex(SESSION_NAME)));
        trainingSessionType.setClassId(cursor.getInt(cursor.getColumnIndex(CLASS_ID)));
        trainingSessionType.setCountry(cursor.getString(cursor.getColumnIndex(COUNTRY)));
        trainingSessionType.setArchived(cursor.getInt(cursor.getColumnIndex(ARCHIVED))==1);
        trainingSessionType.setClientTime(cursor.getLong(cursor.getColumnIndex(CLIENT_TIME)));
        trainingSessionType.setCreatedBy(cursor.getInt(cursor.getColumnIndex(CREATED_BY)));
        trainingSessionType.setDateCreated(cursor.getString(cursor.getColumnIndex(DATE_CREATED)));
        trainingSessionType.setComment(cursor.getString(cursor.getColumnIndex(COMMENT)));
        return trainingSessionType;
    }

    public long addTrainingSessionType(TrainingSessionType trainingSessionType){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ID, trainingSessionType.getId());
        cv.put(SESSION_NAME, trainingSessionType.getSessionName());
        cv.put(CLASS_ID, trainingSessionType.getClassId());
        cv.put(COUNTRY, trainingSessionType.getCountry());
        cv.put(ARCHIVED, trainingSessionType.isArchived());
        cv.put(CLIENT_TIME, trainingSessionType.getClientTime());
        cv.put(CREATED_BY, trainingSessionType.getCreatedBy());
        cv.put(DATE_CREATED, trainingSessionType.getDateCreated());
        cv.put(COMMENT, trainingSessionType.getComment());
        long id;
        if(trainingSessionTypeExists(trainingSessionType)){
            id = db.update(TABLE_TRAINING_SESSION_TYPE, cv, ID+"='"+trainingSessionType.getId()+"'",
                    null);
        }else{
            id = db.insertWithOnConflict(TABLE_TRAINING_SESSION_TYPE, null, cv,
                    SQLiteDatabase.CONFLICT_REPLACE);
        }
        db.close();
        return id;
    }

    public boolean trainingSessionTypeExists(TrainingSessionType trainingSessionType) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cur = db.rawQuery("SELECT "+ID+" FROM " + TABLE_TRAINING_SESSION_TYPE + " WHERE "+
                ID+" = '" + trainingSessionType.getId() + "'", null);
        boolean exist = (cur.getCount() > 0);
        cur.close();
        return exist;

    }

    public TrainingSessionType getTrainingSessionTypeById(String trainingSessionTypeId){
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = ID +" = ?";
        String[] whereArgs = new String[] {
                trainingSessionTypeId,
        };
        Cursor cursor=db.query(TABLE_TRAINING_SESSION_TYPE, trainingSessionTypeColumns, whereClause,
                whereArgs,null,null,null,null);
        if (!(cursor.moveToFirst()) || cursor.getCount() ==0){
            return null;
        }else{

            TrainingSessionType trainingSessionType = cursorToTrainingSessionType(cursor);
            cursor.close();
            return trainingSessionType;
        }
    }

    public List<TrainingSessionType> getTrainingSessionTypes(){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(TABLE_TRAINING_SESSION_TYPE,trainingSessionTypeColumns,null,null,
                null,null,null,null);
        List<TrainingSessionType> trainingSessionTypeList = new ArrayList<>();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
            TrainingSessionType trainingSessionType = cursorToTrainingSessionType(cursor);
            trainingSessionTypeList.add(trainingSessionType);
        }
        cursor.close();
        return trainingSessionTypeList;
    }

    /**
     * ************************************
     *           TRAINING_STATUS          *
     * ************************************
     */

    private TrainingStatus cursorToTrainingStatus(Cursor cursor){
        TrainingStatus trainingStatus = new TrainingStatus();
        trainingStatus.setId(cursor.getInt(cursor.getColumnIndex(ID)));
        trainingStatus.setName(cursor.getString(cursor.getColumnIndex(NAME)));
        trainingStatus.setArchived(cursor.getInt(cursor.getColumnIndex(ARCHIVED))==1);
        trainingStatus.setReadonly(cursor.getInt(cursor.getColumnIndex(READONLY))==1);
        trainingStatus.setCountry(cursor.getString(cursor.getColumnIndex(COUNTRY)));
        trainingStatus.setClientTime(cursor.getLong(cursor.getColumnIndex(CLIENT_TIME)));
        trainingStatus.setCreatedBy(cursor.getInt(cursor.getColumnIndex(CREATED_BY)));
        trainingStatus.setDateCreated(cursor.getLong(cursor.getColumnIndex(DATE_CREATED)));
        trainingStatus.setComment(cursor.getString(cursor.getColumnIndex(COMMENT)));
        return trainingStatus;
    }

    public long addTrainingStatus(TrainingStatus trainingStatus){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ID, trainingStatus.getId());
        cv.put(NAME, trainingStatus.getName());
        cv.put(ARCHIVED, trainingStatus.isArchived());
        cv.put(READONLY, trainingStatus.isReadonly());
        cv.put(COUNTRY, trainingStatus.getCountry());
        cv.put(CLIENT_TIME, trainingStatus.getClientTime());
        cv.put(CREATED_BY, trainingStatus.getCreatedBy());
        cv.put(DATE_CREATED, trainingStatus.getDateCreated());
        cv.put(COMMENT, trainingStatus.getComment());
        long id;
        if(trainingStatusExists(trainingStatus)){
            id = db.update(TABLE_TRAINING_STATUS, cv, ID+"='"+trainingStatus.getId()+"'",
                    null);
        }else{
            id = db.insertWithOnConflict(TABLE_TRAINING_STATUS, null, cv,
                    SQLiteDatabase.CONFLICT_REPLACE);
        }
        db.close();
        return id;
    }

    public boolean trainingStatusExists(TrainingStatus trainingStatus) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cur = db.rawQuery("SELECT "+ID+" FROM " + TABLE_TRAINING_STATUS + " WHERE "+
                ID+" = '" + trainingStatus.getId() + "'", null);
        boolean exist = (cur.getCount() > 0);
        cur.close();
        return exist;

    }

    public TrainingStatus getTrainingStatusById(String trainingStatusId){
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = ID +" = ?";
        String[] whereArgs = new String[] {
                trainingStatusId,
        };
        Cursor cursor=db.query(TABLE_TRAINING_STATUS, trainingStatusColumns, whereClause,
                whereArgs,null,null,null,null);
        if (!(cursor.moveToFirst()) || cursor.getCount() ==0){
            return null;
        }else{

            TrainingStatus trainingStatus = cursorToTrainingStatus(cursor);
            cursor.close();
            return trainingStatus;
        }
    }

    public List<TrainingStatus> getTrainingStatuss(){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(TABLE_TRAINING_STATUS,trainingStatusColumns,null,null,
                null,null,null,null);
        List<TrainingStatus> trainingStatusList = new ArrayList<>();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
            TrainingStatus trainingStatus = cursorToTrainingStatus(cursor);
            trainingStatusList.add(trainingStatus);
        }
        cursor.close();
        return trainingStatusList;
    }

    /**
     * ************************************
     *           TRAINING ROLES           *
     * ************************************
     */

    private TrainingRole cursorToTrainingRole(Cursor cursor){
        TrainingRole trainingRole = new TrainingRole();
        trainingRole.setId(cursor.getInt(cursor.getColumnIndex(ID)));
        trainingRole.setRoleName(cursor.getString(cursor.getColumnIndex(ROLE_NAME)));
        trainingRole.setArchived(cursor.getInt(cursor.getColumnIndex(ARCHIVED))==1);
        trainingRole.setReadonly(cursor.getInt(cursor.getColumnIndex(READONLY))==1);
        trainingRole.setCountry(cursor.getString(cursor.getColumnIndex(COUNTRY)));
        trainingRole.setClientTime(cursor.getLong(cursor.getColumnIndex(CLIENT_TIME)));
        trainingRole.setCreatedBy(cursor.getInt(cursor.getColumnIndex(CREATED_BY)));
        trainingRole.setDateCreated(cursor.getString(cursor.getColumnIndex(DATE_CREATED)));
        trainingRole.setComment(cursor.getString(cursor.getColumnIndex(COMMENT)));
        return trainingRole;
    }

    public long addTrainingRole(TrainingRole trainingRole){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ID, trainingRole.getId());
        cv.put(ROLE_NAME, trainingRole.getRoleName());
        cv.put(ARCHIVED, trainingRole.isArchived());
        cv.put(READONLY, trainingRole.isReadonly());
        cv.put(COUNTRY, trainingRole.getCountry());
        cv.put(CLIENT_TIME, trainingRole.getClientTime());
        cv.put(CREATED_BY, trainingRole.getCreatedBy());
        cv.put(DATE_CREATED, trainingRole.getDateCreated());
        cv.put(COMMENT, trainingRole.getComment());
        long id;
        if(trainingRoleExists(trainingRole)){
            id = db.update(TABLE_TRAINING_ROLES, cv, ID+"='"+trainingRole.getId()+"'",
                    null);
        }else{
            id = db.insertWithOnConflict(TABLE_TRAINING_ROLES, null, cv,
                    SQLiteDatabase.CONFLICT_REPLACE);
        }
        db.close();
        return id;
    }

    public boolean trainingRoleExists(TrainingRole trainingRole) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cur = db.rawQuery("SELECT "+ID+" FROM " + TABLE_TRAINING_ROLES + " WHERE "+
                ID+" = '" + trainingRole.getId() + "'", null);
        boolean exist = (cur.getCount() > 0);
        cur.close();
        return exist;

    }

    public TrainingRole getTrainingRoleById(String trainingRoleId){
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = ID +" = ?";
        String[] whereArgs = new String[] {
                trainingRoleId,
        };
        Cursor cursor=db.query(TABLE_TRAINING_ROLES, trainingRoleColumns, whereClause,
                whereArgs,null,null,null,null);
        if (!(cursor.moveToFirst()) || cursor.getCount() ==0){
            return null;
        }else{

            TrainingRole trainingRole = cursorToTrainingRole(cursor);
            cursor.close();
            return trainingRole;
        }
    }

    public List<TrainingRole> getTrainingRoles(){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(TABLE_TRAINING_ROLES,trainingRoleColumns,null,null,
                null,null,null,null);
        List<TrainingRole> trainingRoleList = new ArrayList<>();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
            TrainingRole trainingRole = cursorToTrainingRole(cursor);
            trainingRoleList.add(trainingRole);
        }
        cursor.close();
        return trainingRoleList;
    }

    /**
     * ************************************
     *         TRAINING TRAINER           *
     * ************************************
     */

    private TrainingTrainer cursorToTrainingTrainer(Cursor cursor){
        TrainingTrainer trainingTrainer = new TrainingTrainer();
        trainingTrainer.setId(cursor.getInt(cursor.getColumnIndex(ID)));
        trainingTrainer.setTrainingId(cursor.getString(cursor.getColumnIndex(TRAINING_ID)));
        trainingTrainer.setClassId(cursor.getInt(cursor.getColumnIndex(CLASS_ID)));
        trainingTrainer.setTrainerId(cursor.getInt(cursor.getColumnIndex(TRAINER_ID)));
        trainingTrainer.setCountry(cursor.getString(cursor.getColumnIndex(COUNTRY)));
        trainingTrainer.setClientTime(cursor.getLong(cursor.getColumnIndex(CLIENT_TIME)));
        trainingTrainer.setCreatedBy(cursor.getInt(cursor.getColumnIndex(CREATED_BY)));
        trainingTrainer.setDateCreated(cursor.getString(cursor.getColumnIndex(DATE_CREATED)));
        trainingTrainer.setArchived(cursor.getInt(cursor.getColumnIndex(ARCHIVED))==1);
        trainingTrainer.setTrainingRoleId(cursor.getInt(cursor.getColumnIndex(TRAINING_ROLE_ID)));
        trainingTrainer.setComment(cursor.getString(cursor.getColumnIndex(COMMENT)));
        return trainingTrainer;
    }

    public long addTrainingTrainer(TrainingTrainer trainingTrainer){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ID, trainingTrainer.getId());
        cv.put(TRAINING_ID, trainingTrainer.getTrainingId());
        cv.put(CLASS_ID, trainingTrainer.getClassId());
        cv.put(TRAINER_ID, trainingTrainer.getTrainerId());
        cv.put(COUNTRY, trainingTrainer.getCountry());
        cv.put(CLIENT_TIME, trainingTrainer.getClientTime());
        cv.put(CREATED_BY, trainingTrainer.getCreatedBy());
        cv.put(DATE_CREATED, trainingTrainer.getDateCreated());
        cv.put(ARCHIVED, trainingTrainer.isArchived());
        cv.put(TRAINING_ROLE_ID, trainingTrainer.getTrainingRoleId());
        cv.put(COMMENT, trainingTrainer.getComment());
        long id;
        if(trainingTrainerExists(trainingTrainer)){
            id = db.update(TABLE_TRAINING_TRAINERS, cv, ID+"='"+trainingTrainer.getId()+"'",
                    null);
        }else{
            id = db.insertWithOnConflict(TABLE_TRAINING_TRAINERS, null, cv,
                    SQLiteDatabase.CONFLICT_REPLACE);
        }
        db.close();
        return id;
    }

    public boolean trainingTrainerExists(TrainingTrainer trainingTrainer) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cur = db.rawQuery("SELECT "+ID+" FROM " + TABLE_TRAINING_TRAINERS + " WHERE "+
                ID+" = '" + trainingTrainer.getId() + "'", null);
        boolean exist = (cur.getCount() > 0);
        cur.close();
        return exist;

    }

    public TrainingTrainer getTrainingTrainerById(String trainingTrainerId){
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = ID +" = ?";
        String[] whereArgs = new String[] {
                trainingTrainerId,
        };
        Cursor cursor=db.query(TABLE_TRAINING_TRAINERS, trainingTrainerColumns, whereClause,
                whereArgs,null,null,null,null);
        if (!(cursor.moveToFirst()) || cursor.getCount() ==0){
            return null;
        }else{

            TrainingTrainer trainingTrainer = cursorToTrainingTrainer(cursor);
            cursor.close();
            return trainingTrainer;
        }
    }

    public List<TrainingTrainer> getTrainingTrainers(){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(TABLE_TRAINING_TRAINERS,trainingTrainerColumns,null,null,
                null,null,null,null);
        List<TrainingTrainer> trainingTrainerList = new ArrayList<>();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
            TrainingTrainer trainingTrainer = cursorToTrainingTrainer(cursor);
            trainingTrainerList.add(trainingTrainer);
        }
        cursor.close();
        return trainingTrainerList;
    }

    /**
     * ************************************
     *         TRAINING CLASSES           *
     * ************************************
     */

    private TrainingClass cursorToTrainingClass(Cursor cursor){
        TrainingClass trainingClass = new TrainingClass();
        trainingClass.setId(cursor.getInt(cursor.getColumnIndex(ID)));
        trainingClass.setTrainingId(cursor.getString(cursor.getColumnIndex(TRAINING_ID)));
        trainingClass.setClassName(cursor.getString(cursor.getColumnIndex(CLASS_NAME)));
        trainingClass.setCountry(cursor.getString(cursor.getColumnIndex(COUNTRY)));
        trainingClass.setClientTime(cursor.getLong(cursor.getColumnIndex(CLIENT_TIME)));
        trainingClass.setCreatedBy(cursor.getInt(cursor.getColumnIndex(CREATED_BY)));
        trainingClass.setDateCreated(cursor.getString(cursor.getColumnIndex(DATE_CREATED)));
        trainingClass.setArchived(cursor.getInt(cursor.getColumnIndex(ARCHIVED))==1);
        trainingClass.setComment(cursor.getString(cursor.getColumnIndex(COMMENT)));
        return trainingClass;
    }

    public void trainingClassFromJson(JSONObject jsonObject){
        TrainingClass trainingClass = new TrainingClass();
        try {
            trainingClass.setId(jsonObject.getInt(ID));
            if (!jsonObject.isNull(TRAINING_ID)){
                trainingClass.setTrainingId(jsonObject.getString(TRAINING_ID));
            }
            if (!jsonObject.isNull(CLASS_NAME)){
                trainingClass.setClassName(jsonObject.getString(CLASS_NAME));
            }
            if (!jsonObject.isNull(COUNTRY)){
                trainingClass.setCountry(jsonObject.getString(COUNTRY));
            }
            if (!jsonObject.isNull(CLIENT_TIME)){
                trainingClass.setClientTime(jsonObject.getLong(CLIENT_TIME));
            }
            if (!jsonObject.isNull(CREATED_BY)){
                trainingClass.setCreatedBy(jsonObject.getInt(CREATED_BY));
            }
            if (!jsonObject.isNull(DATE_CREATED)){
                trainingClass.setDateCreated(jsonObject.getString(DATE_CREATED));
            }
            if (!jsonObject.isNull(ARCHIVED)){
                trainingClass.setArchived(jsonObject.getInt(ARCHIVED)==1);
            }
            if (!jsonObject.isNull(COMMENT)){
                trainingClass.setComment(jsonObject.getString(COMMENT));
            }
            Log.d("Tremap", "=======Creating Training Class from JSON============");
            Log.d("Tremap", "=======Creating Training Class from JSON ============"+trainingClass.getTrainingId());
            this.addTrainingClass(trainingClass);
        }catch (Exception e){
            Log.d("Tremap", "=======ERR Creating Training Class ============");
            Log.d("Tremap", e.getMessage());
            Log.d("Tremap", "===================");
        }
    }


    public long addTrainingClass(TrainingClass trainingClass){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ID, trainingClass.getId());
        cv.put(TRAINING_ID, trainingClass.getTrainingId());
        cv.put(CLASS_NAME, trainingClass.getClassName());
        cv.put(COUNTRY, trainingClass.getCountry());
        cv.put(CLIENT_TIME, trainingClass.getClientTime());
        cv.put(CREATED_BY, trainingClass.getCreatedBy());
        cv.put(DATE_CREATED, trainingClass.getDateCreated());
        cv.put(ARCHIVED, trainingClass.isArchived());
        cv.put(COMMENT, trainingClass.getComment());

        long id;
        if(trainingClassExists(trainingClass)){
            id = db.update(TABLE_TRAINING_CLASSES, cv, ID+"='"+trainingClass.getId()+"'",
                    null);
            Log.d("Tremap", "======================------------==========");
            Log.d("Tremap", "Updated Class Details");
            Log.d("Tremap", "======================------------==========");
        }else{
            id = db.insertWithOnConflict(TABLE_TRAINING_CLASSES, null, cv,
                    SQLiteDatabase.CONFLICT_REPLACE);
            Log.d("Tremap", "======================------------==========");
            Log.d("Tremap", "Created Class Details");
            Log.d("Tremap", "======================------------==========");
        }
        db.close();
        return id;
    }

    public boolean trainingClassExists(TrainingClass trainingClass) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cur = db.rawQuery("SELECT "+ID+" FROM " + TABLE_TRAINING_CLASSES + " WHERE "+
                ID+" = '" + trainingClass.getId() + "'", null);
        boolean exist = (cur.getCount() > 0);
        cur.close();
        return exist;

    }

    public TrainingClass getTrainingClassById(String trainingClassId){
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = ID +" = ?";
        String[] whereArgs = new String[] {
                trainingClassId,
        };
        Cursor cursor=db.query(TABLE_TRAINING_CLASSES, trainingClassesColumns, whereClause,
                whereArgs,null,null,null,null);
        if (!(cursor.moveToFirst()) || cursor.getCount() ==0){
            return null;
        }else{

            TrainingClass trainingClass = cursorToTrainingClass(cursor);
            cursor.close();
            return trainingClass;
        }
    }

    public List<TrainingClass> getTrainingClassByTrainingId(String trainingId){
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = TRAINING_ID +" = ?";
        String[] whereArgs = new String[] {
                trainingId,
        };
        Cursor cursor=db.query(TABLE_TRAINING_CLASSES, trainingClassesColumns, whereClause,
                whereArgs,null,null,null,null);
        List<TrainingClass> trainingClassList = new ArrayList<>();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
            trainingClassList.add(cursorToTrainingClass(cursor));
        }
        cursor.close();
        return trainingClassList;
    }

    public List<TrainingClass> getTrainingClasss(){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(TABLE_TRAINING_CLASSES,trainingClassesColumns,null,null,
                null,null,null,null);
        List<TrainingClass> trainingClassList = new ArrayList<>();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
            TrainingClass trainingClass = cursorToTrainingClass(cursor);
            trainingClassList.add(trainingClass);
        }
        cursor.close();
        return trainingClassList;
    }

    /**
     * ************************************
     *         TRAINING TRAINEES          *
     * ************************************
     */

    private TrainingTrainee cursorToTrainingTrainee(Cursor cursor){
        TrainingTrainee trainingTrainee = new TrainingTrainee();
        trainingTrainee.setId(cursor.getString(cursor.getColumnIndex(ID)));
        trainingTrainee.setRegistrationId(cursor.getString(cursor.getColumnIndex(REGISTRATION_ID)));
        trainingTrainee.setClassId(cursor.getInt(cursor.getColumnIndex(CLASS_ID)));
        trainingTrainee.setTrainingId(cursor.getString(cursor.getColumnIndex(TRAINING_ID)));
        trainingTrainee.setCountry(cursor.getString(cursor.getColumnIndex(COUNTRY)));
        trainingTrainee.setAddedBy(cursor.getInt(cursor.getColumnIndex(ADDED_BY)));
        trainingTrainee.setDateCreated(cursor.getString(cursor.getColumnIndex(DATE_CREATED)));
        trainingTrainee.setClientTime(cursor.getLong(cursor.getColumnIndex(CLIENT_TIME)));
        trainingTrainee.setBranch(cursor.getString(cursor.getColumnIndex(BRANCH)));
        trainingTrainee.setCohort(cursor.getInt(cursor.getColumnIndex(COHORT)));
        trainingTrainee.setChpCode(cursor.getString(cursor.getColumnIndex(CHP_CODE)));
        trainingTrainee.setArchived(cursor.getInt(cursor.getColumnIndex(ARCHIVED))==1);
        trainingTrainee.setTraineeStatus(cursor.getInt(cursor.getColumnIndex(TRAINEE_STATUS)));
        try{
            trainingTrainee.setRegistration(new JSONObject(cursor.getString(cursor.getColumnIndex(REGISTRATION))));
        }catch (Exception e){}

        trainingTrainee.setComment(cursor.getString(cursor.getColumnIndex(COMMENT)));
        return trainingTrainee;
    }

    public long addTrainingTrainee(TrainingTrainee trainingTrainee){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ID, trainingTrainee.getId());
        cv.put(REGISTRATION_ID, trainingTrainee.getRegistrationId());
        cv.put(CLASS_ID, trainingTrainee.getClassId());
        cv.put(TRAINING_ID, trainingTrainee.getTrainingId());
        cv.put(COUNTRY, trainingTrainee.getCountry());
        cv.put(ADDED_BY, trainingTrainee.getAddedBy());
        cv.put(DATE_CREATED, trainingTrainee.getDateCreated());
        cv.put(CLIENT_TIME, trainingTrainee.getClientTime());
        cv.put(BRANCH, trainingTrainee.getBranch());
        cv.put(COHORT, trainingTrainee.getCohort());
        cv.put(CHP_CODE, trainingTrainee.getChpCode());
        cv.put(ARCHIVED, trainingTrainee.isArchived());
        cv.put(REGISTRATION, trainingTrainee.getRegistration().toString());
        cv.put(COMMENT, trainingTrainee.getComment());
        cv.put(TRAINEE_STATUS, trainingTrainee.getTraineeStatus());
        long id;
        if(trainingTraineeExists(trainingTrainee)){
            id = db.update(TABLE_TRAINING_TRAINEES, cv, ID+"='"+trainingTrainee.getId()+"'",
                    null);
            Log.d("Tremap", "Updated Trainee "+trainingTrainee.getId());
        }else{
            id = db.insertWithOnConflict(TABLE_TRAINING_TRAINEES, null, cv,
                    SQLiteDatabase.CONFLICT_REPLACE);
            Log.d("Tremap", "Created Trainee "+trainingTrainee.getId());
        }
        db.close();
        return id;
    }

    public boolean trainingTraineeExists(TrainingTrainee trainingTrainee) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cur = db.rawQuery("SELECT "+ID+" FROM " + TABLE_TRAINING_TRAINEES + " WHERE "+
                ID+" = '" + trainingTrainee.getId() + "'", null);
        boolean exist = (cur.getCount() > 0);
        cur.close();
        return exist;

    }

    public TrainingTrainee getTrainingTraineeById(String trainingTraineeId){
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = ID +" = ?";
        String[] whereArgs = new String[] {
                trainingTraineeId,
        };
        Cursor cursor=db.query(TABLE_TRAINING_TRAINEES, trainingTraineeColumns, whereClause,
                whereArgs,null,null,null,null);
        if (!(cursor.moveToFirst()) || cursor.getCount() ==0){
            return null;
        }else{

            TrainingTrainee trainingTrainee = cursorToTrainingTrainee(cursor);
            cursor.close();
            return trainingTrainee;
        }
    }

    public List<TrainingTrainee> getTrainingTraineesByClassId(String classId){
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = CLASS_ID +" = ?";
        String[] whereArgs = new String[] {
                classId,
        };
        Cursor cursor=db.query(TABLE_TRAINING_TRAINEES, trainingTraineeColumns, whereClause,
                whereArgs,null,null,null,null);
        List<TrainingTrainee> trainingTraineeList = new ArrayList<>();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
            TrainingTrainee trainingTrainee = cursorToTrainingTrainee(cursor);
            trainingTraineeList.add(trainingTrainee);
        }
        cursor.close();
        return trainingTraineeList;
    }

    public List<TrainingTrainee> getTrainingTraineesByTrainingId(String trainingId){
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = TRAINING_ID +" = ?";
        String[] whereArgs = new String[] {
                trainingId,
        };
        Cursor cursor=db.query(TABLE_TRAINING_TRAINEES, trainingTraineeColumns, whereClause,
                whereArgs,null,null,null,null);
        List<TrainingTrainee> trainingTraineeList = new ArrayList<>();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
            TrainingTrainee trainingTrainee = cursorToTrainingTrainee(cursor);
            trainingTraineeList.add(trainingTrainee);
        }
        cursor.close();
        return trainingTraineeList;
    }

    public List<TrainingTrainee> getTrainingTrainees(){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(TABLE_TRAINING_TRAINEES,trainingTraineeColumns,null,null,
                null,null,null,null);
        List<TrainingTrainee> trainingTraineeList = new ArrayList<>();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
            TrainingTrainee trainingTrainee = cursorToTrainingTrainee(cursor);
            trainingTraineeList.add(trainingTrainee);
        }
        cursor.close();
        return trainingTraineeList;
    }

    public void trainingTraineeFromJson(JSONObject jsonObject){
        TrainingTrainee trainingTrainee = new TrainingTrainee();
        try {
            trainingTrainee.setId(jsonObject.getString(ID));
            if (!jsonObject.isNull(REGISTRATION_ID)){
                trainingTrainee.setRegistrationId(jsonObject.getString(REGISTRATION_ID));
            }
            if (!jsonObject.isNull(CLASS_ID)){
                trainingTrainee.setClassId(jsonObject.getInt(CLASS_ID));
            }
            if (!jsonObject.isNull(TRAINING_ID)){
                trainingTrainee.setTrainingId(jsonObject.getString(TRAINING_ID));
            }
            if (!jsonObject.isNull(COUNTRY)){
                trainingTrainee.setCountry(jsonObject.getString(COUNTRY));
            }
            if (!jsonObject.isNull(ADDED_BY)){
                trainingTrainee.setAddedBy(jsonObject.getInt(ADDED_BY));
            }
            if (!jsonObject.isNull(DATE_CREATED)){
                trainingTrainee.setDateCreated(jsonObject.getString(DATE_CREATED));
            }
            if (!jsonObject.isNull(CLIENT_TIME)){
                trainingTrainee.setClientTime(jsonObject.getLong(CLIENT_TIME));
            }
            if (!jsonObject.isNull(BRANCH)){
                trainingTrainee.setBranch(jsonObject.getString(BRANCH));
            }
            if (!jsonObject.isNull(COHORT)){
                trainingTrainee.setCohort(jsonObject.getInt(COHORT));
            }
            if (!jsonObject.isNull(CHP_CODE)){
                trainingTrainee.setChpCode(jsonObject.getString(CHP_CODE));
            }
            if (!jsonObject.isNull(ARCHIVED)){
                trainingTrainee.setArchived(jsonObject.getInt(ARCHIVED)==1);
            }
            if (!jsonObject.isNull(REGISTRATION)){
                trainingTrainee.setRegistration(jsonObject.getJSONObject(REGISTRATION));
            }
            if (!jsonObject.isNull(COMMENT)){
                trainingTrainee.setComment(jsonObject.getString(COMMENT));
            }

            if (!jsonObject.isNull(TRAINEE_STATUS)){
                trainingTrainee.setTraineeStatus(jsonObject.getInt(TRAINEE_STATUS));
            }
            this.addTrainingTrainee(trainingTrainee);
        }catch (Exception e){
            Log.d("Tremap", "=======ERR Creating Training============");
            Log.d("Tremap", e.getMessage());
        }
    }

    /**
     * ************************************
     *         TRAINING COMMENTS          *
     * ************************************
     */

    private TrainingComment cursorToTrainingComment(Cursor cursor){
        TrainingComment trainingComment = new TrainingComment();
        trainingComment.setId(cursor.getString(cursor.getColumnIndex(ID)));
        trainingComment.setTraineeId(cursor.getString(cursor.getColumnIndex(TRAINEE_ID)));
        trainingComment.setTrainingId(cursor.getString(cursor.getColumnIndex(TRAINING_ID)));
        trainingComment.setCountry(cursor.getString(cursor.getColumnIndex(COUNTRY)));
        trainingComment.setAddedBy(cursor.getInt(cursor.getColumnIndex(ADDED_BY)));
        trainingComment.setDateCreated(cursor.getString(cursor.getColumnIndex(DATE_CREATED)));
        trainingComment.setClientTime(cursor.getLong(cursor.getColumnIndex(CLIENT_TIME)));
        trainingComment.setArchived(cursor.getInt(cursor.getColumnIndex(ARCHIVED))==1);
        trainingComment.setComment(cursor.getString(cursor.getColumnIndex(COMMENT)));

        return trainingComment;
    }

    public long addTrainingComment(TrainingComment trainingComment){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ID, trainingComment.getId());
        cv.put(TRAINEE_ID, trainingComment.getTraineeId());
        cv.put(TRAINING_ID, trainingComment.getTrainingId());
        cv.put(COUNTRY, trainingComment.getCountry());
        cv.put(ADDED_BY, trainingComment.getAddedBy());
        cv.put(DATE_CREATED, trainingComment.getDateCreated());
        cv.put(CLIENT_TIME, trainingComment.getClientTime());
        cv.put(ARCHIVED, trainingComment.isArchived());
        cv.put(COMMENT, trainingComment.getComment());
        long id;
        if(trainingCommentExists(trainingComment)){
            id = db.update(TABLE_TRAINING_COMMENTS, cv, ID+"='"+trainingComment.getId()+"'",
                    null);
        }else{
            id = db.insertWithOnConflict(TABLE_TRAINING_COMMENTS, null, cv,
                    SQLiteDatabase.CONFLICT_REPLACE);
        }
        db.close();
        return id;
    }

    public boolean trainingCommentExists(TrainingComment trainingComment) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cur = db.rawQuery("SELECT "+ID+" FROM " + TABLE_TRAINING_COMMENTS + " WHERE "+
                ID+" = '" + trainingComment.getId() + "'", null);
        boolean exist = (cur.getCount() > 0);
        cur.close();
        return exist;

    }

    public TrainingComment getTrainingCommentById(String trainingCommentId){
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = ID +" = ?";
        String[] whereArgs = new String[] {
                trainingCommentId,
        };
        Cursor cursor=db.query(TABLE_TRAINING_COMMENTS, trainingCommentColumns, whereClause,
                whereArgs,null,null,null,null);
        if (!(cursor.moveToFirst()) || cursor.getCount() ==0){
            return null;
        }else{

            TrainingComment trainingComment = cursorToTrainingComment(cursor);
            cursor.close();
            return trainingComment;
        }
    }

    public List<TrainingComment> getTrainingComments(){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(TABLE_TRAINING_COMMENTS,trainingCommentColumns,null,null,
                null,null,null,null);
        List<TrainingComment> trainingCommentList = new ArrayList<>();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
            TrainingComment trainingComment = cursorToTrainingComment(cursor);
            trainingCommentList.add(trainingComment);
        }
        cursor.close();
        return trainingCommentList;
    }

    /**
     * ************************************
     *         TRAINING COMMENTS          *
     * ************************************
     */

    private User cursorToUser(Cursor cursor){
        User user = new User();
        user.setId(cursor.getInt(cursor.getColumnIndex(ID)));
        user.setEmail(cursor.getString(cursor.getColumnIndex(EMAIL)));
        user.setUserName(cursor.getString(cursor.getColumnIndex(USERNAME)));
        user.setPassWord(cursor.getString(cursor.getColumnIndex(PASSWORD)));
        user.setName(cursor.getString(cursor.getColumnIndex(NAME)));
        user.setCountry(cursor.getString(cursor.getColumnIndex(COUNTRY)));
        return user;
    }

    public long addUser(User user){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ID, user.getId());
        cv.put(EMAIL, user.getEmail());
        cv.put(USERNAME, user.getUserName());
        cv.put(PASSWORD, user.getPassWord());
        cv.put(NAME, user.getName());
        cv.put(COUNTRY, user.getCountry());
        long id;
        if(userExists(user)){
            id = db.update(TABLE_USERS, cv, ID+"='"+user.getId()+"'",
                    null);
        }else{
            id = db.insertWithOnConflict(TABLE_USERS, null, cv,
                    SQLiteDatabase.CONFLICT_REPLACE);
        }
        db.close();
        return id;
    }

    public boolean userExists(User user) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cur = db.rawQuery("SELECT "+ID+" FROM " + TABLE_USERS + " WHERE "+
                ID+" = '" + user.getId() + "'", null);
        boolean exist = (cur.getCount() > 0);
        cur.close();
        return exist;

    }

    public User getUserById(String userId){
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = ID +" = ?";
        String[] whereArgs = new String[] {
                userId,
        };
        Cursor cursor=db.query(TABLE_USERS, userColumns, whereClause,
                whereArgs,null,null,null,null);
        if (!(cursor.moveToFirst()) || cursor.getCount() ==0){
            return null;
        }else{

            User user = cursorToUser(cursor);
            cursor.close();
            return user;
        }
    }

    public List<User> getUsers(){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(TABLE_USERS,userColumns,null,null,
                null,null,null,null);
        List<User> userList = new ArrayList<>();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
            User user = cursorToUser(cursor);
            userList.add(user);
        }
        cursor.close();
        return userList;
    }

    public void usersFromJson(JSONObject jsonObject){
        User user = new User();
        try {
            user.setId(jsonObject.getInt(ID));
            user.setEmail(jsonObject.getString(EMAIL));
            user.setUserName(jsonObject.getString(USERNAME));
            byte[] appName = Base64.decode(jsonObject.getString("app_name"),
                    Base64.DEFAULT);
            String pwd = null;
            try{
                pwd = new String(appName, "UTF-8");
            } catch (Exception e){}
            user.setPassWord(pwd);
            user.setName(jsonObject.getString(NAME));
            user.setCountry(jsonObject.getString(COUNTRY));
            this.addUser(user);
        }catch (Exception e){}
    }

    public JSONObject userJson(){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(TABLE_USERS,userColumns,null,null,
                null,null,null,null);
        return cursorToJson(cursor, "users");
    }

    /**
     * ************************************
     *         BRANCH                     *
     * ************************************
     */

    private Branch cursorToBranch(Cursor cursor){
        Branch branch = new Branch();
        branch.setId(cursor.getString(cursor.getColumnIndex(ID)));
        branch.setBranchName(cursor.getString(cursor.getColumnIndex(BRANCH_NAME)));
        branch.setBranchCode(cursor.getString(cursor.getColumnIndex(BRANCH_CODE)));
        branch.setMappingId(cursor.getString(cursor.getColumnIndex(MAPPING_ID)));
        branch.setLat(cursor.getDouble(cursor.getColumnIndex(LAT)));
        branch.setLon(cursor.getDouble(cursor.getColumnIndex(LON)));
        branch.setArchived(cursor.getInt(cursor.getColumnIndex(ARCHIVED))==1);
        return branch;
    }

    public long addBranch(Branch branch){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(ID, branch.getId());
        cv.put(BRANCH_NAME, branch.getBranchName());
        cv.put(BRANCH_CODE, branch.getBranchCode());
        cv.put(MAPPING_ID, branch.getMappingId());
        cv.put(LAT, branch.getLat());
        cv.put(LON, branch.getLon());
        cv.put(ARCHIVED, branch.isArchived());
        long id;
        if(branchExists(branch)){
            id = db.update(TABLE_BRANCH, cv, ID+"='"+branch.getId()+"'",
                    null);
        }else{
            id = db.insertWithOnConflict(TABLE_BRANCH, null, cv,
                    SQLiteDatabase.CONFLICT_REPLACE);
        }
        db.close();
        return id;
    }

    public boolean branchExists(Branch branch) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cur = db.rawQuery("SELECT "+ID+" FROM " + TABLE_BRANCH + " WHERE "+
                ID+" = '" + branch.getId() + "'", null);
        boolean exist = (cur.getCount() > 0);
        cur.close();
        return exist;

    }

    public Branch getBranchById(String branchId){
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = ID +" = ?";
        String[] whereArgs = new String[] {
                branchId,
        };
        Cursor cursor=db.query(TABLE_BRANCH, branchColumns, whereClause,
                whereArgs,null,null,null,null);
        if (!(cursor.moveToFirst()) || cursor.getCount() ==0){
            return null;
        }else{

            Branch branch = cursorToBranch(cursor);
            cursor.close();
            return branch;
        }
    }

    public List<Branch> getBranches(){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(TABLE_BRANCH,branchColumns,null,null,
                null,null,null,null);
        List<Branch> branchList = new ArrayList<>();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
            Branch branch = cursorToBranch(cursor);
            branchList.add(branch);
        }
        cursor.close();
        return branchList;
    }


    /**
     * ************************************
     *         COHORT                     *
     * ************************************
     */

    private Cohort cursorToCohort(Cursor cursor){
        Cohort cohort = new Cohort();
        cohort.setId(cursor.getInt(cursor.getColumnIndex(ID)));
        cohort.setCohortName(cursor.getString(cursor.getColumnIndex(COHORT_NAME)));
        cohort.setCohortNumber(cursor.getString(cursor.getColumnIndex(COHORT_NUMBER)));
        cohort.setBranchId(cursor.getInt(cursor.getColumnIndex(BRANCH_ID)));
        cohort.setArchived(cursor.getInt(cursor.getColumnIndex(ARCHIVED))==1);
        return cohort;
    }

    public long addCohort(Cohort cohort){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(ID, cohort.getId());
        cv.put(COHORT_NAME, cohort.getCohortName());
        cv.put(COHORT_NUMBER, cohort.getCohortNumber());
        cv.put(BRANCH_ID, cohort.getBranchId());
        cv.put(ARCHIVED, cohort.isArchived());
        long id;
        if(cohortExists(cohort)){
            id = db.update(TABLE_COHORT, cv, ID+"='"+cohort.getId()+"'",
                    null);
        }else{
            id = db.insertWithOnConflict(TABLE_COHORT, null, cv,
                    SQLiteDatabase.CONFLICT_REPLACE);
        }
        db.close();
        return id;
    }

    public boolean cohortExists(Cohort cohort) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cur = db.rawQuery("SELECT "+ID+" FROM " + TABLE_COHORT + " WHERE "+
                ID+" = '" + cohort.getId() + "'", null);
        boolean exist = (cur.getCount() > 0);
        cur.close();
        return exist;

    }

    public Cohort getCohortById(String cohortId){
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = ID +" = ?";
        String[] whereArgs = new String[] {
                cohortId,
        };
        Cursor cursor=db.query(TABLE_COHORT, cohortColumns, whereClause,
                whereArgs,null,null,null,null);
        if (!(cursor.moveToFirst()) || cursor.getCount() ==0){
            return null;
        }else{

            Cohort cohort = cursorToCohort(cursor);
            cursor.close();
            return cohort;
        }
    }

    public List<Cohort> getCohorts(){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(TABLE_COHORT,cohortColumns,null,null,
                null,null,null,null);
        List<Cohort> cohortList = new ArrayList<>();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
            Cohort cohort = cursorToCohort(cursor);
            cohortList.add(cohort);
        }
        cursor.close();
        return cohortList;
    }


    public JSONObject cursorToJson(Cursor cursor, String jsonRoot){
        JSONObject results = new JSONObject();
        JSONArray resultSet = new JSONArray();
        for (cursor.moveToFirst(); !cursor.isAfterLast();cursor.moveToNext()){
            int totalColumns = cursor.getColumnCount();
            JSONObject rowObject = new JSONObject();
            for (int i =0; i < totalColumns; i++){
                if (cursor.getColumnName(i) != null){
                    try {
                        if (cursor.getString(i) != null){
                            rowObject.put(cursor.getColumnName(i), cursor.getString(i));
                        }else{
                            rowObject.put(cursor.getColumnName(i), "");
                        }
                    }catch (Exception e){
                    }
                }
            }
            resultSet.put(rowObject);
            try {
                results.put(jsonRoot, resultSet);
            } catch (JSONException e) {}
        }
        return results;
    }


    /**
     * *********************************************************************************************
     *                                                                                             *
     * Trainee Status                                                                              *
     * Helps to indicate the status of the trainee                                                 *
     *                                                                                             *
     * *********************************************************************************************
     */

    private TraineeStatus cursorToTraineeStatus(Cursor cursor){
        TraineeStatus status = new TraineeStatus();
        status.setId(cursor.getInt(cursor.getColumnIndex(ID)));
        status.setArchived(cursor.getInt(cursor.getColumnIndex(ARCHIVED))==1);
        status.setReadonly(cursor.getInt(cursor.getColumnIndex(READONLY))==1);
        status.setName(cursor.getString(cursor.getColumnIndex(NAME)));
        status.setCountry(cursor.getString(cursor.getColumnIndex(COUNTRY)));
        status.setDateCreated(cursor.getString(cursor.getColumnIndex(DATE_CREATED)));
        status.setClientTime(cursor.getLong(cursor.getColumnIndex(CLIENT_TIME)));
        status.setCreatedBy(cursor.getInt(cursor.getColumnIndex(CREATED_BY)));
        return status;
    }

    public long addTraineeStatus(TraineeStatus status){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ID, status.getId());
        cv.put(ARCHIVED, status.isArchived());
        cv.put(READONLY, status.isReadonly());
        cv.put(NAME, status.getName());
        cv.put(COUNTRY, status.getCountry());
        cv.put(DATE_CREATED, status.getDateCreated());
        cv.put(CLIENT_TIME, status.getClientTime());
        cv.put(CREATED_BY, status.getCreatedBy());
        long id;
        if(traineeStatusExists(status)){
            id = db.update(TABLE_TRAINEE_STATUS, cv, ID+"='"+status.getId()+"'",
                    null);
        }else{
            id = db.insertWithOnConflict(TABLE_TRAINEE_STATUS, null, cv,
                    SQLiteDatabase.CONFLICT_REPLACE);
        }
        db.close();
        return id;
    }
    public boolean traineeStatusExists(TraineeStatus status) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cur = db.rawQuery("SELECT "+ID+" FROM " + TABLE_TRAINEE_STATUS + " WHERE "+
                ID+" = '" + status.getId() + "'", null);
        boolean exist = (cur.getCount() > 0);
        cur.close();
        return exist;

    }

    public TraineeStatus getTraineeStatusById(String traineeStatusId){
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = ID +" = ?";
        String[] whereArgs = new String[] {
                traineeStatusId,
        };
        Cursor cursor=db.query(TABLE_TRAINEE_STATUS, traineeStatusColumns, whereClause,
                whereArgs,null,null,null,null);
        if (!(cursor.moveToFirst()) || cursor.getCount() ==0){
            return null;
        }else{

            TraineeStatus status = cursorToTraineeStatus(cursor);
            cursor.close();
            return status;
        }
    }

    public List<TraineeStatus> getTraineeStatuses(){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(TABLE_TRAINEE_STATUS, traineeStatusColumns,null,null,
                null,null,null,null);
        List<TraineeStatus> traineeStatusList = new ArrayList<>();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
            TraineeStatus status = cursorToTraineeStatus(cursor);
            traineeStatusList.add(status);
        }
        cursor.close();
        return traineeStatusList;
    }

    public void traineeStatusFromJson(JSONObject jsonObject){
        TraineeStatus traineeStatus = new TraineeStatus();
        try {
            traineeStatus.setId(jsonObject.getInt(ID));
            if (!jsonObject.isNull(NAME)){
                traineeStatus.setName(jsonObject.getString(NAME));
            }
            if (!jsonObject.isNull(ARCHIVED)){
                traineeStatus.setArchived(jsonObject.getBoolean(ARCHIVED));
            }
            if (!jsonObject.isNull(READONLY)){
                traineeStatus.setReadonly(jsonObject.getBoolean(READONLY));
            }
            if (!jsonObject.isNull(COUNTRY)){
                traineeStatus.setCountry(jsonObject.getString(COUNTRY));
            }
            if (!jsonObject.isNull(CLIENT_TIME)){
                traineeStatus.setClientTime(jsonObject.getLong(CLIENT_TIME));
            }
            if (!jsonObject.isNull(CREATED_BY)){
                traineeStatus.setCreatedBy(jsonObject.getInt(CREATED_BY));
            }
            if (!jsonObject.isNull(DATE_CREATED)){
                traineeStatus.setDateCreated(jsonObject.getString(DATE_CREATED));
            }

            this.addTraineeStatus(traineeStatus);
        }catch (Exception e){
            Log.d("Tremap", "=======ERR Creating Trainee Status ============");
            Log.d("Tremap", e.getMessage());
            Log.d("Tremap", "===================");
        }
    }
}
