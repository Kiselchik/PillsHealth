package diplom.dev.aidhealth.db.model
 class CourseHistory {
     var id: Int = 0
     var courseID = 0
     var statusID = 0
     var datetime  = ""

     constructor(courseID: Int, statusID: Int, datetime: String){
         this.courseID = courseID
         this.statusID = statusID
         this.datetime = datetime

     }
     constructor(){

     }


 }