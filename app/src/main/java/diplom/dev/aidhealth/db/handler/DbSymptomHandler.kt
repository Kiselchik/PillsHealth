package diplom.dev.aidhealth.db.handler

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import diplom.dev.aidhealth.DataRecyclerCourse
import diplom.dev.aidhealth.DataRecyclerDiagnosis
import diplom.dev.aidhealth.User
import diplom.dev.aidhealth.db.model.Course
import diplom.dev.aidhealth.db.model.Diagnosis
import diplom.dev.aidhealth.db.model.DiagnosisWithSymptom
import diplom.dev.aidhealth.db.model.Symptom


val TABLE_NAME_SYMPTOM = "Symptom"
val COL_SYMPTOM_ID = "id"
val COL_SYMPTOM_system_symptom_id = "systemSymptomId"
val COL_SYMPTOM_EMAIL = "email"
val COL_SYMPTOM_TITLE = "title"
//val COL_SYMPTOM_UNITS = "units"
//val COL_SYMPTOM_BALL = "ball"


val CREATE_TABLE_SYMPTOM = "CREATE TABLE IF NOT EXISTS  $TABLE_NAME_SYMPTOM ("+
        "$COL_SYMPTOM_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COL_SYMPTOM_EMAIL TEXT, " +
        "$COL_SYMPTOM_TITLE TEXT," +
        "$COL_SYMPTOM_system_symptom_id TEXT," +
        " FOREIGN KEY(email) REFERENCES Users(email), " +
        " FOREIGN KEY(systemSymptomId) REFERENCES SystemPointUser(symptomPointId) )"

val SQL_DELETE_TABLE_SYMPTOM = "DROP TABLE IF EXISTS ${TABLE_NAME_SYMPTOM}"

class DbSymptomHandler(var context: Context): SQLiteOpenHelper(context,
    DATABASE_NAME, null,
    DATABASE_VERSION
) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_TABLE_SYMPTOM)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(SQL_DELETE_TABLE_SYMPTOM)
        onCreate(db)
    }

    fun clearTableSymptom() {
        val db = writableDatabase
        db.execSQL("DROP TABLE IF EXISTS ${TABLE_NAME_SYMPTOM}")

    }

    fun deleteRow(id: Int){
        val db = writableDatabase
        db.execSQL("DELETE FROM ${TABLE_NAME_SYMPTOM} WHERE id = ${id} ")
        db.close()
    }


    fun insertSymptom(symptom: Symptom) {

        val db = writableDatabase
        //  db.execSQL("DELETE FROM ${TABLE_NAME_COURSE}")
         db.execSQL(CREATE_TABLE_SYMPTOM)

        var cv = ContentValues()
        cv.put(COL_SYMPTOM_EMAIL, symptom.email)
        cv.put(COL_SYMPTOM_TITLE, symptom.title)
      //  cv.put(COL_SYMPTOM_UNITS, symptom.units)
       // cv.put(COL_SYMPTOM_BALL, symptom.ball)
        cv.put(COL_SYMPTOM_system_symptom_id, symptom.systemSymptomId)


        var result = db.insert(TABLE_NAME_SYMPTOM, null, cv)
        if (result == -1.toLong())
            Toast.makeText(context, "FAILED symptom", Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(context, "SUCCESS symptom", Toast.LENGTH_SHORT).show()
    }

    fun readSymptom(): MutableList<Symptom> {
        var list: MutableList<Symptom> = ArrayList()
        val db = this.readableDatabase

        val query = "SELECT * FROM " + TABLE_NAME_SYMPTOM + " WHERE email = '${User.email}'"
        try{

            val result = db.rawQuery(query, null)

            if (result.moveToFirst()) {
                do {
                    var symptom = Symptom()
                    symptom.id = result.getString(result.getColumnIndex(COL_SYMPTOM_ID)).toInt()
                    symptom.email = result.getString(result.getColumnIndex(COL_SYMPTOM_EMAIL))
                    symptom.title = result.getString(result.getColumnIndex(COL_SYMPTOM_TITLE))
                  //  symptom.units = result.getString(result.getColumnIndex(COL_SYMPTOM_UNITS)).toInt()
                   // symptom.ball = result.getString(result.getColumnIndex(COL_SYMPTOM_BALL)).toInt()
                    symptom.systemSymptomId = result.getString(result.getColumnIndex(COL_SYMPTOM_system_symptom_id))
                    list.add(symptom)
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


    fun getDiagnosisWithSymptom(): MutableList<DiagnosisWithSymptom>{
        var list: MutableList<DiagnosisWithSymptom> = ArrayList()
        val db = this.readableDatabase
        val query = "SELECT ${TABLE_NAME_SYMPTOM}.${COL_SYMPTOM_TITLE} " +
                "FROM ${TABLE_NAME_SYMPTOM} JOIN ${TABLE_NAME_DIAGNOSIS_SYMPTOM} " +
                "ON ${TABLE_NAME_SYMPTOM}.${COL_SYMPTOM_ID} = ${TABLE_NAME_DIAGNOSIS_SYMPTOM}.${COL_DIAGNOSYS_SYMPTOM_symptomID} " +
                "WHERE ${TABLE_NAME_DIAGNOSIS_SYMPTOM}.${COL_DIAGNOSYS_SYMPTOM_diagnosisID} = ${DataRecyclerDiagnosis.diagnosisID}"

        try{
            val result = db.rawQuery(query, null)

            if (result.moveToFirst()) {
                do {
                    var diagnosisWithSymptom = DiagnosisWithSymptom()
                    diagnosisWithSymptom.diagnosisSymptomTitle = result.getString(result.getColumnIndex(COL_SYMPTOM_TITLE))
                    list.add(diagnosisWithSymptom)
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


    fun getOneSymptom(id: Int): MutableList<Symptom> {
        var list: MutableList<Symptom> = ArrayList()
        val db = this.readableDatabase

        val query = "SELECT * FROM " + TABLE_NAME_SYMPTOM + " " +
                "WHERE ${COL_SYMPTOM_ID} = ${id}"
        try {
            val result = db.rawQuery(query, null)

            if (result.moveToFirst()) {
                do {
                    var symptom = Symptom()
                    symptom.id = result.getString(result.getColumnIndex(COL_SYMPTOM_ID)).toInt()
                    symptom.email = result.getString(result.getColumnIndex(COL_SYMPTOM_EMAIL))
                    symptom.title = result.getString(result.getColumnIndex(COL_SYMPTOM_TITLE))
                  //  symptom.units = result.getString(result.getColumnIndex(COL_SYMPTOM_UNITS)).toInt()
                  //  symptom.ball = result.getString(result.getColumnIndex(COL_SYMPTOM_BALL)).toInt()
                    list.add(symptom)
                } while (result.moveToNext())
            }
            result.close()
            db.close()

        } catch (e: Exception) {
            Toast.makeText(context, "Таблицы не существует", Toast.LENGTH_SHORT).show()
        }
        return list

    }

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
