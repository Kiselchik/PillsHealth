package diplom.dev.aidhealth.activity

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import diplom.dev.aidhealth.*
import diplom.dev.aidhealth.db.handler.*
import diplom.dev.aidhealth.db.model.CoursePoint
import diplom.dev.aidhealth.notification.NotificationCourse
import java.sql.Time
import java.text.SimpleDateFormat
import java.time.*
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.time.days


class MainActivity : AppCompatActivity() {
    private lateinit var userName: TextView
    private lateinit var pills_button: Button
    private lateinit var doctor_button: Button
    private lateinit var dynamic_button: Button
    private lateinit var course_button: Button
    private lateinit var health_btn: Button
  //  private lateinit var symptom_button: Button
    private lateinit var toDiagnosisBtn: Button
  //  private lateinit var clearCourseBtn: Button
  //  private lateinit var clearPillBtn: Button
  //  private lateinit var clearDoctorBtn: Button
  //  private lateinit var clearDiagnosisBtn: Button
 //   private lateinit var toProcedureActivity: Button
    private lateinit var alarmText: TextView
    lateinit var pointObj: CoursePoint
    private lateinit var options_btn: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        PointCourse.pointID = ""
        initializeView()
        setUserNameTxt()
        setListener()
        val shPref = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val editor = shPref.edit()
        editor.apply {

        }.apply()

       // restartNotify()
      //  getNotificationInfo()
        //TODO: добавить кнопку выхода - удлить sharedpreferences
    }

    private fun initializeView() {
        userName = findViewById(R.id.mainFirstLastNameTxt)
        pills_button = findViewById(R.id.ib_pills)
        doctor_button = findViewById(R.id.ib_doctors)
        //dynamic_button = findViewById(R.id.ib_dynamic)
        course_button = findViewById(R.id.ib_course)
        health_btn = findViewById(R.id.health_btn)
        toDiagnosisBtn = findViewById(R.id.toDiagnosysBtn)
        options_btn = findViewById(R.id.options_btn)

    }

    fun setUserNameTxt() {
        val shPref = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val savedEmail = shPref.getString("EMAIL_KEY", "")
        User.email = savedEmail!!
        val savedFirstName = shPref.getString("FIRSTNAME_KEY", "")
        val savedLastName = shPref.getString("LASTNAME_KEY", null)
        userName.text = savedFirstName + " " + savedLastName

    }


    private fun setListener() {
        pills_button.setOnClickListener() {
            val intent = Intent(this@MainActivity, PillsActivity::class.java)
            startActivity(intent)
        }

        doctor_button.setOnClickListener() {
            val intent = Intent(this@MainActivity, DoctorActivity::class.java)
            startActivity(intent)
        }
        course_button.setOnClickListener() {
            val intent = Intent(this@MainActivity, CourseActivity::class.java)
            startActivity(intent)
        }

        toDiagnosisBtn.setOnClickListener() {
            val intent = Intent(this@MainActivity, DiagnosisActivity::class.java)
            startActivity(intent)
        }
        options_btn.setOnClickListener(){
            val intent = Intent(this@MainActivity, OptionsActivity::class.java)
            startActivity(intent)
        }
        health_btn.setOnClickListener(){
            val intent = Intent(this@MainActivity, HealthActivity::class.java)
            startActivity(intent)
        }
  /*      dynamic_button.setOnClickListener(){
            val intent = Intent(this@MainActivity, DynamicMainActivity::class.java)
            startActivity(intent)
           // Toast.makeText(this, "функционал в разработке", Toast.LENGTH_SHORT).show()

        }*/
    /*    clearCourseBtn.setOnClickListener() {
            var dbCourse = DbCourseHandler(context = this)
            var dbCourseHistory = DbCourseHistoryHandler(context = this)
            var dbCoursePoint = DbCoursePointHandler(context = this)
            dbCourse.clearTableCourse()
            dbCourseHistory.clearTableCourseHistory()
            dbCoursePoint.clearTableCoursePoint()
        }

        clearPillBtn.setOnClickListener() {
            var dbPill = DbPillsHandler(context = this)
            dbPill.clearTablePill()
        }
        clearDoctorBtn.setOnClickListener() {
            var dbDoctor = DbHandler(context = this)
            dbDoctor.clearTableDoctor()
        }

        symptom_button.setOnClickListener() {
            val intent = Intent(this@MainActivity, SymptomActivity::class.java)
            startActivity(intent)
        }
        clearDiagnosisBtn.setOnClickListener() {
            var dbDiagnosis = DbDiagnosisHandler(context = this)
            var dbDiagnosisSymptom = DbDiagnosisSymptomHandler(context = this)

            dbDiagnosis.clearTableDiagnosis()
            dbDiagnosisSymptom.clearTableDiagnosisSymptom()
        }
        toProcedureActivity.setOnClickListener() {
            val intent = Intent(this@MainActivity, ProcedureActivity::class.java)
            startActivity(intent)
        }*/

    }

    fun getNotificationInfo() {
        var dbCourse = DbCourseHandler(context = this)
        var dbCoursePoint = DbCoursePointHandler(context = this)
        var coursesPoints = mutableListOf<CoursePoint>()
        //получаем идентификаторы курсов для этго пользователя
        var usersCourses = dbCourse.readCourseWithNotification()
        if (usersCourses.size != 0) {
            //получаем идентификаторы статуса ОЖИДАНИЕ
            var statusPoint = DbStatusPointHandler(context = this)
            var stWaitID = statusPoint.getIdStatusPointWait().get(0).id
            StatusPoint.stWaitID = stWaitID
            //получаем идентификатор статуса ОТЛОЖЕН
            var stPostponedID = statusPoint.getIdStatusPointPostponed().get(0).id
            StatusPoint.stPostponedID = stPostponedID

            //вычленяем coursePoint с этими статусами


            //Получаем отдельные идентификаторы
            //Для этих идентификаторов ищем поинты с определенными статусами
//получаем coursePoint для курсов пользовтеля, если их статусы подходят
            var coursesPointsObj = mutableListOf<CoursePoint>()
            for (i in 0..usersCourses.size - 1) {
                var courseID = usersCourses.get(i).id //получаем айди курса
                User.courseId = courseID
                coursesPointsObj =
                    dbCoursePoint.getCourseForUser() //получаем список поинтов для этого айди
             /*   alarmText.text =
                    "${alarmText.text}" + "mo ntf. coursesPointsObj ${coursesPointsObj.size}"*/
                for (j in 0..coursesPointsObj.size - 1) {
                    coursesPoints.add(coursesPointsObj[j])
                }
            }

            if (coursesPoints.size > 0) {
                //массив дат для дальнейшего сравнения
                var days = mutableListOf<Date>()
                var pointID = mutableListOf<Int>()
                var times = mutableListOf<Date>()


                for (i in 0..coursesPoints.size - 1) {
                    var datePoint = coursesPoints.get(i).day
                    var timePoint = coursesPoints.get(i).time
                    val day = SimpleDateFormat("dd/mm/yyyy").parse(datePoint)
                    val time = SimpleDateFormat("hh:mm").parse(timePoint)
                    days.add(day)
                    // pointID.add(coursesPoints.get(i).pointID)
                    pointID.add(i)

                    times.add(time)
                }

                //сравниваем даты. Ищем наименьшую
                var minDate: Date = days[0]
                var index = mutableListOf<Int>()
                index.add(0)
                var minDatePointID = mutableListOf<Int>()
                for (i in 1..days.size - 1) {
                    if (minDate >= days[i]) {  //равно, чтобы учесть одинаковые даты
                        minDate = days[i]
                        minDatePointID.add(pointID.get(i))
                        index.add(i)
                    }

                }

                //сравниваем время. Ищем минимальное
                var minTime: Date = times.get(index[0])
                var minTimeId: Int = index[0]
                for (i in 1..minDatePointID.size - 1) {
                    if (minTime >= times.get(index[i])) {
                        minTime = times.get(index[i])
                        minTimeId = index[i]
                    }
                }
                //получить индентификатор поинта с этой минимальной датой и временем

                var minPointID = pointID.get(minTimeId)

                //получить объект с этим идентификатором
                pointObj = coursesPoints.get(minPointID)

                restartNotify(pointObj)

            } else {
            /*    alarmText.text =
                    "${alarmText.text}" + "mo ntf. coursesPointsObj ${coursesPointsObj.size} \n StatusPoint.stWaitID = ${StatusPoint.stWaitID}"*/

            }
        }
    }

    private fun restartNotify(pointObj: CoursePoint) {
        var am = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, NotificationCourse::class.java)

        val pendingIntent = PendingIntent.getBroadcast(
            this, 0,
            intent, PendingIntent.FLAG_UPDATE_CURRENT
        )
        // На случай, если мы ранее запускали активити, а потом поменяли время,
