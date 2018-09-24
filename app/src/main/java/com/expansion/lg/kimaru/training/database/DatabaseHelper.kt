package com.expansion.lg.kimaru.training.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Base64
import android.util.Log

import com.expansion.lg.kimaru.training.objs.Branch
import com.expansion.lg.kimaru.training.objs.Cohort
import com.expansion.lg.kimaru.training.objs.SessionAttendance
import com.expansion.lg.kimaru.training.objs.SessionTopic
import com.expansion.lg.kimaru.training.objs.TraineeStatus
import com.expansion.lg.kimaru.training.objs.Training
import com.expansion.lg.kimaru.training.objs.TrainingClass
import com.expansion.lg.kimaru.training.objs.TrainingComment
import com.expansion.lg.kimaru.training.objs.TrainingExam
import com.expansion.lg.kimaru.training.objs.TrainingExamResult
import com.expansion.lg.kimaru.training.objs.TrainingRole
import com.expansion.lg.kimaru.training.objs.TrainingSession
import com.expansion.lg.kimaru.training.objs.TrainingSessionType
import com.expansion.lg.kimaru.training.objs.TrainingStatus
import com.expansion.lg.kimaru.training.objs.TrainingTrainee
import com.expansion.lg.kimaru.training.objs.TrainingTrainer
import com.expansion.lg.kimaru.training.objs.TrainingVenue
import com.expansion.lg.kimaru.training.objs.User
import com.expansion.lg.kimaru.training.utils.DisplayDate

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

import java.util.ArrayList
import java.util.Date
import java.util.UUID

/**
 * Created by kimaru on 2/21/18.
 */

 class DatabaseHelper(context:Context):SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

private val trainingColumns = arrayOf(ID, TRAINING_NAME, COUNTRY, COUNTY_ID, LOCATION_ID, SUBCOUNTY_ID, WARD_ID, DISTRICT, RECRUITMENT_ID, PARISH_ID, LAT, LON, TRAINING_VENUE_ID, TRAINING_STATUS_ID, CLIENT_TIME, CREATED_BY, DATE_CREATED, ARCHIVED, COMMENT, DATE_COMMENCED, DATE_COMPLETED)
private val trainingVenueColumns = arrayOf(ID, NAME, MAPPING, LAT, LON, INSPECTED, COUNTRY, SELECTED, CAPACITY, DATE_ADDED, ADDED_BY, CLIENT_TIME, META_DATA, ARCHIVED)
private val sessionAttendanceColumns = arrayOf(ID, TRAINING_SESSION_ID, TRAINEE_ID, TRAINING_SESSION_TYPE_ID, TRAINING_ID, COUNTRY, ATTENDED, CREATED_BY, CLIENT_TIME, DATE_CREATED, META_DATA, COMMENT, ARCHIVED)
private val sessionTopicColumns = arrayOf(ID, NAME, COUNTRY, ARCHIVED, ADDED_BY, DATE_ADDED, META_DATA, COMMENT)
private val trainingSessionColumns = arrayOf(ID, TRAINING_SESSION_TYPE_ID, CLASS_ID, TRAINING_ID, TRAINER_ID, COUNTRY, ARCHIVED, SESSION_START_TIME, SESSION_END_TIME, SESSION_TOPIC_ID, SESSION_LEAD_TRAINER, CLIENT_TIME, CREATED_BY, DATE_CREATED, COMMENT)
private val trainingSessionTypeColumns = arrayOf(ID, SESSION_NAME, CLASS_ID, COUNTRY, ARCHIVED, CLIENT_TIME, CREATED_BY, DATE_CREATED, COMMENT)
private val trainingStatusColumns = arrayOf(ID, NAME, ARCHIVED, READONLY, COUNTRY, CLIENT_TIME, CREATED_BY, DATE_CREATED, COMMENT)

private val trainingRoleColumns = arrayOf(ID, ROLE_NAME, ARCHIVED, READONLY, COUNTRY, CLIENT_TIME, CREATED_BY, DATE_CREATED, COMMENT)

private val trainingTrainerColumns = arrayOf(ID, TRAINING_ID, CLASS_ID, TRAINER_ID, COUNTRY, CLIENT_TIME, CREATED_BY, DATE_CREATED, ARCHIVED, TRAINING_ROLE_ID, COMMENT)

private val trainingClassesColumns = arrayOf(ID, TRAINING_ID, CLASS_NAME, COUNTRY, CLIENT_TIME, CREATED_BY, DATE_CREATED, ARCHIVED, COMMENT)

private val trainingTraineeColumns = arrayOf(ID, REGISTRATION_ID, CLASS_ID, TRAINING_ID, COUNTRY, ADDED_BY, DATE_CREATED, CLIENT_TIME, BRANCH, COHORT, CHP_CODE, ARCHIVED, REGISTRATION, COMMENT, TRAINEE_STATUS)
private val userColumns = arrayOf(ID, EMAIL, USERNAME, PASSWORD, NAME, COUNTRY)
private val trainingCommentColumns = arrayOf(ID, TRAINEE_ID, TRAINING_ID, COUNTRY, ADDED_BY, DATE_CREATED, CLIENT_TIME, ARCHIVED, COMMENT)
private val branchColumns = arrayOf(ID, BRANCH_NAME, BRANCH_CODE, MAPPING_ID, LAT, LON, ARCHIVED)
private val cohortColumns = arrayOf(ID, COHORT_NAME, COHORT_NUMBER, BRANCH_ID, ARCHIVED)
private val traineeStatusColumns = arrayOf(ID, NAME, ARCHIVED, READONLY, COUNTRY, CLIENT_TIME, CREATED_BY, DATE_CREATED)
private val trainingExamColumns = arrayOf(ID, EXAM_ID, DATE_ADMINISTERED, CREATED_BY, DATE_CREATED, PASSMARK, TRAINING_ID, COUNTRY, ARCHIVED, TITLE, CLOUD_EXAM, EXAM_STATUS, QUESTIONS, EXAM_STATUS_ID)
private val trainingExamResultColumns = arrayOf(ID, ARCHIVED, CHOICE_ID, COUNTRY, ANSWER, CREATED_BY, DATE_CREATED, QUESTION_ID, QUESTION_SCORE, TRAINEE_ID, TRAINING_EXAM_ID)

/**
 *
 * @return Trainings
 */
     val trainings:List<Training>
get() {
val db = writableDatabase
val cursor = db.query(TABLE_TRAINING, trainingColumns, null, null, null, null, null, null)
val trainingList = ArrayList<Training>()
cursor.moveToFirst()
while (!cursor.isAfterLast)
{
val training = cursorToTraining(cursor)
trainingList.add(training)
cursor.moveToNext()
}
cursor.close()
return trainingList
}

 val trainingVenues:List<TrainingVenue>
get() {
val db = writableDatabase
val cursor = db.query(TABLE_TRAINING_VENUE, trainingVenueColumns, null, null, null, null, null, null)
val trainingVenueList = ArrayList<TrainingVenue>()
cursor.moveToFirst()
while (!cursor.isAfterLast)
{
val trainingVenue = cursorToTrainingVenue(cursor)
trainingVenueList.add(trainingVenue)
cursor.moveToNext()
}
cursor.close()
return trainingVenueList
}

 val sessionAttendances:List<SessionAttendance>
get() {
val db = writableDatabase
val cursor = db.query(TABLE_SESSION_ATTENDANCE, sessionAttendanceColumns, null, null, null, null, null, null)
val sessionAttendanceList = ArrayList<SessionAttendance>()
cursor.moveToFirst()
while (!cursor.isAfterLast)
{
val sessionAttendance = cursorToSessionAttendance(cursor)
sessionAttendanceList.add(sessionAttendance)
cursor.moveToNext()
}
cursor.close()
return sessionAttendanceList
}

 val sessionTopics:List<SessionTopic>
get() {
val db = writableDatabase
val cursor = db.query(TABLE_SESSION_TOPIC, sessionTopicColumns, null, null, null, null, null, null)
val sessionTopicList = ArrayList<SessionTopic>()
cursor.moveToFirst()
while (!cursor.isAfterLast)
{
val sessionTopic = cursorToSessionTopic(cursor)
sessionTopicList.add(sessionTopic)
cursor.moveToNext()
}
cursor.close()
return sessionTopicList
}

 val trainingSessions:List<TrainingSession>
get() {
val db = writableDatabase
val cursor = db.query(TABLE_TRAINING_SESSION, trainingSessionColumns, null, null, null, null, null, null)
val trainingSessionList = ArrayList<TrainingSession>()
cursor.moveToFirst()
while (!cursor.isAfterLast)
{
val trainingSession = cursorToTrainingSession(cursor)
trainingSessionList.add(trainingSession)
cursor.moveToNext()
}
cursor.close()
return trainingSessionList
}

 val trainingSessionTypes:List<TrainingSessionType>
get() {
val db = writableDatabase
val cursor = db.query(TABLE_TRAINING_SESSION_TYPE, trainingSessionTypeColumns, null, null, null, null, null, null)
val trainingSessionTypeList = ArrayList<TrainingSessionType>()
cursor.moveToFirst()
while (!cursor.isAfterLast)
{
val trainingSessionType = cursorToTrainingSessionType(cursor)
trainingSessionTypeList.add(trainingSessionType)
cursor.moveToNext()
}
cursor.close()
return trainingSessionTypeList
}

 val trainingStatuss:List<TrainingStatus>
get() {
val db = writableDatabase
val cursor = db.query(TABLE_TRAINING_STATUS, trainingStatusColumns, null, null, null, null, null, null)
val trainingStatusList = ArrayList<TrainingStatus>()
cursor.moveToFirst()
while (!cursor.isAfterLast)
{
val trainingStatus = cursorToTrainingStatus(cursor)
trainingStatusList.add(trainingStatus)
cursor.moveToNext()
}
cursor.close()
return trainingStatusList
}

 val trainingRoles:List<TrainingRole>
get() {
val db = writableDatabase
val cursor = db.query(TABLE_TRAINING_ROLES, trainingRoleColumns, null, null, null, null, null, null)
val trainingRoleList = ArrayList<TrainingRole>()
cursor.moveToFirst()
while (!cursor.isAfterLast)
{
val trainingRole = cursorToTrainingRole(cursor)
trainingRoleList.add(trainingRole)
cursor.moveToNext()
}
cursor.close()
return trainingRoleList
}

 val trainingTrainers:List<TrainingTrainer>
get() {
val db = writableDatabase
val cursor = db.query(TABLE_TRAINING_TRAINERS, trainingTrainerColumns, null, null, null, null, null, null)
val trainingTrainerList = ArrayList<TrainingTrainer>()
cursor.moveToFirst()
while (!cursor.isAfterLast)
{
val trainingTrainer = cursorToTrainingTrainer(cursor)
trainingTrainerList.add(trainingTrainer)
cursor.moveToNext()
}
cursor.close()
return trainingTrainerList
}

 val trainingClasss:List<TrainingClass>
get() {
val db = writableDatabase
val cursor = db.query(TABLE_TRAINING_CLASSES, trainingClassesColumns, null, null, null, null, null, null)
val trainingClassList = ArrayList<TrainingClass>()
cursor.moveToFirst()
while (!cursor.isAfterLast)
{
val trainingClass = cursorToTrainingClass(cursor)
trainingClassList.add(trainingClass)
cursor.moveToNext()
}
cursor.close()
return trainingClassList
}

 val trainingTrainees:List<TrainingTrainee>
get() {
val db = writableDatabase
val cursor = db.query(TABLE_TRAINING_TRAINEES, trainingTraineeColumns, null, null, null, null, null, null)
val trainingTraineeList = ArrayList<TrainingTrainee>()
cursor.moveToFirst()
while (!cursor.isAfterLast)
{
val trainingTrainee = cursorToTrainingTrainee(cursor)
trainingTraineeList.add(trainingTrainee)
cursor.moveToNext()
}
cursor.close()
return trainingTraineeList
}

 val trainingComments:List<TrainingComment>
get() {
val db = writableDatabase
val cursor = db.query(TABLE_TRAINING_COMMENTS, trainingCommentColumns, null, null, null, null, null, null)
val trainingCommentList = ArrayList<TrainingComment>()
cursor.moveToFirst()
while (!cursor.isAfterLast)
{
val trainingComment = cursorToTrainingComment(cursor)
trainingCommentList.add(trainingComment)
cursor.moveToNext()
}
cursor.close()
return trainingCommentList
}

 val users:List<User>
get() {
val db = writableDatabase
val cursor = db.query(TABLE_USERS, userColumns, null, null, null, null, null, null)
val userList = ArrayList<User>()
cursor.moveToFirst()
while (!cursor.isAfterLast)
{
val user = cursorToUser(cursor)
userList.add(user)
cursor.moveToNext()
}
cursor.close()
return userList
}

 val branches:List<Branch>
get() {
val db = writableDatabase
val cursor = db.query(TABLE_BRANCH, branchColumns, null, null, null, null, null, null)
val branchList = ArrayList<Branch>()
cursor.moveToFirst()
while (!cursor.isAfterLast)
{
val branch = cursorToBranch(cursor)
branchList.add(branch)
cursor.moveToNext()
}
cursor.close()
return branchList
}

 val cohorts:List<Cohort>
get() {
val db = writableDatabase
val cursor = db.query(TABLE_COHORT, cohortColumns, null, null, null, null, null, null)
val cohortList = ArrayList<Cohort>()
cursor.moveToFirst()
while (!cursor.isAfterLast)
{
val cohort = cursorToCohort(cursor)
cohortList.add(cohort)
cursor.moveToNext()
}
cursor.close()
return cohortList
}

 val traineeStatuses:List<TraineeStatus>
get() {
val db = writableDatabase
val cursor = db.query(TABLE_TRAINEE_STATUS, traineeStatusColumns, null, null, null, null, null, null)
val traineeStatusList = ArrayList<TraineeStatus>()
cursor.moveToFirst()
while (!cursor.isAfterLast)
{
val status = cursorToTraineeStatus(cursor)
traineeStatusList.add(status)
cursor.moveToNext()
}
cursor.close()
return traineeStatusList
}

override fun onCreate(db:SQLiteDatabase) {
try
{
db.execSQL(CREATE_TABLE_TRAINING)
db.execSQL(CREATE_TABLE_TRAINING_EXAM) // not created
db.execSQL(CREATE_TABLE_TRAINING_VENUE)
db.execSQL(CREATE_TABLE_SESSION_ATTENDANCE)
db.execSQL(CREATE_TABLE_SESSION_TOPIC)
db.execSQL(CREATE_TABLE_TRAINING_SESSION)
db.execSQL(CREATE_TABLE_TRAINING_SESSION_TYPE)
db.execSQL(CREATE_TABLE_TRAINING_STATUS)
db.execSQL(CREATE_TABLE_TRAINING_ROLES)
db.execSQL(CREATE_TABLE_TRAINING_TRAINERS)
db.execSQL(CREATE_TABLE_TRAINING_CLASSES)
db.execSQL(CREATE_TABLE_TRAINING_TRAINEE)
db.execSQL(CREATE_TABLE_USERS)
db.execSQL(CREATE_TABLE_TRAINEE_COMMENTS)
db.execSQL(CREATE_TABLE_BRANCH)
db.execSQL(CREATE_TABLE_COHORT)
db.execSQL(CREATE_TABLE_TRAINEE_STATUS)
db.execSQL(CREATE_EXAM_RESULTS_TABLE)
}
catch (e:Exception) {
Log.d("TREMAPDB", e.message)
}

}
override fun onUpgrade(db:SQLiteDatabase, oldVersion:Int, newVersion:Int) {

}

