package diplom.dev.aidhealth.db.model

class CoursePoint {
    var id: Int= 0
    var courseID: Int= 0
    var dose = ""
    var day: String = ""//день напоминания
    var time: String="" //время напоминания
    var datetimeStatus: String? = "" //время рисвоения статуса
    var statusPointID: Int = 0

    constructor(courseID: Int, dose: String, day: String, time: String, statusPointID: Int, datetimeStatus: String?) {
        this.courseID= courseID
        this.dose = dose
        this.day = day
        this.time = time
        this. statusPointID = statusPointID
        this.datetimeStatus = datetimeStatus
    }
    constructor(){

    }

}