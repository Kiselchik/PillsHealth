package diplom.dev.aidhealth.db.handler

import diplom.dev.aidhealth.db.model.*


import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import diplom.dev.aidhealth.DataRecyclerDiagnosis
import diplom.dev.aidhealth.User


val TABLE_NAME_SYMPTOM_POINT = "SystemPointUser"
val COL_SYMPTOM_POINT_ID = "symptomPointId"
val COL_SYMPTOM_POINT_symptom_ID = "symptomId"
val COL_SYMPTOM_POINT_EMAIL = "email"
val COL_SYMPTOM_POINT_VALUE = "value"
val COL_SYMPTOM_POINT_DATETIME = "datetime"
//val COL_SYMPTOM_POINT_NOTIFICATION = "notification"



val CREATE_TABLE_SYMPTOM_POINT = "CREATE TABLE IF NOT EXISTS  ${TABLE_NAME_SYMPTOM_POINT} ("+
        "$COL_SYMPTOM_POINT_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COL_SYMPTOM_POINT_symptom_ID INTEGER, " +
        "$COL_SYMPTOM_POINT_EMAIL TEXT, $COL_SYMPTOM_POINT_VALUE TEXT," +
        " $COL_SYMPTOM_POINT_DATETIME TEXT, " +
        " FOREIGN KEY(symptomId) REFERENCES Symptom(id), FOREIGN KEY(email) REFERENCES Users(email)  )"

val SQL_DELETE_TABLE_SYMPTOM_POINT = "DROP TABLE IF EXISTS ${TABLE_NAME_SYMPTOM_POINT}"

