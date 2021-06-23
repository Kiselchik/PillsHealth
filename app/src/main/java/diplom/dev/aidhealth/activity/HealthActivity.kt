package diplom.dev.aidhealth.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import diplom.dev.aidhealth.DataRecyclerCourse
import diplom.dev.aidhealth.R
import diplom.dev.aidhealth.db.handler.DbHealthHandler
import diplom.dev.aidhealth.recycler.HealthPointRecyclerAdapter

class HealthActivity : AppCompatActivity() {
    private lateinit var enterHealth: Button
    private lateinit var toDynamic: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_health)
        val recyclerView: RecyclerView = findViewById(R.id.allHealth)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = HealthPointRecyclerAdapter(fillList())
        initialise()
        setListener()
    }

    fun fillList(): ArrayList<String> {
        var db = DbHealthHandler(context = this)
        var data = db.readHealth()
        var datarecycler = arrayListOf<String>()
        if(data.size!=0) {

            var dataRecyclerId = arrayListOf<Int>()

            //textDoc.text =""
            for (i in 0..data.size - 1) {
                //  datarecycler.add(data.get(i).id.toString() +""+ data.get(i).title)
                datarecycler.add(data.get(i).rating + " " + data.get(i).datetime)
                dataRecyclerId.add(data.get(i).id)
            }
            DataRecyclerCourse.dataRecycler = dataRecyclerId
        }
        return datarecycler

    }

    private fun initialise(){
        enterHealth = findViewById(R.id.enterHealth)
        toDynamic = findViewById(R.id.toEnterSymptom)
    }

    private fun setListener(){
        enterHealth.setOnClickListener(){
            val intent = Intent(this@HealthActivity, EnterHealthActivity::class.java)
            startActivity(intent)
        }
        toDynamic.setOnClickListener(){
            val intent = Intent(this@HealthActivity, DynamicHealthActivity::class.java)
            startActivity(intent)
        }

    }
}