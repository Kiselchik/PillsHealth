package diplom.dev.aidhealth.db.handler

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import diplom.dev.aidhealth.DataRecyclerCourse
import diplom.dev.aidhealth.db.model.Course
import diplom.dev.aidhealth.db.model.CourseHistory


val TABLE_NAME_COURSE_HISTORY = "CourseHistory"
val COL_COURSE_HISTORY_ID ="courseHistoryID"
val COL_COURSE_HISTORY_COURSE_ID = "courseID"
val COL_COURSE_HISTORY_STATUS_ID = "statusID"
val COL_COURSE_HISTORY_DATETIME = "datetime"


val CREATE_TABLE_COURSE_HISTORY = "CREATE TABLE IF NOT EXISTS   $TABLE_NAME_COURSE_HISTORY ("+
        "$COL_COURSE_HISTORY_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COL_COURSE_HISTORY_COURSE_ID INTEGER, " +
        "$COL_COURSE_HISTORY_STATUS_ID INTEGER, $COL_COURSE_HISTORY_DATETIME TEXT, " +
        " FOREIGN KEY(courseID) REFERENCES Course(id), " +
        "FOREIGN KEY(statusID) REFERENCES StatusCourse(id)  )"

val SQL_DELETE_TABLE_COURSE_HISTORY = "DROP TABLE IF EXISTS ${TABLE_NAME_COURSE_HISTORY}"

class DbCourseHistoryHandler(var context: Context): SQLiteOpenHelper(context,
    DATABASE_NAME, null,
    DATABASE_VERSION
) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_TABLE_COURSE_HISTORY)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(SQL_DELETE_TABLE_COURSE_HISTORY)
        onCreate(db)
    }

    fun clearTableCourseHistory(){
        val db = writableDatabase
        db.execSQL("DELETE FROM ${TABLE_NAME_COURSE_HISTORY}")
        db.close()


    }

    fun deleteRow(id: Int){
        val db = writableDatabase
        db.execSQL("DELETE FROM ${TABLE_NAME_COURSE_HISTORY} WHERE courseID = ${id} ")
        db.close()
    }

    fun insertCourseHistory(courseHistory: CourseHistory) {

        val db = writableDatabase
         db.execSQL(CREATE_TABLE_COURSE_HISTORY)

        var cv = ContentValues()
      //  cv.put(COL_COURSE_HISTORY_ID, courseHistory.id)
        cv.put(COL_COURSE_HISTORY_COURSE_ID, courseHistory.courseID)
        cv.put(COL_COURSE_HISTORY_STATUS_ID, courseHistory.statusID)
        cv.put(COL_COURSE_HISTORY_DATETIME, courseHistory.datetime)


        var result = db.insert(TABLE_NAME_COURSE_HISTORY, null, cv)
       if (result == -1.toLong())
            Toast.makeText(context, "FAILED course history", Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(context, "SUCCESS course history", Toast.LENGTH_SHORT).show()
    }

    fun readCourseHistory(): MutableList<CourseHistory> {
        var list: MutableList<CourseHistory> = ArrayList()
        val db = this.readableDatabase

        val query = "SELECT * FROM " + TABLE_NAME_COURSE_HISTORY
        try{
            val result = db.rawQuery(query, null)

            if (result.moveToFirst()) {
                do {
                    var courseHistory = CourseHistory()
                    courseHistory.id = result.getString(result.getColumnIndex(COL_COURSE_HISTORY_ID)).toInt()
                    courseHistory.courseID = result.getString(result.getColumnIndex(COL_COURSE_HISTORY_COURSE_ID)).toInt()
                    courseHistory.statusID = result.getString(result.getColumnIndex(COL_COURSE_HISTORY_STATUS_ID)).toInt()
                    courseHistory.datetime = result.getString(result.getColumnIndex(COL_COURSE_HISTORY_DATETIME))
                    list.add(courseHistory)
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

    fun getChooseCourse(): MutableList<CourseHistory>{
        var list: MutableList<CourseHistory> = ArrayList()
        val db = this.readableDatabase

        val query = "SELECT * FROM " + TABLE_NAME_COURSE_HISTORY + " " +
                "WHERE ${COL_COURSE_HISTORY_COURSE_ID} = ${DataRecyclerCourse.courseID} "
        try{
            val result = db.rawQuery(query, null)

            if (result.moveToFirst()) {
                do {
                    var courseHistory = CourseHistory()
                    courseHistory.id = result.getString(result.getColumnIndex(COL_COURSE_HISTORY_ID)).toInt()
                    courseHistory.courseID = result.getString(result.getColumnIndex(COL_COURSE_HISTORY_COURSE_ID)).toInt()
                    courseHistory.statusID = result.getString(result.getColumnIndex(COL_COURSE_HISTORY_STATUS_ID)).toInt()
                    courseHistory.datetime = result.getString(result.getColumnIndex(COL_COURSE_HISTORY_DATETIME))
                    list.add(courseHistory)
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