// откажемся от уведомления
        am.cancel(pendingIntent)
        // Устанавливаем разовое напоминание
        //val stamp = LocalTime.of(15,01)
        // val stringDate = expiration_button.text.toString()
        // val dt = LocalTime.parse("15:10", formatter);
        // var stamp = Date(2021, 5,8,14,54)
        var time = SimpleDateFormat("hh:mm").parse(pointObj.time)
        var date = SimpleDateFormat("dd/mm/yyyy").parse(pointObj.day)
        var calendar: Calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, date.year)
        calendar.set(Calendar.MONTH, date.month + 1)
        calendar.set(Calendar.DAY_OF_MONTH, date.day)
        calendar.set(Calendar.HOUR_OF_DAY, time.hours)
        calendar.set(Calendar.MINUTE, time.minutes)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
//выяснить препарат или процедура
        var titleNtf = ""
        DataRecyclerCourse.courseID =  pointObj.courseID
        var dbCourse = DbCourseHandler(context = this)
        var pill = dbCourse.getOneCourse().get(0).medicament
        var valuePillProcedure = dbCourse.getOneCourse().get(0).valuePillProcedure
        DataRecyclerCourse.valueID = valuePillProcedure
        if(pill.equals("true")){
            var dbPill = DbPillsHandler(context = this)
             titleNtf = dbPill.getOnePill().get(0).title
        }else{
            var dbProcedure = DbProcedureHandler(context = this)
             titleNtf = dbProcedure.getOneProcedure().get(0).title
        }
        DataRecyclerCourse.courseID = 0
        DataRecyclerCourse.valueID = 0
        //вывести в уведомление название, дозу, еду


        PointCourse.pointID = pointObj.dose + " ${titleNtf} "


        am.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)

        // alarmText.text = "alarm ok ${pointObj.day}, ${pointObj.time}"
    }

    //TODO: кнопки в уведомлениях
    //TODO: синхронизация с БД


}

