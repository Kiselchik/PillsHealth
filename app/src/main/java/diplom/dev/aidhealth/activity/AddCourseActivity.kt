package diplom.dev.aidhealth.activity

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import diplom.dev.aidhealth.R
import diplom.dev.aidhealth.db.handler.*
import diplom.dev.aidhealth.db.model.Course
import diplom.dev.aidhealth.db.model.CourseHistory
import diplom.dev.aidhealth.db.model.StatusCourse
import kotlinx.android.synthetic.main.activity_add_pills.*
import java.text.SimpleDateFormat
import java.util.*


class AddCourseActivity : AppCompatActivity() {

    private lateinit var pillsSpinner: Spinner

    // private lateinit var proceduresSpinner: Spinner
    private lateinit var doctorsSpinner: Spinner
    private lateinit var foodsSpinner: Spinner
    private lateinit var diagnosisSpinner: Spinner
    private lateinit var addCourseButton: Button
    private lateinit var courseNameEdTxt: EditText
    private lateinit var switchPillProcedure: Switch
    private lateinit var timeChSmptmChBox: CheckBox
    private lateinit var timeHealthChBox: CheckBox
    private lateinit var courseNtfChBox: CheckBox
    private lateinit var endCourseTxt: TextView

    // private lateinit var descrEdTxt: EditText
    private lateinit var startCourseEdTxt: TextView
    private lateinit var numInDayEdTxt: EditText
    private lateinit var intervalDaysEdTxt: EditText
    private lateinit var firstTimeEdTxt: TextView
    private lateinit var spinnerIntervalHours: Spinner
    private lateinit var spinnerIntervalMinute: Spinner


