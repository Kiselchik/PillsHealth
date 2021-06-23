package diplom.dev.aidhealth.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import diplom.dev.aidhealth.DataRecyclerSymptoms
import diplom.dev.aidhealth.R
import diplom.dev.aidhealth.Symptom
import diplom.dev.aidhealth.db.handler.DbDiagnosisHandler
import diplom.dev.aidhealth.db.handler.DbSymptomHandler
import diplom.dev.aidhealth.db.model.Diagnosis
import diplom.dev.aidhealth.recycler.DoctorsRecyclerAdapter
import diplom.dev.aidhealth.recycler.SymptomRecyclerAdapter

class SymptomActivity : AppCompatActivity() {
    private lateinit var toAddSymptomActivity: ImageButton
    private lateinit var textViewDiagnosis: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_symptom)
        DataRecyclerSymptoms.dataRecycler.clear()
        Symptom.symptomPointId = 0
        val recyclerView: RecyclerView = findViewById(R.id.symptomRecycler)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = SymptomRecyclerAdapter(fillList())
        initialise()
        setListener()
    }


    fun fillList(): ArrayList<String> {
        var db = DbSymptomHandler(context = this)
        var data = db.readSymptom()
        var datarecycler = arrayListOf<String>()

        //textDoc.text =""
        for (i in 0..data.size-1) {
        //    datarecycler.add(data.get(i).id.toString() + " " + data.get(i).title)
          //  if(data.get(i).title!=null){
                datarecycler.add( data.get(i).title!!)
           // } else{
               // datarecycler.add( data.get(i).systemSymptomId.toString())

          //  }
            DataRecyclerSymptoms.dataRecycler.add(data.get(i).id)

        }
        return datarecycler

    }

    fun initialise(){
        toAddSymptomActivity = findViewById(R.id.toAddSymptomActivity)
        textViewDiagnosis = findViewById(R.id.textViewDiagnosis)
    }

    private fun setListener(){
        toAddSymptomActivity.setOnClickListener(){
            val intent = Intent(this@SymptomActivity, AddSymptomActivity::class.java)
            startActivity(intent)
        }
        textViewDiagnosis.setOnClickListener(){
            val intent = Intent(this@SymptomActivity, DiagnosisActivity::class.java)
            startActivity(intent)
        }
    }
}