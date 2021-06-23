package diplom.dev.aidhealth.db.handler

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import diplom.dev.aidhealth.DataRecyclerDiagnosis
import diplom.dev.aidhealth.User
import diplom.dev.aidhealth.db.model.Course
import diplom.dev.aidhealth.db.model.DiagnosisWithSymptom
import diplom.dev.aidhealth.db.model.Symptom
import diplom.dev.aidhealth.db.model.SystemSymptom


val TABLE_NAME_SYSTEM_SYMPTOM = "SystemSymptom"
val COL_SYSTEM_SYMPTOM_ID = "id"
val COL_SYSTEM_SYMPTOM_TITLE = "title"
val COL_SYSTEM_SYMPTOM_MEASUREMENT = "measurement"
val COL_SYSTEM_SYMPTOM_MASK = "mask"



val CREATE_TABLE_SYSTEM_SYMPTOM = "CREATE TABLE IF NOT EXISTS  ${TABLE_NAME_SYSTEM_SYMPTOM} ("+
        "$COL_SYSTEM_SYMPTOM_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COL_SYSTEM_SYMPTOM_TITLE TEXT, " +
        "$COL_SYSTEM_SYMPTOM_MEASUREMENT TEXT, $COL_SYSTEM_SYMPTOM_MASK TEXT)"

val SQL_DELETE_TABLE_SYSTEM_SYMPTOM = "DROP TABLE IF EXISTS ${TABLE_NAME_SYSTEM_SYMPTOM}"

class DbSystemSymptomHandler(var context: Context): SQLiteOpenHelper(context,
    DATABASE_NAME, null,
    DATABASE_VERSION
) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_TABLE_SYSTEM_SYMPTOM)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(SQL_DELETE_TABLE_SYSTEM_SYMPTOM)
        onCreate(db)
    }

    fun clearTableSystemSymptom() {
        val db = writableDatabase
        db.execSQL("DELETE FROM ${TABLE_NAME_SYSTEM_SYMPTOM}")

    }
    fun dropTableSystemSymptom() {
        val db = writableDatabase
        db.execSQL("DROP TABLE IF EXISTS ${TABLE_NAME_SYSTEM_SYMPTOM}")

    }


    fun deleteRow(id: Int){
        val db = writableDatabase
        db.execSQL("DELETE FROM ${TABLE_NAME_SYMPTOM} WHERE id = ${id} ")
        db.close()
    }


    fun insertSystemSymptom(systemSymptom: SystemSymptom) {

        val db = writableDatabase
        //  db.execSQL("DELETE FROM ${TABLE_NAME_COURSE}")
        db.execSQL(CREATE_TABLE_SYSTEM_SYMPTOM)

        var cv = ContentValues()
        cv.put(COL_SYSTEM_SYMPTOM_TITLE, systemSymptom.title)
        cv.put(COL_SYSTEM_SYMPTOM_MEASUREMENT, systemSymptom.measurement)
        cv.put(COL_SYSTEM_SYMPTOM_MASK, systemSymptom.mask)

        var result = db.insert(TABLE_NAME_SYSTEM_SYMPTOM, null, cv)
        if (result == -1.toLong())
            Toast.makeText(context, "FAILED SystemSymptom", Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(context, "SUCCESS SystemSymptom", Toast.LENGTH_SHORT).show()
    }

    fun readSystemSymptom(): MutableList<SystemSymptom> {
        var list: MutableList<SystemSymptom> = ArrayList()
        val db = this.readableDatabase
        db.execSQL(CREATE_TABLE_SYSTEM_SYMPTOM)

        val query = "SELECT * FROM " + TABLE_NAME_SYSTEM_SYMPTOM
        try {
            val result = db.rawQuery(query, null)

            if (result.moveToFirst()) {
                do {
                    var systemSymptom = SystemSymptom()
                    systemSymptom.id = result.getString(result.getColumnIndex(COL_SYSTEM_SYMPTOM_ID)).toInt()
                    systemSymptom.title = result.getString(result.getColumnIndex(COL_SYSTEM_SYMPTOM_TITLE))
                    systemSymptom.measurement = result.getString(result.getColumnIndex(COL_SYSTEM_SYMPTOM_MEASUREMENT))
                    systemSymptom.mask = result.getString(result.getColumnIndex(COL_SYSTEM_SYMPTOM_MASK))
                    list.add(systemSymptom)
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


    fun getOneSystemSymptom(id: Int): MutableList<SystemSymptom> {
        var list: MutableList<SystemSymptom> = ArrayList()
        val db = this.readableDatabase

        val query = "SELECT * FROM " + TABLE_NAME_SYSTEM_SYMPTOM + " " +
                "WHERE ${COL_SYSTEM_SYMPTOM_ID} = ${id}"
        try {
            val result = db.rawQuery(query, null)

            if (result.moveToFirst()) {
                do {
                    var systemSymptom = SystemSymptom()
                    systemSymptom.id = result.getString(result.getColumnIndex(COL_SYSTEM_SYMPTOM_ID)).toInt()
                    systemSymptom.title = result.getString(result.getColumnIndex(COL_SYSTEM_SYMPTOM_TITLE))
                    systemSymptom.measurement = result.getString(result.getColumnIndex(COL_SYSTEM_SYMPTOM_MEASUREMENT))
                    systemSymptom.mask = result.getString(result.getColumnIndex(COL_SYSTEM_SYMPTOM_MASK))
                    list.add(systemSymptom)

                   // symptom.units = result.getString(result.getColumnIndex(COL_SYMPTOM_UNITS)).toInt()
                   // symptom.ball = result.getString(result.getColumnIndex(COL_SYMPTOM_BALL)).toInt()
                    list.add(systemSymptom)
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
