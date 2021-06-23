package diplom.dev.aidhealth.activity

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import diplom.dev.aidhealth.R
import diplom.dev.aidhealth.db.*
import diplom.dev.aidhealth.db.handler.DbHandler
import diplom.dev.aidhealth.db.model.Doctor

class AddDoctorActivity : AppCompatActivity() {
    private lateinit var docNameEdText: EditText
    private lateinit var docLastNameEdText: EditText
    private lateinit var docAddresEdText: EditText
    private lateinit var docPositionEdText: EditText
  //  private lateinit var textDocTest: TextView
    private lateinit var okDocButton: Button
 //   private lateinit var readDoctorButton: Button
//    private lateinit var dropDoctorButton: Button
    val myDbManager = MyDbManager(this)
    var db: SQLiteDatabase? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_doctor)
        initialize()
        setListener()

    }

   private fun initialize(){
        docNameEdText = findViewById(R.id.DocNameEdText)
        docLastNameEdText = findViewById(R.id.DocLastNameEdText)
        docAddresEdText = findViewById(R.id.DocAddresEdText)
        docPositionEdText = findViewById(R.id.DocPositionEdText)
   //    textDocTest = findViewById(R.id.textDoctorTest)
       okDocButton = findViewById(R.id.okDoctor_button)
   //    readDoctorButton = findViewById(R.id.readDoctor_button)
   //    dropDoctorButton = findViewById(R.id.dropDoctor_button)

    }

    fun setListener() {
        var db = DbHandler(context = this)

        okDocButton.setOnClickListener() {
            val shPref = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
            val savedEmail = shPref.getString("EMAIL_KEY", "").toString()
        var doctor = Doctor( //обавить емейд
            docNameEdText.text.toString(), docLastNameEdText.text.toString(),
            docPositionEdText.text.toString(), docAddresEdText.text.toString(), savedEmail


        )
          //  var db = DbHandler(context = this)
            db.insertDoctor(doctor)


        }
/*
        readDoctorButton.setOnClickListener(){
            var data = db.readDoctor()
            textDocTest.text =""
            for (i in 0..data.size-1){
                textDocTest.append(data.get(i).id.toString() + " "+ data.get(i).firstName + data.get(i).lastName+
                data.get(i).position + data.get(i).address)
            }
        }

        dropDoctorButton.setOnClickListener(){
            db.dropDoctorTable()
        }*/
    }



   /* override fun onDestroy(){
        super.onDestroy()
        myDbManager.closeDbDoctor()
    }*/

}