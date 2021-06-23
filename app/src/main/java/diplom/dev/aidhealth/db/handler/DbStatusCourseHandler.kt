package diplom.dev.aidhealth.db.handler

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import diplom.dev.aidhealth.DataRecyclerCourse
import diplom.dev.aidhealth.User
import diplom.dev.aidhealth.db.model.Course
import diplom.dev.aidhealth.db.model.StatusCourse


val TABLE_NAME_STATUS_COURSE = "StatusCourse"
val COL_ID_STATUS_COURSE = "id"
val COL_STATUS_COURSE_TITLE = "title"

val CREATE_TABLE_STATUS_COURSE = "CREATE TABLE IF NOT EXISTS $TABLE_NAME_STATUS_COURSE("+
        "$COL_ID_STATUS_COURSE INTEGER PRIMARY KEY AUTOINCREMENT, $COL_STATUS_COURSE_TITLE TEXT)"

val SQL_DELETE_TABLE_STATUS_COURSE = "DROP TABLE IF EXISTS ${TABLE_NAME_STATUS_COURSE}"

class DbStatusCourseHandler(var context: Context): SQLiteOpenHelper(context,
    DATABASE_NAME, null,
    DATABASE_VERSION
) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_TABLE_STATUS_COURSE)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(SQL_DELETE_TABLE_STATUS_COURSE)
        onCreate(db)
    }

    fun insertStatusCourse(statusCourse: StatusCourse) {

        val db = writableDatabase
        db.execSQL(CREATE_TABLE_STATUS_COURSE)

       /// db.execSQL("INSERT INTO ${TABLE_NAME_STATUS_COURSE} (${ COL_STATUS_COURSE_TITLE}) VALUES " +
          //      "(Архивный), (Текущий), (Завершенный), (прерванный)")

        var cv = ContentValues()
        cv.put(COL_ID_STATUS_COURSE, statusCourse.id)
        cv.put(COL_STATUS_COURSE_TITLE, statusCourse.title)


       var result = db.insert(TABLE_NAME_STATUS_COURSE, null, cv)
      /*  if (result == -1.toLong())
            Toast.makeText(context, "FAILED status course", Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(context, "SUCCESS status course", Toast.LENGTH_SHORT).show()*/
    }



    fun readStatusCourse(): MutableList<StatusCourse> {
        var list: MutableList<StatusCourse> = ArrayList()
        val db = this.readableDatabase

        val query = "SELECT * FROM " + TABLE_NAME_STATUS_COURSE
        try{
            val result = db.rawQuery(query, null)

            if (result.moveToFirst()) {
                do {
                    var statusCourse = StatusCourse()
                    statusCourse.id = result.getString(result.getColumnIndex(COL_ID_STATUS_COURSE)).toInt()
                    statusCourse.title = result.getString(result.getColumnIndex(COL_STATUS_COURSE_TITLE))

                    list.add(statusCourse)
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


    fun getStatusArchive(): MutableList<StatusCourse>{
        var list: MutableList<StatusCourse> = ArrayList()
        val db = this.readableDatabase

        val query = "SELECT id FROM " + TABLE_NAME_STATUS_COURSE + " WHERE title = 'Архивный' "
        try{
            val result = db.rawQuery(query, null)

            if (result.moveToFirst()) {
                do {
                    var statusCourse = StatusCourse()
                    statusCourse.id = result.getString(result.getColumnIndex(COL_ID_STATUS_COURSE)).toInt()
                   // statusCourse.title = result.getString(result.getColumnIndex(COL_STATUS_COURSE_TITLE))

                    list.add(statusCourse)
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

    fun getOneStatus(): MutableList<StatusCourse> {
        var list: MutableList<StatusCourse> = ArrayList()
        val db = this.readableDatabase

        val query = "SELECT * FROM " + TABLE_NAME_STATUS_COURSE + " " +
                "WHERE id = ${DataRecyclerCourse.statusIDCourse}"
        try{
            val result = db.rawQuery(query, null)

            if (result.moveToFirst()) {
                do {
                    var statusCourse = StatusCourse()
                    statusCourse.id = result.getString(result.getColumnIndex(COL_ID_STATUS_COURSE)).toInt()
                    statusCourse.title = result.getString(result.getColumnIndex(COL_STATUS_COURSE_TITLE))

                    list.add(statusCourse)
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
