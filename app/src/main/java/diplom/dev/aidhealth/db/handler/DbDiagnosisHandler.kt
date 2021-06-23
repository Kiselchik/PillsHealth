package diplom.dev.aidhealth.db.handler

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import diplom.dev.aidhealth.DataRecyclerCourse
import diplom.dev.aidhealth.User
import diplom.dev.aidhealth.db.model.Course
import diplom.dev.aidhealth.db.model.Diagnosis
import diplom.dev.aidhealth.db.model.DiagnosisWithSymptom

val TABLE_NAME_DIAGNOSIS = "Diagnosys"
val COL_DIAGNOSIS_ID = "id"
val COL_DIAGNOSIS_EMAIL = "email"
val COL_DIAGNOSIS_DOCTOR = "doctorID"
val COL_DIAGNOSIS_TITLE = "diagnosisTitle"
val COL_DIAGNOSIS_DESCR = "descr"
val COL_DIAGNOSIS_DATETIME = "datetime"



val CREATE_TABLE_DIAGNOSIS = "CREATE TABLE IF NOT EXISTS  $TABLE_NAME_DIAGNOSIS ("+
        "$COL_DIAGNOSIS_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COL_DIAGNOSIS_EMAIL TEXT, " +
        "$COL_DIAGNOSIS_DOCTOR INTEGER, $COL_DIAGNOSIS_TITLE TEXT, $COL_DIAGNOSIS_DESCR TEXT," +
        "$COL_DIAGNOSIS_DATETIME TEXT, FOREIGN KEY(email) REFERENCES Users(email), " +
        "FOREIGN KEY(doctorID) REFERENCES Doctors(id)  )"

val SQL_DELETE_TABLE_DIAGNOSIS = "DROP TABLE IF EXISTS ${TABLE_NAME_DIAGNOSIS}"

