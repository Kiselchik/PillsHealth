package diplom.dev.aidhealth.db.handler

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import diplom.dev.aidhealth.DataRecyclerCourse
import diplom.dev.aidhealth.DataRecyclerDiagnosis
import diplom.dev.aidhealth.db.model.Course
import diplom.dev.aidhealth.db.model.DiagnosisSymptom

val TABLE_NAME_DIAGNOSIS_SYMPTOM = "DiagnosysSymptom"
val COL_DIAGNOSYS_SYMPTOM_ID = "id"
val COL_DIAGNOSYS_SYMPTOM_symptomID = "symptomID"
val COL_DIAGNOSYS_SYMPTOM_diagnosisID = "diagnosisID"


val CREATE_TABLE_DIAGNOSIS_SYMPTOM = "CREATE TABLE IF NOT EXISTS  $TABLE_NAME_DIAGNOSIS_SYMPTOM ("+
        "$COL_DIAGNOSYS_SYMPTOM_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COL_DIAGNOSYS_SYMPTOM_symptomID INTEGER, " +
        "$COL_DIAGNOSYS_SYMPTOM_diagnosisID INTEGER," +
        "FOREIGN KEY(symptomID) REFERENCES Symptom(id), " +
        "FOREIGN KEY(diagnosisID) REFERENCES Diagnosis(id)   )"

val SQL_DELETE_TABLE_DIAGNOSIS_SYMPTOM = "DROP TABLE IF EXISTS ${TABLE_NAME_DIAGNOSIS_SYMPTOM}"

class DbDiagnosisSymptomHandler(var context: Context): SQLiteOpenHelper(context,
    DATABASE_NAME, null,
    DATABASE_VERSION
) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_TABLE_DIAGNOSIS_SYMPTOM)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(SQL_DELETE_TABLE_DIAGNOSIS_SYMPTOM)
        onCreate(db)
    }

    fun deleteRow(id: Int){
        val db = writableDatabase
        db.execSQL("DELETE FROM ${TABLE_NAME_DIAGNOSIS_SYMPTOM} WHERE diagnosisID = ${id} ")
        db.close()
    }

    fun deleteRowSymptom(id: Int){
        val db = writableDatabase
        db.execSQL("DELETE FROM ${TABLE_NAME_DIAGNOSIS} WHERE symptomID = ${id} ")
        db.close()
    }

    fun clearTableDiagnosisSymptom(){
        val db = writableDatabase
        db.execSQL("DELETE FROM ${TABLE_NAME_DIAGNOSIS_SYMPTOM}")

    }

    fun insertDiagnosisSymptom(diagnosisSymptom: DiagnosisSymptom) {

        val db = writableDatabase
        //  db.execSQL("DELETE FROM ${TABLE_NAME_COURSE}")
         db.execSQL(CREATE_TABLE_DIAGNOSIS_SYMPTOM)
      /*  val query = "INSERT INTO ${TABLE_NAME_DIAGNOSIS_SYMPTOM} " +
                "(${COL_DIAGNOSYS_SYMPTOM_symptomID}, ${COL_DIAGNOSYS_SYMPTOM_diagnosisID})"*/
        var cv = ContentValues()

        cv.put(COL_DIAGNOSYS_SYMPTOM_symptomID, diagnosisSymptom.symptomID)
        cv.put(COL_DIAGNOSYS_SYMPTOM_diagnosisID, diagnosisSymptom.diagnosisID)


        var result = db.insert(TABLE_NAME_DIAGNOSIS_SYMPTOM, null, cv)
        if (result == -1.toLong())
            Toast.makeText(context, "FAILED diagnosisSymptom", Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(context, "SUCCESS diagnosisSymptom", Toast.LENGTH_SHORT).show()
    }

    fun readDiagnosisSymptom(): MutableList<DiagnosisSymptom> {
        var list: MutableList<DiagnosisSymptom> = ArrayList()
        val db = this.readableDatabase

        val query = "SELECT * FROM " + TABLE_NAME_DIAGNOSIS_SYMPTOM
        try{
            val result = db.rawQuery(query, null)

            if (result.moveToFirst()) {
                do {
                    var diagnosisSymptom = DiagnosisSymptom()
                    diagnosisSymptom.id = result.getString(result.getColumnIndex(COL_DIAGNOSYS_SYMPTOM_ID)).toInt()
                    diagnosisSymptom.symptomID = result.getString(result.getColumnIndex(COL_DIAGNOSYS_SYMPTOM_symptomID)).toInt()
                    diagnosisSymptom.diagnosisID = result.getString(result.getColumnIndex(COL_DIAGNOSYS_SYMPTOM_diagnosisID)).toInt()

                    list.add(diagnosisSymptom)
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

    fun readOneDiagnosisSymptom(): MutableList<DiagnosisSymptom> {
        var list: MutableList<DiagnosisSymptom> = ArrayList()
        val db = this.readableDatabase

        val query = "SELECT * FROM " + TABLE_NAME_DIAGNOSIS_SYMPTOM + " " +
                "WHERE ${COL_DIAGNOSYS_SYMPTOM_diagnosisID} = ${DataRecyclerDiagnosis.diagnosisID}"
        try{
            val result = db.rawQuery(query, null)

            if (result.moveToFirst()) {
                do {
                    var diagnosisSymptom = DiagnosisSymptom()
                    diagnosisSymptom.id = result.getString(result.getColumnIndex(COL_DIAGNOSYS_SYMPTOM_ID)).toInt()
                    diagnosisSymptom.symptomID = result.getString(result.getColumnIndex(COL_DIAGNOSYS_SYMPTOM_symptomID)).toInt()
                    diagnosisSymptom.diagnosisID = result.getString(result.getColumnIndex(COL_DIAGNOSYS_SYMPTOM_diagnosisID)).toInt()

                    list.add(diagnosisSymptom)
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