/**
 * **************************************
 * Training                             *
 * **************************************
 */


    /**
 *
 * @param training
 * @return id
 */
     fun addTraining(training:Training):Long {
val db = writableDatabase
val cv = ContentValues()
cv.put(ID, training.id)
cv.put(TRAINING_NAME, training.trainingName)
cv.put(COUNTRY, training.country)
cv.put(COUNTY_ID, training.countyId)
cv.put(LOCATION_ID, training.locationId)
cv.put(SUBCOUNTY_ID, training.subCountyId)
cv.put(WARD_ID, training.wardId)
cv.put(DISTRICT, training.district)
cv.put(RECRUITMENT_ID, training.recruitmentId)
cv.put(PARISH_ID, training.parishId)
cv.put(LAT, training.lat)
cv.put(LON, training.lon)
cv.put(TRAINING_VENUE_ID, training.trainingVenueId)
cv.put(TRAINING_STATUS_ID, training.trainingStatusId)
cv.put(CLIENT_TIME, training.clientTime)
cv.put(CREATED_BY, training.createdBy)
cv.put(DATE_CREATED, training.dateCreated)
cv.put(ARCHIVED, training.isArchived)
cv.put(COMMENT, training.comment)
cv.put(DATE_COMMENCED, training.dateCommenced)
cv.put(DATE_COMPLETED, training.dateCompleted)
val id:Long
if (trainingExists(training))
{
id = db.update(TABLE_TRAINING, cv, ID + "='" + training.id + "'", null).toLong()
}
else
{
id = db.insertWithOnConflict(TABLE_TRAINING, null, cv, SQLiteDatabase.CONFLICT_REPLACE)
}
db.close()
return id
}

/**
 *
 * @param trainingId
 * @return training
 */
     fun getTrainingById(trainingId:String):Training? {
val db = writableDatabase
val whereClause = "$ID = ?"
val whereArgs = arrayOf(trainingId)
val cursor = db.query(TABLE_TRAINING, trainingColumns, whereClause, whereArgs, null, null, null, null)
if (!cursor.moveToFirst() || cursor.count == 0)
{
return null
}
else
{

val training = cursorToTraining(cursor)
cursor.close()
return training
}
}

/**
 *
 * @param country
 * @return training
 */
     fun getTrainingsByCountry(country:String):List<Training> {
val db = writableDatabase
val whereClause = "$COUNTRY = ?"
val whereArgs = arrayOf(country)
val cursor = db.query(TABLE_TRAINING, trainingColumns, whereClause, whereArgs, null, null, null, null)
val trainingList = ArrayList<Training>()
cursor.moveToFirst()
while (!cursor.isAfterLast)
{
val training = cursorToTraining(cursor)
trainingList.add(training)
cursor.moveToNext()
}
cursor.close()
return trainingList
}

 fun trainingFromJson(jsonObject:JSONObject) {
val training = Training()
try
{
training.id = jsonObject.getString(ID)
if (!jsonObject.isNull(TRAINING_NAME))
{
training.trainingName = jsonObject.getString(TRAINING_NAME)
}
if (!jsonObject.isNull(COUNTRY))
{
training.country = jsonObject.getString(COUNTRY)
}
if (!jsonObject.isNull(COUNTY_ID))
{
training.countyId = jsonObject.getInt(COUNTY_ID)
}
if (!jsonObject.isNull(SUBCOUNTY_ID))
{
training.subCountyId = jsonObject.getString(SUBCOUNTY_ID)
}
if (!jsonObject.isNull(WARD_ID))
{
training.wardId = jsonObject.getString(WARD_ID)
}
if (!jsonObject.isNull(DISTRICT))
{
training.district = jsonObject.getString(DISTRICT)
}
if (!jsonObject.isNull(PARISH_ID))
{
training.parishId = jsonObject.getString(PARISH_ID)
}
if (!jsonObject.isNull(LOCATION_ID))
{
training.locationId = jsonObject.getInt(LOCATION_ID)
}
if (!jsonObject.isNull(CREATED_BY))
{
training.createdBy = jsonObject.getInt(CREATED_BY)
}
if (!jsonObject.isNull(TRAINING_STATUS_ID))
{
training.trainingStatusId = jsonObject.getInt(TRAINING_STATUS_ID)
}
if (!jsonObject.isNull(RECRUITMENT_ID))
{
training.recruitmentId = jsonObject.getString(RECRUITMENT_ID)
}
if (!jsonObject.isNull(COMMENT))
{
training.comment = jsonObject.getString(COMMENT)
}
if (!jsonObject.isNull(CLIENT_TIME))
{
training.clientTime = jsonObject.getLong(CLIENT_TIME)
}
if (!jsonObject.isNull(LAT))
{
training.lat = jsonObject.getDouble(LAT)
}
if (!jsonObject.isNull(LON))
{
training.lon = jsonObject.getDouble(LON)
}
if (!jsonObject.isNull(TRAINING_VENUE_ID))
{
training.trainingVenueId = jsonObject.getString(TRAINING_VENUE_ID)
}
if (!jsonObject.isNull(DATE_CREATED))
{
training.dateCreated = jsonObject.getString(DATE_CREATED)
}
if (!jsonObject.isNull(ARCHIVED))
{
training.isArchived = jsonObject.getInt(ARCHIVED) == 1
}
if (!jsonObject.isNull(DATE_COMMENCED))
{
training.dateCommenced = jsonObject.getLong(DATE_COMMENCED)
}
if (!jsonObject.isNull(DATE_COMPLETED))
{
training.dateCompleted = jsonObject.getLong(DATE_COMPLETED)
}
this.addTraining(training)
}
catch (e:Exception) {
Log.d("Tremap", "=======ERR Creating Training============")
Log.d("Tremap", e.message)
Log.d("Tremap", "===================")
}

}

/**
 *
 * @param training
 * @return bool
 */
     fun trainingExists(training:Training):Boolean {
val db = readableDatabase
val cur = db.rawQuery("SELECT " + ID + " FROM " + TABLE_TRAINING + " WHERE " + ID + " = '"
+ training.id + "'", null)
val exist = cur.count > 0
cur.close()
return exist

}

/**
 *
 * @param cursor
 * @return Training
 */
    private fun cursorToTraining(cursor:Cursor):Training {
val training = Training()
training.id = cursor.getString(cursor.getColumnIndex(ID))
training.trainingName = cursor.getString(cursor.getColumnIndex(TRAINING_NAME))
training.country = cursor.getString(cursor.getColumnIndex(COUNTRY))
training.countyId = cursor.getInt(cursor.getColumnIndex(COUNTY_ID))
training.locationId = cursor.getInt(cursor.getColumnIndex(LOCATION_ID))
training.subCountyId = cursor.getString(cursor.getColumnIndex(SUBCOUNTY_ID))
training.wardId = cursor.getString(cursor.getColumnIndex(WARD_ID))
training.district = cursor.getString(cursor.getColumnIndex(DISTRICT))
training.recruitmentId = cursor.getString(cursor.getColumnIndex(RECRUITMENT_ID))
training.parishId = cursor.getString(cursor.getColumnIndex(PARISH_ID))
training.lat = cursor.getDouble(cursor.getColumnIndex(LAT))
training.lon = cursor.getDouble(cursor.getColumnIndex(LON))
training.trainingVenueId = cursor.getString(cursor.getColumnIndex(TRAINING_VENUE_ID))
training.trainingStatusId = cursor.getInt(cursor.getColumnIndex(TRAINING_STATUS_ID))
training.clientTime = cursor.getLong(cursor.getColumnIndex(CLIENT_TIME))
training.createdBy = cursor.getInt(cursor.getColumnIndex(CREATED_BY))
training.dateCreated = cursor.getString(cursor.getColumnIndex(DATE_CREATED))
training.isArchived = cursor.getInt(cursor.getColumnIndex(ARCHIVED)) == 1
training.comment = cursor.getString(cursor.getColumnIndex(COMMENT))
training.dateCommenced = cursor.getLong(cursor.getColumnIndex(DATE_COMMENCED))
training.dateCompleted = cursor.getLong(cursor.getColumnIndex(DATE_COMPLETED))
return training
}

/**
 * ********************************************
 * Training Venue                             *
 * ********************************************
 */

     fun addTrainingVenue(trainingVenue:TrainingVenue):Long {
val db = writableDatabase
val cv = ContentValues()
cv.put(ID, trainingVenue.id)
cv.put(NAME, trainingVenue.name)
cv.put(MAPPING, trainingVenue.mapping)
cv.put(LAT, trainingVenue.lat)
cv.put(LON, trainingVenue.lon)
cv.put(INSPECTED, trainingVenue.inspected)
cv.put(COUNTRY, trainingVenue.country)
cv.put(SELECTED, trainingVenue.isSelected)
cv.put(CAPACITY, trainingVenue.capacity)
cv.put(DATE_ADDED, trainingVenue.dateAdded)
cv.put(ADDED_BY, trainingVenue.addedBy)
cv.put(CLIENT_TIME, trainingVenue.clientTime)
cv.put(META_DATA, trainingVenue.metaData)
cv.put(ARCHIVED, trainingVenue.isArchived)
val id:Long
if (trainingVenueExists(trainingVenue))
{
id = db.update(TABLE_TRAINING_VENUE, cv, ID + "='" + trainingVenue.id + "'", null).toLong()
}
else
{
id = db.insertWithOnConflict(TABLE_TRAINING_VENUE, null, cv,
SQLiteDatabase.CONFLICT_REPLACE)
}
db.close()
return id
}

/**
 *
 * @param trainingVenue
 * @return bool
 */
     fun trainingVenueExists(trainingVenue:TrainingVenue):Boolean {
val db = readableDatabase
val cur = db.rawQuery("SELECT " + ID + " FROM " + TABLE_TRAINING_VENUE + " WHERE " + ID + " = '"
+ trainingVenue.id + "'", null)
val exist = cur.count > 0
cur.close()
return exist

}

 fun getTrainingVenueById(trainingVenueId:String):TrainingVenue? {
val db = writableDatabase
val whereClause = "$ID = ?"
val whereArgs = arrayOf(trainingVenueId)
val cursor = db.query(TABLE_TRAINING_VENUE, trainingVenueColumns, whereClause, whereArgs, null, null, null, null)
if (!cursor.moveToFirst() || cursor.count == 0)
{
return null
}
else
{

val trainingVenue = cursorToTrainingVenue(cursor)
cursor.close()
return trainingVenue
}
}

/**
 *
 * @param cursor
 * @return TrainingVenue
 */
    private fun cursorToTrainingVenue(cursor:Cursor):TrainingVenue {
val trainingVenue = TrainingVenue()
trainingVenue.id = cursor.getString(cursor.getColumnIndex(ID))
trainingVenue.name = cursor.getString(cursor.getColumnIndex(NAME))
trainingVenue.mapping = cursor.getString(cursor.getColumnIndex(MAPPING))
trainingVenue.lat = cursor.getDouble(cursor.getColumnIndex(LAT))
trainingVenue.lon = cursor.getDouble(cursor.getColumnIndex(LON))
trainingVenue.inspected = cursor.getInt(cursor.getColumnIndex(INSPECTED))
trainingVenue.country = cursor.getString(cursor.getColumnIndex(COUNTRY))
trainingVenue.isSelected = cursor.getInt(cursor.getColumnIndex(SELECTED)) == 1
trainingVenue.capacity = cursor.getInt(cursor.getColumnIndex(CAPACITY))
trainingVenue.dateAdded = cursor.getString(cursor.getColumnIndex(DATE_ADDED))
trainingVenue.addedBy = cursor.getInt(cursor.getColumnIndex(ADDED_BY))
trainingVenue.clientTime = cursor.getLong(cursor.getColumnIndex(CLIENT_TIME))
trainingVenue.metaData = cursor.getString(cursor.getColumnIndex(META_DATA))
trainingVenue.isArchived = cursor.getInt(cursor.getColumnIndex(ARCHIVED)) == 1
return trainingVenue
}

 fun trainingVenueFromJson(jsonObject:JSONObject) {
val trainingVenue = TrainingVenue()

try
{
trainingVenue.id = jsonObject.getString(ID)
if (!jsonObject.isNull(NAME))
{
trainingVenue.name = jsonObject.getString(NAME)
}
if (!jsonObject.isNull(MAPPING))
{
trainingVenue.mapping = jsonObject.getString(MAPPING)
}
if (!jsonObject.isNull(LAT))
{
trainingVenue.lat = jsonObject.getDouble(LAT)
}
if (!jsonObject.isNull(LON))
{
trainingVenue.lon = jsonObject.getDouble(LON)
}
if (!jsonObject.isNull(INSPECTED))
{
trainingVenue.inspected = jsonObject.getInt(INSPECTED)
}
if (!jsonObject.isNull(COUNTRY))
{
trainingVenue.country = jsonObject.getString(COUNTRY)
}
if (!jsonObject.isNull(SELECTED))
{
trainingVenue.isSelected = jsonObject.getInt(SELECTED) == 1
}
if (!jsonObject.isNull(CAPACITY))
{
trainingVenue.capacity = jsonObject.getInt(CAPACITY)
}
if (!jsonObject.isNull(DATE_ADDED))
{
trainingVenue.dateAdded = jsonObject.getString(DATE_ADDED)
}
if (!jsonObject.isNull(ADDED_BY))
{
trainingVenue.addedBy = jsonObject.getInt(ADDED_BY)
}
if (!jsonObject.isNull(CLIENT_TIME))
{
trainingVenue.clientTime = jsonObject.getLong(CLIENT_TIME)
}
if (!jsonObject.isNull(META_DATA))
{
trainingVenue.metaData = jsonObject.getString(META_DATA)
}
if (!jsonObject.isNull(ARCHIVED))
{
trainingVenue.isArchived = jsonObject.getInt(ARCHIVED) == 1
}
this.addTrainingVenue(trainingVenue)
}
catch (e:Exception) {
Log.d("Tremap", "=======ERR Creating Training Venue from JSON============")
Log.d("Tremap", e.message)
Log.d("Tremap", "===================")
}

}


