package diplom.dev.aidhealth.db.model

class Procedure {
    var id: Int = 0//
    var email: String = ""//
    var title: String = ""//
    var descr: String = ""//

    constructor(email: String, title: String, descr: String ) {
        this.email = email
        this.title = title
        this.descr = descr

    }

    constructor(){

    }

}