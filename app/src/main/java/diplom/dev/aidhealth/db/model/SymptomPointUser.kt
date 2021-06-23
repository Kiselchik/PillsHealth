package diplom.dev.aidhealth.db.model

class SymptomPointUser {
    var systemPointId = 0
    var symptomId  = 0
    var email = ""
    var value = ""
    var datetime = ""


    constructor(symptomId: Int, email: String, value: String, datetime: String){
        this.symptomId = symptomId
        this.email = email
        this.value = value
        this.datetime = datetime

    }

    constructor(){

    }
}