/**
 * ************************************
 * SESSION ATTENDANCE          *
 * ************************************
 */

    private fun cursorToSessionAttendance(cursor:Cursor):SessionAttendance {
val sessionAttendance = SessionAttendance()

sessionAttendance.id = cursor.getString(cursor.getColumnIndex(ID))
sessionAttendance.trainingSessionId = cursor.getString(cursor.getColumnIndex(
TRAINING_SESSION_ID))
sessionAttendance.traineeId = cursor.getString(cursor.getColumnIndex(TRAINEE_ID))
sessionAttendance.trainingSessionTypeId = cursor.getInt(cursor.getColumnIndex(
TRAINING_SESSION_TYPE_ID))
sessionAttendance.trainingId = cursor.getString(cursor.getColumnIndex(TRAINING_ID))
sessionAttendance.country = cursor.getString(cursor.getColumnIndex(COUNTRY))
sessionAttendance.isAttended = cursor.getInt(cursor.getColumnIndex(ATTENDED)) == 1
sessionAttendance.createdBy = cursor.getInt(cursor.getColumnIndex(CREATED_BY))
sessionAttendance.clientTime = cursor.getLong(cursor.getColumnIndex(CLIENT_TIME))
sessionAttendance.dateCreated = cursor.getString(cursor.getColumnIndex(DATE_CREATED))
sessionAttendance.metaData = cursor.getString(cursor.getColumnIndex(META_DATA))
sessionAttendance.comment = cursor.getString(cursor.getColumnIndex(COMMENT))
sessionAttendance.isArchived = cursor.getInt(cursor.getColumnIndex(ARCHIVED)) == 1
return sessionAttendance
}

 fun generateSessionAttendance(trainingId:String) {
/**
 * get all topics
 * for each topic, get trainees
 * for each trainee, create sesssion
 */
        for (session in getTrainingSessionsByTrainingId(trainingId))
{
for (trainee in getTrainingTraineesByTrainingId(trainingId))
{
 //if training_id and session_id exists, do not create one
                if (getSessionAttendancesByTraineeIdAndTrainindId(trainee.id, trainingId, session.id) == null)
{
val currentDate = Date().time
val s = SessionAttendance()
s.id = UUID.randomUUID().toString()
s.trainingSessionId = session.id
s.traineeId = trainee.id
s.trainingId = trainingId
s.country = session.country
s.dateCreated = DisplayDate(currentDate).dateAndTime()
s.createdBy = 1
s.isAttended = false
s.isArchived = false
s.clientTime = currentDate
addSessionAttendance(s)
}
}
}
}

 fun addSessionAttendance(sessionAttendance:SessionAttendance):Long {
val db = writableDatabase
val cv = ContentValues()
cv.put(ID, sessionAttendance.id)
cv.put(TRAINING_SESSION_ID, sessionAttendance.trainingSessionId)
cv.put(TRAINEE_ID, sessionAttendance.traineeId)
cv.put(TRAINING_SESSION_TYPE_ID, sessionAttendance.trainingSessionTypeId)
cv.put(TRAINING_ID, sessionAttendance.trainingId)
cv.put(COUNTRY, sessionAttendance.country)
cv.put(ATTENDED, sessionAttendance.isAttended)
cv.put(CREATED_BY, sessionAttendance.createdBy)
cv.put(CLIENT_TIME, sessionAttendance.clientTime)
cv.put(DATE_CREATED, sessionAttendance.dateCreated)
cv.put(META_DATA, sessionAttendance.metaData)
cv.put(COMMENT, sessionAttendance.comment)
cv.put(ARCHIVED, sessionAttendance.isArchived)
val id:Long
if (sessionAttendanceExists(sessionAttendance))
{
id = db.update(TABLE_SESSION_ATTENDANCE, cv, ID + "='" + sessionAttendance.id + "'", null).toLong()
}
else
{
id = db.insertWithOnConflict(TABLE_SESSION_ATTENDANCE, null, cv,
SQLiteDatabase.CONFLICT_REPLACE)
}
db.close()
return id
}

 fun sessionAttendanceExists(sessionAttendance:SessionAttendance):Boolean {
val db = readableDatabase
val cur = db.rawQuery("SELECT " + ID + " FROM " + TABLE_SESSION_ATTENDANCE + " WHERE " +
ID + " = '" + sessionAttendance.id + "'", null)
val exist = cur.count > 0
cur.close()
return exist

}

 fun getSessionAttendaceById(sessionAttendanceId:String):SessionAttendance? {
val db = writableDatabase
val whereClause = "$ID = ?"
val whereArgs = arrayOf(sessionAttendanceId)
val cursor = db.query(TABLE_SESSION_ATTENDANCE, sessionAttendanceColumns, whereClause,
whereArgs, null, null, null, null)
if (!cursor.moveToFirst() || cursor.count == 0)
{
return null
}
else
{

val sessionAttendance = cursorToSessionAttendance(cursor)
cursor.close()
return sessionAttendance
}
}

 fun getSessionAttendancesBySessionId(sessionId:String):List<SessionAttendance> {
val db = writableDatabase
val whereClause = "$TRAINING_SESSION_ID = ?"
val whereArgs = arrayOf(sessionId)
val cursor = db.query(TABLE_SESSION_ATTENDANCE, sessionAttendanceColumns, whereClause,
whereArgs, null, null, null, null)
val sessionAttendanceList = ArrayList<SessionAttendance>()
cursor.moveToFirst()
while (!cursor.isAfterLast)
{
val sessionAttendance = cursorToSessionAttendance(cursor)
sessionAttendanceList.add(sessionAttendance)
cursor.moveToNext()
}
cursor.close()
return sessionAttendanceList
}

 fun getNotAttendedSessionAttendancesBySessionId(sessionId:String,
attended:String):List<SessionAttendance> {
val db = writableDatabase
val whereClause = "$TRAINING_SESSION_ID = ? AND $ATTENDED = ? "
val whereArgs = arrayOf(sessionId, attended)
val cursor = db.query(TABLE_SESSION_ATTENDANCE, sessionAttendanceColumns, whereClause,
whereArgs, null, null, null, null)
val sessionAttendanceList = ArrayList<SessionAttendance>()
cursor.moveToFirst()
while (!cursor.isAfterLast)
{
val sessionAttendance = cursorToSessionAttendance(cursor)
sessionAttendanceList.add(sessionAttendance)
cursor.moveToNext()
}
cursor.close()
return sessionAttendanceList
}

 fun getSessionAttendancesByTraineeId(traineeId:String):List<SessionAttendance> {
val db = writableDatabase
val whereClause = "$TRAINEE_ID = ?"
val whereArgs = arrayOf(traineeId)
val cursor = db.query(TABLE_SESSION_ATTENDANCE, sessionAttendanceColumns, whereClause,
whereArgs, null, null, null, null)
val sessionAttendanceList = ArrayList<SessionAttendance>()
cursor.moveToFirst()
while (!cursor.isAfterLast)
{
val sessionAttendance = cursorToSessionAttendance(cursor)
sessionAttendanceList.add(sessionAttendance)
cursor.moveToNext()
}
cursor.close()
return sessionAttendanceList
}

 fun getSessionAttendancesByTraineeIdAndTrainindId(traineeId:String,
trainingId:String, sessionId:String):SessionAttendance? {
val db = writableDatabase
val whereClause = "$TRAINEE_ID = ? AND $TRAINING_ID = ? AND $TRAINING_SESSION_ID = ?"
val whereArgs = arrayOf(traineeId, trainingId, sessionId)
val cursor = db.query(TABLE_SESSION_ATTENDANCE, sessionAttendanceColumns, whereClause,
whereArgs, null, null, null, null)
if (!cursor.moveToFirst() || cursor.count == 0)
{
return null
}
else
{

val sessionAttendance = cursorToSessionAttendance(cursor)
cursor.close()
return sessionAttendance
}
}

 fun sessionAttendanceFromJSON(jsonObject:JSONObject) {
val sessionAttendance = SessionAttendance()
try
{
sessionAttendance.id = jsonObject.getString(ID)
if (!jsonObject.isNull(TRAINING_SESSION_ID))
{
sessionAttendance.trainingSessionId = jsonObject.getString(TRAINING_SESSION_ID)
}
if (!jsonObject.isNull(TRAINEE_ID))
{
sessionAttendance.traineeId = jsonObject.getString(TRAINEE_ID)
}
if (!jsonObject.isNull(TRAINING_SESSION_TYPE_ID))
{
sessionAttendance.trainingSessionTypeId = jsonObject.getInt(TRAINING_SESSION_TYPE_ID)
}
if (!jsonObject.isNull(TRAINING_ID))
{
sessionAttendance.trainingId = jsonObject.getString(TRAINING_ID)
}
if (!jsonObject.isNull(COUNTRY))
{
sessionAttendance.country = jsonObject.getString(COUNTRY)
}
if (!jsonObject.isNull(ATTENDED))
{
sessionAttendance.isAttended = jsonObject.getInt(ATTENDED) == 1
}
if (!jsonObject.isNull(CREATED_BY))
{
sessionAttendance.createdBy = jsonObject.getInt(CREATED_BY)
}
if (!jsonObject.isNull(CLIENT_TIME))
{
sessionAttendance.clientTime = jsonObject.getLong(CLIENT_TIME)
}
if (!jsonObject.isNull(DATE_CREATED))
{
sessionAttendance.dateCreated = jsonObject.getString(DATE_CREATED)
}
if (!jsonObject.isNull(META_DATA))
{
sessionAttendance.metaData = jsonObject.getString(META_DATA)
}
if (!jsonObject.isNull(COMMENT))
{
sessionAttendance.comment = jsonObject.getString(COMMENT)
}
if (!jsonObject.isNull(ARCHIVED))
{
sessionAttendance.isArchived = jsonObject.getInt(ARCHIVED) == 1
}
if (!jsonObject.isNull(ATTENDED))
{
if (jsonObject.getInt(ATTENDED) != 1)
{
this.addSessionAttendance(sessionAttendance)
}
}

}
catch (e:Exception) {
Log.d("TremapJSON", e.message)
}

}

 fun trainingSessionAttendanceToUploadJson(trainingId:String):JSONObject {
val db = writableDatabase
val whereClause = "$TRAINING_ID = ? AND $ATTENDED = ?"
val whereArgs = arrayOf(trainingId, "1")
val cursor = db.query(TABLE_SESSION_ATTENDANCE, sessionAttendanceColumns, whereClause,
whereArgs, null, null, null, null)
return cursorToJson(cursor, TABLE_SESSION_ATTENDANCE)
}

 fun allTrainingSessionAttendanceToJson():JSONObject {
val db = writableDatabase
val cursor = db.query(TABLE_SESSION_ATTENDANCE, sessionAttendanceColumns, null, null, null, null, null, null)
return cursorToJson(cursor, TABLE_SESSION_ATTENDANCE)
}

/**
 * ************************************
 * SESSION_TOPIC         *
 * ************************************
 */

    private fun cursorToSessionTopic(cursor:Cursor):SessionTopic {
val sessionTopic = SessionTopic()
sessionTopic.id = cursor.getInt(cursor.getColumnIndex(ID))
sessionTopic.name = cursor.getString(cursor.getColumnIndex(NAME))
sessionTopic.country = cursor.getString(cursor.getColumnIndex(COUNTRY))
sessionTopic.isArchived = cursor.getInt(cursor.getColumnIndex(ARCHIVED)) == 1
sessionTopic.addedBy = cursor.getInt(cursor.getColumnIndex(ADDED_BY))
sessionTopic.dateAdded = cursor.getString(cursor.getColumnIndex(DATE_ADDED))
sessionTopic.metaData = cursor.getString(cursor.getColumnIndex(META_DATA))
sessionTopic.comment = cursor.getString(cursor.getColumnIndex(COMMENT))
return sessionTopic
}

 fun sessionTopicFromJson(jsonObject:JSONObject) {
val sessionTopic = SessionTopic()
try
{
sessionTopic.id = jsonObject.getInt(ID)
if (!jsonObject.isNull(NAME))
{
sessionTopic.name = jsonObject.getString(NAME)
}
if (!jsonObject.isNull(COUNTRY))
{
sessionTopic.country = jsonObject.getString(COUNTRY)
}
if (!jsonObject.isNull(ARCHIVED))
{
sessionTopic.isArchived = jsonObject.getInt(ARCHIVED) == 0
}
if (!jsonObject.isNull(ADDED_BY))
{
sessionTopic.addedBy = jsonObject.getInt(ADDED_BY)
}
if (!jsonObject.isNull(META_DATA))
{
sessionTopic.metaData = jsonObject.getString(META_DATA)
}
if (!jsonObject.isNull(COMMENT))
{
sessionTopic.comment = jsonObject.getString(COMMENT)
}
this.addSessionTopic(sessionTopic)
}
catch (e:Exception) {
Log.d("Tremap", "=======ERR Creating Training session ============")
Log.d("Tremap", e.message)
Log.d("Tremap", "===================")
}

}

 fun addSessionTopic(sessionTopic:SessionTopic):Long {
val db = writableDatabase
val cv = ContentValues()
cv.put(ID, sessionTopic.id)
cv.put(NAME, sessionTopic.name)
cv.put(COUNTRY, sessionTopic.country)
cv.put(ARCHIVED, sessionTopic.isArchived)
cv.put(ADDED_BY, sessionTopic.addedBy)
cv.put(DATE_ADDED, sessionTopic.dateAdded)
cv.put(META_DATA, sessionTopic.metaData)
cv.put(COMMENT, sessionTopic.comment)
val id:Long
if (sessionTopicExists(sessionTopic))
{
id = db.update(TABLE_SESSION_TOPIC, cv, ID + "='" + sessionTopic.id + "'", null).toLong()
}
else
{
id = db.insertWithOnConflict(TABLE_SESSION_TOPIC, null, cv,
SQLiteDatabase.CONFLICT_REPLACE)
}
db.close()
return id
}

 fun sessionTopicExists(sessionTopic:SessionTopic):Boolean {
val db = readableDatabase
val cur = db.rawQuery("SELECT " + ID + " FROM " + TABLE_SESSION_TOPIC + " WHERE " + ID + " = '"
+ sessionTopic.id + "'", null)
val exist = cur.count > 0
cur.close()
return exist

}

 fun getSessionTopicById(sessionTopicId:String):SessionTopic? {
val db = writableDatabase
val whereClause = "$ID = ?"
val whereArgs = arrayOf(sessionTopicId)
val cursor = db.query(TABLE_SESSION_TOPIC, sessionTopicColumns, whereClause,
whereArgs, null, null, null, null)
if (!cursor.moveToFirst() || cursor.count == 0)
{
return null
}
else
{

val sessionTopic = cursorToSessionTopic(cursor)
cursor.close()
return sessionTopic
}
}


