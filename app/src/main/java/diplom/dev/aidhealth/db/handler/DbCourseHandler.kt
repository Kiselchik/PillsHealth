package diplom.dev.aidhealth.db.handler

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import diplom.dev.aidhealth.CheckCourse
import diplom.dev.aidhealth.DataRecyclerCourse
import diplom.dev.aidhealth.User
import diplom.dev.aidhealth.db.model.Course
import diplom.dev.aidhealth.db.model.Pill


val TABLE_NAME_COURSE = "Course"
val COL_ID_COURSE = "id"
val COL_COURSE_EMAIL = "email"
val COL_COURSE_PROCEDURE = "procedure"
val COL_COURSE_MEDICAMENT = "medicament"
val COL_COURSE_DOCTOR = "doctor"
val COL_COURSE_FOOD = "food"
val COL_COURSE_TITLE="title"
val COL_COURSE_TIME_CH_SMPTM = "timeChSmptm"
val COL_CORSE_TIME_CH_HEALTH = "timeChNtf"
val COL_COURSE_NOTIFICATION = "notification"
val COL_COURSE_DATE ="date"
val COL_COURSE_DESCR = "descr"
val COL_COURSE_DIAGNOSIS_SYMPTOM = "diagnosisSymptom"

val CREATE_TABLE_COURSE = "CREATE TABLE IF NOT EXISTS  $TABLE_NAME_COURSE ("+
        "$COL_ID_COURSE INTEGER PRIMARY KEY AUTOINCREMENT, $COL_COURSE_EMAIL TEXT, " +
        "$COL_COURSE_PROCEDURE INTEGER, $COL_COURSE_MEDICAMENT TEXT, $COL_COURSE_DOCTOR INTEGER," +
        "$COL_COURSE_FOOD TEXT, $COL_COURSE_TITLE TEXT, $COL_COURSE_TIME_CH_SMPTM TEXT, " +
        "$COL_CORSE_TIME_CH_HEALTH TEXT, $COL_COURSE_NOTIFICATION TEXT, $COL_COURSE_DATE TEXT," +
        "$COL_COURSE_DESCR TEXT, $COL_COURSE_DIAGNOSIS_SYMPTOM INTEGER, FOREIGN KEY(email) REFERENCES Users(email), " +
        "FOREIGN KEY(procedure) REFERENCES Procedures(id), FOREIGN KEY(medicament) REFERENCES Pills(id)," +
        "FOREIGN KEY(doctor) REFERENCES Doctors(id),  FOREIGN KEY(diagnosisSymptom) REFERENCES DiagnosisSymptom(id)    )"

val SQL_DELETE_TABLE_COURSE = "DROP TABLE IF EXISTS ${TABLE_NAME_COURSE}"

