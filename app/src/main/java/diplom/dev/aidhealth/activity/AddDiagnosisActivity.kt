package diplom.dev.aidhealth.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import diplom.dev.aidhealth.DataRecyclerSymptoms
import diplom.dev.aidhealth.R
import diplom.dev.aidhealth.db.handler.*
import diplom.dev.aidhealth.db.model.Diagnosis
import diplom.dev.aidhealth.db.model.DiagnosisSymptom
import diplom.dev.aidhealth.recycler.AddDiagnosisRecyclerAdapter
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AddDiagnosisActivity : AppCompatActivity() {
    private lateinit var nameNewDiagnosisEdTxt: EditText
    private lateinit var spinnerDiagnosisDoctor: Spinner
    private lateinit var symptomDiagnosisRecycler: RecyclerView
    private lateinit var saveNewDiagnosis: Button
    private lateinit var descrDiagnosisEdTxt: EditText
//    private lateinit var textView: TextView

    var chooseDoctor : Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_diagnosis)
        symptomDiagnosisRecycler = findViewById(R.id.symptomDiagnosisRecycler)
        symptomDiagnosisRecycler.layoutManager = LinearLayoutManager(this)
        symptomDiagnosisRecycler.adapter = AddDiagnosisRecyclerAdapter(fillList())
        initialise()
        spinnerDoctors()
        //spinnerSymptom()
        setListener()
    }

    fun fillList(): ArrayList<String> {
        var db = DbSymptomHandler(context = this)
        var data = db.readSymptom()
        var datarecycler = arrayListOf<String>()
        var dataSymptomID = arrayListOf<Int>()

        //textDoc.text =""
        for (i in 0..data.size-1) {
            datarecycler.add( data.get(i).id.toString() + " "+data.get(i).title)
            dataSymptomID.add(data.get(i).id)
        }
        DataRecyclerSymptoms.symptomId = dataSymptomID
        return datarecycler

    }

    fun initialise(){

        nameNewDiagnosisEdTxt = findViewById(R.id.nameNewDiagnosisEdTxt)
        spinnerDiagnosisDoctor = findViewById(R.id.spinnerDiagnosisDoctor)
        saveNewDiagnosis = findViewById(R.id.saveNewDiagnosis)
        descrDiagnosisEdTxt = findViewById(R.id.descrDiagnosisEdTxt)
     //   textView = findViewById(R.id.textViewNewDiagnosis)
      //  textView.text = DataRecyclerSymptoms.chooseSymptomId.size.toString()
  //      spinnerDiagnosisSymptom = findViewById(R.id.spinnerDiagnosisSymptom)
    }

    private fun setListener(){
        saveNewDiagnosis.setOnClickListener(){
            var dbDiagnosis = DbDiagnosisHandler(context = this)
            var dbDiagnosisSymptom = DbDiagnosisSymptomHandler(context = this)
            val shPref = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
            val savedEmail = shPref.getString("EMAIL_KEY", "").toString()
            val sdf = SimpleDateFormat("dd/M/yyyy")
            val currentDate = sdf.format(Date())
           // textView.text = DataRecyclerSymptoms.chooseSymptomId.size.toString()

            //сохраняем диагноз
          var diagnosis = Diagnosis(savedEmail, chooseDoctor, nameNewDiagnosisEdTxt.text.toString(),
                descrDiagnosisEdTxt.text.toString(), currentDate )
            dbDiagnosis.insertDiagnosis(diagnosis)
        //    textView.text = ""
            //получаем id записанного диагноза
          var diagnosisId =   dbDiagnosis.selectDiagnosisMaxID().get(0).id
          //  textView.text = diagnosisId.toString()
            //сохранние симптомов в диагноз

            for(i in 0..DataRecyclerSymptoms.chooseSymptomId.size-1){

                DataRecyclerSymptoms.ID.add(DataRecyclerSymptoms.symptomId.get(DataRecyclerSymptoms.chooseSymptomId.get(i)))
             //   textView.text = textView.text.toString() +"\n"+DataRecyclerSymptoms.ID.get(i).toString()
                var diagnosisSymptom = DiagnosisSymptom(DataRecyclerSymptoms.ID.get(i), diagnosisId)
                dbDiagnosisSymptom.insertDiagnosisSymptom(diagnosisSymptom)
            }
/*

            for(i in 0..DataRecyclerSymptoms.ID.size-1){
                var diagnosisSymptom = DiagnosisSymptom(DataRecyclerSymptoms.ID.get(i), diagnosisId)
                dbDiagnosisSymptom.insertDiagnosisSymptom(diagnosisSymptom)
            }*/

        }
    }
/*
    fun spinnerSymptom() {

        var db = DbSymptomHandler(context = this)
        var data = db.readSymptom()
        var dataSpinner = arrayListOf<String>()
        var dataSpinnerID = arrayListOf<Int>()
        for (i in 0..data.size-1) {
            dataSpinner.add(data.get(i).title)
            dataSpinnerID.add(data.get(i).id)

        }

        spinnerDiagnosisDoctor.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dataSpinner)
        spinnerDiagnosisDoctor.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                chooseDoctor = dataSpinnerID.get(position)

            }
        }
    }*/


    fun spinnerDoctors() {

        var db = DbHandler(context = this)
        var data = db.readDoctor()
        var dataSpinner = arrayListOf<String>()
        var dataSpinnerID = arrayListOf<Int>()
        for (i in 0..data.size-1) {
            dataSpinner.add(data.get(i).firstName +" "+data.get(i).lastName+" ("+data.get(i).position+")")
            dataSpinnerID.add(data.get(i).id)

        }

        spinnerDiagnosisDoctor.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dataSpinner)
        spinnerDiagnosisDoctor.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                //TODO("Not yet implemented")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                chooseDoctor = dataSpinnerID.get(position)

            }
        }
    }
}