package diplom.dev.aidhealth.db.handler

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import diplom.dev.aidhealth.DataRecyclerCourse
import diplom.dev.aidhealth.PointCourse
import diplom.dev.aidhealth.User
import diplom.dev.aidhealth.db.model.*
import java.util.*
import kotlin.collections.ArrayList


val TABLE_NAME_COURSE_POINT = "CoursePoint"
val COL_COURSE_POINT_ID ="id"
val COL_COURSE_POINT_DOSE = "dose"
val COL_COURSE_POINT_courseID = "courseID"
val COL_COURSE_POINT_DAY = "day"
val COL_COURSE_POINT_TIME = "time"
val COL_COURSE_POINT_DATETIME_STATUS = "datetimeStatus"
val COL_COURSE_POINT_STATUS = "statusPointID"


val CREATE_TABLE_COURSE_POINT = "CREATE TABLE IF NOT EXISTS   $TABLE_NAME_COURSE_POINT ("+
        "$COL_COURSE_POINT_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COL_COURSE_POINT_DOSE TEXT , $COL_COURSE_POINT_courseID INTEGER ,$COL_COURSE_POINT_DAY TEXT, " +
        "$COL_COURSE_POINT_TIME TEXT, $COL_COURSE_POINT_STATUS INTEGER, $COL_COURSE_POINT_DATETIME_STATUS TEXT, " +
        " FOREIGN KEY(statusPointID) REFERENCES StatusPoint(id) )"

val SQL_DELETE_TABLE_COURSE_POINT = "DROP TABLE IF EXISTS ${TABLE_NAME_COURSE_POINT}"

class DbCoursePointHandler(var context: Context): SQLiteOpenHelper(context,
    DATABASE_NAME, null,
    DATABASE_VERSION
) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_TABLE_COURSE_POINT)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(SQL_DELETE_TABLE_COURSE_POINT)
        onCreate(db)
    }

    fun deleteTable() {
        val db = writableDatabase
        db.execSQL("DROP TABLE IF EXISTS ${TABLE_NAME_COURSE_POINT}")
        db.close()


    }

    fun clearTableCoursePoint() {
        val db = writableDatabase
        db.execSQL("DELETE FROM ${TABLE_NAME_COURSE_POINT}")
        db.close()


    }

    fun deleteRow(id: Int){
        val db = writableDatabase
        db.execSQL("DELETE FROM ${TABLE_NAME_COURSE_POINT} WHERE courseID = ${id} ")
        db.close()

    }
    fun deleteCoursePoint(id: Int){
        val db = writableDatabase
        db.execSQL("DELETE FROM ${TABLE_NAME_COURSE_POINT} WHERE id = ${id} ")
        db.close()

    }

    fun insertCoursePoint(coursePoint: CoursePoint) {

        val db = writableDatabase
        db.execSQL(CREATE_TABLE_COURSE_POINT)

        var cv = ContentValues()

        cv.put(COL_COURSE_POINT_courseID, coursePoint.courseID)
        cv.put(COL_COURSE_POINT_DOSE, coursePoint.dose)
        cv.put(COL_COURSE_POINT_DAY, coursePoint.day)
        cv.put(COL_COURSE_POINT_TIME, coursePoint.time)
        cv.put(COL_COURSE_POINT_DATETIME_STATUS, coursePoint.datetimeStatus)
        cv.put(COL_COURSE_POINT_STATUS, coursePoint.statusPointID)


        var result = db.insert(TABLE_NAME_COURSE_POINT, null, cv)
        db.close()

        /* if (result == -1.toLong())
             Toast.makeText(context, "FAILED coursePoint", Toast.LENGTH_SHORT).show()
         else
             Toast.makeText(context, "SUCCESS coursePoint", Toast.LENGTH_SHORT).show()*/
    }

    fun readCourseHPoint(): MutableList<CoursePoint> {
        var list: MutableList<CoursePoint> = ArrayList()
        val db = this.readableDatabase

        val query = "SELECT * FROM " + TABLE_NAME_COURSE_POINT
        try {
            val result = db.rawQuery(query, null)

            if (result.moveToFirst()) {
                do {
                    var coursePoint = CoursePoint()
                    coursePoint.courseID = result.getString(result.getColumnIndex(COL_COURSE_POINT_courseID)).toInt()
                    coursePoint.id =
                        result.getString(result.getColumnIndex(COL_COURSE_POINT_ID)).toInt()
                    coursePoint.dose = result.getString(result.getColumnIndex(COL_COURSE_POINT_DOSE))
                    coursePoint.day = result.getString(result.getColumnIndex(COL_COURSE_POINT_DAY))
                    coursePoint.time =
                        result.getString(result.getColumnIndex(COL_COURSE_POINT_TIME))
                    coursePoint.datetimeStatus =result.getString(result.getColumnIndex(COL_COURSE_POINT_DATETIME_STATUS))
                    coursePoint.statusPointID = result.getString(result.getColumnIndex(COL_COURSE_POINT_STATUS)).toInt()
                    list.add(coursePoint)
                } while (result.moveToNext())
            }
            result.close()
            db.close()

        } catch (e: Exception) {
            Toast.makeText(context, "Таблицы не существует", Toast.LENGTH_SHORT).show()
        }
        return list
    }