class DbCourseHandler(var context: Context): SQLiteOpenHelper(context,
    DATABASE_NAME, null,
    DATABASE_VERSION
) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_TABLE_COURSE)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(SQL_DELETE_TABLE_COURSE)
        onCreate(db)
    }

    fun clearTableCourse(){
        val db = writableDatabase
        db.execSQL("DROP TABLE IF EXISTS ${TABLE_NAME_COURSE}")
        // db.execSQL("DELETE FROM ${TABLE_NAME_COURSE}")

    }
    fun deleteRow(id: Int){
        val db = writableDatabase

        db.execSQL("DELETE FROM ${TABLE_NAME_COURSE} WHERE id = ${id} ")

        db.close()
    }


    fun getCourseId(id: Int): MutableList<Course> {
        val db = writableDatabase
        var list: MutableList<Course> = ArrayList()

        val query = "SELECT id FROM " + TABLE_NAME_COURSE + "" +
                " WHERE $COL_COURSE_PROCEDURE = '${id}'  "

        val result = db.rawQuery(query, null)

        if (result.moveToFirst()) {
            do {
                var course = Course()
                course.id = result.getString(result.getColumnIndex(COL_ID_COURSE)).toInt()
                list.add(course)
            } while (result.moveToNext())
        }
        result.close()
        db.close()


        return list
    }

    fun insertCourse(course: Course) {

        val db = writableDatabase
     //  db.execSQL("DELETE FROM ${TABLE_NAME_COURSE}")
     db.execSQL(CREATE_TABLE_COURSE)

        var cv = ContentValues()

        cv.put(COL_COURSE_PROCEDURE, course.valuePillProcedure)
        cv.put(COL_COURSE_MEDICAMENT, course.medicament)
        cv.put(COL_COURSE_EMAIL, course.email)
        cv.put(COL_COURSE_DOCTOR, course.doctor)
        cv.put(COL_COURSE_FOOD, course.food)
        cv.put(COL_COURSE_TITLE, course.title)
        cv.put(COL_COURSE_TIME_CH_SMPTM, course.timeCheckSymptom)
        cv.put(COL_CORSE_TIME_CH_HEALTH, course.timeHealthNotification)
        cv.put(COL_COURSE_NOTIFICATION, course.notification)
        cv.put(COL_COURSE_DATE, course.date)
        cv.put(COL_COURSE_DESCR, course.descr)
        cv.put(COL_COURSE_DIAGNOSIS_SYMPTOM, course.diagnosisSymptomID)




        var result = db.insert(TABLE_NAME_COURSE, null, cv)
        if (result == -1.toLong())
         Toast.makeText(context, "FAILED", Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(context, "SUCCESS", Toast.LENGTH_SHORT).show()
    }

    fun readCourse(): MutableList<Course> {
        var list: MutableList<Course> = ArrayList()
        val db = this.readableDatabase
        db.execSQL(CREATE_TABLE_COURSE)

        val query = "SELECT * FROM " + TABLE_NAME_COURSE + "" +
                " WHERE email = '${User.email}'  "

            val result = db.rawQuery(query, null)

            if (result.moveToFirst()) {
                do {
                    var course = Course()
                    course.id = result.getString(result.getColumnIndex(COL_ID_COURSE)).toInt()
                    course.email = result.getString(result.getColumnIndex(COL_COURSE_EMAIL))
                    course.valuePillProcedure = result.getString(result.getColumnIndex(COL_COURSE_PROCEDURE)).toInt()
                    course?.medicament = result.getString(result.getColumnIndex(COL_COURSE_MEDICAMENT))
                    course.doctor = result.getString(result.getColumnIndex(COL_COURSE_DOCTOR)).toInt()
                    course.food = result.getString(result.getColumnIndex(COL_COURSE_FOOD))
                    course.title = result.getString(result.getColumnIndex(COL_COURSE_TITLE))
                    course.timeCheckSymptom = result.getString(result.getColumnIndex(COL_COURSE_TIME_CH_SMPTM))
                    course.timeHealthNotification = result.getString(result.getColumnIndex(COL_CORSE_TIME_CH_HEALTH))
                    course.notification = result.getString(result.getColumnIndex(COL_COURSE_NOTIFICATION))
                    course.date = result.getString(result.getColumnIndex(COL_COURSE_DATE))
                    course.descr = result.getString(result.getColumnIndex(COL_COURSE_DESCR))
                    course.diagnosisSymptomID = result.getString(result.getColumnIndex(COL_COURSE_DIAGNOSIS_SYMPTOM)).toInt()
                    list.add(course)
                } while (result.moveToNext())
            }
            result.close()
            db.close()


        return list
    }

    fun readCourseWithNotification(): MutableList<Course> {
        var list: MutableList<Course> = ArrayList()
        val db = this.readableDatabase

        val query = "SELECT * FROM " + TABLE_NAME_COURSE + "" +
                " WHERE email = '${User.email}' AND $COL_COURSE_NOTIFICATION = '1' "

        val result = db.rawQuery(query, null)

        if (result.moveToFirst()) {
            do {
                var course = Course()
                course.id = result.getString(result.getColumnIndex(COL_ID_COURSE)).toInt()
                course.email = result.getString(result.getColumnIndex(COL_COURSE_EMAIL))
                course.valuePillProcedure = result.getString(result.getColumnIndex(COL_COURSE_PROCEDURE)).toInt()
                course.medicament = result.getString(result.getColumnIndex(COL_COURSE_MEDICAMENT))
                course.doctor = result.getString(result.getColumnIndex(COL_COURSE_DOCTOR)).toInt()
                course.food = result.getString(result.getColumnIndex(COL_COURSE_FOOD))
                course.title = result.getString(result.getColumnIndex(COL_COURSE_TITLE))
                course.timeCheckSymptom = result.getString(result.getColumnIndex(COL_COURSE_TIME_CH_SMPTM))
                course.timeHealthNotification = result.getString(result.getColumnIndex(COL_CORSE_TIME_CH_HEALTH))
                course.notification = result.getString(result.getColumnIndex(COL_COURSE_NOTIFICATION))
                course.date = result.getString(result.getColumnIndex(COL_COURSE_DATE))
                course.descr = result.getString(result.getColumnIndex(COL_COURSE_DESCR))
                course.diagnosisSymptomID = result.getString(result.getColumnIndex(COL_COURSE_DIAGNOSIS_SYMPTOM)).toInt()
                list.add(course)
            } while (result.moveToNext())
        }
        result.close()
        db.close()


        return list
    }

    fun updateChBox(symptom: String, health: String, notification: String){
        val db = writableDatabase
  try{
        db.execSQL("UPDATE ${TABLE_NAME_COURSE} SET " +
                "${COL_COURSE_TIME_CH_SMPTM} = ${symptom}, ${COL_CORSE_TIME_CH_HEALTH} = ${health}, " +
                " ${COL_COURSE_NOTIFICATION} = ${notification}" +
                "  WHERE ${COL_ID_COURSE} = ${DataRecyclerCourse.courseID}")}
  catch (e: Exception) {
      Toast.makeText(context, "Проблемки с обновлением", Toast.LENGTH_SHORT).show()
  }
    }

    fun getOneCourse(): MutableList<Course> {
        var list: MutableList<Course> = ArrayList()
        val db = this.readableDatabase

        val query = "SELECT * FROM " + TABLE_NAME_COURSE + " " +
                "WHERE ${COL_ID_COURSE} = ${DataRecyclerCourse.courseID}"
        try{
            val result = db.rawQuery(query, null)

            if (result.moveToFirst()) {
                do {
                    var course = Course()
                    course.id = result.getString(result.getColumnIndex(COL_ID_COURSE)).toInt()
                    course.email = result.getString(result.getColumnIndex(COL_COURSE_EMAIL))
                    course.valuePillProcedure = result.getString(result.getColumnIndex(COL_COURSE_PROCEDURE)).toInt()
                   course.medicament = result.getString(result.getColumnIndex(COL_COURSE_MEDICAMENT))
                    course.doctor = result.getString(result.getColumnIndex(COL_COURSE_DOCTOR)).toInt()
                    course.food = result.getString(result.getColumnIndex(COL_COURSE_FOOD))
                    course.title = result.getString(result.getColumnIndex(COL_COURSE_TITLE))
                    course.timeCheckSymptom = result.getString(result.getColumnIndex(COL_COURSE_TIME_CH_SMPTM))
                    course.timeHealthNotification = result.getString(result.getColumnIndex(COL_CORSE_TIME_CH_HEALTH))
                    course.notification = result.getString(result.getColumnIndex(COL_COURSE_NOTIFICATION))
                    course.date = result.getString(result.getColumnIndex(COL_COURSE_DATE))
                    course.descr = result.getString(result.getColumnIndex(COL_COURSE_DESCR))
                    course.diagnosisSymptomID = result.getString(result.getColumnIndex(COL_COURSE_DIAGNOSIS_SYMPTOM)).toInt()

                    list.add(course)
                } while (result.moveToNext())
            }
            result.close()
            db.close()

        }
        catch(e: Exception) {
            Toast.makeText(context, "Таблицы не существует", Toast.LENGTH_SHORT).show()
        }
        return list

    }

    fun selectMaxId(): MutableList<Course>{
        var list: MutableList<Course> = ArrayList()
        val db = this.readableDatabase

        val query = "SELECT id FROM " + TABLE_NAME_COURSE +
                " WHERE ${COL_ID_COURSE} = (" +
                "SELECT MAX(id) FROM ${TABLE_NAME_COURSE}) "
        try{
            val result = db.rawQuery(query, null)

            if (result.moveToFirst()) {
                do {
                    var course = Course()
                    course.id = result.getString(result.getColumnIndex(COL_ID_COURSE)).toInt()
                    list.add(course)
                } while (result.moveToNext())
            }
            result.close()
            db.close()

        }
        catch(e: Exception) {
            Toast.makeText(context, "Таблицы не существует", Toast.LENGTH_SHORT).show()
        }
        return list

    }
    /*
    fun dropDoctorTable(){
        val db = this.writableDatabase
        db.execSQL("DROP TABLE " + TABLE_NAME)
        DATABASE_VERSION = DATABASE_VERSION+1
        db.close()
    }*/

}