class DbSymptomPointHandler(var context: Context): SQLiteOpenHelper(context,
    DATABASE_NAME, null,
    DATABASE_VERSION
) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_TABLE_SYMPTOM_POINT)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(SQL_DELETE_TABLE_SYMPTOM_POINT)
        onCreate(db)
    }

    fun dropTableSymptomPoint() {
        val db = writableDatabase
        db.execSQL("DROP TABLE IF EXISTS ${TABLE_NAME_SYMPTOM_POINT}")

    }

    fun deleteRow(id: Int){
        val db = writableDatabase
        db.execSQL("DELETE FROM ${TABLE_NAME_SYMPTOM} WHERE id = ${id} ")
        db.close()
    }


    fun insertSymptomPoint(symptomPoint: SymptomPointUser) {

        val db = writableDatabase
        //  db.execSQL("DELETE FROM ${TABLE_NAME_COURSE}")
        db.execSQL(CREATE_TABLE_SYMPTOM_POINT)

        var cv = ContentValues()
        cv.put(COL_SYMPTOM_POINT_symptom_ID, symptomPoint.symptomId)
        cv.put(COL_SYMPTOM_POINT_EMAIL, symptomPoint.email)
        cv.put(COL_SYMPTOM_POINT_VALUE, symptomPoint.value)
        cv.put(COL_SYMPTOM_POINT_DATETIME, symptomPoint.datetime)
       // cv.put(COL_SYMPTOM_POINT_NOTIFICATION, symptomPoint.notification)

        var result = db.insert(TABLE_NAME_SYMPTOM_POINT, null, cv)
        if (result == -1.toLong())
            Toast.makeText(context, "FAILED SymptomPointUser", Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(context, "SUCCESS SymptomPointUser", Toast.LENGTH_SHORT).show()
    }

    fun readSymptom(): MutableList<SymptomPointUser> {
        var list: MutableList<SymptomPointUser> = ArrayList()
        val db = this.readableDatabase
        db.execSQL(CREATE_TABLE_SYMPTOM_POINT)

        val query = "SELECT * FROM " + TABLE_NAME_SYMPTOM_POINT + " WHERE email = '${User.email}' "
        try {
            val result = db.rawQuery(query, null)

            if (result.moveToFirst()) {
                do {
                    var symptomPoint = SymptomPointUser()
                    symptomPoint.systemPointId = result.getString(result.getColumnIndex(COL_SYMPTOM_POINT_ID)).toInt()
                    symptomPoint.symptomId = result.getString(result.getColumnIndex(COL_SYMPTOM_POINT_symptom_ID)).toInt()
                    symptomPoint.email = result.getString(result.getColumnIndex(COL_SYMPTOM_POINT_EMAIL))
                    symptomPoint.value = result.getString(result.getColumnIndex(COL_SYMPTOM_POINT_VALUE))
                    symptomPoint.datetime = result.getString(result.getColumnIndex(COL_SYMPTOM_POINT_DATETIME))
             //       symptomPoint.notification = result.getString(result.getColumnIndex(COL_SYMPTOM_POINT_NOTIFICATION))
                    list.add(symptomPoint)
                } while (result.moveToNext())
            }
            result.close()
            db.close()

        } catch (e: Exception) {
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


    fun getOneSymptomPoint(id: Int): MutableList<SymptomPointUser> {
        var list: MutableList<SymptomPointUser> = ArrayList()
        val db = this.readableDatabase

        val query = "SELECT * FROM " + TABLE_NAME_SYMPTOM_POINT + " " +
                "WHERE ${COL_SYMPTOM_POINT_symptom_ID} = ${id}"
        try {
            val result = db.rawQuery(query, null)

            if (result.moveToFirst()) {
                do {
                    var symptomPoint = SymptomPointUser()
                    symptomPoint.systemPointId = result.getString(result.getColumnIndex(COL_SYMPTOM_POINT_ID)).toInt()
                    symptomPoint.email = result.getString(result.getColumnIndex(COL_SYMPTOM_POINT_EMAIL))
                    symptomPoint.symptomId = result.getString(result.getColumnIndex(COL_SYMPTOM_POINT_symptom_ID)).toInt()
                    symptomPoint.value = result.getString(result.getColumnIndex(COL_SYMPTOM_POINT_VALUE))
                    symptomPoint.datetime = result.getString(result.getColumnIndex(COL_SYMPTOM_POINT_DATETIME))


                    // symptom.units = result.getString(result.getColumnIndex(COL_SYMPTOM_UNITS)).toInt()
                  //  symptom.ball = result.getString(result.getColumnIndex(COL_SYMPTOM_BALL)).toInt()
                    list.add(symptomPoint)
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



    fun getOneSymptom(idSymptom: Int): MutableList<SymptomPointUser>{
        var list: MutableList<SymptomPointUser> = ArrayList()
        val db = this.readableDatabase

        val query = "SELECT * FROM " + TABLE_NAME_SYMPTOM_POINT + " WHERE $COL_SYMPTOM_POINT_symptom_ID = ${idSymptom}" +
                " AND $COL_SYMPTOM_POINT_EMAIL = ${User.email}"
        try{
            val result = db.rawQuery(query, null)

            if (result.moveToFirst()) {
                do {
                    var symptomPoint = SymptomPointUser()
                    symptomPoint.systemPointId = result.getString(result.getColumnIndex(COL_SYMPTOM_POINT_ID)).toInt()
                    symptomPoint.symptomId = result.getString(result.getColumnIndex(COL_SYMPTOM_POINT_symptom_ID)).toInt()
                    symptomPoint.email = result.getString(result.getColumnIndex(COL_SYMPTOM_POINT_EMAIL))
                    symptomPoint.value = result.getString(result.getColumnIndex(COL_SYMPTOM_POINT_VALUE))
                    symptomPoint.datetime = result.getString(result.getColumnIndex(COL_SYMPTOM_POINT_DATETIME))
                    //       symptomPoint.notification = result.getString(result.getColumnIndex(COL_SYMPTOM_POINT_NOTIFICATION))
                    list.add(symptomPoint)
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


}



/*
fun dropDoctorTable(){
    val db = this.writableDatabase
    db.execSQL("DROP TABLE " + TABLE_NAME)
    DATABASE_VERSION = DATABASE_VERSION+1
    db.close()
}*/
