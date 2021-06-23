package diplom.dev.aidhealth.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import diplom.dev.aidhealth.DataRecyclerCourse
import diplom.dev.aidhealth.Dates
import diplom.dev.aidhealth.Items
import diplom.dev.aidhealth.R
import diplom.dev.aidhealth.db.handler.DbCourseHandler
import diplom.dev.aidhealth.db.handler.DbHandler
import diplom.dev.aidhealth.fragment.DoseStablyFragment
import diplom.dev.aidhealth.recycler.CourseRecyclerAdapter
import diplom.dev.aidhealth.recycler.DoctorsRecyclerAdapter

class CourseActivity : AppCompatActivity() {

    private lateinit var addNewCourseButton: ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course)

        DataRecyclerCourse.courseID = 0
        Dates.dates.clear()
        Items.chooseItem.clear()

        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewCourse)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = CourseRecyclerAdapter(fillList())
        initialize()
        setListener()
    }

    fun fillList(): ArrayList<String> {
        var db = DbCourseHandler(context = this)
        var data = db.readCourse()
        var datarecycler = arrayListOf<String>()
        if(data.size!=0) {

            var dataRecyclerId = arrayListOf<Int>()

            //textDoc.text =""
            for (i in 0..data.size - 1) {
                //  datarecycler.add(data.get(i).id.toString() +""+ data.get(i).title)
                datarecycler.add(data.get(i).title + " " + data.get(i).id)
                dataRecyclerId.add(data.get(i).id)
            }
            DataRecyclerCourse.dataRecycler = dataRecyclerId
        }
            return datarecycler

    }


    fun initialize(){
        addNewCourseButton = findViewById(R.id.addCourseButton)
    }

    fun setListener(){
        addNewCourseButton.setOnClickListener(){
            val builder = android.app.AlertDialog.Builder(this@CourseActivity)
           // val measurement  = intent.getStringExtra("MEASUREMENT")

            builder.setTitle("Настройки")
            builder.setMessage("Выберете тип настройки")
            builder.setPositiveButton("Быстрая") { dialog, which ->
                val intent = Intent(this@CourseActivity, AddCourseActivity::class.java)
                startActivity(intent)


            }
            builder.setNegativeButton("Ручная") { dialog, which ->
                val intent = Intent(this@CourseActivity, UserSetCourseActivity::class.java)
                startActivity(intent)

            }
            builder.setNeutralButton("Отмена") { _,_ ->

            }
            val dialog: android.app.AlertDialog = builder.create()
            dialog.show()

/*
            val intent = Intent(this@CourseActivity, AddCourseActivity::class.java)
            startActivity(intent)*/

        }
    }
}