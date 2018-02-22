package com.expansion.lg.kimaru.training.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.expansion.lg.kimaru.training.objs.SessionAttendance;
import com.expansion.lg.kimaru.training.objs.Training;
import com.expansion.lg.kimaru.training.objs.TrainingVenue;

import java.util.ArrayList;
import java.util.List;

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
    private static final String TABLE_USERS = "users";
    private static final String TABLE_TRAINING_COMMENTS = "training_comments";

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
    public static final String REGISTRATION = "registration";
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
            + REGISTRATION + text_field + ", " //will be storing the JSON
            + COMMENT + text_field + "); ";

    public static final String CREATE_TABLE_TRAINEE_COMMENTS ="CREATE TABLE " + TABLE_TRAINING_COMMENTS + "("
            + ID + varchar_field +", "
            + TRAINEE_ID + varchar_field + ", "
            + TRAINING_ID + varchar_field + ", "
            + COUNTRY + varchar_field + ", "
            + ADDED_BY + integer_field + ", "
            + DATE_CREATED + " DATETIME " + ", "
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
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }

    String [] trainingColumns = new String[]{ID, TRAINING_NAME, COUNTRY, COUNTY_ID, LOCATION_ID,
            SUBCOUNTY_ID, WARD_ID, DISTRICT, RECRUITMENT_ID, PARISH_ID, LAT, LON, TRAINING_VENUE_ID,
            TRAINING_STATUS_ID, CLIENT_TIME, CREATED_BY, DATE_CREATED, ARCHIVED, COMMENT,
            DATE_COMMENCED, DATE_COMPLETED};
    String [] trainingVenueColumns = new String[] {ID, NAME, MAPPING, LAT, LON, INSPECTED, COUNTRY,
            SELECTED, CAPACITY, DATE_ADDED, ADDED_BY, CLIENT_TIME, META_DATA, ARCHIVED};
    String[] sessionAttendanceColumns = new String[]{ID, TRAINING_SESSION_ID, TRAINEE_ID,
            TRAINING_SESSION_TYPE_ID, TRAINING_ID, COUNTRY, ATTENDED, CREATED_BY, CLIENT_TIME,
            DATE_CREATED, META_DATA, COMMENT, ARCHIVED};

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
        Cursor cursor=db.query(TABLE_TRAINING,trainingColumns,whereClause,whereArgs,null,null,null,null);
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

    /**
     *
     * @param training
     * @return bool
     */
    public boolean trainingExists(Training training) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cur = db.rawQuery("SELECT "+ID+" FROM " + TABLE_TRAINING + " WHERE "+ID+" = '" + training.getId() + "'", null);
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
            id = db.insertWithOnConflict(TABLE_TRAINING_VENUE, null, cv, SQLiteDatabase.CONFLICT_REPLACE);
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
        Cursor cur = db.rawQuery("SELECT "+ID+" FROM " + TABLE_TRAINING_VENUE + " WHERE "+ID+" = '" + trainingVenue.getId() + "'", null);
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
        Cursor cursor=db.query(TABLE_TRAINING_VENUE,trainingVenueColumns,whereClause,whereArgs,null,null,null,null);
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


    /**
     * ************************************
     *        SESSION ATTENDANCE          *
     * ************************************
     */

    private SessionAttendance cursorToSessionAttendance(Cursor cursor){
        SessionAttendance sessionAttendance = new SessionAttendance();

        sessionAttendance.setId(cursor.getString(cursor.getColumnIndex(ID)));
        sessionAttendance.setTrainingSessionId(cursor.getString(cursor.getColumnIndex(TRAINING_SESSION_ID)));
        sessionAttendance.setTraineeId(cursor.getString(cursor.getColumnIndex(TRAINEE_ID)));
        sessionAttendance.setTrainingSessionTypeId(cursor.getInt(cursor.getColumnIndex(TRAINING_SESSION_TYPE_ID)));
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
            id = db.update(TABLE_SESSION_ATTENDANCE, cv, ID+"='"+sessionAttendance.getId()+"'", null);
        }else{
            id = db.insertWithOnConflict(TABLE_SESSION_ATTENDANCE, null, cv, SQLiteDatabase.CONFLICT_REPLACE);
        }
        db.close();
        return id;
    }

    public boolean sessionAttendanceExists(SessionAttendance sessionAttendance) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cur = db.rawQuery("SELECT "+ID+" FROM " + TABLE_SESSION_ATTENDANCE + " WHERE "+ID+" = '" + sessionAttendance.getId() + "'", null);
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
        Cursor cursor=db.query(TABLE_SESSION_ATTENDANCE, sessionAttendanceColumns, whereClause,whereArgs,null,null,null,null);
        if (!(cursor.moveToFirst()) || cursor.getCount() ==0){
            return null;
        }else{

            SessionAttendance sessionAttendance = cursorToSessionAttendance(cursor);
            cursor.close();
            return sessionAttendance;
        }
    }

    public List<SessionAttendance> getSessionAttendance(){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(TABLE_SESSION_ATTENDANCE,sessionAttendanceColumns,null,null,null,null,
                null,null);
        List<SessionAttendance> sessionAttendanceList = new ArrayList<>();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
            SessionAttendance sessionAttendance = cursorToSessionAttendance(cursor);
            sessionAttendanceList.add(sessionAttendance);
        }
        cursor.close();
        return sessionAttendanceList;
    }
}