/**
 * ************************************
 * TRAINING_SESSION         *
 * ************************************
 */

    private fun cursorToTrainingSession(cursor:Cursor):TrainingSession {
val trainingSession = TrainingSession()
trainingSession.id = cursor.getString(cursor.getColumnIndex(ID))
trainingSession.trainingSessionTypeId = cursor.getInt(cursor.getColumnIndex(
TRAINING_SESSION_TYPE_ID))
trainingSession.classId = cursor.getInt(cursor.getColumnIndex(CLASS_ID))
trainingSession.trainingId = cursor.getString(cursor.getColumnIndex(TRAINING_ID))
trainingSession.trainerId = cursor.getInt(cursor.getColumnIndex(TRAINER_ID))
trainingSession.country = cursor.getString(cursor.getColumnIndex(COUNTRY))
trainingSession.isArchived = cursor.getInt(cursor.getColumnIndex(ARCHIVED)) == 1
trainingSession.sessionStartTime = cursor.getLong(cursor.getColumnIndex(SESSION_START_TIME))
trainingSession.sessionEndTime = cursor.getLong(cursor.getColumnIndex(SESSION_END_TIME))
trainingSession.sessionTopicId = cursor.getInt(cursor.getColumnIndex(SESSION_TOPIC_ID))
trainingSession.sessionLeadTrainer = cursor.getInt(cursor.getColumnIndex(SESSION_LEAD_TRAINER))
trainingSession.clientTime = cursor.getLong(cursor.getColumnIndex(CLIENT_TIME))
trainingSession.createdBy = cursor.getInt(cursor.getColumnIndex(CREATED_BY))
trainingSession.dateCreated = cursor.getString(cursor.getColumnIndex(DATE_CREATED))
trainingSession.comment = cursor.getString(cursor.getColumnIndex(COMMENT))
return trainingSession
}

 fun addTrainingSession(trainingSession:TrainingSession):Long {
val db = writableDatabase
val cv = ContentValues()

cv.put(ID, trainingSession.id)
cv.put(TRAINING_SESSION_TYPE_ID, trainingSession.trainingSessionTypeId)
cv.put(CLASS_ID, trainingSession.classId)
cv.put(TRAINING_ID, trainingSession.trainingId)
cv.put(TRAINER_ID, trainingSession.trainerId)
cv.put(COUNTRY, trainingSession.country)
cv.put(ARCHIVED, trainingSession.isArchived)
cv.put(SESSION_START_TIME, trainingSession.sessionStartTime)
cv.put(SESSION_END_TIME, trainingSession.sessionEndTime)
cv.put(SESSION_TOPIC_ID, trainingSession.sessionTopicId)
cv.put(SESSION_LEAD_TRAINER, trainingSession.sessionLeadTrainer)
cv.put(CLIENT_TIME, trainingSession.clientTime)
cv.put(CREATED_BY, trainingSession.createdBy)
cv.put(DATE_CREATED, trainingSession.dateCreated)
cv.put(COMMENT, trainingSession.comment)
val id:Long
if (trainingSessionExists(trainingSession))
{
id = db.update(TABLE_TRAINING_SESSION, cv, ID + "='" + trainingSession.id + "'", null).toLong()
}
else
{
id = db.insertWithOnConflict(TABLE_TRAINING_SESSION, null, cv,
SQLiteDatabase.CONFLICT_REPLACE)
}
db.close()
return id
}

 fun trainingSessionExists(trainingSession:TrainingSession):Boolean {
val db = readableDatabase
val cur = db.rawQuery("SELECT " + ID + " FROM " + TABLE_TRAINING_SESSION + " WHERE " +
ID + " = '" + trainingSession.id + "'", null)
val exist = cur.count > 0
cur.close()
return exist

}

 fun getTrainingSessionById(trainingSessionId:String):TrainingSession? {
val db = writableDatabase
val whereClause = "$ID = ?"
val whereArgs = arrayOf(trainingSessionId)
val cursor = db.query(TABLE_TRAINING_SESSION, trainingSessionColumns, whereClause,
whereArgs, null, null, null, null)
if (!cursor.moveToFirst() || cursor.count == 0)
{
return null
}
else
{

val trainingSession = cursorToTrainingSession(cursor)
cursor.close()
return trainingSession
}
}

 fun getTrainingSessionsByTrainingId(trainingId:String):List<TrainingSession> {
val db = writableDatabase
val whereClause = "$TRAINING_ID = ?"
val whereArgs = arrayOf(trainingId)

val cursor = db.query(TABLE_TRAINING_SESSION, trainingSessionColumns, whereClause, whereArgs, null, null, null, null)
val trainingSessionList = ArrayList<TrainingSession>()
cursor.moveToFirst()
while (!cursor.isAfterLast)
{
val trainingSession = cursorToTrainingSession(cursor)
trainingSessionList.add(trainingSession)
cursor.moveToNext()
}
cursor.close()
return trainingSessionList
}

 fun trainingSessionFromJson(jsonObject:JSONObject) {
val trainingSession = TrainingSession()
try
{
trainingSession.id = jsonObject.getString(ID)
if (!jsonObject.isNull(TRAINING_SESSION_TYPE_ID))
{
trainingSession.trainingSessionTypeId = jsonObject.getInt(TRAINING_SESSION_TYPE_ID)
}
if (!jsonObject.isNull(CLASS_ID))
{
trainingSession.classId = jsonObject.getInt(CLASS_ID)
}
if (!jsonObject.isNull(TRAINING_ID))
{
trainingSession.trainingId = jsonObject.getString(TRAINING_ID)
}
if (!jsonObject.isNull(TRAINER_ID))
{
trainingSession.trainerId = jsonObject.getInt(TRAINER_ID)
}
if (!jsonObject.isNull(COUNTRY))
{
trainingSession.country = jsonObject.getString(COUNTRY)
}
if (!jsonObject.isNull(ARCHIVED))
{
trainingSession.isArchived = jsonObject.getInt(ARCHIVED) == 1
}
if (!jsonObject.isNull(SESSION_START_TIME))
{
trainingSession.sessionStartTime = jsonObject.getLong(SESSION_START_TIME)
}
if (!jsonObject.isNull(SESSION_END_TIME))
{
trainingSession.sessionEndTime = jsonObject.getLong(SESSION_END_TIME)
}
if (!jsonObject.isNull(SESSION_TOPIC_ID))
{
trainingSession.sessionTopicId = jsonObject.getInt(SESSION_TOPIC_ID)
}
if (!jsonObject.isNull(SESSION_LEAD_TRAINER))
{
trainingSession.sessionLeadTrainer = jsonObject.getInt(SESSION_LEAD_TRAINER)
}
if (!jsonObject.isNull(CLIENT_TIME))
{
trainingSession.clientTime = jsonObject.getLong(CLIENT_TIME)
}
if (!jsonObject.isNull(CREATED_BY))
{
trainingSession.createdBy = jsonObject.getInt(CREATED_BY)
}
if (!jsonObject.isNull(DATE_CREATED))
{
trainingSession.dateCreated = jsonObject.getString(DATE_CREATED)
}
if (!jsonObject.isNull(COMMENT))
{
trainingSession.comment = jsonObject.getString(COMMENT)
}
this.addTrainingSession(trainingSession)
}
catch (e:Exception) {
Log.d("Tremap", e.message)
}

}

/**
 * ************************************
 * TRAINING SESSION TYPE    *
 * ************************************
 */

    private fun cursorToTrainingSessionType(cursor:Cursor):TrainingSessionType {
val trainingSessionType = TrainingSessionType()
trainingSessionType.id = cursor.getString(cursor.getColumnIndex(ID))
trainingSessionType.sessionName = cursor.getString(cursor.getColumnIndex(SESSION_NAME))
trainingSessionType.classId = cursor.getInt(cursor.getColumnIndex(CLASS_ID))
trainingSessionType.country = cursor.getString(cursor.getColumnIndex(COUNTRY))
trainingSessionType.isArchived = cursor.getInt(cursor.getColumnIndex(ARCHIVED)) == 1
trainingSessionType.clientTime = cursor.getLong(cursor.getColumnIndex(CLIENT_TIME))
trainingSessionType.createdBy = cursor.getInt(cursor.getColumnIndex(CREATED_BY))
trainingSessionType.dateCreated = cursor.getString(cursor.getColumnIndex(DATE_CREATED))
trainingSessionType.comment = cursor.getString(cursor.getColumnIndex(COMMENT))
return trainingSessionType
}

 fun addTrainingSessionType(trainingSessionType:TrainingSessionType):Long {
val db = writableDatabase
val cv = ContentValues()
cv.put(ID, trainingSessionType.id)
cv.put(SESSION_NAME, trainingSessionType.sessionName)
cv.put(CLASS_ID, trainingSessionType.classId)
cv.put(COUNTRY, trainingSessionType.country)
cv.put(ARCHIVED, trainingSessionType.isArchived)
cv.put(CLIENT_TIME, trainingSessionType.clientTime)
cv.put(CREATED_BY, trainingSessionType.createdBy)
cv.put(DATE_CREATED, trainingSessionType.dateCreated)
cv.put(COMMENT, trainingSessionType.comment)
val id:Long
if (trainingSessionTypeExists(trainingSessionType))
{
id = db.update(TABLE_TRAINING_SESSION_TYPE, cv, ID + "='" + trainingSessionType.id + "'", null).toLong()
}
else
{
id = db.insertWithOnConflict(TABLE_TRAINING_SESSION_TYPE, null, cv,
SQLiteDatabase.CONFLICT_REPLACE)
}
db.close()
return id
}

 fun trainingSessionTypeExists(trainingSessionType:TrainingSessionType):Boolean {
val db = readableDatabase
val cur = db.rawQuery("SELECT " + ID + " FROM " + TABLE_TRAINING_SESSION_TYPE + " WHERE " +
ID + " = '" + trainingSessionType.id + "'", null)
val exist = cur.count > 0
cur.close()
return exist

}

 fun getTrainingSessionTypeById(trainingSessionTypeId:String):TrainingSessionType? {
val db = writableDatabase
val whereClause = "$ID = ?"
val whereArgs = arrayOf(trainingSessionTypeId)
val cursor = db.query(TABLE_TRAINING_SESSION_TYPE, trainingSessionTypeColumns, whereClause,
whereArgs, null, null, null, null)
if (!cursor.moveToFirst() || cursor.count == 0)
{
return null
}
else
{

val trainingSessionType = cursorToTrainingSessionType(cursor)
cursor.close()
return trainingSessionType
}
}

/**
 * ************************************
 * TRAINING_STATUS          *
 * ************************************
 */

    private fun cursorToTrainingStatus(cursor:Cursor):TrainingStatus {
val trainingStatus = TrainingStatus()
trainingStatus.id = cursor.getInt(cursor.getColumnIndex(ID))
trainingStatus.name = cursor.getString(cursor.getColumnIndex(NAME))
trainingStatus.isArchived = cursor.getInt(cursor.getColumnIndex(ARCHIVED)) == 1
trainingStatus.isReadonly = cursor.getInt(cursor.getColumnIndex(READONLY)) == 1
trainingStatus.country = cursor.getString(cursor.getColumnIndex(COUNTRY))
trainingStatus.clientTime = cursor.getLong(cursor.getColumnIndex(CLIENT_TIME))
trainingStatus.createdBy = cursor.getInt(cursor.getColumnIndex(CREATED_BY))
trainingStatus.dateCreated = cursor.getLong(cursor.getColumnIndex(DATE_CREATED))
trainingStatus.comment = cursor.getString(cursor.getColumnIndex(COMMENT))
return trainingStatus
}

 fun addTrainingStatus(trainingStatus:TrainingStatus):Long {
val db = writableDatabase
val cv = ContentValues()
cv.put(ID, trainingStatus.id)
cv.put(NAME, trainingStatus.name)
cv.put(ARCHIVED, trainingStatus.isArchived)
cv.put(READONLY, trainingStatus.isReadonly)
cv.put(COUNTRY, trainingStatus.country)
cv.put(CLIENT_TIME, trainingStatus.clientTime)
cv.put(CREATED_BY, trainingStatus.createdBy)
cv.put(DATE_CREATED, trainingStatus.dateCreated)
cv.put(COMMENT, trainingStatus.comment)
val id:Long
if (trainingStatusExists(trainingStatus))
{
id = db.update(TABLE_TRAINING_STATUS, cv, ID + "='" + trainingStatus.id + "'", null).toLong()
}
else
{
id = db.insertWithOnConflict(TABLE_TRAINING_STATUS, null, cv,
SQLiteDatabase.CONFLICT_REPLACE)
}
db.close()
return id
}

 fun trainingStatusExists(trainingStatus:TrainingStatus):Boolean {
val db = readableDatabase
val cur = db.rawQuery("SELECT " + ID + " FROM " + TABLE_TRAINING_STATUS + " WHERE " +
ID + " = '" + trainingStatus.id + "'", null)
val exist = cur.count > 0
cur.close()
return exist

}

 fun getTrainingStatusById(trainingStatusId:String):TrainingStatus? {
val db = writableDatabase
val whereClause = "$ID = ?"
val whereArgs = arrayOf(trainingStatusId)
val cursor = db.query(TABLE_TRAINING_STATUS, trainingStatusColumns, whereClause,
whereArgs, null, null, null, null)
if (!cursor.moveToFirst() || cursor.count == 0)
{
return null
}
else
{

val trainingStatus = cursorToTrainingStatus(cursor)
cursor.close()
return trainingStatus
}
}

/**
 * ************************************
 * TRAINING ROLES           *
 * ************************************
 */

    private fun cursorToTrainingRole(cursor:Cursor):TrainingRole {
val trainingRole = TrainingRole()
trainingRole.id = cursor.getInt(cursor.getColumnIndex(ID))
trainingRole.roleName = cursor.getString(cursor.getColumnIndex(ROLE_NAME))
trainingRole.isArchived = cursor.getInt(cursor.getColumnIndex(ARCHIVED)) == 1
trainingRole.isReadonly = cursor.getInt(cursor.getColumnIndex(READONLY)) == 1
trainingRole.country = cursor.getString(cursor.getColumnIndex(COUNTRY))
trainingRole.clientTime = cursor.getLong(cursor.getColumnIndex(CLIENT_TIME))
trainingRole.createdBy = cursor.getInt(cursor.getColumnIndex(CREATED_BY))
trainingRole.dateCreated = cursor.getString(cursor.getColumnIndex(DATE_CREATED))
trainingRole.comment = cursor.getString(cursor.getColumnIndex(COMMENT))
return trainingRole
}

 fun addTrainingRole(trainingRole:TrainingRole):Long {
val db = writableDatabase
val cv = ContentValues()
cv.put(ID, trainingRole.id)
cv.put(ROLE_NAME, trainingRole.roleName)
cv.put(ARCHIVED, trainingRole.isArchived)
cv.put(READONLY, trainingRole.isReadonly)
cv.put(COUNTRY, trainingRole.country)
cv.put(CLIENT_TIME, trainingRole.clientTime)
cv.put(CREATED_BY, trainingRole.createdBy)
cv.put(DATE_CREATED, trainingRole.dateCreated)
cv.put(COMMENT, trainingRole.comment)
val id:Long
if (trainingRoleExists(trainingRole))
{
id = db.update(TABLE_TRAINING_ROLES, cv, ID + "='" + trainingRole.id + "'", null).toLong()
}
else
{
id = db.insertWithOnConflict(TABLE_TRAINING_ROLES, null, cv,
SQLiteDatabase.CONFLICT_REPLACE)
}
db.close()
return id
}

 fun trainingRoleExists(trainingRole:TrainingRole):Boolean {
val db = readableDatabase
val cur = db.rawQuery("SELECT " + ID + " FROM " + TABLE_TRAINING_ROLES + " WHERE " +
ID + " = '" + trainingRole.id + "'", null)
val exist = cur.count > 0
cur.close()
return exist

}

 fun getTrainingRoleById(trainingRoleId:String):TrainingRole? {
val db = writableDatabase
val whereClause = "$ID = ?"
val whereArgs = arrayOf(trainingRoleId)
val cursor = db.query(TABLE_TRAINING_ROLES, trainingRoleColumns, whereClause,
whereArgs, null, null, null, null)
if (!cursor.moveToFirst() || cursor.count == 0)
{
return null
}
else
{

val trainingRole = cursorToTrainingRole(cursor)
cursor.close()
return trainingRole
}
}

