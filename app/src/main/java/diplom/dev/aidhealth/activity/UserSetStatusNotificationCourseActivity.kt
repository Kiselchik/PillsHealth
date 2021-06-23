package diplom.dev.aidhealth.activity

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import diplom.dev.aidhealth.R
import diplom.dev.aidhealth.db.handler.DbCoursePointHandler
import diplom.dev.aidhealth.db.handler.DbStatusPointHandler

class UserSetStatusNotificationCourseActivity : AppCompatActivity() {
    private lateinit var pointDoneBtn: Button
    private lateinit var pointLaterBtn: Button
    private lateinit var pointCancelBtn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_set_status_notification_course)
        initialise()
        setListener()
    }

    fun initialise(){
        pointDoneBtn = findViewById(R.id.pointDoneBtn)
        pointLaterBtn = findViewById(R.id.pointLaterBtn)
        pointCancelBtn = findViewById(R.id.pointCancelBtn)
    }
    private fun setListener(){
        pointDoneBtn.setOnClickListener(){
            var dbStatusPoint = DbStatusPointHandler(context = this)
            var statusID = dbStatusPoint.getIdStatusPointConfirmed().get(0).id
            //получить идентификатор поинта и поменять ему статус
            var dbCoursePoint = DbCoursePointHandler(context = this)
            dbCoursePoint.updateStatusPoint(statusID)
        }
        pointLaterBtn.setOnClickListener(){}
        pointCancelBtn.setOnClickListener(){}
    }
}