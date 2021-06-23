package diplom.dev.aidhealth.db.handler

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import diplom.dev.aidhealth.DataRecyclerCourse
import diplom.dev.aidhealth.User
import diplom.dev.aidhealth.db.model.Pill


val TABLE_NAME_PILLS = "Pill"
val COL_ID_PILL = "id"
val COL_PILL_TITLE = "title"
val COL_PILL_NUM = "num"
//val COL_PILL_PACK = "pack"
//val COL_PILL_UNITY = "unity"
val COL_PILL_MEASUREMENT="measurement"
val COL_PILL_EMAIL = "email"
val COL_PILL_DATETIME = "datetime"

val CREATE_TABLE_PILL = "CREATE TABLE IF NOT EXISTS  $TABLE_NAME_PILLS ("+
        "$COL_ID_PILL INTEGER PRIMARY KEY AUTOINCREMENT, $COL_PILL_TITLE TEXT, "+
        " $COL_PILL_NUM REAL,"+
        "$COL_PILL_MEASUREMENT TEXT, $COL_PILL_EMAIL TEXT," +
        " $COL_PILL_DATETIME TEXT," +
        " FOREIGN KEY(email) REFERENCES Users(email) )"

val SQL_DELETE_TABLE_PILL = "DROP TABLE IF EXISTS $TABLE_NAME_PILLS"

class DbPillsHandler(var context: Context): SQLiteOpenHelper(context,
    DATABASE_NAME, null,
    DATABASE_VERSION
) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_TABLE_PILL)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(SQL_DELETE_TABLE_PILL)
        onCreate(db)
    }

    fun insertPill(pill: Pill) {

        val db = writableDatabase
        db.execSQL(CREATE_TABLE_PILL)
        var cv = ContentValues()

        cv.put(COL_PILL_TITLE, pill.title)
        cv.put(COL_PILL_NUM, pill.num)
      //  cv.put(COL_PILL_PACK, pill.pack)
      //  cv.put(COL_PILL_UNITY, pill.unity)
        cv.put(COL_PILL_MEASUREMENT, pill.measurement)
        cv.put(COL_PILL_EMAIL,pill.email)
        cv.put(COL_PILL_DATETIME, pill.datetime)
        var result = db.insert(TABLE_NAME_PILLS, null, cv)
       // if (result == -1.toLong())
          //  Toast.makeText(context, "FAILED", Toast.LENGTH_SHORT).show()
       // else
       //     Toast.makeText(context, "SUCCESS", Toast.LENGTH_SHORT).show()
    }

    fun readPill(): MutableList<Pill> {
        var list: MutableList<Pill> = ArrayList()
        val db = this.readableDatabase

        val query = "SELECT * FROM " + TABLE_NAME_PILLS +"" +
                " WHERE email = '${User.email}' "
        try{
            val result = db.rawQuery(query, null)

            if (result.moveToFirst()) {
                do {
                    var pill = Pill()
                    pill.id = result.getString(result.getColumnIndex(COL_ID_PILL)).toInt()
                    pill.title = result.getString(result.getColumnIndex(COL_PILL_TITLE))
                    pill.num = result.getString(result.getColumnIndex(COL_PILL_NUM)).toDouble()
                    //pill.pack = result.getString(result.getColumnIndex(COL_PILL_PACK)).toInt()
                    // pill.unity = result.getString(result.getColumnIndex(COL_PILL_UNITY)).toFloat()
                    pill.measurement = result.getString(result.getColumnIndex(COL_PILL_MEASUREMENT))
                    pill.email = result.getString(result.getColumnIndex(COL_PILL_EMAIL))
                    list.add(pill)
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

    fun getOnePill(): MutableList<Pill> {
            var list: MutableList<Pill> = ArrayList()
            val db = this.readableDatabase

            val query = "SELECT * FROM " + TABLE_NAME_PILLS + " " +
                    "WHERE ${COL_ID_PILL} = ${DataRecyclerCourse.valueID}"
            try{
                val result = db.rawQuery(query, null)

                if (result.moveToFirst()) {
                    do {
                        var pill = Pill()
                        pill.id = result.getString(result.getColumnIndex(COL_ID_PILL)).toInt()
                        pill.title = result.getString(result.getColumnIndex(COL_PILL_TITLE))
                        pill.num = result.getString(result.getColumnIndex(COL_PILL_NUM)).toDouble()
                      //  pill.pack = result.getString(result.getColumnIndex(COL_PILL_PACK)).toInt()
                       // pill.unity = result.getString(result.getColumnIndex(COL_PILL_UNITY)).toFloat()
                        pill.measurement = result.getString(result.getColumnIndex(COL_PILL_MEASUREMENT))
                        pill.email = result.getString(result.getColumnIndex(COL_PILL_EMAIL))
                        pill.datetime = result.getString(result.getColumnIndex(COL_PILL_DATETIME))
                        list.add(pill)
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


    fun clearTablePill(){
        val db = writableDatabase
        db.execSQL("DELETE FROM ${TABLE_NAME_PILLS}")

    }
    fun deleteTable(){
        val db = writableDatabase
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME_PILLS")
        db.close()

    }

    fun deletePill(id: Int){
        val db = writableDatabase
        db.execSQL("DELETE FROM ${TABLE_NAME_PILLS} WHERE id = ${id} ")
        db.close()
    }

    fun updateNum(num: Double, date: String){
        val db = writableDatabase
        db.execSQL("UPDATE $TABLE_NAME_PILLS " +
                " SET ${COL_PILL_NUM} = $num , $COL_PILL_DATETIME = '$date' " +
                " WHERE $COL_ID_PILL = ${DataRecyclerCourse.valueID}")
        db.close()
    }



}
