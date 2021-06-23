package diplom.dev.aidhealth.db.model

class Doctor {
    var id: Int = 0//
    var firstName: String ="" //
    var lastName: String = ""//
    var position: String = ""//
    var address: String = ""//
    var email: String = ""//

    constructor(firstName: String, lastName: String, position: String, address: String, email: String){
        this.firstName = firstName
        this.lastName = lastName
        this. position = position
        this.address = address
        this.email = email
    }

    constructor(){

    }
}