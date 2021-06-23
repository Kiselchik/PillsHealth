package diplom.dev.aidhealth.db.model

class Symptom {
    var id: Int = 0//
    var systemSymptomId = ""
    var email: String = ""//добавить
    var title: String?= ""//
   // var units: Int = 0//
   // var ball: Int = 0//

    constructor(email: String, systemSymptomId: String, title: String?){
        this.email = email
        this.systemSymptomId = systemSymptomId
        this.title = title
       // this.units = units
      //  this.ball = ball
    }
    constructor(){

    }
}