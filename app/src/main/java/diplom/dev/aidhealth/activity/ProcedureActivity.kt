package diplom.dev.aidhealth.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import diplom.dev.aidhealth.DataRecyclerCourse
import diplom.dev.aidhealth.Item
import diplom.dev.aidhealth.R
import diplom.dev.aidhealth.db.handler.DbProcedureHandler
import diplom.dev.aidhealth.recycler.ProcedureRecyclerAdapter

class ProcedureActivity : AppCompatActivity() {
    private lateinit var toAddProcedureActivityBtn: ImageButton
    private lateinit var textViewPills: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_procedure)
        Item.dataRecyclerItem.clear()

        val recyclerView: RecyclerView = findViewById(R.id.recyclerProcedure)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = ProcedureRecyclerAdapter(fillList())
        initialise()
        setListener()
    }

    fun fillList(): ArrayList<String> {
        var db = DbProcedureHandler(context = this)
        var data = db.readProcedure()
        var datarecycler = arrayListOf<String>()
        var dataRecyclerId = arrayListOf<Int>()

        //textDoc.text =""
        for (i in 0..data.size-1) {
            datarecycler.add(data.get(i).title)
                Item.dataRecyclerItem.add(data.get(i).id)
        }
        DataRecyclerCourse.dataRecycler = dataRecyclerId
        return datarecycler

    }

    fun initialise(){
        toAddProcedureActivityBtn = findViewById(R.id.toAddProcedureActivityBtn)
        textViewPills = findViewById(R.id.textViewDiagnosis)
    }
    private fun setListener(){
        toAddProcedureActivityBtn.setOnClickListener(){
            val intent = Intent(this@ProcedureActivity, AddProcedureActivity::class.java)
            startActivity(intent)
        }
        textViewPills.setOnClickListener(){
            val intent = Intent(this@ProcedureActivity, PillsActivity::class.java)
            startActivity(intent)
        }
    }
}