/**
 * ************************************
 * TRAINING TRAINER           *
 * ************************************
 */

    private fun cursorToTrainingTrainer(cursor:Cursor):TrainingTrainer {
val trainingTrainer = TrainingTrainer()
trainingTrainer.id = cursor.getInt(cursor.getColumnIndex(ID))
trainingTrainer.trainingId = cursor.getString(cursor.getColumnIndex(TRAINING_ID))
trainingTrainer.classId = cursor.getInt(cursor.getColumnIndex(CLASS_ID))
trainingTrainer.trainerId = cursor.getInt(cursor.getColumnIndex(TRAINER_ID))
trainingTrainer.country = cursor.getString(cursor.getColumnIndex(COUNTRY))
trainingTrainer.clientTime = cursor.getLong(cursor.getColumnIndex(CLIENT_TIME))
trainingTrainer.createdBy = cursor.getInt(cursor.getColumnIndex(CREATED_BY))
trainingTrainer.dateCreated = cursor.getString(cursor.getColumnIndex(DATE_CREATED))
trainingTrainer.isArchived = cursor.getInt(cursor.getColumnIndex(ARCHIVED)) == 1
trainingTrainer.trainingRoleId = cursor.getInt(cursor.getColumnIndex(TRAINING_ROLE_ID))
trainingTrainer.comment = cursor.getString(cursor.getColumnIndex(COMMENT))
return trainingTrainer
}

 fun addTrainingTrainer(trainingTrainer:TrainingTrainer):Long {
val db = writableDatabase
val cv = ContentValues()
cv.put(ID, trainingTrainer.id)
cv.put(TRAINING_ID, trainingTrainer.trainingId)
cv.put(CLASS_ID, trainingTrainer.classId)
cv.put(TRAINER_ID, trainingTrainer.trainerId)
cv.put(COUNTRY, trainingTrainer.country)
cv.put(CLIENT_TIME, trainingTrainer.clientTime)
cv.put(CREATED_BY, trainingTrainer.createdBy)
cv.put(DATE_CREATED, trainingTrainer.dateCreated)
cv.put(ARCHIVED, trainingTrainer.isArchived)
cv.put(TRAINING_ROLE_ID, trainingTrainer.trainingRoleId)
cv.put(COMMENT, trainingTrainer.comment)
val id:Long
if (trainingTrainerExists(trainingTrainer))
{
id = db.update(TABLE_TRAINING_TRAINERS, cv, ID + "='" + trainingTrainer.id + "'", null).toLong()
}
else
{
id = db.insertWithOnConflict(TABLE_TRAINING_TRAINERS, null, cv,
SQLiteDatabase.CONFLICT_REPLACE)
}
db.close()
return id
}

 fun trainingTrainerExists(trainingTrainer:TrainingTrainer):Boolean {
val db = readableDatabase
val cur = db.rawQuery("SELECT " + ID + " FROM " + TABLE_TRAINING_TRAINERS + " WHERE " +
ID + " = '" + trainingTrainer.id + "'", null)
val exist = cur.count > 0
cur.close()
return exist

}

 fun getTrainingTrainerById(trainingTrainerId:String):TrainingTrainer? {
val db = writableDatabase
val whereClause = "$ID = ?"
val whereArgs = arrayOf(trainingTrainerId)
val cursor = db.query(TABLE_TRAINING_TRAINERS, trainingTrainerColumns, whereClause,
whereArgs, null, null, null, null)
if (!cursor.moveToFirst() || cursor.count == 0)
{
return null
}
else
{

val trainingTrainer = cursorToTrainingTrainer(cursor)
cursor.close()
return trainingTrainer
}
}

/**
 * ************************************
 * TRAINING CLASSES           *
 * ************************************
 */

    private fun cursorToTrainingClass(cursor:Cursor):TrainingClass {
val trainingClass = TrainingClass()
trainingClass.id = cursor.getInt(cursor.getColumnIndex(ID))
trainingClass.trainingId = cursor.getString(cursor.getColumnIndex(TRAINING_ID))
trainingClass.className = cursor.getString(cursor.getColumnIndex(CLASS_NAME))
trainingClass.country = cursor.getString(cursor.getColumnIndex(COUNTRY))
trainingClass.clientTime = cursor.getLong(cursor.getColumnIndex(CLIENT_TIME))
trainingClass.createdBy = cursor.getInt(cursor.getColumnIndex(CREATED_BY))
trainingClass.dateCreated = cursor.getString(cursor.getColumnIndex(DATE_CREATED))
trainingClass.isArchived = cursor.getInt(cursor.getColumnIndex(ARCHIVED)) == 1
trainingClass.comment = cursor.getString(cursor.getColumnIndex(COMMENT))
return trainingClass
}

 fun trainingClassFromJson(jsonObject:JSONObject) {
val trainingClass = TrainingClass()
try
{
trainingClass.id = jsonObject.getInt(ID)
if (!jsonObject.isNull(TRAINING_ID))
{
trainingClass.trainingId = jsonObject.getString(TRAINING_ID)
}
if (!jsonObject.isNull(CLASS_NAME))
{
trainingClass.className = jsonObject.getString(CLASS_NAME)
}
if (!jsonObject.isNull(COUNTRY))
{
trainingClass.country = jsonObject.getString(COUNTRY)
}
if (!jsonObject.isNull(CLIENT_TIME))
{
trainingClass.clientTime = jsonObject.getLong(CLIENT_TIME)
}
if (!jsonObject.isNull(CREATED_BY))
{
trainingClass.createdBy = jsonObject.getInt(CREATED_BY)
}
if (!jsonObject.isNull(DATE_CREATED))
{
trainingClass.dateCreated = jsonObject.getString(DATE_CREATED)
}
if (!jsonObject.isNull(ARCHIVED))
{
trainingClass.isArchived = jsonObject.getInt(ARCHIVED) == 1
}
if (!jsonObject.isNull(COMMENT))
{
trainingClass.comment = jsonObject.getString(COMMENT)
}
Log.d("Tremap", "=======Creating Training Class from JSON============")
Log.d("Tremap", "=======Creating Training Class from JSON ============" + trainingClass.trainingId)
this.addTrainingClass(trainingClass)
}
catch (e:Exception) {
Log.d("Tremap", "=======ERR Creating Training Class ============")
Log.d("Tremap", e.message)
Log.d("Tremap", "===================")
}

}


 fun addTrainingClass(trainingClass:TrainingClass):Long {
val db = writableDatabase
val cv = ContentValues()
cv.put(ID, trainingClass.id)
cv.put(TRAINING_ID, trainingClass.trainingId)
cv.put(CLASS_NAME, trainingClass.className)
cv.put(COUNTRY, trainingClass.country)
cv.put(CLIENT_TIME, trainingClass.clientTime)
cv.put(CREATED_BY, trainingClass.createdBy)
cv.put(DATE_CREATED, trainingClass.dateCreated)
cv.put(ARCHIVED, trainingClass.isArchived)
cv.put(COMMENT, trainingClass.comment)

val id:Long
if (trainingClassExists(trainingClass))
{
id = db.update(TABLE_TRAINING_CLASSES, cv, ID + "='" + trainingClass.id + "'", null).toLong()
Log.d("Tremap", "======================------------==========")
Log.d("Tremap", "Updated Class Details")
Log.d("Tremap", "======================------------==========")
}
else
{
id = db.insertWithOnConflict(TABLE_TRAINING_CLASSES, null, cv,
SQLiteDatabase.CONFLICT_REPLACE)
Log.d("Tremap", "======================------------==========")
Log.d("Tremap", "Created Class Details")
Log.d("Tremap", "======================------------==========")
}
db.close()
return id
}

 fun trainingClassExists(trainingClass:TrainingClass):Boolean {
val db = readableDatabase
val cur = db.rawQuery("SELECT " + ID + " FROM " + TABLE_TRAINING_CLASSES + " WHERE " +
ID + " = '" + trainingClass.id + "'", null)
val exist = cur.count > 0
cur.close()
return exist

}

 fun getTrainingClassById(trainingClassId:String):TrainingClass? {
val db = writableDatabase
val whereClause = "$ID = ?"
val whereArgs = arrayOf(trainingClassId)
val cursor = db.query(TABLE_TRAINING_CLASSES, trainingClassesColumns, whereClause,
whereArgs, null, null, null, null)
if (!cursor.moveToFirst() || cursor.count == 0)
{
return null
}
else
{

val trainingClass = cursorToTrainingClass(cursor)
cursor.close()
return trainingClass
}
}

 fun getTrainingClassByTrainingId(trainingId:String):List<TrainingClass> {
val db = writableDatabase
val whereClause = "$TRAINING_ID = ?"
val whereArgs = arrayOf(trainingId)
val cursor = db.query(TABLE_TRAINING_CLASSES, trainingClassesColumns, whereClause,
whereArgs, null, null, null, null)
val trainingClassList = ArrayList<TrainingClass>()
cursor.moveToFirst()
while (!cursor.isAfterLast)
{
trainingClassList.add(cursorToTrainingClass(cursor))
cursor.moveToNext()
}
cursor.close()
return trainingClassList
}

/**
 * ************************************
 * TRAINING TRAINEES          *
 * ************************************
 */

    private fun cursorToTrainingTrainee(cursor:Cursor):TrainingTrainee {
val trainingTrainee = TrainingTrainee()
trainingTrainee.id = cursor.getString(cursor.getColumnIndex(ID))
trainingTrainee.registrationId = cursor.getString(cursor.getColumnIndex(REGISTRATION_ID))
trainingTrainee.classId = cursor.getInt(cursor.getColumnIndex(CLASS_ID))
trainingTrainee.trainingId = cursor.getString(cursor.getColumnIndex(TRAINING_ID))
trainingTrainee.country = cursor.getString(cursor.getColumnIndex(COUNTRY))
trainingTrainee.addedBy = cursor.getInt(cursor.getColumnIndex(ADDED_BY))
trainingTrainee.dateCreated = cursor.getString(cursor.getColumnIndex(DATE_CREATED))
trainingTrainee.clientTime = cursor.getLong(cursor.getColumnIndex(CLIENT_TIME))
trainingTrainee.branch = cursor.getString(cursor.getColumnIndex(BRANCH))
trainingTrainee.cohort = cursor.getInt(cursor.getColumnIndex(COHORT))
trainingTrainee.chpCode = cursor.getString(cursor.getColumnIndex(CHP_CODE))
trainingTrainee.isArchived = cursor.getInt(cursor.getColumnIndex(ARCHIVED)) == 1
trainingTrainee.traineeStatus = cursor.getInt(cursor.getColumnIndex(TRAINEE_STATUS))
try
{
trainingTrainee.registration = JSONObject(cursor.getString(cursor.getColumnIndex(REGISTRATION)))
}
catch (e:Exception) {}

trainingTrainee.comment = cursor.getString(cursor.getColumnIndex(COMMENT))
return trainingTrainee
}

 fun addTrainingTrainee(trainingTrainee:TrainingTrainee):Long {
val db = writableDatabase
val cv = ContentValues()
cv.put(ID, trainingTrainee.id)
cv.put(REGISTRATION_ID, trainingTrainee.registrationId)
cv.put(CLASS_ID, trainingTrainee.classId)
cv.put(TRAINING_ID, trainingTrainee.trainingId)
cv.put(COUNTRY, trainingTrainee.country)
cv.put(ADDED_BY, trainingTrainee.addedBy)
cv.put(DATE_CREATED, trainingTrainee.dateCreated)
cv.put(CLIENT_TIME, trainingTrainee.clientTime)
cv.put(BRANCH, trainingTrainee.branch)
cv.put(COHORT, trainingTrainee.cohort)
cv.put(CHP_CODE, trainingTrainee.chpCode)
cv.put(ARCHIVED, trainingTrainee.isArchived)
cv.put(REGISTRATION, trainingTrainee.registration.toString())
cv.put(COMMENT, trainingTrainee.comment)
cv.put(TRAINEE_STATUS, trainingTrainee.traineeStatus)
val id:Long
if (trainingTraineeExists(trainingTrainee))
{
id = db.update(TABLE_TRAINING_TRAINEES, cv, ID + "='" + trainingTrainee.id + "'", null).toLong()
Log.d("Tremap", "Updated Trainee " + trainingTrainee.id)
}
else
{
id = db.insertWithOnConflict(TABLE_TRAINING_TRAINEES, null, cv,
SQLiteDatabase.CONFLICT_REPLACE)
Log.d("Tremap", "Created Trainee " + trainingTrainee.id)
}
db.close()
return id
}

 fun trainingTraineeExists(trainingTrainee:TrainingTrainee):Boolean {
val db = readableDatabase
val cur = db.rawQuery("SELECT " + ID + " FROM " + TABLE_TRAINING_TRAINEES + " WHERE " +
ID + " = '" + trainingTrainee.id + "'", null)
val exist = cur.count > 0
cur.close()
return exist

}

 fun getTrainingTraineeById(trainingTraineeId:String):TrainingTrainee? {
val db = writableDatabase
val whereClause = "$ID = ?"
val whereArgs = arrayOf(trainingTraineeId)
val cursor = db.query(TABLE_TRAINING_TRAINEES, trainingTraineeColumns, whereClause,
whereArgs, null, null, null, null)
if (!cursor.moveToFirst() || cursor.count == 0)
{
return null
}
else
{

val trainingTrainee = cursorToTrainingTrainee(cursor)
cursor.close()
return trainingTrainee
}
}

 fun getTrainingTraineeByRegistrationId(registrationId:String):TrainingTrainee? {
val db = writableDatabase
val whereClause = "$REGISTRATION_ID = ?"
val whereArgs = arrayOf(registrationId)
val cursor = db.query(TABLE_TRAINING_TRAINEES, trainingTraineeColumns, whereClause,
whereArgs, null, null, null, null)
if (!cursor.moveToFirst() || cursor.count == 0)
{
return null
}
else
{

val trainingTrainee = cursorToTrainingTrainee(cursor)
cursor.close()
return trainingTrainee
}
}

 fun getTrainingTraineesByClassId(classId:String):List<TrainingTrainee> {
val db = writableDatabase
val whereClause = "$CLASS_ID = ?"
val whereArgs = arrayOf(classId)
val cursor = db.query(TABLE_TRAINING_TRAINEES, trainingTraineeColumns, whereClause,
whereArgs, null, null, null, null)
val trainingTraineeList = ArrayList<TrainingTrainee>()
cursor.moveToFirst()
while (!cursor.isAfterLast)
{
val trainingTrainee = cursorToTrainingTrainee(cursor)
trainingTraineeList.add(trainingTrainee)
cursor.moveToNext()
}
cursor.close()
return trainingTraineeList
}

 fun getTrainingTraineesByTrainingId(trainingId:String):List<TrainingTrainee> {
val db = writableDatabase
val whereClause = "$TRAINING_ID = ?"
val whereArgs = arrayOf(trainingId)
val cursor = db.query(TABLE_TRAINING_TRAINEES, trainingTraineeColumns, whereClause,
whereArgs, null, null, null, null)
val trainingTraineeList = ArrayList<TrainingTrainee>()
cursor.moveToFirst()
while (!cursor.isAfterLast)
{
val trainingTrainee = cursorToTrainingTrainee(cursor)
trainingTraineeList.add(trainingTrainee)
cursor.moveToNext()
}
cursor.close()
return trainingTraineeList
}

 fun trainingTraineeFromJson(jsonObject:JSONObject) {
val trainingTrainee = TrainingTrainee()
try
{
trainingTrainee.id = jsonObject.getString(ID)
if (!jsonObject.isNull(REGISTRATION_ID))
{
trainingTrainee.registrationId = jsonObject.getString(REGISTRATION_ID)
}
if (!jsonObject.isNull(CLASS_ID))
{
trainingTrainee.classId = jsonObject.getInt(CLASS_ID)
}
if (!jsonObject.isNull(TRAINING_ID))
{
trainingTrainee.trainingId = jsonObject.getString(TRAINING_ID)
}
if (!jsonObject.isNull(COUNTRY))
{
trainingTrainee.country = jsonObject.getString(COUNTRY)
}
if (!jsonObject.isNull(ADDED_BY))
{
trainingTrainee.addedBy = jsonObject.getInt(ADDED_BY)
}
if (!jsonObject.isNull(DATE_CREATED))
{
trainingTrainee.dateCreated = jsonObject.getString(DATE_CREATED)
}
if (!jsonObject.isNull(CLIENT_TIME))
{
trainingTrainee.clientTime = jsonObject.getLong(CLIENT_TIME)
}
if (!jsonObject.isNull(BRANCH))
{
trainingTrainee.branch = jsonObject.getString(BRANCH)
}
if (!jsonObject.isNull(COHORT))
{
trainingTrainee.cohort = jsonObject.getInt(COHORT)
}
if (!jsonObject.isNull(CHP_CODE))
{
trainingTrainee.chpCode = jsonObject.getString(CHP_CODE)
}
if (!jsonObject.isNull(ARCHIVED))
{
trainingTrainee.isArchived = jsonObject.getInt(ARCHIVED) == 1
}
if (!jsonObject.isNull(REGISTRATION))
{
trainingTrainee.registration = jsonObject.getJSONObject(REGISTRATION)
}
if (!jsonObject.isNull(COMMENT))
{
trainingTrainee.comment = jsonObject.getString(COMMENT)
}

if (!jsonObject.isNull(TRAINEE_STATUS))
{
trainingTrainee.traineeStatus = jsonObject.getInt(TRAINEE_STATUS)
}
this.addTrainingTrainee(trainingTrainee)
}
catch (e:Exception) {
Log.d("Tremap", "=======ERR Creating Training============")
Log.d("Tremap", e.message)
}

}

