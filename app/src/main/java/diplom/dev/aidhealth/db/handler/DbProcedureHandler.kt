package diplom.dev.aidhealth.db.handler

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import diplom.dev.aidhealth.DataRecyclerCourse
import diplom.dev.aidhealth.User
import diplom.dev.aidhealth.db.model.Pill
import diplom.dev.aidhealth.db.model.Procedure


val TABLE_NAME_PROCEDURE = "Procedure"
val COL_ID_PROCEDURE = "id"
val COL_PROCEDURE_EMAIL="email"
val COL_PROCEDURE_TITLE = "title"
val COL_PROCEDURE_DESCRIPTION = "descr"

val CREATE_TABLE_PROCEDURE = "CREATE TABLE IF NOT EXISTS  $TABLE_NAME_PROCEDURE ("+
        "$COL_ID_PROCEDURE INTEGER PRIMARY KEY AUTOINCREMENT, $COL_PROCEDURE_EMAIL TEXT , $COL_PROCEDURE_TITLE TEXT, "+
        "$COL_PROCEDURE_DESCRIPTION TEXT, FOREIGN KEY(email) REFERENCES Users(email))"

val SQL_DELETE_TABLE_PROCEDURE = "DROP TABLE IF EXISTS $TABLE_NAME_PROCEDURE"

class DbProcedureHandler(var context: Context): SQLiteOpenHelper(context,
    DATABASE_NAME, null,
    DATABASE_VERSION
) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_TABLE_PROCEDURE)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(SQL_DELETE_TABLE_PROCEDURE)
        onCreate(db)

    }

    fun getOneProcedure(): MutableList<Procedure> {
        var list: MutableList<Procedure> = ArrayList()
        val db = this.readableDatabase

        val query = "SELECT * FROM " + TABLE_NAME_PROCEDURE + " " +
                "WHERE ${COL_ID_PROCEDURE} = ${DataRecyclerCourse.valueID}"
        try{
            val result = db.rawQuery(query, null)

            if (result.moveToFirst()) {
                do {
                    var procedure = Procedure()
                    procedure.id = result.getString(result.getColumnIndex(COL_ID_PROCEDURE)).toInt()
                    procedure.title = result.getString(result.getColumnIndex(COL_PROCEDURE_TITLE))
                    procedure.email = result.getString(result.getColumnIndex(COL_PROCEDURE_EMAIL))
                    procedure.descr = result.getString(result.getColumnIndex(COL_PROCEDURE_DESCRIPTION))

                    list.add(procedure)
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

    fun insertProcedure(procedure: Procedure) {

        val db = writableDatabase
        db.execSQL(CREATE_TABLE_PROCEDURE)
        var cv = ContentValues()
        cv.put(COL_PROCEDURE_EMAIL, procedure.email)
        cv.put(COL_PROCEDURE_TITLE, procedure.title)
        cv.put(COL_PROCEDURE_DESCRIPTION, procedure.descr)
        var result = db.insert(TABLE_NAME_PROCEDURE, null, cv)
        if (result == -1.toLong())
            Toast.makeText(context, "FAILED procedure", Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(context, "SUCCESS procedure", Toast.LENGTH_SHORT).show()
    }

    fun readProcedure(): MutableList<Procedure> {
        var list: MutableList<Procedure> = ArrayList()
        val db = this.readableDatabase

        val query = "SELECT * FROM " + TABLE_NAME_PROCEDURE +" WHERE email = '${User.email}' "
        try{
            val result = db.rawQuery(query, null)

            if (result.moveToFirst()) {
                do {
                    var procedure = Procedure()
                    procedure.id = result.getString(result.getColumnIndex(COL_ID_PROCEDURE)).toInt()
                    procedure.title = result.getString(result.getColumnIndex(COL_PROCEDURE_TITLE))
                    procedure.email = result.getString(result.getColumnIndex(COL_PROCEDURE_EMAIL))
                    procedure.descr = result.getString(result.getColumnIndex(COL_PROCEDURE_DESCRIPTION))

                    list.add(procedure)
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

    fun deleteProcedure(id: Int){
        val db = writableDatabase
        db.execSQL("DELETE FROM ${TABLE_NAME_PROCEDURE} WHERE id = ${id} ")
        db.close()
    }


/*
    fun dropDoctorTable(){
        val db = this.writableDatabase
        db.execSQL("DROP TABLE " + TABLE_NAME)
        DATABASE_VERSION = DATABASE_VERSION+1
        db.close()
    }*/

}
