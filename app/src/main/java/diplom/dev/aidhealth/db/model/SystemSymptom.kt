package diplom.dev.aidhealth.db.model

class SystemSymptom {
    var id = 0
    var title = ""
    var measurement = ""
    var mask = ""

    constructor(title: String, measurement: String, mask: String){
        this.title = title
        this.measurement = measurement
        this.mask = mask
    }

    constructor(){

    }
}