    var chooseProcedure: Int = 0
    var choosePill: Int = 0
    var chooseDoctor: Int = 0
    var chooseFood: String = ""
    var chooseTimeChSmptmChBox = "0"
    var chooseTimeHealthChBox = "0"
    var chooseCourseNtfChBox = "0"
    var chooseDiagnosis = 0
    var startDate = ""
    var endDate = ""
    var chooseHour = ""
    var chooseMinute = ""
    var startHour=""
    var startMunite = ""
    var pills = true
    var choosePillMeasurement = ""
    var emptyPills = false
    var emptyProcedures = false
    var emptyDoctors = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_course_layout2)
        initialize()
        setListener()
        //    checkRow()
        //checkBox()
        spinnerPills()
        spinnerDoctors()
        spinnerFoods()
        //spinnerProcedures()
        spinnerDiagnosis()
        spinnerHours()
        spinnerMinute()

    }

    fun initialize() {
        pillsSpinner = findViewById(R.id.pillsSpinner)
        // proceduresSpinner = findViewById(R.id.procedursSpinner)
        doctorsSpinner = findViewById(R.id.doctorsSpinner)
        foodsSpinner = findViewById(R.id.foodsSpinner)
        addCourseButton = findViewById(R.id.addCourseButton)
        courseNameEdTxt = findViewById(R.id.courseNameEdTxt)
        //   timeChSmptmChBox = findViewById(R.id.timeChSmptmChBox)
        //   timeHealthChBox = findViewById(R.id.timeHealthChBox)
        //   courseNtfChBox = findViewById(R.id.courseNtfChBox)
        //   descrEdTxt = findViewById(R.id.descrEdTxt)
        diagnosisSpinner = findViewById(R.id.diagnosisSpinner)
        switchPillProcedure = findViewById(R.id.switchPillProcedure)
        startCourseEdTxt = findViewById(R.id.startCourseEdTxt)
        endCourseTxt = findViewById(R.id.endCourseTxt)
        startCourseEdTxt.setText(Html.fromHtml("<u>начало курса</u>"))
        endCourseTxt.setText(Html.fromHtml("<u>конец курса</u>"))
        numInDayEdTxt = findViewById(R.id.numInDayEdTxt)
        intervalDaysEdTxt = findViewById(R.id.intervalDaysEdTxt)
        firstTimeEdTxt = findViewById(R.id.firstTimeEdTxt)
        firstTimeEdTxt.setText(Html.fromHtml("<u>время</u>"))
        spinnerIntervalHours = findViewById(R.id.spinnerIntervalHours)
        spinnerIntervalMinute = findViewById(R.id.spinnerIntervalMinute)


    }


    fun checkRow() {
        var courseName = courseNameEdTxt.text.toString()

        if (courseName == "") {
            addCourseButton.isEnabled = false
            addCourseButton.isClickable = false
        } else {
            addCourseButton.isEnabled = true
            addCourseButton.isClickable = true
        }
    }


    private fun saveDateCourse(){
        val intent = Intent(this@AddCourseActivity, SetDoseActivity::class.java)
        intent.putExtra("COURSE_NAME",courseNameEdTxt.text.toString())
        if(pills){
            intent.putExtra("CHOOSE_PILL", choosePill.toString())
            intent.putExtra("MEASUREMENT", choosePillMeasurement)
        }else{
            intent.putExtra("CHOOSE_PROCEDURE", chooseProcedure.toString())
            intent.putExtra("MEASUREMENT","раз")

        }
        intent.putExtra("CHOOSE_FOOD", chooseFood)
        intent.putExtra("CHOOSE_DOCTOR", chooseDoctor.toString())
        intent.putExtra("CHOOSE_DIAGNOSIS", chooseDiagnosis.toString())
        //TODO: проверка пустоты дат
        intent.putExtra("START_DATE", startDate)
        intent.putExtra("END_DATE", endDate)
        intent.putExtra("NUM_IN_DAY", numInDayEdTxt.text.toString())
        intent.putExtra("CHOOSE_HOUR", chooseHour)
        intent.putExtra("CHOOSE_MINUTE", chooseMinute)
        intent.putExtra("INTERVAL_DAYS", intervalDaysEdTxt.text.toString())
        intent.putExtra("FIRST_TIME", firstTimeEdTxt.text.toString())
        startActivity(intent)


    }

    fun setListener() {

        addCourseButton.setOnClickListener() {
            //TODO: проверь пустоту
            if(checkDate() && checkNumInDay() && checkIntervalDaysEdTxt() && checkFirstTimeEdTxt()){
                saveDateCourse()
            }else{
                Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show()
            }


/*

            val title = courseNameEdTxt.text.toString()
            if (title != "") {
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
                val sdf = SimpleDateFormat("dd/M/yyyy")
                val currentDate = sdf.format(Date())
                val descr = ""


                //записываем курс
                var course = Course(
                    savedEmail,
                    chooseProcedure,
                    choosePill,
                    chooseDoctor,
                    chooseFood,
                    title,
                    chooseTimeChSmptmChBox,
                    chooseTimeHealthChBox,
                    chooseCourseNtfChBox,
                    currentDate,
                    descr,
                    chooseDiagnosis
                )
                db.insertCourse(course)
                var selectMaxCourseID = db.selectMaxId()
                var selectIDStatus = dbStatusCourse.setStatusArchive()
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
                dbCourseHistory.insertCourseinsertCourseHistory(courseHistory)

                val intent = Intent(this@AddCourseActivity, CourseActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Введите название курса", Toast.LENGTH_SHORT)
            }*/
        }

        switchPillProcedure.setOnCheckedChangeListener() { buttonView, isChecked ->
            if (isChecked) {
                switchPillProcedure.text = "процедуры"
                pills = false
                spinnerProcedures()
            } else {
                switchPillProcedure.text = "препараты"
                pills = true
                spinnerPills()

            }

        }
        startCourseEdTxt.setOnClickListener() {
            calendar(true)
        }
        endCourseTxt.setOnClickListener() {
            calendar(false)
        }
        firstTimeEdTxt.setOnClickListener(){
            timePicker()
        }




    }

    fun calendar(start: Boolean) {
        var calendar = Calendar.getInstance()
        var mYear = calendar.get(Calendar.YEAR);
        var mMonth = calendar.get(Calendar.MONTH);
        var mDay = calendar.get(Calendar.DAY_OF_MONTH);

        val d =
            OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, monthOfYear)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                if (start) {
                    startDate = "$dayOfMonth/${monthOfYear + 1}/$year"
                    startCourseEdTxt.setText(Html.fromHtml("<u>$dayOfMonth/${monthOfYear + 1}/$year</u>"))
                } else {
                    endCourseTxt.setText(Html.fromHtml("<u>$dayOfMonth/${monthOfYear + 1}/$year</u>"))
                    endDate = "$dayOfMonth/${monthOfYear + 1}/$year"
                }
                //   setInitialDateTime()
            }


        var datePickerDialog = DatePickerDialog(this@AddCourseActivity, d, mYear, mMonth, mDay)
        datePickerDialog.show()
    }

    fun timePicker() {
        var calendar = Calendar.getInstance()
        var mHour = calendar.get(Calendar.HOUR_OF_DAY)
        var mMinute = calendar.get(Calendar.MINUTE)

        var t = OnTimeSetListener { view, hourOfDay, minute ->
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
            calendar.set(Calendar.MINUTE, minute)
            startHour = hourOfDay.toString()
            startMunite = minute.toString()
            firstTimeEdTxt.text = "$startHour:$startMunite"
        }


        var timePickerDialog = TimePickerDialog(this@AddCourseActivity, t, mHour, mMinute, true)
        timePickerDialog.show()
    }



    fun checkDate(): Boolean {
        if (startDate.equals("") || endDate.equals("")) {
            return false
        } else {
            val start = SimpleDateFormat("dd/mm/yyyy").parse(startDate)
            var end = SimpleDateFormat("dd/mm/yyyy").parse(endDate)
            if (end < start) {
                Toast.makeText(this, "Некорректная дата окончания курса", Toast.LENGTH_SHORT).show()
            }
            return true
        }
    }
    fun  checkNumInDay(): Boolean {
        if(numInDayEdTxt.text.length==0){
            return false
        }else{
            return true
        }
    }
    fun checkIntervalDaysEdTxt(): Boolean{
        if(intervalDaysEdTxt.text.length==0){
            return false
        }else{
            return true
        }
    }
    fun checkFirstTimeEdTxt(): Boolean{
        if(chooseHour.length==0 || chooseMinute.length==0){
            return false
        }else{
            return true
        }
    }



    /*  fun checkBox(){
        timeChSmptmChBox.setOnCheckedChangeListener(){buttonView, isChecked ->
        if(isChecked){
            chooseTimeChSmptmChBox = "1"
        }else{
            chooseTimeChSmptmChBox = "0"
        }

        }
        timeHealthChBox.setOnCheckedChangeListener(){buttonView, isChecked ->
            if(isChecked){
                chooseTimeHealthChBox = "1"
            }
            else{
                chooseTimeHealthChBox = "0"

            }

        }
        courseNtfChBox.setOnCheckedChangeListener(){buttonView, isChecked ->
            if(isChecked){
                chooseCourseNtfChBox = "1"
            }else{
                chooseCourseNtfChBox = "0"

            }

        }
    }

*/


    fun spinnerPills() {

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
                choosePillMeasurement = dataSpinnerMeasurement.get(position)
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

        var db = DbProcedureHandler(context = this)
        var data = db.readProcedure()
        var dataSpinner = arrayListOf<String>()
        if(data.size!=0){
        for (i in 0..data.size - 1) {
            dataSpinner.add(data.get(i).title)
        }}else{
            dataSpinner.add("Нет процедур")
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
            ) {
                for (i in 0..data.size - 1) {
                    if (data[i].equals("${dataSpinner.get(position)}")) {
                        chooseProcedure = i
                    }
                }


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

        for (i in 0..data.size - 1) {
            dataSpinner.add(data.get(i).diagnosisTitle)
            dataSpinnerID.add(data.get(i).id)
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

    fun spinnerHours() {

        //  var db = DbHandler(context = this)
        var dataSpinner = arrayListOf<String>()
        for (i in 0..23) {
            dataSpinner.add(i.toString()+"ч")
        }


        spinnerIntervalHours.adapter =
            ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dataSpinner)
        spinnerIntervalHours.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                // TODO("Not yet implemented")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                chooseHour = dataSpinner.get(position).replace("ч", "")
                //   Toast.makeText(applicationContext, "${dataSpinner.get(position)}", Toast.LENGTH_SHORT).show()

                //  measurement = units.get(position)
            }
        }
    }


    fun spinnerMinute() {

        //  var db = DbHandler(context = this)
        var dataSpinner = arrayListOf<String>()
        for (i in 0..12) {

            dataSpinner.add((i*5).toString()+"мин")
        }


        spinnerIntervalMinute.adapter =
            ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dataSpinner)
        spinnerIntervalMinute.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                // TODO("Not yet implemented")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                chooseMinute = dataSpinner.get(position).replace("мин", "")
                //   Toast.makeText(applicationContext, "${dataSpinner.get(position)}", Toast.LENGTH_SHORT).show()

                //  measurement = units.get(position)
            }
        }
    }

}