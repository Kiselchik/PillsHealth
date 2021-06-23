package diplom.dev.aidhealth.db.model

class DiagnosisSymptom {
    var id : Int = 0
    var symptomID: Int = 0
    var diagnosisID : Int =0
    //удалить курс

    constructor(symptomID: Int, diagnosisID: Int){
        this.symptomID = symptomID
        this.diagnosisID = diagnosisID
    }

    constructor(){

    }
}