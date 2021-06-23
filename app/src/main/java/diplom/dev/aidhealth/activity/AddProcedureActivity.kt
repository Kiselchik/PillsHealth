package diplom.dev.aidhealth.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import diplom.dev.aidhealth.R
import diplom.dev.aidhealth.db.handler.DbProcedureHandler
import diplom.dev.aidhealth.db.model.Procedure

class AddProcedureActivity : AppCompatActivity() {
    private lateinit var titleNewProcedureEdTxt: EditText
    private lateinit var descrProcedureEdTxt: EditText
    private lateinit var saveNewProcedureBtn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_procedure)
        initialise()
        setListener()
    }

    fun initialise(){
        titleNewProcedureEdTxt = findViewById(R.id.titleNewProcedureEdTxt)
        descrProcedureEdTxt = findViewById(R.id.descrProcedureEdTxt)
        saveNewProcedureBtn = findViewById(R.id.saveNewProcedureBtn)
    }

    private fun setListener(){
        saveNewProcedureBtn.setOnClickListener(){
            val shPref = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
            val savedEmail = shPref.getString("EMAIL_KEY", "").toString()
            var db = DbProcedureHandler(context = this)

            val procedure = Procedure(savedEmail,titleNewProcedureEdTxt.text.toString(), descrProcedureEdTxt.text.toString() )
            db.insertProcedure(procedure)
        }
    }
}