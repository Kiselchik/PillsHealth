package diplom.dev.aidhealth.activity

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import diplom.dev.aidhealth.*
import diplom.dev.aidhealth.db.handler.DbCourseHandler
import diplom.dev.aidhealth.db.handler.DbCoursePointHandler
import diplom.dev.aidhealth.db.handler.DbStatusPointHandler
import diplom.dev.aidhealth.db.model.CoursePoint
import diplom.dev.aidhealth.db.model.StatusPoint
import diplom.dev.aidhealth.notification.NotificationCourse
import java.text.SimpleDateFormat
import java.util.*

class ManualSetingCoursePointActivity : AppCompatActivity() {
    private lateinit var dosePillEdTxt: EditText
    private lateinit var measuringDoseSpinner: Spinner
    private lateinit var manualTimePoint: TextView
    private lateinit var setTimePicker: TimePicker
    private lateinit var saveCoursePointButton: Button
    var hour= 0
    var minute = 0
    var measurement = ""
    lateinit var pointObj: CoursePoint


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manual_seting_course_point)
     //   var textViewTestManual = findViewById<TextView>(R.id.textViewTestManual)
     //   textViewTestManual.text = ""
    for(i in 0..Items.chooseItem.size-1){
     //   textViewTestManual.text = "${textViewTestManual.text} \n ${Items.chooseItem}"
    }
        initialise()

        spinnerMeasuring()
        setListener()
    }

    private fun initialise(){
        dosePillEdTxt = findViewById(R.id.dosePillEdTxt)
        measuringDoseSpinner = findViewById(R.id.measuringDoseSpinner)
        manualTimePoint = findViewById(R.id.manualTimePoint)
        setTimePicker = findViewById(R.id.setTimePicker)
        saveCoursePointButton = findViewById(R.id.saveCoursePointButton)
        setTimePicker.setIs24HourView(true)


    }
    fun spinnerMeasuring() {

        // var db = DbPillsHandler(context = this)
        //var data = db.readPill()
        var dataSpinner = arrayListOf<String>("шт", "мл", "мг", "капель", "впрыскиваний")
        /* for (i in 0..data.size-1) {
             dataSpinner.add(data.get(i).title)
         }*/

        measuringDoseSpinner.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dataSpinner)
        measuringDoseSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                //   Toast.makeText(applicationContext, "${dataSpinner.get(position)}", Toast.LENGTH_SHORT).show()

                measurement = dataSpinner.get(position)
            }
        }

    }

    private fun setListener() {
        manualTimePoint.setOnClickListener() {

            if (Build.VERSION.SDK_INT >= 23) {
                hour = setTimePicker.hour
                minute = setTimePicker.minute
            } else {
                hour = setTimePicker.currentHour
                minute = setTimePicker.currentMinute
            }
            manualTimePoint.text = "$hour : $minute"

        }
        saveCoursePointButton.setOnClickListener(){
            // var Array = intent.getStringArrayListExtra("DATES")
          //  Items.numInDay = numInDayEdTxt.text.toString().toInt()
            //Items.timeFrom = fromTimeEdTxt.text.toString()
            //  Items.timeTo = toTimeEdTxt.text.toString()
            Items.dose = dosePillEdTxt.text.toString()
            Items.measuring = measurement



            var dbStatusPoint = DbStatusPointHandler(context = this)
            var dataStatusPoint = dbStatusPoint.readStatusPoint()
            if(dataStatusPoint.size==0){
                var statusPoint = StatusPoint("Ожидание")
                dbStatusPoint.insertStatusPoint(statusPoint)
                statusPoint = StatusPoint("Отложен")
                dbStatusPoint.insertStatusPoint(statusPoint)
                statusPoint = StatusPoint("Отклонен")
                dbStatusPoint.insertStatusPoint(statusPoint)
                statusPoint = StatusPoint("Подтвержден")
                dbStatusPoint.insertStatusPoint(statusPoint)
            }

            var statusWaitId = dbStatusPoint.getIdStatusPointWait().get(0).id
            //course ID???
            for(i in 0..Items.chooseItem.size-1){
                var dbCoursePoint = DbCoursePointHandler(context = this)
                //TODO: insert values to CoursePoint
                //TODO: проверять все ли выбранные даты насторены!!!
                    //было PointCourse.courseID

                    var coursePoint = CoursePoint(DataRecyclerCourse.courseID,
                        dosePillEdTxt.text.toString(),
                        Items.chooseItem[i],
                        "$hour:$minute",
                        statusWaitId,
                        null )
                    dbCoursePoint.insertCoursePoint(coursePoint)


            }



            /// testInterval.text = doseTime.joinToString()
            //getNotificationInfo()

        }


        }

   /* fun getNotificationInfo(){
        var dbCourse = DbCourseHandler(context = this)
        var dbCoursePoint = DbCoursePointHandler(context = this)
        var coursesPoints = mutableListOf<CoursePoint>()
        //получаем идентификаторы курсов для этго пользователя
        var usersCourses = dbCourse.readCourseWithNotification()
        if(usersCourses.size!=0) {
            //получаем идентификаторы статуса ОЖИДАНИЕ
            var statusPoint = DbStatusPointHandler(context = this)
            var stWaitID = statusPoint.getIdStatusPointWait().get(0).id
            diplom.dev.aidhealth.StatusPoint.stWaitID = stWaitID
            //получаем идентификатор статуса ОТЛОЖЕН
            var stPostponedID = statusPoint.getIdStatusPointPostponed().get(0).id
            diplom.dev.aidhealth.StatusPoint.stPostponedID = stPostponedID

            //Получаем отдельные идентификаторы
            //Для этих идентификаторов ищем поинты с определенными статусами

            for (i in 0..usersCourses.size - 1) {
                var courseID = usersCourses.get(i).id //получаем айди курса
                User.courseId = courseID
                var coursesPointsObj =
                    dbCoursePoint.getCourseForUser() //получаем список поинтов для этого айди
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
                Toast.makeText(this, "Нет уведомлений", Toast.LENGTH_SHORT)
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
        calendar.set(Calendar.YEAR,date.year)
        calendar.set(Calendar.MONTH,date.month+1)
        calendar.set(Calendar.DAY_OF_MONTH,date.day)
        calendar.set(Calendar.HOUR_OF_DAY, time.hours)
        calendar.set(Calendar.MINUTE,time.minutes )
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        PointCourse.pointID = pointObj.id.toString()


        am.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
        Toast.makeText(this, "Аларм установлен", Toast.LENGTH_SHORT)

    }
*/

    }
