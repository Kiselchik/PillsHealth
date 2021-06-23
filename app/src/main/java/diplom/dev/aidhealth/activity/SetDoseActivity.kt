package diplom.dev.aidhealth.activity

import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import diplom.dev.aidhealth.CheckCourse
import diplom.dev.aidhealth.R
import diplom.dev.aidhealth.db.handler.*
import diplom.dev.aidhealth.db.model.Course
import diplom.dev.aidhealth.db.model.CourseHistory
import diplom.dev.aidhealth.db.model.CoursePoint
import diplom.dev.aidhealth.db.model.StatusCourse
import diplom.dev.aidhealth.fragment.DoseStablyFragment
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalTime
import java.time.Period
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*
import javax.xml.datatype.DatatypeConstants.DAYS


class SetDoseActivity: AppCompatActivity() {
    val fm = supportFragmentManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_dose)
        dialog()
    }

    fun dialog(){

            val builder = android.app.AlertDialog.Builder(this@SetDoseActivity)
        val measurement  = intent.getStringExtra("MEASUREMENT")

        builder.setTitle("Дозировка")
            builder.setMessage("Выберете тип графика дозировки")
                builder.setPositiveButton("Стабильна") { dialog, which ->
                    val fragmentDoseStably = DoseStablyFragment()
                    val bundle = Bundle()
                    bundle.putString("MEASUREMENT", measurement )
                    fragmentDoseStably.arguments = bundle
                    fm.beginTransaction()
                        .add(R.id.frgmCont, fragmentDoseStably)
                        .commit()
                 //   val shPref = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)


                }
                builder.setNegativeButton("Умен") { dialog, which ->


                }
                builder.setNeutralButton("Увел") { dialog, which ->

                }
                val dialog: android.app.AlertDialog = builder.create()
                dialog.show()

                //dialog.setTitle("Диалог")
                //   calendar.visibility = View.INVISIBLE
                //    val intent = Intent(this@TuningCourseActivity, SetTimeCourseActivity::class.java)
                //    startActivity(intent)


            }


    @RequiresApi(Build.VERSION_CODES.O)
    fun saveCourse(stablyDose: Int){
        var courseName =  intent.getStringExtra("COURSE_NAME")
        var choosePill = intent.getStringExtra("CHOOSE_PILL")
        var chooseProcedure = intent.getStringExtra("CHOOSE_PROCEDURE")
        var measurement = intent.getStringExtra("MEASUREMENT")
        var chooseFood = intent.getStringExtra("CHOOSE_FOOD")
        var chooseDoctor = intent.getStringExtra("CHOOSE_DOCTOR")
        var chooseDiagnosis = intent.getStringExtra("CHOOSE_DIAGNOSIS")
        var startDate = intent.getStringExtra("START_DATE")
        var endDate = intent.getStringExtra("END_DATE")
        var numInDay = intent.getStringExtra("NUM_IN_DAY")
        var chooseHour = intent.getStringExtra("CHOOSE_HOUR")
        var chooseMinute = intent.getStringExtra("CHOOSE_MINUTE")
        var intervalDays = intent.getStringExtra("INTERVAL_DAYS")
        var firstTime = intent.getStringExtra("FIRST_TIME").toString()



        val shPref = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val savedEmail = shPref.getString("EMAIL_KEY", "").toString()
        val sdf = SimpleDateFormat("dd/M/yyyy")
        val currentDate = sdf.format(Date())
        val descr = ""
        if (chooseProcedure ==null){
            CheckCourse.procedure=true
            chooseProcedure = 0.toString()
        }
        else{
            CheckCourse.procedure = false
            choosePill = 0.toString()
        }
        var course = Course(
            savedEmail,
            chooseProcedure!!.toInt(),
            choosePill.toString(),
            chooseDoctor!!.toInt(),
            chooseFood.toString(),
            courseName.toString(),
            "0",
            "0",
            "0",
            currentDate,
            descr,
            chooseDiagnosis!!.toInt()
        )
        var dbCourse = DbCourseHandler(context = this)
        dbCourse.insertCourse(course)

        //получаем айди последнего записанного курса
        var selectMaxCourseID = dbCourse.selectMaxId()
        var courseID = selectMaxCourseID.get(0).id

        //получаем айди статуса Архивный
        var dbStatusCourse = DbStatusCourseHandler(context = this)
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
        var getStatusArchive = dbStatusCourse.getStatusArchive()
        var statusArchiveID = getStatusArchive.get(0).id

        //записываем статус курса в историю курса
        var dbCourseHistory = DbCourseHistoryHandler(context = this)
        var courseHistory = CourseHistory(courseID, statusArchiveID, currentDate)
        dbCourseHistory.insertCourseHistory(courseHistory)

        //счетчик дат
       // val startDay =LocalDate.parse(startDate.toString(),DateTimeFormatter.ofPattern("dd/mm/yyyy", Locale.ENGLISH))
        val startDay = SimpleDateFormat("dd/mm/yyyy").parse("$startDate")
        val firstDate = SimpleDateFormat("hh:mm").parse(firstTime)
       // val firstDate = LocalTime.parse(firstTime, DateTimeFormatter.ofPattern("hh:mm"))
      ///  var firstHourse = firstDate.hours

        //var firstMinute = firstDate.minutes

       // val endDay =LocalDate.parse(endDate,  DateTimeFormatter.ofPattern("dd/mm/yyyy"))
        val endDay = SimpleDateFormat("dd/mm/yyyy").parse(endDate)
       // val difference = Period.between(startDay, endDay)

       // val betweenYears = difference.years
       // val betweenDays = difference.days
       // val betweenDays = difference.days
        //val endDay = SimpleDateFormat("dd/mm/yyyy").parse(endDate)


        //получаем айти статуса ожидание
        val dbStatusPoint = DbStatusPointHandler(context = this)
        val statusWaitId = dbStatusPoint.getIdStatusPointWait().get(0).id

        val dbCoursePoint = DbCoursePointHandler(context = this)

       val calendarTime = Calendar.getInstance()
        val calendarDays = Calendar.getInstance()
        calendarTime.time = firstDate
       // set(startDay.year,startDay.month,startDay.day, firstHourse, firstMinute)
       calendarDays.time = startDay
        var intervalMinute = chooseHour!!.toInt()*60+chooseMinute!!.toInt()
        //записываем уведомления
        var j = 0
      //  var datePoint: LocalDate = startDay
        if(numInDay!!.toInt()>=1){
          do {
              for (i in 0..numInDay!!.toInt()) {
                 // var period = Period.of(0,0,(intervalDays!!.toInt()+1) *  j)
                   //datePoint = startDay.plus(period)
                  //TODO: как минуты в инт перевести в лонги
                 // var time = firstDate.plusMinutes((intervalMinute*i).toLong())
                    calendarDays.add(Calendar.DAY_OF_YEAR, (intervalDays!!.toInt()+1) *  j )
                   calendarTime.add(Calendar.MINUTE, intervalMinute * i) //время


                  //TODO: взять из календаря дату и время
               /*   val coursePoint = CoursePoint(courseID, stablyDose.toString(), "${datePoint.year}/${datePoint.month}/${datePoint.dayOfMonth}",
                      "${time.hour}:${time.minute}", statusWaitId )*/

                  val coursePoint = CoursePoint(courseID, stablyDose.toString(), "${calendarDays.time.date}",
                      "${calendarTime.time.hours}:${calendarTime.time.minutes}", statusWaitId, null  )
                  dbCoursePoint.insertCoursePoint(coursePoint)
              }
              j++
          }
              while(calendarDays.time != endDay)
        }


    }
        }

