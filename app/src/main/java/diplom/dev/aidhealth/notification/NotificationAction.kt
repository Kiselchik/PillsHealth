package diplom.dev.aidhealth.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import diplom.dev.aidhealth.PointCourse
import diplom.dev.aidhealth.StatusPoint
import diplom.dev.aidhealth.User
import diplom.dev.aidhealth.db.handler.DbCourseHandler
import diplom.dev.aidhealth.db.handler.DbCoursePointHandler
import diplom.dev.aidhealth.db.handler.DbStatusPointHandler
import diplom.dev.aidhealth.db.model.CoursePoint
import java.text.SimpleDateFormat
import java.util.*

class NotificationAction {
    lateinit var pointObj: CoursePoint

    fun getNotificationInfo(context: Context) {
        var dbCourse = DbCourseHandler(context = context)
        var dbCoursePoint = DbCoursePointHandler(context = context)
        var coursesPoints = mutableListOf<CoursePoint>()
        //получаем идентификаторы курсов для этго пользователя
        var usersCourses = dbCourse.readCourseWithNotification()
        if (usersCourses.size != 0) {
            //получаем идентификаторы статуса ОЖИДАНИЕ
            var statusPoint = DbStatusPointHandler(context = context)
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
             //   restartNotify(pointObj)

            }

            }
        }
    }
/*
    private fun restartNotify(pointObj: CoursePoint, context: Context) {
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
        PointCourse.pointID = pointObj.id.toString()


        am.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)

        // alarmText.text = "alarm ok ${pointObj.day}, ${pointObj.time}"
    }
*/