/**
 * ************************************
 * TRAINING COMMENTS          *
 * ************************************
 */

    private fun cursorToTrainingComment(cursor:Cursor):TrainingComment {
val trainingComment = TrainingComment()
trainingComment.id = cursor.getString(cursor.getColumnIndex(ID))
trainingComment.traineeId = cursor.getString(cursor.getColumnIndex(TRAINEE_ID))
trainingComment.trainingId = cursor.getString(cursor.getColumnIndex(TRAINING_ID))
trainingComment.country = cursor.getString(cursor.getColumnIndex(COUNTRY))
trainingComment.addedBy = cursor.getInt(cursor.getColumnIndex(ADDED_BY))
trainingComment.dateCreated = cursor.getString(cursor.getColumnIndex(DATE_CREATED))
trainingComment.clientTime = cursor.getLong(cursor.getColumnIndex(CLIENT_TIME))
trainingComment.isArchived = cursor.getInt(cursor.getColumnIndex(ARCHIVED)) == 1
trainingComment.comment = cursor.getString(cursor.getColumnIndex(COMMENT))

return trainingComment
}

 fun addTrainingComment(trainingComment:TrainingComment):Long {
val db = writableDatabase
val cv = ContentValues()
cv.put(ID, trainingComment.id)
cv.put(TRAINEE_ID, trainingComment.traineeId)
cv.put(TRAINING_ID, trainingComment.trainingId)
cv.put(COUNTRY, trainingComment.country)
cv.put(ADDED_BY, trainingComment.addedBy)
cv.put(DATE_CREATED, trainingComment.dateCreated)
cv.put(CLIENT_TIME, trainingComment.clientTime)
cv.put(ARCHIVED, trainingComment.isArchived)
cv.put(COMMENT, trainingComment.comment)
val id:Long
if (trainingCommentExists(trainingComment))
{
id = db.update(TABLE_TRAINING_COMMENTS, cv, ID + "='" + trainingComment.id + "'", null).toLong()
}
else
{
id = db.insertWithOnConflict(TABLE_TRAINING_COMMENTS, null, cv,
SQLiteDatabase.CONFLICT_REPLACE)
}
db.close()
return id
}

 fun trainingCommentExists(trainingComment:TrainingComment):Boolean {
val db = readableDatabase
val cur = db.rawQuery("SELECT " + ID + " FROM " + TABLE_TRAINING_COMMENTS + " WHERE " +
ID + " = '" + trainingComment.id + "'", null)
val exist = cur.count > 0
cur.close()
return exist

}

 fun getTrainingCommentById(trainingCommentId:String):TrainingComment? {
val db = writableDatabase
val whereClause = "$ID = ?"
val whereArgs = arrayOf(trainingCommentId)
val cursor = db.query(TABLE_TRAINING_COMMENTS, trainingCommentColumns, whereClause,
whereArgs, null, null, null, null)
if (!cursor.moveToFirst() || cursor.count == 0)
{
return null
}
else
{

val trainingComment = cursorToTrainingComment(cursor)
cursor.close()
return trainingComment
}
}

/**
 * ************************************
 * TRAINING COMMENTS          *
 * ************************************
 */

    private fun cursorToUser(cursor:Cursor):User {
val user = User()
user.id = cursor.getInt(cursor.getColumnIndex(ID))
user.email = cursor.getString(cursor.getColumnIndex(EMAIL))
user.userName = cursor.getString(cursor.getColumnIndex(USERNAME))
user.passWord = cursor.getString(cursor.getColumnIndex(PASSWORD))
user.name = cursor.getString(cursor.getColumnIndex(NAME))
user.country = cursor.getString(cursor.getColumnIndex(COUNTRY))
return user
}

 fun addUser(user:User):Long {
val db = writableDatabase
val cv = ContentValues()
cv.put(ID, user.id)
cv.put(EMAIL, user.email)
cv.put(USERNAME, user.userName)
cv.put(PASSWORD, user.passWord)
cv.put(NAME, user.name)
cv.put(COUNTRY, user.country)
val id:Long
if (userExists(user))
{
id = db.update(TABLE_USERS, cv, ID + "='" + user.id + "'", null).toLong()
}
else
{
id = db.insertWithOnConflict(TABLE_USERS, null, cv,
SQLiteDatabase.CONFLICT_REPLACE)
}
db.close()
return id
}

 fun userExists(user:User):Boolean {
val db = readableDatabase
val cur = db.rawQuery("SELECT " + ID + " FROM " + TABLE_USERS + " WHERE " +
ID + " = '" + user.id + "'", null)
val exist = cur.count > 0
cur.close()
return exist

}

 fun getUserById(userId:String):User? {
val db = writableDatabase
val whereClause = "$ID = ?"
val whereArgs = arrayOf(userId)
val cursor = db.query(TABLE_USERS, userColumns, whereClause,
whereArgs, null, null, null, null)
if (!cursor.moveToFirst() || cursor.count == 0)
{
return null
}
else
{

val user = cursorToUser(cursor)
cursor.close()
return user
}
}

 fun usersFromJson(jsonObject:JSONObject) {
val user = User()
try
{
user.id = jsonObject.getInt(ID)
user.email = jsonObject.getString(EMAIL)
user.userName = jsonObject.getString(USERNAME)
val appName = Base64.decode(jsonObject.getString("app_name"),
Base64.DEFAULT)
var pwd:String? = null
try
{
pwd = String(appName, "UTF-8")
}
catch (e:Exception) {}

user.passWord = pwd
user.name = jsonObject.getString(NAME)
user.country = jsonObject.getString(COUNTRY)
this.addUser(user)
}
catch (e:Exception) {}

}

 fun userJson():JSONObject {
val db = writableDatabase
val cursor = db.query(TABLE_USERS, userColumns, null, null, null, null, null, null)
return cursorToJson(cursor, "users")
}

/**
 * ************************************
 * BRANCH                     *
 * ************************************
 */

    private fun cursorToBranch(cursor:Cursor):Branch {
val branch = Branch()
branch.id = cursor.getString(cursor.getColumnIndex(ID))
branch.branchName = cursor.getString(cursor.getColumnIndex(BRANCH_NAME))
branch.branchCode = cursor.getString(cursor.getColumnIndex(BRANCH_CODE))
branch.mappingId = cursor.getString(cursor.getColumnIndex(MAPPING_ID))
branch.lat = cursor.getDouble(cursor.getColumnIndex(LAT))
branch.lon = cursor.getDouble(cursor.getColumnIndex(LON))
branch.isArchived = cursor.getInt(cursor.getColumnIndex(ARCHIVED)) == 1
return branch
}

 fun addBranch(branch:Branch):Long {
val db = writableDatabase
val cv = ContentValues()

cv.put(ID, branch.id)
cv.put(BRANCH_NAME, branch.branchName)
cv.put(BRANCH_CODE, branch.branchCode)
cv.put(MAPPING_ID, branch.mappingId)
cv.put(LAT, branch.lat)
cv.put(LON, branch.lon)
cv.put(ARCHIVED, branch.isArchived)
val id:Long
if (branchExists(branch))
{
id = db.update(TABLE_BRANCH, cv, ID + "='" + branch.id + "'", null).toLong()
}
else
{
id = db.insertWithOnConflict(TABLE_BRANCH, null, cv,
SQLiteDatabase.CONFLICT_REPLACE)
}
db.close()
return id
}

 fun branchExists(branch:Branch):Boolean {
val db = readableDatabase
val cur = db.rawQuery("SELECT " + ID + " FROM " + TABLE_BRANCH + " WHERE " +
ID + " = '" + branch.id + "'", null)
val exist = cur.count > 0
cur.close()
return exist

}

 fun getBranchById(branchId:String):Branch? {
val db = writableDatabase
val whereClause = "$ID = ?"
val whereArgs = arrayOf(branchId)
val cursor = db.query(TABLE_BRANCH, branchColumns, whereClause,
whereArgs, null, null, null, null)
if (!cursor.moveToFirst() || cursor.count == 0)
{
return null
}
else
{

val branch = cursorToBranch(cursor)
cursor.close()
return branch
}
}


/**
 * ************************************
 * COHORT                     *
 * ************************************
 */

    private fun cursorToCohort(cursor:Cursor):Cohort {
val cohort = Cohort()
cohort.id = cursor.getInt(cursor.getColumnIndex(ID))
cohort.cohortName = cursor.getString(cursor.getColumnIndex(COHORT_NAME))
cohort.cohortNumber = cursor.getString(cursor.getColumnIndex(COHORT_NUMBER))
cohort.branchId = cursor.getInt(cursor.getColumnIndex(BRANCH_ID))
cohort.isArchived = cursor.getInt(cursor.getColumnIndex(ARCHIVED)) == 1
return cohort
}

 fun addCohort(cohort:Cohort):Long {
val db = writableDatabase
val cv = ContentValues()

cv.put(ID, cohort.id)
cv.put(COHORT_NAME, cohort.cohortName)
cv.put(COHORT_NUMBER, cohort.cohortNumber)
cv.put(BRANCH_ID, cohort.branchId)
cv.put(ARCHIVED, cohort.isArchived)
val id:Long
if (cohortExists(cohort))
{
id = db.update(TABLE_COHORT, cv, ID + "='" + cohort.id + "'", null).toLong()
}
else
{
id = db.insertWithOnConflict(TABLE_COHORT, null, cv,
SQLiteDatabase.CONFLICT_REPLACE)
}
db.close()
return id
}

 fun cohortExists(cohort:Cohort):Boolean {
val db = readableDatabase
val cur = db.rawQuery("SELECT " + ID + " FROM " + TABLE_COHORT + " WHERE " +
ID + " = '" + cohort.id + "'", null)
val exist = cur.count > 0
cur.close()
return exist

}

 fun getCohortById(cohortId:String):Cohort? {
val db = writableDatabase
val whereClause = "$ID = ?"
val whereArgs = arrayOf(cohortId)
val cursor = db.query(TABLE_COHORT, cohortColumns, whereClause,
whereArgs, null, null, null, null)
if (!cursor.moveToFirst() || cursor.count == 0)
{
return null
}
else
{

val cohort = cursorToCohort(cursor)
cursor.close()
return cohort
}
}


 fun cursorToJson(cursor:Cursor, jsonRoot:String):JSONObject {
val results = JSONObject()
val resultSet = JSONArray()
cursor.moveToFirst()
while (!cursor.isAfterLast)
{
val totalColumns = cursor.columnCount
val rowObject = JSONObject()
for (i in 0 until totalColumns)
{
if (cursor.getColumnName(i) != null)
{
try
{
if (cursor.getString(i) != null)
{
rowObject.put(cursor.getColumnName(i), cursor.getString(i))
}
else
{
rowObject.put(cursor.getColumnName(i), "")
}
}
catch (e:Exception) {}

}
}
resultSet.put(rowObject)
try
{
results.put(jsonRoot, resultSet)
}
catch (e:JSONException) {}

cursor.moveToNext()
}
return results
}


