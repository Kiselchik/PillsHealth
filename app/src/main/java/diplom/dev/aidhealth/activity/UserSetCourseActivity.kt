package diplom.dev.aidhealth.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import diplom.dev.aidhealth.CheckCourse
import diplom.dev.aidhealth.R
import diplom.dev.aidhealth.db.handler.*
import diplom.dev.aidhealth.db.model.Course
import diplom.dev.aidhealth.db.model.CourseHistory
import diplom.dev.aidhealth.db.model.StatusCourse
import java.text.SimpleDateFormat
import java.util.*

class UserSetCourseActivity: AppCompatActivity()  {
    private lateinit var pillsSpinner: Spinner
    private lateinit var switchPillProcedure: Switch

    // private lateinit var proceduresSpinner: Spinner
    private lateinit var doctorsSpinner: Spinner
    private lateinit var foodsSpinner: Spinner
    private lateinit var diagnosisSpinner: Spinner
    private lateinit var addCourseButton: Button
    private lateinit var courseNameEdTxt: EditText


    var chooseProcedure: Int = 0
    var choosePill: Int = 0
    var chooseDoctor: Int = 0
    var chooseFood: String = ""
    var chooseDiagnosis = 0
   private var pillstrue = "true"
    var valuePillProcedure = 0
    var emptyPills = false
    var emptyProcedures = false
    var emptyDoctors = false
    var emptyDiagnosis = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_course)
        initialise()
     //   checkData()
        spinnerDiagnosis()
        spinnerDoctors()
        spinnerFoods()
        setListener()
    }

    fun checkData(){
        var dbDoctor = DbHandler(context = this)
        if(dbDoctor.readDoctor().size!=0){
            var dbDiagnosis = DbDiagnosisHandler(context = this)
            if(dbDiagnosis.readDiagnosis().size!=0){

            }else{
                Toast.makeText(this, "Введите в ситему хотя бы один диагноз", Toast.LENGTH_SHORT).show()
                //TODO: Закрыть активити
            }
        }else{
            Toast.makeText(this, "Введите в ситему хотя бы одного врача", Toast.LENGTH_SHORT).show()
            //TODO: Закрыть активити

        }
    }

    private fun initialise(){
            pillsSpinner = findViewById(R.id.pillsSpinner)
            doctorsSpinner = findViewById(R.id.doctorsSpinner)
            foodsSpinner = findViewById(R.id.foodsSpinner)
            addCourseButton = findViewById(R.id.addCourseButton)
            courseNameEdTxt = findViewById(R.id.courseNameEdTxt)
            diagnosisSpinner = findViewById(R.id.diagnosisSpinner)
            switchPillProcedure = findViewById(R.id.switchPillProcedure2)
        spinnerPills()
    }

    private fun setListener(){
        switchPillProcedure.setOnCheckedChangeListener() { buttonView, isChecked ->
            if (isChecked) {
                switchPillProcedure.text = "процедуры"
              //  pills = false
                spinnerProcedures()
            } else {
                switchPillProcedure.text = "препараты"
              //  pills = true
                spinnerPills()

            }

        }
        addCourseButton.setOnClickListener() {

            val title = courseNameEdTxt.text.toString()
            if (title != "") {
                if(!(emptyDiagnosis||emptyDoctors||(emptyPills||emptyProcedures))){
                val shPref = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
                val savedEmail = shPref.getString("EMAIL_KEY", "").toString()
                var db = DbCourseHandler(context = this)
                var dbStatusCourse = DbStatusCourseHandler(context = this)
                var dbCourseHistory = DbCourseHistoryHandler(context = this)
                var statusCourse = dbStatusCourse.readStatusCourse()
                if (statusCourse.size == 0) {
                    var stCourse = StatusCourse("Архивный")
                    dbStatusCourse.insertStatusCourse(stCourse)
                    stCourse = StatusCourse("Текущий")
                    dbStatusCourse.insertStatusCourse(stCourse)
                    stCourse = StatusCourse("Завершенный")
                    dbStatusCourse.insertStatusCourse(stCourse)
                    stCourse = StatusCourse("Прерванный")
                    dbStatusCourse.insertStatusCourse(stCourse)

                }
                val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
                val currentDate = sdf.format(Date())
                val descr = ""
/*if(pills) {
    valuePillProcedure = choosePill
}else{
    valuePillProcedure = chooseProcedure
}*/
               // pills = pills

                //записываем курс
                var course = Course(
                    savedEmail,
                    valuePillProcedure,
                  pillstrue,
                    chooseDoctor,
                    chooseFood,
                    title,
                    "0",
                    "0",
                    "0",
                    currentDate,
                    descr,
                    chooseDiagnosis
                )
                db.insertCourse(course)
                var selectMaxCourseID = db.selectMaxId()
                var selectIDStatus = dbStatusCourse.getStatusArchive()
                var stCourse: Int = 0
                var courseID: Int = 0
                for (i in 0..selectMaxCourseID.size - 1) {
                    courseID = selectMaxCourseID.get(i).id
                    //  PointCourse.courseID = courseID       !!!!!!!!!
                }
                for (i in 0..selectIDStatus.size - 1) {
                    stCourse = selectIDStatus.get(i).id
                }
                var courseHistory = CourseHistory(courseID, stCourse, currentDate)
                dbCourseHistory.insertCourseHistory(courseHistory)

                val intent = Intent(this@UserSetCourseActivity, CourseActivity::class.java)
                startActivity(intent)
            } else{
                    Toast.makeText(this, "Добавьте недостающую информацию в систему", Toast.LENGTH_SHORT).show()
            }
        }else {
                Toast.makeText(this, "Введите название курса", Toast.LENGTH_SHORT).show()
            }

        }


    }


    fun spinnerPills() {
       pillstrue = "true"
        var db = DbPillsHandler(context = this)
        var data = db.readPill()
        var dataSpinner = arrayListOf<String>()
        var dataSpinnerID = arrayListOf<Int>()
        var dataSpinnerMeasurement = arrayListOf<String>()

        if(data.size!=0){
            for (i in 0..data.size - 1) {
                dataSpinner.add(data.get(i).title)
                dataSpinnerID.add(data.get(i).id)
                dataSpinnerMeasurement.add(data.get(i).measurement)
            }}else{
            dataSpinner.add("Нет добавленных")
            dataSpinnerID.add(0)
            emptyPills = true
        }


        pillsSpinner.adapter =
            ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dataSpinner)
        pillsSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                // TODO("Not yet implemented")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                choosePill = dataSpinnerID.get(position)
                valuePillProcedure = choosePill
            //    choosePillMeasurement = dataSpinnerMeasurement.get(position)
                /*for(i in 0..data.size-1){
                    if(data[i].equals("${dataSpinner.get(position)}")){
                        choosePill=i
                    }
                }*/
                //   Toast.makeText(applicationContext, "${dataSpinner.get(position)}", Toast.LENGTH_SHORT).show()

                //  measurement = units.get(position)
            }
        }

    }


    fun spinnerProcedures() {
        pillstrue = "false"
        var db = DbProcedureHandler(context = this)
        var data = db.readProcedure()
        var dataSpinner = arrayListOf<String>()
        var dataSpinnerID = arrayListOf<Int>()

        if(data.size!=0){
            for (i in 0..data.size - 1) {
                dataSpinner.add(data.get(i).title)
                dataSpinnerID.add(data.get(i).id)
            }}else{
            dataSpinner.add("Нет процедур")
            dataSpinnerID.add(0)
            emptyProcedures = true
        }

        pillsSpinner.adapter =
            ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dataSpinner)
        pillsSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                // TODO("Not yet implemented")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {chooseProcedure = dataSpinnerID.get(position)
                valuePillProcedure = chooseProcedure

              /*  for (i in 0..data.size - 1) {
                    if (data[i].equals("${dataSpinner.get(position)}")) {
                        chooseProcedure = i
                        valuePillProcedure = chooseProcedure
                    }
                }*/


                //  Toast.makeText(applicationContext, "${dataSpinner.get(position)}", Toast.LENGTH_SHORT).show()

                //  measurement = units.get(position)
            }
        }

    }

    fun spinnerDoctors() {

        var db = DbHandler(context = this)
        var data = db.readDoctor()
        var dataSpinner = arrayListOf<String>()
        var dataSpinnerID = arrayListOf<Int>()
        if(data.size!=0){
            for (i in 0..data.size - 1) {
                dataSpinner.add(data.get(i).firstName + " " + data.get(i).lastName + " (" + data.get(i).position + ")")
                dataSpinnerID.add(data.get(i).id)

            }}else{
            dataSpinner.add("Нет врачей")
            dataSpinnerID.add(0)
            emptyDoctors = true
        }

        doctorsSpinner.adapter =
            ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dataSpinner)
        doctorsSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                //   TODO("Not yet implemented")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                chooseDoctor = dataSpinnerID.get(position)

                /*   for(i in 0..data.size-1){
                       if(data[i].equals("${dataSpinner.get(position)}")){
                           chooseDoctor=i
                       }
                   }*/
                //  Toast.makeText(applicationContext, "${dataSpinner.get(position)}", Toast.LENGTH_SHORT).show()

                //  measurement = units.get(position)
            }
        }

    }

    fun spinnerFoods() {

        //  var db = DbHandler(context = this)
        var dataSpinner =
            arrayOf<String>("до еды", "после еды", "во время еды", "нет зависимости от еды")

        foodsSpinner.adapter =
            ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dataSpinner)
        foodsSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                // TODO("Not yet implemented")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                chooseFood = dataSpinner.get(position)
                //   Toast.makeText(applicationContext, "${dataSpinner.get(position)}", Toast.LENGTH_SHORT).show()

                //  measurement = units.get(position)
            }
        }

    }


    fun spinnerDiagnosis() {

        var db = DbDiagnosisHandler(context = this)
        var data = db.readDiagnosis()
        var dataSpinner = arrayListOf<String>()
        var dataSpinnerID = arrayListOf<Int>()

        if(data.size!=0){
        for (i in 0..data.size - 1) {
            dataSpinner.add(data.get(i).diagnosisTitle)
            dataSpinnerID.add(data.get(i).id)
        }}else{
            dataSpinner.add("Нет процедур")
            dataSpinnerID.add(0)
            emptyDiagnosis = true
        }

        diagnosisSpinner.adapter =
            ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dataSpinner)
        diagnosisSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                //     TODO("Not yet implemented")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                chooseDiagnosis = dataSpinnerID.get(position)
                /*for(i in 0..data.size-1){
                    if(data[i].equals("${dataSpinner.get(position)}")){
                        choosePill=i
                    }
                }*/
                //   Toast.makeText(applicationContext, "${dataSpinner.get(position)}", Toast.LENGTH_SHORT).show()

                //  measurement = units.get(position)
            }
        }

    }


}