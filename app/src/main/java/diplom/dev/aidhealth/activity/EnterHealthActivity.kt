package diplom.dev.aidhealth.activity

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import diplom.dev.aidhealth.R
import diplom.dev.aidhealth.db.handler.DbHandler
import diplom.dev.aidhealth.db.handler.DbHealthHandler
import diplom.dev.aidhealth.db.model.Health
import java.text.SimpleDateFormat
import java.util.*

class EnterHealthActivity : AppCompatActivity() {
    private lateinit var health1: RadioButton
    private lateinit var health2: RadioButton
    private lateinit var health3: RadioButton
    private lateinit var health4: RadioButton
    private lateinit var health5: RadioButton
    private lateinit var health6: RadioButton
    private lateinit var health7: RadioButton
    private lateinit var health8: RadioButton
    private lateinit var health9: RadioButton
    private lateinit var radioButton: RadioButton
    private lateinit var radioGroup: RadioGroup
    private lateinit var saveHealth: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enter_health)

        initialise()
        setListener()
    }

    private fun initialise(){
    /*    health1 = findViewById(R.id.health1)
        health2 = findViewById(R.id.health2)
        health3 = findViewById(R.id.health3)
        health4 = findViewById(R.id.health4)
        health5 = findViewById(R.id.health5)
        health6 = findViewById(R.id.health6)
        health7 = findViewById(R.id.health7)
        health8 = findViewById(R.id.health8)
        health9 = findViewById(R.id.health9)*/
        radioGroup = findViewById(R.id.radio_group)
        saveHealth = findViewById(R.id.saveHealth)
    }

    private fun setListener(){
        saveHealth.setOnClickListener(){
            var selected = radioGroup!!.checkedRadioButtonId
            radioButton = findViewById(selected)
            var text = radioButton.text.toString()

            var dbHealth = DbHealthHandler(context = this)
            val shPref = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
            val savedEmail = shPref.getString("EMAIL_KEY", "").toString()
            val sdf = SimpleDateFormat("dd/M/yyyy")
            val currentDate = sdf.format(Date())
            var health = Health(savedEmail, text,currentDate, 0 )
            dbHealth.insertHealth(health)
        }
    }
}