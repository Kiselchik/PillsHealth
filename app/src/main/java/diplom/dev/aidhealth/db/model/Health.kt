package diplom.dev.aidhealth.db.model

import android.media.Rating
import diplom.dev.aidhealth.notification.NotificationAction

class Health {
    var id = 0
    var email = ""
    var rating = ""
    var datetime = ""
    var notification = 0

    constructor(email: String, rating: String, datetime: String, notification: Int){
        this.email = email
        this.rating = rating
        this.datetime = datetime
        this.notification = notification
    }
    constructor(){

    }
}