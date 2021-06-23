package diplom.dev.aidhealth.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import diplom.dev.aidhealth.Item
import diplom.dev.aidhealth.R
import diplom.dev.aidhealth.db.handler.DbPillsHandler
import diplom.dev.aidhealth.db.MyDbManager
import diplom.dev.aidhealth.recycler.CustomRecyclerAdapter

class PillsActivity : AppCompatActivity() {
    private lateinit var addButton: ImageButton
    private lateinit var textViewPills: TextView
    private lateinit var textViewProcedures: TextView
    val myDbManager = MyDbManager(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pills)
        Item.dataRecyclerItem.clear()
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = CustomRecyclerAdapter(fillList())
        initializeView()
        setListener()
    }

    fun fillList(): ArrayList<String> {
        var db = DbPillsHandler(context = this)
        var data = db.readPill()
        var datarecycler = arrayListOf<String>()

        //textDoc.text =""
        for (i in 0..data.size-1) {
          /*  datarecycler.add(data.get(i).id.toString() + " " + data.get(i).title + data.get(i).pack +
                    data.get(i).unity + data.get(i).measurement
            )*/

         /*   datarecycler.add(data.get(i).id.toString() + " " + data.get(i).title + data.get(i).pack +
                    data.get(i).unity + data.get(i).measurement)*/
            datarecycler.add(data.get(i).title)
            Item.dataRecyclerItem.add(data.get(i).id)
        }
        return datarecycler
    }


    private fun initializeView() {
        addButton = findViewById(R.id.addButton)
        textViewPills = findViewById(R.id.textViewDiagnosis)
        textViewProcedures = findViewById(R.id.textViewSymptoms)

    }

    private fun setListener() {
        addButton.setOnClickListener() {
            val intent = Intent(this@PillsActivity, AddPillsActivity::class.java)
            startActivity(intent)
        }
        textViewProcedures.setOnClickListener(){
            val intent = Intent(this@PillsActivity, ProcedureActivity::class.java)
            startActivity(intent)
        }




    }
}