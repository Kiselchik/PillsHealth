package diplom.dev.aidhealth.db.handler

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import diplom.dev.aidhealth.DataRecyclerCourse
import diplom.dev.aidhealth.User
import diplom.dev.aidhealth.db.model.Doctor

 val TABLE_NAME = "Doctors"
 val COL_ID = "id"
 val COL_DOC_FIRST_NAME = "firstName"
 val COL_DOC_LAST_NAME = "lastName"
 val COL_ADDRESS = "address"
 val COL_POSITION = "position"
 val COL_EMAIL = "email"
 val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS   $TABLE_NAME ("+
           "$COL_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COL_DOC_FIRST_NAME TEXT, "+
           "$COL_DOC_LAST_NAME TEXT, $COL_ADDRESS TEXT,"+
           "$COL_POSITION TEXT, $COL_EMAIL TEXT, FOREIGN KEY(email) REFERENCES Users(email))"

 val SQL_DELETE_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"

class DbHandler(var context: Context): SQLiteOpenHelper(context,
    DATABASE_NAME, null,
    DATABASE_VERSION
) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_TABLE)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(SQL_DELETE_TABLE)
        onCreate(db)
    }

    fun insertDoctor(doctor: Doctor) {

        val db = writableDatabase
        var cv = ContentValues()

            cv.put(COL_DOC_FIRST_NAME, doctor.firstName)
            cv.put(COL_DOC_LAST_NAME, doctor.lastName)
            cv.put(COL_ADDRESS, doctor.address)
            cv.put(COL_POSITION, doctor.position)
            cv.put(COL_EMAIL,doctor.email)
            var result = db.insert(TABLE_NAME, null, cv)
          //  if (result == -1.toLong())
            //    Toast.makeText(context, "FAILED", Toast.LENGTH_SHORT).show()
           // else
            //    Toast.makeText(context, "SUCCESS", Toast.LENGTH_SHORT).show()
        db.close()
        }

    fun readDoctor(): MutableList<Doctor> {
        var list: MutableList<Doctor> = ArrayList()
        val db = this.readableDatabase


        val query = "SELECT * FROM " + TABLE_NAME +" WHERE email = '${User.email}' "
        try{
        val result = db.rawQuery(query, null)

        if (result.moveToFirst()) {
            do {
                var doctor = Doctor()
               doctor.id = result.getString(result.getColumnIndex(COL_ID)).toInt()
                doctor.firstName = result.getString(result.getColumnIndex(COL_DOC_FIRST_NAME))
                doctor.lastName = result.getString(result.getColumnIndex(COL_DOC_LAST_NAME))
                doctor.position = result.getString(result.getColumnIndex(COL_POSITION))
                doctor.address = result.getString(result.getColumnIndex(COL_ADDRESS))
                doctor.email = result.getString(result.getColumnIndex(COL_EMAIL))
                list.add(doctor)
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

    fun getOneDoctor(): MutableList<Doctor> {
        var list: MutableList<Doctor> = ArrayList()
        val db = this.readableDatabase


        val query = "SELECT * FROM " + TABLE_NAME + " " +
                "WHERE id = ${DataRecyclerCourse.doctorID}"
        try{
            val result = db.rawQuery(query, null)

            if (result.moveToFirst()) {
                do {
                    var doctor = Doctor()
                    doctor.id = result.getString(result.getColumnIndex(COL_ID)).toInt()
                    doctor.firstName = result.getString(result.getColumnIndex(COL_DOC_FIRST_NAME))
                    doctor.lastName = result.getString(result.getColumnIndex(COL_DOC_LAST_NAME))
                    doctor.position = result.getString(result.getColumnIndex(COL_POSITION))
                    doctor.address = result.getString(result.getColumnIndex(COL_ADDRESS))
                    doctor.email = result.getString(result.getColumnIndex(COL_EMAIL))
                    list.add(doctor)
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


    fun clearTableDoctor(){
        val db = writableDatabase
        db.execSQL("DELETE FROM ${TABLE_NAME}")

    }

    fun deleteDoctor(docID : Int) {
        val db = writableDatabase
        db.execSQL("DELETE FROM ${TABLE_NAME} WHERE $COL_ID = $docID " )
        db.close()
    }

    fun dropDoctorTable(){
        val db = this.writableDatabase
        db.execSQL("DROP TABLE " + TABLE_NAME)
        db.close()
    }

}
