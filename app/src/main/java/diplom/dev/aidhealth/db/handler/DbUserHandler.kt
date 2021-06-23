package diplom.dev.aidhealth.db.handler

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import diplom.dev.aidhealth.db.model.User

val TABLE_NAME_USER = "Users"
val COL_EMAIL_USER = "email"
val COL_USER_PASSWORD = "password"
val COL_USER_FIRSTNAME = "firstName"
val COL_USER_LASTNAME = "lastName"

val CREATE_TABLE_USER = "CREATE TABLE IF NOT EXISTS $TABLE_NAME_USER ("+
        "$COL_EMAIL_USER TEXT PRIMARY KEY, $COL_USER_PASSWORD TEXT, "+
        "$COL_USER_FIRSTNAME TEXT, $COL_USER_LASTNAME TEXT)"

val SQL_DELETE_TABLE_USER = "DROP TABLE  $TABLE_NAME_USER"

class DbUserHandler(var context: Context): SQLiteOpenHelper(context,
    DATABASE_NAME, null,
    DATABASE_VERSION
) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_TABLE_USER)
        Toast.makeText(context, "Вызван", Toast.LENGTH_SHORT).show()


    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(SQL_DELETE_TABLE_USER)
        onCreate(db)
    }


    fun insertUser(user: User) {

        val db = writableDatabase
        var cv = ContentValues()

        cv.put(COL_EMAIL_USER, user.email)
        cv.put(COL_USER_PASSWORD, user.password)
        cv.put(COL_USER_FIRSTNAME, user.firstName)
        cv.put(COL_USER_LASTNAME, user.lastName)
        var result = db.insert(TABLE_NAME_USER, null, cv)
        if (result == -1.toLong())
            Toast.makeText(context, "FAILED", Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(context, "SUCCESS", Toast.LENGTH_SHORT).show()
    }

    fun readUser(regEmail: String): MutableList<User> {
        var list: MutableList<User> = ArrayList()
        val db = this.readableDatabase
        db.execSQL(CREATE_TABLE_USER)

        val query = "SELECT * FROM " + TABLE_NAME_USER + "WHERE email = $regEmail "
        try {

            val result = db.rawQuery(query, null)

            if (result.moveToFirst()) {
                do {
                    var user = User()
                    user.email = result.getString(result.getColumnIndex(COL_EMAIL_USER))
                    user.password = result.getString(result.getColumnIndex(COL_USER_PASSWORD))
                    user.firstName = result.getString(result.getColumnIndex(COL_USER_FIRSTNAME))
                    user.lastName = result.getString(result.getColumnIndex(COL_USER_LASTNAME))
                    list.add(user)
                } while (result.moveToNext())
            }
            result.close()
            db.close()

        } catch (e: Exception) {
            Toast.makeText(context, "Таблицы не существует", Toast.LENGTH_SHORT).show()
        }
        return list
    }


    fun checkUser(email: String, password: String): MutableList<User> {
        var list: MutableList<User> = ArrayList()
        val db = this.readableDatabase
        db.execSQL(CREATE_TABLE_USER)

        val query = "SELECT email, password, firstName, lastName" +
                " FROM " + TABLE_NAME_USER +
                " WHERE email='${email}' and password='${password}'"

            val result = db.rawQuery(query, null)

            if (result.moveToFirst()) {
                do {
                    var user = User()
                    user.email = result.getString(result.getColumnIndex(COL_EMAIL_USER))
                    user.password = result.getString(result.getColumnIndex(COL_USER_PASSWORD))
                    user.firstName = result.getString(result.getColumnIndex(COL_USER_FIRSTNAME))
                    user.lastName = result.getString(result.getColumnIndex(COL_USER_LASTNAME))
                    list.add(user)
                } while (result.moveToNext())
            }
            result.close()
            db.close()

        return list
    }

    fun dropUserTable() {
        val db = this.writableDatabase
        db.execSQL("DROP TABLE " + TABLE_NAME)
        DATABASE_VERSION = DATABASE_VERSION + 1
        db.close()


    }
}
