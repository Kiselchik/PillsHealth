package diplom.dev.aidhealth.notification

import android.app.PendingIntent
import android.app.PendingIntent.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import diplom.dev.aidhealth.PointCourse
import diplom.dev.aidhealth.R
import diplom.dev.aidhealth.activity.MainActivity

class NotificationCourse: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
     //   val okIntent = Intent(context, MainActivity::class.java)
     //   var piOk: PendingIntent = getActivity(context, 0 , okIntent, 0)


        val okIntent = Intent(context, NotificationAction::class.java)
        okIntent.putExtra("NOTIFICATION", true)
          var piOk: PendingIntent = getBroadcast(context, 0 , okIntent, FLAG_UPDATE_CURRENT )
        var builder = NotificationCompat.Builder(context, 1.toString())
            .setSmallIcon(R.drawable.ic_pills)
            .setContentTitle("${PointCourse.pointID}")
            .setContentText("Much longer text that cannot fit one line...")
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("Much longer text that cannot fit one line...")
            )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .addAction(R.drawable.add_button, "Отметить", piOk)
            .setOngoing(true)

        with(NotificationManagerCompat.from(context)) {
            // notificationId is a unique int for each notification that you must define
            notify(1, builder.build())
        }


    }

}