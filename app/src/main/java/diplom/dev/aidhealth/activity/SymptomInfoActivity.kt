package diplom.dev.aidhealth.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import diplom.dev.aidhealth.DataRecyclerSymptoms
import diplom.dev.aidhealth.R
import diplom.dev.aidhealth.Symptom
import diplom.dev.aidhealth.db.handler.DbSymptomHandler
import diplom.dev.aidhealth.db.handler.DbSymptomPointHandler
import diplom.dev.aidhealth.recycler.SymptomRecyclerAdapter

class SymptomInfoActivity : AppCompatActivity() {
    private lateinit var nameSymptomTxt: TextView
    private lateinit var toDynamic: Button
    private lateinit var toEnterSymptom: Button
  //  private lateinit var symptomPointRecycler: TextView
    val symptomID  =  Symptom.symptomPointId

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_symptom_info)
        //DataRecyclerSymptoms.dataRecycler.clear()
        val symptomPointRecycler: RecyclerView = findViewById(R.id.symptomPointRecycler)
        symptomPointRecycler.layoutManager = LinearLayoutManager(this)
        symptomPointRecycler.adapter = SymptomRecyclerAdapter(fillList())
        initialise()
        setText()
       setListener()
    }
    private fun initialise(){
        nameSymptomTxt = findViewById(R.id.nameSymptomTxt)
        toDynamic = findViewById(R.id.toDynamic2)
        toEnterSymptom = findViewById(R.id.toEnterSymptom)
       // symptomPointRecycler = findViewById(R.id.symptomPointRecycler)
    }

    fun setText(){
        var dbSymptom = DbSymptomHandler(context = this)
        var dataSymptom = dbSymptom.getOneSymptom(symptomID)
        nameSymptomTxt.text = dataSymptom.get(0).title

    }

    fun fillList(): ArrayList<String> {

        var dbSymptomPoint = DbSymptomPointHandler(context = this)
        var dataSymptomPoint = dbSymptomPoint.getOneSymptomPoint(symptomID)
        var datarecycler = arrayListOf<String>()


        //textDoc.text =""
        for (i in 0..dataSymptomPoint.size-1) {
            //    datarecycler.add(data.get(i).id.toString() + " " + data.get(i).title)
            //  if(data.get(i).title!=null){
            datarecycler.add( "Дата и время: "+dataSymptomPoint.get(i).datetime + " \n Значение: " + dataSymptomPoint.get(i).value)
            // } else{
            // datarecycler.add( data.get(i).systemSymptomId.toString())

            //  }
            DataRecyclerSymptoms.dataRecycler.add(dataSymptomPoint.get(i).systemPointId)

        }
        return datarecycler

    }

    private fun setListener(){
        toDynamic.setOnClickListener(){
            val intent = Intent(this@SymptomInfoActivity, DynamicSymptomActivity::class.java)
            startActivity(intent)
        }
        toEnterSymptom.setOnClickListener(){
            val intent = Intent(this@SymptomInfoActivity, EnterSymptomActivity::class.java)
            startActivity(intent)
        }
    }
}