/**
 * *********************************************************************************************
 * *
 * Trainee Status                                                                              *
 * Helps to indicate the status of the trainee                                                 *
 * *
 * *********************************************************************************************
 */

    private fun cursorToTraineeStatus(cursor:Cursor):TraineeStatus {
val status = TraineeStatus()
status.id = cursor.getInt(cursor.getColumnIndex(ID))
status.isArchived = cursor.getInt(cursor.getColumnIndex(ARCHIVED)) == 1
status.isReadonly = cursor.getInt(cursor.getColumnIndex(READONLY)) == 1
status.name = cursor.getString(cursor.getColumnIndex(NAME))
status.country = cursor.getString(cursor.getColumnIndex(COUNTRY))
status.dateCreated = cursor.getString(cursor.getColumnIndex(DATE_CREATED))
status.clientTime = cursor.getLong(cursor.getColumnIndex(CLIENT_TIME))
status.createdBy = cursor.getInt(cursor.getColumnIndex(CREATED_BY))
return status
}

 fun addTraineeStatus(status:TraineeStatus):Long {
val db = writableDatabase
val cv = ContentValues()
cv.put(ID, status.id)
cv.put(ARCHIVED, status.isArchived)
cv.put(READONLY, status.isReadonly)
cv.put(NAME, status.name)
cv.put(COUNTRY, status.country)
cv.put(DATE_CREATED, status.dateCreated)
cv.put(CLIENT_TIME, status.clientTime)
cv.put(CREATED_BY, status.createdBy)
val id:Long
if (traineeStatusExists(status))
{
id = db.update(TABLE_TRAINEE_STATUS, cv, ID + "='" + status.id + "'", null).toLong()
}
else
{
id = db.insertWithOnConflict(TABLE_TRAINEE_STATUS, null, cv,
SQLiteDatabase.CONFLICT_REPLACE)
}
db.close()
return id
}
 fun traineeStatusExists(status:TraineeStatus):Boolean {
val db = readableDatabase
val cur = db.rawQuery("SELECT " + ID + " FROM " + TABLE_TRAINEE_STATUS + " WHERE " +
ID + " = '" + status.id + "'", null)
val exist = cur.count > 0
cur.close()
return exist

}

 fun getTraineeStatusById(traineeStatusId:String):TraineeStatus? {
val db = writableDatabase
val whereClause = "$ID = ?"
val whereArgs = arrayOf(traineeStatusId)
val cursor = db.query(TABLE_TRAINEE_STATUS, traineeStatusColumns, whereClause,
whereArgs, null, null, null, null)
if (!cursor.moveToFirst() || cursor.count == 0)
{
return null
}
else
{

val status = cursorToTraineeStatus(cursor)
cursor.close()
return status
}
}

 fun traineeStatusFromJson(jsonObject:JSONObject) {
val traineeStatus = TraineeStatus()
try
{
traineeStatus.id = jsonObject.getInt(ID)
if (!jsonObject.isNull(NAME))
{
traineeStatus.name = jsonObject.getString(NAME)
}
if (!jsonObject.isNull(ARCHIVED))
{
traineeStatus.isArchived = jsonObject.getBoolean(ARCHIVED)
}
if (!jsonObject.isNull(READONLY))
{
traineeStatus.isReadonly = jsonObject.getBoolean(READONLY)
}
if (!jsonObject.isNull(COUNTRY))
{
traineeStatus.country = jsonObject.getString(COUNTRY)
}
if (!jsonObject.isNull(CLIENT_TIME))
{
traineeStatus.clientTime = jsonObject.getLong(CLIENT_TIME)
}
if (!jsonObject.isNull(CREATED_BY))
{
traineeStatus.createdBy = jsonObject.getInt(CREATED_BY)
}
if (!jsonObject.isNull(DATE_CREATED))
{
traineeStatus.dateCreated = jsonObject.getString(DATE_CREATED)
}

this.addTraineeStatus(traineeStatus)
}
catch (e:Exception) {
Log.d("Tremap", "=======ERR Creating Trainee Status ============")
Log.d("Tremap", e.message)
Log.d("Tremap", "===================")
}

}

/**
 * *********************************************************************************************
 * *
 * Training Exams                                                                              *
 * Helps to indicate the status of the training exams and performance                          *
 * Instead of downloading a whole universe of exams and questions, we can allow users to just  *
 * select the exams that they want to download. These are the ones that they can administer.   *
 * *
 * A good usecase would be to configure exams from the cloud, and the app only downloads the   *
 * ones for the training(s) needed. This will be the initial approach.                         *
 * *********************************************************************************************
 *
 */


    private fun cursorToTrainingExam(cursor:Cursor):TrainingExam {
val trainingExam = TrainingExam()

trainingExam.id = cursor.getInt(cursor.getColumnIndex(ID))
trainingExam.examId = cursor.getInt(cursor.getColumnIndex(EXAM_ID))
trainingExam.createdBy = cursor.getInt(cursor.getColumnIndex(CREATED_BY))
trainingExam.passmark = cursor.getInt(cursor.getColumnIndex(PASSMARK))
trainingExam.isArchived = cursor.getInt(cursor.getColumnIndex(ARCHIVED)) == 1
trainingExam.dateAdministered = cursor.getString(cursor.getColumnIndex(DATE_ADMINISTERED))
trainingExam.dateCreated = cursor.getString(cursor.getColumnIndex(DATE_CREATED))
trainingExam.trainingId = cursor.getString(cursor.getColumnIndex(TRAINING_ID))
trainingExam.country = cursor.getString(cursor.getColumnIndex(COUNTRY))
trainingExam.title = cursor.getString(cursor.getColumnIndex(TITLE))
try
{
val exam = JSONObject(cursor.getString(cursor.getColumnIndex(CLOUD_EXAM)))
trainingExam.setCloudExamJson(exam)
}
catch (e:Exception) {}


return trainingExam
}


 fun addTrainingExam(exam:TrainingExam):Long {
val db = writableDatabase
val cv = ContentValues()
cv.put(ID, exam.id)
cv.put(EXAM_ID, exam.examId)
cv.put(CREATED_BY, exam.createdBy)
cv.put(PASSMARK, exam.passmark)
cv.put(ARCHIVED, exam.isArchived)
cv.put(DATE_ADMINISTERED, exam.dateAdministered)
cv.put(DATE_CREATED, exam.dateCreated)
cv.put(TRAINING_ID, exam.trainingId)
cv.put(COUNTRY, exam.country)
cv.put(TITLE, exam.title)
cv.put(CLOUD_EXAM, exam.getCloudExamJson().toString())
cv.put(QUESTIONS, exam.getQuestions()!!.toString())

val id:Long
if (trainingExamExists(exam))
{
id = db.update(TABLE_TRAINING_EXAM, cv, ID + "='" + exam.id + "'", null).toLong()
}
else
{
id = db.insertWithOnConflict(TABLE_TRAINING_EXAM, null, cv,
SQLiteDatabase.CONFLICT_REPLACE)
}
db.close()
return id
}
 fun trainingExamExists(exam:TrainingExam):Boolean {
val db = writableDatabase
val cur = db.rawQuery("SELECT " + ID + " FROM " + TABLE_TRAINING_EXAM + " WHERE " +
ID + " = '" + exam.id + "'", null)
val exist = cur.count > 0
cur.close()
return exist

}

 fun getTrainingExamById(trainingExamId:String):TrainingExam? {
val db = writableDatabase
val whereClause = "$ID = ?"
val whereArgs = arrayOf(trainingExamId)
val cursor = db.query(TABLE_TRAINING_EXAM, trainingExamColumns, whereClause,
whereArgs, null, null, null, null)
if (!cursor.moveToFirst() || cursor.count == 0)
{
return null
}
else
{

val exam = cursorToTrainingExam(cursor)
cursor.close()
return exam
}
}

 fun getTrainingExamsByTrainingId(trainingId:String):List<TrainingExam> {
val db = writableDatabase
val whereClause = "$TRAINING_ID = ?"
val whereArgs = arrayOf(trainingId)
val cursor = db.query(TABLE_TRAINING_EXAM, trainingExamColumns, whereClause,
whereArgs, null, null, null, null)
val trainingExamList = ArrayList<TrainingExam>()
cursor.moveToFirst()
while (!cursor.isAfterLast)
{
val exam = cursorToTrainingExam(cursor)
trainingExamList.add(exam)
cursor.moveToNext()
}
cursor.close()
return trainingExamList
}

 fun trainingExamFromJson(jsonObject:JSONObject) {
val exam = TrainingExam()
try
{
exam.id = jsonObject.getInt(ID)
if (!jsonObject.isNull(ID))
{
exam.id = jsonObject.getInt(ID)
}
if (!jsonObject.isNull(EXAM_ID))
{
exam.examId = jsonObject.getInt(EXAM_ID)
}
if (!jsonObject.isNull(TRAINING_ID))
{
exam.trainingId = jsonObject.getString(TRAINING_ID)
}
if (!jsonObject.isNull(DATE_ADMINISTERED))
{
exam.dateAdministered = jsonObject.getString(DATE_ADMINISTERED)
}
if (!jsonObject.isNull(CREATED_BY))
{
exam.createdBy = jsonObject.getInt(CREATED_BY)
}
if (!jsonObject.isNull(DATE_CREATED))
{
exam.dateCreated = jsonObject.getString(DATE_CREATED)
}
if (!jsonObject.isNull(ARCHIVED))
{
exam.isArchived = jsonObject.getBoolean(ARCHIVED)
}
if (!jsonObject.isNull(PASSMARK))
{
exam.passmark = jsonObject.getInt(PASSMARK)
}
if (!jsonObject.isNull(COUNTRY))
{
exam.country = jsonObject.getString(COUNTRY)
}
if (!jsonObject.isNull(TITLE))
{
exam.title = jsonObject.getString(TITLE)
}
exam.setCloudExamJson(jsonObject)


this.addTrainingExam(exam)
}
catch (e:Exception) {
Log.d("Tremap", "=======ERR Creating Training Exam ============")
Log.d("Tremap", e.message)
}

}


/**
 * *********************************************************************************************
 * *
 * Training Exams Results            engage                                                    *
 * *
 * *********************************************************************************************
 *
 */

    private fun cursorToTrainingExamResult(cursor:Cursor):TrainingExamResult {
val result = TrainingExamResult()
result.id = cursor.getInt(cursor.getColumnIndex(ID))
result.answer = cursor.getString(cursor.getColumnIndex(ANSWER))
result.isArchived = cursor.getInt(cursor.getColumnIndex(ARCHIVED)) == 1
result.choiceId = cursor.getInt(cursor.getColumnIndex(CHOICE_ID))
result.country = cursor.getString(cursor.getColumnIndex(COUNTRY))
result.createdBy = cursor.getInt(cursor.getColumnIndex(CREATED_BY))
result.dateCreated = cursor.getString(cursor.getColumnIndex(DATE_CREATED))
result.questionId = cursor.getInt(cursor.getColumnIndex(QUESTION_ID))
result.questionScore = cursor.getInt(cursor.getColumnIndex(QUESTION_SCORE))
result.traineeId = cursor.getString(cursor.getColumnIndex(TRAINEE_ID))
result.trainingExamId = cursor.getInt(cursor.getColumnIndex(TRAINING_EXAM_ID))

return result
}

 fun trainingExamResultFromJson(jsonObject:JSONObject) {
val result = TrainingExamResult()
try
{
if (!jsonObject.isNull(ID))
{
result.id = jsonObject.getInt(ID)
}
if (!jsonObject.isNull(ANSWER))
{
result.answer = jsonObject.getString(ANSWER)
}
if (!jsonObject.isNull(ARCHIVED))
{
result.isArchived = jsonObject.getBoolean(ARCHIVED)
}
if (!jsonObject.isNull(CHOICE_ID))
{
result.choiceId = jsonObject.getInt(CHOICE_ID)
}
if (!jsonObject.isNull(COUNTRY))
{
result.country = jsonObject.getString(COUNTRY)
}
if (!jsonObject.isNull(CREATED_BY))
{
result.createdBy = jsonObject.getInt(CREATED_BY)
}
if (!jsonObject.isNull(DATE_CREATED))
{
result.dateCreated = jsonObject.getString(DATE_CREATED)
}
if (!jsonObject.isNull(QUESTION_ID))
{
result.questionId = jsonObject.getInt(QUESTION_ID)
}
if (!jsonObject.isNull(QUESTION_SCORE))
{
result.questionScore = jsonObject.getInt(QUESTION_SCORE)
}
if (!jsonObject.isNull(TRAINEE_ID))
{
result.traineeId = jsonObject.getString(TRAINEE_ID)
}
if (!jsonObject.isNull(TRAINING_EXAM_ID))
{
result.trainingExamId = jsonObject.getInt(TRAINING_EXAM_ID)
}

this.addTrainingExamResult(result)
}
catch (e:Exception) {
Log.d("Tremap", "=======ERR Creating Training Exam Result ============")
Log.d("Tremap", e.message)
}

}

 fun addTrainingExamResult(exam:TrainingExamResult):Long {
val db = writableDatabase

val cv = ContentValues()
cv.put(ID, exam.id)
cv.put(ARCHIVED, exam.isArchived)
cv.put(CHOICE_ID, exam.choiceId)
cv.put(COUNTRY, exam.country)
cv.put(CREATED_BY, exam.createdBy)
cv.put(DATE_CREATED, exam.dateCreated)
cv.put(QUESTION_ID, exam.questionId)
cv.put(QUESTION_SCORE, exam.questionScore)
cv.put(TRAINEE_ID, exam.traineeId)
cv.put(TRAINING_EXAM_ID, exam.trainingExamId)
cv.put(ANSWER, exam.answer)

val id:Long
if (trainingExamResultExists(exam))
{
id = db.update(TABLE_EXAM_RESULTS, cv, ID + "='" + exam.id + "'", null).toLong()
}
else
{
id = db.insertWithOnConflict(TABLE_EXAM_RESULTS, null, cv,
SQLiteDatabase.CONFLICT_REPLACE)
}
db.close()
return id
}
 fun trainingExamResultExists(exam:TrainingExamResult):Boolean {
val db = writableDatabase
val cur = db.rawQuery("SELECT " + ID + " FROM " + TABLE_EXAM_RESULTS + " WHERE " +
ID + " = '" + exam.id + "'", null)
val exist = cur.count > 0
cur.close()
return exist

}


 fun getTrainingExamResultByTrainee(traineeId:String):List<TrainingExamResult> {
val db = writableDatabase
val whereClause = "$TRAINEE_ID = ?"
val whereArgs = arrayOf(traineeId)
val cursor = db.query(TABLE_EXAM_RESULTS, trainingExamResultColumns, whereClause,
whereArgs, null, null, null, null)
val trainingExamResults = ArrayList<TrainingExamResult>()
cursor.moveToFirst()
while (!cursor.isAfterLast)
{
val result = cursorToTrainingExamResult(cursor)
trainingExamResults.add(result)
cursor.moveToNext()
}
cursor.close()
return trainingExamResults
}

 fun getTrainingExamResultByExam(examId:String):List<TrainingExamResult> {
val db = writableDatabase
val whereClause = "$TRAINING_EXAM_ID = ?"
val whereArgs = arrayOf(examId)
val cursor = db.query(TABLE_EXAM_RESULTS, trainingExamResultColumns, whereClause,
whereArgs, TRAINEE_ID, null, null, null)
val trainingExamResults = ArrayList<TrainingExamResult>()
cursor.moveToFirst()
while (!cursor.isAfterLast)
{
val result = cursorToTrainingExamResult(cursor)
trainingExamResults.add(result)
cursor.moveToNext()
}
cursor.close()
return trainingExamResults
}


 fun getTrainingExamResultByQuestionId(questionId:String):List<TrainingExamResult> {
val db = writableDatabase
val whereClause = "$QUESTION_ID = ?"
val whereArgs = arrayOf(questionId)
val cursor = db.query(TABLE_EXAM_RESULTS, trainingExamResultColumns, whereClause,
whereArgs, null, null, null, null)
val trainingExamResults = ArrayList<TrainingExamResult>()
cursor.moveToFirst()
while (!cursor.isAfterLast)
{
val result = cursorToTrainingExamResult(cursor)
trainingExamResults.add(result)
cursor.moveToNext()
}
cursor.close()
return trainingExamResults
}

 fun getTrainingExamResultByExamQuestionAndTrainee(questionId:String,
examId:String, trainee:String):TrainingExamResult? {
val db = writableDatabase
val whereClause = "$TRAINING_EXAM_ID = ? AND $TRAINEE_ID = ? AND $QUESTION_ID= ? "
val whereArgs = arrayOf(examId, trainee, questionId)
val cursor = db.query(TABLE_EXAM_RESULTS, trainingExamResultColumns, whereClause,
whereArgs, null, null, null, null)
if (!cursor.moveToFirst() || cursor.count == 0)
{
return null
}
else
{

val exam = cursorToTrainingExamResult(cursor)
cursor.close()
return exam
}
}

