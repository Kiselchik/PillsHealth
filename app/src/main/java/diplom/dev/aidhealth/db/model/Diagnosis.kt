package diplom.dev.aidhealth.db.model

class Diagnosis {
    var id : Int = 0 //
    var email: String = "" //
    var doctorID: Int = 0  //
    var diagnosisTitle: String = "" //
    var descr: String = "" //добавить
    var datetime: String = "" //доб

    constructor(email: String, doctorID: Int, diagnosisTitle: String, descr: String, datetime: String){
        this.email = email
        this.doctorID = doctorID
        this.diagnosisTitle = diagnosisTitle
        this.descr = descr
        this.datetime  = datetime
    }

    constructor(){

    }
}