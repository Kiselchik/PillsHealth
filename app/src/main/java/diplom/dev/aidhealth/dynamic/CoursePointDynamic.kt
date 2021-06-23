package diplom.dev.aidhealth.dynamic

class CoursePointDynamic {
    var statuses : String
    var date = ""

    constructor(date: String, statuses: String){
        this.statuses = statuses
        this.date = date
    }
}