companion object {
private val LOG = "DatabaseHelper"
private val DATABASE_VERSION = 1
private val DATABASE_NAME = "training.db"

private val varchar_field = " varchar(512) "
private val integer_field = " integer default 0 "
private val text_field = " text "
private val real_field = " REAL "
private val datetime_field = " DATETIME "
private val primary_field = " id INTEGER PRIMARY KEY AUTOINCREMENT "

private val TABLE_TRAINING = "training"
private val TABLE_TRAINING_VENUE = "training_venues"
private val TABLE_SESSION_ATTENDANCE = "session_attendance"
private val TABLE_SESSION_TOPIC = "session_topic"
private val TABLE_TRAINING_SESSION = "training_session"
private val TABLE_TRAINING_SESSION_TYPE = "training_session_type"
private val TABLE_TRAINING_STATUS = "training_status"
private val TABLE_TRAINING_ROLES = "training_roles"
private val TABLE_TRAINING_TRAINERS = "training_trainers"
private val TABLE_TRAINING_CLASSES = "training_classes"
private val TABLE_TRAINING_TRAINEES = "trainees"
private val TABLE_USERS = "users"
private val TABLE_TRAINING_COMMENTS = "training_comments"
private val TABLE_BRANCH = "branch"
private val TABLE_COHORT = "cohort"
private val TABLE_TRAINEE_STATUS = "trainee_status"
private val TABLE_TRAINING_EXAM = "training_exams"
private val TABLE_EXAM_RESULTS = "exam_results"

 // fields for Training
    private val ID = "id"
private val TRAINING_NAME = "training_name"
private val COUNTRY = "country"
private val COUNTY_ID = "county_id"
private val LOCATION_ID = "location_id"
private val SUBCOUNTY_ID = "subcounty_id"
private val WARD_ID = "ward_id"
private val DISTRICT = "district"
private val RECRUITMENT_ID = "recruitment_id"
private val PARISH_ID = "parish_id"
private val LAT = "lat"
private val LON = "lon"
private val TRAINING_VENUE_ID = "training_venue_id"
private val TRAINING_STATUS_ID = "training_status_id"
private val CLIENT_TIME = "client_time"
private val CREATED_BY = "created_by"
private val DATE_CREATED = "date_created"
private val ARCHIVED = "archived"
private val COMMENT = "comment"
private val DATE_COMMENCED = "date_commenced"
private val DATE_COMPLETED = "date_completed"
private val BRANCH_NAME = "branch_name"
private val BRANCH_CODE = "branch_code"
private val COHORT_NUMBER = "cohort_number"
private val COHORT_NAME = "cohort_name"
private val TRAINEE_STATUS = "trainee_status"
private val BRANCH_ID = "branch_id"
private val DATE_ADMINISTERED = "date_administered"
private val EXAM_ID = "exam_id"
private val TITLE = "title"
 // training venue
    private val NAME = "name"
private val MAPPING = "mapping"
private val MAPPING_ID = "mapping_id"
private val INSPECTED = "inspected"
private val SELECTED = "selected"
private val CAPACITY = "capacity"
private val DATE_ADDED = "date_added"
private val ADDED_BY = "added_by"
private val META_DATA = "meta_data"
 //sessionAttendance
    private val TRAINING_SESSION_ID = "training_session_id"
private val TRAINEE_ID = "trainee_id"
private val TRAINING_SESSION_TYPE_ID = "training_session_type_id"
private val TRAINING_ID = "training_id"
private val ATTENDED = "attended"
 //training session
    private val CLASS_ID = "class_id"
private val TRAINER_ID = "trainer_id"
private val SESSION_START_TIME = "session_start_time"
private val SESSION_END_TIME = "session_end_time"
private val SESSION_TOPIC_ID = "session_topic_id"
private val SESSION_LEAD_TRAINER = "session_lead_trainer"
 //training_session_type
    private val SESSION_NAME = "session_name"
private val READONLY = "readonly"
private val ROLE_NAME = "role_name"
private val TRAINING_ROLE_ID = "training_role_id"
private val REGISTRATION_ID = "registration_id"
private val BRANCH = "branch"
private val COHORT = "cohort"
private val CHP_CODE = "chp_code"
private val REGISTRATION = "registration"
private val CLASS_NAME = "class_name"
private val EMAIL = "email"
private val USERNAME = "username"
private val PASSWORD = "password"
private val PASSMARK = "passmark"
private val EXAM_STATUS = "exam_status"
private val EXAM_STATUS_ID = "exam_status_id"
private val QUESTIONS = "questions"
private val CLOUD_EXAM = "cloud_exam"

private val ANSWER = "answer"
private val CHOICE_ID = "choice_id"
private val QUESTION_ID = "question_id"
private val QUESTION_SCORE = "question_score"
private val TRAINING_EXAM_ID = "training_exam_id"

private val CREATE_EXAM_RESULTS_TABLE = ("CREATE TABLE " + TABLE_EXAM_RESULTS + "("
+ ID + integer_field + ", "
+ ARCHIVED + real_field + ", "
+ CHOICE_ID + integer_field + ", "
+ COUNTRY + varchar_field + ", "
+ ANSWER + text_field + ", "
+ CREATED_BY + integer_field + ", "
+ DATE_CREATED + varchar_field + ", "
+ QUESTION_ID + integer_field + ", "
+ QUESTION_SCORE + integer_field + ", "
+ TRAINEE_ID + varchar_field + ", "
+ TRAINING_EXAM_ID + varchar_field + "); ")

private val CREATE_TABLE_TRAINING = ("CREATE TABLE " + TABLE_TRAINING + "("
+ ID + varchar_field + ", "
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
+ DATE_CREATED + datetime_field + ", "
+ ARCHIVED + integer_field + ", "
+ COMMENT + text_field + ", "
+ DATE_COMMENCED + real_field + ", "
+ DATE_COMPLETED + real_field + "); ")

private val CREATE_TABLE_TRAINING_VENUE = ("CREATE TABLE " + TABLE_TRAINING_VENUE + "("
+ ID + varchar_field + ", "
+ NAME + varchar_field + ", "
+ MAPPING + varchar_field + ", "
+ LAT + real_field + ", "
+ LON + real_field + ", "
+ INSPECTED + integer_field + ", "
+ COUNTRY + varchar_field + ", "
+ SELECTED + integer_field + ", "
+ CAPACITY + integer_field + ", "
+ DATE_ADDED + datetime_field + ", "
+ ADDED_BY + integer_field + ", "
+ CLIENT_TIME + real_field + ", "
+ META_DATA + text_field + ", "
+ ARCHIVED + integer_field + "); ")

private val CREATE_TABLE_SESSION_ATTENDANCE = ("CREATE TABLE " + TABLE_SESSION_ATTENDANCE + "("
+ ID + varchar_field + ", "
+ TRAINING_SESSION_ID + varchar_field + ", "
+ TRAINEE_ID + varchar_field + ", "
+ TRAINING_SESSION_TYPE_ID + integer_field + ", "
+ TRAINING_ID + varchar_field + ", "
+ COUNTRY + varchar_field + ", "
+ ATTENDED + integer_field + ", "
+ CREATED_BY + integer_field + ", "
+ CLIENT_TIME + real_field + ", "
+ DATE_CREATED + datetime_field + ", "
+ META_DATA + text_field + ", "
+ COMMENT + text_field + ", "
+ ARCHIVED + integer_field + "); ")

private val CREATE_TABLE_SESSION_TOPIC = ("CREATE TABLE " + TABLE_SESSION_TOPIC + "("
+ primary_field + ", "
+ NAME + varchar_field + ", "
+ COUNTRY + varchar_field + ", "
+ ARCHIVED + integer_field + ", "
+ ADDED_BY + integer_field + ", "
+ DATE_ADDED + datetime_field + ", "
+ META_DATA + text_field + ", "
+ COMMENT + text_field + "); ")


private val CREATE_TABLE_TRAINING_SESSION = ("CREATE TABLE " + TABLE_TRAINING_SESSION + "("
+ ID + varchar_field + ", "
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
+ DATE_CREATED + datetime_field + ", "
+ COMMENT + text_field + "); ")

private val CREATE_TABLE_TRAINING_SESSION_TYPE = ("CREATE TABLE " + TABLE_TRAINING_SESSION_TYPE + "("
+ ID + varchar_field + ", "
+ SESSION_NAME + varchar_field + ", "
+ CLASS_ID + integer_field + ", "
+ COUNTRY + varchar_field + ", "
+ ARCHIVED + integer_field + ", "
+ CLIENT_TIME + real_field + ", "
+ CREATED_BY + integer_field + ", "
+ DATE_CREATED + real_field + ", "
+ COMMENT + text_field + "); ")

private val CREATE_TABLE_TRAINING_STATUS = ("CREATE TABLE " + TABLE_TRAINING_STATUS + "("
+ primary_field + ", "
+ NAME + varchar_field + ", "
+ ARCHIVED + integer_field + ", "
+ READONLY + integer_field + ", "
+ COUNTRY + varchar_field + ", "
+ CLIENT_TIME + real_field + ", "
+ CREATED_BY + integer_field + ", "
+ DATE_CREATED + real_field + ", "
+ COMMENT + text_field + "); ")

private val CREATE_TABLE_TRAINING_ROLES = ("CREATE TABLE " + TABLE_TRAINING_ROLES + "("
+ primary_field + ", "
+ ROLE_NAME + varchar_field + ", "
+ ARCHIVED + integer_field + ", "
+ READONLY + integer_field + ", "
+ COUNTRY + varchar_field + ", "
+ CLIENT_TIME + real_field + ", "
+ CREATED_BY + integer_field + ", "
+ DATE_CREATED + real_field + ", "
+ COMMENT + text_field + "); ")

private val CREATE_TABLE_TRAINING_TRAINERS = ("CREATE TABLE " + TABLE_TRAINING_TRAINERS + "("
+ primary_field + ", "
+ TRAINING_ID + varchar_field + ", "
+ CLASS_ID + integer_field + ", "
+ TRAINER_ID + integer_field + ", "
+ COUNTRY + varchar_field + ", "
+ CLIENT_TIME + real_field + ", "
+ CREATED_BY + integer_field + ", "
+ DATE_CREATED + real_field + ", "
+ ARCHIVED + integer_field + ", "
+ TRAINING_ROLE_ID + integer_field + ", "
+ COMMENT + text_field + "); ")

private val CREATE_TABLE_TRAINING_CLASSES = ("CREATE TABLE " + TABLE_TRAINING_CLASSES + "("
+ primary_field + ", "
+ TRAINING_ID + varchar_field + ", "
+ CLASS_NAME + varchar_field + ", "
+ COUNTRY + varchar_field + ", "
+ CLIENT_TIME + real_field + ", "
+ CREATED_BY + integer_field + ", "
+ DATE_CREATED + datetime_field + ", "
+ ARCHIVED + integer_field + ", "
+ COMMENT + text_field + "); ")

private val CREATE_TABLE_TRAINING_TRAINEE = ("CREATE TABLE " + TABLE_TRAINING_TRAINEES + "("
+ ID + varchar_field + ", "
+ REGISTRATION_ID + varchar_field + ", "
+ CLASS_ID + integer_field + ", "
+ TRAINING_ID + varchar_field + ", "
+ COUNTRY + varchar_field + ", "
+ ADDED_BY + integer_field + ", "
+ DATE_CREATED + datetime_field + ", "
+ CLIENT_TIME + real_field + ", "
+ BRANCH + varchar_field + ", "
+ COHORT + real_field + ", "
+ CHP_CODE + real_field + ", "
+ ARCHIVED + integer_field + ", "
+ TRAINEE_STATUS + integer_field + ", "
+ REGISTRATION + text_field + ", " //will be storing the JSON

+ COMMENT + text_field + "); ")

private val CREATE_TABLE_TRAINEE_COMMENTS = ("CREATE TABLE " + TABLE_TRAINING_COMMENTS + "("
+ ID + varchar_field + ", "
+ TRAINEE_ID + varchar_field + ", "
+ TRAINING_ID + varchar_field + ", "
+ COUNTRY + varchar_field + ", "
+ ADDED_BY + integer_field + ", "
+ DATE_CREATED + datetime_field + ", "
+ CLIENT_TIME + real_field + ", "
+ ARCHIVED + integer_field + ", "
+ COMMENT + text_field + "); ")

private val CREATE_TABLE_USERS = ("CREATE TABLE " + TABLE_USERS + "("
+ ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
+ EMAIL + varchar_field + ", "
+ USERNAME + varchar_field + ", "
+ PASSWORD + varchar_field + ", "
+ NAME + varchar_field + ", "
+ COUNTRY + varchar_field + "); ")

private val CREATE_TABLE_BRANCH = ("CREATE TABLE " + TABLE_BRANCH + "("
+ ID + varchar_field + ", "
+ BRANCH_NAME + varchar_field + ", "
+ BRANCH_CODE + varchar_field + ", "
+ MAPPING + varchar_field + ", "
+ LAT + real_field + ", "
+ LON + real_field + ", "
+ ARCHIVED + integer_field + "); ")

private val CREATE_TABLE_COHORT = ("CREATE TABLE " + TABLE_COHORT + "("
+ ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
+ COHORT_NAME + varchar_field + ", "
+ COHORT_NUMBER + varchar_field + ", "
+ BRANCH_ID + varchar_field + ", "
+ ARCHIVED + integer_field + "); ")


private val CREATE_TABLE_TRAINEE_STATUS = ("CREATE TABLE " + TABLE_TRAINEE_STATUS + "("
+ ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
+ NAME + varchar_field + ", "
+ ARCHIVED + integer_field + ", "
+ READONLY + integer_field + ", "
+ COUNTRY + varchar_field + ", "
+ CLIENT_TIME + real_field + ", "
+ CREATED_BY + integer_field + ", "
+ DATE_CREATED + varchar_field + "); ")

 val CREATE_TABLE_TRAINING_EXAM = ("CREATE TABLE " + TABLE_TRAINING_EXAM + "("
+ ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
+ EXAM_ID + varchar_field + ", "
+ DATE_ADMINISTERED + varchar_field + ", "
+ CREATED_BY + integer_field + ", "
+ DATE_CREATED + varchar_field + ", "
+ PASSMARK + integer_field + ", "
+ TRAINING_ID + varchar_field + ", "
+ COUNTRY + varchar_field + ", "
+ TITLE + varchar_field + ", "
+ CLOUD_EXAM + text_field + ", "
+ EXAM_STATUS + text_field + ", "
+ QUESTIONS + text_field + ", "
+ EXAM_STATUS_ID + integer_field + ", "
+ ARCHIVED + integer_field + "); ")
}
}