/*
    fun getDoses(date: Date, id: Int ): MutableList<CoursePoint> {
        var list: MutableList<CoursePoint> = ArrayList()
        val db = this.readableDatabase

        val query = "SELECT $COL_COURSE_POINT_DOSE FROM " + TABLE_NAME_COURSE_POINT + "" +
                " WHERE $COL_COURSE_POINT_ID = ${id} AND $COL_COURSE_POINT"
        try {
            val result = db.rawQuery(query, null)

            if (result.moveToFirst()) {
                do {
                    var coursePoint = CoursePoint()
                    coursePoint.courseID = result.getString(result.getColumnIndex(COL_COURSE_POINT_courseID)).toInt()
                    coursePoint.id =
                        result.getString(result.getColumnIndex(COL_COURSE_POINT_ID)).toInt()
                    coursePoint.dose = result.getString(result.getColumnIndex(COL_COURSE_POINT_DOSE))
                    coursePoint.day = result.getString(result.getColumnIndex(COL_COURSE_POINT_DAY))
                    coursePoint.time =
                        result.getString(result.getColumnIndex(COL_COURSE_POINT_TIME))
                    coursePoint.statusPointID =
                        result.getString(result.getColumnIndex(COL_COURSE_POINT_STATUS)).toInt()
                    list.add(coursePoint)
                } while (result.moveToNext())
            }
            result.close()
            db.close()

        } catch (e: Exception) {
            Toast.makeText(context, "Таблицы не существует", Toast.LENGTH_SHORT).show()
        }
        return list
    }*/

    fun updateStatusPoint(statusPoint: Int){
        val db = writableDatabase
        try{
            db.execSQL("UPDATE ${TABLE_NAME_STATUS_POINT} SET " +
                    "${COL_COURSE_POINT_STATUS} = ${statusPoint} " +
                    "WHERE ${COL_COURSE_POINT_ID} = ${PointCourse.pointID}")
        }
        catch (e: Exception) {
            Toast.makeText(context, "Проблемки с обновлением статуса", Toast.LENGTH_SHORT).show()
        }
        db.close()
    }



    fun getOneCourse(): MutableList<CoursePoint> {
        var list: MutableList<CoursePoint> = ArrayList()
        val db = this.readableDatabase
        val query = "SELECT * FROM " + TABLE_NAME_COURSE_POINT + " " +
               "WHERE ${COL_COURSE_POINT_courseID} = ${DataRecyclerCourse.courseID} "
     //   val query = "SELECT * FROM " + TABLE_NAME_COURSE_POINT
        try{
            val result = db.rawQuery(query, null)

            if (result.moveToFirst()) {
                do {
                    var coursePoint = CoursePoint()
                    coursePoint.courseID = result.getString(result.getColumnIndex(COL_COURSE_POINT_courseID)).toInt()
                    coursePoint.id =
                        result.getString(result.getColumnIndex(COL_COURSE_POINT_ID)).toInt()
                    coursePoint.dose = result.getString(result.getColumnIndex(COL_COURSE_POINT_DOSE))
                    coursePoint.day = result.getString(result.getColumnIndex(COL_COURSE_POINT_DAY))
                    coursePoint.time =
                        result.getString(result.getColumnIndex(COL_COURSE_POINT_TIME))
                    coursePoint.datetimeStatus = result.getString(result.getColumnIndex(COL_COURSE_POINT_DATETIME_STATUS))
                    coursePoint.statusPointID =
                        result.getString(result.getColumnIndex(COL_COURSE_POINT_STATUS)).toInt()

                    list.add(coursePoint)
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

   fun setCoursePointDone(statusIdDone: Int, idPoint: Int, date: String){
       val db = writableDatabase
      db.execSQL("UPDATE ${TABLE_NAME_COURSE_POINT} " +
              "SET statusPointID = ${statusIdDone}, " +
              " $COL_COURSE_POINT_DATETIME_STATUS = '$date' " +
              " WHERE id = ${idPoint}")

       db.close()
   }
    fun getCourseForUser(): MutableList<CoursePoint> {
        var list: MutableList<CoursePoint> = ArrayList()
        val db = this.readableDatabase
      /*  val query = " ${TABLE_NAME_COURSE_POINT}.${COL_COURSE_POINT_DAY}, " +
                "${TABLE_NAME_COURSE_POINT}.${COL_COURSE_POINT_TIME}, ${TABLE_NAME_COURSE_POINT}.${COL_COURSE_POINT_courseID}, ${TABLE_NAME_COURSE_POINT}.${COL_COURSE_POINT_ID}" +
                " FROM ${TABLE_NAME_COURSE_POINT}  " +
                "WHERE $COL_COURSE_POINT_courseID = ${User.courseId}}"*/

      val query = "SELECT ${COL_COURSE_POINT_DAY}, " +
                "${COL_COURSE_POINT_TIME}, ${COL_COURSE_POINT_courseID}, ${COL_COURSE_POINT_ID} " +
                " FROM ${TABLE_NAME_COURSE_POINT}  " +
                "WHERE ( ${COL_COURSE_POINT_STATUS} = ${diplom.dev.aidhealth.StatusPoint.stWaitID} OR " +
                " ${COL_COURSE_POINT_STATUS} = ${diplom.dev.aidhealth.StatusPoint.stPostponedID}) " +
                "AND $COL_COURSE_POINT_courseID = ${User.courseId} "

       /* val query = "SELECT ${TABLE_NAME_STATUS_POINT}.${COL_STATUS_COURSE_TITLE}, ${TABLE_NAME_COURSE_POINT}.${COL_COURSE_POINT_DAY}, " +
                "${TABLE_NAME_COURSE_POINT}.${COL_COURSE_POINT_TIME}, ${TABLE_NAME_COURSE_POINT}.${COL_COURSE_POINT_courseID}, ${TABLE_NAME_COURSE_POINT}.${COL_COURSE_POINT_ID}" +
                " FROM ${TABLE_NAME_COURSE_POINT} JOIN ${TABLE_NAME_STATUS_POINT} " +
                " ON ${TABLE_NAME_STATUS_POINT}.${COL_ID_STATUS_POINT} = ${TABLE_NAME_COURSE_POINT}.${COL_COURSE_POINT_STATUS} " +
                "WHERE  ${TABLE_NAME_STATUS_POINT}.${COL_ID_STATUS_POINT} = ${diplom.dev.aidhealth.StatusPoint.stWaitID} AND " +
                "${TABLE_NAME_STATUS_POINT}.${COL_ID_STATUS_POINT} = ${diplom.dev.aidhealth.StatusPoint.stPostponedID} "*/
        //   val query = "SELECT * FROM " + TABLE_NAME_COURSE_POINT

            val result = db.rawQuery(query, null)

            if (result.moveToFirst()) {
                do {
                    var coursePoint = CoursePoint()

                   // courseWithStatus.statusTitle = result.getString(result.getColumnIndex(COL_STATUS_COURSE_TITLE))
                    coursePoint.day = result.getString(result.getColumnIndex(COL_COURSE_POINT_DAY))
                    coursePoint.time = result.getString(result.getColumnIndex(COL_COURSE_POINT_TIME))
                    coursePoint.courseID = result.getString(result.getColumnIndex(COL_COURSE_POINT_courseID)).toInt()
                    coursePoint.id = result.getString(result.getColumnIndex(COL_COURSE_POINT_ID)).toInt()

                    list.add(coursePoint)
                } while (result.moveToNext())
            }
            result.close()
            db.close()

        return list

    }

   fun getPointsID(){

/*
       val query = "SELECT ${TABLE_NAME_STATUS_POINT}.${COL_STATUS_COURSE_TITLE}, ${TABLE_NAME_COURSE_POINT}.${COL_COURSE_POINT_DAY}, " +
               "${TABLE_NAME_COURSE_POINT}.${COL_COURSE_POINT_TIME}, ${TABLE_NAME_COURSE_POINT}.${COL_COURSE_POINT_courseID}, ${TABLE_NAME_COURSE_POINT}.${COL_COURSE_POINT_ID}" +
               " FROM ${TABLE_NAME_COURSE_POINT} JOIN ${TABLE_NAME_STATUS_POINT} " +
               " ON ${TABLE_NAME_STATUS_POINT}.${COL_ID_STATUS_POINT} = ${TABLE_NAME_COURSE_POINT}.${COL_COURSE_POINT_STATUS} " +
               "WHERE "*/

   }
}
/*
    fun dropDoctorTable(){
        val db = this.writableDatabase
        db.execSQL("DROP TABLE " + TABLE_NAME)
        DATABASE_VERSION = DATABASE_VERSION+1
        db.close()
    }*/