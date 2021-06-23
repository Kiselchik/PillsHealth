package diplom.dev.aidhealth.activity

import android.content.Context
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import br.com.sapereaude.maskedEditText.MaskedEditText
import diplom.dev.aidhealth.R
import diplom.dev.aidhealth.db.handler.DbPillsHandler
import diplom.dev.aidhealth.db.handler.DbSymptomHandler
import diplom.dev.aidhealth.db.handler.DbSymptomPointHandler
import diplom.dev.aidhealth.db.handler.DbSystemSymptomHandler
import diplom.dev.aidhealth.db.model.SymptomPointUser
import java.text.SimpleDateFormat
import java.util.*

class EnterSymptomActivity  : AppCompatActivity() {
        private lateinit var valueEdTxt: MaskedEditText
        private lateinit var helpTxt: TextView
        private lateinit var symptomSpinner: Spinner
        private lateinit var saveEnterSymptomBtn: Button
        var chooseSymptom = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enter_symptom)
        initialise()
        spinnerSymptom()
        setListener()
    }

    private fun initialise(){
        valueEdTxt = findViewById(R.id.valueEdTxt)
        symptomSpinner = findViewById(R.id.symptomSpinner)
        helpTxt = findViewById(R.id.helpTxt)
        valueEdTxt.inputType = InputType.TYPE_NUMBER_FLAG_DECIMAL
        saveEnterSymptomBtn = findViewById(R.id.saveEnterSymptomBtn)
//        valueEdTxt.mask = "#"

    }
    fun spinnerSymptom() {

        var db = DbSymptomHandler(context = this)
        var data = db.readSymptom()
        var dataSpinner = arrayListOf<String?>()
        var dataSpinnerID = arrayListOf<Int>()
        var dataSpinnerSystem = arrayListOf<String>()
     //   var dataSpinnerMask = arrayListOf<String>()
        var dataSpinnerMeasurement = arrayListOf<String>()

        for (i in 0..data.size - 1) {
            dataSpinner.add(data.get(i).title)
            dataSpinnerID.add(data.get(i).id)
            dataSpinnerSystem.add(data.get(i).systemSymptomId)
          //  dataSpinnerMeasurement.add(data.get(i).measurement)
        }

        symptomSpinner.adapter =
            ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dataSpinner)
        symptomSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                // TODO("Not yet implemented")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                chooseSymptom = dataSpinnerID.get(position)
                if(dataSpinnerSystem.get(position).equals("")){
                    helpTxt.text = "от 0 до 5"
                   valueEdTxt.mask = "#"

                }else{
                    //получить по симптому его маску
                    var dbSystemSymptom = DbSystemSymptomHandler(context = this@EnterSymptomActivity)
                    var dataMask = dbSystemSymptom.getOneSystemSymptom(dataSpinnerSystem.get(position).toInt())
                    helpTxt.text = ""
                    valueEdTxt.mask = "${dataMask.get(0).mask}"
                }
                //choosePillMeasurement = dataSpinnerMeasurement.get(position)
                /*for(i in 0..data.size-1){
                    if(data[i].equals("${dataSpinner.get(position)}")){
                        choosePill=i
                    }
                }*/
                //   Toast.makeText(applicationContext, "${dataSpinner.get(position)}", Toast.LENGTH_SHORT).show()

                //  measurement = units.get(position)
            }
        }

    }
    private fun setListener(){
        saveEnterSymptomBtn.setOnClickListener(){
            if(valueEdTxt.text.toString().equals("")){
                Toast.makeText(this, "Заполните поле", Toast.LENGTH_SHORT).show()
            }else{
                val shPref = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
                val savedEmail = shPref.getString("EMAIL_KEY", "").toString()
                val sdf = SimpleDateFormat("dd/M/yyyy HH:mm:ss")
                val currentDate = sdf.format(Date())
                var dbSymptomPointUser = DbSymptomPointHandler(context = this)
                var symptomPoint = SymptomPointUser(chooseSymptom,savedEmail, valueEdTxt.text.toString(), currentDate)
                dbSymptomPointUser.insertSymptomPoint(symptomPoint)
            }
        }
    }



}