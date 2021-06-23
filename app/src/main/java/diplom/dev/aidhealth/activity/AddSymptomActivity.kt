package diplom.dev.aidhealth.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import diplom.dev.aidhealth.R
import diplom.dev.aidhealth.db.handler.DbSymptomHandler
import diplom.dev.aidhealth.db.handler.DbSystemSymptomHandler
import diplom.dev.aidhealth.db.model.Symptom
import diplom.dev.aidhealth.db.model.SystemSymptom
import kotlinx.android.synthetic.main.activity_add_diagnosis.*

class AddSymptomActivity : AppCompatActivity() {
    private lateinit var newSymptomEdTxt: EditText
  //  private lateinit var evaluationSymptomChBox: CheckBox
    private lateinit var addNewSymptomBtn: Button
    private lateinit var systemSymptomSpinner: Spinner
   // var chooseEvaluation: Int = 0
    var chooseSystem = false
    var chooseSymptom = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_symptom)
        initialise()
        spinnerSetData()
        setListener()
    }

    fun initialise() {
        newSymptomEdTxt = findViewById(R.id.newSymptomEdTxt)
        addNewSymptomBtn = findViewById(R.id.addNewSymptomBtn)
        systemSymptomSpinner = findViewById(R.id.systemSymptomSpinner)
    }

    private fun setListener() {

        addNewSymptomBtn.setOnClickListener(){
            val shPref = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
            val savedEmail = shPref.getString("EMAIL_KEY", "").toString()
            val nameSymptom = newSymptomEdTxt.text.toString()
            var db = DbSymptomHandler(context = this)
           // var units = 0
           // var ball = 0
            if(!chooseSystem){
                var symptom = Symptom(savedEmail, chooseSymptom, nameSymptom)
                db.insertSymptom(symptom)
            }else{
                var symptom = Symptom(savedEmail, chooseSymptom, nameSymptom )
                db.insertSymptom(symptom)
            }

        }
    }

    fun spinnerSetData(){

            var dbSystemSymptom = DbSystemSymptomHandler(context = this)
            var data = dbSystemSymptom.readSystemSymptom()
        if(data.size==0){
            var systemSymptom = SystemSymptom("Cвой симптом", "", "")
            dbSystemSymptom.insertSystemSymptom(systemSymptom)
            systemSymptom = SystemSymptom("Температура", "Ц", "##,#")
            dbSystemSymptom.insertSystemSymptom(systemSymptom)
             systemSymptom = SystemSymptom("Сахар", "ммоль/л", "####")
            dbSystemSymptom.insertSystemSymptom(systemSymptom)

        }

        data = dbSystemSymptom.readSystemSymptom()

            var dataSpinner = arrayListOf<String>()
            var dataSpinnerID = arrayListOf<Int>()
            var dataSpinnerMeasurement = arrayListOf<String>()

            for (i in 0..data.size - 1) {
                dataSpinner.add(data.get(i).title)
                dataSpinnerID.add(data.get(i).id)
                dataSpinnerMeasurement.add(data.get(i).measurement)
            }

            systemSymptomSpinner.adapter =
                ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dataSpinner)
        systemSymptomSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // TODO("Not yet implemented")
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {

                    if(dataSpinner.get(position).equals("Cвой симптом")){
                        chooseSymptom = ""
                        chooseSystem = false
                       // evaluationSymptomChBox.visibility = View.VISIBLE
                        //evaluationSymptomChBox.setEnabled(true)
                    }else{
                        chooseSymptom = dataSpinnerID.get(position).toString()

                        // evaluationSymptomChBox.visibility = View.INVISIBLE
                        //evaluationSymptomChBox.setEnabled(false)
                            chooseSystem = true
                        newSymptomEdTxt.setText( dataSpinner.get(position).toString())
                    }
                   // choosePillMeasurement = dataSpinnerMeasurement.get(position)
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


}
