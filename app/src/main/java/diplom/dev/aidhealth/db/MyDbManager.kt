package diplom.dev.aidhealth.db

import android.content.Context

class MyDbManager(val context: Context) {
   /* val myDbHelper = MyDbHelper(context) //открывает закрывает и тд
    val dbDoctorHelper = DbDoctorHelper(context)
    var db: SQLiteDatabase? = null          //зписывает и тд

    fun openDb(){
        db = myDbHelper.writableDatabase //сначала обязательно открываем
    }
    fun openDbDOctor(){
        db = dbDoctorHelper.writableDatabase //сначала обязательно открываем
    }

    fun insertToDb(title: String){

        val values = ContentValues().apply {
            put(MyDbNameClass.COLUMN_NAME_TITLE, title)
        }
        db?.insert(MyDbNameClass.TABLE_NAME, null, values)
    }

//, lastName: String, position: String, address: String
    fun insertDoctorToDb(firstName: String){

        val values = ContentValues().apply {
            put(DbDoctorClass.COLUMN_DOCTOR_FIRST_NAME_TITLE, firstName)
           // put(DbDoctorClass.COLUMN_DOCTOR_LAST_NAME_TITLE, lastName)
           // put(DbDoctorClass.COLUMN_POSITION_TITLE, position)
          //  put(DbDoctorClass.COLUMN_ADDRESS_TITLE, address)
        }
        db?.insert(DbDoctorClass.TABLE_NAME, null, values)


    }

    fun readDbData(): ArrayList<String>{
        val dataList = ArrayList<String>()
        //курсор после ипользования нужно закрывать, тк память
        val cursor = db?.query(MyDbNameClass.TABLE_NAME, null, null, null,
            null, null, null)
        with(cursor){
            while (this?.moveToNext()!!){
                var dataText = cursor?.getString(cursor.getColumnIndex(MyDbNameClass.COLUMN_NAME_TITLE))

                dataList.add(dataText.toString())
            }
        }
        cursor?.close()
        return dataList
    }

    fun readDbDoctorData(): ArrayList<String>{
        val dataListFirstName = ArrayList<String>()
        val dataListLastName = ArrayList<String>()
        val dataListPosition = ArrayList<String>()
        val dataListAddress = ArrayList<String>()
        //курсор после ипользования нужно закрывать, тк память
        val cursor = db?.query(DbDoctorClass.TABLE_NAME, null, null, null,
            null, null, null)
      //  cursor?.moveToFirst()
        with(cursor){
            while (this?.moveToNext()!!){
                //посмотри кка вытаскивать несколько значений из бд
                var firstName = cursor?.getString(cursor.getColumnIndex(DbDoctorClass.COLUMN_DOCTOR_FIRST_NAME_TITLE))
           /*     var lastName = cursor?.getString(cursor.getColumnIndex(DbDoctorClass.COLUMN_DOCTOR_LAST_NAME_TITLE))
                var position = cursor?.getString(cursor.getColumnIndex(DbDoctorClass.COLUMN_POSITION_TITLE))
                var address = cursor?.getString(cursor.getColumnIndex(DbDoctorClass.COLUMN_ADDRESS_TITLE))*/
                dataListFirstName.add(firstName.toString())
             //   dataListFirstName.add("${firstName.toString()} "+"${lastName.toString()} "+"${position.toString()} "+"${address.toString()}")
                dataListFirstName.add("${firstName.toString()}")

            }
        }
        cursor?.close()
        return dataListFirstName

    }

        fun closeDb(){
            myDbHelper.close()
        }
    fun closeDbDoctor(){
        dbDoctorHelper.close()
    }

        fun allPillsDelete(){

            val deletedRows = db?.delete(MyDbNameClass.TABLE_NAME, null, null)

        }
    fun allDocDelete(){

        val deletedRows = db?.delete(DbDoctorClass.TABLE_NAME, null, null)

    }

    fun dropDocеor(){

    }

*/
}