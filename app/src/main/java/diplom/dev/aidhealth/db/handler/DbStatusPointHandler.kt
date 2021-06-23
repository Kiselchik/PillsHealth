package diplom.dev.aidhealth.db.handler

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import diplom.dev.aidhealth.User
import diplom.dev.aidhealth.db.model.StatusCourse
import diplom.dev.aidhealth.db.model.StatusPoint


val TABLE_NAME_STATUS_POINT = "StatusPoint"
val COL_ID_STATUS_POINT = "id"
val COL_STATUS_POINT_TITLE = "title"

val CREATE_TABLE_STATUS_POINT = "CREATE TABLE IF NOT EXISTS  $TABLE_NAME_STATUS_POINT("+
        "$COL_ID_STATUS_POINT INTEGER PRIMARY KEY AUTOINCREMENT, $COL_STATUS_POINT_TITLE TEXT)"

val SQL_DELETE_TABLE_STATUS_POINT = "DROP TABLE IF EXISTS ${TABLE_NAME_STATUS_POINT}"

class DbStatusPointHandler(var context: Context): SQLiteOpenHelper(context,
    DATABASE_NAME, null,
    DATABASE_VERSION
) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_TABLE_STATUS_POINT)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(SQL_DELETE_TABLE_STATUS_POINT)
        onCreate(db)
    }

    fun getStatusTitle(statusPointID: Int): MutableList<StatusPoint> {
        var list: MutableList<StatusPoint> = ArrayList()
        val db = this.readableDatabase

        val query = "SELECT title FROM " + TABLE_NAME_STATUS_POINT + " WHERE id = ${statusPointID} "
        try {
            val result = db.rawQuery(query, null)

            if (result.moveToFirst()) {
                do {
                    var statusPoint = StatusPoint()
                    //   statusPoint.id = result.getString(result.getColumnIndex(COL_ID_STATUS_POINT)).toInt()
                    statusPoint.title =
                        result.getString(result.getColumnIndex(COL_STATUS_POINT_TITLE))

                    list.add(statusPoint)
                } while (result.moveToNext())
            }
            result.close()
            db.close()

        } catch(e: Exception) {
            Toast.makeText(context, "Таблицы не существует", Toast.LENGTH_SHORT).show()
        }
        return list
    }


    fun insertStatusPoint(statusPoint: StatusPoint) {

        val db = writableDatabase
         db.execSQL(CREATE_TABLE_STATUS_POINT)

        var cv = ContentValues()

        cv.put(COL_STATUS_POINT_TITLE, statusPoint.title)


        var result = db.insert(TABLE_NAME_STATUS_POINT, null, cv)
        if (result == -1.toLong())
            Toast.makeText(context, "FAILED", Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(context, "SUCCESS", Toast.LENGTH_SHORT).show()
    }

    fun readStatusPoint(): MutableList<StatusPoint> {
        var list: MutableList<StatusPoint> = ArrayList()
        val db = this.readableDatabase

        val query = "SELECT * FROM " + TABLE_NAME_STATUS_POINT + " WHERE email = '${User.email}' "
        try{
            val result = db.rawQuery(query, null)

            if (result.moveToFirst()) {
                do {
                    var statusPoint = StatusPoint()
                    statusPoint.id = result.getString(result.getColumnIndex(COL_ID_STATUS_POINT)).toInt()
                    statusPoint.title = result.getString(result.getColumnIndex(COL_STATUS_POINT_TITLE))

                    list.add(statusPoint)
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
    fun getIdDone(): MutableList<StatusPoint> {
        var list: MutableList<StatusPoint> = ArrayList()
        val db = this.readableDatabase

        val query = "SELECT id FROM " + TABLE_NAME_STATUS_POINT + " WHERE title = 'Подтвержден'"
        try{
            val result = db.rawQuery(query, null)

            if (result.moveToFirst()) {
                do {
                    var statusPoint = StatusPoint()
                    statusPoint.id = result.getString(result.getColumnIndex(COL_ID_STATUS_POINT)).toInt()
                    // statusPoint.title = result.getString(result.getColumnIndex(COL_STATUS_POINT_TITLE))

                    list.add(statusPoint)
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

    fun getIdStatusPointWait(): MutableList<StatusPoint> {
            var list: MutableList<StatusPoint> = ArrayList()
            val db = this.readableDatabase

            val query = "SELECT id FROM " + TABLE_NAME_STATUS_POINT + " WHERE title = 'Ожидание'"
            try{
                val result = db.rawQuery(query, null)

                if (result.moveToFirst()) {
                    do {
                        var statusPoint = StatusPoint()
                        statusPoint.id = result.getString(result.getColumnIndex(COL_ID_STATUS_POINT)).toInt()
                       // statusPoint.title = result.getString(result.getColumnIndex(COL_STATUS_POINT_TITLE))

                        list.add(statusPoint)
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

    fun getIdStatusPointPostponed(): MutableList<StatusPoint> {
        var list: MutableList<StatusPoint> = ArrayList()
        val db = this.readableDatabase

        val query = "SELECT id FROM " + TABLE_NAME_STATUS_POINT + " WHERE title = 'Отложен'"
        try{
            val result = db.rawQuery(query, null)

            if (result.moveToFirst()) {
                do {
                    var statusPoint = StatusPoint()
                    statusPoint.id = result.getString(result.getColumnIndex(COL_ID_STATUS_POINT)).toInt()
                    // statusPoint.title = result.getString(result.getColumnIndex(COL_STATUS_POINT_TITLE))

                    list.add(statusPoint)
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

    fun getIdStatusPointConfirmed(): MutableList<StatusPoint> {
        var list: MutableList<StatusPoint> = ArrayList()
        val db = this.readableDatabase

        val query = "SELECT id FROM " + TABLE_NAME_STATUS_POINT + " WHERE title = 'Подтвержден'"
        try{
            val result = db.rawQuery(query, null)

            if (result.moveToFirst()) {
                do {
                    var statusPoint = StatusPoint()
                    statusPoint.id = result.getString(result.getColumnIndex(COL_ID_STATUS_POINT)).toInt()
                    // statusPoint.title = result.getString(result.getColumnIndex(COL_STATUS_POINT_TITLE))

                    list.add(statusPoint)
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
