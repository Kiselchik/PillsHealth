package diplom.dev.aidhealth.activity

import android.content.Intent
import android.graphics.Point
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import diplom.dev.aidhealth.DataRecyclerCourse
import diplom.dev.aidhealth.PointCourse
import diplom.dev.aidhealth.R
import diplom.dev.aidhealth.db.handler.*
import diplom.dev.aidhealth.recycler.CourseRecyclerAdapter
import diplom.dev.aidhealth.recycler.PointsRecyclerAdapter

class PointCourseActivity : AppCompatActivity() {
    private lateinit var textView: TextView
    private lateinit var addNotification:ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_point_course)
        DataRecyclerCourse.valueID = 0
        PointCourse.dataRecyclerId.clear()
        initialise()
        val recyclerView: RecyclerView = findViewById(R.id.recyclerPointsForCourse)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = PointsRecyclerAdapter(fillList())
        setListener()
    }

    fun initialise(){
        textView = findViewById(R.id.testTextView)
        addNotification = findViewById(R.id.addNotification)

    }

    fun setListener(){
        addNotification.setOnClickListener(){
            val intent = Intent(this@PointCourseActivity, TuningCourseActivity::class.java)
            startActivity(intent)
        }
    }


    fun fillList(): ArrayList<String> {
        var db = DbCoursePointHandler(context = this)
        var data = db.getOneCourse()
      //  Toast.makeText(this, "${data.size}")
        var datarecycler = arrayListOf<String>()
        var dataRecyclerId = arrayListOf<Int>()
var titleMed = ""
       // textView.text = "${DataRecyclerCourse.courseID}"

        var dbCourse = DbCourseHandler(context = this)
        var pill = dbCourse.getOneCourse().get(0).medicament
        var valuePillProcedure = dbCourse.getOneCourse().get(0).valuePillProcedure
        DataRecyclerCourse.valueID = valuePillProcedure
        if(pill.equals("true")){
            var dbPill = DbPillsHandler(context = this)
            titleMed = dbPill.getOnePill().get(0).title
        }else{
            var dbProcedure = DbProcedureHandler(context = this)
            titleMed = dbProcedure.getOneProcedure().get(0).title
        }


            for (i in 0..data.size - 1) {
                if(data.get(i).dose.equals("")){
                    Toast.makeText(this, "strange", Toast.LENGTH_SHORT).show()
                }
                //TODO: попросить айди и вывести статус, расшифровать статус
                    var dbStatusPoint = DbStatusPointHandler(context = this)
                var statusTitle = dbStatusPoint.getStatusTitle(data.get(i).statusPointID)

                datarecycler.add("${data.get(i).day}  ${data.get(i).time} \n доза  ${data.get(i).dose} \n ${titleMed} \n статус ${statusTitle.get(0).title}")
              //  datarecycler.add(db.readCourseHPoint().get(i).dose + " "+ db.readCourseHPoint().get(i).statusPointID)
                 PointCourse.dataRecyclerId.add(data.get(i).id)

            }
          //  DataRecyclerCourse.dataRecycler = dataRecyclerId
        return datarecycler
    }
}