class DbDiagnosisHandler(var context: Context): SQLiteOpenHelper(context,
    DATABASE_NAME, null,
    DATABASE_VERSION
) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_TABLE_DIAGNOSIS)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(SQL_DELETE_TABLE_DIAGNOSIS)
        onCreate(db)
    }

    fun deleteRow(id: Int){
        val db = writableDatabase
        db.execSQL("DELETE FROM ${TABLE_NAME_DIAGNOSIS} WHERE id = ${id} ")
        db.close()
    }



    fun clearTableDiagnosis() {
        val db = writableDatabase
        db.execSQL("DELETE FROM ${TABLE_NAME_DIAGNOSIS}")

    }

    fun insertDiagnosis(diagnosis: Diagnosis) {

        val db = writableDatabase
        //  db.execSQL("DELETE FROM ${TABLE_NAME_COURSE}")
        db.execSQL(CREATE_TABLE_DIAGNOSIS)

        var cv = ContentValues()

        cv.put(COL_DIAGNOSIS_EMAIL, diagnosis.email)
        cv.put(COL_DIAGNOSIS_DOCTOR, diagnosis.doctorID)
        cv.put(COL_DIAGNOSIS_TITLE, diagnosis.diagnosisTitle)
        cv.put(COL_DIAGNOSIS_DESCR, diagnosis.descr)
        cv.put(COL_DIAGNOSIS_DATETIME, diagnosis.datetime)

        var result = db.insert(TABLE_NAME_DIAGNOSIS, null, cv)
        if (result == -1.toLong())
            Toast.makeText(context, "FAILED diagnosis", Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(context, "SUCCESS diagnosis", Toast.LENGTH_SHORT).show()
    }

    fun readDiagnosis(): MutableList<Diagnosis> {
        var list: MutableList<Diagnosis> = ArrayList()
        val db = this.readableDatabase

        val query = "SELECT * FROM " + TABLE_NAME_DIAGNOSIS +" " +
                "WHERE email = '${User.email}' "
        try {
            val result = db.rawQuery(query, null)

            if (result.moveToFirst()) {
                do {
                    var diagnosis = Diagnosis()
                    diagnosis.id = result.getString(result.getColumnIndex(COL_DIAGNOSIS_ID)).toInt()
                    diagnosis.email = result.getString(result.getColumnIndex(COL_DIAGNOSIS_EMAIL))
                    diagnosis.doctorID = result.getString(result.getColumnIndex(COL_DIAGNOSIS_DOCTOR)).toInt()
                    diagnosis.diagnosisTitle = result.getString(result.getColumnIndex(COL_DIAGNOSIS_TITLE))
                    diagnosis.descr = result.getString(result.getColumnIndex(COL_DIAGNOSIS_DESCR))
                    diagnosis.datetime = result.getString(result.getColumnIndex(COL_DIAGNOSIS_DATETIME))
                    list.add(diagnosis)
                } while (result.moveToNext())
            }
            result.close()
            db.close()

        } catch (e: Exception) {
            Toast.makeText(context, "Таблицы не существует", Toast.LENGTH_SHORT).show()
        }
        return list
    }

    fun selectDiagnosisMaxID(): MutableList<Diagnosis>{
        var list: MutableList<Diagnosis> = ArrayList()
        val db = this.readableDatabase

        val query = "SELECT id FROM " + TABLE_NAME_DIAGNOSIS +
                " WHERE ${COL_DIAGNOSIS_ID} = (" +
                "SELECT MAX(id) FROM ${TABLE_NAME_DIAGNOSIS}) "
        try{
            val result = db.rawQuery(query, null)

            if (result.moveToFirst()) {
                do {
                    var diagnosis = Diagnosis()
                    diagnosis.id = result.getString(result.getColumnIndex(COL_ID_COURSE)).toInt()
                    list.add(diagnosis)
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
    fun getOneDiagnosis(): MutableList<Diagnosis> {
        var list: MutableList<Diagnosis> = ArrayList()
        val db = this.readableDatabase

        val query = "SELECT * FROM " + TABLE_NAME_COURSE + " " +
                "WHERE ${COL_DIAGNOSIS_ID} = ${DataRecyclerCourse.courseID}"
        try {
            val result = db.rawQuery(query, null)

            if (result.moveToFirst()) {
                do {
                    var course = Course()
                    course.id = result.getString(result.getColumnIndex(COL_ID_COURSE)).toInt()
                    course.email = result.getString(result.getColumnIndex(COL_COURSE_EMAIL))
                    course.procedure =
                        result.getString(result.getColumnIndex(COL_COURSE_PROCEDURE)).toInt()
                    course.medicament =
                        result.getString(result.getColumnIndex(COL_COURSE_MEDICAMENT)).toInt()
                    course.doctor =
                        result.getString(result.getColumnIndex(COL_COURSE_DOCTOR)).toInt()
                    course.food = result.getString(result.getColumnIndex(COL_COURSE_FOOD))
                    course.title = result.getString(result.getColumnIndex(COL_COURSE_TITLE))
                    course.timeCheckSymptom =
                        result.getString(result.getColumnIndex(COL_COURSE_TIME_CH_SMPTM))
                    course.timeHealthNotification =
                        result.getString(result.getColumnIndex(COL_CORSE_TIME_CH_HEALTH))
                    course.notification =
                        result.getString(result.getColumnIndex(COL_COURSE_NOTIFICATION))
                    course.date = result.getString(result.getColumnIndex(COL_COURSE_DATE))
                    course.descr = result.getString(result.getColumnIndex(COL_COURSE_DESCR))
                    course.diagnosisSymptomID =
                        result.getString(result.getColumnIndex(COL_COURSE_DIAGNOSIS_SYMPTOM))
                            .toInt()

                    list.add(course)
                } while (result.moveToNext())
            }
            result.close()
            db.close()

        } catch (e: Exception) {
            Toast.makeText(context, "Таблицы не существует", Toast.LENGTH_SHORT).show()
        }
        return list

    }
*/
    fun selectMaxId(): MutableList<Course> {
        var list: MutableList<Course> = ArrayList()
        val db = this.readableDatabase

        val query = "SELECT id FROM " + TABLE_NAME_COURSE +
                " WHERE ${COL_ID_COURSE} = (" +
                "SELECT MAX(id) FROM ${TABLE_NAME_COURSE}) "
        try {
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

        } catch (e: Exception) {
            Toast.makeText(context, "Таблицы не существует", Toast.LENGTH_SHORT).show()
        }
        return list

    }
}
/*
fun dropDoctorTable(){
    val db = this.writableDatabase
    db.execSQL("DROP TABLE " + TABLE_NAME)
    DATABASE_VERSION = DATABASE_VERSION+1
    db.close()
}*/
