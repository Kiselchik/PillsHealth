package diplom.dev.aidhealth.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.anychart.AnyChartView
import com.jjoe64.graphview.GraphView
import diplom.dev.aidhealth.R
import diplom.dev.aidhealth.dynamic.CoursePointDynamic

class DynamicMainActivity  : AppCompatActivity() {
    private lateinit var toDynamicHealthBtn: Button
    private lateinit var toDynamicSymptomBtn: Button
    private lateinit var toDynamicCourseBtn: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dynamic_main)
        initialise()
        setListener()
    }

    private fun initialise(){
        toDynamicHealthBtn = findViewById(R.id.toDynamicHealthBtn)
        toDynamicSymptomBtn = findViewById(R.id.toDynamicSymptomBtn)
        toDynamicCourseBtn = findViewById(R.id.toDynamicCourseBtn)
    }

    fun setListener(){
        toDynamicHealthBtn.setOnClickListener(){
            val intent = Intent(this@DynamicMainActivity, DynamicHealthActivity::class.java)
            startActivity(intent)
        }
        toDynamicSymptomBtn.setOnClickListener(){
            val intent = Intent(this@DynamicMainActivity, DynamicSymptomActivity::class.java)
            intent.putExtra("checkFirst", false)
            startActivity(intent)
        }
        toDynamicCourseBtn.setOnClickListener(){
            val intent = Intent(this@DynamicMainActivity, DynamicActivity::class.java)
            startActivity(intent)
        }
    }
}