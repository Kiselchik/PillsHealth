package diplom.dev.aidhealth.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import diplom.dev.aidhealth.R
import diplom.dev.aidhealth.db.handler.*

class OptionsActivity: AppCompatActivity()  {

private lateinit var clearCourseBtn: Button
    private lateinit var clearPillBtn: Button
    private lateinit var clearDoctorBtn: Button
    private lateinit var clearDiagnosisBtn: Button
    private lateinit var deleteCoursePointBtn: Button
    private lateinit var deleteSymptomButton: Button
    private lateinit var deleteSystemSymptomBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_options)
        initialise()
        setListener()
    }

    private fun initialise(){
        clearCourseBtn = findViewById(R.id.clearCourseBtn)
         clearPillBtn = findViewById(R.id.clearPillBtn)
        clearDoctorBtn = findViewById(R.id.clearDoctorBtn)
        clearDiagnosisBtn = findViewById(R.id.clearDiagnosisBtn)
        deleteCoursePointBtn = findViewById(R.id.deleteCoursePointBtn)
        deleteSymptomButton = findViewById(R.id.deleteSymptomButton)
        deleteSystemSymptomBtn = findViewById(R.id.deleteSystemSymptomBtn)
    }
        private fun setListener() {
        clearCourseBtn.setOnClickListener() {
          var dbCourse = DbCourseHandler(context = this)
          var dbCourseHistory = DbCourseHistoryHandler(context = this)
          var dbCoursePoint = DbCoursePointHandler(context = this)
          dbCourse.clearTableCourse()
          dbCourseHistory.clearTableCourseHistory()
          dbCoursePoint.clearTableCoursePoint()
      }

      clearPillBtn.setOnClickListener() {
          var dbPill = DbPillsHandler(context = this)
          //dbPill.clearTablePill()
          dbPill.deleteTable()
      }
      clearDoctorBtn.setOnClickListener() {
          var dbDoctor = DbHandler(context = this)
          dbDoctor.clearTableDoctor()
      }


      clearDiagnosisBtn.setOnClickListener() {
          var dbDiagnosis = DbDiagnosisHandler(context = this)
          var dbDiagnosisSymptom = DbDiagnosisSymptomHandler(context = this)

          dbDiagnosis.clearTableDiagnosis()
          dbDiagnosisSymptom.clearTableDiagnosisSymptom()
      }
            deleteCoursePointBtn.setOnClickListener(){
                var dbCoursePoint = DbCoursePointHandler(context = this)
                dbCoursePoint.deleteTable()
            }
            deleteSymptomButton.setOnClickListener(){
                var dbSymptom = DbSymptomHandler(context = this)
                var dbSymptomPoint = DbSymptomPointHandler(context = this)
                dbSymptomPoint.dropTableSymptomPoint()
                dbSymptom.clearTableSymptom()
            }
            deleteSystemSymptomBtn.setOnClickListener({
                var dbSystemSymptom = DbSystemSymptomHandler(context = this)
                //dbSystemSymptom.clearTableSystemSymptom()
                var dbSymptomPoint = DbSymptomPointHandler(context = this)
                dbSymptomPoint.dropTableSymptomPoint()
                dbSystemSymptom.dropTableSystemSymptom()
            })


}

}